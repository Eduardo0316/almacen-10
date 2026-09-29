package com.eduardo.almacen.dto.productos;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Son datos necesarios para crear o actualizar un producto")
public record ProductoRequest(
    @Schema(
        description = "Nombre del producto",
        example = "Laptop gamer"
    )
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 5, max = 30, message = "El nombre debe tener entre 5 y 30 caracteres")
    String nombre,
    
    @Schema(
        description = "Categoria del producto, puede estar en mayusculas o minusculas",
        example = "Electrónica",
        allowableValues = {
            "ALIMENTO",
            "HIGIENE",
            "JUGUETE",
            "ELECTRONICA",
            "ROPA",
            "ACCESORIO",
            "FARMACIA"
        }
    )
    @NotBlank(message = "La categoria es requerida")
    String categoria,

    @Schema(description = "Precio del producto", example = "15999.67")
    @NotNull(message = "El precio es requerido")
    @Positive(message = "El precio debe ser positivo")
    BigDecimal precio,
    
    @Schema(description = "Existencias disponibles del producto", example = "300")
    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser positiva")
    Integer cantidad
) {
}
