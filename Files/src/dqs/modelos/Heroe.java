package dqs.modelos;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Heroe extends Personaje implements Sanador, Tanque {
    private TipoHeroe tipo;

    public Heroe(String nombre, TipoHeroe tipo, int hp, int mp, int ataque, int defensa, int velocidad) {
        super(nombre, hp, mp, ataque, defensa, velocidad);
        this.tipo = tipo;

// Validar todos los atributos, incluyendo velocidad
        if (!tipo.validarAtributos(hp, mp, ataque, defensa, velocidad)) {
            throw new IllegalArgumentException(
                "Atributos fuera del rango permitido para el tipo " + tipo.name() +
                "\nHP: " + tipo.getMinHP() + " - " + tipo.getMaxHP() +
                " | MP: " + tipo.getMinMP() + " - " + tipo.getMaxMP() +
                " | Ataque: " + tipo.getMinAtaque() + " - " + tipo.getMaxAtaque() +
                " | Defensa: " + tipo.getMinDefensa() + " - " + tipo.getMaxDefensa() +
                " | Velocidad: 5 - 100"
            );
        }

// Validación explícita del rango de velocidad
        if (velocidad < 5 || velocidad > 100) {
            throw new IllegalArgumentException("La velocidad debe estar entre 5 y 100.");
        }
    }

// ===== MÉTODO PARA CREAR UN HÉROE DESDE CONSOLA =====
    public static Heroe crearHeroePorConsola() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== CREAR HÉROE ===");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.println("\nSeleccione el tipo de héroe:");
        for (TipoHeroe t : TipoHeroe.values()) {
            System.out.println("- " + t.name() + ": " + t.getDescripcion());
        }

        TipoHeroe tipo = null;
        while (tipo == null) {
            System.out.print("Ingrese el tipo (MAGO/GUERRERO/PALADIN/DRUIDA): ");
            String entrada = sc.nextLine().toUpperCase();
            try {
                tipo = TipoHeroe.valueOf(entrada);
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo inválido. Intente de nuevo.");
            }
        }

        System.out.println("\nIngrese los atributos dentro de los rangos permitidos:");
        System.out.println("HP: " + tipo.getMinHP() + " - " + tipo.getMaxHP());
        System.out.println("MP: " + tipo.getMinMP() + " - " + tipo.getMaxMP());
        System.out.println("Ataque: " + tipo.getMinAtaque() + " - " + tipo.getMaxAtaque());
        System.out.println("Defensa: " + tipo.getMinDefensa() + " - " + tipo.getMaxDefensa());
        System.out.println("Velocidad: 5 - 100");

        int hp = leerAtributo(sc, "HP", tipo.getMinHP(), tipo.getMaxHP());
        int mp = leerAtributo(sc, "MP", tipo.getMinMP(), tipo.getMaxMP());
        int ataque = leerAtributo(sc, "Ataque", tipo.getMinAtaque(), tipo.getMaxAtaque());
        int defensa = leerAtributo(sc, "Defensa", tipo.getMinDefensa(), tipo.getMaxDefensa());
        int velocidad = leerAtributo(sc, "Velocidad", 5, 100);

        return new Heroe(nombre, tipo, hp, mp, ataque, defensa, velocidad);
    }

    private static int leerAtributo(Scanner sc, String nombre, int min, int max) {
        int valor;
        while (true) {
            System.out.print(nombre + ": ");
            valor = sc.nextInt();
            if (valor >= min && valor <= max) break;
            System.out.println(" Valor fuera del rango permitido (" + min + " - " + max + "). Intente de nuevo.");
        }
        return valor;
    }

    public TipoHeroe getTipo() {
        return tipo;
    }

// ===== SISTEMA DE BATALLA POR VELOCIDAD =====
    public static void batallaPorVelocidad(List<Heroe> heroes, List<Enemigo> enemigos) {
        System.out.println("\n=== COMIENZA LA BATALLA ===");

        List<Personaje> participantes = new ArrayList<>();
        participantes.addAll(heroes);
        participantes.addAll(enemigos);

// Orden descendente de velocidad
        Collections.sort(participantes, Comparator.comparingInt(Personaje::getVelocidad).reversed());

        for (Personaje p : participantes) {
            if (p.getHp() <= 0) continue;

            System.out.println("\nTurno de " + p.getNombre() + " (Velocidad: " + p.getVelocidad() + ")");

            if (p instanceof Heroe) {
                Enemigo objetivo = enemigos.stream().filter(e -> e.getHp() > 0).findFirst().orElse(null);
                if (objetivo != null) {
                    p.atacar(objetivo);
                }
            } else if (p instanceof Enemigo) {
                Heroe objetivo = heroes.stream().filter(h -> h.getHp() > 0).findFirst().orElse(null);
                if (objetivo != null) {
                    p.atacar(objetivo);
                }
            }
        }

        System.out.println("\n=== BATALLA FINALIZADA ===");
    }

    @Override
    public String toString() {
        return "Heroe{" +
                "nombre='" + getNombre() + '\'' +
                ", tipo=" + tipo +
                ", HP=" + getHp() +
                ", MP=" + getMp() +
                ", Ataque=" + getAtaque() +
                ", Defensa=" + getDefensa() +
                ", Velocidad=" + getVelocidad() +
                '}';
    }
}

