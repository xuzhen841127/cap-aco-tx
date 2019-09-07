package com.yonyou.aco.user.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 徐真
 * @since 2019-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("DD_USER")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId("ID")
	private Number id;

	/**
	 * 用户id（内网用户id）
	 */
	@TableField("USER_ID")
	private Number userId;

	/**
	 * 手机号（钉钉账号）
	 */
	@TableField("TELEPHONE")
	private String telephone;

	/**
	 * 用户名称
	 */
	@TableField("NAME")
	private String name;

	/**
	 * 归口科室编码
	 */
	@TableField("BELONG_ORG")
	private String belongOrg;

	/**
	 * 归口科室名称
	 */
	@TableField("MOF_DEP")
	private String mofDep;

	/**
	 * 分管科室编码
	 */
	@TableField("FG_MOF_ID")
	private String fgMofId;

	/**
	 * 分管科室名称
	 */
	@TableField("FG_MOF_NAME")
	private String fgMofName;

	/**
	 * 钉钉userId
	 */
	@TableField("DD_USER_ID")
	private String ddUserId;
}