package com.his.common.mq.elastic;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElasticNode implements Serializable {
	private static final long serialVersionUID = -8073266152086690482L;
	private long id;
	private long clinicId;
	private String word;
}
