package com.yonyou.aco.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yonyou.aco.user.entity.UserEntity;
import com.yonyou.aco.user.mapper.UserMapper;
import com.yonyou.aco.user.service.IUserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 徐真
 * @since 2019-09-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {
	@Override
	public List<UserEntity> findAllUser() {
		return this.baseMapper.selectList(null);
	}

	@Override
	public boolean updateUser(String telephone, String ddUserId) {
		UserEntity user = new UserEntity();
		user.setTelephone(telephone);
		user.setDdUserId(ddUserId);
		return this.baseMapper.updateUser(user);
	}
}