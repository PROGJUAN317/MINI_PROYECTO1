package dqs.main;

import dqs.modelos.*;

public class PruebaEnemigosAleatorios {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE ENEMIGOS ALEATORIOS ===\n");
        
        // Crear una batalla y generar varios equipos para ver la aleatoriedad
        for (int equipo = 1; equipo <= 3; equipo++) {
            System.out.println("🎲 EQUIPO DE ENEMIGOS #" + equipo + ":");
            Batalla batalla = new Batalla();
            
            // Crear 5 enemigos aleatorios
            for (int i = 0; i < 5; i++) {
                batalla.crearYAgregarEnemigo(i);
            }
            
            // Mostrar el equipo creado
            System.out.println("\nEnemigos generados:");
            for (int i = 0; i < batalla.getEquipoEnemigos().length; i++) {
                Enemigo enemigo = batalla.getEquipoEnemigos()[i];
                if (enemigo != null) {
                    System.out.println("  " + (i + 1) + ". " + enemigo.toString());
                }
            }
            System.out.println("\n" + "=".repeat(50) + "\n");
        }
    }
}