package model;

import java.util.Objects;

/**
 * Item del laberinto.
 * Posición en MATRIZ (fila, columna) y un tipo simple en texto.
 * Por defecto usamos "KEY" para la llave, pero pueden agregarse más tipos.
 */
public final class Item {

    /** Tipo estándar para llave. */
    public static final String KEY = "KEY";

    private final int fila;       // r (row) en la matriz
    private final int columna;    // c (col) en la matriz
    private final String tipo;    // p.ej. "KEY", "POTION", etc.

    /**
     * Crea un Item.
     * @param fila    fila en la matriz (>=0)
     * @param columna columna en la matriz (>=0)
     * @param tipo    tipo lógico del item (no nulo), ej. {@link #KEY}
     */
    public Item(int fila, int columna, String tipo) {
        if (fila < 0 || columna < 0) {
            throw new IllegalArgumentException("fila/columna no pueden ser negativas");
        }
        this.fila = fila;
        this.columna = columna;
        this.tipo = Objects.requireNonNull(tipo, "tipo no puede ser null");
    }

    /** Fila (r) en la matriz. */
    public int getFila() { return fila; }

    /** Columna (c) en la matriz. */
    public int getColumna() { return columna; }

    /** Tipo del ítem (por ejemplo, {@link #KEY}). */
    public String getTipo() { return tipo; }

    /** Ayuda a chequear si este item es una llave. */
    public boolean isKey() { return KEY.equals(tipo); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return fila == item.fila && columna == item.columna && tipo.equals(item.tipo);
    }

    @Override public int hashCode() {
        return Objects.hash(fila, columna, tipo);
    }

    @Override public String toString() {
        return "Item{fila=" + fila + ", columna=" + columna + ", tipo='" + tipo + "'}";
    }
}