package com.yonyou.aco.ding;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.request.OapiUserListbypageRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse.Department;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse.Userlist;
import com.taobao.api.ApiException;
import com.yonyou.aco.utils.PlatformConfigUtil;

/**
 * <p> 概述：钉钉服务接口 Service类
 * <p> 功能：钉钉服务接口 Service类
 * <p> 作者：徐真
 * <p> 创建时间：2019年8月22日
 * <p> 类调用特殊情况：无
 */
public class DingService {
	private final static Logger logger = LoggerFactory.getLogger(DingService.class);

	/**
	 * 获取token值
     * 
	 * @return token值
	 */
	public static String getToken() {
		OapiGettokenResponse response = null;
		OapiGettokenRequest request = null;

		try {
			DefaultDingTalkClient client = new DefaultDingTalkClient(PlatformConfigUtil.getString("SERVER_URL") + "/gettoken");
			request = new OapiGettokenRequest();
			request.setAppkey(PlatformConfigUtil.getString("APP_KEY"));
			request.setAppsecret(PlatformConfigUtil.getString("APP_SECRET"));
			request.setHttpMethod("GET");
			response = (OapiGettokenResponse) client.execute(request);

			logger.info("【token值获取成功】" + response.getAccessToken());
		} catch (ApiException e) {
			logger.info("【token值获取失败】" + e.getErrMsg());
		}

		return response.getAccessToken();
	}

	/**
	 * 获取用户基础信息
	 *   
	 * @param requestAuthCode 鉴权Code
	 * @return
	 */
	public static OapiUserGetResponse findUserInfo(String requestAuthCode) {
		OapiUserGetResponse response = null;

		try {
			String accessToken = DingService.getToken();
			DingTalkClient client = new DefaultDingTalkClient(PlatformConfigUtil.getString("SERVER_URL") + "/user/getuserinfo");
			OapiUserGetuserinfoRequest userInfoRequest = new OapiUserGetuserinfoRequest();
			userInfoRequest.setCode(requestAuthCode);
			userInfoRequest.setHttpMethod("GET");
			OapiUserGetuserinfoResponse userInfoResponse = client.execute(userInfoRequest, accessToken);

			client = new DefaultDingTalkClient(PlatformConfigUtil.getString("SERVER_URL") + "/user/get");
			OapiUserGetRequest userRequest = new OapiUserGetRequest();
			userRequest.setUserid(userInfoResponse.getUserid());
			userRequest.setHttpMethod("GET");
			response = client.execute(userRequest, accessToken);

			logger.info("【获取钉钉用户信息成功】" + response.getName() + "-" + response.getMobile() + "-" + response.getDingId());
		} catch (ApiException e) {
			logger.info("【获取钉钉用户信息失败】" + e.getErrCode()+ "-" + e.getErrMsg());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 获取用户UserId
	 * 
	 * @param requestAuthCode
	 * @return
	 */
	public static String getUserIdByAuthCode(String requestAuthCode) {
		String userId = "";

		try {
			String accessToken = DingService.getToken();
			DingTalkClient client = new DefaultDingTalkClient(PlatformConfigUtil.getString("SERVER_URL") + "/user/getuserinfo");
			OapiUserGetuserinfoRequest userInfoRequest = new OapiUserGetuserinfoRequest();
			userInfoRequest.setCode(requestAuthCode);
			userInfoRequest.setHttpMethod("GET");
			OapiUserGetuserinfoResponse userInfoResponse = client.execute(userInfoRequest, accessToken);
			userId =  userInfoResponse.getUserid();

			logger.info("【获取钉钉用户ID成功】" + userInfoResponse.getUserid());
		} catch (ApiException e) {
			logger.info("【获取钉钉用户ID成功】" + e.getErrMsg());
		}

		return userId;
	}

	/**
	 * 根据部门ID获取部门用户
	 * 
	 * @param deptId 部门ID
	 * 
	 * @return List<Userlist>
	 */
	public static List<Userlist> findUsersByDeptId(long deptId) {
		List<Userlist> userList = new ArrayList<Userlist>();

		try {
			DingTalkClient client = new DefaultDingTalkClient(PlatformConfigUtil.getString("SERVER_URL") + "/user/listbypage");
			OapiUserListbypageRequest request = new OapiUserListbypageRequest();
			request.setDepartmentId(deptId);
			request.setOffset(0L);
			request.setSize(100L);
			request.setOrder("entry_desc");
			request.setHttpMethod("GET");

			String accessToken = DingService.getToken();
			OapiUserListbypageResponse response = client.execute(request,accessToken);

			userList = response.getUserlist();
		} catch (ApiException e) {
			e.printStackTrace();
		}

		return userList;
	}

	/**
	 * 获取所有部门
	 * 
	 * @return List<Department>
	 */
	public static List<Department>  findAllDepts() {
		List<Department> departmentList = new ArrayList<Department>();

		try {
			DingTalkClient client = new DefaultDingTalkClient(PlatformConfigUtil.getString("SERVER_URL") + "/department/list");
			OapiDepartmentListRequest request = new OapiDepartmentListRequest();
			request.setHttpMethod("GET");

			String accessToken = DingService.getToken();
			OapiDepartmentListResponse response = client.execute(request, accessToken);

			departmentList = response.getDepartment();
		} catch (ApiException e) {
			e.printStackTrace();
		}

		return departmentList;
	}

	/**
	 * 发送待办通知
	 * 
	 * @param title
	 * @param text
	 * @param url
	 */
	public static OapiMessageCorpconversationAsyncsendV2Response sendNotice(String userIds, String title, String text, String url) {
		OapiMessageCorpconversationAsyncsendV2Response response = null;

		try {
			DingTalkClient client = new DefaultDingTalkClient(PlatformConfigUtil.getString("SERVER_URL") + "/topapi/message/corpconversation/asyncsend_v2");
			OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
			request.setUseridList(userIds);
			request.setAgentId(Long.valueOf(PlatformConfigUtil.getString("AGENT_ID")));
			request.setToAllUser(false);

			OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
			msg.setMsgtype("link");
			msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
			msg.getLink().setTitle(title);
			msg.getLink().setText(text);
			msg.getLink().setMessageUrl(url);
			msg.getLink().setPicUrl("test");
			request.setMsg(msg);

			String accessToken = DingService.getToken();
			response = client.execute(request,accessToken);
		} catch (ApiException e) {
			logger.info("系统错误: " + e.getErrCode() + "-" +e.getErrMsg());
			e.printStackTrace();
		}

		return response;
	}
}