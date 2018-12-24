package com.tf.sdk.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * STS临时授权信息
 * @author chenjx
 * @Version 1.1.0
 * @time   2018年6月6日
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OssStsInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String region;
	private String bucket;
	private String accessKeyId;
	private String accessKeySecret;
	private String securityToken;
	private long durationSeconds;
}

