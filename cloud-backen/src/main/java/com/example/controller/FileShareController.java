package com.example.controller;

import com.example.domain.File;
import com.example.domain.Fileshare;
import com.example.service.FileShareService;
import com.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fileshare")
public class FileShareController {

    @Autowired
    private final FileShareService fileShareService;

    @Autowired
    public FileShareController(FileShareService fileShareService){
        this.fileShareService = fileShareService;
    }

    /**
     * 文件分享
     * fileid为文件id
     * 返回String值为分享后的url地址
     * @param file
     * @return
     */
    @CrossOrigin
    @PostMapping("/share")
    public R<String> share(@RequestBody File file){
        return fileShareService.share(file);
    }

    @CrossOrigin
    @PostMapping("/getfile")
    public R<String> getfile(@RequestBody Fileshare fileshare){
        return fileShareService.getfile(fileshare);
    }

    @CrossOrigin
    @PostMapping("/download")
    public R<String> download(@RequestBody File file) throws Exception {
       return fileShareService.download(file);
    }

}
