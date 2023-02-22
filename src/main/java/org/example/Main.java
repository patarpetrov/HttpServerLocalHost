package org.example;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    public static void main(String[] args) throws IOException {
        ServerListenerThread serverListenerThread = new ServerListenerThread(8080, "");
        serverListenerThread.start();
    }
}