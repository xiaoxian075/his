package com.his.common.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuBo implements Comparable<MenuBo>,Serializable {
	private static final long serialVersionUID = 6815857656176818046L;
	
	private String pid;
	private String index;
	private String title;
	private boolean icon;
	private String about;
	private String routeLink;
	private int sort;
	private List<MenuBo> children;

	public void addChild(MenuBo vo) {
		if (children == null) {
			children = new ArrayList<MenuBo>();
		}
		if (vo != null) {
			children.add(vo);
		}
	}
	
	@Override
	public int compareTo(MenuBo o) {
		if (this.sort > o.sort) {
			return 1;
		} else if (this.sort < o.sort){
			return -1;
		} else {
			return 0;
		}
	}
}

