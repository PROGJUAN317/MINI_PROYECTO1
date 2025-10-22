package dqs.modelos;

public class Batalla {

    private final Heroe[] equipoHeroes;
    private final Enemigo[] equipoEnemigos;
    private int turnoActual;
    private boolean batallaTerminada;

    // Constructor
    public Batalla(){
        this.equipoHeroes = new Heroe[5];
        this.equipoEnemigos = new Enemigo[5];
        this.turnoActual = 0;
        this.batallaTerminada = false;
    }

    // metodos para agregar heroes y enemigos al equipo
    public void agregarHeroe(Heroe heroe, int posicion) {
        if (posicion >= 0 && posicion < equipoHeroes.length) {
            equipoHeroes[posicion] = heroe;
        } else {
            throw new IllegalArgumentException("Posición inválida para el equipo de héroes.");
        }
    }

    public void agregarEnemigo(Enemigo enemigo, int posicion) {
        if (posicion >= 0 && posicion < equipoEnemigos.length) {
            equipoEnemigos[posicion] = enemigo;
        } else {
            throw new IllegalArgumentException("Posición inválida para el equipo de enemigos.");
        }
    }

    // Método para crear y agregar héroes directamente al arreglo
    public void crearYAgregarHeroe(int posicion) {
        if (posicion >= 0 && posicion < equipoHeroes.length) {
            System.out.println("\n=== Creando héroe para la posición " + (posicion + 1) + " ===");
            equipoHeroes[posicion] = Heroe.crearHeroePorConsola();
            System.out.println("¡Héroe agregado exitosamente!");
        } else {
            throw new IllegalArgumentException("Posición inválida para el equipo de héroes.");
        }
    }

    // Método para crear y agregar enemigos directamente al arreglo
    public void crearYAgregarEnemigo(int posicion) {
        if (posicion >= 0 && posicion < equipoEnemigos.length) {
            System.out.println("\n=== Creando enemigo para la posición " + (posicion + 1) + " ===");
            // Usar el primer tipo disponible como valor por defecto y un nombre generado automáticamente.
            equipoEnemigos[posicion] = Enemigo.crearEnemigo(Tipo_Enemigo.values()[0], "Enemigo " + (posicion + 1));
            System.out.println("¡Enemigo agregado exitosamente!");
        } else {
            throw new IllegalArgumentException("Posición inválida para el equipo de enemigos.");
        }
    }

    // Método para crear todo el equipo de héroes
    public void crearEquipoHeroes() {
        System.out.println("\n=== CREACIÓN DEL EQUIPO DE HÉROES ===");
        for (int i = 0; i < equipoHeroes.length; i++) {
            crearYAgregarHeroe(i);
        }
        System.out.println("\n¡Equipo de héroes completo!");
    }

    // Método para crear todo el equipo de enemigos
    public void crearEquipoEnemigos() {
        System.out.println("\n=== CREACIÓN DEL EQUIPO DE ENEMIGOS ===");
        for (int i = 0; i < equipoEnemigos.length; i++) {
            crearYAgregarEnemigo(i);
        }
        System.out.println("\n¡Equipo de enemigos completo!");
    }

    // Método para mostrar los equipos
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

    // Getters
    public Heroe[] getEquipoHeroes() { return equipoHeroes; }
    public Enemigo[] getEquipoEnemigos() { return equipoEnemigos; }
    public boolean isBatallaTerminada() { return batallaTerminada; }
    public int getTurnoActual() { return turnoActual; }

    // Setters
    public void setBatallaTerminada(boolean batallaTerminada){
         this.batallaTerminada = batallaTerminada; }
         
    public void setTurnoActual(int turnoActual) {
         this.turnoActual = turnoActual; }

    // Métodos para el sistema de velocidades
    
