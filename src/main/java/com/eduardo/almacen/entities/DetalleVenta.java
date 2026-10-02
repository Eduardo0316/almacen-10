package com.eduardo.almacen.entities;

import java.math.BigDecimal;

import com.eduardo.almacen.exceptions.DatoInvalidoExeption;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "DETALLES_VENTAS")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter @Builder 
public class DetalleVenta {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_VENTA")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VENTA", nullable = false)
    private Venta venta;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUCTO", nullable = false)
    private Producto producto;

    @Column(name = "CANTIDAD_PRODUCTO")
    private Integer cantidadProducto;

    @Column(name = "PRECIO_PRODUCTO")
    private BigDecimal precioProduto;

    public void asignarVenta(Venta venta){
        if (venta == null)
            throw new DatoInvalidoExeption("La venta es requerida");

        this.venta = venta;
    }

    public BigDecimal getSubtotal(){
        return BigDecimal.valueOf(this.cantidadProducto).multiply(this.precioProduto);
    }
}
