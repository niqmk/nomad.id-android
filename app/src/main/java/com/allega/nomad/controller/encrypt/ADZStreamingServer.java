package com.allega.nomad.controller.encrypt;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ADZStreamingServer {

    private String filePath;
    private String filename;
    private ServerSocket serverSocket;
    private Thread mainThread;
    private boolean isSecureVideo;

    private int port = 4321;
    private boolean isServerStart = true;

    public ADZStreamingServer(String filePath, boolean isSecureVideo) {
        this.filePath = filePath;
        this.isSecureVideo = isSecureVideo;

        generateFileName();
        startServerSocket();
    }

    public void stopServer() {
        isServerStart = false;
        if (this.serverSocket != null) {
            try {
                serverSocket.close();
                mainThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getVideoPath() {
        for (int i = 0; i < 5; i++) {

            if (this.serverSocket != null) {
                return "http://localhost:" + this.serverSocket.getLocalPort() + "/" + filename;
            }

            try {
                Thread.sleep(i * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private void generateFileName() {
        String[] splitString = filePath.split("/");
        if (splitString.length > 0) {
            filename = splitString[splitString.length - 1];
        }
    }

    private void startServerSocket() {
        mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(port);
                    while (isServerStart) {
                        if (!isServerStart) {
                            break;
                        }

                        Socket socket = serverSocket.accept();

                        ADZSocketHandler socketHandler = new ADZSocketHandler(socket, filePath, filename, isSecureVideo);
                        socketHandler.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mainThread.setDaemon(true);
        mainThread.setName("Stream Server");
        mainThread.start();
    }
}
