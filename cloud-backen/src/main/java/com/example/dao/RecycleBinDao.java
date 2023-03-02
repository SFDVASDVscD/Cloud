package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.RecycleBin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecycleBinDao extends BaseMapper<RecycleBin> {
}
