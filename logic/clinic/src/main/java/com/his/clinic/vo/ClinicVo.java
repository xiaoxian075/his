package com.his.clinic.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClinicVo implements Serializable {
	private static final long serialVersionUID = 7407481467028997599L;
	
	private long clinicId;
	private String clinicName;
//	private long roleId;
//	private long deptId;
//	private String logoImage;
//	private int limitUserNum;
//	private long endLineTime;
//	private long createUser;
//	private long updateUser;
//	// 是否是超级账号
//	private boolean isAdmin;
//	// 类型：1:正式 2:试用
//	private int type;
//	// 状态：1:正常 2:停用 3:删除
//	private int state;
}
