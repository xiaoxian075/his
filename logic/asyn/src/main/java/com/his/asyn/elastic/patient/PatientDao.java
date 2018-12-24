package com.his.asyn.elastic.patient;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDao extends ElasticsearchRepository<Patient, Long>{

}