package dqs.modelos;

/**
 * Interfaz que define comportamientos de soporte y curacion para personajes.
 * Los personajes que implementen esta interfaz tendran capacidades de ayuda
 * como curacion, resurreccion, restauracion de mana y eliminacion de efectos negativos.
 * 
 * @author Sistema RPG
 * @version 1.0
 */
public interface Sanador {

    /**
     * Restaura puntos de vida a un personaje objetivo.
     * La cantidad de curacion puede depender de los atributos del sanador
     * y puede consumir puntos de mana.
     * 
     * @param objetivo el personaje que recibira la curacion
     */
    void curar(Personaje objetivo);

    /**
     * Revive a un personaje que ha sido derrotado.
     * Restaura al personaje a la vida con una cantidad determinada de puntos de vida.
     * Generalmente requiere un costo alto de mana.
     * 
     * @param objetivo el personaje caido que sera revivido
     */
    void revivir(Personaje objetivo);

    /**
     * Restaura puntos de mana a un personaje objetivo.
     * Permite que otros personajes recuperen su capacidad de usar habilidades magicas.
     * 
     * @param objetivo el personaje que recibira la restauracion de mana
     */
    void restaurarMana(Personaje objetivo);

    /**
     * Elimina efectos negativos de un personaje objetivo.
     * Puede remover estados como veneno, paralisis, maldiciones u otros debuffs.
     * 
     * @param objetivo el personaje del cual se eliminaran los efectos negativos
     */
    void eliminarEfectoNegativo(Personaje objetivo);

}
