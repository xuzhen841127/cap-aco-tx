package com.yonyou.aco.utils;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p> 概述：与移动端对接的接口协议头工具
 * <p> 功能：与移动端对接的接口协议头工具
 * <p> 创建时间：2019年8月22日
 * <p> 类调用特殊情况：无
 * 
 * @author 徐真
 */
public class IProtocolUtils {
	private static Logger logger = LoggerFactory.getLogger(IProtocolUtils.class);

	/**
	 * 版本
	 */
	private String version;

	/**
	 * 压缩方式
	 */
	private String compress;

	/**
	 * 加密方式
	 */
	private String encrypt;

	/**
	 * 编码格式
	 */
	private String encode;

	/**
	 * 调用结果
	 */
	private String result;

	/**
	 * 返回结果
	 */
	private String items;

	private IProtocolUtils() {
		version = "0.0.1";
		compress = "0";
		encrypt = "0";
		encode = "UTF-8";
	}

	private static class IProtocolUtilsHolder {
		private static IProtocolUtils iProtocolUtils = new IProtocolUtils();
	}

	public static IProtocolUtils getInstance() {
		return IProtocolUtilsHolder.iProtocolUtils;
	}

	/**
	 * 返回接口头部,以JSON形式展示。<br>
	 * version:版本,<br>
	 * compress:压缩方式,<br>
	 * encrypt:加密方式,<br>
	 * encode:编码格式,<br>
	 * result:成功/失败,<br>
	 * items:结果/结果集<br>
	 * 
	 * @param result Type类型<br>
	 * @param items  结果/结果集<br>
	 * @return
	 */
	public String createHeader(Type result, String items) {
		JSONObject json = new JSONObject();
		try {
			json.put("version", version);
			json.put("compress", compress);
			json.put("encrypt", encrypt);
			json.put("encode", encode);
			json.put("result", result.getValue());
			json.put("items", items);
		} catch (JSONException e) {
			logger.error(e.getMessage());
			return null;
		}
		return json.toString();
	}

	public String createHeader(Type result, String items, Map<String, Object> params) {
		JSONObject json = new JSONObject();

		try {
			json.put("version", version);
			json.put("compress", compress);
			json.put("encrypt", encrypt);
			json.put("encode", encode);
			json.put("result", result.getValue());
			json.put("items", items);
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				json.put(entry.getKey(), entry.getValue());
			}
		} catch (JSONException e) {
			logger.error(e.getMessage());
			return null;
		}

		return json.toString();
	}

	/**
	 * 从request中解析JSON对象，并根据键名称返回结果值
	 * 
	 * @param request
	 * @param key     键名称
	 * @return 结果值
	 */
	public String getParam(HttpServletRequest request, String key) {
		String paramJson = request.getParameter("json");
		String param = "";

		try {
			JSONObject jsonObject = new JSONObject(paramJson);
			if (jsonObject.has(key)) {
				param = jsonObject.getString(key);
				param = URLDecoder.decode(param, encode);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

		return param;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getVersion() {
		return version;
	}

	public String getCompress() {
		return compress;
	}

	public String getEncrypt() {
		return encrypt;
	}

	public String getEncode() {
		return encode;
	}

	public static enum Type {
		/**
		 * 0:成功
		 */
		success(0),
		/**
		 * 1:失败
		 */
		failed(1),
		/**
		 * 2:报错
		 */
		error(2);

		/**
		 * 默认值
		 */
		private int defaultValue = 99;

		/**
		 * 调用接口执行结果的类型:0代表成功,1代表失败,2代表出错
		 * 
		 * @param value
		 */
		private Type(int value) {
			this.defaultValue = value;
		}

		public int getValue() {
			try {
				return this.defaultValue;
			} catch (Exception e) {
				logger.error(e.getMessage());
				return 2;
			}
		}
	}
}