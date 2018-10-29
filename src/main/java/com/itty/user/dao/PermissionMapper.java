package com.itty.user.dao;

import com.itty.user.entity.Permission;

public interface PermissionMapper {
    int insert(Permission record);

    int insertSelective(Permission record);
}