package com.eduardo.almacen.dto.ventas;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalle de un producto dentro de una venta")
public record DetalleVentaResponse(
    @Schema(description = "ID del producto", example = "1")
    Long idProducto,

    @Schema(description = "Nombre del producto", example = "Laptop")
    String nombreProducto,
    
    @Schema(description = "Cantidad del producto", example = "12")
    Integer cantidadProducto,
    
    @Schema(description = "Precio del producto", example = "1500.56")
    BigDecimal precioProduto,
    
    @Schema(description = "Subtotal de la compra", example = "15001.56")
    BigDecimal subtotal
) {

}
