package com.ada.customer.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Builder
public class BusinessCustomerDto {


    @NotBlank
    @NotNull
    @NotEmpty
    @Size(min = 14, max = 14)
    private String cnpj;

    @NotBlank
    @NotEmpty
    @Size(max = 50)
    @NotNull
    private String razaoSocial;

    @Size(max = 4)
    @NotBlank
    @NotEmpty
    @NotNull
    private String mcc;

    @NotBlank
    @NotEmpty
    @NotNull
    @Size(min = 11, max = 11)
    private String businessContactCpf;

    @NotBlank
    @NotNull
    @NotEmpty
    private String businessContactName;

    @NotBlank
    @NotEmpty
    @NotNull
    @Email
    private String businessEmailName;

}
