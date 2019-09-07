<%@ page contentType="text/html;charset=UTF-8"%>
<template>
	<div class="page no-navbar cached">
		<form class="searchbar searchbar-init">
			<div class="searchbar-inner">
				<div class="searchbar-input-wrap">
					<input id="title" type="search" style="font-size: 14px;" placeholder="请输入标题关键字..." />
					<i class="searchbar-icon"></i> <span class="input-clear-button"></span>
				</div>
				<span style="font-size: 17px; flex-shrink: 0; padding-left: 5px;padding-right: 5px;">
					<a id="searchOptA" href="#" @click="searchTodoTask" class="link" style="color: #007aff;">搜索</a>
				</span>
				<span class="searchbar-disable-button if-not-aurora">取消</span>
			</div>
		</form>

		<div class="page-content ptr-content infinite-scroll-content" data-ptr-mousewheel="true" @ptr:refresh="pulldownRefresh" @infinite="pullupRefresh">
			<!-- 下拉刷新 -->
			<div class="ptr-preloader">
				<div class="preloader"></div>
				<div class="ptr-arrow"></div>
			</div>

			<!-- 列表 -->
			<div class="list media-list">
				<ul id="realpayList"></ul>
			</div>

			<!-- 上拉加载更多 -->
			<div class="preloader infinite-scroll-preloader"></div>
			<div class="emptyDiv" style="padding: 15px;display: none;text-align: center;">没有更多数据了</div>
		</div>
	</div>
</template>

