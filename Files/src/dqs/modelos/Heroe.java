import java.util.Scanner;
package dqs.modelos;
public class Heroe extends Personaje implements Sanador, Tanque {
    private TipoHeroe tipo;

    public Heroe(String nombre, TipoHeroe tipo, int hp, int mp, int ataque, int defensa, int velocidad) {
        super(nombre, hp, mp, ataque, defensa, velocidad);
        this.tipo = tipo;

        if(!tipo.validarAtributos(hp, mp, ataque, defensa)) {
            throw new IllegalArgumentException(
                "Atributos fuera del rango permitido para el tipo " + tipo.name() +
            "\nHP: " + tipo.getMinHP() + " - " + tipo.getMaxHP() +
            "|MP: " + tipo.getMinMP() + " - " + tipo.getMaxMP() +
            "|Ataque: " + tipo.getMinAtaque() + " - " + tipo.getMaxAtaque() +
            "|Defensa: " + tipo.getMinDefensa() + " - " + tipo.getMaxDefensa() +
            );
    }
    
    //METODO PARA CREAR UN HEROE PIDIENDO DATOS POR CONSOLA
    public static Heroe crearHeroePorConsola() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Crear héroe: ");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Seleccione el tipo de heroe: ");
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

    public void elegirAccion()
}



    public TipoHeroe getTipo() {
        return tipo;
    }

    @Override
    public void elegirAccion() {
        System.out.println(nombre + " (" + tipo.name() + ") esta eligiendo su acción...");
    }
// METODO DE LA INTERFAZ SANADOR
    @Override
    public void curar(Personaje objetivo) {
        if (tipo == TipoHeroe.SANADOR || tipo == TipoHeroe.PALADIN) {
            if (mp >= 15) {
                mp -= 15;
                objetivo.recibirCuracion(-30);
                System.out.println(nombre + " ha curado a " + objetivo.getNombre() + " por 30 puntos de vida.");
            } else {
                System.out.println(nombre + " no tiene suficiente MP para curar.");
            }
        } else {
            System.out.println(nombre + " no puede curar.");
        }
}

@Override
public void revivir(Personaje objetivo) {
    if (tipo == TipoHeroe.PALADIN) {
        if (!objetivo.estaVivo() && mp >= 25) {
            mp -= 25;
            objetivo.recibirDanio(-5);
            System.out.println(nombre + " ha revivido a " + objetivo.getNombre() + " con 50 puntos de vida.");
        } else if(objetivo.estaVivo()) {
            System.out.println(objetivo.getNombre() + " ya está vivo.");
        } else {
            System.out.println(nombre + " no puede revivir a otros.");
        }
    }
@Override
public void restaurarMana(Personaje objetivo) {
    if (tipo == TipoHeroe.DRUIDA) {
        if (mp >= 20) {
            mp -= 10;
            objetivo.recibirMana(25);
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
    if (tipo == TipoHeroe.DRUIDA || tipo == TipoHeroe.PALADIN) {

        System.out.println(nombre + "  elimina efectos negativos de " + objetivo.getNombre() + " veneno.");
        objetivo.limpiarEstados();
        } else {
            System.out.println(nombre + " no puede eliminar efectos negativos de " + objetivo.getNombre() + ".");
        }
    }

//MÉTODOS DE LA INTERFAZ TANQUE
@Override
public void protegerAliado(Personaje aliado) {
    if (tipo == TipoHeroe.GUERRERO || tipo == TipoHeroe.PALADIN) {
        int danioReducido = (int) (danio * 0.7);
        super.recibirDanio(danioReducido);
        System.out.println(nombre + " resiste el golpe con su armadura. Daño recibido: " + danioReducido);

    } else {
        super.recibirDanio(danio);
        System.out.println(nombre + " recibe el golpe completo. Daño recibido: " + danio);
    }
}

@Override
public String toString() {
    return "Heroe: " + nombre + " |  " + tipo.name() + " | HP: " + hp +
     " | MP: " + mp +
     " | Ataque: " + ataque +
     " | Defensa: " + defensa +
     " | Velocidad: " + velocidad;
}
