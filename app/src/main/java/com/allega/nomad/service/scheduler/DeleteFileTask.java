package com.allega.nomad.service.scheduler;

import com.allega.nomad.entity.Collection;
import com.allega.nomad.entity.Videos;
import com.allega.nomad.model.Purchase;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.util.VideoUtil;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

/**
 * Created by imnotpro on 6/21/15.
 */
public class DeleteFileTask extends GcmTaskService {

    private static final int PERIOD_SECS = 60 * 60 * 24;
    private static final int FLEX_SECS = 60 * 60 * 12;

    public static PeriodicTask generateTask() {
        return new PeriodicTask.Builder()
                .setService(DeleteFileTask.class)
                .setPeriod(PERIOD_SECS)
                .setFlex(FLEX_SECS)
                .setTag(DeleteFileTask.class.getSimpleName())
                .setPersisted(true)
                .setRequiredNetwork(Task.NETWORK_STATE_ANY)
                .setRequiresCharging(false)
                .build();
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        boolean isNoError = true;
        Date now = new Date();
        try {
            Realm realm = DatabaseManager.getInstance(this);
            List<Collection> collectionList = realm.where(Collection.class).findAll();
            for (Collection collection : collectionList) {
                try {
                    Purchase purchase = collection.getDetail().getLogOnMember().getPurchase();
                    Videos videos = collection.getDetail().getVideos();
                    if (purchase != null && videos != null) {
                        Date expiredDate = new Date(purchase.getExpiredAt());
                        if (now.after(expiredDate)) {
                            File videoFile = new File(VideoUtil.getVideo(videos));
                            if (videoFile.exists())
                                FileUtils.deleteQuietly(videoFile);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    isNoError = false;
                }
            }
        } catch (Exception e) {
            isNoError = false;
        }
        if (isNoError)
            return GcmNetworkManager.RESULT_SUCCESS;
        else
            return GcmNetworkManager.RESULT_FAILURE;
    }
}
