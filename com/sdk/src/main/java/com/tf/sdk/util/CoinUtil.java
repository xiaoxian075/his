package com.tf.sdk.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;

public class CoinUtil {

	public static final BigDecimal ONE = new BigDecimal("1.00");
	
	
	private final static BigDecimal MAX_AMOUNT = new BigDecimal("100000.00");
	private static final BigDecimal HUNDRED = new BigDecimal(100);

	public static int toInt(BigDecimal amount) {
		if (amount == null) {
			return 0;
		}
		try {
			BigInteger bigAmount = amount.multiply(HUNDRED).setScale(0, BigDecimal.ROUND_HALF_UP).toBigInteger();
			return bigAmount.intValue();
		} catch (Exception e) {
			return 0;
		}
	}
    
	public static int toInt(String amount) {
		if (StringUtils.isBlank(amount)) {
			return 0;
		}
		try {
			BigDecimal dAmount = new BigDecimal(amount);
			BigInteger bigAmount = dAmount.multiply(HUNDRED).setScale(0, BigDecimal.ROUND_HALF_UP).toBigInteger();
			return bigAmount.intValue();
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static int toIntOri(String amount) {
		if (StringUtils.isBlank(amount)) {
			return 0;
		}
		try {
			BigDecimal dAmount = new BigDecimal(amount);
			BigInteger bigAmount = dAmount.setScale(0, BigDecimal.ROUND_HALF_UP).toBigInteger();
			return bigAmount.intValue();
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static BigDecimal toBigDecimal(int amount) {
		try {
	    	BigDecimal bRefundAmount = new BigDecimal(amount);
	    	return bRefundAmount.divide(HUNDRED);
		} catch (Exception e) {
			return null;
		}
    }
	
	public static String toStr(int amount) {
		try {
	    	BigDecimal bRefundAmount = new BigDecimal(amount);
	    	return bRefundAmount.divide(HUNDRED).toString();
		} catch (Exception e) {
			return null;
		}
    }
	
	

	public static boolean checkAmount(BigDecimal amount) {
		if (amount == null) {
			return false;
		}
		if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(MAX_AMOUNT) > 0) {
			return false;
		}
		
		return true;
	}

}
