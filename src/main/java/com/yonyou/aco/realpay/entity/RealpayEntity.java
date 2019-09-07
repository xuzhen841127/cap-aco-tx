package com.yonyou.aco.realpay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *  实体类
 * </p>
 *
 * @author 徐真
 * @since 2019-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("DD_REALPAY")
public class RealpayEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("REALPAY_ID")
    private String realpayId;

    /**
     * 拨款申请id
     */
    @TableField("REALPAY_REQUEST_ID")
    private Double realpayRequestId;

    /**
     * 拨款申请编号
     */
    @TableField("REALPAY_REQUEST_CODE")
    private String realpayRequestCode;

    /**
     * 单位
     */
    @TableField("AGENCY_CODE")
    private String agencyCode;

    @TableField("DEP_PRO_ID")
    private Double depProId;

    /**
     * 项目名称
     */
    @TableField("DEP_PRO_NAME")
    private String depProName;

    /**
     * 金额
     */
    @TableField("AMOUNT")
    private Double amount;

    /**
     * 收款人账号
     */
    @TableField("PAYEE_ACCOUNT_NO")
    private String payeeAccountNo;

    /**
     * 收款人全称
     */
    @TableField("PAYEE_ACCOUNT_NAME")
    private String payeeAccountName;

    /**
     * 收款人开户行
     */
    @TableField("PAYEE_ACCOUNT_BANK")
    private String payeeAccountBank;

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
     * 经济分类
     */
    @TableField("EXP_ECO")
    private String expEco;

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
     * 上传日期
     */
    @TableField("CREATE_DATE")
    private String createDate;

    /**
     * 日志id
     */
    @TableField("TASK_ID")
    private Double taskId;

    /**
     * 业务科室
     */
    @TableField("MOF_NAME")
    private String mofName;

    /**
     * 用户账号
     */
    @TableField("USER_CODE")
    private String userCode;

    /**
     * 用户名称
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 菜单id
     */
    @TableField("MENU_ID")
    private Double menuId;

    /**
     * 菜单名称
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

    /**
     * 是否已发送钉钉提醒 0：未发送 1：已发送
     */
    @TableField("IS_SEND_NOTICE")
    private String isSendNotice;
}