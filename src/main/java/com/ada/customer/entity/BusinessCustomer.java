package com.ada.customer.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
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
