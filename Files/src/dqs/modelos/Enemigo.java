
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

    public static Enemigo crearEnemigo(String nombre, Tipo_Enemigo tipo) {
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
		// Simple placeholder AI: actual targeting/decision logic should be added later.
		// Currently this method intentionally does nothing to avoid null behavior.
	}

	@Override
	public void atacar(Personaje objetivo) {
		// Placeholder implementation of a basic attack; real logic can use stats from Personaje.
		// Currently no action to keep compilation independent of Personaje internals.
	}

	@Override
	public void usarHabilidadEspecial(Personaje objetivo) {
		// Placeholder for special ability; implement effect on `objetivo` as needed.
	}
}
