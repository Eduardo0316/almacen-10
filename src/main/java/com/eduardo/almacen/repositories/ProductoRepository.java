package com.eduardo.almacen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eduardo.almacen.entities.Producto;

@Repository 
public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
}
