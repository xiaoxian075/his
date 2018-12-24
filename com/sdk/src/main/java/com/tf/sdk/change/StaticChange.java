package com.tf.sdk.change;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageInfo;
import com.tf.sdk.pojo.TFPageInfo;

public class StaticChange {
	
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
	

    public static <K, V> K change(V v, Class<K> clazz) {
    	K out = null;
        try {
            out = clazz.newInstance();
			BeanUtils.copyProperties(v, out);
		} catch (Exception e) {
			return null;
		}
        return out;
    }
    
    /**
	 * 列表
	 * @param listV
	 * @return
	 */
	public static <K, V> List<K> changeList(List<V> listV, Class<K> clazz) {
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
			pojo = change(v, clazz);
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
	public static <K, V> TFPageInfo<K> changePage(List<V> listV, Class<K> clazz) {
		if (listV == null) {
			return TFPageInfo.createNew(0, 0, null);
		}
		PageInfo<V> pageInfo = new PageInfo<>(listV);
		
		int page = pageInfo.getPageNum();
		long total = pageInfo.getTotal();
		List<K> listPojo = changeList(pageInfo.getList(), clazz);
		if (listPojo == null) {
			return null;
		}
		
		return TFPageInfo.createNew(page, total, listPojo);		
	}
}
