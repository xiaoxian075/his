package com.his.common.net;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NetBo implements Serializable {
	private static final long serialVersionUID = -581459893900408868L;

	private int encrypt;
	private String token;
	private long timestamp;
	private String sign;
	private String data;
}
