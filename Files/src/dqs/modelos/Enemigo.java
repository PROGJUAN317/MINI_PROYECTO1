package modelos;

import java.util.Random;

interface Agresivo {
    void atacar(Personaje objetivo);
}

// Enum de tipos de enemigos
enum TipoEnemigo {
    ORCO, BANDIDO, DRAGON, DEMONIO, GOBLIN;
}

// CLASE ENEMIGO QUE EXTIENDE PERSONAJE E IMPLEMENTAL AGRESIVO
public class Enemigo extends Personaje implements Agresivo {
    private TipoEnemigo tipo;
    private static final Random random = new Random();

    // CONSTRUCTOR PRINCIPAL
    public Enemigo(String nombre, TipoEnemigo tipo, int salud, int ataque, int defensa) {
        super(nombre, salud, ataque, defensa);
        this.tipo = tipo;
    }

    // CONSTRUCTOR ALEATORIO
    public Enemigo() {
        super("", 0, 0, 0);
        this.tipo = generarTipoAleatorio();
        inicializarEstadisticas();
    }

    private TipoEnemigo generarTipoAleatorio() {
        TipoEnemigo[] tipos = TipoEnemigo.values();
        return tipos[random.nextInt(tipos.length)];
    }

    // INICIA ESTADISTICAS SEGUN EL TIPO
    private void inicializarEstadisticas() {
        switch (tipo) {
            case ORCO:
                setNombre("Orco Salvaje");
                setSalud(120);
                setAtaque(25);
                setDefensa(10);
                break;
            case BANDIDO:
                setNombre("Bandido de Camino");
                setSalud(100);
                setAtaque(20);
                setDefensa(8);
                break;
            case DRAGON:
                setNombre("Dragón Ancestral");
                setSalud(250);
                setAtaque(40);
                setDefensa(25);
                break;
            case DEMONIO:
                setNombre("Demonio del Abismo");
                setSalud(180);
                setAtaque(35);
                setDefensa(15);
                break;
            case GOBLIN:
                setNombre("Goblin Astuto");
                setSalud(80);
                setAtaque(15);
                setDefensa(5);
                break;
        }
    }

    public TipoEnemigo getTipo() {
        return tipo;
    }

    @Override
    public void atacar(Personaje objetivo) {
        int danio = getAtaque() - objetivo.getDefensa();
        if (danio < 0) danio = 0;

        objetivo.setSalud(objetivo.getSalud() - danio);

        System.out.println(getNombre() + " (" + tipo + ") ataca a " + objetivo.getNombre()
                + " causando " + danio + " de daño.");

        if (objetivo.getSalud() <= 0) {
            objetivo.setSalud(0);
            System.out.println(objetivo.getNombre() + " ha sido derrotado por " + getNombre() + "!");
        }
    }

    // METODO PARA GENERAR ENEMIGOS ALEATORIOS
    public static Enemigo generarEnemigoAleatorio() {
        return new Enemigo();
    }

    @Override
    public String toString() {
        return "Enemigo: " + getNombre() +
                " | Tipo: " + tipo +
                " | Salud: " + getSalud() +
                " | Ataque: " + getAtaque() +
                " | Defensa: " + getDefensa();
    }
}
