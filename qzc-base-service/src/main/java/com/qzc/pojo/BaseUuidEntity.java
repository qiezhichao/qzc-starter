package com.qzc.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class BaseUuidEntity extends BaseIdEntity {

    @Column(name = "uuid", nullable = false, length = 40)
    private String uuid;

}
