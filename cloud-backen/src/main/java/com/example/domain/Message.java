package com.example.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Message implements Serializable {
    @Id
    private Long id;

    private Long managerId;

    private Long userId;

    private String message;
}