<script>
	// framework 页面回调
	return {
		data : function() {
			return {
				CUR_PAGE_INDEX : 1,		// 当前页码
				CUR_PAGE_SIZE : 10,		// 每页条数
				CUR_INDEX : 0,			// 列表计数
				ALLOW_INFINITE : true,	// 是否允许上拉
				CUR_TITLE : '',			// 搜索标题
			}
		},
		// 页面初始化完成后，执行一次下拉刷新
		on: {
			pageInit: function() {
				var self = this;
				self.pulldownRefresh();
			}
		},
		methods : {
			// 下拉刷新列表
			pulldownRefresh : function(e, done) {
				var self = this;

				self.title = $$('#title').val();
				self.CUR_PAGE_INDEX = 1;

				setTimeout(function() {
					app.request({
						url : CTX + '/realpayController/findRealpays',
						data: {
							mobile : '${mobile }',
							pageIndex : self.CUR_PAGE_INDEX,
							pageSize : self.CUR_PAGE_SIZE,
							title: self.title
						},
						dataType: 'json',
						method: 'post',
						async: false,
						timeout: 5000,
						success: function(data, status, xhr){
							if (data.result == 0) {
								var items = eval('(' + data.items + ')');
								if (items.length > 0) {
									// 下拉后，页码从1开始
									self.CUR_PAGE_INDEX = 1;

									// 列表计数置0
									self.index = 0;

									// 清空列表 
									$$("#realpayList li").remove();

									// 重新加载列表
									var template = self.planTemplate(items);
									$$('#realpayList').append(template);

									// 如果条数小于每页条数，或者等于0，结束上拉加载
									if (items.length < self.CUR_PAGE_SIZE || items.length == 0) {
										// 销毁无限滚动监听事件
										app.infiniteScroll.destroy('.infinite-scroll-content');
										// 隐藏无限滚动
										$$('.infinite-scroll-preloader').css('display', 'none');
										// 显示没有跟多数据
										$$('.emptyDiv').css('display', 'block');
									} else {
										// 创建无限滚动监听事件
										app.infiniteScroll.create('.infinite-scroll-content');
										// 显示无限滚动
										$$('.infinite-scroll-preloader').css('display', 'block');
										// 隐藏没有跟多数据
										$$('.emptyDiv').css('display', 'none');
									}
								} else {
									// 清空列表 
									$$("#realpayList li").remove();

									// 销毁无限滚动监听事件
									app.infiniteScroll.destroy('.infinite-scroll-content');
									// 隐藏无限滚动
									$$('.infinite-scroll-preloader').css('display', 'none');
									// 显示没有跟多数据
									$$('.emptyDiv').css('display', 'block');
								}
							}

							// 下拉刷新完成
							app.ptr.done();
							app.preloader.hide();
						},
						error: function (data, status, xhr) {
							// 下拉刷新完成
							app.ptr.done();

							// 系统错误、或网络连接异常通知，在mobile-todotask-home.jsp中全局定义
							if (status == 'timeout') {
								notifiNetError.open();
							} else {
								notifiSystemError.open();
							}
						}
					});

					app.searchbar.disable('.searchbar');
					$$('#title').val(self.title);
				}, 1000);
			},
			// 上拉加载更多
			pullupRefresh: function(e, done) {
				var self = this;

				// Exit, if loading in progress
				if (!self.ALLOW_INFINITE) return;

				// Set loading flag
				self.ALLOW_INFINITE = false;

				setTimeout(function() {
					app.request({
						url : CTX + '/realpayController/findRealpays',
						data: {
							mobile : '${mobile }',
							pageIndex : parseInt(self.CUR_PAGE_INDEX) + 1,
							pageSize : self.CUR_PAGE_SIZE,
							title: self.title
						},
						dataType: 'json',
						method: 'post',
						async: false,
						timeout: 5000,
						success: function(data, status, xhr){
							if (data.result == 0) {
								// 成功加载更多后，页码加1
								self.CUR_PAGE_INDEX = parseInt(self.CUR_PAGE_INDEX) + 1;

								var items = eval('(' + data.items + ')');
								if (items.length > 0) {
									// 追加记录到底部
									var template = self.planTemplate(items);
									$$('#realpayList').append(template);

									// 如果条数小于每页条数，或者等于0，结束上拉加载
									if (items.length < self.CUR_PAGE_SIZE || items.length == 0) {
										// 销毁无限滚动监听事件
										app.infiniteScroll.destroy('.infinite-scroll-content');
										// 隐藏无限滚动
										$$('.infinite-scroll-preloader').css('display', 'none');
										// 显示没有跟多数据
										$$('.emptyDiv').css('display', 'block');
									}
								}
							}

							self.ALLOW_INFINITE = true;
						},
						error: function (data, status, xhr) {
							self.ALLOW_INFINITE = true;

							// 系统错误、或网络连接异常通知，在mobile-todotask-home.jsp中全局定义
							if (status == 'timeout') {
								notifiNetError.open();
							} else {
								notifiSystemError.open();
							}
						}
					});
				}, 1000);
			},
			// 用款计划列表模板
			planTemplate : function(items) {
				var self = this;

				var html = '';
				for (var i = 0; i < items.length; i++) {
					self.index = self.index + 1;

					var item = items[i];

					html += '<li id="realpay_' + item.realpayId + '"> '+
                                '<a href="/toRealpayDetail/'+ item.realpayId +'/${mobile}" class="item-link item-content">' +
                                	'<div class="item-inner">' +
                                    	'<div class="item-title-row">' +
	                                        '<div class="item-title">' +
	                                            '<span class="badge color-blue" style="margin-bottom: 3px;border-radius: 0px;">' + self.index + '</span>&nbsp;' +
	                                                item.agencyCode + '&nbsp;' + item.menuName +
											'</div>' +
	                                        //'<div class="item-after">' + item.createDate + '</div>' +
                                        '</div>' +
                                        '<div class="item-subtitle" style="top:5px; color: #8e8e93;">' + item.remark + '</div>' +
                                        '<div class="item-text">' +
                                            '<p style="font-size: 12px;">拟稿时间：' + item.createDate + '</p>' +
                                            //'<p style="font-size: 12px;position: absolute;right: 0px;">耗时：1小时/5天</p>' +
                                        '</div>' +
                             		'</div>' +
                             	'</a>' +
							 '</li>';
				}

				return html;
			},
			searchTodoTask: function() {
				var self = this;
				app.preloader.show();
				self.pulldownRefresh();
			}
		}
	}
</script>