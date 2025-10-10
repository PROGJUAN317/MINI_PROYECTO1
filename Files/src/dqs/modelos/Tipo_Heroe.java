package dqs.modelos;

public enum Tipo_Heroe {
    MAGO(50, 100, 150, 300, 30, 40, 10, 25, "Utiliza hechizos poderosos para atacar a distancia"),
    DRUIDA(80, 160, 120, 250, 25, 40, 18, 35, "Controla la naturaleza y puede sanar a sus aliados"),
    GUERRERO(180, 300, 10, 60, 35, 55, 20, 35, "Especialista en combate cuerpo a cuerpo con gran fuerza y defensa"),
    PALADIN(100, 200, 50, 100, 30, 50, 25, 45, "Combina habilidades de combate y magia sagrada para proteger a sus aliados");

    private final int minHp, maxHp;
    private final int minMp, maxMp;
    private final int minAtaque, maxAtaque;
    private final int minDefensa, maxDefensa;
    private final String descripcion;

    // Constructor
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

    // Métodos para obtener los rangos de atributos
    public int getMinHP() {
        return minHp;
    }

    public int getMaxHP() {
        return maxHp;
    }

    public int getMinMP() {
        return minMp;
    }

    public int getMaxMP() {
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
