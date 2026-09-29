package com.eduardo.almacen.enums;

import com.eduardo.almacen.exceptions.DatoInvalidoExeption;
import com.eduardo.almacen.utils.StringCustomUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Getter 
public enum Categoria {
    ALIMENTO("Alimento"),
    HIGIENE("Higiene"),
    JUGUETE("Juguete"),
    ELECTRONICA("Electronica"),
    ROPA("Ropa"),
    ACCESORIO("Accesorio"),
    FARMACIA("Farmacia");

    private final String descripcion;

    public static Categoria obtenetCategoriaPorDescripcion(String descripcion){
        StringCustomUtils.validarNoVacio(descripcion, "La descripcion es requerida");

        String descipcionNormalizada = StringCustomUtils.normalizarTexto(descripcion);

        for(Categoria categoria : values()){
            if(StringCustomUtils.normalizarTexto(categoria.descripcion).equalsIgnoreCase(descipcionNormalizada))
                return categoria;
        }
        
        throw new DatoInvalidoExeption("No existe una categoria con la descripcion: " + descripcion);
    }
}
