package dqs.modelos;

/**
 * Clase que gestiona los combates entre equipos de heroes y enemigos.
 * Administra equipos de hasta 5 personajes por bando, controla el flujo
 * de la batalla y proporciona metodos para crear, mostrar y gestionar
 * los combates del sistema RPG.
 * 
 * @author Sistema RPG
 * @version 1.0
 */
public class Batalla {

    /** Array que contiene el equipo de heroes (maximo 5 heroes) */
    private final Heroe[] equipoHeroes;
    /** Array que contiene el equipo de enemigos (maximo 5 enemigos) */
    private final Enemigo[] equipoEnemigos;
    /** Contador del turno actual de la batalla */
    private int turnoActual;
    /** Estado que indica si la batalla ha terminado */
    private boolean batallaTerminada;

    /**
     * Constructor de la clase Batalla.
     * Inicializa los arrays de equipos con capacidad para 5 personajes
     * cada uno y establece los valores iniciales de turno y estado.
     */
    public Batalla(){
        this.equipoHeroes = new Heroe[5];
        this.equipoEnemigos = new Enemigo[5];
        this.turnoActual = 0;
        this.batallaTerminada = false;
    }

    /**
     * Agrega un heroe al equipo en la posicion especificada.
     * Valida que la posicion este dentro del rango permitido (0-4).
     * 
     * @param heroe el heroe a agregar al equipo
     * @param posicion la posicion en el array donde colocar el heroe (0-4)
     * @throws IllegalArgumentException si la posicion esta fuera del rango valido
     */
    public void agregarHeroe(Heroe heroe, int posicion) {
        if (posicion >= 0 && posicion < equipoHeroes.length) {
            equipoHeroes[posicion] = heroe;
        } else {
            throw new IllegalArgumentException("Posición inválida para el equipo de héroes.");
        }
    }

    /**
     * Agrega un enemigo al equipo en la posicion especificada.
     * Valida que la posicion este dentro del rango permitido (0-4).
     * 
     * @param enemigo el enemigo a agregar al equipo
     * @param posicion la posicion en el array donde colocar el enemigo (0-4)
     * @throws IllegalArgumentException si la posicion esta fuera del rango valido
     */
    public void agregarEnemigo(Enemigo enemigo, int posicion) {
        if (posicion >= 0 && posicion < equipoEnemigos.length) {
            equipoEnemigos[posicion] = enemigo;
        } else {
            throw new IllegalArgumentException("Posición inválida para el equipo de enemigos.");
        }
    }

    /**
     * Crea un nuevo heroe mediante consola y lo agrega directamente al equipo.
     * Utiliza el metodo crearHeroePorConsola() de la clase Heroe para
     * solicitar los datos al usuario y crear el personaje.
     * 
     * @param posicion la posicion en el equipo donde agregar el heroe (0-4)
     * @throws IllegalArgumentException si la posicion esta fuera del rango valido
     */
    public void crearYAgregarHeroe(int posicion) {
        if (posicion >= 0 && posicion < equipoHeroes.length) {
            System.out.println("\n=== Creando héroe para la posición " + (posicion + 1) + " ===");
            equipoHeroes[posicion] = Heroe.crearHeroePorConsola();
            System.out.println("¡Héroe agregado exitosamente!");
        } else {
            throw new IllegalArgumentException("Posición inválida para el equipo de héroes.");
        }
    }

    /**
     * Crea un nuevo enemigo aleatorio y lo agrega directamente al equipo.
     * Selecciona aleatoriamente un tipo de enemigo y genera sus estadisticas
     * dentro de los rangos permitidos para ese tipo.
     * 
     * @param posicion la posicion en el equipo donde agregar el enemigo (0-4)
     * @throws IllegalArgumentException si la posicion esta fuera del rango valido
     */
    public void crearYAgregarEnemigo(int posicion) {
        if (posicion >= 0 && posicion < equipoEnemigos.length) {
            System.out.println("\n=== Creando enemigo para la posición " + (posicion + 1) + " ===");
            // Seleccionar un tipo de enemigo aleatorio
            Tipo_Enemigo[] tiposDisponibles = Tipo_Enemigo.values();
            int indiceAleatorio = (int)(Math.random() * tiposDisponibles.length);
            Tipo_Enemigo tipoAleatorio = tiposDisponibles[indiceAleatorio];
            
            // Crear nombre descriptivo basado en el tipo
            String nombreEnemigo = tipoAleatorio.name() + " " + (posicion + 1);
            
            equipoEnemigos[posicion] = Enemigo.crearEnemigo(tipoAleatorio, nombreEnemigo);
            System.out.println("¡Enemigo " + tipoAleatorio.name() + " agregado exitosamente!");
        } else {
            throw new IllegalArgumentException("Posición inválida para el equipo de enemigos.");
        }
    }

