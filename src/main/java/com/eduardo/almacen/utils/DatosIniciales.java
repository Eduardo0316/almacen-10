package com.eduardo.almacen.utils;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.eduardo.almacen.entities.Producto;
import com.eduardo.almacen.enums.Categoria;
import com.eduardo.almacen.repositories.ProductoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j 
@RequiredArgsConstructor  
public class DatosIniciales implements CommandLineRunner{
    private final ProductoRepository repository;

    @Override 
    public void run(String... args) throws Exception{
        if(repository.count()==0){
            repository.saveAll(List.of(
                new Producto(null,
                    "Laptop gamer",
                    Categoria.ELECTRONICA,
                    BigDecimal.valueOf(1500),
                    10
                ),
                new Producto(null,
                    "Mouse inalambrico",
                    Categoria.ELECTRONICA,
                    BigDecimal.valueOf(25),
                    20
                ),
                new Producto(null,
                    "Playera deportiva",
                    Categoria.ROPA,
                    BigDecimal.valueOf(20),
                    100
                )
            ));
            log.info("Productos de prueba cargados correctamente");
        }
    }
}
