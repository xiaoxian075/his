package com.his.asyn.elastic;

import java.util.List;

public interface IElastic {
	PywuVo get(long id);
	boolean addList(List<PywuVo> listObj);
	boolean add(PywuVo obj);
	boolean update(PywuVo obj);
	boolean addOrUpdate(PywuVo obj);
	PywuVo delete(long id);
	boolean deleteAll();

	List<Long> search(int page, int size, String word, long extra);
	
	
	List<PywuVo> testSearch(int page, int size, String word, long extra);
}
