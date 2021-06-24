package com.xingyu.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class Customer {
    @Column
    private String name;
    @Column
    private String phone;
}
