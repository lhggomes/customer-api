package com.ada.customer.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class BusinessCustomerDto {


    @NotBlank
    @NotNull
    @NotEmpty
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
    private String businessContactCpf;

    @NotBlank
    @NotNull
    @NotEmpty
    private String businessContactName;

    @NotBlank
    @NotEmpty
    @NotNull
    private String businessEmailName;

}
