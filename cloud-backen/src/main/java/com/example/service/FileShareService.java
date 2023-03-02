package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.File;
import com.example.domain.Fileshare;
import com.example.utils.R;

public interface FileShareService extends IService<Fileshare> {

    R<String> share(File file);

    R<String> getfile(Fileshare fileshare);

    R<String> download(File file) throws Exception;

}
