package com.healthtrack;

import io.javalin.Javalin;
import java.util.HashMap;
import java.util.Map;

public class ApiServer {

    // Usamos un objeto Usuario para mantener el estado (el peso)
    private static Usuario usuarioDePrueba = new Usuario("Carlos", 80.0);

    // Constructor privado para evitar instanciación (clase utilitaria)
    private ApiServer() {
        throw new IllegalStateException("Utility class");
    }

    public static void main(String[] args) {
        startServer();
    }

    // Método separado para facilitar testing y mejorar la estructura
    public static void startServer() {
        // Se crea y configura el servidor para que se ejecute en el puerto 5500
        try (Javalin app = Javalin.create()) {
            app.start(5500);
            
            System.out.println("Servidor API de HealthTrack escuchando en http://localhost:5500");

            // --- Definición de Endpoints ---
            setupEndpoints(app);
            
            // Mantener el servidor corriendo
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Cerrando servidor...");
                app.stop();
            }));
            
            // Mantener el hilo principal vivo
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    // Método separado para configurar endpoints (mejora la legibilidad y testing)
    private static void setupEndpoints(Javalin app) {
        // 1. Endpoint GET para obtener el peso actual de un usuario
        // Responde a peticiones como: http://localhost:5500/api/usuario/1/peso
        app.get("/api/usuario/{id}/peso", ctx -> {
            System.out.println("Petición GET recibida para el usuario: " + ctx.pathParam("id"));
            
            Map<String, Object> response = new HashMap<>();
            response.put("usuarioId", ctx.pathParam("id"));
            response.put("pesoActual", usuarioDePrueba.getPeso());
            
            // ctx.json() convierte el mapa a una respuesta JSON automáticamente
            ctx.json(response);
        });

        // 2. Endpoint PUT para actualizar el peso de un usuario
        // Responde a peticiones PUT a: http://localhost:5500/api/usuario/1/peso
        app.put("/api/usuario/{id}/peso", ctx -> {
            try {
                // ctx.bodyAsClass() convierte el cuerpo JSON de la petición a un objeto
                Map<String, Double> requestBody = ctx.bodyAsClass(Map.class);
                Double nuevoPesoObj = requestBody.get("nuevoPeso");
                
                if (nuevoPesoObj == null) {
                    ctx.status(400).json(Map.of("error", "Campo 'nuevoPeso' es requerido"));
                    return;
                }
                
                double nuevoPeso = nuevoPesoObj;
                
                System.out.println(
                    "Petición PUT recibida para el usuario: " + ctx.pathParam("id") + 
                    " con nuevo peso: " + nuevoPeso
                );

                // Actualizamos el peso en nuestro objeto de prueba
                usuarioDePrueba.actualizarPeso(nuevoPeso);

                Map<String, Object> response = new HashMap<>();
                response.put("status", "éxito");
                response.put("mensaje", "Peso actualizado a " + usuarioDePrueba.getPeso() + " kg.");
                
                ctx.json(response);
                
            } catch (Exception e) {
                ctx.status(400).json(Map.of("error", "Error al procesar la petición: " + e.getMessage()));
            }
        });
    }
}