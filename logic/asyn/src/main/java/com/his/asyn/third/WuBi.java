package com.his.asyn.third;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 五笔转换器
 * @author lil
 */
public class WuBi {

	private static Properties wb86;
	
	public WuBi(String fileName) {
		wb86 = WuBiDictionary.load(fileName);
	}

	/**
	 * 字符转化为五笔(86),无法转化返回null
	 *
	 * @param c
	 * @return
	 */
	public String[] charToWubi(char c) {
		if (c < 0x4E00 || c > 0x9FA5) {
			// GBK字库在unicode中的起始和结束位置
			return null;
		}
		String result = wb86.getProperty(Integer.toHexString(c).toUpperCase());
		if (result == null) {
			return null;
		}
		if (result.contains(",")) {
			return result.split(",");
		} else {
			return new String[] { result };
		}
	}

	/**
	 * 字符转化为五笔(86)首字母,无法转化返回“”
	 *
	 * @param c
	 * @return
	 */
	public String charToWubiSingle(char c) {
		String[] wubiArr =charToWubi(c);
		if(wubiArr!=null && wubiArr.length>0){
			return wubiArr[0].substring(0,1);
		}
		return String.valueOf(c);
	}

	public String getWubiSingle(String statement){
		StringBuffer sb = new StringBuffer();
			char[] chs = statement.toCharArray();
			for(int i=0;i<chs.length;i++){
				sb.append(charToWubiSingle(chs[i]));
			}
			String result = sb.toString();
			
			if (StringUtils.isBlank(result)) {
				return null;
			}
			
			return result;
	}
}


