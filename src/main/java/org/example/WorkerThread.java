package org.example;

import Parser.HttpParser;
import Parser.Request;
import RequestHandler.Handler;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WorkerThread extends Thread{
    private static final Logger LOGGER = Logger.getLogger(WorkerThread.class);
    private Socket socket;
    public WorkerThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        BasicConfigurator.configure();
        InputStream input = null;
        OutputStream output = null;
        try {
            final String CRLF = "\n\r";
            input = socket.getInputStream();
            output = socket.getOutputStream();
            Request request = new Request();

            HttpParser parser = new HttpParser();
            request = parser.parseHttpRequest(input);
            System.out.println("Request method: " + request.method);
            System.out.println("Request: " + request.request);
            System.out.println("Request HttpVersion: " + request.httpVersion);

            Handler handler = new Handler();
            String content1 = handler.handleRequest(request);
            if (content1.equals("")){
                String response = "HTTP/1.1 404 Not Found" + CRLF;
                output.write(response.getBytes());
                System.out.println("Status: Not Found");
                System.out.println("==============");
            }
            else{
                String content = "<html><head><title>Http-server</title></head><body><h1>Petar Stanislavov Petrov</h1></body</html>";
                String response = "HTTP/1.1 200 OK" + CRLF +
                        "Content-Length" + content1.getBytes().length + CRLF +
                        CRLF +
                        content1 +
                        CRLF + CRLF;
                System.out.println("Status: OK");
                System.out.println("==============");
                output.write(response.getBytes());
            }
            //LOGGER.info("Connection established");
        } catch (IOException e) {
            LOGGER.info("Problem with communication");
            throw new RuntimeException(e);
        }finally {
            if(input != null){
                try {
                    input.close();
                } catch (IOException e) {}
            }
            if (output != null){
                try {
                    output.close();
                } catch (IOException e) {}
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}
