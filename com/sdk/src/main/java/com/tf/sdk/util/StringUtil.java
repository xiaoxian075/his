package com.tf.sdk.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 转为String
 * @ClassName:StringUtil
 * @Description:TODO
 * @author liufeng
 * @date 2018年6月9日 下午3:26:41
 *
 */
public class StringUtil {
	
	/**
	 * 时间戳转为String类型的时间
	 * @param timeStamp
	 * @return
	 */
	public static String timeStampFormatString(long timeStamp) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(timeStamp);
		return sdf.format(date);
		
	}
	
	public static int length(String str) {
		if (str == null) {
			return 0;
		}
		
		try {
			return str.getBytes("utf-8").length;
		} catch (UnsupportedEncodingException e) {
			return 0;
		}
	}
	
	public static String chargLetter(String str) {
		if (str == null || str.trim().length() == 0) {
			return null;
		}
		str = str.trim();
		
		// 验证是否全英文
		if (!str.matches("[a-zA-Z]+")) {
			return null;
		}
		
		str = str.toLowerCase();
		
		return str;
	}
	
	public static boolean checkNotNullAndLength(String str, int maxLen) {
		int len = length(str);
		if (len == 0 || len > maxLen) {
			return false;
		}
		
		return true;
	}
	
	public static boolean checkNotNullAndLength(String str, int minLen, int maxLen) {
		int len = str.length();
		if (len < minLen || len > maxLen) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isBlack(String str) {
		if (str == null) {
			return true;
		}
		
		int len = length(str.trim());
		if (len == 0) {
			return true;
		}
		
		return false;
	}


	/**
	 * 
	 * @param stb 
	 * @param oldStr 被替换的字符串
	 * @param newStr 替换oldString字符串
	 * @return
	 */
	public static StringBuilder replaceAll(StringBuilder stb, String oldStr, String newStr) {
		if (stb == null || oldStr == null || newStr == null || stb.length() == 0 || oldStr.length() == 0)
			return stb;
		int index = stb.indexOf(oldStr);
		if (index > -1 && !oldStr.equals(newStr)) {
			StringBuilder st = new StringBuilder();
			StringBuilder temp = new StringBuilder();
			while (index > -1) {
				//stb暂存到temp中
				temp.setLength(0);
				temp.append(stb);
				// 替换
				stb.replace(index, index + oldStr.length(), newStr);
				st.append(stb.substring(0, index + newStr.length()));
				//赋值还原
				stb.setLength(0);
				stb.append(temp);
				//去掉已替换部分
				stb.delete(0, index+oldStr.length());
				index = stb.indexOf(oldStr);
			}
			return st.append(stb);
		}
		return stb;

	}

	public static String getSerialNumber(int num) {
		//return String.format("%05", num);
		return String.format("%5d", num).replace(" ", "0");
	}
	
	
    /**
     * 具体参数替换模板特殊替换字符
     * @param content	内容
     * @param 所要替换的字符
     * @param args
     * @return
     */
    public final static String assembleModel(String content, String replace, String... args) {
  	  if (args == null || args.length == 0) {
  		  return content;
  	  }
  	  
  	  String msg = content;
  	  for (String arg : args) {
  		  msg = msg.replaceFirst(replace, arg);
  	  }
  	  return msg;
    }	
	
}
