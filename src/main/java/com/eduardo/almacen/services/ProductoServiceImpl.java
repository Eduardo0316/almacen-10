package com.eduardo.almacen.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduardo.almacen.dto.productos.ProductoRequest;
import com.eduardo.almacen.dto.productos.ProductoResponse;
import com.eduardo.almacen.entities.Producto;
import com.eduardo.almacen.enums.Categoria;
import com.eduardo.almacen.mappers.ProductoMapper;
import com.eduardo.almacen.repositories.ProductoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service 
@RequiredArgsConstructor 
@Transactional 
@Slf4j 
public class ProductoServiceImpl implements ProductoService{

    private final ProductoRepository repository;
    private final ProductoMapper mapper;

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {
        Producto producto = obtenerProductoOExeption(id);

        log.info("Actualizando producto con id {}", id);

        producto.actualizar(
            request.nombre(), 
            Categoria.obtenetCategoriaPorDescripcion(request.categoria().trim()), 
            request.precio(), 
            request.cantidad());

        repository.saveAndFlush(producto);

        log.info("Producto con id {} actualizado correctamente", id);
        return mapper.entidadAResponse(producto);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = obtenerProductoOExeption(id);

        log.info("Eliminando producto con id {}", id);
        
        repository.delete(producto);
        repository.flush();

        log.info("Producto con id {} eliminado correctamente", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax) {
        log.info("Listando todos los productos");

        Categoria categoriaEnum = null;
        if (categoria != null && !categoria.isBlank()) {
            categoriaEnum = Categoria.obtenetCategoriaPorDescripcion(categoria);
        }

        return repository.listarFiltrado(nombre, categoriaEnum, precioMin, precioMax)
            .stream()
            .map(mapper::entidadAResponse).
            toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {
        return mapper.entidadAResponse(obtenerProductoOExeption(id));
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {
        log.info("Registrando nuevo producto");
        Producto producto = mapper.requestAEntidad(
            request,
            Categoria.obtenetCategoriaPorDescripcion(
                request.categoria().trim()
            ));

        repository.save(producto);

        return mapper.entidadAResponse(producto);
    }
    
    private Producto obtenerProductoOExeption(Long id){
        log.info("Buscando producto con id: {}", id);

        return repository.findById(id).orElseThrow(
            () -> new RuntimeException(
                "Producto no encontrado con id: " + id));
    }
}
