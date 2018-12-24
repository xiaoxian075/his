package com.his.asyn.third;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 拼音转换器
 * @author lil
 */
public class PinYin {
	private final Map<String,List<String>> duoYinZiMap;
	
	public PinYin(String fileName) {
		this.duoYinZiMap = PinYinDictionary.load(fileName);
	}

	public String[] getPinyinFull(char ch) {
		try{
			HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
			outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

			if(ch>=32 && ch<=125){	//ASCII >=33 ASCII<=125的直接返回 ,ASCII码表：http://www.asciitable.com/
				return new String[]{String.valueOf(ch)};
			}
			return distinct(PinyinHelper.toHanyuPinyinStringArray(ch, outputFormat));
		} catch (BadHanyuPinyinOutputFormatCombination e) {
//			throw new Exception(e);
		}
        return null;
	}

	public String getPinyinFull(String chinese){
		if(StringUtils.isEmpty(chinese)){
			return null;
		}
		chinese = chinese.replaceAll("[\\.，\\,！·\\!？\\?；\\;\\(\\)（）\\[\\]\\:： ]+", " ").trim();

		StringBuilder py_sb = new StringBuilder(32);
		char[] chs = chinese.toCharArray();
		for(int i=0;i<chs.length;i++){
			getPinyinByChar(chinese, py_sb, chs[i], i, false);
		}
		
		return py_sb.toString();
	}

	public String getPinyinSingle(String chinese) {
		if(StringUtils.isEmpty(chinese)){
			return null;
		}

//		chinese = chinese.replaceAll("[\\.，\\,！·\\!？\\?；\\;\\(\\)（）\\[\\]\\:： ]+", " ").trim();

		StringBuilder py_sb = new StringBuilder(32);
		char[] chs = chinese.toCharArray();
		for(int i=0;i<chs.length;i++){
			getPinyinByChar(chinese, py_sb, chs[i], i, true);
		}

		String result = py_sb.toString();
		
		if (StringUtils.isBlank(result)) {
			return null;
		}
		
		return result;
	}

	private void getPinyinByChar(String chinese, StringBuilder py_sb, char ch, int i,boolean singlePinyin) {
		try{
            String[] py_arr = getPinyinFull(ch);
            if(py_arr==null || py_arr.length<1){
                throw new Exception("pinyinwubi array is empty, char:"+ ch +",chinese:"+chinese);
            }
            if(py_arr.length==1){
                py_sb.append(convertInitialToUpperCase(py_arr[0], singlePinyin));
            }else if(py_arr.length==2 && py_arr[0].equals(py_arr[1])){
                py_sb.append(convertInitialToUpperCase(py_arr[0], singlePinyin));
            }else{
                String resultPy = null, defaultPy = null;;
                for (String py : py_arr) {
                    String left = null;	//向左多取一个字,例如 银[行]
                    if(i>=1 && i+1<=chinese.length()){
                        left = chinese.substring(i-1,i+1);
                        if(duoYinZiMap.containsKey(py) && duoYinZiMap.get(py).contains(left)){
                            resultPy = py;
                            break;
                        }
                    }

                    String right = null;	//向右多取一个字,例如 [长]沙
                    if(i<=chinese.length()-2){
                        right = chinese.substring(i,i+2);
                        if(duoYinZiMap.containsKey(py) && duoYinZiMap.get(py).contains(right)){
                            resultPy = py;
                            break;
                        }
                    }

                    String middle = null;	//左右各多取一个字,例如 龙[爪]槐
                    if(i>=1 && i+2<=chinese.length()){
                        middle = chinese.substring(i-1,i+2);
                        if(duoYinZiMap.containsKey(py) && duoYinZiMap.get(py).contains(middle)){
                            resultPy = py;
                            break;
                        }
                    }
                    String left3 = null;	//向左多取2个字,如 芈月[传],列车长
                    if(i>=2 && i+1<=chinese.length()){
                        left3 = chinese.substring(i-2,i+1);
                        if(duoYinZiMap.containsKey(py) && duoYinZiMap.get(py).contains(left3)){
                            resultPy = py;
                            break;
                        }
                    }

                    String right3 = null;	//向右多取2个字,如 [长]孙无忌
                    if(i<=chinese.length()-3){
                        right3 = chinese.substring(i,i+3);
                        if(duoYinZiMap.containsKey(py) && duoYinZiMap.get(py).contains(right3)){
                            resultPy = py;
                            break;
                        }
                    }

                    if(duoYinZiMap.containsKey(py) && duoYinZiMap.get(py).contains(String.valueOf(ch))){	//默认拼音
                        defaultPy = py;
                    }
                }

                if(StringUtils.isEmpty(resultPy)){
                    if(StringUtils.isNotEmpty(defaultPy)){
                        resultPy = defaultPy;
                    }else{
                        resultPy = py_arr[0];
                    }
                }
                py_sb.append(convertInitialToUpperCase(resultPy,singlePinyin));
            }
        }catch (Exception e){

        }

	}

	private String convertInitialToUpperCase(String str, boolean singlePinyin) {
		if (str == null || str.length()==0) {
			return "";
		}
		if(singlePinyin){
			return str.substring(0, 1);
		}
		return str.substring(0, 1).toUpperCase()+str.substring(1);
	}

    public static String[] distinct(String[] arr) {
        if(arr==null || arr.length==0) {
            return arr;
        }
        HashSet<String> set = new HashSet<>();
        for (String str : arr) {
            set.add(str);
        }
        return set.toArray(new String[0]);
    }
}


