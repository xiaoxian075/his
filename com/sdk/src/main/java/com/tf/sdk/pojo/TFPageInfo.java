package com.tf.sdk.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 分页
 * @author chenjx
 * @Version 1.0.0
 * @time   2018年5月16日
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TFPageInfo<T> implements Serializable {
	private static final long serialVersionUID = -702736129782062259L;
	
	public static <T> TFPageInfo<T> createNew(int page, long total, List<T> listData) {
		return new TFPageInfo<T>(page, total, listData);
	}
	
	private int page;
	private long total;
	private List<T> listData;
}
