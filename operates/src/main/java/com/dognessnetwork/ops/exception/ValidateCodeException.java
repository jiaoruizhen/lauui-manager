package com.dognessnetwork.ops.exception;

import org.springframework.security.core.AuthenticationException;

import com.dognessnetwork.ops.dto.APIStatus;


/**
 * @Author: HanLong
 * @Date: Create in 2018/3/21 21:11
 * @Description:
 */
public class ValidateCodeException extends AuthenticationException {

	/**
	 * 
	 */
	private Integer code = null;
	
    public ValidateCodeException(String msg) {
        super(msg);
    }
    
    public ValidateCodeException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public ValidateCodeException(APIStatus s) {
        super(s.getMessage());
        this.code = s.getStatus();
    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
    
    
}
