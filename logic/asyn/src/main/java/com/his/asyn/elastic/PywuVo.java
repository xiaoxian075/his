package com.his.asyn.elastic;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PywuVo implements Serializable {
	private static final long serialVersionUID = -463315759521782926L;
	
	private long id;
	private String pinyin;
	private String wubi;
	private long extra;
	
	public PywuVo(long id, String pinyin, String wubi) {
		this.id = id;
		this.pinyin = pinyin;
		this.wubi = wubi;
		this.extra = 0;
	}
}
