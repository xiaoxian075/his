package com.his.common.menu;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthPojo implements Serializable {
	private static final long serialVersionUID = 6130124677055337148L;
	
	private long id;
	private long menuId;
	private List<String> listUri;
}
