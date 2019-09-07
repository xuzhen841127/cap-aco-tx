<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${request.getContextPath()}/cap-aco-tx" />
<c:set var="basePath" value="${request.getScheme() }://${request.getServerName() }:${request.getServerPort() }${request.getContextPath() }" />
<!DOCTYPE html>
<html>
	<head>
		<title>请假申请</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui, viewport-fit=cover">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="stylesheet" href="${ctx }/pages/framework7/css/framework7.bundle.min.css">
		<link rel="stylesheet" href="${ctx }/pages/framework7/css/framework7-icons.css">
		<link rel="stylesheet" href="${ctx }/pages/ding/css/ding-home.css">
		<script type="text/javascript">
			var CUR_CTX = '${ctx }';
		</script>
	</head>
	<body>
		<!-- app 根节点 -->
		<div id="app">
			<!-- 主视图 -->
			<div class="view view-main view-init" data-master-detail-breakpoint="800" data-name="home">
				<div class="page no-navbar cached">
					<div class="page-content">
						<div class="list media-list"></div>
					</div>
				</div>
			</div>
		</div>

		<script src="${ctx }/pages/framework7/js/framework7.bundle.min.js"></script>
		<script src="//g.alicdn.com/dingding/dingtalk-jsapi/2.6.41/dingtalk.open.js"></script>
		<script type="text/javascript">
			var $$ = Dom7;

			var app = new Framework7({
				id : 'HomeApp',
				root : '#app',
				theme : 'auto',
				methods: {
					// 获取登录鉴权Code
				    getAuthCode: function () {
				    	var self = this;

						// 通过该免登授权码获取用户身份
				    	dd.runtime.permission.requestAuthCode({
					        corpId: 'ding042f53d36f0e8f3235c2f4657eb6378f',
					        onSuccess: function (info) {
					        	self.methods.getUserInfo(info.code);
					  		}
						});
				    },
				    // 获取当前登录用户信息
				    getUserInfo: function(authCode) {
				    	var self = this;

				    	app.request({
							url : CUR_CTX + '/dingController/findUserByAuthCode',
							data: {
								authCode: authCode
							},
							dataType: 'json',
							method: 'post',
							async: false,
							timeout: 5000,
							success: function(data, status, xhr){
								if (data.result == 0) {
									var items = eval('(' + data.items + ')');
									if (items.length > 0) {
										self.user_code = items[0].mobile;

										// 加载用户模块
										self.methods.loadModelList(self.user_code);
									}
								} else {
									notifiSystemError.open();
								}
							},
							error: function (data, status, xhr) {
								// 系统错误、或网络连接异常通知，在mobile-todotask-home.jsp中全局定义
								if (status == 'timeout') {
									notifiNetError.open();
								} else {
									notifiSystemError.open();
								}
							}
						});
				    },
				    // 加载用户模块列表
				    loadModelList: function(mobile) {
				    	var self = this;

				    	Framework7.request({
							url : CUR_CTX + '/dingController/findModels',
							data: {
								mobile: mobile
							},
							dataType: 'json',
							method: 'post',
							async: false,
							timeout: 5000,
							success: function(data, status, xhr){
								if (data.result == 0) {
									var items = eval('(' + data.items + ')');
									if (items.length > 0) {
										// 加载模块列表模板
										self.methods.modelTemplate(items);
									}
								} else {
									notifiSystemError.open();
								}
							},
							error: function (data, status, xhr) {
								// 系统错误、或网络连接异常通知，在mobile-todotask-home.jsp中全局定义
								if (status == 'timeout') {
									notifiNetError.open();
								} else {
									notifiSystemError.open();
								}
							}
						});
				    },
				    // 模块列表模板
				    modelTemplate: function(items) {
				    	var modelList = items[0].modelMap;
				    	var numMap = items[0].numMap;
				    	var mobile = items[0].mobile;

				    	var li = '<div class="list-group">' +
				    			  '<ul>' + 
				    			  	'<li class="list-group-title">待办事项</li>';

				    			  	for (var j = 0; j < modelList.length; j++) {
				    			  		var model = modelList[j];

										var todoNum = numMap[model.modId];
										var isShowTodoNum = (todoNum == undefined || todoNum == '0') ? 'none' : 'block';
										var href = '${ctx }/' + model.modUrl + '?mobile=' + mobile;

										li += '<li>' +
									          	'<a href="'+ href + '" class="item-link item-content external">' +
													'<div class="item-inner ">' +
														'<div class="item-title-row">' +
															'<div class="item-title">' +
																'<span class="badge color-blue" style="margin-bottom: 3px;border-radius: 0px;">'+ (j + 1) +'</span>&nbsp;' + model.modName + 
															'</div>' +
															'<div class="item-after" style="display: '+ isShowTodoNum +'">' +
																'<span class="badge color-red" style="line-height: normal;">'+ todoNum +'</span>' +
															'</div>' +
														'</div>' +
													'</div>' +
												'</a>' +
									      	  '</li>';
									}

				    	 	li += '</ul>' +
								 '</div>';
							$$('.media-list').append(li);
				    	}
				    },
					on: {
						pageInit: function() {
							var self = this;
	
						    // 获取用户登录鉴权
						   	//self.methods.getAuthCode();
						   	self.methods.loadModelList('13957369911');
						}
					}
			});
			
			// 定义全局网络异常消息
			var notifiNetError = app.notification.create({
				title : '网络异常',
				text : '网络连接异常，请检查网络',
				closeTimeout : 4000,
				icon : '<i class="f7-icons" style="color: red;">alert</i>',
			});
			
			// 定义全局系统异常消息
			var notifiSystemError = app.notification.create({
				title : '系统错误',
				text : '系统错误，请联系系统管理员',
				closeTimeout : 4000,
				icon : '<i class="f7-icons" style="color: red;">alert</i>',
			});
		</script>
	</body>
</html>