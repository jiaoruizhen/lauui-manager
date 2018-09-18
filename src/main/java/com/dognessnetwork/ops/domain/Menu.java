package com.dognessnetwork.ops.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_menu")
public class Menu extends BaseEntity{
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
