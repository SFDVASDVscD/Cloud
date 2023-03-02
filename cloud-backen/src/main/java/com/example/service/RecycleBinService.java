package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.File;
import com.example.domain.RecycleBin;
import com.example.utils.R;

import java.util.List;

public interface RecycleBinService extends IService<RecycleBin> {

    R<List<File>> getAll();

    R<String> delete(File file) throws Exception;

    R<String> restore(File file);

    R<String> deleteAll();

}
