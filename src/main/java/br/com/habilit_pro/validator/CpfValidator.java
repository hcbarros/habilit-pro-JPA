package br.com.habilit_pro.validator;


import br.com.habilit_pro.annotations.CPF;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class CpfValidator implements ConstraintValidator<CPF, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && ehCpfValido(value, true);
    }

    private static boolean ehCpfValido(String cpf, boolean inicio) {
        cpf = cpf.replaceAll("[^\\d]", "");
        if(!cpf.matches("\\d{11}")) return false;
        int soma = 0;
        int desc = (inicio ? 9 : 10) + 1;
        BigDecimal big = BigDecimal.ZERO;
        for (int j = 0; j < (inicio ? 9 : 10); j++) {
            if(big.toString().equals(cpf)) return false;
            big = big.add(new BigDecimal("11111111111"));
            soma += (cpf.charAt(j) - '0') * (desc--);
        }
        int resp = (soma % 11) < 2 ? 0 : (11 - (soma % 11));
        int dig = cpf.charAt(inicio ? 9 : 10) - '0';
        return inicio ? ((dig == resp) && ehCpfValido(cpf,false)) : (dig == resp);
    }

}
