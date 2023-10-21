package com.ada.customer.validators;


import com.ada.customer.annotation.CnpjCpf;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.InputMismatchException;

public class CnpjCpfValidator implements ConstraintValidator<CnpjCpf, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s == null || s.isEmpty() || isCpf(s) || isCnpj(s);
    }

    @Override
    public void initialize(CnpjCpf constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    private boolean isCpf(String cpf) {

        // Leaving only the numbers on the string
        cpf = cpf.replaceAll("\\D", "");

        try {
            Long.parseLong(cpf);
        } catch (NumberFormatException e) {
            return false;
        }

        int d1, d2;
        int firstDigit, secondDigit, remainder;
        int cpfDigit;
        String nDigResult;

        d1 = d2 = 0;
        for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
            cpfDigit = Integer.parseInt(cpf.substring(nCount - 1, nCount));
            d1 = d1 + (11 - nCount) * cpfDigit;
            d2 = d2 + (12 - nCount) * cpfDigit;
        }

        remainder = d1 % 11;

        if (remainder < 2)
            firstDigit = 0;
        else
            firstDigit = 11 - remainder;

        d2 += 2 * firstDigit;

        remainder = (d2 % 11);
        if (remainder < 2)
            secondDigit = 0;
        else
            secondDigit = 11 - remainder;

        String numCheckDigit = cpf.substring(cpf.length() - 2);
        nDigResult = firstDigit + String.valueOf(secondDigit);
        return numCheckDigit.equals(nDigResult);
    }

    private boolean isCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("\\D", "");
        try {
            Long.parseLong(cnpj);
        } catch (NumberFormatException e) {
            return false;
        }

        if (cnpj.equals("0".repeat(14)) || cnpj.equals("1".repeat(14))
                || cnpj.equals("2".repeat(14)) || cnpj.equals("3".repeat(14))
                || cnpj.equals("4".repeat(14)) || cnpj.equals("5".repeat(14))
                || cnpj.equals("6".repeat(14)) || cnpj.equals("7".repeat(14))
                || cnpj.equals("8".repeat(14)) || cnpj.equals("9".repeat(14)) || (cnpj.length() != 14))
            return (false);

        char dig13, dig14;
        int sm, i, r, num, peso;
        try {

            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                num = cnpj.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else
                dig13 = (char) ((11 - r) + 48);

            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = cnpj.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }
            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else
                dig14 = (char) ((11 - r) + 48);
            return (dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13));
        } catch (InputMismatchException error) {
            return (false);
        }
    }
}
