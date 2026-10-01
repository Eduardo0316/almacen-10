package com.eduardo.almacen.mappers;

import org.springframework.stereotype.Component;

import com.eduardo.almacen.dto.sucursales.SucursalRequest;
import com.eduardo.almacen.dto.sucursales.SucursalResponse;
import com.eduardo.almacen.entities.Sucursal;

@Component 
public class SucursalMapper {

    public Sucursal requestAEntidad(SucursalRequest request) {

        return request == null
                ? null
                : Sucursal.crear(
                        request.nombre(),
                        request.direccion());
    }

    public SucursalResponse entidadAResponse(Sucursal sucursal) {

        return sucursal == null
                ? null
                : new SucursalResponse(
                    sucursal.getId(),
                    sucursal.getNombre(),
                    sucursal.getDireccion());
    }
}