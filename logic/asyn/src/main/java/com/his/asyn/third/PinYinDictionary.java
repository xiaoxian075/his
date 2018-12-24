package com.his.asyn.third;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 *
 * @author lil
 */
public class PinYinDictionary {
    private final static Logger logger = LoggerFactory.getLogger(PinYinDictionary.class);

    //private static final String FILE_NAME = "D:/py4j.txt";
    private static final String PINYIN_SEPARATOR = "#";
    private static final String WORD_SEPARATOR = "/";

    public static synchronized Map<String,List<String>> load(String fileName) {
        debug("******start load py4j config******");
        Map<String,List<String>> duoYinZiMap = new HashMap<>();
        try{
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> urls = cl.getResources(fileName);
            if(urls!=null){
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    parseURL(url, duoYinZiMap);
                }
            }
        } catch (Exception e){
            error("caught exception when load py4j vocabulary", e);
            throw new RuntimeException("caught exception when load py4j vocabulary", e);
        }
        debug("******load py4j config over******");
        debug("py4j map key size:{}", duoYinZiMap.keySet().size());
        return duoYinZiMap;
    }

    private static void parseURL(URL url, Map<String, List<String>> duoYinZiMap){
        debug("load py4j dictionary file:{}", url.getPath());
        InputStream in = null;
        BufferedReader br = null;
        try {
            in = url.openStream();
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {

                String[] arr = line.split(PINYIN_SEPARATOR);

                if (StringUtils.isNotEmpty(arr[1])) {
                    String[] dyzs = arr[1].split(WORD_SEPARATOR);
                    for (String dyz : dyzs) {
                        if (StringUtils.isNotEmpty(dyz)) {
//                            duoYinZiMap.put(arr[0], dyz.trim());
                            List<String> myClassList = duoYinZiMap.get(arr[0]);
                            if(myClassList == null) {
                                myClassList = new ArrayList<>();
                                myClassList.add(dyz.trim());
                                duoYinZiMap.put(arr[0],myClassList);
                            }
                            myClassList.add(dyz.trim());
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("load py4j config:%s error", url), e);
        } finally {
            try {
                if(br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void error(String msg, Throwable err){
        logger.error(msg, err);
    }

    private static void debug(String msg, Object... args) {
        if(logger.isDebugEnabled()) {
            logger.debug(msg, args);
        }
    }

//    private static class SingletonHolder {
//        private static final PinYinDictionary INSTANCE = new PinYinDictionary();
//    }
}
