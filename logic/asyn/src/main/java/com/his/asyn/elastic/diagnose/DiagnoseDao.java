package com.his.asyn.elastic.diagnose;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnoseDao extends ElasticsearchRepository<Diagnose, Long>{
	
}

