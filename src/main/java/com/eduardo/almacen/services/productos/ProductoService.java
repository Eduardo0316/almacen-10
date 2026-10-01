package com.eduardo.almacen.services.productos;

import java.math.BigDecimal;
import java.util.List;

import com.eduardo.almacen.dto.productos.ProductoRequest;
import com.eduardo.almacen.dto.productos.ProductoResponse;

public interface ProductoService {
    List<ProductoResponse> listar(String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax);

    ProductoResponse obtenerPorId(Long id);

    ProductoResponse registrar(ProductoRequest request);

    ProductoResponse actualizar(ProductoRequest request, Long id);

    void eliminar(Long id);
}
