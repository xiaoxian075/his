package com.his.asyn.elastic.drug;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
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
public class ElasticDrug implements IElastic {
	@Autowired
	private DrugDao drugDao;
	
	@Override
	public List<Long> search(int page, int size, String word, long clinicId) {
		Pageable pageable = PageRequest.of(page, size);
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if(clinicId>=0){
			queryBuilder.must(QueryBuilders.termQuery(Drug.CLINIC_ID,clinicId));
		}
		BoolQueryBuilder likeBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.wildcardQuery(Drug.PINYING,"*"+word+"*"));
		queryBuilder.must(likeBuilder);
		SearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable).build();
		Page<Drug> pages = drugDao.search(query);
		List<Drug> listDrug = pages.getContent();
		if (listDrug == null) {
			return null;
		}
		List<Long> idList = new ArrayList<Long>();
		for (Drug drug : listDrug) {
			idList.add(drug.getId());
		}
		return idList;
	}
	
	public PywuVo get(long id) {
		Optional<Drug> optional = drugDao.findById(id);
		if (!optional.isPresent()) {
			return null;
		}
		Drug drug = optional.get();
		
		return new PywuVo(drug.getId(), drug.getPinying(), drug.getWubi(), drug.getClinicId());
	}
	

	/**
	 * 批量增加
	 */
	@Override
	public boolean addList(List<PywuVo> listObj) {
		List<Drug> listDrug = new ArrayList<Drug>();
		for (PywuVo vo : listObj) {
			listDrug.add(new Drug(vo.getId(), vo.getPinyin(), vo.getWubi(), vo.getExtra()));
		}
		
		Iterable<Drug> iterable = drugDao.saveAll(listDrug);
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
		Optional<Drug> optional = drugDao.findById(id);
		if (optional.isPresent()) {
			return false;
		}

		Drug drug = new Drug(id, obj.getPinyin(), obj.getWubi(), obj.getExtra());
		Drug _drug = drugDao.save(drug);
		if (_drug == null) {
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
		Optional<Drug> optional = drugDao.findById(id);
		if (!optional.isPresent()) {
			return false;
		}
		
		Drug drug = new Drug(id, obj.getPinyin(), obj.getWubi(), obj.getExtra());
		Drug _drug = drugDao.save(drug);
		if (_drug == null) {
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

		Drug drug = new Drug(obj.getId(), obj.getPinyin(), obj.getWubi(), obj.getExtra());
		Drug _drug = drugDao.save(drug);
		if (_drug == null) {
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
		Optional<Drug> optional = drugDao.findById(id);
		if (!optional.isPresent()) {
			return null;
		}
		Drug drug = optional.get();

		drugDao.deleteById(id);
		return new PywuVo(drug.getId(), drug.getPinying(), drug.getWubi(), drug.getClinicId());
	}
	
	/**
	 * 删除全部
	 * @return
	 */
	@Override
	public boolean deleteAll() {
		drugDao.deleteAll();
		return true;
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 查询:全文检索（根据整个实体的所有属性，可能结果为0个）
	 * @param q
	 * @return
	 */
	public List<Drug> allSearch(String q) {
		QueryStringQueryBuilder builder = new QueryStringQueryBuilder(q);
		Iterable<Drug> searchResult = drugDao.search(builder);
		Iterator<Drug> iterator = searchResult.iterator();
		List<Drug> listDrug = new ArrayList<Drug>();
		while (iterator.hasNext()) {
			listDrug.add(iterator.next());
		}
		return listDrug;
	}
	
//	/**
//	 * 查询：分页、分数、分域（结果一个也不少）
//	 * @param page
//	 * @param size
//	 * @param q
//	 * @return 
//	 * @return
//	 */
//	public List<Drug> searchCity(int page, int size, String q) {
//
//		// 分页参数
//		Pageable pageable = new PageRequest(page, size);
//
//		// 分数，并自动按分排序
//		FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
//				.add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("name", q)),
//						ScoreFunctionBuilders.weightFactorFunction(1000)) // 权重：name 1000分
//				.add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("message", q)),
//						ScoreFunctionBuilders.weightFactorFunction(100)); // 权重：message 100分
//
//		// 分数、分页
//		SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable)
//				.withQuery(functionScoreQueryBuilder).build();
//
//		Page<Drug> searchPageResults = drugDao.search(searchQuery);
//		List<Drug> listDrug = searchPageResults.getContent();
//		if (listDrug == null) {
//			return null;
//		}
//		return listDrug;
//	}
	
	
	@Override
	public List<PywuVo> testSearch(int page, int size, String word, long clinicId) {
		Pageable pageable = PageRequest.of(page, size);
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if(clinicId>=0){
			queryBuilder.must(QueryBuilders.termQuery(Drug.CLINIC_ID,clinicId));
		}
		BoolQueryBuilder likeBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.wildcardQuery(Drug.PINYING,"*"+word+"*"));
		queryBuilder.must(likeBuilder);
		SearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable).build();
		Page<Drug> pages = drugDao.search(query);
		List<Drug> listDrug = pages.getContent();
		if (listDrug == null) {
			return null;
		}
		List<PywuVo> voList = new ArrayList<PywuVo>();
		for (Drug drug : listDrug) {
			voList.add(new PywuVo(drug.getId(), drug.getPinying(), drug.getWubi(), drug.getClinicId()));
		}
		return voList;
	}
}
