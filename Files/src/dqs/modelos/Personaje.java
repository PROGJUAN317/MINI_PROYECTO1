package dqs.modelos;

/**
 * Clase abstracta que representa a un personaje en el sistema de batalla RPG.
 * Sirve como clase base para Heroes y Enemigos, definiendo atributos y
 * comportamientos comunes como estadisticas, estado de vida, mecanicas de
 * defensa y provocacion.
 * 
 * @author Sistema RPG
 * @version 1.0
 */
public abstract class Personaje {
    /** Nombre del personaje */
    protected String nombre;
    /** Puntos de vida actuales del personaje */
    protected int hp;
    /** Puntos de mana actuales del personaje */
    protected int mp;
    /** Poder de ataque del personaje */
    protected int ataque;
    /** Capacidad defensiva del personaje */
    protected int defensa;
    /** Velocidad de accion del personaje */
    protected int velocidad;
    /** Estado que indica si el personaje esta vivo */
    protected boolean esta_vivo = true;
    /** Estado que indica si el personaje esta siendo defendido por otro */
    protected boolean siendo_defendido = false;
    /** Referencia al personaje que esta defendiendo a este personaje */
    protected Personaje defensor = null;
    /** Estado que indica si el personaje esta provocado */
    protected boolean esta_provocado = false;
    /** Referencia al personaje que esta provocando a este personaje */
    protected Personaje provocador = null;
    
    /**
     * Obtiene el nombre del personaje.
     * @return nombre del personaje
     */
    public String getNombre() { return nombre; }
    
    /**
     * Obtiene los puntos de vida actuales del personaje.
     * @return puntos de vida actuales
     */
    public int getHp() { return hp; }
    
    /**
     * Obtiene los puntos de mana actuales del personaje.
     * @return puntos de mana actuales
     */
    public int getMp() { return mp; }
    
    /**
     * Obtiene el poder de ataque del personaje.
     * @return valor de ataque
     */
    public int getAtaque() { return ataque; }
    
    /**
     * Obtiene la capacidad defensiva del personaje.
     * @return valor de defensa
     */
    public int getDefensa() { return defensa; }
    
    /**
     * Obtiene la velocidad de accion del personaje.
     * @return valor de velocidad
     */
    public int getVelocidad() { return velocidad; }
    
    /**
     * Establece los puntos de vida del personaje.
     * Si el valor es negativo, se establece en 0.
     * @param hp nuevos puntos de vida
     */
    public void setHp(int hp) {
        if (hp < 0) this.hp = 0;
        else this.hp = hp;
    }
    
    /**
     * Establece los puntos de mana del personaje.
     * Si el valor es negativo, se establece en 0.
     * @param mp nuevos puntos de mana
     */
    public void setMp(int mp) {
        if (mp < 0) this.mp = 0;
        else this.mp = mp;}

    /**
     * Constructor de la clase Personaje.
     * Inicializa todos los atributos basicos del personaje y establece
     * el estado de vida basado en si tiene puntos de vida.
     * 
     * @param nombre nombre del personaje
     * @param hp puntos de vida iniciales
     * @param mp puntos de mana iniciales
     * @param ataque poder de ataque
     * @param defensa capacidad defensiva
     * @param velocidad velocidad de accion
     */
    public Personaje(String nombre, int hp, int mp, int ataque, int defensa, int velocidad) {
        this.nombre = nombre;
        this.hp = hp;
        this.mp = mp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.esta_vivo = hp > 0;
    }
    
    /**
     * Aplica daño al personaje, considerando mecanicas de defensa.
     * Si el personaje esta siendo defendido por un tanque, aplica defensa combinada.
     * El daño minimo que se puede recibir es 1.
     * 
     * @param cantidad cantidad de daño a aplicar antes de calcular defensas
     */
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
    
    /**
     * Verifica si el personaje esta vivo.
     * @return true si el personaje tiene puntos de vida mayor a 0, false en caso contrario
     */
    public boolean esta_vivo() {
        return esta_vivo;
    }
    
    /**
     * Establece que este personaje esta siendo defendido por un tanque.
     * Cuando un personaje es defendido, la defensa del tanque se suma a la suya
     * al calcular el daño recibido.
     * 
     * @param tanque el personaje que actuara como defensor
     */
    public void recibirDefensa(Personaje tanque) {
        this.siendo_defendido = true;
        this.defensor = tanque;
        System.out.println(tanque.getNombre() + " ahora está defendiendo a " + this.nombre);
    }
    
    /**
     * Remueve el estado de defensa del personaje.
     * Se llama automaticamente cuando el personaje muere o cuando
     * se quiere cancelar la defensa manualmente.
     */
    public void removerDefensa() {
        if (siendo_defendido) {
            System.out.println(this.nombre + " ya no está siendo defendido.");
            this.siendo_defendido = false;
            this.defensor = null;
        }
    }
    
    /**
     * Verifica si el personaje esta siendo defendido por un tanque.
     * @return true si esta siendo defendido, false en caso contrario
     */
    public boolean estaSiendoDefendido() {
        return siendo_defendido;
    }
    
    /**
     * Obtiene la referencia al personaje que esta defendiendo a este personaje.
     * @return el personaje defensor, o null si no esta siendo defendido
     */
    public Personaje getDefensor() {
        return defensor;
    }
    
    /**
     * Establece que este personaje ha sido provocado por un tanque.
     * Un personaje provocado debe atacar obligatoriamente al provocador
     * en su proximo turno, no puede elegir otro objetivo.
     * 
     * @param tanque el personaje que provoca a este personaje
     */
    public void serProvocado(Personaje tanque) {
        this.esta_provocado = true;
        this.provocador = tanque;
        System.out.println(this.nombre + " ha sido provocado por " + tanque.getNombre() + 
                         "! Debe atacar al tanque en su próximo turno.");
    }
    
    /**
     * Remueve el estado de provocacion del personaje.
     * Se llama automaticamente cuando el provocador muere o cuando
     * se quiere cancelar la provocacion manualmente.
     */
    public void removerProvocacion() {
        if (esta_provocado) {
            System.out.println(this.nombre + " ya no está provocado.");
            this.esta_provocado = false;
            this.provocador = null;
        }
    }
    
    /**
     * Verifica si el personaje esta provocado.
     * @return true si esta provocado, false en caso contrario
     */
    public boolean estaProvocado() {
        return esta_provocado;
    }
    
    /**
     * Obtiene la referencia al personaje que esta provocando a este personaje.
     * @return el personaje provocador, o null si no esta provocado
     */
    public Personaje getProvocador() {
        return provocador;
    }
    
    /**
     * Selecciona un objetivo para atacar respetando las reglas de provocacion.
     * Si el personaje esta provocado, debe atacar al provocador si esta vivo.
     * Si no esta provocado, selecciona el primer objetivo vivo disponible.
     * 
     * @param objetivos array de posibles objetivos para atacar
     * @return el personaje seleccionado como objetivo, o null si no hay objetivos validos
     */
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
    
    /**
     * Realiza un ataque considerando automaticamente las reglas de provocacion.
     * Selecciona el objetivo apropiado segun el estado de provocacion,
     * calcula el daño y lo aplica al objetivo seleccionado.
     * 
     * @param objetivos array de posibles objetivos para atacar
     */
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
    
    /**
     * Metodo abstracto que debe ser implementado por las clases hijas.
     * Define la logica de decision de acciones para cada tipo de personaje.
     */
    public abstract void elegirAccion();
}