package com.tf.sdk.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Md5
 * @author chenjx
 * @Version 1.0.0
 * @time   2018年5月11日
 */
public class Md5Util {
	private final static Logger logger = LoggerFactory.getLogger(Md5Util.class);
	

    
    
    /**
     * 对字符串md5加密(大写+数字)
     * @param str 传入要加密的字符串
     * @return  MD5加密后的字符串
     */
    //private final static char HEX_DIGITS_UPPER[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    private final static char HEX_DIGITS[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    
    public static String md5(String data) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(data.getBytes());
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
                str[k++] = HEX_DIGITS[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
        	logger.error(e.getMessage());
            return null;
        }
    }
    
    public static String md5ToUpper(String data) {
    	String result = md5(data);
    	if (result == null) {
    		return null;
    	}
    	return result.toUpperCase();
    }
    
    /**
	 * MD5生成签名算法
	 * @param data
	 * @param sign
	 * @return
	 */
    public static String getSign(Map<String, String> data, String key) {
    	try {
	        Set<String> keySet = data.keySet();
	        String[] keyArray = keySet.toArray(new String[keySet.size()]);
	        Arrays.sort(keyArray);
	        StringBuilder sb = new StringBuilder();
	        for (String k : keyArray) {
	            if (k.equals("sign")) {
	                continue;
	            }
	            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
	                sb.append(k).append("=").append(data.get(k).trim()).append("&");
	        }
	        if (key != null) {
	        	sb.append("key=").append(key);
	        }
	        MessageDigest md = null;
	        try {
	            md = MessageDigest.getInstance("MD5");
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        byte[] array = md.digest(sb.toString().getBytes("UTF-8"));

	        StringBuilder sb2 = new StringBuilder();
	        for (byte item : array) {
	            sb2.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
	        }
	        return sb2.toString().toUpperCase();
    	} catch (UnsupportedEncodingException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
	/**
	 * 密码加盐入库
	 * @param phone	取手机号的后四位为盐分
	 * @param password
	 * @return
	 */
	public static String PasswordMd5(String phone, String password) {
		if (phone != null && phone.length() > 7) {
			String salt = phone.substring(7);
			password += salt;
		}
		return Md5Util.md5(password);
	}    

//	public static void main(String[] args) {
//		String md5 = md5("12345678");
//		System.out.println(md5);
//    }
}
