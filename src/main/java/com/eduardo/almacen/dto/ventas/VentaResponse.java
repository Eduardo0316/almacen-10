package com.eduardo.almacen.dto.ventas;

import java.math.BigDecimal;
import java.util.List;

import com.eduardo.almacen.dto.sucursales.SucursalResponse;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informacion de una venta")
public record VentaResponse(
    @Schema(description = "Identificador de la venta", example = "1")
    Long idVenta,
    
    @Schema(description = "Fecha de la venta", example = "01/10/2026")
    String fecha,
    
    @Schema(description = "Estado de la venta", example = "Registrada")
    String estado,
    
    @Schema(description = "Sucursal donde se realizo la venta")
    SucursalResponse sucursal,
    
    @Schema(description = "Lista de productos de la venta")
    List<DetalleVentaResponse> detalleVenta,
    
    @Schema(description = "Total de la venta", example = "15001.56")
    BigDecimal total
) {

}
