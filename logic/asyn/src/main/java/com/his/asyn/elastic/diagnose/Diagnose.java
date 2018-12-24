package com.his.asyn.elastic.diagnose;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "diagnose")
public class Diagnose implements Serializable {
	private static final long serialVersionUID = -6631701374676870135L;
	
	public final static String CLINIC_ID = "clinicId";
	public final static String PINYING = "pinying";

	@Id
	private long id;
	
	@Field(type=FieldType.Text/*, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word"*/)
	private String pinying;
	
	@Field(type=FieldType.Text)
	private String wubi;
	
	@Field(type=FieldType.Long)
	private long clinicId;
}
