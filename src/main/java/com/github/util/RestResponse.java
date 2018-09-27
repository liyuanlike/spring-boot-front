package com.github.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class RestResponse {

	private Integer code;
	private Integer status;
	private String message;
	private String requestId;
	private String description;
	private Object data;

	public static final Integer CODE_OK = 1;
	public static final Integer CODE_ERROR = 0;

	private static final Map<Integer, String> messageMap = new HashMap<Integer, String>() {
		private static final long serialVersionUID = -7326308791851018461L;
		{
			put(0, "请求错误");
			put(1, "请求正确响应");
		}
	};
	
	public RestResponse() {
	}

	public RestResponse(Integer code) {
		this.status = HttpStatus.OK.value();
		this.code = code;
		this.message = messageMap.get(code);
	}

	public RestResponse(Object data) {
		this.status = HttpStatus.OK.value();
		this.data = data;
		this.code = CODE_OK;
		this.message = messageMap.get(CODE_OK);
	}

	public RestResponse(HttpStatus httpStatus, Integer code, Object data, String description) {
		this.status = httpStatus.value();
		this.code = code;
		this.message = messageMap.get(code);
		this.data = data;
		this.description = description;
	}
	public static final RestResponse ok() {
		RestResponse jsonResponse = new RestResponse(CODE_OK);
		return jsonResponse;
	}
	public static final RestResponse ok(Object object) {
		return new RestResponse(object);
	}
	public static final RestResponse error() {
		return new RestResponse(CODE_ERROR);
	}
	public RestResponse put(String key, Object value) {

		if (this.data == null) {
			this.data = new HashMap<>();
		}

		Map<String, Object> dataMap = (Map<String, Object>) data;
		dataMap.put(key, value);

		return this;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	public String toJSONString() {
		return JSON.toJSONString(this, true);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
