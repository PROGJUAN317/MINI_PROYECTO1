package dqs.modelos;

/**
 * Enumeracion que define los diferentes tipos de heroes disponibles en el sistema RPG.
 * Cada tipo tiene rangos especificos de atributos y una descripcion de su rol en combate.
 * Los tipos incluyen: MAGO (daño magico), DRUIDA (soporte y curacion), 
 * GUERRERO (daño fisico), y PALADIN (tanque y soporte).
 * 
 * @author Sistema RPG
 * @version 1.0
 */
public enum Tipo_Heroe {
    /** Heroe especializado en magia ofensiva con alto mana pero baja defensa */
    MAGO(50, 100, 150, 300, 30, 40, 10, 25, "Utiliza hechizos poderosos para atacar a distancia"),
    /** Heroe de soporte que puede curar aliados y controlar el campo de batalla */
    DRUIDA(80, 160, 120, 250, 25, 40, 18, 35, "Controla la naturaleza y puede sanar a sus aliados"),
    /** Heroe de combate cuerpo a cuerpo con alta vida y ataque fisico */
    GUERRERO(180, 300, 10, 60, 35, 55, 20, 35, "Especialista en combate cuerpo a cuerpo con gran fuerza y defensa"),
    /** Heroe tanque con capacidades de soporte y alta defensa */
    PALADIN(100, 200, 50, 100, 30, 50, 25, 45, "Combina habilidades de combate y magia sagrada para proteger a sus aliados");

    /** Valor minimo de puntos de vida para este tipo de heroe */
    private final int minHp;
    /** Valor maximo de puntos de vida para este tipo de heroe */
    private final int maxHp;
    /** Valor minimo de puntos de mana para este tipo de heroe */
    private final int minMp;
    /** Valor maximo de puntos de mana para este tipo de heroe */
    private final int maxMp;
    /** Valor minimo de ataque para este tipo de heroe */
    private final int minAtaque;
    /** Valor maximo de ataque para este tipo de heroe */
    private final int maxAtaque;
    /** Valor minimo de defensa para este tipo de heroe */
    private final int minDefensa;
    /** Valor maximo de defensa para este tipo de heroe */
    private final int maxDefensa;
    /** Descripcion del rol y habilidades del tipo de heroe */
    private final String descripcion;

    /**
     * Constructor del enum Tipo_Heroe.
     * Define los rangos de atributos permitidos para cada tipo de heroe.
     * 
     * @param minHp valor minimo de puntos de vida
     * @param maxHp valor maximo de puntos de vida
     * @param minMp valor minimo de puntos de mana
     * @param maxMp valor maximo de puntos de mana
     * @param minAtaque valor minimo de ataque
     * @param maxAtaque valor maximo de ataque
     * @param minDefensa valor minimo de defensa
     * @param maxDefensa valor maximo de defensa
     * @param descripcion descripcion del tipo de heroe
     */
    Tipo_Heroe(int minHp, int maxHp, int minMp, int maxMp, int minAtaque, int maxAtaque, int minDefensa, int maxDefensa, String descripcion) {
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
     * Obtiene el valor minimo de puntos de vida para este tipo de heroe.
     * @return valor minimo de HP
     */
    public int getMinHP() {
        return minHp;
    }

    /**
     * Obtiene el valor maximo de puntos de vida para este tipo de heroe.
     * @return valor maximo de HP
     */
    public int getMaxHP() {
        return maxHp;
    }

    /**
     * Obtiene el valor minimo de puntos de mana para este tipo de heroe.
     * @return valor minimo de MP
     */
    public int getMinMP() {
        return minMp;
    }

    /**
     * Obtiene el valor maximo de puntos de mana para este tipo de heroe.
     * @return valor maximo de MP
     */
    public int getMaxMP() {
        return maxMp;
    }

    /**
     * Obtiene el valor minimo de ataque para este tipo de heroe.
     * @return valor minimo de ataque
     */
    public int getMinAtaque() {
        return minAtaque;
    }

    /**
     * Obtiene el valor maximo de ataque para este tipo de heroe.
     * @return valor maximo de ataque
     */
    public int getMaxAtaque() {
        return maxAtaque;
    }

    /**
     * Obtiene el valor minimo de defensa para este tipo de heroe.
     * @return valor minimo de defensa
     */
    public int getMinDefensa() {
        return minDefensa;
    }

    /**
     * Obtiene el valor maximo de defensa para este tipo de heroe.
     * @return valor maximo de defensa
     */
    public int getMaxDefensa() {
        return maxDefensa;
    }

    /**
     * Obtiene la descripcion del rol y habilidades de este tipo de heroe.
     * @return descripcion del tipo de heroe
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Valida si un conjunto de atributos esta dentro de los rangos permitidos
     * para este tipo de heroe. Verifica que HP, MP, ataque y defensa esten
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
