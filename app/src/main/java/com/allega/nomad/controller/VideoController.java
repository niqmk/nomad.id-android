package com.allega.nomad.controller;

import android.content.Context;

import com.allega.nomad.controller.encrypt.aes.ADZAESException;
import com.allega.nomad.controller.encrypt.aes.ADZAESGenerator;
import com.allega.nomad.storage.FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by imnotpro on 6/16/15.
 */
public class VideoController {

    private static final int SIZE = 1024 * 10;
    private static String key = "qwertyuiopasdfghjklzxcvbnm";

    public static File prepareVideoFile(Context context, File file) {
        File decryptFile = new File(FileManager.getCacheFolder(context), file.getName());
        if (decryptFile.exists() && decryptFile.length() == file.length())
            return decryptFile;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileOutputStream = new FileOutputStream(decryptFile);

            byte[] bufferData = new byte[SIZE];
            int length;
            while ((length = fileInputStream.read(bufferData)) > 0) {
                byte[] decryptByte = ADZAESGenerator.decrypt(bufferData, key, SIZE);
                fileOutputStream.write(decryptByte, 0, length);
            }
            fileOutputStream.flush();
            return decryptFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ADZAESException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (fileOutputStream != null)
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }
}
