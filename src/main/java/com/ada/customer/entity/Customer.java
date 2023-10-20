package com.ada.customer.entity;

import lombok.Data;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class Customer {

    private String mcc;
    private String contactName;
    private String contactEmail;

}
