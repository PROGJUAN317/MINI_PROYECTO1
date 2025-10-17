package dqs.main;

import dqs.modelos.*;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Batalla batalla = new Batalla();

    public static void main(String[] args) {
        System.out.println("  ¡Bienvenido al Sistema de Batallas RPG!");
        System.out.println("==========================================");
        
        mostrarMenuPrincipal();
    }
    
    private static void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Crear Equipos");
            System.out.println("2. Mostrar Equipos");
            System.out.println("3. Iniciar Batalla");
            System.out.println("4. Prueba de Mecánicas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerEntero();
            
            switch (opcion) {
                case 1 -> menuCrearEquipos();
                case 2 -> batalla.mostrarEquipos();
                case 3 -> iniciarBatalla();
                case 4 -> menuPruebaMecanicas();
                case 5 -> {
                    System.out.println("¡Gracias por jugar! ");
                    System.exit(0);
                }
                default -> System.out.println(" Opción inválida. Intente de nuevo.");
            }
        }
    }
    
    private static void menuCrearEquipos() {
        System.out.println("\n=== CREACIÓN DE EQUIPOS ===");
        System.out.println("1. Crear Equipo de Héroes");
        System.out.println("2. Crear Equipo de Enemigos");
        System.out.println("3. Crear Héroe Individual");
        System.out.println("4. Crear Enemigo Individual");
        System.out.println("5. Volver al Menú Principal");
        System.out.print("Seleccione una opción: ");
        
        int opcion = leerEntero();
        
        switch (opcion) {
            case 1 -> batalla.crearEquipoHeroes();
            case 2 -> batalla.crearEquipoEnemigos();
            case 3 -> crearHeroeIndividual();
            case 4 -> crearEnemigoIndividual();
            default -> System.out.println(" Opción inválida.");
        }
    }
    
    private static void crearHeroeIndividual() {
        System.out.print("Ingrese la posición (1-5): ");
        int posicion = leerEntero() - 1;
        
        if (posicion >= 0 && posicion < 5) {
            batalla.crearYAgregarHeroe(posicion);
        } else {
            System.out.println(" Posición inválida.");
        }
    }
    
    private static void crearEnemigoIndividual() {
        System.out.print("Ingrese la posición (1-5): ");
        int posicion = leerEntero() - 1;
        
        if (posicion >= 0 && posicion < 5) {
            System.out.println("Seleccione el tipo de enemigo:");
            Tipo_Enemigo[] tipos = Tipo_Enemigo.values();
            for (int i = 0; i < tipos.length; i++) {
                System.out.println((i + 1) + ". " + tipos[i].name() + " - " + tipos[i].getDescripcion());
            }
            System.out.print("Tipo: ");
            int tipoIndex = leerEntero() - 1;
            
            if (tipoIndex >= 0 && tipoIndex < tipos.length) {
                System.out.print("Nombre del enemigo: ");
                String nombre = scanner.nextLine();
                Enemigo enemigo = Enemigo.crearEnemigo(tipos[tipoIndex], nombre);
                batalla.agregarEnemigo(enemigo, posicion);
                System.out.println(" Enemigo creado exitosamente!");
                enemigo.mostrarEstado();
            } else {
                System.out.println(" Tipo inválido.");
            }
        } else {
            System.out.println(" Posición inválida.");
        }
    }
    
    private static void iniciarBatalla() {
        // Verificar que ambos equipos tengan al menos un miembro
        boolean hayHeroes = false, hayEnemigos = false;
        
        for (Heroe heroe : batalla.getEquipoHeroes()) {
            if (heroe != null) { hayHeroes = true; break; }
        }
        
        for (Enemigo enemigo : batalla.getEquipoEnemigos()) {
            if (enemigo != null) { hayEnemigos = true; break; }
        }
        
        if (!hayHeroes || !hayEnemigos) {
            System.out.println(" Ambos equipos deben tener al menos un miembro para iniciar la batalla.");
            return;
        }
        
        System.out.println("\n ¡LA BATALLA COMIENZA! ");
        batalla.mostrarEquipos();
        
        simulacionDeBatalla();
    }
    
    private static void simulacionDeBatalla() {
        int turno = 1;
        
        while (!batalla.isBatallaTerminada()) {
            System.out.println("\n=== TURNO " + turno + " ===");
            mostrarEstadoActual();
            
            // Turno de los héroes (MANUAL)
            System.out.println("\n--- Turno de los Héroes ---");
            turnoHeroesManual();
            
            if (verificarVictoria()) break;
            
            // Turno de los enemigos (automático pero con pausa)
            System.out.println("\n--- Turno de los Enemigos ---");
            System.out.println("Presione Enter para continuar con el turno de los enemigos...");
            scanner.nextLine();
            
            // Ejecutar acciones de enemigos con un scheduler para evitar bloquear el hilo principal
            Enemigo[] enemigos = batalla.getEquipoEnemigos();
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            long delayMs = 0L;
            final long stepMs = 1000L; // pausa entre acciones

            for (Enemigo enemigo : enemigos) {
                if (enemigo != null && enemigo.esta_vivo()) {
                    Enemigo eFinal = enemigo; // para usar dentro de la lambda
                    scheduler.schedule(() -> {
                        System.out.println("\n" + eFinal.getNombre() + " está actuando...");
                        eFinal.atacarConProvocacion(convertirHeroesAPersonajes(batalla.getEquipoHeroes()));
                    }, delayMs, TimeUnit.MILLISECONDS);
                    delayMs += stepMs;
                }
            }

            // esperar a que terminen todas las tareas programadas
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(delayMs + 500, TimeUnit.MILLISECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                scheduler.shutdownNow();
            }
            
            if (verificarVictoria()) break;
            
            turno++;
            
            if (turno > 50) { // Límite de seguridad
                System.out.println(" ¡La batalla ha durado demasiado! Es un empate.");
                break;
            }
            
            System.out.println("\nPresione Enter para continuar al siguiente turno...");
            scanner.nextLine();
        }
    }
    
    private static void mostrarEstadoActual() {
        System.out.println("\n ESTADO ACTUAL DE LA BATALLA:");
        
        System.out.println("\n HÉROES VIVOS:");
        for (int i = 0; i < batalla.getEquipoHeroes().length; i++) {
            Heroe heroe = batalla.getEquipoHeroes()[i];
            if (heroe != null && heroe.esta_vivo()) {
                System.out.println((i + 1) + ". " + heroe.getNombre() + " [" + heroe.getTipo().name() + "] " +
                                 "HP: " + heroe.getHp() + " | MP: " + heroe.getMp());
            }
        }
        
        System.out.println("\n ENEMIGOS VIVOS:");
        for (int i = 0; i < batalla.getEquipoEnemigos().length; i++) {
            Enemigo enemigo = batalla.getEquipoEnemigos()[i];
            if (enemigo != null && enemigo.esta_vivo()) {
                System.out.println((i + 1) + ". " + enemigo.getNombre() + " [" + enemigo.getTipo().name() + "] " +
                                 "HP: " + enemigo.getHp() + " | MP: " + enemigo.getMp());
            }
        }
    }
    
    private static void turnoHeroesManual() {
        for (Heroe heroe : batalla.getEquipoHeroes()) {
            if (heroe != null && heroe.esta_vivo()) {
                System.out.println("\n Es el turno de: " + heroe.getNombre() + " [" + heroe.getTipo().name() + "]");
                System.out.println("HP: " + heroe.getHp() + " | MP: " + heroe.getMp());
                
                mostrarMenuAccionHeroe(heroe);
                
                if (verificarVictoria()) break;
            }
        }
    }
    
    private static void mostrarMenuAccionHeroe(Heroe heroe) {
        while (true) {
            System.out.println("\n¿Qué acción desea realizar?");
            System.out.println("1. Atacar Enemigo");
            
            // Opciones específicas por tipo
            if (heroe.getTipo() == Tipo_Heroe.GUERRERO || heroe.getTipo() == Tipo_Heroe.PALADIN) {
                System.out.println("2. Defender Aliado");
                System.out.println("3. Provocar Enemigo");
                System.out.println("4. Aumentar Defensa");
            }
            
            if (heroe.getTipo() == Tipo_Heroe.DRUIDA || heroe.getTipo() == Tipo_Heroe.PALADIN) {
                System.out.println("5. Curar Aliado");
                System.out.println("6. Restaurar Mana");
                System.out.println("7. Eliminar Efecto Negativo");
            }
            
            if (heroe.getTipo() == Tipo_Heroe.PALADIN) {
                System.out.println("8. Revivir Aliado");
            }
            
            System.out.println("9. Pasar Turno");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerEntero();
            
            if (ejecutarAccionHeroe(heroe, opcion)) {
                break; // Salir del bucle cuando se ejecute una acción válida
            }
        }
    }
    
    private static boolean ejecutarAccionHeroe(Heroe heroe, int opcion) {
        switch (opcion) {
            case 1 -> {
                // Atacar
                return atacarConHeroe(heroe);
            }
            case 2 -> {
                // Defender (solo Guerrero/Paladin)
                if (heroe.getTipo() == Tipo_Heroe.GUERRERO || heroe.getTipo() == Tipo_Heroe.PALADIN) {
                    return defenderConHeroe(heroe);
                }
            }
            case 3 -> {
                // Provocar (solo Guerrero/Paladin)
                if (heroe.getTipo() == Tipo_Heroe.GUERRERO || heroe.getTipo() == Tipo_Heroe.PALADIN) {
                    return provocarConHeroe(heroe);
                }
            }
            case 4 -> {
                // Aumentar Defensa (solo Guerrero/Paladin)
                if (heroe.getTipo() == Tipo_Heroe.GUERRERO || heroe.getTipo() == Tipo_Heroe.PALADIN) {
                    heroe.aumentarDefensa(10);
                    return true;
                }
            }
            case 5 -> {
                // Curar (solo Druida/Paladin)
                if (heroe.getTipo() == Tipo_Heroe.DRUIDA || heroe.getTipo() == Tipo_Heroe.PALADIN) {
                    return curarConHeroe(heroe);
                }
            }
            case 6 -> {
                // Restaurar Mana (solo Druida)
                if (heroe.getTipo() == Tipo_Heroe.DRUIDA) {
                    return restaurarManaConHeroe(heroe);
                }
            }
            case 7 -> {
                // Eliminar Efecto (solo Druida/Paladin)
                if (heroe.getTipo() == Tipo_Heroe.DRUIDA || heroe.getTipo() == Tipo_Heroe.PALADIN) {
                    return eliminarEfectoConHeroe(heroe);
                }
            }
            case 8 -> {
                // Revivir (solo Paladin)
                if (heroe.getTipo() == Tipo_Heroe.PALADIN) {
                    return revivirConHeroe(heroe);
                }
            }
            case 9 -> {
                // Pasar turno
                System.out.println(heroe.getNombre() + " pasa su turno.");
                return true;
            }
        }
        
        System.out.println(" Opción inválida o no disponible para este tipo de héroe.");
        return false;
    }
    
    private static boolean verificarVictoria() {
        boolean heroesVivos = false, enemigosVivos = false;
        
        for (Heroe heroe : batalla.getEquipoHeroes()) {
            if (heroe != null && heroe.esta_vivo()) {
                heroesVivos = true;
                break;
            }
        }
        
        for (Enemigo enemigo : batalla.getEquipoEnemigos()) {
            if (enemigo != null && enemigo.esta_vivo()) {
                enemigosVivos = true;
                break;
            }
        }
        
        if (!heroesVivos) {
            System.out.println("\n ¡Los Enemigos han ganado la batalla!");
            batalla.setBatallaTerminada(true);
            return true;
        } else if (!enemigosVivos) {
            System.out.println("\n ¡Los Héroes han ganado la batalla!");
            batalla.setBatallaTerminada(true);
            return true;
        }
        
        return false;
    }
    
    private static void menuPruebaMecanicas() {
        System.out.println("\n=== PRUEBA DE MECÁNICAS ===");
        System.out.println("1. Prueba de Defensa del Tanque");
        System.out.println("2. Prueba de Provocación");
        System.out.println("3. Prueba de Curación");
        System.out.println("4. Volver al Menú Principal");
        System.out.print("Seleccione una opción: ");
        
        int opcion = leerEntero();
        
        switch (opcion) {
            case 1 -> pruebaDefensaTanque();
            case 2 -> pruebaProvocacion();
            case 3 -> pruebaCuracion();
            default -> System.out.println(" Opción inválida.");
        }
    }
    
    private static void pruebaDefensaTanque() {
        System.out.println("\n PRUEBA DE DEFENSA DEL TANQUE ");
        
    // Crear personajes de prueba
        Heroe tanque = new Heroe("Tanque", Tipo_Heroe.GUERRERO, 200, 50, 40, 30, 15);
        Heroe mago = new Heroe("Mago", Tipo_Heroe.MAGO, 80, 150, 35, 15, 20);
        Enemigo enemigo = Enemigo.crearEnemigo(Tipo_Enemigo.ORCO, "Orco Feroz");
        
        System.out.println("Antes de la defensa:");
        mago.mostrarEstado();
        
        // El tanque defiende al mago
        tanque.defender(mago);
        
        System.out.println("\nEl enemigo ataca al mago defendido:");
        enemigo.atacar(mago);
        
        System.out.println("\nDespués del ataque:");
        mago.mostrarEstado();
    }
    
    private static void pruebaProvocacion() {
        System.out.println("\n PRUEBA DE PROVOCACIÓN ");
        
        // Crear personajes de prueba
        Heroe tanque = new Heroe("Tanque", Tipo_Heroe.PALADIN, 180, 80, 35, 35, 18);
        Heroe mago = new Heroe("Mago", Tipo_Heroe.MAGO, 80, 150, 50, 15, 20);
        Enemigo enemigo = Enemigo.crearEnemigo(Tipo_Enemigo.TROLL, "Troll Gigante");
        
        Personaje[] heroes = {tanque, mago};
        
        System.out.println("Sin provocación - el enemigo puede atacar a cualquiera:");
        enemigo.seleccionarObjetivo(heroes);
        
        // El tanque provoca al enemigo
        tanque.provocarEnemigo(enemigo);
        
        System.out.println("\nCon provocación - el enemigo DEBE atacar al tanque:");
        enemigo.atacarConProvocacion(heroes);
    }
    
    private static void pruebaCuracion() {
        System.out.println("\n PRUEBA DE CURACIÓN ");
        
        // Crear personajes de prueba
        Heroe sanador = new Heroe("Druida", Tipo_Heroe.DRUIDA, 120, 200, 30, 25, 18);
        Heroe herido = new Heroe("Guerrero", Tipo_Heroe.GUERRERO, 50, 30, 45, 30, 15); // HP bajo
        
        System.out.println("Antes de la curación:");
        herido.mostrarEstado();
        
        sanador.curar(herido);
        
        System.out.println("\nDespués de la curación:");
        herido.mostrarEstado();
    }
    
    // Método auxiliar para convertir Heroe[] a Personaje[]
    private static Personaje[] convertirHeroesAPersonajes(Heroe[] heroes) {
        Personaje[] personajes = new Personaje[heroes.length];
        System.arraycopy(heroes, 0, personajes, 0, heroes.length);
        return personajes;
    }
    
    // Métodos para acciones específicas de héroes
    private static boolean atacarConHeroe(Heroe heroe) {
        System.out.println("\n Seleccione el enemigo a atacar:");
        Enemigo objetivo = seleccionarEnemigo();
        if (objetivo != null) {
            heroe.atacar(objetivo);
            return true;
        }
        return false;
    }
    
    private static boolean defenderConHeroe(Heroe heroe) {
        System.out.println("\n Seleccione el aliado a defender:");
        Heroe aliado = seleccionarHeroe();
        if (aliado != null && aliado != heroe) {
            heroe.defender(aliado);
            return true;
        } else if (aliado == heroe) {
            System.out.println(" No puedes defenderte a ti mismo.");
        }
        return false;
    }
    
    private static boolean provocarConHeroe(Heroe heroe) {
        System.out.println("\n Seleccione el enemigo a provocar:");
        Enemigo enemigo = seleccionarEnemigo();
        if (enemigo != null) {
            heroe.provocarEnemigo(enemigo);
            return true;
        }
        return false;
    }
    
    private static boolean curarConHeroe(Heroe heroe) {
        System.out.println("\n Seleccione el aliado a curar:");
        Heroe aliado = seleccionarHeroe();
        if (aliado != null) {
            heroe.curar(aliado);
            return true;
        }
        return false;
    }
    
    private static boolean restaurarManaConHeroe(Heroe heroe) {
        System.out.println("\n Seleccione el aliado para restaurar mana:");
        Heroe aliado = seleccionarHeroe();
        if (aliado != null) {
            heroe.restaurarMana(aliado);
            return true;
        }
        return false;
    }
    
    private static boolean eliminarEfectoConHeroe(Heroe heroe) {
        System.out.println("\n Seleccione el aliado para eliminar efectos negativos:");
        Heroe aliado = seleccionarHeroe();
        if (aliado != null) {
            heroe.eliminarEfectoNegativo(aliado);
            return true;
        }
        return false;
    }
    
    private static boolean revivirConHeroe(Heroe heroe) {
        System.out.println("\n Seleccione el aliado a revivir:");
        Heroe aliado = seleccionarHeroeMuerto();
        if (aliado != null) {
            heroe.revivir(aliado);
            return true;
        }
        return false;
    }
    
    private static Enemigo seleccionarEnemigo() {
        System.out.println("Enemigos disponibles:");
        int contador = 1;
        for (Enemigo enemigo : batalla.getEquipoEnemigos()) {
            if (enemigo != null && enemigo.esta_vivo()) {
                System.out.println(contador + ". " + enemigo.getNombre() + " [" + enemigo.getTipo().name() + "] " +
                        "HP: " + enemigo.getHp());
                contador++;
            }
        }
        System.out.println(contador + ". Cancelar");
        System.out.print("Seleccione: ");
        
        int opcion = leerEntero();
        if (opcion == contador) return null; // Cancelar
        
        // Buscar el enemigo seleccionado
        contador = 1;
        for (Enemigo enemigo : batalla.getEquipoEnemigos()) {
            if (enemigo != null && enemigo.esta_vivo()) {
                if (contador == opcion) {
                    return enemigo;
                }
                contador++;
            }
        }
        
        System.out.println(" Selección inválida.");
        return null;
    }
    
    private static Heroe seleccionarHeroe() {
        System.out.println("Héroes disponibles:");
        int contador = 1;
        for (Heroe heroe : batalla.getEquipoHeroes()) {
            if (heroe != null && heroe.esta_vivo()) {
                System.out.println(contador + ". " + heroe.getNombre() + " [" + heroe.getTipo().name() + "] " +
                        "HP: " + heroe.getHp() + " | MP: " + heroe.getMp());
                contador++;
            }
        }
        System.out.println(contador + ". Cancelar");
        System.out.print("Seleccione: ");
        
        int opcion = leerEntero();
        if (opcion == contador) return null; // Cancelar
        
        // Buscar el héroe seleccionado
        contador = 1;
        for (Heroe heroe : batalla.getEquipoHeroes()) {
            if (heroe != null && heroe.esta_vivo()) {
                if (contador == opcion) {
                    return heroe;
                }
                contador++;
            }
        }
        
        System.out.println(" Selección inválida.");
        return null;
    }
    
    private static Heroe seleccionarHeroeMuerto() {
        System.out.println("Héroes caídos:");
        int contador = 1;
        for (Heroe heroe : batalla.getEquipoHeroes()) {
            if (heroe != null && !heroe.esta_vivo()) {
                System.out.println(contador + ". " + heroe.getNombre() + " [" + heroe.getTipo().name() + "] " +
                        "HP: " + heroe.getHp());
                contador++;
            }
        }
        
        if (contador == 1) {
            System.out.println("No hay héroes caídos para revivir.");
            return null;
        }
        
        System.out.println(contador + ". Cancelar");
        System.out.print("Seleccione: ");
        
        int opcion = leerEntero();
        if (opcion == contador) return null; // Cancelar
        
        // Buscar el héroe muerto seleccionado
        contador = 1;
        for (Heroe heroe : batalla.getEquipoHeroes()) {
            if (heroe != null && !heroe.esta_vivo()) {
                if (contador == opcion) {
                    return heroe;
                }
                contador++;
            }
        }
        
        System.out.println(" Selección inválida.");
        return null;
    }
    
    private static int leerEntero() {
        while (true) {
            try {
                // Si no hay más líneas (entrada cerrada), salir de forma limpia
                if (!scanner.hasNextLine()) {
                    System.out.println("\nNo hay entrada disponible. Terminando la aplicación.");
                    System.exit(0);
                }
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print(" Ingrese un número válido: ");
            }
        }
    }
}
