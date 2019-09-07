package com.yonyou.aco.user.mapper;

import com.yonyou.aco.user.entity.UserEntity;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 徐真
 * @since 2019-09-04
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
	public boolean updateUser(UserEntity userEntity);
}