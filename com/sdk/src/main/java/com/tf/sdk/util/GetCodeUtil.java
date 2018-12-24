package com.tf.sdk.util;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取编码
 * @ClassName:GetCodeUtil
 * @Description:TODO
 * @author liufeng
 * @date 2018年7月20日 上午9:03:05
 *
 */
public class GetCodeUtil {

	public static String getCode(int intCode) {
		String strCode = null;
		if(intCode<0) {
			strCode = null;
		}else if(intCode <100000) {
			strCode = String.format("%0" + 5 + "d", intCode);
		}else {
			strCode = intCode+"";
		}
		return strCode;
	}

	public static String getStockCode(String currentMaxCode,String prefix) {
		return 	getCommonCode(prefix,"yyMMdd",currentMaxCode,3);
	}

	public static String getRegisterCode(String currentMaxCode) {
		return getCommonCode("AA","yyMMdd",currentMaxCode,2);
	}

	public static String getCommonCode(String prefix,String dateFormat,String currentMaxCode,int numLength){
		String formatDate = "";
		if(!StringUtils.isEmpty(dateFormat)){
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			formatDate = sdf.format(new Date());
		}
		String codeNumStr = null;

		int preCodeNumLength = (prefix+dateFormat).length();
		int numInt;
		if(currentMaxCode!= null && currentMaxCode.length() > preCodeNumLength) {
			String num = currentMaxCode.substring(preCodeNumLength);
			String currentMaxCodeDate = currentMaxCode.substring(prefix.length(),preCodeNumLength);
			if(!StringUtils.isEmpty(currentMaxCodeDate) && currentMaxCodeDate.equals(formatDate)){
				numInt = Integer.parseInt(num)+1;
			}else {
				numInt = 1;
			}
		}else {
			numInt = 1;
		}
		int maxCodeInt = 10;
		for(int i=0;i<numLength;i++){
			maxCodeInt *= 10;
		}
		if(numInt <maxCodeInt) {
			codeNumStr = String.format("%0" + numLength + "d", numInt);
		}else {
			codeNumStr = numInt+"";
		}
		return prefix+formatDate+codeNumStr;
	}

//	public static void main(String[] args) {
//		String currentCode = "TJ180816002";
//		System.out.println(getCommonCode("TJ","yyMMdd",currentCode,3));
//	}
}
