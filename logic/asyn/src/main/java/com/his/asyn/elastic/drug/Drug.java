package com.his.asyn.elastic.drug;

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
@Document(indexName = "drug")
public class Drug implements Serializable {
	private static final long serialVersionUID = 4710551864197926741L;
	
	public final static String CLINIC_ID = "clinicId";
	public final static String PINYING = "pinying";

	@Id
	private long id;
	
	@Field(type=FieldType.Text)
	private String pinying;
	
	@Field(type=FieldType.Text)
	private String wubi;
	
	@Field(type=FieldType.Long)
	private long clinicId;
}
