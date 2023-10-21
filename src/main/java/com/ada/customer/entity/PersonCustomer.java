package com.ada.customer.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 11, max = 11)
    @Column(unique = true)
    private String cpf;

    @NotBlank
    @Size(max = 4)
    private String mcc;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;


}
