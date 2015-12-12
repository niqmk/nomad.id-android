package com.allega.nomad.controller.encrypt;

import android.util.Log;

import com.allega.nomad.controller.encrypt.aes.ADZAESException;
import com.allega.nomad.controller.encrypt.aes.ADZAESGenerator;
import com.allega.nomad.controller.encrypt.utils.ADZSocketConstant;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class ADZSocketHandler extends Thread {

    String key = "qwertyuiopasdfghjklzxcvbnm";
    private int partSize = 10240;

    private Socket socket;
    private String fileExtension = null;

    private String headerStatus = null;
    private String headerMessage = null;
    private int sendCount = 0;
    private int diffSeek = 0;
    private boolean isSecureVideo;

    private File file;
    private RandomAccessFile randomAccessFile;

    public ADZSocketHandler(Socket socket, String filePath, String fileName, boolean isSecureVideo) {
        this.socket = socket;
        this.isSecureVideo = isSecureVideo;

        int indexOfSeparator = fileName.indexOf(".");
        if (indexOfSeparator > 0) {
            this.fileExtension = fileName.substring(indexOfSeparator + 1);
        }
        Log.d("DEBUG", "Check " + filePath);
        this.file = new File(filePath);

        Log.d("DEBUG", "Check " + file.exists());

    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            randomAccessFile = new RandomAccessFile(file, "r");

            Properties headerRequest = new Properties();
            List<String> headerResponse = null;

            if (validateHeaderRequest(bufferedReader, headerRequest)) {
                headerResponse = generateHeaderResponse(headerRequest);
            }

            sendData(headerResponse);

            //Close All
            randomAccessFile.close();
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("DEBUG", "DONE!!!!");
        super.run();
    }

    private boolean validateHeaderRequest(BufferedReader bufferedReader, Properties properties) throws IOException {
        String data = bufferedReader.readLine();
        String[] splitRequest = data.split(" ");
        if (splitRequest.length == 3) {
            if (!splitRequest[0].equals(ADZSocketConstant.METHOD_GET)) {
                headerStatus = ADZSocketConstant.HTTP_BAD_REQUEST;
                headerMessage = "Invalid Method Request";
                return false;
            }

            if (!splitRequest[2].startsWith("HTTP")) {
                headerStatus = ADZSocketConstant.HTTP_BAD_REQUEST;
                headerMessage = "Malformed Url";
                return false;
            } else {
                ADZSocketConstant.REQUEST_HTTP_FORMAT = splitRequest[2];
            }

        } else {
            headerStatus = ADZSocketConstant.HTTP_BAD_REQUEST;
            headerMessage = "Malformed Url";
            return false;
        }

        while (true) {
            data = bufferedReader.readLine();
            if (data == null || data.equals("")) {
                break;
            }

            int indexOfSeparator = data.indexOf(":");
            if (indexOfSeparator > 0) {
                String key = data.substring(0, indexOfSeparator).trim().toLowerCase(Locale.US);
                String value = data.substring(indexOfSeparator + 1).trim();
                properties.put(key, value);
                Log.d("DEBUG", "READ [" + data + "]");
            }
        }

        return true;
    }

    private List<String> generateHeaderResponse(Properties headerRequest) throws IOException {
        List<String> listHeader = new ArrayList<String>();
        long startSeek = 0;

        if (fileExtension == null) {
            headerStatus = ADZSocketConstant.HTTP_INTERNAL_ERROR;
            headerMessage = "File is invalid";
            return null;
        }

        if (file.length() < 0) {
            headerStatus = ADZSocketConstant.HTTP_INTERNAL_ERROR;
            headerMessage = "File is not found";
            return null;
        }

        listHeader.add(ADZSocketConstant.HEADER_ACCEPT_RANGES + ": " + ADZSocketConstant.VALUE_BYTE + " \r\n");

        if (headerRequest.containsKey(ADZSocketConstant.HEADER_RANGE)) {
            //Send Data with Seek index
            String range = headerRequest.getProperty(ADZSocketConstant.HEADER_RANGE);

            if (!range.startsWith(ADZSocketConstant.PREFIX_RANGE)) {
                headerStatus = ADZSocketConstant.HTTP_416;
                headerMessage = "Failure Invalid Range";
                return null;
            }

            range = range.substring(ADZSocketConstant.PREFIX_RANGE.length());
            int indexOfMinus = range.indexOf(ADZSocketConstant.PREFIX_MINUS);
            long endSeek = -1;

            //If Seek request Start and End Position
            if (indexOfMinus > 0) {
                try {
                    String startSeekString = range.substring(0, indexOfMinus);
                    String endSeekString = range.substring(indexOfMinus + ADZSocketConstant.PREFIX_MINUS.length());
                    startSeek = Long.parseLong(startSeekString);
                    endSeek = Long.parseLong(endSeekString);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            if (endSeek < 0) {
                endSeek = file.length() - 1;
            }

            if (startSeek >= file.length()) {
                headerStatus = ADZSocketConstant.HTTP_416;
                headerMessage = "Failure Invalid Start Seek";
                return null;
            }

            sendCount = (int) (endSeek - startSeek + 1);
            if (sendCount < 0) {
                sendCount = 0;
            }

            diffSeek = (int) startSeek % partSize;

            String rangeForResponse = ADZSocketConstant.VALUE_BYTE + " " + startSeek + "-" + endSeek + "/" + file.length();

            listHeader.add(ADZSocketConstant.HEADER_CONTENT_RANGE + ": " + rangeForResponse + " \r\n");
            listHeader.add(ADZSocketConstant.HEADER_CONTENT_LENGHT + ": " + sendCount + " \r\n");

            headerStatus = ADZSocketConstant.HTTP_206;
        } else {
            //Send Data with no seek index
            sendCount = (int) file.length();
            listHeader.add(ADZSocketConstant.HEADER_CONTENT_LENGHT + ": " + sendCount + " \r\n");

            headerStatus = ADZSocketConstant.HTTP_200;
        }

        randomAccessFile.seek(startSeek - diffSeek);
//        randomAccessFile.seek(startSeek);

        return listHeader;
    }

    private void sendData(List<String> headerResponse) throws IOException {
        OutputStream outputStream = socket.getOutputStream();

        String requestResponse = ADZSocketConstant.REQUEST_HTTP_FORMAT + " " + headerStatus + " \r\n";
        String firstHeader = ADZSocketConstant.HEADER_CONTENT_TYPE + ": " + fileExtension + " \r\n";

        outputStream.write(requestResponse.getBytes());
        outputStream.write(firstHeader.getBytes());
        Log.d("DEBUG", "SEND " + requestResponse);
        Log.d("DEBUG", "SEND " + firstHeader);

        if (headerResponse != null) {
            //Send Header
            for (int i = 0; i < headerResponse.size(); i++) {
                outputStream.write(headerResponse.get(i).getBytes());
                Log.d("DEBUG", "SEND " + headerResponse.get(i));
            }

            outputStream.write("\r\n".getBytes());
            outputStream.flush();

            //Send Data
            sendDataFile(outputStream);

            outputStream.flush();
        } else {
            outputStream.write("\r\n".getBytes());
            outputStream.flush();

            //Send Error Message
            outputStream.write(headerMessage.getBytes());
            outputStream.flush();
        }

        outputStream.close();
    }


    private void sendDataFile(OutputStream outputStream) throws IOException {
        int maxSendFile = sendCount;
        while (maxSendFile > 0) {
            byte[] bufferData = new byte[partSize];
            int readCount = randomAccessFile.read(bufferData);
            int writeCount = readCount;

            if (readCount < 0) {
                break;
            }

            if (maxSendFile < readCount) {
                writeCount = maxSendFile;
                maxSendFile = -1;
            }

            if (isSecureVideo) {
                bufferData = decrypt(bufferData);
            }

            maxSendFile -= (readCount - diffSeek);
            outputStream.write(bufferData, diffSeek, writeCount - diffSeek);

            if (diffSeek != 0) {
                diffSeek = 0;
            }
        }
    }

    private byte[] decrypt(byte[] cipher) {
        try {
            byte[] result = ADZAESGenerator.decrypt(cipher, key, partSize);
            return result;
        } catch (ADZAESException e) {
            e.printStackTrace();
        }

        return cipher;
    }
}
