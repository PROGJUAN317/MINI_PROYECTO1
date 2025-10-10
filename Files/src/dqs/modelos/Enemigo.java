package dqs.modelos;
public class Enemigo extends Personaje implements Agresivo {
	private final Tipo_Enemigo tipo;
	public Enemigo(String nombre, int hp, int mp, int ataque, int defensa, int velocidad, Tipo_Enemigo tipo) {
		super(nombre, hp, mp, ataque, defensa, velocidad);
		this.tipo = tipo;
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
