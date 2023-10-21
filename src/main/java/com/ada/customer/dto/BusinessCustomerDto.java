package com.ada.customer.dto;

import com.ada.customer.annotation.CnpjCpf;
import lombok.*;

import javax.validation.constraints.*;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessCustomerDto {


    @NotBlank
    @NotNull
    @NotEmpty
    @CnpjCpf
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
    @CnpjCpf
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
