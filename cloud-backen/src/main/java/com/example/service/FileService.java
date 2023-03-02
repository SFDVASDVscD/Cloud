package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.File;
import com.example.utils.R;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService extends IService<File> {
    void delFile(java.io.File file) throws Exception;

    R<List<File>> fileList(Long id);

    R<List<File>> getAllFile(String[] type);

    R<String> upload(MultipartFile file) throws IOException;

    R<String> download(File file) throws Exception;

    R<String> delete(File file);

    R<String> deleteByManager(File file) throws Exception;

    R<String> browse( File file);

    R<List<File>> recentBrowse();

}
