package dqs.modelos;

/**
 * Enumeracion que define los diferentes tipos de enemigos disponibles en el sistema RPG.
 * Cada tipo tiene rangos especificos de atributos y una descripcion de sus caracteristicas.
 * Los tipos incluyen: GOLEM (tanque), ORCO (guerrero), TROLL (resistente), 
 * NOMUERTO (agil), y DRAGON (jefe final).
 * 
 * @author Sistema RPG
 * @version 1.0
 */
public enum Tipo_Enemigo {
    /** Enemigo tanque con alta defensa y vida, pero lento */
    GOLEM(200, 400, 0, 0, 40, 60, 30, 50, "Criatura de piedra con gran fuerza y defensa"),
    /** Enemigo guerrero balanceado con ataque y defensa moderados */
    ORCO(150, 300, 0, 0, 30, 50, 20, 40, "Guerrero salvaje y agresivo"),
    /** Enemigo resistente con mucha vida y buen ataque */
    TROLL(180, 350, 0, 0, 35, 55, 25, 45, "Gigante con gran resistencia y fuerza"),
    /** Enemigo agil con poca defensa pero ataque rapido */
    NOMUERTO(100, 250, 0, 0, 25, 45, 15, 35, "Espíritu vengativo que ataca sin piedad"),
    /** Enemigo jefe con las estadisticas mas altas en todos los aspectos */
    DRAGON(300, 600, 0, 0, 50, 80, 40, 60, "Bestia legendaria con aliento de fuego");

    /** Valor minimo de puntos de vida para este tipo de enemigo */
    private final int minHp;
    /** Valor maximo de puntos de vida para este tipo de enemigo */
    private final int maxHp;
    /** Valor minimo de puntos de mana para este tipo de enemigo */
    private final int minMp;
    /** Valor maximo de puntos de mana para este tipo de enemigo */
    private final int maxMp;
    /** Valor minimo de ataque para este tipo de enemigo */
    private final int minAtaque;
    /** Valor maximo de ataque para este tipo de enemigo */
    private final int maxAtaque;
    /** Valor minimo de defensa para este tipo de enemigo */
    private final int minDefensa;
    /** Valor maximo de defensa para este tipo de enemigo */
    private final int maxDefensa;
    /** Descripcion de las caracteristicas del tipo de enemigo */
    private final String descripcion;

    /**
     * Constructor del enum Tipo_Enemigo.
     * Define los rangos de atributos permitidos para cada tipo de enemigo.
     * 
     * @param minHp valor minimo de puntos de vida
     * @param maxHp valor maximo de puntos de vida
     * @param minMp valor minimo de puntos de mana
     * @param maxMp valor maximo de puntos de mana
     * @param minAtaque valor minimo de ataque
     * @param maxAtaque valor maximo de ataque
     * @param minDefensa valor minimo de defensa
     * @param maxDefensa valor maximo de defensa
     * @param descripcion descripcion del tipo de enemigo
     */
    Tipo_Enemigo(int minHp, int maxHp, int minMp, int maxMp, int minAtaque, int maxAtaque, int minDefensa, int maxDefensa, String descripcion) {
        this.minHp = minHp;
        this.maxHp = maxHp;
        this.minMp = minMp;
        this.maxMp = maxMp;
        this.minAtaque = minAtaque;
        this.maxAtaque = maxAtaque;
        this.minDefensa = minDefensa;
        this.maxDefensa = maxDefensa;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el valor minimo de puntos de vida para este tipo de enemigo.
     * @return valor minimo de HP
     */
    public int getMinHp() {
        return minHp;
    }
    
    /**
     * Obtiene el valor maximo de puntos de vida para este tipo de enemigo.
     * @return valor maximo de HP
     */
    public int getMaxHp() {
        return maxHp;
    }
    
    /**
     * Obtiene el valor minimo de puntos de mana para este tipo de enemigo.
     * @return valor minimo de MP
     */
    public int getMinMp() {
        return minMp;
    }
    
    /**
     * Obtiene el valor maximo de puntos de mana para este tipo de enemigo.
     * @return valor maximo de MP
     */
    public int getMaxMp() {
        return maxMp;
    }
    
    /**
     * Obtiene el valor minimo de ataque para este tipo de enemigo.
     * @return valor minimo de ataque
     */
    public int getMinAtaque() {
        return minAtaque;
    }
    
    /**
     * Obtiene el valor maximo de ataque para este tipo de enemigo.
     * @return valor maximo de ataque
     */
    public int getMaxAtaque() {
        return maxAtaque;
    }
    
    /**
     * Obtiene el valor minimo de defensa para este tipo de enemigo.
     * @return valor minimo de defensa
     */
    public int getMinDefensa() {
        return minDefensa;
    }
    
    /**
     * Obtiene el valor maximo de defensa para este tipo de enemigo.
     * @return valor maximo de defensa
     */
    public int getMaxDefensa() {
        return maxDefensa;
    }
    
    /**
     * Obtiene la descripcion de las caracteristicas de este tipo de enemigo.
     * @return descripcion del tipo de enemigo
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Valida si un conjunto de atributos esta dentro de los rangos permitidos
     * para este tipo de enemigo. Verifica que HP, MP, ataque y defensa esten
     * dentro de los limites minimos y maximos definidos.
     * 
     * @param hp puntos de vida a validar
     * @param mp puntos de mana a validar
     * @param ataque valor de ataque a validar
     * @param defensa valor de defensa a validar
     * @return true si todos los atributos estan en rango, false en caso contrario
     */
    public boolean validarAtributos(int hp, int mp, int ataque, int defensa) {
        return (hp >= minHp && hp <= maxHp) &&
               (mp >= minMp && mp <= maxMp) &&
               (ataque >= minAtaque && ataque <= maxAtaque) &&
               (defensa >= minDefensa && defensa <= maxDefensa);
    }
}
