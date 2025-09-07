# Trabajo Final
# Proyecto: Ghostly Mansion

## 1. Integrantes del Equipo 

- Pizzonia, Carolina Belen.
- Ruiz, Lucas.
- Segurado, Matias.  
- Verdasco, Ana Victoria.

## 2. Dominio y Alcance del Sistema 

### Descripción del Problema
Se busca desarrollar un juego de escritorio tipo laberinto, inspirado en **Pac-Man** con temática de terror. El jugador se genera en una ubicación de un laberinto con una visibilidad reducida, y debe buscar una llave de una sala de escape para completar el nivel. Para realizar esto, se debe evitar diversos fantasmas los cuales en caso de entrar en contacto provocaran una pérdida de vida o de la partida.

### Objetivo del Sistema
Entregar un juego funcional y escalable, el cual permita experimentar las mecánicas de **Survival and Horror**. El diseño será modular para facilitar la adición de nuevos niveles, texturas, habilidades del jugador, radios de visión, movimiento de los fantasmas, aplicando los cuatro pilares claves de la materia (encapsulamiento, abstracción, herencia y polimorfismo).

### Funcionalidades Principales (Features)
    
- **Mapa:**
    - Los mapas cambiaran según el nivel.
    -Los mapas incluyen: 
        -Sala de escape cerca del inicio.
        -Puerta en sala de escape. 
        -Llave lejos del inicio.
        -Diversos fantasmas.
        -Paredes y caminos del laberinto.
        -Jugador.

- **Fantasmas:**
    - Se irán moviendo a lo largo del mapa. 
    - Dañan al jugador al entrar en contacto. 
    - No atraviesan paredes.

- **Jugador**
    -Posee un movimiento lineal (en eje x e y).
    -Vidas (total de 3).

- **Mecánicas de Juego:**
    - visión Reducida:  círculo de radio R = 5 celdas (este valor puede llegar a cambiar), bloqueando la vista del resto.  
    - El jugador tiene un número limitado de "vidas", que disminuyen si un fantasma entra en contacto con él. El juego termina si las vidas llegan a cero.
    -La llave desbloqueara la puerta de escape. 
    - Tutorial: laberinto (15x15) fijo para explicar mecánicas. 

- **Interfaz Gráfica (IGU):**
    -Pantallas: Usuario, Menú, Juego, Pausa, Win/Game Over.
    -visualización del personaje y de los enemigos.
    -Botones:
        -Usuario: Crear/cargar usuario.
        -Menú: Comenzar, tutorial, Salir, volumen, Puntuaciones.
        -Juego: Pausa.
        -Pausa: Continuar, salir, menú principal, volumen.
        -Win/game over: menú principal.

- **Persistencia:**
    - Sistema de guardado y carga de los tiempos más bajos en completar los niveles en una base de datos.


---------------------------------------------------------------------------------------------------------------------------------------------------

## 3. Arquitectura y Diseño 

### Patrón de Diseño Adicional: Factory Method
- **Nombre del Patrón:** **Factory Method (Método de Fábrica)**.
- **Justificación:** Se utilizará este patrón para la creación de los objetos `Enemigo`. Tendremos una clase abstracta `EnemigoFactory` con un método `crearEnemigo()`. Se crearán subclases concretas como `OrcoFactory` y `GoblinFactory` que implementarán este método para instanciar los enemigos correspondientes. Esto **desacopla** la lógica del juego (que solo necesita pedir un enemigo de un tipo) de la lógica de instanciación de cada enemigo concreto. Así, añadir un nuevo tipo de enemigo (ej. `DragonFactory`) no requerirá modificar el código que gestiona las oleadas.

### Diagramas de Diseño

#### **Diagrama de Clases UML (Conceptual)**
## 4. Stack Tecnológico 

- **Lenguaje:** Java 17
- **IDE:** Visual Studio Code
- **Base de Datos:** MySQL 8.0 (para persistencia de High Scores)
- **Framework de IGU:** Java Swing
- **Control de Versiones:** Git y GitHub Classroom

