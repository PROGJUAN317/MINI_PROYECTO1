package dqs.modelos;

public class Enemigo extends Personaje implements Agresivo {
	private final Tipo_Enemigo tipo;

	public Enemigo(String nombre, int hp, int mp, int ataque, int defensa, int velocidad, Tipo_Enemigo tipo) {
		super(nombre, hp, mp, ataque, defensa, velocidad);
		this.tipo = tipo;
        if(!tipo.validarAtributos(hp, mp, ataque, defensa)) {
            throw new IllegalArgumentException(
                "Atributos fuera del rango permitido para el tipo " + tipo.name() +
                "\nHP: " + tipo.getMinHp() + " - " + tipo.getMaxHp() +
                " |MP: " + tipo.getMinMp() + " - " + tipo.getMaxMp() +
                " |Ataque: " + tipo.getMinAtaque() + " - " + tipo.getMaxAtaque() +
                " |Defensa: " + tipo.getMinDefensa() + " - " + tipo.getMaxDefensa()
            );
        }
	}

     public void mostrarEstado() {
        System.out.println("\n " + nombre + " [" + tipo.name() + "]");
        System.out.println("HP: " + hp + " | MP: " + mp +
                           " | Ataque: " + ataque + " | Defensa: " + defensa +
                           " | Velocidad: " + velocidad);
        System.out.println("Descripción: " + tipo.getDescripcion());
        System.out.println("--------------------------------------");
    }

    public static Enemigo crearEnemigo(Tipo_Enemigo tipo, String nombre) {
        int hp = (int)(Math.random() * (tipo.getMaxHp() - tipo.getMinHp() + 1)) + tipo.getMinHp();
        int mp = (int)(Math.random() * (tipo.getMaxMp() - tipo.getMinMp() + 1)) + tipo.getMinMp();
        int ataque = (int)(Math.random() * (tipo.getMaxAtaque() - tipo.getMinAtaque() + 1)) + tipo.getMinAtaque();
        int defensa = (int)(Math.random() * (tipo.getMaxDefensa() - tipo.getMinDefensa() + 1)) + tipo.getMinDefensa();
        int velocidad = (int)(Math.random() * 20 + 10); // Velocidad aleatoria entre 10 y 30

        return new Enemigo(nombre, hp, mp, ataque, defensa, velocidad, tipo);
    }

	public Tipo_Enemigo getTipo() {
		return tipo;
	}

	@Override
	public void elegirAccion() {
		System.out.println(nombre + " (" + tipo.name() + ") esta eligiendo su acción...");
	}

	@Override
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

    // Busca y devuelve un héroe vivo aleatorio del array proporcionado
    public Heroe buscarHeroeVivo(Heroe[] heroes) {
        if (heroes == null || heroes.length == 0) return null;

        // Contar héroes vivos
        int vivos = 0;
        for (Heroe h : heroes) {
            if (h != null && h.esta_vivo()) vivos++;
        }
        if (vivos == 0) return null;

        // Elegir un índice aleatorio entre 0 y vivos-1
        int elegido = (int) (Math.random() * vivos);
        int idx = 0;
        for (Heroe h : heroes) {
            if (h != null && h.esta_vivo()) {
                if (idx == elegido) return h;
                idx++;
            }
        }
        return null;
    }

    // Permite al enemigo elegir y atacar a un héroe vivo del array proporcionado
    public void atacarAleatorio(Heroe[] heroes) {
        if (heroes == null || heroes.length == 0) {
            System.out.println(this.nombre + " no tiene héroes a los que atacar.");
            return;
        }
        Heroe objetivo = buscarHeroeVivo(heroes);
        if (objetivo != null) {
            atacar(objetivo);
        } else {
            System.out.println(this.nombre + " no encontró héroes vivos para atacar.");
        }
    }

    @Override
    public void usarHabilidadEspecial(Personaje objetivo) {
        int daño = this.ataque * 2 - objetivo.getDefensa();
        if (daño < 1) daño = 1; // Daño mínimo de 1
        
        objetivo.recibir_daño(daño);
        System.out.println(this.nombre + " (" + tipo.name() + ") usa su habilidad especial contra " + objetivo.getNombre() + 
                         " causando " + daño + " puntos de daño!");
        
        if (!objetivo.esta_vivo()) {
            System.out.println(objetivo.getNombre() + " ha sido derrotado!");
        }
    }

    public void setDaño(int daño) {
        if (daño < 0) this.ataque = 0;
        else this.ataque = daño;
    }

    @Override
    public String toString() {
        return "Enemigo: " + nombre + " | " + tipo.name() + " | HP: " + hp +
         " | MP: " + mp +
         " | Ataque: " + ataque +
         " | Defensa: " + defensa +
         " | Velocidad: " + velocidad;
    }
}
