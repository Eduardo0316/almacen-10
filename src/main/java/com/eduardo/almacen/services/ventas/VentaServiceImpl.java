package com.eduardo.almacen.services.ventas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduardo.almacen.dto.ventas.VentaRequest;
import com.eduardo.almacen.dto.ventas.VentaResponse;
import com.eduardo.almacen.entities.DetalleVenta;
import com.eduardo.almacen.entities.Producto;
import com.eduardo.almacen.entities.Sucursal;
import com.eduardo.almacen.entities.Venta;
import com.eduardo.almacen.enums.EstadoVenta;
import com.eduardo.almacen.exceptions.ConflictoException;
import com.eduardo.almacen.exceptions.RecursoNoEncontradoException;
import com.eduardo.almacen.mappers.DetalleVentaMapper;
import com.eduardo.almacen.mappers.VentaMapper;
import com.eduardo.almacen.repositories.ProductoRepository;
import com.eduardo.almacen.repositories.SucursalRepository;
import com.eduardo.almacen.repositories.VentaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service 
@Transactional 
@Slf4j 
@RequiredArgsConstructor 
public class VentaServiceImpl implements VentaService{

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;

    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;


    @Transactional 
    @Override
    public VentaResponse cancelar(Long id) {
        log.info("Cancelando venta con ID: {}", id);
        Venta venta = obtenerVentaOException(id);
        venta.cancelar();
        venta.getDetalleVentas().forEach(detalle -> {
            Producto producto = detalle.getProducto();
            int cantidadADevolver = detalle.getCantidadProducto();
            
            producto.aumentarCantidad(cantidadADevolver);
            
            log.info("Restaurando {} unidades al stock del producto ID: {}", 
                    cantidadADevolver, producto.getId());
        });
        return ventaMapper.entidadAResponse(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listar() {
        log.info("Obteniento listado de ventas activas");
        return ventaRepository.findAllAndActive().stream()
            .map(ventaMapper::entidadAResponse)
            .toList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listarCanceladas() {
        log.info("Obteniento listado de ventas canceladas");
        return ventaRepository.findAllCancelled().stream()
            .map(ventaMapper::entidadAResponse)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponse obtenerPorIdActiva(Long id) {
        log.info("Obteniendo venta activa con ID: {}", id);
        return ventaMapper.entidadAResponse(
            ventaRepository.findByIdAndActive(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta activa no encontrada con id: " + id))
        );
    }

    @Override
    @Transactional 
    public VentaResponse registrar(VentaRequest request) {
        log.info("Registrando venta para sucursal ID: {}", request.idSucursal());

        Sucursal sucursal = obtenerSucursalOException(request.idSucursal());

        Venta venta = Venta.builder()
            .sucursal(sucursal)
            .estadoVenta(EstadoVenta.REGISTRADA)
            .fecha(LocalDate.now())
            .build();

        request.productos().forEach(p -> {
            Producto producto = obtenerProductoOException(p.idProducto());

            if(producto.getCantidad() < p.cantidadProducto())
                throw new ConflictoException("Stock insuficiente para producto");

            producto.descontarCantidad(p.cantidadProducto());
            
            DetalleVenta detalleVenta = DetalleVentaMapper.requestAEntidad(p, producto);
            venta.agregarDetalle(detalleVenta);
        });

        Venta ventaGuardada = ventaRepository.save(venta);
        return ventaMapper.entidadAResponse(ventaGuardada);
    }
    
    private Venta obtenerVentaOException(Long id){
        log.info("Obteniendo venta por id: {}", id);
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con id: " + id));
    }

    private Sucursal obtenerSucursalOException(Long id){
        log.info("Obteniendo sucursal con id: {}", id);
        return sucursalRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Sucursal no encontrada con id: " + id));
    }

    private Producto obtenerProductoOException(Long id) {
        log.info("Obteniendo producto con id: {}", id);
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));
    }
}