    /**
     * Crea un equipo completo de heroes solicitando datos por consola.
     * Itera a traves de todas las posiciones del equipo (5 heroes)
     * y crea cada heroe individualmente mediante interaccion con el usuario.
     */
    public void crearEquipoHeroes() {
        System.out.println("\n=== CREACIÓN DEL EQUIPO DE HÉROES ===");
        for (int i = 0; i < equipoHeroes.length; i++) {
            crearYAgregarHeroe(i);
        }
        System.out.println("\n¡Equipo de héroes completo!");
    }

    /**
     * Crea un equipo completo de enemigos con tipos y estadisticas aleatorias.
     * Genera automaticamente 5 enemigos con tipos seleccionados aleatoriamente
     * y estadisticas dentro de los rangos permitidos para cada tipo.
     */
    public void crearEquipoEnemigos() {
        System.out.println("\n=== CREACIÓN DEL EQUIPO DE ENEMIGOS ===");
        for (int i = 0; i < equipoEnemigos.length; i++) {
            crearYAgregarEnemigo(i);
        }
        System.out.println("\n¡Equipo de enemigos completo!");
    }

    /**
     * Muestra el estado actual de ambos equipos en la batalla.
     * Presenta una vista organizada de todos los heroes y enemigos
     * con sus estadisticas actuales. Las posiciones vacias se muestran
     * como [Vacio] para indicar espacios disponibles.
     */
    public void mostrarEquipos() {
        System.out.println("\n=== EQUIPOS DE BATALLA ===");
        
        System.out.println("\nEQUIPO DE HÉROES:");
        for (int i = 0; i < equipoHeroes.length; i++) {
            if (equipoHeroes[i] != null) {
                System.out.println((i + 1) + ". " + equipoHeroes[i].toString());
            } else {
                System.out.println((i + 1) + ". [Vacío]");
            }
        }
        
        System.out.println("\nEQUIPO DE ENEMIGOS:");
        for (int i = 0; i < equipoEnemigos.length; i++) {
            if (equipoEnemigos[i] != null) {
                System.out.println((i + 1) + ". " + equipoEnemigos[i].toString());
            } else {
                System.out.println((i + 1) + ". [Vacío]");
            }
        }
    }

    /**
     * Obtiene una copia del array de heroes del equipo.
     * @return array de heroes en el equipo
     */
    public Heroe[] getEquipoHeroes() { return equipoHeroes; }
    
    /**
     * Obtiene una copia del array de enemigos del equipo.
     * @return array de enemigos en el equipo
     */
    public Enemigo[] getEquipoEnemigos() { return equipoEnemigos; }
    
    /**
     * Verifica si la batalla ha terminado.
     * @return true si la batalla termino, false si continua
     */
    public boolean isBatallaTerminada() { return batallaTerminada; }
    
    /**
     * Obtiene el numero del turno actual de la batalla.
     * @return numero del turno actual
     */
    public int getTurnoActual() { return turnoActual; }

    /**
     * Establece el estado de finalizacion de la batalla.
     * @param batallaTerminada true para marcar la batalla como terminada
     */
    public void setBatallaTerminada(boolean batallaTerminada){
         this.batallaTerminada = batallaTerminada; }
         
    /**
     * Establece el numero del turno actual de la batalla.
     * @param turnoActual el nuevo numero de turno
     */
    public void setTurnoActual(int turnoActual) {
         this.turnoActual = turnoActual; }

}
