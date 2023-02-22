package Parser;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.InputStreamReader;

public class Request {
    public String method;
    public String request;
    public String httpVersion;
    private static final Logger LOGGER = Logger.getLogger(HttpParser.class);
    int setMethod(String methodName){
        BasicConfigurator.configure();
        for (Methods method : Methods.values()){
            ///LOGGER.debug(method.name());
            if (methodName.equals(method.name())){
                this.method = methodName;
                //LOGGER.debug("Method set succesfully: {" + methodName + "}");
                return 1;
            }
        }
        LOGGER.error("Method not allowed: {" + methodName + "}");

        return -1;
    }
    int setRequest(String requestToSet){
        BasicConfigurator.configure();
        if(requestToSet.length() != 0)
        {
            this.request = requestToSet;
            return 1;
        }
        LOGGER.error("Can`t handle request");
        return -1;
    }
    int setHttpVersion(String versionTobeSet){
        this.httpVersion = versionTobeSet;
        return 1;
    }

}
