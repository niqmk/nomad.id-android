package com.allega.nomad.controller.encrypt.aes;


public class ADZAESGenerator {


    private static final int BLOCK_SIZE = 16;
    private static int[] mixMultiplierX = new int[]{2, 3, 1, 1, 1, 2, 3, 1, 1, 1, 2, 3, 3, 1, 1, 2};
    private static int[] inversMixMultiplierX = new int[]{14, 11, 13, 9, 9, 14, 11, 13, 13, 9, 14, 11, 11, 13, 9, 14};

    private static byte[] addRoundKey(byte[] cipher, ADZAESKey key) {
        byte[] result = new byte[cipher.length];
        for (int i = 0; i < cipher.length; i++) {
            result[i] = ADZAESUtils.xorBytes(cipher[i], key.getCipherKey()[i]);
        }

        return result;
    }

    private static byte[] subBytes(byte[] cipher, ADZAESKey key) {
        byte[] result = new byte[cipher.length];
        for (int i = 0; i < cipher.length; i++) {
            result[i] = key.getsBoxList().get((int) cipher[i] + 128);
        }

        return result;
    }

    private static byte[] shiftRows(byte[] cipher) {
        byte[] result = new byte[cipher.length];
        for (int i = 0; i < cipher.length; i++) {
            int colPosition = i / 4;
            int difPosition = i % 4;
            int newPosition = difPosition - colPosition;
            if (newPosition < 0) {
                newPosition += 4;
            }

            newPosition = colPosition * 4 + newPosition;
            result[i] = cipher[newPosition];
        }

        return result;
    }

    protected static byte[] mixColumns(byte[] cipher) {

        byte[] result = new byte[cipher.length];

        for (int i = 0; i < result.length; i++) {
            int colIndex = i / 4;
            int rowIndex = i % 4;
            int mixIndex = colIndex * 4;

            result[i] = (byte) (0xff & (
                    ADZAESUtils.multipleGaloisField((cipher[(0 * 4 + rowIndex)]), mixMultiplierX[mixIndex]) ^
                            ADZAESUtils.multipleGaloisField((cipher[(1 * 4 + rowIndex)]), mixMultiplierX[mixIndex + 1]) ^
                            ADZAESUtils.multipleGaloisField((cipher[(2 * 4 + rowIndex)]), mixMultiplierX[mixIndex + 2]) ^
                            ADZAESUtils.multipleGaloisField((cipher[(3 * 4 + rowIndex)]), mixMultiplierX[mixIndex + 3])));
        }

        return result;
    }

    private static byte[] inversSubBytes(byte[] cipher, ADZAESKey key) {
        byte[] result = new byte[cipher.length];
        for (int i = 0; i < cipher.length; i++) {
            result[i] = key.getInverSBoxList()[cipher[i] + 128];
        }

        return result;
    }

    private static byte[] inversShiftRows(byte[] cipher) {
        byte[] result = new byte[cipher.length];
        for (int i = 0; i < cipher.length; i++) {
            int colPosition = i / 4;
            int difPosition = i % 4;
            int newPosition = difPosition + colPosition;
            if (newPosition >= 4) {
                newPosition -= 4;
            }

            newPosition = colPosition * 4 + newPosition;
            result[i] = cipher[newPosition];
        }

        return result;
    }

    protected static byte[] inversMixColumns(byte[] cipher) {
        byte[] result = new byte[cipher.length];
        for (int i = 0; i < result.length; i++) {
            int colIndex = i / 4;
            int rowIndex = i % 4;
            int mixIndex = colIndex * 4;

            result[i] = (byte) (0xff & (
                    ADZAESUtils.multipleGaloisField((cipher[(0 * 4 + rowIndex)]), inversMixMultiplierX[mixIndex]) ^
                            ADZAESUtils.multipleGaloisField((cipher[(1 * 4 + rowIndex)]), inversMixMultiplierX[mixIndex + 1]) ^
                            ADZAESUtils.multipleGaloisField((cipher[(2 * 4 + rowIndex)]), inversMixMultiplierX[mixIndex + 2]) ^
                            ADZAESUtils.multipleGaloisField((cipher[(3 * 4 + rowIndex)]), inversMixMultiplierX[mixIndex + 3])));
        }


        return result;
    }

    private static byte[] encryptPerBlock(byte[] dataPerBlock, ADZAESKey aeskey) {
        byte[] result = new byte[dataPerBlock.length];

        result = addRoundKey(dataPerBlock, aeskey);
        for (int i = 0; i < 1; i++) {
            result = subBytes(result, aeskey);
            result = shiftRows(result);
//			result = mixColumns(result);
            result = addRoundKey(result, aeskey);
        }

        result = subBytes(result, aeskey);
        result = shiftRows(result);
        result = addRoundKey(result, aeskey);

        return result;
    }

    private static byte[] decryptPerBlock(byte[] dataPerBlock, ADZAESKey aesKey) {
        byte[] result = new byte[dataPerBlock.length];

        result = addRoundKey(dataPerBlock, aesKey);
        for (int i = 0; i < 1; i++) {
            result = inversShiftRows(result);
            result = inversSubBytes(result, aesKey);
            result = addRoundKey(result, aesKey);
//			result = inversMixColumns(result);
        }

        result = inversShiftRows(result);
        result = inversSubBytes(result, aesKey);
        result = addRoundKey(result, aesKey);

        return result;
    }

