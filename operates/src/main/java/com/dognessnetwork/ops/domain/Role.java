package com.dognessnetwork.ops.domain;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="role")
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8713433498670862907L;
	@Id
	@GeneratedValue
	private Long id;
	//权限名称
	@NotNull
	private String name;
	
	@NotNull
	private String detail;
	
	private boolean enable;
	
	private Integer roleIdentify;
	
	@ManyToMany(fetch = FetchType.LAZY)
	  @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	   @JoinTable(name = "ROLE_RESOURCE", 
	      	joinColumns = { @JoinColumn(name = "ROLE_ID") },
	        inverseJoinColumns = { @JoinColumn(name = "RESOURCE_ID") })
	private Set<Resource> resources;
	
	
	@ManyToMany(mappedBy="roles",cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
	private Set<User>users;
	
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
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
}
