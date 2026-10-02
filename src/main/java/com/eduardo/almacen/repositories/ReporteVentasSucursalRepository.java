package com.eduardo.almacen.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eduardo.almacen.dto.reporteVentasSucursal.ReporteVentasSucursalResponse;
import com.eduardo.almacen.entities.Venta;

@Repository 
public interface ReporteVentasSucursalRepository extends JpaRepository<Venta, Long>{
    @Query("""
        SELECT new com.eduardo.almacen.dto.reporteVentasSucursal.ReporteVentasSucursalResponse(
            s.id,
            s.nombre,
            COALESCE(SUM(d.cantidadProducto * d.precioProduto), 0),
            COALESCE(SUM(d.cantidadProducto), 0L)
        )
        FROM Sucursal s
        LEFT JOIN Venta v ON s.id = v.sucursal.id AND v.estadoVenta = com.eduardo.almacen.enums.EstadoVenta.REGISTRADA
        LEFT JOIN v.detalleVentas d
        GROUP BY s.id, s.nombre
    """)

    List<ReporteVentasSucursalResponse> obtenerReportesVentas();
}
