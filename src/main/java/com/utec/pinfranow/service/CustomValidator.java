package com.utec.pinfranow.service;

import com.fabdelgado.ciuy.Validator;

public class CustomValidator extends Validator {

    @Override
    public void validateInput(String ci) {
        super.validateInput(ci);

        String cleaned = cleanCi(ci);
        if (cleaned.length() != 8) { // se usa el cleaned para sacar los puntos y el guión
            throw new IllegalArgumentException("La cédula debe tener exactamente 8 dígitos");
        }
    }
}
