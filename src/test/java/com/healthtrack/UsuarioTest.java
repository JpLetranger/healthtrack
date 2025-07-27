package com.healthtrack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// contiene las pruebas unitarias.

public class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Se crea un nuevo usuario antes de cada prueba para asegurar el aislamiento.
        usuario = new Usuario("Ana", 70.0);
    }

    @Test
    @DisplayName("Prueba que el peso se actualiza correctamente")
    void testActualizarPesoCorrectamente() {
        // 1. Arrange (Preparar)
        double nuevoPeso = 68.5;
        
        // 2. Act (Actuar)
        usuario.actualizarPeso(nuevoPeso);

        // 3. Assert (Verificar)
        assertEquals(nuevoPeso, usuario.getPeso(), "El peso del usuario no se actualizó como se esperaba.");
    }

    @Test
    @DisplayName("Prueba que el método ignora una actualización a peso negativo")
    void testIgnorarPesoNegativo() {
        // 1. Arrange
        double pesoOriginal = usuario.getPeso(); // 70.0
        double nuevoPesoInvalido = -55.0;

        // 2. Act
        usuario.actualizarPeso(nuevoPesoInvalido);

        // 3. Assert
        assertEquals(pesoOriginal, usuario.getPeso(), "El peso no debería cambiar si el nuevo peso es inválido.");
    }
}