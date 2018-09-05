package com.dognessnetwork.ops.domain;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name="user",uniqueConstraints={@UniqueConstraint(columnNames="username")})
public class User implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8713433498670862907L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Size(max=10)
	private String name;
	
	@Size(min=6,max=16)
	@NotNull
	private String username;
	
	@Size(min=6,max=256)
	@NotNull
	private String password;
	
	@Size(max=11)
	private String mobile;
	private String email;
	
	private boolean enable;
	private Long creatTime;
	
	@ManyToMany(fetch = FetchType.LAZY)
	  @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	   @JoinTable(name = "USER_ROLE", 
	      	joinColumns = { @JoinColumn(name = "USER_ID") },
	        inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<Role> roles;
	
	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Long creatTime) {
		this.creatTime = creatTime;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", mobile="
				+ mobile + ", email=" + email + ", enable=" + enable + ", creatTime=" + creatTime + ", roles=" + roles
				+ "]";
	}
	
	
}
