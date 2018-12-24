package com.his.asyn.elastic.diagnose;

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
public class ElasticDiagnose implements IElastic {
	@Autowired
	private DiagnoseDao diagnoseDao;

	@Override
	public List<Long> search(int page, int size, String word, long clinicId) {
		Pageable pageable = PageRequest.of(page, size);
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if(clinicId>=0){
			queryBuilder.must(QueryBuilders.termQuery(Diagnose.CLINIC_ID,clinicId));
		}
		BoolQueryBuilder likeBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.wildcardQuery(Diagnose.PINYING,/*"*"+*/word+"*"));
		queryBuilder.must(likeBuilder);
		SearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable).build();
		Page<Diagnose> pages = diagnoseDao.search(query);
		List<Diagnose> listDiagnose = pages.getContent();		
		if (listDiagnose == null) {
			return null;
		}
		List<Long> idList = new ArrayList<Long>();
		for (Diagnose diagnose : listDiagnose) {
			idList.add(diagnose.getId());
		}
		return idList;
	}
	
	public PywuVo get(long id) {
		Optional<Diagnose> optional = diagnoseDao.findById(id);
		if (!optional.isPresent()) {
			return null;
		}
		Diagnose diagnose = optional.get();
		return new PywuVo(diagnose.getId(), diagnose.getPinying(), diagnose.getWubi(), diagnose.getClinicId());
	}
	

	/**
	 * 批量增加
	 */
	@Override
	public boolean addList(List<PywuVo> listObj) {
		List<Diagnose> listItem = new ArrayList<Diagnose>();
		for (PywuVo vo : listObj) {
			listItem.add(new Diagnose(vo.getId(), vo.getPinyin(), vo.getWubi(), vo.getExtra()));
		}
	
		Iterable<Diagnose> iterable = diagnoseDao.saveAll(listItem);
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
		Optional<Diagnose> optional = diagnoseDao.findById(id);
		if (optional.isPresent()) {
			return false;
		}

		Diagnose diagnose = new Diagnose(id, obj.getPinyin(), obj.getWubi(), obj.getExtra());
		Diagnose _diagnose = diagnoseDao.save(diagnose);
		if (_diagnose == null) {
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
		Optional<Diagnose> optional = diagnoseDao.findById(id);
		if (!optional.isPresent()) {
			return false;
		}
		
		Diagnose diagnose = new Diagnose(id, obj.getPinyin(), obj.getWubi(), obj.getExtra());
		Diagnose _item = diagnoseDao.save(diagnose);
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

		Diagnose diagnose = new Diagnose(obj.getId(), obj.getPinyin(), obj.getWubi(), obj.getExtra());
		Diagnose _diagnose = diagnoseDao.save(diagnose);
		if (_diagnose== null) {
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
		Optional<Diagnose> optional = diagnoseDao.findById(id);
		if (!optional.isPresent()) {
			return null;
		}
		Diagnose diagnose = optional.get();
		
		diagnoseDao.deleteById(id);
		return new PywuVo(diagnose.getId(), diagnose.getPinying(), diagnose.getWubi(), diagnose.getClinicId());
	}
	
	/**
	 * 删除全部
	 * @return
	 */
	@Override
	public boolean deleteAll() {
		diagnoseDao.deleteAll();
		return true;
	}

	@Override
	public List<PywuVo> testSearch(int page, int size, String word, long clinicId) {
		Pageable pageable = PageRequest.of(page, size);
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if(clinicId>=0){
			queryBuilder.must(QueryBuilders.termQuery(Diagnose.CLINIC_ID,clinicId));
		}
		BoolQueryBuilder likeBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.wildcardQuery(Diagnose.PINYING,"*"+word+"*"));
		queryBuilder.must(likeBuilder);
		SearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable).build();
		Page<Diagnose> pages = diagnoseDao.search(query);
		List<Diagnose> listDiagnose = pages.getContent();		
		if (listDiagnose == null) {
			return null;
		}
		List<PywuVo> voList = new ArrayList<PywuVo>();
		for (Diagnose diagnose : listDiagnose) {
			voList.add(new PywuVo(diagnose.getId(), diagnose.getPinying(), diagnose.getWubi(), diagnose.getClinicId()));
		}
		return voList;
	}
}
