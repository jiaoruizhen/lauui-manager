package com.dognessnetwork.ops.dto;

import java.io.Serializable;

import com.dognessnetwork.ops.domain.Dictionary;

public class DictionaryVo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dictionary dictionary;
	private DictionaryVo children;
	
	public Dictionary getDictionary() {
		return dictionary;
	}
	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}
	public DictionaryVo getChildren() {
		return children;
	}
	public void setChildren(DictionaryVo children) {
		this.children = children;
	}
	
}
