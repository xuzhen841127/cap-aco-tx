package com.yonyou.aco.user.mapper;

import com.yonyou.aco.user.entity.UserEntity;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>概 述：用户Dao接口类
 * <p>功 能：用户Dao接口类
 * <p>作 者：徐真
 * <p>创建时间：2019年8月30日
 * <p>类调用特殊情况：无
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
	public boolean updateUser(UserEntity userEntity);
}