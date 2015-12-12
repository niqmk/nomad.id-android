package com.allega.nomad.storage;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.allega.nomad.entity.Collection;
import com.allega.nomad.entity.Message;
import com.allega.nomad.entity.WatchListVideo;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by imnotpro on 6/27/15.
 */
public class DatabaseManager {

    public static Realm getInstance(Context context) {
        return Realm.getInstance(getConfiguration(context));
    }

    public static RealmConfiguration getConfiguration(Context context) {
        byte[] salt = {
                (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
                (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99
        };
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final int iterations = 1000;
        final int outputKeyLength = 64 * 8;
        byte[] key = new byte[0];
        SecretKeyFactory secretKeyFactory = null;
        try {
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(telephonyManager.getDeviceId().toCharArray(), salt, iterations, outputKeyLength);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
            key = secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return new RealmConfiguration.Builder(context)
                .encryptionKey(key)
                .build();
    }

    public static void clearPersonalDatabase(Context context) {
        Realm realm = DatabaseManager.getInstance(context);
        realm.beginTransaction();
        realm.clear(Collection.class);
        realm.clear(WatchListVideo.class);
        realm.clear(Message.class);
        realm.commitTransaction();
    }
}
