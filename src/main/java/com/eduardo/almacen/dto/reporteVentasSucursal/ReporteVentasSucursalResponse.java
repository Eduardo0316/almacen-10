package com.eduardo.almacen.dto.reporteVentasSucursal;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

@Schema(description = "Descripción del reporte de ventas")
public record ReporteVentasSucursalResponse(
    @Schema(description = "ID único del reporte", example = "1")
    Long id,

    @Schema(description = "Nombre de la sucursal", example = "Norte")
    String nombreSucursal,

    @Schema(description = "Total facturado", example = "12000")
    @Positive(message = "El total debe ser positivo")
    BigDecimal totalFacturado,

    @Schema(description = "Cantidad total de productos vendidos", example = "12")
    @Positive(message = "El total debe ser positivo")
    Long totalProductosVendidos
) {

}