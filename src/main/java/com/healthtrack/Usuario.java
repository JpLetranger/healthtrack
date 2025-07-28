package com.healthtrack;

public class Usuario {
    private String nombre;
    private double peso;

    public Usuario(String nombre, double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo o cero.");
        }
        this.nombre = nombre;
        this.peso = peso; 
    }

    public String getNombre() {
        return nombre;
    }

    public double getPeso() {
        return peso;
    }

    // CORRECCIÓN: Se asigna directamente el nuevo peso.
    public void actualizarPeso(double nuevoPeso) {
        if (nuevoPeso <= 0) {
            // Se añade validación para evitar pesos inválidos, negativos o cero
            return; 
        }
        this.peso = nuevoPeso;  // Lógica Corregida: this.peso = nuevoPeso; ahora asigna correctamente el valor.
    }

    public void mostrarInformacion() {
        System.out.println("Usuario: " + nombre + ", Peso Actual: " + peso + " kg");
    }
}