package com.eduardo.almacen.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.almacen.doc.ProblemaDoc;
import com.eduardo.almacen.dto.reporteVentasSucursal.ReporteVentasSucursalResponse;
import com.eduardo.almacen.services.reporteVentasSucursal.ReporteVentasSucursalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
@RequestMapping("/reporte-ventas")
@RequiredArgsConstructor 
@Tag(name = "Reportes Ventas Sucursales", description = "Gestion de reportes de ventas en sucursales")
@ApiResponse(
    responseCode = "400",
    description = "Datos o parametros invalidos",
    content = @Content(
            mediaType = "application/problem+json",
            schema = @Schema(
                    implementation = ProblemaDoc.class
            )
    )
)
@ApiResponse(
    responseCode = "500",
    description = "Error interno del servidor",
    content = @Content(
            mediaType = "application/problem+json",
            schema = @Schema(
                    implementation = ProblemaDoc.class
            )
    )
)
public class ReporteVentasSucursalController {
    private final ReporteVentasSucursalService service;
    
    @GetMapping
    @Operation(
            summary = "Listar reporte de ventas de cada sucursal"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Reportes listados"
    )
    public ResponseEntity<List<ReporteVentasSucursalResponse>> listarReporte() {
        return ResponseEntity.ok(service.listarReporteVentasSucursal());
    }
    
}
