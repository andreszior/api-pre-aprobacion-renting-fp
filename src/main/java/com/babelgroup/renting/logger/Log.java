package com.babelgroup.renting.logger;

import com.babelgroup.renting.RentingApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    public static final Logger logger = LogManager.getLogger(RentingApplication.class);

    public static void logError(String msg, Exception e){
        logger.error(msg, e);
    }

    public static void logDebug(String msg){
        logger.debug(msg);
    }

    public static void logInfo(String msg){
        logger.info(msg);
    }

    public static void logWarn(String msg){
        logger.warn(msg);
    }

    public static void logFatal(String msg){
        logger.fatal(msg);
    }
}
