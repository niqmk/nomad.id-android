package com.allega.nomad.controller.encrypt.aes;

import com.allega.nomad.controller.encrypt.utils.ADZRandom;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class ADZAESKey {

    private static final int MIN_S_BOX_VALUE = -128;
    private static final int MAX_S_BOX_VALUE = 127;
    private static final int CIPHER_KEY_SIZE = 16;
    private List<Byte> sBoxList;
    private byte[] inverSBoxList;
    private byte[] cipherKey;
    private String key = "";

    public ADZAESKey(String key) {
        this.key = key;

        generateSBox();
        generateCipherKey(updateKey());
    }

    public void resetCipherKey() {
        generateCipherKey(updateKey());
    }

    public void changeCipherKey(byte[] newCipher) {
        for (int i = 0; i < cipherKey.length; i++) {
            cipherKey[i] = newCipher[i];
        }
    }

    private void generateCipherKey(String updatedKey) {
        try {
            byte[] rawCipherKey = updatedKey.getBytes("UTF8");

            List<Byte> cipherKeyByte = new ArrayList<Byte>();

            for (int i = 0; i < rawCipherKey.length; i++) {
                if (i < 16) {
                    cipherKeyByte.add(rawCipherKey[i]);
                } else {
                    ADZRandom random = new ADZRandom(rawCipherKey[i]);
                    cipherKeyByte = random.shuffleArray(cipherKeyByte);
                }
            }

            cipherKey = new byte[cipherKeyByte.size()];
            for (int i = 0; i < cipherKeyByte.size(); i++) {
                cipherKey[i] = cipherKeyByte.get(i);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void generateSBox() {
        sBoxList = new ArrayList<Byte>();
        ADZRandom random = new ADZRandom(key.length());
        for (int i = MIN_S_BOX_VALUE; i <= MAX_S_BOX_VALUE; i++) {
            sBoxList.add((byte) i);
        }

        sBoxList = random.shuffleArray(sBoxList);

        inverSBoxList = new byte[sBoxList.size()];
        for (int i = 0; i < sBoxList.size(); i++) {
            inverSBoxList[sBoxList.get(i) + 128] = (byte) (sBoxList.indexOf(sBoxList.get(i)) - 128);
        }
    }

    private String updateKey() {

        int diffSize = key.length() % CIPHER_KEY_SIZE;
        ADZRandom random = new ADZRandom(diffSize);
        StringBuilder stringBuilder = new StringBuilder(key);
        for (int i = 0; i < diffSize; i++) {
            int nextValue = random.nextInt() % 256;
            while (nextValue == 0) {
                nextValue = random.nextInt() % 256;
            }

            stringBuilder.append((char) (nextValue));
        }

        return stringBuilder.toString();
    }

    public List<Byte> getsBoxList() {
        return sBoxList;
    }

    public byte[] getCipherKey() {
        return cipherKey;
    }

    public byte[] getInverSBoxList() {
        return inverSBoxList;
    }

}
