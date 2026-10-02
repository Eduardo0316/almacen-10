package com.eduardo.almacen.services.reporteVentasSucursal;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduardo.almacen.dto.reporteVentasSucursal.ReporteVentasSucursalResponse;
import com.eduardo.almacen.repositories.ReporteVentasSucursalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service 
@RequiredArgsConstructor 
@Transactional 
@Slf4j 
public class ReporteVentasSucursalServiceImpl implements ReporteVentasSucursalService {
    private final ReporteVentasSucursalRepository repository;

    @Override
    public List<ReporteVentasSucursalResponse> listarReporteVentasSucursal() {
        log.info("Listando reportes de ventas");
        return repository.obtenerReportesVentas();
    }
}
