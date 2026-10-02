package com.eduardo.almacen.mappers;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.eduardo.almacen.dto.ventas.VentaResponse;
import com.eduardo.almacen.entities.DetalleVenta;
import com.eduardo.almacen.entities.Venta;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class VentaMapper {
    private final DetalleVentaMapper detalleVenta;
    private final SucursalMapper sucursalMapper;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public VentaResponse entidadAResponse(Venta venta){

        BigDecimal totalVenta = venta.getDetalleVentas().stream()
                .map(DetalleVenta::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String fechaFormateada = (venta.getFecha() != null) ? venta.getFecha().format(FORMATTER) : null;
        String estadoString = (venta.getEstadoVenta() != null) ? venta.getEstadoVenta().toString() : null;

        return venta == null
            ? null
            : new VentaResponse(
                venta.getId(), 
                fechaFormateada, 
                estadoString,
                sucursalMapper.entidadAResponse(venta.getSucursal()), 
                detalleVenta.entidadAResponse(venta.getDetalleVentas()), 
                totalVenta
                );
    }
}
