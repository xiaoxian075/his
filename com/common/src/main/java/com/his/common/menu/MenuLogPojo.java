package com.his.common.menu;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuLogPojo implements Serializable {
	private static final long serialVersionUID = -4693094285593409577L;
	
	private int menuId;
	private String menuName;
	private int modelId;
	private String modelName;
}
