package vn.ript.api.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomLogger<T> {

    final static Level CUSTOM_INFO = Level.forName("CUSTOM_INFO", 450);
    final static Level CUSTOM_WARN = Level.forName("CUSTOM_WARN", 350);
    final static Level CUSTOM_ERROR = Level.forName("CUSTOM_ERROR", 250);
    Logger logger;

    public CustomLogger(T t) {
        this.logger = LogManager.getLogger(t.getClass());
    }

    public void info(Object obj) {
        System.out.println("==============Start INFO Log==============");
        this.logger.log(CUSTOM_INFO, obj.toString());
        System.out.println("===============End INFO Log===============");
    }

    public void warn(Object obj) {
        System.out.println("==============Start WARN Log==============");
        this.logger.log(CUSTOM_WARN, obj.toString());
        System.out.println("===============End WARN Log===============");
    }

    public void error(Object obj) {
        System.out.println("==============Start ERROR Log==============");
        this.logger.log(CUSTOM_ERROR, obj.toString());
        System.out.println("===============End ERROR Log===============");
    }

}
