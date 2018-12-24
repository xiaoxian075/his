package com.his.asyn.third;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 聚合支付实现
 * 接口调用demo： https://www.juhe.cn/docs/api/id/54
 * 接口调用注意事项：https://www.juhe.cn/news/index/id/50
 * @author LiLiang
 */
public class JuHeChit {
	private static final Logger logger = LoggerFactory.getLogger(JuHeChit.class);

    private String jhUrl;

    //配置您申请的KEY
    private String appKey;
    
    public JuHeChit(String jhUrl, String appKey) {
    	this.jhUrl = jhUrl;
    	this.appKey = appKey;
    }

//    public static void init(String _jhUrl, String _appKey) {
//    	jhUrl = _jhUrl;
//    	appKey = _appKey;
//    }
   
	private final static String DEF_CHARSET = "utf-8";
	/**
	 * 连接超时 单位毫秒
	 */
	private final static int DEF_CONN_TIMEOUT = 10000;
	/**
	 * 读取超时 单位毫秒
	 */
	private final static int DEF_READ_TIMEOUT = 10000;

    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";


    //1.屏蔽词检查测
    public void getRequest1(){
        String result =null;
        //String url = jhUrl;//请求接口地址
        Map<String, Object> params = new HashMap<String, Object>();//请求参数
            params.put("word","");//需要检测的短信内容，需要UTF8 URLENCODE
            params.put("key",appKey);//应用APPKEY(应用详细页查询)
 
        try {
            result =net(jhUrl, params, "GET");
            JSONObject object = JSONObject.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //2.发送短信
    public boolean sendMsg(String phone,String tplId,Map<String,String> tplParams){
        String result =null;
        String url = jhUrl;//请求接口地址

        Map<String, Object> params = new HashMap<String, Object>(5);//请求参数
        params.put("mobile",phone);//接收短信的手机号码
        params.put("tpl_id",tplId);//短信模板ID，请参考个人中心短信模板设置
        params.put("tpl_value",tplParams);//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
        params.put("key",appKey);//应用APPKEY(应用详细页查询)
        params.put("dtype","");//返回数据的格式,xml或json，默认json
 
        try {
            result =net(url, params, "POST");
            logger.info("****** juHe sms return ******："+result);
            JSONObject object = JSONObject.parseObject(result);
            if(object.getInteger("error_code")==0){
                logger.info(object.get("result").toString());
                return true;
            }else{
                logger.info(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return false;
    }
 
         
         
//    public static void main(String[] args) {
//        JuHeChitImpl juHeChit = new JuHeChitImpl("http://v.juhe.cn/sms/send","f22eb630b15ff281c5e8983b57fd724d");
//        juHeChit.sendRegister("15006009569","666888");
//    }
 
      /**
      *
      * @param strUrl 请求地址
      * @param params 请求参数
      * @param method 请求方法
      * @return  网络请求字符串
      * @throws Exception
      */
    public String net(String strUrl, Map<String, Object> params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                        out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHARSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
 
    //将map型转为请求参数型
    @SuppressWarnings("unchecked")
	private static String urlencode(Map<String,Object>data) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, Object> i : data.entrySet()) {
        try {
            String value;
            if(i.getValue() instanceof Map){
                Map<String,Object> tplParams = (Map<String, Object>) i.getValue();
                value = urlencode(tplParams);
            }else {
                value = i.getValue()+"";
            }
            sb.append(i.getKey()).append("=").append(URLEncoder.encode(value,"UTF-8")).append("&");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
        return sb.toString();
    }
}


