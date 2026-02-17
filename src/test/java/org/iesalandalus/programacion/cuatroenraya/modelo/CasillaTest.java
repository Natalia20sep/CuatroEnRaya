package org.iesalandalus.programacion.cuatroenraya.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CasillaTest {

	private Casilla casilla;

	@BeforeEach
	void setUp() {
		casilla =
                new Casilla();
	}

	@Test
	void constructorPorDefectoCreaCasillaVacia() {
        assertNull(casilla.getFicha());
	}
	
	@ParameterizedTest(name = "Cuando ponemos la ficha {0} ésta se coloca correctamente.")
	@CsvSource({"AZUL, VERDE"})
	void ponerFichaCasillaVaciaPoneFichaCorrectamente(Ficha ficha) {
		assertDoesNotThrow(() -> casilla.setFicha(ficha));
		assertEquals(ficha, casilla.getFicha());
	}
	
	@Test
	void ponerFichaNulaLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> casilla.setFicha(null));
		assertEquals("No se puede poner una ficha nula.", npe.getMessage());
	}
	
	@ParameterizedTest(name = "Cuando ponemos la ficha {1} y la casilla contenía la ficha {0} lanza la excepción.")
	@CsvSource({"AZUL, AZUL", "AZUL, VERDE", "VERDE, VERDE", "VERDE, AZUL"})
	void ponerFichaCasillaOcupadaLanzaExcepcion(Ficha primera, Ficha segunda) {
		assertDoesNotThrow(() -> casilla.setFicha(primera));
		CuatroEnRayaExcepcion cere = assertThrows(CuatroEnRayaExcepcion.class, () -> casilla.setFicha(segunda));
		assertEquals("La casilla ya contiene una ficha.", cere.getMessage());
	}
	
	@Test
	void toStringConCasillaVaciaMuestraCadenaCorrectamente() {
		assertEquals(" ", casilla.toString());
	}

	@ParameterizedTest(name = "toString con la casilla ocupada por la ficha {0} devuelve {1}")
	@CsvSource({"AZUL, A", "VERDE, V"})
	void toStringCasillaOcupadaMuestraCadenaCorrectamente(Ficha ficha, String cadena) {
		assertDoesNotThrow(() -> casilla.setFicha(ficha));
		assertEquals(cadena, casilla.toString());
	}

}
