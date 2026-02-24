package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public class Casilla {
    private Ficha ficha;

    public Casilla(){
        this.ficha = null;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        if (estaOcupada()) {
            throw new IllegalArgumentException("La casilla ya contiene una ficha.");
        }
        this.ficha = Objects.requireNonNull(ficha, "No se puede poner una ficha nula.");
    }

    public boolean estaOcupada() {
        return (ficha != null);
    }

    @Override
    public String toString() {
        return String.format("%s", ficha == null ? " " : ficha.name().substring(0, 1) );
    }


}
