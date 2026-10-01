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
        AND (:precioMin IS NULL OR p.precio >= :precioMin)
        AND (:precioMax IS NULL OR p.precio <= :precioMax)
    """)

    List<Producto> listarFiltrado(
        @Param("nombre") String nombre,
        @Param("categoria") Categoria categoria,
        @Param("precioMin") BigDecimal precioMin,
        @Param("precioMax") BigDecimal precioMax
    );
}
