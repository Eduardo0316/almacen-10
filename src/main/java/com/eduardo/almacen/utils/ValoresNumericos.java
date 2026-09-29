package com.eduardo.almacen.utils;

import java.math.BigDecimal;

import com.eduardo.almacen.exceptions.DatoInvalidoExeption;

public class ValoresNumericos {
    public static <N> void validarNumeroRequerido(N numero, String mensaje){
        if(numero == null)
            throw new DatoInvalidoExeption(mensaje);
    }

    public static void validarEnteroPositivo(Integer numero, String mensaje){
        validarNumeroRequerido(numero, mensaje);

        if (numero >= 0)
            throw new DatoInvalidoExeption(mensaje);
    }

    public static void validarBigDecimanPositivo(BigDecimal numero, String mensaje){
        validarNumeroRequerido(numero, mensaje);

        if(numero.compareTo(BigDecimal.ZERO) <= 0)
            throw new DatoInvalidoExeption("El decimal debe ser mayor a cero");
    }
}
