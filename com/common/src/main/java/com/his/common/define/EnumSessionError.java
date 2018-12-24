package com.his.common.define;

public enum EnumSessionError {
	
	EXCEPTION_ERROR(10001, "异常"), 	
	UNPACK_ERROR(10002, "解包错误"), 
	PARAM_ERROR(10003, "参数错误"), 
	POWER_ERROR(10004, "权限错误"), 
	SAVE_REDIS_ERROR(10005, "缓存错误"), 
	NO_DATA_ERROR(10006, "无数据"),
	TYPE_NOT_FIND_ERROR(10007, "无此类型"),
	UNPACK_JSON_ERROR(10008, "解包JSON格式错误"),
	NO_MESSAGE_ERROR(10009, "错误"),
	ACCOUNT_NOT_EXIST_ERROR(10010, "账号不存在"),
	ACCOUNT_OR_PWD_ERROR(10011, "账号或密码有误"),
	GET_POWER_ERROR(10012, "获取权限错误"),
	SAVE_POWER_ERROR(10013, "保存权限错误"),
	ACCOUNT_INFO_ERROR(10014, "账号信息错误"),
	
	NET_PARSE_ERROR(10100, "解析网络数据包错误"), 
	NET_CLINT_IP_ERROR(10101, "网络客户端IP验证错误"), 
	NET_DATA_LENGTH_ERROR(10102, "网络数据包长度错误"),
	NET_DATA_JSON_ERROR(10103, "网络数据包JSON格式错误"),
	NET_LOGIC_DATA_LENGTH_ERROR(10104, "网络数据包业务数据包长度错误"), 
	NET_LOGIC_DATA_JSON_ERROR(10105, "网络数据包业务数据包JSON格式错误"), 
	NET_PARSE_PARAM_ERROR(10106, "网络数据包验证参数错误"), 
	NET_ACCOUNT_LOGIN_ERROR(10107, "未登入"), 
	NET_ACCOUNT_AUTH_ERROR(10108, "账号权限错误"), 
	NET_CLINIC_ENTER_ERROR(10109, "账号没有进入诊所"),
	NET_CLINIC_AUTH_ERROR(10110, "账号诊所权限验证错误"), 
	NET_URI_ERROR(10111, "网络获取URI错误"), 
	
	EASTIC_ADD_ERROR(10201, "添加EASTIC错误"), 
	EASTIC_UPDATE_ERROR(10202, "更新EASTIC错误"),
	EASTIC_ADDORUPDATE_ERROR(10203, "添加或更新EASTIC错误"),
	EASTIC_DELETE_ERROR(10204, "删除EASTIC错误"),
	EASTIC_DELETEALL_ERROR(10205, "全部删除EASTIC错误"),
	EASTIC_ADDLIST_ERROR(10211, "添加EASTIC列表错误"), 
	PINYIN_CHANGE_ERROR(10231, "拼音转换错误"), 
	
	DB_INSERT_ERROR(10301, "记录插入表失败")
	

	
	
	;
	
	private int code;
	private String desc;
	private EnumSessionError(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
