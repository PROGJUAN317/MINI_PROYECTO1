package dqs.modelos;

import java.util.Scanner;

public class Heroe extends Personaje implements Sanador, Tanque {
    private Tipo_Heroe tipo;

    public Heroe(String nombre, Tipo_Heroe tipo, int hp, int mp, int ataque, int defensa, int velocidad) {
        super(nombre, hp, mp, ataque, defensa, velocidad);
        this.tipo = tipo;

        if(!tipo.validarAtributos(hp, mp, ataque, defensa)) {
            throw new IllegalArgumentException(
                "Atributos fuera del rango permitido para el tipo " + tipo.name() +
                "\nHP: " + tipo.getMinHP() + " - " + tipo.getMaxHP() +
                " |MP: " + tipo.getMinMP() + " - " + tipo.getMaxMP() +
                " |Ataque: " + tipo.getMinAtaque() + " - " + tipo.getMaxAtaque() +
                " |Defensa: " + tipo.getMinDefensa() + " - " + tipo.getMaxDefensa()
            );
        }
    }
    
    //METODO PARA CREAR UN HEROE PIDIENDO DATOS POR CONSOLA
    public static Heroe crearHeroePorConsola() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Crear héroe: ");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Seleccione el tipo de heroe: ");
        for (Tipo_Heroe t : Tipo_Heroe.values()) {
            System.out.println("- " + t.name() + ": " + t.getDescripcion());
        }

        Tipo_Heroe tipo = null;
        while (tipo == null) {
            System.out.print("Ingrese el tipo (MAGO/GUERRERO/PALADIN/DRUIDA): ");
            String entrada = sc.nextLine().toUpperCase();
            try {
                tipo = Tipo_Heroe.valueOf(entrada);
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo inválido. Intente de nuevo.");
            } 
        }

        System.out.print("\nIngrese los atributos dentro de los rasgos permitidos:");
        System.out.print("HP: " + tipo.getMinHP() + " - " + tipo.getMaxHP());
        System.out.print("MP: " + tipo.getMinMP() + " - " + tipo.getMaxMP());
        System.out.print("Ataque: " + tipo.getMinAtaque() + " - " + tipo.getMaxAtaque());
        System.out.print("Defensa: " + tipo.getMinDefensa() + " - " + tipo.getMaxDefensa());

        int hp = pedirEnRango(sc, "HP", tipo.getMinHP(), tipo.getMaxHP());
        int mp = pedirEnRango(sc, "MP", tipo.getMinMP(), tipo.getMaxMP());
        int ataque = pedirEnRango(sc, "Ataque", tipo.getMinAtaque(), tipo.getMaxAtaque());
        int defensa = pedirEnRango(sc, "Defensa", tipo.getMinDefensa(), tipo.getMaxDefensa());
        int velocidad = (int)(Math.random() * 20 + 10);

        return new Heroe(nombre, tipo, hp, mp, ataque, defensa, velocidad);
    }

//METODO AUXILIAR PARA PEDIR NUMEROS DENTRO DE UN RANGO
 private static int pedirEnRango(Scanner sc, String atributo, int min, int max) {
        int valor;
        while (true) {
            System.out.print(atributo + ": ");
            try {
                valor = Integer.parseInt(sc.nextLine());
                if (valor >= min && valor <= max) {
                    break;
                } else {
                    System.out.println(" El valor debe estar entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Ingresa un número válido.");
            }
        }
        return valor;
    }

    public void mostrarEstado() {
        System.out.println("\n " + nombre + " [" + tipo.name() + "]");
        System.out.println("HP: " + hp + " | MP: " + mp +
                           " | Ataque: " + ataque + " | Defensa: " + defensa +
                           " | Velocidad: " + velocidad);
        System.out.println("Descripción: " + tipo.getDescripcion());
        System.out.println("--------------------------------------");
    }

    public Tipo_Heroe getTipo() {
        return tipo;
    }

    @Override
    public void elegirAccion() {
        System.out.println(nombre + " (" + tipo.name() + ") esta eligiendo su acción...");
    }
    
    // MÉTODOS DE LA INTERFAZ TANQUE
    @Override
    public void aumentarDefensa(int cantidad) {
        this.defensa += cantidad;
        System.out.println(nombre + " aumenta su defensa en " + cantidad + " puntos.");
    }
    
    @Override
    public void defender(Personaje aliado) {
        System.out.println(nombre + " se prepara para defender a " + aliado.getNombre());
        // Lógica para reducir el daño que recibe el aliado
    }
    
    @Override
    public void provocarEnemigo(Personaje enemigo) {
        System.out.println(nombre + " provoca a " + enemigo.getNombre() + " para que lo ataque!");
        // Lógica para forzar al enemigo a atacar a este héroe
    }
    // MÉTODOS DE LA INTERFAZ SANADOR
    @Override
    public void curar(Personaje objetivo) {
        if (tipo == Tipo_Heroe.DRUIDA || tipo == Tipo_Heroe.PALADIN) {
            if (mp >= 15) {
                mp -= 15;
                int curacion = 30;
                objetivo.setHp(objetivo.getHp() + curacion);
                System.out.println(nombre + " ha curado a " + objetivo.getNombre() + " por " + curacion + " puntos de vida.");
            } else {
                System.out.println(nombre + " no tiene suficiente MP para curar.");
            }
        } else {
            System.out.println(nombre + " no puede curar.");
        }
    }

    @Override
    public void revivir(Personaje objetivo) {
        if (tipo == Tipo_Heroe.PALADIN) {
            if (!objetivo.esta_vivo() && mp >= 25) {
                mp -= 25;
                objetivo.setHp(50);
                System.out.println(nombre + " ha revivido a " + objetivo.getNombre() + " con 50 puntos de vida.");
            } else if(objetivo.esta_vivo()) {
                System.out.println(objetivo.getNombre() + " ya está vivo.");
            } else {
                System.out.println(nombre + " no tiene suficiente MP para revivir.");
            }
        } else {
            System.out.println(nombre + " no puede revivir a otros.");
        }
    }
    @Override
    public void restaurarMana(Personaje objetivo) {
        if (tipo == Tipo_Heroe.DRUIDA) {
            if (mp >= 20) {
                mp -= 10;
                objetivo.setMp(objetivo.getMp() + 25);
                System.out.println(nombre + " ha restaurado 25 puntos de MP a " + objetivo.getNombre() + ".");
            } else {
                System.out.println(nombre + " no tiene suficiente MP para restaurar.");
            }
        } else {
            System.out.println(nombre + " no puede restaurar mana.");
        }
    }

    @Override
    public void eliminarEfectoNegativo(Personaje objetivo) {
        if (tipo == Tipo_Heroe.DRUIDA || tipo == Tipo_Heroe.PALADIN) {
            System.out.println(nombre + " elimina efectos negativos de " + objetivo.getNombre() + ".");
            // Lógica para limpiar estados negativos
        } else {
            System.out.println(nombre + " no puede eliminar efectos negativos de " + objetivo.getNombre() + ".");
        }
    }

    @Override
    public String toString() {
        return "Heroe: " + nombre + " | " + tipo.name() + " | HP: " + hp +
         " | MP: " + mp +
         " | Ataque: " + ataque +
         " | Defensa: " + defensa +
         " | Velocidad: " + velocidad;
    }
}
