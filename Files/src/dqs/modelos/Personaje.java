package dqs.modelos;

public abstract class Personaje {
    protected String nombre;
    protected int hp;
    protected int mp;
    protected int ataque;
    protected int defensa;
    protected int velocidad;
    protected boolean esta_vivo = true;
    protected boolean siendo_defendido = false;
    protected Personaje defensor = null;
    protected boolean esta_provocado = false;
    protected Personaje provocador = null;
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
        this.esta_vivo = hp > 0;
    }
    public void recibir_daño(int cantidad){
        int dañoFinal = cantidad;
        
        // Si está siendo defendido por un tanque, aplicar defensa combinada
        if (siendo_defendido && defensor != null && defensor.esta_vivo()) {
            int defensaCombinada = this.defensa + defensor.getDefensa();
            dañoFinal = cantidad - defensaCombinada;
            
            // Daño mínimo de 1
            if (dañoFinal < 1) dañoFinal = 1;
            
            System.out.println(defensor.getNombre() + " defiende a " + this.nombre + 
                             "! Defensa combinada: " + defensaCombinada + 
                             " | Daño reducido de " + cantidad + " a " + dañoFinal);
        } else {
            // Defensa normal
            dañoFinal = cantidad - this.defensa;
            if (dañoFinal < 1) dañoFinal = 1;
        }
        
        hp -= dañoFinal;
        if (hp < 0) hp = 0;
        esta_vivo = hp > 0;
        
        // Si el personaje muere, remover la defensa
        if (!esta_vivo) {
            removerDefensa();
        }
    }
    public boolean esta_vivo() {
        return esta_vivo;
    }
    
    // Métodos para manejar la defensa por tanque
    public void recibirDefensa(Personaje tanque) {
        this.siendo_defendido = true;
        this.defensor = tanque;
        System.out.println(tanque.getNombre() + " ahora está defendiendo a " + this.nombre);
    }
    
    public void removerDefensa() {
        if (siendo_defendido) {
            System.out.println(this.nombre + " ya no está siendo defendido.");
            this.siendo_defendido = false;
            this.defensor = null;
        }
    }
    
    public boolean estaSiendoDefendido() {
        return siendo_defendido;
    }
    
    public Personaje getDefensor() {
        return defensor;
    }
    
    // Métodos para manejar la provocación
    public void serProvocado(Personaje tanque) {
        this.esta_provocado = true;
        this.provocador = tanque;
        System.out.println(this.nombre + " ha sido provocado por " + tanque.getNombre() + 
                         "! Debe atacar al tanque en su próximo turno.");
    }
    
    public void removerProvocacion() {
        if (esta_provocado) {
            System.out.println(this.nombre + " ya no está provocado.");
            this.esta_provocado = false;
            this.provocador = null;
        }
    }
    
    public boolean estaProvocado() {
        return esta_provocado;
    }
    
    public Personaje getProvocador() {
        return provocador;
    }
    
    // Método para seleccionar objetivo respetando la provocación
    public Personaje seleccionarObjetivo(Personaje[] objetivos) {
        // Si está provocado, debe atacar al provocador si está vivo
        if (esta_provocado && provocador != null && provocador.esta_vivo()) {
            System.out.println(this.nombre + " está provocado y debe atacar a " + provocador.getNombre());
            return provocador;
        }
        
        // Si no está provocado, buscar el primer objetivo vivo
        for (Personaje objetivo : objetivos) {
            if (objetivo != null && objetivo.esta_vivo()) {
                return objetivo;
            }
        }
        
        return null; // No hay objetivos válidos
    }
    
    // Método de ataque que respeta la provocación automáticamente
    public void atacarConProvocacion(Personaje[] objetivos) {
        Personaje objetivo = seleccionarObjetivo(objetivos);
        
        if (objetivo != null) {
            int daño = this.ataque - objetivo.getDefensa();
            if (daño < 1) daño = 1;
            
            objetivo.recibir_daño(daño);
            System.out.println(this.nombre + " ataca a " + objetivo.getNombre() + 
                             " causando " + daño + " puntos de daño!");
            
            if (!objetivo.esta_vivo()) {
                System.out.println(objetivo.getNombre() + " ha sido derrotado!");
                // Si el objetivo derrotado era el provocador, remover provocación
                if (objetivo == this.provocador) {
                    this.removerProvocacion();
                }
            }
        } else {
            System.out.println(this.nombre + " no encuentra objetivos válidos para atacar.");
        }
    }
    
    public abstract void elegirAccion();
}