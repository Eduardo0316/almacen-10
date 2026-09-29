package com.eduardo.almacen.entities;

import java.math.BigDecimal;

import com.eduardo.almacen.enums.Categoria;
import com.eduardo.almacen.exceptions.DatoInvalidoExeption;
import com.eduardo.almacen.utils.StringCustomUtils;
import com.eduardo.almacen.utils.ValoresNumericos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCTOS") 
@AllArgsConstructor 
@NoArgsConstructor 
@Getter @Builder 
public class Producto {
    ///La entidad no puede confiar en nadie mas que en ella misma, debe encapsular y validar por si misma
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 30)
    private String nombre;
    
    @Column(name = "CATEGORIA", nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    
    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;
    
    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    public static void validarDatos(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad) {
        StringCustomUtils.validarTamanio(nombre, 5,30, "El nombre es requerido y debe tener entre 5 y 30 caracteres");
        
        if(categoria == null)
            throw new DatoInvalidoExeption("La cateogria es necesaria");

        ValoresNumericos.validarBigDecimanPositivo(precio, "El precui es requerido y debe ser positivo");

        ValoresNumericos.validarEnteroPositivo(cantidad, "La cantidad es requerida  debe ser positiva");
    }

    public void actualizar(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad){
        validarDatos(nombre, categoria, precio, cantidad);

        this.nombre = nombre.trim();
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public void aumentarCantidad(int cantidad){
        ValoresNumericos.validarEnteroPositivo(cantidad, "La cantidad debe ser posivtiva");
        this.cantidad += cantidad;
    }

    public void descontarCantidad(int cantidad){
        ValoresNumericos.validarEnteroPositivo(cantidad, "La cantidad debe ser posivtiva");

        if(cantidad > this.cantidad)
            throw new DatoInvalidoExeption("La cantidad debe ser menor o igual a la cantidad actual");

        this.cantidad -= cantidad;
    }

    public static Producto crear(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad){
        validarDatos(nombre, categoria, precio, cantidad);

        return Producto.builder()
            .nombre(nombre.trim())
            .categoria(categoria)
            .precio(precio)
            .cantidad(cantidad)
            .build();
    }
}
