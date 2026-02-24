package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public class Tablero {
    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;
    private final Casilla[][] casillas = new Casilla[FILAS][COLUMNAS];

    public Tablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    private boolean columnaVacia(int columna) {
        return casillas[FILAS - 1][columna].estaOcupada();
    }

    public boolean estaVacio() {
        int columnasNoVacias = 0;
        for (int i = 0; i < COLUMNAS; i++) {
            if (columnaVacia(i)) {
                columnasNoVacias++;
            }
        }
        return columnasNoVacias == 0;
    }

    private boolean columnaLLena(int columna) {
        int cantidadFilasDeColumnaLlenas = 0;
        for (int i = (FILAS - 1); i >= 0; i--) {
            if (casillas[i][columna].estaOcupada()) {
                cantidadFilasDeColumnaLlenas++;
            }
        }
        return cantidadFilasDeColumnaLlenas == FILAS;
    }

    public boolean estaLleno() {
        int cantidadColumnasLlenas = 0;
        for (int i = 0; i < COLUMNAS; i++) {
            if (columnaLLena(i)) {
                cantidadColumnasLlenas++;
            }
        }
        return cantidadColumnasLlenas == COLUMNAS;
    }

    private void comprobarFicha(Ficha ficha) {
        Objects.requireNonNull(ficha, "La ficha no puede ser nula.");
    }

    private void comprobarColumna(int columna) {
        if ((columna > (COLUMNAS - 1)) || (columna < 0)) {
            throw new IllegalArgumentException("Columna incorrecta.");
        }
    }

    private int getPrimeraFilaVacia(int columna) {
        int primeraFilaVacia = (FILAS - 1);
        for (int i = (FILAS - 1); i >= 0; i--) {
            if (casillas[i][columna].estaOcupada()) {
                primeraFilaVacia--;
            }
        }
        return primeraFilaVacia;
    }

    private boolean objetivoAlcanzado(int fichasIgualesConsecutivas) {
        return fichasIgualesConsecutivas >= FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }

    private boolean comprobarHorizontal(int fila, Ficha ficha) {
        boolean horizontalGanadora;
        int fichasConsecutivas = 0;
        for (int i = 0; i < COLUMNAS; i++) {
            if (casillas[fila][i].getFicha() == ficha) {
                fichasConsecutivas++;
            } else if (fichasConsecutivas < FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS) {
                fichasConsecutivas = 0;
            }
        }
        horizontalGanadora = objetivoAlcanzado(fichasConsecutivas);
        return horizontalGanadora;
    }

    private boolean comprobarVertical(int columna, Ficha ficha) {
        boolean verticalGanadora;
        int fichasConsecutivas = 0;
        for (int i = (FILAS - 1); i >= 0; i--) {
            if (casillas[i][columna].getFicha() == ficha) {
                fichasConsecutivas++;
            } else if (fichasConsecutivas < FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS) {
                fichasConsecutivas = 0;
            }
        }
        verticalGanadora = objetivoAlcanzado(fichasConsecutivas);
        return verticalGanadora;
    }

    private int menor(int fila, int columna) {
        return Math.min(fila, columna);
    }

    private boolean comprobarDiagonalNE(int filaSemilla, int columnaSemilla, Ficha ficha) {
        boolean diagonalNEGanadora;
        int fichasConsecutivas = 0;
        int filaInicial;
        int columaInicial;
        if ((columnaSemilla + filaSemilla) <= (FILAS - 1)) {
            filaInicial = columnaSemilla + filaSemilla;
            columaInicial = 0;
            for (int i = filaInicial; i >= 0; i--) {
                if (casillas[i][columaInicial].getFicha() == ficha) {
                    fichasConsecutivas++;
                } else if (fichasConsecutivas < FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS) {
                    fichasConsecutivas = 0;
                }
                columaInicial++;
            }
        } else {
            filaInicial = FILAS - 1;
            columaInicial = columnaSemilla - (filaInicial - filaSemilla);
            for (int i = columaInicial; i < COLUMNAS; i++) {
                if (casillas[filaInicial][i].getFicha() == ficha) {
                    fichasConsecutivas++;
                } else if (fichasConsecutivas < FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS) {
                    fichasConsecutivas = 0;
                }
                filaInicial--;
            }
        }
        diagonalNEGanadora = objetivoAlcanzado(fichasConsecutivas);
        return diagonalNEGanadora;
    }

    private boolean comprobarDiagonalNO(int filaSemilla, int columnaSemilla, Ficha ficha) {
        boolean diagonalNOGanadora;
        int fichasConsecutivas = 0;
        int filaInicial;
        int columnaInicial;
        filaInicial = filaSemilla - (menor(filaSemilla, columnaSemilla));
        columnaInicial = columnaSemilla - (menor(filaSemilla, columnaSemilla));
        if (columnaInicial == 0) {
            for (int i = filaInicial; i < FILAS; i++) {
                if (casillas[i][columnaInicial].getFicha() == ficha) {
                    fichasConsecutivas++;
                } else if (fichasConsecutivas < FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS) {
                    fichasConsecutivas = 0;
                }
                columnaInicial++;
            }
        } else {
            for (int i = columnaInicial; i < COLUMNAS; i++) {
                if (casillas[filaInicial][i].getFicha() == ficha) {
                    fichasConsecutivas++;
                } else if (fichasConsecutivas < FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS) {
                    fichasConsecutivas = 0;
                }
                filaInicial++;
            }
        }
        diagonalNOGanadora = objetivoAlcanzado(fichasConsecutivas);
        return diagonalNOGanadora;
    }

    private boolean comprobarTirada(int fila, int columna, Ficha ficha) {
        boolean tiradaGanadora;
        tiradaGanadora = comprobarHorizontal(fila, ficha) || comprobarVertical(columna, ficha) || comprobarDiagonalNE(fila, columna, ficha) || comprobarDiagonalNO(fila, columna, ficha);
        return tiradaGanadora;
    }

    public boolean introducirFicha(int columna, Ficha ficha) throws CuatroEnRayaExcepcion {
        boolean fichaGanadora;
        comprobarFicha(ficha);
        comprobarColumna(columna);
        if (estaLleno()) {
            throw new CuatroEnRayaExcepcion("Tablero lleno.");
        } else if (columnaLLena(columna)) {
            throw new CuatroEnRayaExcepcion("Columna llena.");
        }
        int fila = getPrimeraFilaVacia(columna);
        this.casillas[fila][columna].setFicha(ficha);
        fichaGanadora = comprobarTirada(fila, columna, ficha);
        return fichaGanadora;
    }

    @Override
    public String toString() {
        StringBuilder tableroRepresentacion = new StringBuilder();
        for(int i = 0; i < FILAS; i++){
            for(int j = 0; j < COLUMNAS; j++){
                if(j == 0){
                    tableroRepresentacion.append(String.format("|%s", casillas[i][j]));
                }else if(j == (COLUMNAS - 1)){
                    tableroRepresentacion.append(String.format("%s|\n", casillas[i][j]));
                }else{
                    tableroRepresentacion.append(casillas[i][j]);
                }
            }
        }
        tableroRepresentacion.append(" -------\n");
        return tableroRepresentacion.toString();
    }
}