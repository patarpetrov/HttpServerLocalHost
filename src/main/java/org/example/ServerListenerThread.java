package org.example;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread{
    private static final Logger LOGGER = Logger.getLogger(ServerListenerThread.class);
    ServerSocket serverSocket;
    int port;
    String webroot;
    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }
    @Override
    public void run(){
        try{
            BasicConfigurator.configure();
            while(serverSocket.isBound() && !serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                WorkerThread workerThread = new WorkerThread(socket);
                workerThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
