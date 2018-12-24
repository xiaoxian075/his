package com.his.asyn.elastic.drug;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugDao extends ElasticsearchRepository<Drug, Long>{

}