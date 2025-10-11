package dqs.modelos;

import java.util.Scanner;

public class Heroe extends Personaje implements Sanador, Tanque {
    private final Tipo_Heroe tipo;

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
    public void aumentarDefensa(int defensa) {
        if (tipo == Tipo_Heroe.GUERRERO || tipo == Tipo_Heroe.PALADIN) {
            if (mp >= 10) {
                mp -= 10;
                this.defensa += defensa;
                System.out.println(nombre + " aumenta su defensa en " + defensa + " puntos.");
            } else {
                System.out.println(nombre + " no tiene suficiente MP para aumentar defensa.");
            }
        } else {
            System.out.println(nombre + " no puede aumentar defensa.");
        }
    }

    @Override
    public void defender(Personaje aliado) {
        if (tipo == Tipo_Heroe.GUERRERO || tipo == Tipo_Heroe.PALADIN) {
            if (mp >= 10) {
                mp -= 10;
                
                // Remover defensa anterior si existe
                if (aliado.estaSiendoDefendido()) {
                    aliado.removerDefensa();
                }
                
                // Activar nueva defensa
                aliado.recibirDefensa(this);
                System.out.println(nombre + " está defendiendo a " + aliado.getNombre() + 
                                 "! Los próximos ataques tendrán defensa combinada.");
            } else {
                System.out.println(nombre + " no tiene suficiente MP para defender.");
            }
        } else {
            System.out.println(nombre + " no puede defender a otros.");
        }
    }
    
    @Override
    public void provocarEnemigo(Personaje enemigo) {
        if (tipo == Tipo_Heroe.GUERRERO || tipo == Tipo_Heroe.PALADIN) {
            if (mp >= 5) {
                mp -= 5;
                
                // Remover provocación anterior si existe
                if (enemigo.estaProvocado()) {
                    enemigo.removerProvocacion();
                }
                
                // Aplicar nueva provocación
                enemigo.serProvocado(this);
                System.out.println(nombre + " provoca a " + enemigo.getNombre() + 
                                 "! El enemigo debe atacar al tanque en su próximo turno.");
            } else {
                System.out.println(nombre + " no tiene suficiente MP para provocar.");
            }
        } else {
            System.out.println(nombre + " no puede provocar enemigos.");
        }
    }
    
    // Método para dejar de defender a un aliado
    public void dejarDeDefender(Personaje aliado) {
        if (aliado.estaSiendoDefendido() && aliado.getDefensor() == this) {
            aliado.removerDefensa();
            System.out.println(nombre + " ha dejado de defender a " + aliado.getNombre());
        }
    }
    
    // Método para provocar a todos los enemigos en un arreglo (área de efecto)
    public void provocarTodosLosEnemigos(Personaje[] enemigos) {
        if (tipo == Tipo_Heroe.GUERRERO || tipo == Tipo_Heroe.PALADIN) {    
            int costoPorEnemigo = 3;
            int enemigosVivos = 0;
            
            // Contar enemigos vivos
            for (Personaje enemigo : enemigos) {
                if (enemigo != null && enemigo.esta_vivo()) {
                    enemigosVivos++;
                }
            }
            
            int costoTotal = costoPorEnemigo * enemigosVivos;
            
            if (mp >= costoTotal) {
                mp -= costoTotal;
                System.out.println(nombre + " provoca a todos los enemigos vivos!");
                
                for (Personaje enemigo : enemigos) {
                    if (enemigo != null && enemigo.esta_vivo()) {
                        if (enemigo.estaProvocado()) {
                            enemigo.removerProvocacion();
                        }
                        enemigo.serProvocado(this);
                    }
                }
            } else {
                System.out.println(nombre + " no tiene suficiente MP para provocar a todos los enemigos. Costo: " + costoTotal);
            }
        } else {
            System.out.println(nombre + " no puede provocar enemigos.");
        }
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

    // MÉTODO DE ATAQUE
    public void atacar(Personaje objetivo) {
        if (objetivo != null && objetivo.esta_vivo()) {
            int daño = this.ataque - objetivo.getDefensa();
            if (daño < 1) daño = 1; // Daño mínimo de 1
            
            objetivo.recibir_daño(daño);
            System.out.println(this.nombre + " (" + tipo.name() + ") ataca a " + objetivo.getNombre() + 
                             " causando " + daño + " puntos de daño!");
            
            if (!objetivo.esta_vivo()) {
                System.out.println(objetivo.getNombre() + " ha sido derrotado!");
            }
        } else {
            System.out.println(this.nombre + " no puede atacar a un objetivo inválido o muerto.");
        }
    }

    // Método para atacar en el contexto de una batalla (busca automáticamente enemigos)
    public void atacarEnemigo(Enemigo[] enemigos) {
        Enemigo objetivo = buscarEnemigoVivo(enemigos);
        if (objetivo != null) {
            atacar(objetivo);
        } else {
            System.out.println(this.nombre + " no encuentra enemigos vivos para atacar.");
        }
    }

    // Método auxiliar para buscar un enemigo vivo
    private Enemigo buscarEnemigoVivo(Enemigo[] enemigos) {
        for (Enemigo enemigo : enemigos) {
            if (enemigo != null && enemigo.esta_vivo()) {
                return enemigo;
            }
        }
        return null;
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
