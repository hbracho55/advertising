package com.sm360.advertising.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerApp {

    static Logger getLogApp(){
        return Logger.getGlobal();
    }

    public static Logger getLogApp(String pack, String fileName, Level level){
        Logger logApp=null;
        logApp = Logger.getLogger(pack);

        try{
            FileHandler handler = new FileHandler(fileName);
            SimpleFormatter formatterTxt = new SimpleFormatter();
            handler.setFormatter(formatterTxt);
            logApp.addHandler(handler);
        }catch (IOException e){
            logApp = Logger.getGlobal();
            logApp.severe("Error in log creation: "+e.getMessage());
            return  logApp;
        }
        logApp.setLevel(level);
        return logApp;

    }
}
