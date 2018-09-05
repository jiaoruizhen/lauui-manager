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
@Table(name="resource")
public class Resource implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8713433498670862907L;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private String name;
	//所属分类
	private String type;
	
	private String detail;
	
	@NotNull
	private String url;
	
	
	@ManyToMany(mappedBy="resources",cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
	@JsonIgnore
	private Set<Role>roles;
	
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
