package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.Manager;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManagerDao extends BaseMapper<Manager> {
}
