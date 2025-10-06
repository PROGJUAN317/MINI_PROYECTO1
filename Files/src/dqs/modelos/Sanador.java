package dqs.modelos;

public interface Sanador {

    void curar(Personaje objetivo);

    void revivir(Personaje objetivo);

    void restaurarMana(Personaje objetivo);

    void eliminarEfectoNegativo(Personaje objetivo);

}
