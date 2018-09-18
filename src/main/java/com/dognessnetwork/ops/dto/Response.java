package com.dognessnetwork.ops.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Response implements Serializable {

	private static final long serialVersionUID = -4522894528257894381L;

	//private final Logger logger = LoggerFactory.getLogger(Response.class);

	private static final ObjectMapper mapper = new ObjectMapper();
	private ResponseHeader header;
	private Object data;

	public Response() {
		super();
	}
	
	public Response(ResponseHeader header) {
		this(header, null);
	}

	public Response(String message, int status) {
		super();
		this.header = new ResponseHeader(message,status);
	}

	public Response(ResponseHeader header, Object data) {
		super();
		this.header = header;
		this.data = data;
	}

	public ResponseHeader getHeader() {
		return header;
	}

	public Response setHeader(ResponseHeader header) {
		this.header = header;
		return this;
	}

	public Object getData() {
		return data;
	}

	public Response setData(Object data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	* @Description: 将对象转为Map。注意方法名不能为getXxx，否则在将对象进行Json转换时，该get方法也会被默认调用。
	* @param @return
	* @return Map<String,Object>
	* @throws
	*/
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("header", header);
		map.put("data", data);
		return map;
	}

	public static Response SUCCESS(){
		return new Response(new ResponseHeader(APIStatus.SUCCESS));
	}
	
	public static Response SUCCESS(Object data){
		return new Response(new ResponseHeader(APIStatus.SUCCESS)).setData(data);
	}
	
	public static Response ERROR(APIStatus aPIStatus){
		return new Response(new ResponseHeader(aPIStatus));
	}
	
	public static Response ERROR(String message, int status){
		return new Response(new ResponseHeader(message, status));
	}

	public static final Response FAILURE = new Response(new ResponseHeader(APIStatus.FAILURE));

	public static final Response INVALID_PARAMETER = new Response(new ResponseHeader(APIStatus.INVALID_PARAMETER));
	public static final Response DATA_TOOLONG = new Response(new ResponseHeader(APIStatus.DATA_TOOLONG));
	public static final Response INCORRECT_STRING = new Response(new ResponseHeader(APIStatus.INCORRECT_STRING));
	public static final Response INVALID_SIGN = new Response(new ResponseHeader(APIStatus.INVALID_SIGN));
	public static final Response INSERT_DUPLICATE = new Response(new ResponseHeader(APIStatus.INSERT_DUPLICATE));
	public static final Response INTERNAL_SERVER_ERROR = new Response(new ResponseHeader(APIStatus.INTERNAL_SERVER_ERROR));
	
	public static final Response PET_NOT_EXIST = new Response(new ResponseHeader(APIStatus.PET_NOT_EXIST));
	public static final Response PUBLISH_EXIST = new Response(new ResponseHeader(APIStatus.PUBLISH_EXIST));
	public static final Response PUBLISH_NOT_EXIST = new Response(new ResponseHeader(APIStatus.PUBLISH_NOT_EXIST));
	
	public static final Response DEVICE_IS_BIND_USER = new Response(new ResponseHeader(APIStatus.DEVICE_HAS_BINDED_USER));
	public static final Response DEVICE_NOT_BIND_USER = new Response(new ResponseHeader(APIStatus.DEVICE_NOT_BIND_USER));
	public static final Response DEVICE_IS_BIND_PET = new Response(new ResponseHeader(APIStatus.DEVICE_HAS_BINDED_PET));
	public static final Response DEVICE_NOT_BIND_PET = new Response(new ResponseHeader(APIStatus.DEVICE_NOT_BIND_PET));
	
	public static final Response INVALID_TOKEN = new Response(new ResponseHeader(APIStatus.INVALID_TOKEN));	
}
