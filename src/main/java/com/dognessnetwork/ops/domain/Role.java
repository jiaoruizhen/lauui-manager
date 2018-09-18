package com.dognessnetwork.ops.domain;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="role")
public class Role implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8713433498670862907L;
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//权限名称
	@NotNull
	private String name;
	@NotNull
	private String detail;
	private Boolean enable;
	private Integer roleIdentify;
	@ManyToMany
	@OrderBy("id ASC")
	private Set<Resource> resources;
	@ManyToMany(mappedBy="roles")
	private Set<User>users;
	@Transient
	private Boolean checked = false;
	
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public Set<Resource> getResources() {
		return resources;
	}
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
	@JsonIgnore
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Integer getRoleIdentify() {
		return roleIdentify;
	}
	public void setRoleIdentify(Integer roleIdentify) {
		this.roleIdentify = roleIdentify;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
