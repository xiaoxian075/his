package com.his.asyn.elastic.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.his.asyn.elastic.IElastic;
import com.his.asyn.elastic.PywuVo;

/**
* drug search
* @author chenjx
* @Version 1.1.1
* @time   2018年10月27日
*/
@Service
public class ElasticItem implements IElastic {
	@Autowired
	private ItemDao itemDao;
	
	@Override
	public List<Long> search(int page, int size, String word, long clinicId) {
		Pageable pageable = PageRequest.of(page, size);
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if(clinicId>=0){
			queryBuilder.must(QueryBuilders.termQuery(Item.CLINIC_ID,clinicId));
		}
		BoolQueryBuilder likeBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.wildcardQuery(Item.PINYING,"*"+word+"*"));
		queryBuilder.must(likeBuilder);
		SearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable).build();
		Page<Item> pages = itemDao.search(query);
		List<Item> listItem = pages.getContent();
		if (listItem == null) {
			return null;
		}
		List<Long> idList = new ArrayList<Long>();
		for (Item item : listItem) {
			idList.add(item.getId());
		}
		return idList;
	}
	
	public PywuVo get(long id) {
		Optional<Item> optional = itemDao.findById(id);
		if (!optional.isPresent()) {
			return null;
		}
		Item item = optional.get();

		return new PywuVo(item.getId(), item.getPinying(), item.getWubi(), item.getClinicId());
	}
	

	/**
	 * 批量增加
	 */
	@Override
	public boolean addList(List<PywuVo> listObj) {
		List<Item> listItem = new ArrayList<Item>();
		for (PywuVo vo : listObj) {
			listItem.add(new Item(vo.getId(), vo.getPinyin(), vo.getWubi(), vo.getExtra()));
		}
		
		Iterable<Item> iterable = itemDao.saveAll(listItem);
		if (iterable == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 增加
	 * @param book
	 * @return
	 */
	@Override
	public boolean add(PywuVo obj) {
		long id = obj.getId();
		Optional<Item> optional = itemDao.findById(id);
		if (optional.isPresent()) {
			return false;
		}

		Item item = new Item(id, obj.getPinyin(), obj.getWubi(), obj.getExtra());
		Item _item = itemDao.save(item);
		if (_item == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 修改
	 * @param book
	 * @return
	 */
	@Override
	public boolean update(PywuVo obj) {
		long id = obj.getId();
		Optional<Item> optional = itemDao.findById(id);
		if (!optional.isPresent()) {
			return false;
		}
		
		Item item = new Item(id, obj.getPinyin(), obj.getWubi(), obj.getExtra());
		Item _item = itemDao.save(item);
		if (_item == null) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 添加或修改
	 * @param book
	 * @return
	 */
	@Override
	public boolean addOrUpdate(PywuVo obj) {

		Item item = new Item(obj.getId(), obj.getPinyin(), obj.getWubi(), obj.getExtra());
		Item _item = itemDao.save(item);
		if (_item == null) {
			return false;
		}
		
		return true;
	}
	
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@Override
	public PywuVo delete(long id) {
		Optional<Item> optional = itemDao.findById(id);
		if (!optional.isPresent()) {
			return null;
		}
		Item item = optional.get();

		itemDao.deleteById(id);
		return new PywuVo(item.getId(), item.getPinying(), item.getWubi(), item.getClinicId());
	}
	
	/**
	 * 删除全部
	 * @return
	 */
	@Override
	public boolean deleteAll() {
		itemDao.deleteAll();
		return true;
	}
	
	@Override
	public List<PywuVo> testSearch(int page, int size, String word, long clinicId) {
		Pageable pageable = PageRequest.of(page, size);
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if(clinicId>=0){
			queryBuilder.must(QueryBuilders.termQuery(Item.CLINIC_ID,clinicId));
		}
		BoolQueryBuilder likeBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.wildcardQuery(Item.PINYING,"*"+word+"*"));
		queryBuilder.must(likeBuilder);
		SearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable).build();
		Page<Item> pages = itemDao.search(query);
		List<Item> listItem = pages.getContent();
		if (listItem == null) {
			return null;
		}
		List<PywuVo> voList = new ArrayList<PywuVo>();
		for (Item item : listItem) {
			voList.add(new PywuVo(item.getId(), item.getPinying(), item.getWubi(), item.getClinicId()));
		}
		return voList;
	}
}
