package com.yonyou.aco.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice(basePackages = "com.yonyou.aco")
public class GlobalExceptionHandler {
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public Map<String, Object> errorResult() {
		Map<String, Object> errorResultMap = new HashMap<String, Object>();
		errorResultMap.put("errorCode", "500");
		errorResultMap.put("errorMsg", "系统错误!");
		return errorResultMap;
	}
}