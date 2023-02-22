package RequestHandler;

import Parser.Request;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Handler {
    public Request request;
    public String handleRequest(Request request){
        String FileName = request.request.substring(1);

        String path = "";
        String content = "";

        for (Files filename : Files.values()){
            if (filename.name().equals(FileName)){
                path = filename.filepath;

            }
            if (FileName == ""){
                path = Files.index.filepath;
            }
        }
        if (path == ""){
            //System.out.println("File not found");
            return path;
        }
        else{
            try {
                //System.out.println(path);
                content = htmlToString(path);
            } catch (FileNotFoundException e) {
                System.out.println("Can`t downloadfile");
            }
        }
        return content;
    }
    private String htmlToString(String filepath) throws FileNotFoundException {
        FileReader reader = new FileReader(filepath);

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(reader);
        String line;
        while (true) {
            try {
                if((line = br.readLine()) != null) {
                    sb.append(line);
                }
                else{
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return sb.toString();
    }
}
