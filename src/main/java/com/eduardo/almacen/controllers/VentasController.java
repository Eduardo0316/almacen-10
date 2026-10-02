package com.eduardo.almacen.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.almacen.doc.ProblemaDoc;
import com.eduardo.almacen.dto.ventas.VentaRequest;
import com.eduardo.almacen.dto.ventas.VentaResponse;
import com.eduardo.almacen.services.ventas.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController 
@RequestMapping("/ventas")
@Validated 
@Tag(name = "Ventas", description = "Gestion de ventas")
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
@RequiredArgsConstructor 
public class VentasController {
    private final VentaService ventaService;

    @GetMapping
    @Operation(
        summary = "Listar ventas",
        description = "Lista todas las ventas registradas que se encuentren activas"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Listado obtenido"
    )
    @ApiResponse(
        responseCode = "409",
        description = "El estado de venta no es válido",
        content = @Content(
                mediaType = "application/problem+json",
                schema = @Schema(
                        implementation = ProblemaDoc.class
                )
        )
    )
    public ResponseEntity<List<VentaResponse>> listarVentas() {
        return ResponseEntity.ok(ventaService.listar());
    }
    
    @GetMapping("/canceladas")
    @Operation(
        summary = "Listar ventas canceladas",
        description = "Lista el historico de las ventas canceladas"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Listado obtenido"
    )
    @ApiResponse(
        responseCode = "409",
        description = "El estado de venta no es válido",
        content = @Content(
                mediaType = "application/problem+json",
                schema = @Schema(
                        implementation = ProblemaDoc.class
                )
        )
    )
    public ResponseEntity<List<VentaResponse>> listarVentasCanceladas() {
        return ResponseEntity.ok(ventaService.listarCanceladas());
    }
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener venta por ID",
        description = "Recupera la venta con el ID proporcionado"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Venta obtenida"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Venta no encontrada",
        content = @Content(
                mediaType = "application/problem+json",
                schema = @Schema(
                        implementation = ProblemaDoc.class
                )
        )
    )
    public ResponseEntity<VentaResponse> obtenerPorId(
        @Parameter(description = "Identificador de la venta", example = "1")
        @PathVariable @Positive(message = "El identificador debe ser positivo") Long id
    ) {
        return ResponseEntity.ok(ventaService.obtenerPorIdActiva(id));
    }
    
    @PostMapping
    @Operation(summary = "Registrando una nueva venta")
    @ApiResponse(
        responseCode = "201",
        description = "Nueva venta registrada"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Datos sobre venta no encontrados",
        content = @Content(
            mediaType = "application/problem+json",
            schema = @Schema(
                    implementation = ProblemaDoc.class
            )
        )
    )
    @ApiResponse(
        responseCode = "409",
        description = "El detalle de venta ya se encuentra registrado",
        content = @Content(
                mediaType = "application/problem+json",
                schema = @Schema(
                        implementation = ProblemaDoc.class
                )
        )
    )
    public ResponseEntity<VentaResponse> registrarVenta(@Valid @RequestBody VentaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(request));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Simular eliminar una venta")
    @ApiResponse(
        responseCode = "204",
        description = "Venta eliminada"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Registro de venta no existente",
        content = @Content(
            mediaType = "application/problem+json",
            schema = @Schema(
                    implementation = ProblemaDoc.class
            )
        )
    )
    @ApiResponse(
            responseCode = "409",
            description = "Conflicto con los datos",
            content = @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(
                            implementation = ProblemaDoc.class
                    )
            )
    )
    public ResponseEntity<Void> cancelarVenta(
        @Parameter(description = "Identificador de la venta", example = "1")
        @PathVariable @Positive(message = "El identificador debe ser positivo") Long id
    ){
        ventaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
