<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
	<head>
		<title>待办主页</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui, viewport-fit=cover">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="stylesheet" href="${ctx }/framework7/css/framework7.bundle.min.css">
		<link rel="stylesheet" href="${ctx }/framework7/css/framework7-icons.css">
		<link rel="stylesheet" href="${ctx }/framework7/css/ding-home.css">
		<script type="text/javascript">
			var CTX = '${ctx}';
		</script>
	</head>
	<body>
		<!-- app 根节点 -->
		<div id="app">
			<!-- 主视图 -->
			<div class="view view-main" data-master-detail-breakpoint="800" data-name="home">
				<div class="page no-navbar cached" data-name="dingHomePage">
					<div class="page-content">
						<div id="dingList" class="list media-list"></div>
					</div>
				</div>
			</div>
		</div>

		<script src="${ctx }/framework7/js/framework7.bundle.min.js"></script>
		<script src="${ctx }/framework7/js/utils/inobounce.min.js"></script>
		<script src="${ctx }/framework7/js/utils/localforage.min.js"></script>
		<script src="//g.alicdn.com/dingding/dingtalk-jsapi/2.6.41/dingtalk.open.js"></script>
		<script type="text/javascript">
			var $$ = Dom7;

			var app = new Framework7({
				id : 'HomeApp',
				root : '#app',
				theme : 'auto', 			// 主题自动装配，根据设备类型分为IOS、安卓、桌面
				stackPages : true,
				input : {
					scrollIntoViewOnFocus : true,
					scrollIntoViewCentered : true,
				},
				dialog : {
					title : '提示', 			// 设置dialog默认标题
					buttonOk : '确定', 		// 设置dialog默认确定按钮
					buttonCancel : '取消' 	// 设置dialog默认取消按钮
				},
				view : {
					mdSwipeBack : true, 	// 安卓允许右滑返回上一页
					iosSwipeBack : true, 	// IOS允许右滑返回上一页
					auroraSwipeBack : true, // 其他设备允许右滑返回上一页
					stackPages : true,
					pushState : true, 		// 开启 url 路由 hash 变化
					pushStateRoot : location.href.split(location.host)[1].split('#!')[0]
				},
				routes : [
					{
						name: 'realpayHomePage',
						path: '/toRealpayHome/:mobile',
						componentUrl: CTX + '/realpayController/toRealpayHome?mobile={{mobile}}',
						on : {
							pageBeforeIn : function(event, page) {
								app.preloader.show();
							},
							pageAfterIn : function(event, page) {
								app.preloader.hide();
							}
						}
					},
					{
						name: 'realpayDetailPage',
						path: '/toRealpayDetail/:realpayId/:mobile',
						componentUrl: CTX + '/realpayController/toRealpayDetail?realpayId={{realpayId}}&mobile={{mobile}}',
						on : {
							pageBeforeIn : function(event, page) {
								app.preloader.show();
							},
							pageAfterIn : function(event, page) {
								app.preloader.hide();
							}
						}
					},
					{
						name: 'realpaySignPage',
						path: '/toRealpaySign/:realpayId/:signType/:mobile',
						componentUrl: CTX + '/realpayController/toRealpaySign?realpayId={{realpayId}}&signType={{signType}}&mobile={{mobile}}',
						on : {
							pageBeforeIn : function(event, page) {
								app.preloader.show();
							},
							pageAfterIn : function(event, page) {
								app.preloader.hide();
							}
						}
					},
					{
						name: 'planHomePage',
						path: '/toPlanHome/:mobile',
						componentUrl: CTX + '/planController/toPlanHome?mobile={{mobile}}',
						on : {
							pageBeforeIn : function(event, page) {
								app.preloader.show();
							},
							pageAfterIn : function(event, page) {
								app.preloader.hide();
							}
						}
					},
					{
						name: 'planDetailPage',
						path: '/toPlanDetail/:planId/:mobile',
						componentUrl: CTX + '/planController/toPlanDetail?planId={{planId}}&mobile={{mobile}}',
						on : {
							pageBeforeIn : function(event, page) {
								app.preloader.show();
							},
							pageAfterIn : function(event, page) {
								app.preloader.hide();
							}
						}
					},
					{
						name: 'planSignPage',
						path: '/toPlanSign/:planId/:signType/:mobile',
						componentUrl: CTX + '/planController/toPlanSign?planId={{planId}}&signType={{signType}}&mobile={{mobile}}',
						on : {
							pageBeforeIn : function(event, page) {
								app.preloader.show();
							},
							pageAfterIn : function(event, page) {
								app.preloader.hide();
							}
						}
					},
					{
					    path: '(.*)',
					    url: CTX + '/framework7/404.html',
					}
				],
				methods: {
					// 获取登录鉴权Code
				    getAuthCode: function () {
				    	var self = this;
						// 通过该免登授权码获取用户身份
				    	/*dd.runtime.permission.requestAuthCode({
					        corpId: '${corpId }',
					        onSuccess: function (info) {
					        	if (info.code == null || info.code == '') {
					        		app.toast.show({ text : '登录失败，请联系系统管理员', position: 'center', closeTimeout : 3000 });
					        	} else {
					        		//app.toast.show({ text : '++++++++++++++++++++++++++++++++'+info.code, position: 'center', closeTimeout : 6000 });
					        		self.methods.getUserInfo(info.code);
					        	}
					  		}
						});*/

						self.methods.loadModelList('13806715255');
				    },
				    // 获取当前登录用户信息
				    getUserInfo: function(authCode) {
				    	var self = this;

				    	app.request({
							url : CTX + '/dingController/findUserByAuthCode',
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
										self.mobile = items[0].mobile;
										//app.toast.show({ text : '++++++++++++++++++++++++++++++++'+self.mobile, position: 'center', closeTimeout : 10000 });
										// 加载用户模块
										self.methods.loadModelList(self.mobile);
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
							url : CTX + '/dingController/findModels',
							data: {
								mobile: mobile
							},
							dataType: 'json',
							method: 'post',
							async: false,
							timeout: 5000,
							success: function(data, status, xhr) {
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
										var modNumId = 'modelId_' + model.modId;

										li += '<li>' +
									          	'<a href="'+ model.modUrl + '" class="item-link item-content">' +
													'<div class="item-inner ">' +
														'<div class="item-title-row">' +
															'<div class="item-title">' +
																'<span class="badge color-blue" style="margin-bottom: 3px;border-radius: 0px;">'+ (j + 1) +'</span>&nbsp;' + model.modName + 
															'</div>' +
															'<div class="item-after">' +
																'<span class="badge color-red" style="line-height: normal;" id="'+ modNumId +'">'+ todoNum +'</span>' +
															'</div>' +
														'</div>' +
													'</div>' +
												'</a>' +
									      	  '</li>';
									}
				    	 	li += '</ul>' +
								 '</div>';
							$$('#dingList').append(li);
				    	}
				    },
					on: {
						pageInit: function(page) {
							var self = this;

						    // 获取用户登录鉴权
						    if (page.name == 'dingHomePage') {
						    	self.methods.getAuthCode();
						    }
						}
					}
			});

			view = app.views.create('.view-main');
			var viewType = '${viewType}';
			if (viewType == 'realpayView') {
				view.router.navigate({
					name: 'realpayDetailPage',
					params: {
						realpayId: '${realpayId}', 
						mobile: '${mobile}',
					}
				});
			} else if (viewType == 'planView') {
				view.router.navigate({
					name: 'planDetailPage',
					params: {
						planId: '${planId}', 
						mobile: '${mobile}',
					}
				});
			}

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