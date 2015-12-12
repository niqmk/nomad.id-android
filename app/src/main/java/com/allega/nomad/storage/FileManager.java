package com.allega.nomad.storage;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by imnotpro on 6/16/15.
 */
public class FileManager {

    public static File getFolder(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
    }

    public static File getCacheFolder(Context context) {
        return context.getCacheDir();
    }

    public static File getFile(Context context, String url) {
        String fileName = getFileName(url);
        if (fileName != null) {
            return new File(getFolder(context), fileName);
        }
        return null;
    }

    public static String getFileName(String url) {
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }
}
