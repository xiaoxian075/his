package com.his.common.mq.elastic;

public class ElasticDefine {
	/**
	 * 拼音五笔查询类型
	 * 1：药品
	 * 2: 项目
	 * 3: 诊断
	 * 4: 患者
	 */
	public final static int PYWB_DATA_DRUG = 1;
	public final static int PYWB_DATA_ITEM = 2;
	public final static int PYWB_DATA_DIAGNOSE = 3;
	public final static int PYWB_DATA_PATIENT= 4;
	public static boolean checkPywbData(int type) {
		if (	type == PYWB_DATA_DRUG
				|| type == PYWB_DATA_ITEM
				|| type == PYWB_DATA_DIAGNOSE
				|| type == PYWB_DATA_PATIENT) {
			return true;
		}
		return false;
	}
	
	/**
	 * 拼音五笔操作类型
	 */
	public final static int PYWB_OPERATOR_TYPE_ADD = 1;
	public final static int PYWB_OPERATOR_TYPE_UPDATE = 2;
	public final static int PYWB_OPERATOR_TYPE_DELETE = 3;
	public static boolean checkPywbOperator(int type) {
		if (type == PYWB_OPERATOR_TYPE_ADD ||
				type == PYWB_OPERATOR_TYPE_UPDATE ||
				type == PYWB_OPERATOR_TYPE_DELETE) {
			return true;
		}
		return false;
	}
}
