package com.dognessnetwork.ops.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="review_message")
public class ReviewMessage {
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private @ManyToOne SIMCardInfo sIMCardInfo;
	private Integer status;//评审状态：   1、运营经理审核  2、总经办审核    3、已通过   4、未通过

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public SIMCardInfo getsIMCardInfo() {
		return sIMCardInfo;
	}

	public void setsIMCardInfo(SIMCardInfo sIMCardInfo) {
		this.sIMCardInfo = sIMCardInfo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
