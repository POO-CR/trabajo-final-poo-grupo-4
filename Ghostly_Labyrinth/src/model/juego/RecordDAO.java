package model.juego;
import java.util.List;

public interface RecordDAO {

    /**
     * Guarda un nuevo récord de juego en la base de datos.
     * @param record El objeto Record a guardar.
     */
    void save(Record record);

    /**
     * Obtiene una lista de los mejores récords (ej: Top 10) de la BD.
     * @return Lista de objetos Record ordenados por tiempo (ascendente).
     */
    List<Record> getTop10Records();

    
}
