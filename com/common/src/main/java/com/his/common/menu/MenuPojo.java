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
public class MenuPojo implements Serializable {
	private static final long serialVersionUID = -3553506104001029475L;
	
	private long id;
	private long parentId;
	private String title;
	private String icon;
	private String route;
	private int sort;
}
