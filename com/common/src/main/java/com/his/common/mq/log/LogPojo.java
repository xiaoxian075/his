package com.his.common.mq.log;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogPojo implements Serializable {
	private static final long serialVersionUID = -2631849315928546111L;

	private Long userId;
	private String userName;
	private Long clinicId;
	private String clinicName;
	private Integer menuId;
	private String menuName;
	private Integer modelId;
	private String modelName;
	private String clientIp;
	private Long timestamp;
	private String content;
}

