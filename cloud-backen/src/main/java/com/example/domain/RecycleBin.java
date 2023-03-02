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
public class RecycleBin implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private Long fileId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
