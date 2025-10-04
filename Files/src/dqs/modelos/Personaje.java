package dqs.modelos;

public abstract class Personaje {
    protected String nombre;
    protected int hp;
    protected int mp;
    protected int ataque;
    protected int defensa;
    protected int velocidad;
    public String getNombre() { return nombre; }
    public int getHp() { return hp; }
    public int getMp() { return mp; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getVelocidad() { return velocidad; }
    public void setHp(int hp) {
        if (hp < 0) this.hp = 0;
        else this.hp = hp;
    }
    public void setMp(int mp) {
        if (mp < 0) this.mp = 0;
        else this.mp = mp;}

    public Personaje(String nombre, int hp, int mp, int ataque, int defensa, int velocidad) {
        this.nombre = nombre;
        this.hp = hp;
        this.mp = mp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
    }
    public void recibir_daño(int cantidad){
        hp -= cantidad;
        if (hp < 0) hp = 0;
    }
    public boolean esta_vivo() {
        return hp > 0;
    }
    public abstract void elegirAccion();
}