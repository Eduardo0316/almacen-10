package com.eduardo.almacen.mappers;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.eduardo.almacen.dto.ventas.DetalleVentaRequest;
import com.eduardo.almacen.dto.ventas.DetalleVentaResponse;
import com.eduardo.almacen.entities.DetalleVenta;
import com.eduardo.almacen.entities.Producto;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class DetalleVentaMapper {
    public static DetalleVenta requestAEntidad(DetalleVentaRequest request, Producto producto){
        return request == null
            ? null
            : DetalleVenta.builder()
                .producto(producto)
                .cantidadProducto(request.cantidadProducto())
                .precioProduto(producto.getPrecio())
                .build();
    }

    public static DetalleVentaResponse entidadAResponse(DetalleVenta detalleVenta){
        return detalleVenta == null 
            ? null
            : new DetalleVentaResponse(
                detalleVenta.getProducto().getId(), 
                detalleVenta.getProducto().getNombre(), 
                detalleVenta.getCantidadProducto(), 
                detalleVenta.getPrecioProduto(),
                detalleVenta.getSubtotal());
    }

    public List<DetalleVentaResponse> entidadAResponse(List<DetalleVenta> lista) {
        if (lista == null || lista.isEmpty()) {
            return Collections.emptyList();
        }

        return lista.stream()
                .map(DetalleVentaMapper::entidadAResponse)
                .toList();
    }
}
