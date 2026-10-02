package com.eduardo.almacen.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eduardo.almacen.entities.Producto;
import com.eduardo.almacen.enums.Categoria;

@Repository 
public interface ProductoRepository extends JpaRepository<Producto, Long>{
    @Query(""" 
        SELECT p FROM Producto p
        WHERE (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
        AND (:categoria IS NULL OR p.categoria = :categoria)
        AND (
            (:precioMin IS NOT NULL AND :precioMax IS NOT NULL AND p.precio BETWEEN :precioMin AND :precioMax)
            OR (:precioMin IS NOT NULL AND :precioMax IS NULL AND p.precio >= :precioMin)
            OR (:precioMin IS NULL AND :precioMax IS NOT NULL AND p.precio <= :precioMax)
            OR (:precioMin IS NULL AND :precioMax IS NULL)
        )
    """)
    List<Producto> listarFiltrado(
        @Param("nombre") String nombre,
        @Param("categoria") Categoria categoria,
        @Param("precioMin") BigDecimal precioMin,
        @Param("precioMax") BigDecimal precioMax
    );
}
