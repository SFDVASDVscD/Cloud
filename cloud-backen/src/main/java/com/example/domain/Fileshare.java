package com.example.domain;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Entity
public class Fileshare implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private Long fileId;

    //文件有效时间
    private LocalDateTime shareTime;

    private String shareUrl;

    private Integer accessRead;

    private Integer accessWrite;


}