    private java.util.List<Personaje> obtenerOrdenPorVelocidad(Heroe[] heroes, Enemigo[] enemigos) {
        java.util.List<Personaje> participantes = new java.util.ArrayList<>();
        for (Heroe h : heroes) if (h != null && h.esta_vivo()) participantes.add(h);
        for (Enemigo e : enemigos) if (e != null && e.esta_vivo()) participantes.add(e);

        // Ordenar por velocidad descendente; si empate, tie-breaker: héroes antes que enemigos, luego random
        java.util.Collections.sort(participantes, (a, b) -> {
            int diff = Integer.compare(b.getVelocidad(), a.getVelocidad()); // descendente
            if (diff != 0) return diff;
            // tie-breaker: héroes antes que enemigos
            if (a instanceof Heroe && !(b instanceof Heroe)) return -1;
            if (!(a instanceof Heroe) && b instanceof Heroe) return 1;
            // por defecto, azar
            return Double.compare(Math.random(), Math.random());
        });

        return participantes;
    }
    
    public boolean ejecutarTurnoPorVelocidad(Heroe[] heroes, Enemigo[] enemigos, boolean modoManual) {
        java.util.List<Personaje> orden = obtenerOrdenPorVelocidad(heroes, enemigos);
        System.out.println("Orden de acción para este turno:");
        for (Personaje p : orden) {
            System.out.println(" - " + p.getNombre() + " (vel=" + p.getVelocidad() + ")");
        }

        for (Personaje actor : orden) {
            if (actor == null || !actor.esta_vivo()) continue;
            if (actor.estaParalizado()) {
                System.out.println(actor.getNombre() + " está paralizado y salta su turno."); 
                continue;
            }

            if (actor instanceof Heroe) {
                Heroe h = (Heroe) actor;
                if (modoManual) {
                    // En modo manual, se requiere interacción del usuario (delegado a App)
                    // Este método solo se usa en modo automático para pruebas
                    Enemigo objetivo = elegirEnemigoVivo(enemigos);
                    if (objetivo != null) h.atacar(objetivo);
                } else {
                    // IA simple: atacar al primer enemigo vivo
                    Enemigo objetivo = elegirEnemigoVivo(enemigos);
                    if (objetivo != null) h.atacar(objetivo);
                }
            } else if (actor instanceof Enemigo) {
                Enemigo e = (Enemigo) actor;
                // Si está provocado, atacar al provocador
                if (e.estaProvocado() && e.getProvocador() != null && e.getProvocador().esta_vivo()) {
                    e.atacar(e.getProvocador());
                    continue;
                }
                // Comportamiento enemigo: atacar héroe vivo aleatorio
                Heroe objetivo = elegirHeroeVivo(heroes);
                if (objetivo != null) {
                    e.atacar(objetivo);
                }
            }
        }

        // Al final del turno, decrementar estados globalmente
        for (Heroe h : heroes) if (h != null) h.decrementarEstadosPorTurno();
        for (Enemigo e : enemigos) if (e != null) e.decrementarEstadosPorTurno();

        // comprobar victoria/derrota
        boolean heroesVivos = hayHeroesVivos(heroes);
        boolean enemigosVivos = hayEnemigosVivos(enemigos);
        if (!heroesVivos) {
            System.out.println("Derrota: todos los héroes han muerto.");
            return false;
        }
        if (!enemigosVivos) {
            System.out.println("Victoria: todos los enemigos han sido derrotados.");
            return true;
        }
        // si la batalla no terminó, retorna null (indicado con false aquí por simplicidad)
        return false; // batalla continúa
    }
    
    private boolean hayHeroesVivos(Heroe[] heroes) {
        for (Heroe h : heroes) if (h != null && h.esta_vivo()) return true;
        return false;
    }
    
    private boolean hayEnemigosVivos(Enemigo[] enemigos) {
        for (Enemigo e : enemigos) if (e != null && e.esta_vivo()) return true;
        return false;
    }
    
    private Enemigo elegirEnemigoVivo(Enemigo[] enemigos) {
        for (Enemigo e : enemigos) if (e != null && e.esta_vivo()) return e;
        return null;
    }
    
    private Heroe elegirHeroeVivo(Heroe[] heroes) {
        for (Heroe h : heroes) if (h != null && h.esta_vivo()) return h;
        return null;
    }

}
