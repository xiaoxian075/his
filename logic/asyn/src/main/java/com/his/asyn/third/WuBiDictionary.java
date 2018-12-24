package com.his.asyn.third;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author lil
 */
public class WuBiDictionary {
    private final static Logger logger = LoggerFactory.getLogger(WuBiDictionary.class);

    public static synchronized Properties load(String fileName){
        debug("******start load wubi properties******");
        Properties wb86 = null;
        try{
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            wb86 = new Properties();
            try {
                wb86.load(new BufferedInputStream(cl
                        .getResourceAsStream(fileName)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e){
            error("caught exception when load wubi properties", e);
            throw new RuntimeException("caught exception when load wubi properties", e);
        }
        debug("******load wubi config over******");
        return wb86;
    }



    private static void error(String msg, Throwable err){
        logger.error(msg, err);
    }

    private static void debug(String msg, Object... args) {
        if(logger.isDebugEnabled()) {
            logger.debug(msg, args);
        }
    }
}
