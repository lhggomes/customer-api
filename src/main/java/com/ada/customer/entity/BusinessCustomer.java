package com.ada.customer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String cnpj;
    @NotBlank
    @Size(max = 50)
    private String razaoSocial;
    @NotBlank
    @Size(max = 4)
    private String mcc;
    @NotBlank
    private String businessContactCpf;
    @NotBlank
    private String businessContactName;
    @NotBlank
    @Email
    private String businessEmailName;

}
