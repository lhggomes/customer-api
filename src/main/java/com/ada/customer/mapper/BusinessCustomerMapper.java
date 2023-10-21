package com.ada.customer.mapper;

import com.ada.customer.dto.BusinessCustomerDto;
import com.ada.customer.entity.BusinessCustomer;

public class BusinessCustomerMapper {

    public static BusinessCustomer toBusinessCustomer(BusinessCustomerDto businessCustomerDto){
        if (businessCustomerDto == null) return BusinessCustomer.builder().build();
        return BusinessCustomer.builder()
                .cnpj(businessCustomerDto.getCnpj())
                .mcc(businessCustomerDto.getMcc())
                .razaoSocial(businessCustomerDto.getRazaoSocial())
                .businessContactCpf(businessCustomerDto.getBusinessContactCpf())
                .businessEmailName(businessCustomerDto.getBusinessEmailName())
                .businessContactName(businessCustomerDto.getBusinessContactName())
                .build();
    }

   public static BusinessCustomerDto toBusinessCustomerDto(BusinessCustomer businessCustomer){
        if (businessCustomer == null) return BusinessCustomerDto.builder().build();
        return BusinessCustomerDto.builder()
                .cnpj(businessCustomer.getCnpj())
                .mcc(businessCustomer.getMcc())
                .razaoSocial(businessCustomer.getRazaoSocial())
                .businessContactCpf(businessCustomer.getBusinessContactCpf())
                .businessEmailName(businessCustomer.getBusinessEmailName())
                .businessContactName(businessCustomer.getBusinessContactName())
                .build();

   }
}
