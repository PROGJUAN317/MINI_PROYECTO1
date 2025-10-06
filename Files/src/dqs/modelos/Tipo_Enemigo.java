package dqs.modelos;

public enum Tipo_Enemigo {
    GOLEM(200, 400, 0, 0, 40, 60, 30, 50, "Criatura de piedra con gran fuerza y defensa"),
    ORCO(150, 300, 0, 0, 30, 50, 20, 40, "Guerrero salvaje y agresivo"),
    TROLL(180, 350, 0, 0, 35, 55, 25, 45, "Gigante con gran resistencia y fuerza"),
    NOMUERTO(100, 250, 0, 0, 25, 45, 15, 35, "Espíritu vengativo que ataca sin piedad"),
    DRAGON(300, 600, 0, 0, 50, 80, 40, 60, "Bestia legendaria con aliento de fuego");

    private final int minHp, maxHp;
    private final int minMp, maxMp;
    private final int minAtaque, maxAtaque;
    private final int minDefensa, maxDefensa;
    private final String descripcion;

    // Constructor
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

    //Metodos para obtener los atributos
    public int getMinHp() {
        return minHp;
    }
    public int getMaxHp() {
        return maxHp;
    }
    public int getMinMp() {
        return minMp;
    }
    public int getMaxMp() {
        return maxMp;
    }
    public int getMinAtaque() {
        return minAtaque;
    }
    public int getMaxAtaque() {
        return maxAtaque;
    }
    public int getMinDefensa() {
        return minDefensa;
    }
    public int getMaxDefensa() {
        return maxDefensa;
    }
    public String getDescripcion() {
        return descripcion;
    }
    
    // Método para validar si los atributos están dentro del rango permitido
    public boolean validarAtributos(int hp, int mp, int ataque, int defensa) {
        return (hp >= minHp && hp <= maxHp) &&
               (mp >= minMp && mp <= maxMp) &&
               (ataque >= minAtaque && ataque <= maxAtaque) &&
               (defensa >= minDefensa && defensa <= maxDefensa);
    }
}
