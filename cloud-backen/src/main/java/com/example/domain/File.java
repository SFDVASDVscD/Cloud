package com.example.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@Entity
public class File implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private Long userId;

    private String file;

    private String fileName;

    private Long fileSize;

    private String fileType;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Integer status;
}