    private static byte[] encryptPerPart(byte[] data, ADZAESKey aeskey) {
        byte[] result = new byte[data.length];
        int blockCount = data.length / BLOCK_SIZE;
        int diffBlock = data.length % BLOCK_SIZE;

        for (int i = 0; i < blockCount; i++) {
            byte[] block = new byte[BLOCK_SIZE];

            for (int j = 0; j < block.length; j++) {
                block[j] = data[i * block.length + j];
            }

            byte[] resultBlock = encryptPerBlock(block, aeskey);
            aeskey.changeCipherKey(resultBlock);

            for (int j = 0; j < resultBlock.length; j++) {
                result[i * resultBlock.length + j] = resultBlock[j];
            }
        }

        for (int i = 0; i < diffBlock; i++) {
            result[blockCount * BLOCK_SIZE + i] = data[blockCount * BLOCK_SIZE + i];
        }

        return result;
    }

    private static byte[] decryptPerPart(byte[] data, ADZAESKey aeskey) {
        byte[] result = new byte[data.length];
        int blockCount = data.length / BLOCK_SIZE;
        int diffBlock = data.length % BLOCK_SIZE;

        for (int i = 0; i < blockCount; i++) {
            byte[] block = new byte[BLOCK_SIZE];


            for (int j = 0; j < block.length; j++) {
                block[j] = data[i * block.length + j];
            }

            byte[] resultBlock = decryptPerBlock(block, aeskey);
            aeskey.changeCipherKey(block);

            for (int j = 0; j < resultBlock.length; j++) {
                result[i * resultBlock.length + j] = resultBlock[j];
            }
        }

        for (int i = 0; i < diffBlock; i++) {
            result[blockCount * BLOCK_SIZE + i] = data[blockCount * BLOCK_SIZE + i];
        }

        return result;
    }

    protected static byte[] insertAdditionalByte(byte[] data) {
        int additinalByteLenght = 0;
        int remainPart = data.length % BLOCK_SIZE;
        if (remainPart != 0) {
            additinalByteLenght = BLOCK_SIZE - remainPart;
        }

        int additionalDataLenght = data.length + additinalByteLenght;
        byte[] result = new byte[additionalDataLenght];

        for (int i = 0; i < additionalDataLenght; i++) {
            if (i < data.length) {
                result[i] = data[i];
            } else {
                result[i] = 0;
            }
        }

        return result;
    }

    protected static byte[] removeAdditionalByte(byte[] data) {
        int zeroByteCounter = 0;
        for (int i = data.length - 1; i >= 0; i--) {
            if (data[i] == 0) {
                zeroByteCounter++;
            } else {
                break;
            }
        }

        int planDataLenght = data.length - zeroByteCounter;
        byte[] result = new byte[planDataLenght];

        for (int i = 0; i < planDataLenght; i++) {
            result[i] = data[i];
        }

        return result;
    }

    public static byte[] encrypt(byte[] data, String key, int partSize) throws ADZAESException {
        if ((partSize % BLOCK_SIZE) != 0) {
            throw new ADZAESException("Part size must be multiple of " + BLOCK_SIZE);
        }

//		data = insertAdditionalByte(data);

        int partCount = data.length / partSize;
        int diffPart = data.length % partSize;
        byte[] result = new byte[data.length];
        ADZAESKey aesKey = new ADZAESKey(key);

        for (int i = 0; i < partCount; i++) {
            byte[] partData = new byte[partSize];
            for (int j = 0; j < partData.length; j++) {
                partData[j] = data[i * partSize + j];
            }

            partData = encryptPerPart(partData, aesKey);

            for (int j = 0; j < partData.length; j++) {
                result[i * partData.length + j] = partData[j];
            }

            aesKey.resetCipherKey();
        }


        byte[] partData = new byte[diffPart];
        for (int i = 0; i < diffPart; i++) {
            partData[i] = data[partCount * partSize + i];
        }

        partData = encryptPerPart(partData, aesKey);

        for (int i = 0; i < diffPart; i++) {
            result[partCount * partData.length + i] = partData[i];
        }

        return result;
    }

    public static byte[] decrypt(byte[] data, String key, int partSize) throws ADZAESException {
        if ((partSize % BLOCK_SIZE) != 0) {
            throw new ADZAESException("Part size must be multiple of " + BLOCK_SIZE);
        }

        int partCount = data.length / partSize;
        int diffPart = data.length % partSize;
        byte[] result = new byte[data.length];
        ADZAESKey aesKey = new ADZAESKey(key);

        for (int i = 0; i < partCount; i++) {
            byte[] partData = new byte[partSize];
            for (int j = 0; j < partData.length; j++) {
                partData[j] = data[i * partSize + j];
            }

            partData = decryptPerPart(partData, aesKey);

            for (int j = 0; j < partData.length; j++) {
                result[i * partData.length + j] = partData[j];
            }

            aesKey.resetCipherKey();
        }


        byte[] partData = new byte[diffPart];
        for (int i = 0; i < diffPart; i++) {
            partData[i] = data[partCount * partSize + i];
        }

        partData = decryptPerPart(partData, aesKey);

        for (int i = 0; i < diffPart; i++) {
            result[partCount * partData.length + i] = partData[i];
        }

//		result = removeAdditionalByte(result);

        return result;
    }

    public static void check() {

//		byte x = ADZAESUtils.xorBytes((byte)1025, (byte)1225);
//		System.out.println("XOR RESULT "+x);

        String key = "qwertyuiopasdfghjklzxcvbnm";
        ADZAESKey aesKey = new ADZAESKey(key);

        byte[] rawData = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        byte[] shifting = shiftRows(rawData);
        byte[] subs = subBytes(rawData, aesKey);
        byte[] addRound = addRoundKey(rawData, aesKey);

        for (int i = 0; i < rawData.length; i++) {
            System.out.println("Raw " + rawData[i] + " Shift:" + shifting[i] + " Subs:" + subs[i] + " AddRound:" + addRound[i]);
        }


    }
}
