package com.tf.sdk.change;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageInfo;
import com.tf.sdk.pojo.TFPageInfo;

/**
 * 转换抽象类
 * @author chenjx
 * @Version 1.0.0
 * @time   2018年5月17日
 */
public abstract class AbstractChange<K, V> {
	
	public static <T> TFPageInfo<T> getPageList(List<T> listV) {
	  if (CollectionUtils.isEmpty(listV)) {
	      return new TFPageInfo<>();
	  } else {
	      PageInfo<T> pageInfo = new PageInfo<T>(listV);
	      int page = pageInfo.getPageNum();
	      long total = pageInfo.getTotal();
	      return listV == null ? null : TFPageInfo.createNew(page, total, listV);
	  }
	}
	
	private BaseChange<K, V> base;
	
	protected AbstractChange(BaseChange<K, V> base) {
		this.base = base;
	}

	/**
	 * 单个
	 * @param v
	 * @return
	 */
	public K change(V v) {
		return base.change(v);
	}
	
	/**
	 * 列表
	 * @param listV
	 * @return
	 */
	public List<K> changeList(List<V> listV) {
		if (listV == null) {
			return new ArrayList<K>(0);
		}
		
		int size = listV.size();
		if (size == 0) {
			return new ArrayList<K>(0);
		}
		K pojo = null;
		List<K> listPojo = new ArrayList<K>(size);
		for (V v : listV) {
			pojo = base.change(v);
			if (pojo != null) {
				listPojo.add(pojo);
			}
		}
		return listPojo;
	}
	
	/**
	 * 分页
	 * @param listV
	 * @return
	 */
	public TFPageInfo<K> changePage(List<V> listV) {
		if (listV == null) {
			return TFPageInfo.createNew(0, 0, null);
		}
		PageInfo<V> pageInfo = new PageInfo<>(listV);
		
		int page = pageInfo.getPageNum();
		long total = pageInfo.getTotal();
		List<K> listPojo = changeList(pageInfo.getList());
		if (listPojo == null) {
			return null;
		}
		
		return TFPageInfo.createNew(page, total, listPojo);		
	}
}
