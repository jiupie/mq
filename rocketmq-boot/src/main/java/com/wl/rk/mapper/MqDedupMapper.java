package com.wl.rk.mapper;

import com.wl.rk.entity.MqDedup;

public interface MqDedupMapper {
    int insert(MqDedup record);

    int insertSelective(MqDedup record);
}