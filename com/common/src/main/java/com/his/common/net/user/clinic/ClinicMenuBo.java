package com.his.common.net.user.clinic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClinicMenuBo implements Comparable<ClinicMenuBo>,Serializable {
	private static final long serialVersionUID = 6815857656176818046L;
	
	private String pid;
	private String index;
	private String title;
	private boolean icon;
	private String about;
	private String routeLink;
	private int sort;
	private List<ClinicMenuBo> children;

	public void addChild(ClinicMenuBo vo) {
		if (children == null) {
			children = new ArrayList<ClinicMenuBo>();
		}
		if (vo != null) {
			children.add(vo);
		}
	}
	
	@Override
	public int compareTo(ClinicMenuBo o) {
		if (this.sort > o.sort) {
			return 1;
		} else if (this.sort < o.sort){
			return -1;
		} else {
			return 0;
		}
	}
}

