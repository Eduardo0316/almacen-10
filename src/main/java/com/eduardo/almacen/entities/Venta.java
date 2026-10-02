package com.eduardo.almacen.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.eduardo.almacen.enums.EstadoVenta;
import com.eduardo.almacen.exceptions.ConflictoException;
import com.eduardo.almacen.exceptions.DatoInvalidoExeption;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "VENTAS")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter @Builder 
public class Venta {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA")
    private Long id;

    @Column(name = "ESTADO", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoVenta estadoVenta;

    @Column(name = "FECHA")
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUCURSAL", nullable = false)
    private Sucursal sucursal;
    
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    public void agregarDetalle(DetalleVenta detalleVenta) {

        if (detalleVenta == null)
            throw new DatoInvalidoExeption("El detalle de la venta es requerido");

        this.detalleVentas.add(detalleVenta);
        detalleVenta.asignarVenta(this);
    }

    public void cancelar() {

        if (this.estadoVenta == EstadoVenta.CANCELADA)
            throw new ConflictoException("La venta ya está cancelada");

        this.estadoVenta = EstadoVenta.CANCELADA;
    }
}
