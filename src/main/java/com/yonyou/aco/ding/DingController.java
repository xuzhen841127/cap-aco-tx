package com.yonyou.aco.ding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dingtalk.api.response.OapiUserGetResponse;
import com.yonyou.aco.common.BaseController;
import com.yonyou.aco.utils.IProtocolUtils;
import com.yonyou.aco.utils.RedisUtil;

import net.sf.json.JSONArray;

/**
 * <p> 概述：钉钉服务集成 Controller类
 * <p> 功能：钉钉服务集成 Controller类
 * <p> 作者：徐真
 * <p> 创建时间：2019年8月22日
 * <p> 类调用特殊情况：无
 */
@Controller
@RequestMapping("/dingController")
public class DingController extends BaseController {
	
	public RedisUtil redisUtil = new RedisUtil();
	
	private IProtocolUtils ipu;

	public DingController() {
		this.ipu = IProtocolUtils.getInstance();
	}

	/**
	 * 跳转到钉钉主页
     * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/toDingHome")
	public ModelAndView toDingHome(HttpServletRequest request) {
		// 跳转到钉钉主页
		ModelAndView mv = new ModelAndView();
		redisUtil.set("myname", "xuzhen");
		mv.addObject("request", request);
		mv.setViewName("/ding/ding-home");
		return mv;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param authCode 用户鉴权code
	 * @return
	 */
	@RequestMapping("findUserByAuthCode")
	@ResponseBody
	public String findUserByAuthCode(@RequestParam(value = "authCode") String authCode) {
		String resultStr = "";

		try {
			OapiUserGetResponse user = DingService.findUserInfo(authCode);
			resultStr = ipu.createHeader(IProtocolUtils.Type.success, JSONArray.fromObject(user).toString());
		} catch (Exception e) {
			resultStr = ipu.createHeader(IProtocolUtils.Type.failed, null);
			e.printStackTrace();
		}

		return resultStr;
	}

	/**
	   *   获取用户工作模块
	 *   
	 * @param userId 用户ID
	 * @return
	 */
	@RequestMapping("findModels")
	@ResponseBody
	public String findModels(@RequestParam(value = "mobile") String mobile) {
		String resultStr = "";

		try {
			// 根据手机号获取用户信息
			//UserEntity userEntity = userSerivce.findUserByMobile(mobile);

			// 获取用户模块列表
			List<DingModel> modelList = new ArrayList<DingModel>();
			DingModel model = new DingModel();
			model.setModId("1001");
			model.setModName("用款计划");
			model.setModUrl("/toPlanHome/" + mobile);
			modelList.add(model);
			model = new DingModel();
			model.setModId("1002");
			model.setModName("拨款计划");
			model.setModUrl("/toRealpayHome/" + mobile);
			modelList.add(model);

			// 获取模块待办数量
			Map<String, Integer> numMap = new HashMap<String, Integer>();
			Integer planNum = planService.findPlanTotal(mobile);
			numMap.put("1001", planNum);
			Integer reaplayNum = realpayService.findRealpayTotal(mobile);
			numMap.put("1002", reaplayNum);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("modelMap", modelList);
			map.put("numMap", numMap);
			map.put("mobile", mobile);

			resultStr = ipu.createHeader(IProtocolUtils.Type.success, JSONArray.fromObject(map).toString());
		} catch (Exception e) {
			resultStr = ipu.createHeader(IProtocolUtils.Type.failed, null);
			e.printStackTrace();
		}

		return resultStr;
	}
}