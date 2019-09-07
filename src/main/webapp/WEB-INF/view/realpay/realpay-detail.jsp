<%@ page contentType="text/html;charset=UTF-8"%>
<template>
	<div class="page no-navbar cached" data-name="todotaskDetailPage">
		<div class="page-content" style="padding-bottom: 30px;">
			<!-- 审批详情基础信息 -->
			<div style="padding: 10px 10px 0px 10px;">
				<div style="margin-left:10px;margin-bottom: 5px;">
					<span style="font-weight: 600;">${realpayEntity.agencyCode }&nbsp;&nbsp;${realpayEntity.remark }</span><br>
					<span style="color: #949494;">${realpayEntity.menuName } / ${realpayEntity.createDate }</span>
				</div>
			</div>

			<!-- 审批详情 -->
			<div class="card data-table data-table-collapsible data-table-init" style="padding-left: 10px;">
				<span style="font-size: 16px;"><i class="f7-icons">sort_down</i>审批详情</span>
				<hr>
				<div class="card-content">
					<div class="row no-gap" style="padding-top: 15px;">
					    <div class="col-30" style="font-weight: 600; font-size: 14px; color: #8e8e93;">单据号:</div>
						<div class="col-70">${realpayEntity.realpayRequestCode }</div>
					</div>
					<div class="row no-gap" style="padding-top: 15px;">
					    <div class="col-30" style="font-weight: 600; font-size: 14px; color: #8e8e93;">申请日期:</div>
						<div class="col-70">${realpayEntity.createDate }</div>
					</div>
					<div class="row no-gap" style="padding-top: 15px;">
					    <div class="col-30" style="font-weight: 600; font-size: 14px; color: #8e8e93;">预算单位:</div>
						<div class="col-70">${realpayEntity.agencyCode }</div>
					</div>
					<div class="row no-gap" style="padding-top: 15px;">
					    <div class="col-30" style="font-weight: 600; font-size: 14px; color: #8e8e93;">项目名称:</div>
						<div class="col-70">${realpayEntity.depProName }</div>
					</div>
					<div class="row no-gap" style="padding-top: 15px;">
					    <div class="col-30" style="font-weight: 600; font-size: 14px; color: #8e8e93;">申请金额:</div>
						<div class="col-70">${realpayEntity.amount }</div>
					</div>
					<div class="row no-gap" style="padding-top: 15px;">
					    <div class="col-30" style="font-weight: 600; font-size: 14px; color: #8e8e93;">收款人账号:</div>
						<div class="col-70">${realpayEntity.payeeAccountNo }</div>
					</div>
					<div class="row no-gap" style="padding-top: 15px;">
					    <div class="col-30" style="font-weight: 600; font-size: 14px; color: #8e8e93;">收款人全称:</div>
						<div class="col-70">${realpayEntity.payeeAccountName }</div>
					</div>
					<div class="row no-gap" style="padding-top: 15px;">
					    <div class="col-30" style="font-weight: 600; font-size: 14px; color: #8e8e93;">收款人开户行:</div>
						<div class="col-70">${realpayEntity.payeeAccountBank }</div>
					</div>
					<div class="row no-gap" style="padding-top: 15px;">
					    <div class="col-30" style="font-weight: 600; font-size: 14px; color: #8e8e93;">指标来源:</div>
						<div class="col-70">${realpayEntity.bgtSourceName }</div>
					</div>
					<div class="row no-gap" style="padding-top: 15px;">
					    <div class="col-30" style="font-weight: 600; font-size: 14px; color: #8e8e93;">资金性质:</div>
						<div class="col-70">${realpayEntity.fundType }</div>
					</div>
					<div class="row no-gap" style="padding-top: 15px;">
					    <div class="col-30" style="font-weight: 600; font-size: 14px; color: #8e8e93;">功能分类:</div>
						<div class="col-70">${realpayEntity.expFunc }</div>
					</div>
					<div class="row no-gap" style="padding-top: 15px;">
					    <div class="col-30" style="font-weight: 600; font-size: 14px; color: #8e8e93;">经济分类:</div>
						<div class="col-70">${realpayEntity.expEco }</div>
					</div>
					<div class="row no-gap" style="padding-top: 15px;">
					    <div class="col-30" style="font-weight: 600; font-size: 14px; color: #8e8e93;">用途:</div>
						<div class="col-70">${realpayEntity.remark }</div>
					</div>
				</div>
			</div>
		</div>

		<div class="toolbar tabbar tabbar-labels toolbar-bottom">
         	<div class="toolbar-inner">
				<a href="/toRealpaySign/${realpayEntity.realpayId }/agree/${mobile}" class="tab-link tab-link-active">
					<i class="f7-icons">check</i>
					<span class="tabbar-label">同意</span>
				</a>
				<a href="/toRealpaySign/${realpayEntity.realpayId }/back/${mobile}" class="tab-link tab-link-active">
					<i class="f7-icons" style="font-size: 24px;">undo</i>
					<span class="tabbar-label">驳回</span>
				</a>
			</div>
		</div>
	</div>
</template>

<style>
	.block-title {
		padding-left: 5px;
		margin-top: 0px;
		margin-bottom: 0px;
		font-weight: var(- -f7-table-head-font-weight);
		font-size: var(- -f7-table-head-font-size);
		color: var(- -f7-table-head-text-color);
	}

	.data-table.data-table-collapsible td:not(.checkbox-cell):before {
		font-size: 14px;
		width: 30%;
	}

	.data-table thead td, .data-table thead th {
		font-size: 14px;
	}

	.data-table.data-table-collapsible td {
		min-height: 35px;
	}
	
	.card .data-table .card-header, .data-table.card .card-header {
		background-color: #f6f6f6;
		min-height: 30px;
		height: 30px;
		padding-top: 10px;
		padding-left: 10px;
		margin-top:10px;
		border-radius: 0px;
	}

	.card .card-header {
		background-color: #f6f6f6;
		min-height: 30px;
		height: 30px;
		padding-top: 10px;
		padding-left: 10px;
		margin-top:10px;
		border-radius: 0px;
		/* box-shadow: #666 0px 0px 10px */
	}

	.row {
		padding-left: 10px;
		padding-right: 10px;
	}

	.sheet-modal .row {
		padding-left: 0px;
		padding-right: 0px;
	}

	.card-content .f7-icons {
		color: #1296db;
		font-size: 16px;
		padding-top: 2px;
	}
	
	.card {
		box-shadow: 0 0 0 0;
		margin-left: 0px;
	}
	
	.photo-browser-light .navbar {
		background-color: #ffffff;
	}
	
	.photo-browser-light .navbar:after {
		background-color: #ffffff;
	}
</style>

<script>
	// framework 页面回调
	return {
		data : function() {
			return {
			}
		},
		methods : {
			
		}
	}
</script>