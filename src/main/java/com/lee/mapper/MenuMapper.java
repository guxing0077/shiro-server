package com.lee.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lee.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 根据角色id获取权限标识
	 * @param roleId 角色id
	 * @return
	 */
	Set<String> findPermsByRoleId(@Param("roleId") Integer roleId);
}
