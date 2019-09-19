<%@ page contentType="text/html;charset=UTF-8"%>
<template>
	<!-- 文字签批页 -->
	<div class="page no-navbar cached" data-name="planSignPage">
		<div class="page-content">
			<!-- 文字签批区域 -->
			<div id="wordSignDiv">
				<div class="block-title" style="height: 30px; margin: 10px; color: #949494; border-bottom: 1px dashed #F00; font-size: 16px;">文字签批：
					<span style="float: right;">
						<a id="shortcutsOptA" href="#" data-actions=".shortcuts-actions" class="link actions-open">常用语</a>
					</span>
				</div>
				<textarea id="wordSignTextArea" @focusout="inputFocusOut" name="wordSignTextArea" style="padding-left: 10px; padding-right: 10px;" class="resizable" placeholder="签批意见"></textarea>
			</div>
		</div>

		<!-- 确认签批按钮 -->
		<div class="toolbar tabbar tabbar-labels toolbar-bottom">
			<div class="toolbar-inner">
				<a href="#" @click="sendPlan" class="tab-link tab-link-active">
					<i class="f7-icons">check</i>
					<span class="tabbar-label">确认签批</span>
				</a>
			</div>
		</div>

		<!-- 快捷语菜单 -->
		<div class="actions-modal shortcuts-actions">
			<div class="actions-group">
				<div class="actions-label">常用语</div>
				<div class="actions-button" @click="shortcuts('同意')">
					<div class="actions-button-text actions-close">同意</div>
				</div>
				<div class="actions-button" @click="shortcuts('不同意')">
					<div class="actions-button-text actions-close">不同意</div>
				</div>
				<div class="actions-button" @click="shortcuts('单据有误请核实')">
					<div class="actions-button-text actions-close">单据有误请核实</div>
				</div>
			</div>

			<div class="actions-group">
				<div class="actions-button">
					<div class="actions-button-text actions-close">取消</div>
				</div>
			</div>
		</div>
	</div>
</template>

<style>
	.list-button {
		line-height: normal;
		height: 40px;
		padding-top: 15px;
	}
</style>

<script>
	// framework 页面回调
	return {
		data : function() {
			return {
				WORD_KEY : '${signType}_${realpayId}',
				SING_TYPE : '${signType}'
			}
		},
		methods : {
			// 初始化手写签批、文字签批区域高度
			initSignature: function() {
				var self = this;

				// 计算并设置手写签批、文字签批区域高度
				var screenHeight = $$(window).height() - 50;
				var screenWidth = $$(window).width();

				$$('#wordSignDiv').css('height', parseInt(screenHeight -31) + 'px');
				$$('#wordSignTextArea').css('min-height', parseInt(screenHeight - 80) + 'px');
				$$('#wordSignTextArea').css('height', parseInt(screenHeight) + 'px');
				$$('#wordSignTextArea').css('width', parseInt(screenWidth) + 'px');

				// 初始化组件时，从本地获取缓存文字签批意见
				localforage.getItem(self.WORD_KEY, function(err, value) {
					// 初始化文字签批
					if (value != null) {
						$$('#wordSignTextArea').val(value);
					} else {
						if (self.SING_TYPE == 'agree') {
							$$('#wordSignTextArea').val('同意');
						} else {
							$$('#wordSignTextArea').val('驳回');
						}
					}
				});
			},
			// 快捷语切换
			shortcuts: function(value) {
				$$('#wordSignTextArea').val(value);
			},
			// 输入框失去焦点			
			inputFocusOut: function(e) {
				window.scrollTo(0,0);
			},
			// 送交用户计划
			sendPlan: function(e) {
				var self = this;

				var suggestion = $$('#wordSignTextArea').val();
				app.dialog.confirm('确定送交吗？', '提示', function() {
					app.request({
						url : CTX + '/realpayController/doSendRealpay',
						data: {
							realpayId: '${realpayId}', 
							mobile: '${mobile}',
							suggestion: suggestion
						},
						dataType: 'json',
						method: 'post',
						async: false,
						timeout: 5000,
						success: function(data, status, xhr){
							if (data.result == 0) {
								var items = eval('(' + data.items + ')');
								if (items.length > 0) {
									if (items[0]) {
										// 移除列表记录
										$$('#realpay_${realpayId}').remove();

										// 成功后修改待办数量
										var modelNum = $$('#modelId_1002').text();
										$$('#modelId_1002').text(modelNum - 1);

										// 清空缓存
										localforage.removeItem(self.WORD_KEY);

										// 跳转到列表页
										var url = '/toRealpayHome/${mobile}';
										view.router.back(url, {
											reloadCurrent: false,
											clearPreviousHistory: true,
											force: true
										});

										app.toast.show({ text : '送交成功', position:'center', closeTimeout : 2000 });
									}
								}
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
				});
			}
		},
		on : {
			pageBeforeIn: function() {
				var self = this;

				// 初始化手写组件
				self.initSignature();
			},
			pageAfterOut: function() {
				var self = this;

				// 移除之前存储的旧值
				localforage.removeItem(self.WORD_KEY);

				// 本地存储签批意见
				var wordVal = $$('#wordSignTextArea').val();
				localforage.setItem(self.WORD_KEY, wordVal);
			}
		}
	}
</script>