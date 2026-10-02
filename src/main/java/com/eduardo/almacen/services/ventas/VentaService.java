package com.eduardo.almacen.services.ventas;

import java.util.List;

import com.eduardo.almacen.dto.ventas.VentaRequest;
import com.eduardo.almacen.dto.ventas.VentaResponse;

public interface VentaService {
    List<VentaResponse> listar();

    List<VentaResponse> listarCanceladas();

    VentaResponse obtenerPorIdActiva(Long id);

    VentaResponse registrar(VentaRequest request);

    VentaResponse cancelar(Long id);
}