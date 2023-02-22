package Parser;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.example.Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.http.HttpRequest;

public class HttpParser {
    private static final Logger LOGGER = Logger.getLogger(HttpParser.class);
    public Request parseHttpRequest(InputStream inputStream){
        InputStreamReader reader = new InputStreamReader(inputStream);
        Request request = new Request();
        try {
            parseRequestLine(reader, request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return request;
    }
    private void parseRequestLine(InputStreamReader reader, Request request) throws IOException {
        int _byte;
        boolean methodParsed = false;
        boolean requestTargetParsed = false;
        StringBuilder processingDataBuffer = new StringBuilder();
        BasicConfigurator.configure();
        int SP = 32;
        int CR = 13;
        int LF = 10;
        while((_byte = reader.read()) >= 0){
            if(_byte == CR){
                _byte = reader.read();
                if (_byte == LF){
                    //LOGGER.debug("Request Line in Version: " + processingDataBuffer);
                    if (request.setHttpVersion(processingDataBuffer.toString()) <= 0){

                    }
                    return;

                }
            }
            if(_byte == SP){
                if(!methodParsed){
                    //LOGGER.debug("Method: " + processingDataBuffer);
                    if (request.setMethod(processingDataBuffer.toString()) > 0){
                        methodParsed = true;
                    };
                }
                else if(!requestTargetParsed){
                    //LOGGER.debug("Reuquest: " + processingDataBuffer.toString());
                    if(request.setRequest(processingDataBuffer.toString()) > 0){
                        requestTargetParsed = true;
                    }
                }
                processingDataBuffer.delete(0, processingDataBuffer.length());
            }
            else {
                processingDataBuffer.append((char) _byte);
            }
        }
    }
}
