package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileDao extends BaseMapper<File> {

    @Delete("delete f, r from file d inner join recycleBin r on f.id = r.file_id")
    void deleteAll();

}
