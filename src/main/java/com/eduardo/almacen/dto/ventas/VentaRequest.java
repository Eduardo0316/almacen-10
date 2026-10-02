package com.eduardo.almacen.dto.ventas;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Datos necesarios para crear una venta")
public record VentaRequest(
    @Schema(description = "Identificador de sucursal donde se realizo la venta", example = "1")
    @NotNull(message = "El ID de la sucursal es requerido")
    @Positive(message = "El ID de la sucursal debe ser positivo")
    Long idSucursal,

    @Schema(description = "Lista de productos de una venta")
    @NotEmpty(message = "La lista de productos no debe estar vacia")
    List<@Valid DetalleVentaRequest> productos
) {

}
