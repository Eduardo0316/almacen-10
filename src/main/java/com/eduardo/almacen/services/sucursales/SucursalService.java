package com.eduardo.almacen.services.sucursales;

import java.util.List;

import com.eduardo.almacen.dto.sucursales.SucursalRequest;
import com.eduardo.almacen.dto.sucursales.SucursalResponse;

public interface SucursalService {

    List<SucursalResponse> listar();

    SucursalResponse obtenerPorId(Long id);

    SucursalResponse registrar(SucursalRequest request);

    SucursalResponse actualizar(SucursalRequest request, Long id);

    void eliminar(Long id);
}