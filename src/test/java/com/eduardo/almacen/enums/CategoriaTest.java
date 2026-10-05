package com.eduardo.almacen.enums;

import com.eduardo.almacen.exceptions.DatoInvalidoExeption;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CategoriaTest {
    @Test
    void obtenerCategoriaPorDescripcion_debeEncontrarCategoria_ignorandoAcentosYMayusculas() {
        // Act
        Categoria categoria = Categoria.obtenetCategoriaPorDescripcion("eleCtróNICa");

        // Assert
        assertThat(categoria).isEqualTo(Categoria.ELECTRONICA);
    }

    @Test
    void obtenerCategoriaPorDescripcion_debeEncontrarCategoria_conMayusculasYTilde() {
        Categoria categoria = Categoria.obtenetCategoriaPorDescripcion("ELECTRÓNICA");

        assertThat(categoria).isEqualTo(Categoria.ELECTRONICA);
    }

    @Test
    void obtenerCategoriaPorDescripcion_debeLanzarExcepcion_cuandoNoExisteLaCategoria() {
        assertThatThrownBy(() -> Categoria.obtenetCategoriaPorDescripcion("Mueble"))
                .isInstanceOf(DatoInvalidoExeption.class)
                .hasMessageContaining("No existe una categoria con la descripcion: Mueble");
    }

    @Test
    void obtenerCategoriaPorDescripcion_debeLanzarExcepcion_cuandoLaDescripcionEsVacia() {
        assertThatThrownBy(() -> Categoria.obtenetCategoriaPorDescripcion(null))
                .isInstanceOf(DatoInvalidoExeption.class)
                .hasMessage("La descripcion es requerida");
    }
}
