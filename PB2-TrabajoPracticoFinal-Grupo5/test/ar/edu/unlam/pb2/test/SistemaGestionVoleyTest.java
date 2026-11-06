package ar.edu.unlam.pb2.test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.pb2.domain.*;

public class SistemaGestionVoleyTest {

	private SistemaGestion liga;
	private Miembro jugador1;
	private Miembro jugador2;
	private Miembro jugador3;
	private Equipo river;
	private Partido partido;

	@Before
	public void setUp() {
		liga = new SistemaGestion();
		jugador1 = new Jugador("Martino", "48067068", LocalDate.of(2008, 03, 04), 6, PosicionEnum.PUNTA, true);
		jugador2 = new Jugador("Daniel", "44089012", LocalDate.of(2004, 05, 07), 43, PosicionEnum.CENTRAL, false);
		jugador3 = new Jugador("Agus", "47034876", LocalDate.of(2008, 06, 13), 38, PosicionEnum.CENTRAL, true);
	}

	// Gestión de Jugadores
	@Test
	public void agregarCorrectamenteUnJugadorAlEquipo() throws EquipoNoEncontradoException, JugadorNoFederadoException {
		liga.registrarEquipo("Boca");

		assertTrue(liga.agregarJugadorAlEquipo((Jugador) jugador1, "Boca"));
	}

	@Test
	public void darDeBajaUnJugadorRegistrado()
			throws EquipoNoEncontradoException, JugadorNoFederadoException, JugadorNoEncontradoException {
		liga.registrarEquipo("Boca");

		assertTrue(liga.agregarJugadorAlEquipo((Jugador) jugador1, "Boca"));
		assertTrue(liga.darDeBajaJugador("48067068"));
	}

	@Test
	public void queSePuedaActualizarElNumeroDeCamisetaDeUnJugador() throws JugadorNoEncontradoException, EquipoNoEncontradoException, JugadorNoFederadoException {
		liga.registrarEquipo("Boca");

		assertTrue(liga.agregarJugadorAlEquipo((Jugador) jugador1, "Boca"));
		assertTrue(liga.actualizarNumeroCamisetaJugador("48067068", 11));
	}

	@Test(expected = JugadorNoEncontradoException.class)
	public void lanzarJugadorNoEncontradoExceptionCuandoSeIntenteOperarConJugadorInexistente()
			throws JugadorNoEncontradoException {
		liga.actualizarNumeroCamisetaJugador("48067068", 11);
	}

	@Test(expected = JugadorNoFederadoException.class)
	public void lanzarJugadorNoFederadoExceptionCuandoSeAgregaJugadorSinAptoMedico() throws JugadorNoFederadoException, EquipoNoEncontradoException {
		liga.agregarJugadorAlEquipo((Jugador) jugador2, "Boca");
	}

	// Control de Plantilla y Partidos

	@Test
	public void controlarLaCantidadDeJugadoresAgregadosAlEquipo() {
		liga.agregarJugadorAlEquipo((Jugador)jugador1, "Boca");
		liga.agregarJugadorAlEquipo((Jugador)jugador3, "Boca");

		Integer cantidadJugadores = liga.obtenerCantidadJugadoresEquipo("boca");

		assertEquals(2, cantidadJugadores, 0.0);

	}

	@Test
	public void alQuitarUnJugadorLaCantidadDeMiembrosDebeDisminuirEnElEquipo() {
		liga.agregarJugadorAlEquipo(jugador1, "boca");
		liga.agregarJugadorAlEquipo(jugador3, "boca");

		liga.darDeBajaJugador("48067068");

		Integer cantidadJugadores = liga.obtenerCantidadJugadoresEquipo("boca");

		assertEquals(1, cantidadJugadores, 0.0);
	}

	@Test
	public void alRealizarUnPartidoAumentanLosPartidosJugadosDelEquipo() {

	}

	@Test
	public void lanzarEquipoIncompletoExceptionCuandoSeIntenteJugarSinSuficientesJugadores() {

	}

	@Test
	public void lanzarPosicionInvalidaExceptionCuandoHayDosLiberos() {

	}

	@Test
	public void lanzarCupoLlenoExceptionCuandoNoHayLugarEnLaPlantilla() {

	}

	// Cálculo de Puntos y Viáticos

	@Test
	public void calcularLosPuntosTablaPorPartidoGanadoTresCero() {

	}

	@Test
	public void calcularLosPuntosTablaPorPartidoPerdidoEnTieBreak() {

	}

	@Test
	public void calcularElViaticoFinalDelArbitroConAumentoDePlayOffs() {

	}

	// Alta de Usuario

	@Test
	public void registrarUnArbitroCorrectamente() {

	}

	@Test
	public void lanzarDocumentoInvalidoException() {

	}

	@Test
	public void noSePuedenRegistrarDosMiembrosConElMismoDNI() {

	}

	// Ordenamiento

	@Test
	public void ordenarJugadoresPorNombreDeFormaAscendente() {

	}

	@Test
	public void ordenarLosEquiposPorPuntosDescendenteYDiferenciaDeSets() {

	}

	@Test
	public void lanzarNoHayEquiposException() {

	}

	@Test
	public void lanzarNoHayPartidosException() {

	}
}
