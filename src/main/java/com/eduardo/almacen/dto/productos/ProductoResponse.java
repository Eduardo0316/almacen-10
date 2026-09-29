package com.eduardo.almacen.dto.productos;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

@Schema(description = "Informacion de un producto")
public record ProductoResponse(
    @Schema(description = "ID único del producto", example = "1")
    Long id,

    @Schema(description = "Nombre del producto", example = "Laptop gamer")
    String nombre,
    
    @Schema(description = "Categoria del producto, puede estar en mayusculas o minusculas", example = "Electrónica")
    String categoria,

    @Schema(description = "Precio del producto", example = "15999.67")
    @Positive(message = "El precio debe ser positivo")
    BigDecimal precio,
    
    @Schema(description = "Existencias disponibles del producto", example = "300")
    @Positive(message = "La cantidad debe ser positiva")
    Integer cantidad
) {

}
