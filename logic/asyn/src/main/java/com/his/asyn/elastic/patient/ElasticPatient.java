package com.his.asyn.elastic.patient;

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
public class ElasticPatient implements IElastic {
	@Autowired
	private PatientDao patientDao;

	@Override
	public List<Long> search(int page, int size, String word, long clinicId) {
		Pageable pageable = PageRequest.of(page, size);
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if(clinicId>=0){
			queryBuilder.must(QueryBuilders.termQuery(Patient.CLINIC_ID,clinicId));
		}
		BoolQueryBuilder likeBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.wildcardQuery(Patient.PINYING,"*"+word+"*"));
		queryBuilder.must(likeBuilder);
		SearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable).build();
		Page<Patient> pages = patientDao.search(query);
		List<Patient> listPatient = pages.getContent();
		if (listPatient == null) {
			return null;
		}
		List<Long> idList = new ArrayList<Long>();
		for (Patient patient : listPatient) {
			idList.add(patient.getId());
		}
		return idList;		
	}
	
	public PywuVo get(long id) {
		Optional<Patient> optional = patientDao.findById(id);
		Patient patient = optional.get();
		if (patient == null) {
			return null;
		}
		return new PywuVo(patient.getId(), patient.getPinying(), patient.getWubi(), patient.getClinicId());
	}
	

	/**
	 * 批量增加
	 */
	@Override
	public boolean addList(List<PywuVo> listObj) {
		List<Patient> listPatient = new ArrayList<Patient>();
		for (PywuVo vo : listObj) {
			listPatient.add(new Patient(vo.getId(), vo.getPinyin(), vo.getWubi(), vo.getExtra()));
		}
		
		Iterable<Patient> iterable = patientDao.saveAll(listPatient);
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
		Optional<Patient> optional = patientDao.findById(id);
		if (optional.isPresent()) {
			return false;
		}
		
		Patient patient = new Patient(id, obj.getPinyin(), obj.getWubi(), obj.getExtra());
		Patient _patient = patientDao.save(patient);
		if (_patient == null) {
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
		Optional<Patient> optional = patientDao.findById(id);
		if (!optional.isPresent()) {
			return false;
		}
		
		Patient patient = new Patient(id, obj.getPinyin(), obj.getWubi(), obj.getExtra());
		Patient _patient = patientDao.save(patient);
		if (_patient == null) {
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

		Patient patient = new Patient(obj.getId(), obj.getPinyin(), obj.getWubi(), obj.getExtra());
		Patient _patient = patientDao.save(patient);
		if (_patient == null) {
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
		Optional<Patient> optional = patientDao.findById(id);
		if (!optional.isPresent()) {
			return null;
		}
		Patient patient = optional.get();
		
		patientDao.deleteById(id);
		return new PywuVo(patient.getId(), patient.getPinying(), patient.getWubi(), patient.getClinicId());
	}
	
	/**
	 * 删除全部
	 * @return
	 */
	@Override
	public boolean deleteAll() {
		patientDao.deleteAll();
		return true;
	}
	
	
	@Override
	public List<PywuVo> testSearch(int page, int size, String word, long clinicId) {
		Pageable pageable = PageRequest.of(page, size);
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if(clinicId>=0){
			queryBuilder.must(QueryBuilders.termQuery(Patient.CLINIC_ID,clinicId));
		}
		BoolQueryBuilder likeBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.wildcardQuery(Patient.PINYING,"*"+word+"*"));
		queryBuilder.must(likeBuilder);
		SearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable).build();
		Page<Patient> pages = patientDao.search(query);
		List<Patient> listPatient = pages.getContent();
		if (listPatient == null) {
			return null;
		}
		List<PywuVo> voList = new ArrayList<PywuVo>();
		for (Patient patient : listPatient) {
			voList.add(new PywuVo(patient.getId(), patient.getPinying(), patient.getWubi(), patient.getClinicId()));
		}
		return voList;
	}
}
