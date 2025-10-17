package dqs.modelos;

/**
 * Interfaz que define comportamientos agresivos para personajes de combate.
 * Los personajes que implementen esta interfaz tendran capacidades ofensivas
 * como ataques basicos y habilidades especiales de daño.
 * 
 * @author Sistema RPG
 * @version 1.0
 */
public interface Agresivo {

    /**
     * Realiza un ataque basico contra un objetivo especificado.
     * El ataque calcula el daño basado en los atributos del atacante
     * y las defensas del objetivo.
     * 
     * @param objetivo el personaje que recibira el ataque
     */
    void atacar(Personaje objetivo);

    /**
     * Utiliza una habilidad especial de combate contra un objetivo.
     * Las habilidades especiales pueden tener efectos adicionales
     * como mayor daño, efectos de estado, o consumo de mana.
     * 
     * @param objetivo el personaje que sera afectado por la habilidad especial
     */
    void usarHabilidadEspecial(Personaje objetivo);

}
