package com.tf.sdk.util;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 *
 * @Description:参数校验
 * @author lil
 * @date 2018年6月9日 下午3:26:41
 *
 */
public class ParamCheckUtil {

	/**
	 * 判断字符串是否是long类型
	 *   eg：checkIsLong("123")==true;
	 *      checkIsLong("a12")==false;
	 * @param value
	 * @return
	 */
	public static boolean checkIsLong(String value){
		if(value==null){
			return false;
		}
		try {
			/*long v = */Long.parseLong(value);
			return true;
		}catch (Exception e){
			return false;
		}
	}

    /**
     * 判断是否大于0
     * @param value
     * @return
     */
    public static boolean grater0(Object value){
        if(value==null){
            return false;
        }
        if(value instanceof Long){
            return (Long)value>0;
        }else if(value instanceof Integer){
            return (Integer)value>0;
        }else if(value instanceof Short){
            return (Short)value>0;
        }else if(value instanceof BigDecimal){
            return ((BigDecimal) value).compareTo(BigDecimal.ZERO)>0;
        }else{
            return false;
        }
    }

	/**
	 * 判断是否大于0
	 * @param value
	 * @return
	 */
	public static boolean notGrater0(Object value){
		return !grater0(value);
	}

	/**
	 * 数字是否在入参数组中
	 *   eg：checkStrIn(1,1,2)==true;
	 *      checkStrIn(1,2)==false;
	 * @param i
	 * @param arg
	 * @return
	 */
	public static boolean checkIntegerIn(Integer i,Integer ...arg){
		if(arg==null){
			return false;
		}
		return Arrays.asList(arg).contains(i);
	}

	/**
	 * 对象是否在入参数组中
	 *   eg：checkObjectIn(1,1,2)==true;
	 *   	checkObjectIn("1","1","2")==true;
	 * @param i
	 * @param arg
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean checkObjectIn(T i,T ...arg){
		if(arg==null){
			return false;
		}
		return Arrays.asList(arg).contains(i);
	}

	/**
	 * 字符串是否在入参数组中
	 *   eg：checkStrIn("1","1","2")==true;
	 *      checkStrIn("1","2")==false;
	 * @param str
	 * @param arg
	 * @return
	 */
	public static boolean checkStrIn(String str,String ...arg){
		if(arg==null){
			return false;
		}
		return Arrays.asList(arg).contains(str);
	}

	/**
	 * 字符串长度校验
	 *   eg：checkStrLength("asdf",2,4)==true;
	 *      checkStrLength("asdf",null,4)==true;
	 *      checkStrLength("asdf",2,3)==false;
	 * @param str
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean checkStrLength(String str,Integer min, Integer max){
		int len = str==null?0:str.length();
		if(min==null && max==null){
			return false;
		}else if(min==null && max!=null){
			return len<=max;
		}else if(min!=null && max==null){
			return len>=min;
		}else if(min!=null && max!=null){
			return len>=min && len<=max;
		}
		return false;
	}

	/**
	 * 小数校验
	 * eg：checkDecimal(new BigDecimal("123.2321"),"123.221","123.23211",4) = true;
	 *     checkDecimal(new BigDecimal("123.2321"),null,"123.23211",4) = true;
	 *     checkDecimal(new BigDecimal("123.2321"),"123.221",null,4) = true;
	 *
	 * @param in 待校验的数字
	 * @param min 最小值（null则不校验最小值）
	 * @param max  最大值（null则不校验最大值）
	 * @param maxScaleLength 最大小数点位数（null则不校验）
	 * @return
	 */
	public static boolean checkDecimal(BigDecimal in, String min, String max, Integer maxScaleLength){
		try {
			// 验证小数点位数
			if (maxScaleLength != null && in.scale() > maxScaleLength) {
				return false;
			}
			// 验证大小
			if (min == null && max == null) {
				return false;
			} else if (min == null && max != null) {
				BigDecimal maxTemp = new BigDecimal(max);
				return in.compareTo(maxTemp) <= 0;
			} else if (min != null && max == null) {
				BigDecimal minTemp = new BigDecimal(min);
				return in.compareTo(minTemp) >= 0;
			} else if (min != null && max != null) {
				BigDecimal minTemp = new BigDecimal(min);
				BigDecimal maxTemp = new BigDecimal(max);
				return in.compareTo(minTemp) >= 0 && in.compareTo(maxTemp) <= 0;
			}
		}catch (Exception e){
			// 传参错误
			return false;
		}

		return false;
	}

	public static void main(String[] args) {
Short s = -0;
		System.out.println(grater0(s));
//		System.out.println(checkStrLength("asdf",2,4));

//		System.out.println(checkDecimal(new BigDecimal("123.2321"),"123.221","123.23211",4));
	}
}
