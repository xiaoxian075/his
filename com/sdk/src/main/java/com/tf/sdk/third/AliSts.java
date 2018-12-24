//package com.tf.sdk.third;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.http.MethodType;
//import com.aliyuncs.http.ProtocolType;
//import com.aliyuncs.profile.DefaultProfile;
//import com.aliyuncs.profile.IClientProfile;
//import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
//import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
//import com.tf.sdk.pojo.OssStsInfo;
//
//
//
///**
// * OSS操作对象
// * @author chenjx
// * @Version 1.1.0
// * @time   2018年6月5日
// */
//public class AliSts {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//	
//	private AliSts() {}
//	private static class AliStsFactory {
//	    private static AliSts instance = new AliSts();
//	}
//	public static AliSts getInstance() {
//	    return AliStsFactory.instance;
//	}
//	
//	private String bucket;
//	private String region;
//	private long durationSeconds;
//	private DefaultAcsClient client;
//	private AssumeRoleRequest request;
//
//	public boolean init(String bucket, String region, String accessKeyId, String accessKeySecret, String roleArn, long durationSeconds, String roleSessionName) {
//		this.bucket = bucket;
//		this.region = region;
//		this.durationSeconds = durationSeconds/2;
//		try {
//			DefaultProfile.addEndpoint("", "", "Sts", "sts.aliyuncs.com");
//	        IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
//	        client = new DefaultAcsClient(profile);
//	        request = new AssumeRoleRequest();
//	        request.setMethod(MethodType.POST);
//	        request.setRoleArn(roleArn);
//	        request.setRoleSessionName(roleSessionName);
//	        request.setPolicy(null);
//	        request.setProtocol(ProtocolType.HTTPS);
//	        request.setDurationSeconds(durationSeconds);
//	        return true;
//		} catch (ClientException e) {
//			logger.error("inti sts fail, Error code: " + e.getErrCode() + ", Error message: " + e.getErrMsg());
//			return false;
//        }
//	}
//	
//	/**
//	 * 获取STS临时令牌
//	 * @return
//	 */
//	public OssStsInfo generatorStsInfo() {
//		try {
//			final AssumeRoleResponse response = client.getAcsResponse(request);
//
//	    	return new OssStsInfo(
//	    			this.region,
//	    			this.bucket,
//	    			response.getCredentials().getAccessKeyId(),
//	    			response.getCredentials().getAccessKeySecret(),
//	    			response.getCredentials().getSecurityToken(),
//	    			this.durationSeconds);
//		} catch (ClientException e) {
//			logger.error("generator sts fail, Error code: " + e.getErrCode() + ", Error message: " + e.getErrMsg());
//			return null;
//        }
//	}
//}
