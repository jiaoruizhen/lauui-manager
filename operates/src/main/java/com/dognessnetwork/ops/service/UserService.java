package com.dognessnetwork.ops.service;

import com.dognessnetwork.ops.domain.User;
import com.dognessnetwork.ops.dto.Response;

public interface UserService{
	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 */
	Response selectByUsername(String username);
	/**
	 * 分页查询
	 * @param page
	 * @param username
	 * @return
	 */
	Response selectAllUsers(Integer page, String username);
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	Response updateUsers(User user);
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	Response insertUsers(User user);
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	Response delete(String id);
	/**
	 * 根据id获取用户信息
	 * @param id
	 * @return
	 */
	Response findById(String id);
	/**
	 * 分配用户角色
	 * @param id
	 * @param role
	 * @return
	 */
	Response changeRole(String id, String role);
}
