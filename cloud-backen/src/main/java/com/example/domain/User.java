package com.example.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


/**
 * 用户信息
 */

@Data
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String userName;

    private String password;

    private String email;

    private String phone;

    private String realName;

    private Integer status;

    private Long spaceSize;

    private Long usedSize;

    private Long remainSize;


}
