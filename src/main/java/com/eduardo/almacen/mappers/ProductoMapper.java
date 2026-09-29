package com.eduardo.almacen.mappers;

import org.springframework.stereotype.Component;

import com.eduardo.almacen.dto.productos.ProductoRequest;
import com.eduardo.almacen.dto.productos.ProductoResponse;
import com.eduardo.almacen.entities.Producto;
import com.eduardo.almacen.enums.Categoria;

@Component 
public class ProductoMapper {
    //Al mapper no le interesa de donde salen las cosas, no le importa 
    // verificar si existen o no las cosas, no hace validaciones
    public Producto requestAEntidad(ProductoRequest request, Categoria categoria) {
        return request == null ? null : 
            Producto.crear(
                request.nombre(),
                categoria,
                request.precio(),
                request.cantidad());
    }

    public ProductoResponse entidadAResponse(Producto producto){
        return producto == null ? null : 
            new ProductoResponse(
                producto.getId(),
                producto.getNombre(), 
                producto.getCategoria().getDescripcion(), 
                producto.getPrecio(), 
                producto.getCantidad());
    }
}
