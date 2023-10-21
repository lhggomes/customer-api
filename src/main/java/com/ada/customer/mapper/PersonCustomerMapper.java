package com.ada.customer.mapper;

import com.ada.customer.dto.PersonCustomerDto;
import com.ada.customer.entity.PersonCustomer;

public class PersonCustomerMapper {

    public static PersonCustomer toPersonCustomer(PersonCustomerDto personCustomerDto){
        if (personCustomerDto == null) return PersonCustomer.builder().build();
        return PersonCustomer.builder()
                .cpf(personCustomerDto.getCpf())
                .email(personCustomerDto.getEmail())
                .mcc(personCustomerDto.getMcc())
                .name(personCustomerDto.getName())
                .build();
    }

    public static PersonCustomerDto toPersonCustomerDto(PersonCustomer personCustomer){
        if (personCustomer == null) return PersonCustomerDto.builder().build();
        return PersonCustomerDto.builder()
                .cpf(personCustomer.getCpf())
                .email(personCustomer.getEmail())
                .mcc(personCustomer.getMcc())
                .name(personCustomer.getName())
                .build();
    }
}
