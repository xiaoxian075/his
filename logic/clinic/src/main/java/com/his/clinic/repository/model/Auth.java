package com.his.clinic.repository.model;

public class Auth {
	private Long id;
	private Long menuId;
	private String authName;
	private Integer def;
	private Integer state;
	private String desc;
	
	private String uris;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName == null ? null : authName.trim();
	}
	public String getUris() {
		return uris;
	}
	public void setUris(String uris) {
		this.uris = uris == null ? null : uris.trim();
	}
	public Integer getDef() {
		return def;
	}
	public void setDef(Integer def) {
		this.def = def;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc == null ? null : desc.trim();
	}
	
}
