package com.allega.nomad.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.DownloadFinishEvent;
import com.allega.nomad.bus.event.DownloadPauseEvent;
import com.allega.nomad.bus.event.DownloadProgressEvent;
import com.allega.nomad.entity.PendingDownload;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.storage.FileManager;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.realm.Realm;

/**
 * Created by imnotpro on 6/10/15.
 */
public class DownloadService extends IntentService {

    private static final int SIZE = 1024;
    private static final String EXTRA_URL = "extra-url";
    private static String currentDownload;

    public DownloadService() {
        super(DownloadService.class.getName());
    }

    public static void startService(Context context, PendingDownload pendingDownload) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(EXTRA_URL, pendingDownload.getUrl());
        context.startService(intent);
    }

    public static boolean isStillDownload() {
        return currentDownload != null;
    }

    public static boolean isStillDownload(String url) {
        return currentDownload != null && currentDownload.equals(url);
    }

    public static void stopDownload() {
        currentDownload = null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String location = intent.getStringExtra(EXTRA_URL);
        currentDownload = location;
        Realm realm = DatabaseManager.getInstance(this);
        PendingDownload pendingDownload = realm.where(PendingDownload.class).equalTo(PendingDownload.FIELD_URL, location).findFirst();
        File file = FileManager.getFile(this, location);

        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL(pendingDownload.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            long max = 0;
            List values = connection.getHeaderFields().get("content-Length");
            if (values != null && !values.isEmpty()) {
                String sLength = (String) values.get(0);
                if (sLength != null) {
                    max = Long.parseLong(sLength);
                }
            }
            connection.disconnect();
            connection = (HttpURLConnection) url.openConnection();
            long downloaded = 0;
            if (file != null && file.exists()) {
                downloaded = file.length();
                if (max > 0 && max == downloaded) {
                    DownloadFinishEvent downloadFinishEvent = new DownloadFinishEvent();
                    downloadFinishEvent.setFile(file);
                    Bus.getInstance().post(downloadFinishEvent);
                    return;
                }
                connection.setRequestProperty("Range", "bytes=" + downloaded + "-");
                fileOutputStream = new FileOutputStream(file, true);
            } else {
                fileOutputStream = new FileOutputStream(file);
            }
            connection.setDoInput(true);
            connection.connect();
            if (max == 0)
                max = connection.getContentLength();
            bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream, SIZE);
            byte[] data = new byte[SIZE];
            int read = 0;
            int step = 0;
            while ((read = bufferedInputStream.read(data, 0, SIZE)) >= 0) {
                bufferedOutputStream.write(data, 0, read);
                downloaded += read;
                if (currentDownload == null && step % 100 == 0) {
                    DownloadPauseEvent downloadPauseEvent = new DownloadPauseEvent();
                    downloadPauseEvent.setUrl(pendingDownload.getUrl());
                    Bus.getInstance().post(downloadPauseEvent);
                    return;
                }
                DownloadProgressEvent downloadProgressEvent = new DownloadProgressEvent(location);
                downloadProgressEvent.setCurrent(downloaded);
                downloadProgressEvent.setMax(max);
                Bus.getInstance().post(downloadProgressEvent);
                step++;
            }
            realm.beginTransaction();
            pendingDownload.removeFromRealm();
            realm.commitTransaction();
            DownloadFinishEvent downloadFinishEvent = new DownloadFinishEvent();
            downloadFinishEvent.setFile(file);
            Bus.getInstance().post(downloadFinishEvent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bufferedInputStream);
            IOUtils.closeQuietly(bufferedOutputStream);
            IOUtils.closeQuietly(fileOutputStream);
            currentDownload = null;
        }
    }
}
