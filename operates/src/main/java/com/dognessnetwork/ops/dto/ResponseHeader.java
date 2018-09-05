package com.dognessnetwork.ops.dto;

import java.io.Serializable;

public class ResponseHeader implements Serializable {
	
	private static final long serialVersionUID = 3090500160279752399L;

	//private final Logger logger = LoggerFactory.getLogger(ResponseHeader.class);
	
	private int status;
	private String message;

	public ResponseHeader(APIStatus status) {
		this.status = status.getStatus();
		this.message = status.getMessage();
	}

	public ResponseHeader(String message, int status) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ResponseHeader [status=" + status + ", message=" + message + "]";
	}
	
}
