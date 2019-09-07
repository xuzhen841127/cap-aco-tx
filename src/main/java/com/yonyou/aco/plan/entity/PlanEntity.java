package com.yonyou.aco.plan.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p> 用款计划实体类 </p>
 *
 * @author 徐真
 * @since 2019-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("DD_PLAN")
public class PlanEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @TableId("PLAN_ID")
    private String planId;

    /**
     * 用款计划主键id
     */
    @TableField("PLAN_DETAIL_ID")
    private Double planDetailId;

    /**
     * 用款计划明细编号
     */
    @TableField("PLAN_DETAIL_CODE")
    private String planDetailCode;

    /**
     * 单位
     */
    @TableField("AGENCY_CODE")
    private String agencyCode;

    /**
     * 项目id
     */
    @TableField("DEP_PRO_ID")
    private Double depProId;

    /**
     * 项目名称
     */
    @TableField("DEP_PRO_NAME")
    private String depProName;

    /**
     * 指标来源
     */
    @TableField("BGT_SOURCE_NAME")
    private String bgtSourceName;

    /**
     * 资金性质
     */
    @TableField("FUND_TYPE")
    private String fundType;

    /**
     * 功能分类
     */
    @TableField("EXP_FUNC")
    private String expFunc;

    /**
     * 金额
     */
    @TableField("AMOUNT")
    private Double amount;

    /**
     * 月份
     */
    @TableField("PLAN_MONTH")
    private Integer planMonth;

    /**
     * 支付方式
     */
    @TableField("PAY_TYPE")
    private String payType;

    /**
     * 用途
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 办理意见
     */
    @TableField("TASK_OPINION")
    private String taskOpinion;

    /**
     * 上传时间
     */
    @TableField("CREATE_DATE")
    private String createDate;

    /**
     * 流程id
     */
    @TableField("TASK_ID")
    private Double taskId;

    /**
     * 业务科室
     */
    @TableField("MOF_NAME")
    private String mofName;

    /**
     * 用户
     */
    @TableField("USER_CODE")
    private String userCode;

    /**
     * 用户名称
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 流程菜单id
     */
    @TableField("MENU_ID")
    private Double menuId;

    /**
     * 流程菜单
     */
    @TableField("MENU_NAME")
    private String menuName;

    /**
     * 处理状态 1新增、2送审、3退回
     */
    @TableField("STATUS")
    private Double status;

    /**
     * 内网处理时间
     */
    @TableField("INDATE")
    private String indate;

    /**
     * 外网处理时间
     */
    @TableField("OUTDATE")
    private String outdate;

    /**
     * 是否已发短信提醒：null未发1 已发
     */
    @TableField("IS_MESSAGE")
    private Double isMessage;
}