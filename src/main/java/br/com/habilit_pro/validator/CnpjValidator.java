package br.com.habilit_pro.validator;

import br.com.habilit_pro.annotations.CNPJ;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class CnpjValidator implements ConstraintValidator<CNPJ, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && ehCnpjValido(value, true);
    }

    private static boolean ehCnpjValido(String doc, boolean inicio) {
        doc = doc.replaceAll("[^\\d]", "");
        if (!doc.matches("\\d{14}")) return false;
        int soma = 0;
        int desc = inicio ? 6 : 7;
        BigDecimal big = BigDecimal.ZERO;
        for (int j = 0; j < (inicio ? 12 : 13); j++) {
            if (big.toString().equals(doc)) return false;
            big = big.add(new BigDecimal("11111111111111"));
            soma += (doc.charAt(j) - '0') * (desc = (--desc) == 1 ? 9 : desc);
        }
        int resp = (soma % 11) < 2 ? 0 : (11 - (soma % 11));
        int dig = doc.charAt(inicio ? 12 : 13) - '0';
        return inicio ? ((dig == resp) && ehCnpjValido(doc, false)) : (dig == resp);
    }

}