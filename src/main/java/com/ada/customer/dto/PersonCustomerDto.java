package com.ada.customer.dto;

import com.ada.customer.annotation.CnpjCpf;
import lombok.*;

import javax.validation.constraints.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonCustomerDto {

    @NotBlank
    @NotEmpty
    @NotNull
    @CnpjCpf
    @Size(min = 11, max = 11)
    private String cpf;

    @NotBlank
    @NotEmpty
    @NotNull
    @Size(max = 4)
    private String mcc;

    @NotBlank
    @NotEmpty
    @NotNull
    @Email
    private String email;

    @NotBlank
    @NotEmpty
    @NotNull
    @Size(max = 50)
    private String name;
}
