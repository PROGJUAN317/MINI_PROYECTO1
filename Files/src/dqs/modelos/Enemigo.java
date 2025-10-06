package dqs.modelos;

public class Enemigo extends Personaje {
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
		// Implement the method logic here
	}
}
