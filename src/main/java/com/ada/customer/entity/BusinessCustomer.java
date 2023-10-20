package com.ada.customer.entity;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class BusinessCustomer extends Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String cnpj;
    @NotBlank
    private String razaoSocial;

}
