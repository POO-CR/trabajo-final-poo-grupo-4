package model.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Record {

    private String nombre;
    private long tiempoMs; // Almacena el tiempo total en milisegundos
    private LocalDateTime fecha;

    // Constructor usado para crear un nuevo récord
    public Record(String nombre, long tiempoMs) {
        this.nombre = nombre;
        this.tiempoMs = tiempoMs;
        this.fecha = LocalDateTime.now(); // Fija la fecha y hora de creación
    }
    
    // Constructor usado para recuperar un record desde la BD (si se lee la fecha)
    public Record(String nombre, long tiempoMs, LocalDateTime fecha) {
        this.nombre = nombre;
        this.tiempoMs = tiempoMs;
        this.fecha = fecha;
    }

    // Metodo para formato de tiempo (util para la interfaz/ranking)
    public String getTiempoFormato() {
        long totalSegundos = tiempoMs / 1000;
        long horas = totalSegundos / 3600;
        long minutos = (totalSegundos % 3600) / 60;
        long segundosRestantes = totalSegundos % 60;
        //long milis = tiempoMs % 1000;

       // Opcional: Si el tiempo es tan corto que solo hay segundos, muestra solo S.
        if (horas == 0 && minutos == 0) {
        // Formato: 5 S (Ejemplo)
        return String.format("%dS", segundosRestantes);
        }
    
        // Si hay minutos o más, usa el formato completo.
        // Formato: 0 H 4 M 55 S (Ejemplo para 295 segundos)
        return String.format("%dH %dM %dS", horas, minutos, segundosRestantes);
    }
    
    // Metodo para formato de fecha
    public String getFechaFormato() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fecha.format(formatter);
    }

    // --- Getters ---
    public String getNombre() {
        return nombre;
    }

    public long getTiempoMs() {
        return tiempoMs;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
