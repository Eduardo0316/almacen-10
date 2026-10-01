package com.eduardo.almacen.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.almacen.dto.productos.ProductoRequest;
import com.eduardo.almacen.dto.productos.ProductoResponse;
import com.eduardo.almacen.services.productos.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController 
@RequestMapping("/api/productos")
@RequiredArgsConstructor 
@Tag(name = "Productos", description = "Gestion del inventario de productos")
public class ProductoController {
    private final ProductoService service;

    @GetMapping
    @Operation(summary = "Listar todos los productos")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    @ApiResponse(responseCode = "404", description = "Productos no existen")
    public ResponseEntity<List<ProductoResponse>> listar(
        @Parameter(description = "Búsqueda por nombre", example = "Laptop")
        @RequestParam(required = false) String nombre,
        
        @Parameter(description = "Filtro por categoria", example = "Electrónica")
        @RequestParam(required = false) String categoria,

        @Parameter(description = "Precio mínimo", example = "100")
        @RequestParam(required = false) BigDecimal precioMin,

        @Parameter(description = "Precio máximo", example = "15000")
        @RequestParam(required = false) BigDecimal precioMax
    ) {
        return ResponseEntity.ok(service.listar(nombre, categoria, precioMin, precioMax));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener Producto por ID")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    @ApiResponse(responseCode = "404", description = "El producto no existe")
    public ResponseEntity<ProductoResponse> obtenerPorID(
        @Parameter(description = "ID del producto", example = "1")
        @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
    
    @PostMapping
    @Operation(summary = "Registrar un nuevo producto")
    @ApiResponse(responseCode = "201", description = "Producto creado")
    @ApiResponse(responseCode = "409", description = "Conflicto con datos del producto")
    public ResponseEntity<ProductoResponse> registrar(
        @Valid @RequestBody ProductoRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.registrar(request));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente")
    @ApiResponse(responseCode = "200", description = "Producto actualizado")
    @ApiResponse(responseCode = "404", description = "El producto no existe")
    @ApiResponse(responseCode = "409", description = "Conflicto con datos del producto")
    public ResponseEntity<ProductoResponse> actualizar(
        @Parameter(description = "ID del producto", example = "1")
        @PathVariable @Positive(message = "El ID debe ser positivo") Long id,

        @Valid @RequestBody ProductoRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    @ApiResponse(responseCode = "204", description = "producto eliminado")
    @ApiResponse(responseCode = "404", description = "El producto no existe")
    @ApiResponse(responseCode = "409", description = "El producto esta en uno y no se puede eliminar")
    public ResponseEntity<Void> eliminar(
        @Parameter(description = "ID del producto", example = "1")
        @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
