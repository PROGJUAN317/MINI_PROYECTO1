package dqs.modelos;

/**
 * Interfaz que define comportamientos defensivos y de proteccion para personajes.
 * Los personajes que implementen esta interfaz tendran capacidades de tanque
 * como defensa de aliados, provocacion de enemigos y aumento de capacidades defensivas.
 * 
 * @author Sistema RPG
 * @version 1.0
 */
public interface Tanque {

    /**
     * Protege a un aliado, combinando las defensas para reducir el daño recibido.
     * Cuando un tanque defiende a un aliado, la defensa del tanque se suma
     * a la del aliado al calcular la reduccion de daño.
     * 
     * @param aliado el personaje que sera protegido por el tanque
     */
    void defender(Personaje aliado);

    /**
     * Provoca a un enemigo, forzandolo a atacar al tanque en su proximo turno.
     * Los enemigos provocados no pueden elegir otro objetivo mientras
     * el tanque que los provoco siga vivo.
     * 
     * @param enemigo el personaje enemigo que sera provocado
     */
    void provocarEnemigo(Personaje enemigo);

    /**
     * Aumenta temporalmente la capacidad defensiva del tanque.
     * Puede representar habilidades como postura defensiva o fortificacion.
     * 
     * @param cantidad la cantidad de defensa adicional que se añadira
     */
    void aumentarDefensa(int cantidad);

}
