package ar.edu.unlam.pb2.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.pb2.domain.*;

public class SistemaGestionVoleyTest {

	private SistemaGestion liga;
	private Miembro jugador1;
	private Miembro jugador2;
	private Miembro jugador3;
	private Arbitro arbitro;
	private Partido partido;

	@Before
	public void setUp() {
		liga = new SistemaGestion();
		jugador1 = new Jugador("Martino", "48067068", LocalDate.of(2008, 03, 04), 6, PosicionEnum.PUNTA, true);
		jugador2 = new Jugador("Daniel", "44089012", LocalDate.of(2004, 05, 07), 43, PosicionEnum.CENTRAL, false);
		jugador3 = new Jugador("Agus", "47034876", LocalDate.of(2008, 06, 13), 38, PosicionEnum.CENTRAL, true);
		arbitro = new Arbitro("Teo", "34958857", LocalDate.of(2002, 03, 07), CategoriaEnum.SUB18, 20000.0);
	}

	// Gestión de Jugadores
	@Test
	public void agregarCorrectamenteUnJugadorAlEquipo() throws EquipoNoEncontradoException, JugadorNoFederadoException,
			PosicionInvalidaException, CupoEquipoLlenoException, DocumentoInvalidoException {
		liga.registrarEquipo("Boca");

		assertTrue(liga.agregarJugadorAlEquipo((Jugador) jugador1, "Boca"));
	}

	@Test
	public void darDeBajaUnJugadorRegistrado()
			throws EquipoNoEncontradoException, JugadorNoFederadoException, JugadorNoEncontradoException,
			PosicionInvalidaException, CupoEquipoLlenoException, DocumentoInvalidoException {
		liga.registrarEquipo("Boca");

		assertTrue(liga.agregarJugadorAlEquipo((Jugador) jugador1, "Boca"));
		assertTrue(liga.darDeBajaJugador("48067068"));
	}

	@Test
	public void queSePuedaActualizarElNumeroDeCamisetaDeUnJugador()
			throws JugadorNoEncontradoException, EquipoNoEncontradoException, JugadorNoFederadoException,
			PosicionInvalidaException, CupoEquipoLlenoException, DocumentoInvalidoException {
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
	public void lanzarJugadorNoFederadoExceptionCuandoSeAgregaJugadorSinAptoMedico()
			throws JugadorNoFederadoException, EquipoNoEncontradoException, PosicionInvalidaException,
			CupoEquipoLlenoException, DocumentoInvalidoException {
		liga.agregarJugadorAlEquipo((Jugador) jugador2, "Boca");
	}

	// Control de Plantilla y Partidos

	@Test
	public void controlarLaCantidadDeJugadoresAgregadosAlEquipo()
			throws EquipoNoEncontradoException, JugadorNoFederadoException, PosicionInvalidaException,
			CupoEquipoLlenoException, DocumentoInvalidoException {
		liga.registrarEquipo("Boca");
		liga.agregarJugadorAlEquipo((Jugador) jugador1, "Boca");
		liga.agregarJugadorAlEquipo((Jugador) jugador3, "Boca");

		Integer cantidadJugadores = liga.obtenerCantidadJugadoresEquipo("Boca");

		assertEquals(2, cantidadJugadores, 0.0);

	}

	@Test
	public void alQuitarUnJugadorLaCantidadDeMiembrosDebeDisminuirEnElEquipo()
			throws EquipoNoEncontradoException, JugadorNoFederadoException, PosicionInvalidaException,
			CupoEquipoLlenoException, JugadorNoEncontradoException, DocumentoInvalidoException {
		liga.registrarEquipo("Boca");
		liga.agregarJugadorAlEquipo(jugador1, "boca");
		liga.agregarJugadorAlEquipo(jugador3, "boca");

		liga.darDeBajaJugador("48067068");

		Integer cantidadJugadores = liga.obtenerCantidadJugadoresEquipo("boca");

		assertEquals(1, cantidadJugadores, 0.0);
	}

	@Test
	public void alRealizarUnPartidoAumentanLosPartidosJugadosDelEquipo()
			throws EquipoIncompletoException, EquipoNoEncontradoException, JugadorNoFederadoException,
			PosicionInvalidaException, CupoEquipoLlenoException, DocumentoInvalidoException {

		Equipo equipoLocal = liga.registrarEquipo("Boca");

		// Agregar 6 jugadores al equipo
		for (int i = 0; i < 7; i++) {
			Jugador jugador = new Jugador("Jugador" + i, "3000000" + i, LocalDate.of(2007, 6, 11), i + 1,
					PosicionEnum.PUNTA, true);
			liga.agregarJugadorAlEquipo(jugador, "Boca");
		}

		Equipo equipoVisitante = liga.registrarEquipo("River");

		// Agregar 6 jugadores al equipo
		for (int i = 0; i < 7; i++) {
			Jugador jugador = new Jugador("Jugador" + i, "3000000" + i, LocalDate.of(2007, 6, 11), i + 1,
					PosicionEnum.PUNTA, true);
			liga.agregarJugadorAlEquipo(jugador, "River");
		}

		partido = new Partido(equipoLocal, equipoVisitante, arbitro, true, 2, 3);

		liga.realizarPartido(partido);

		assertEquals(1, equipoLocal.getPartidosJugados(), 0.0);
		assertEquals(1, equipoVisitante.getPartidosJugados(), 0.0);
	}

	@Test(expected = EquipoIncompletoException.class)
	public void lanzarEquipoIncompletoExceptionCuandoSeIntenteJugarSinSuficientesJugadores()
			throws EquipoIncompletoException {
		Equipo equipoLocal = new Equipo("Boca");
		Equipo equipoVisitante = new Equipo("River");
		partido = new Partido(equipoLocal, equipoVisitante, arbitro, true, 2, 3);

		liga.realizarPartido(partido);
	}

	@Test(expected = PosicionInvalidaException.class)
	public void lanzarPosicionInvalidaExceptionCuandoHayDosLiberos()
			throws EquipoNoEncontradoException, JugadorNoFederadoException, PosicionInvalidaException,
			CupoEquipoLlenoException, DocumentoInvalidoException {
		liga.registrarEquipo("Boca");
		Jugador primerLibero = new Jugador("Libero1", "10000001", LocalDate.of(2007, 9, 3), 1, PosicionEnum.LIBERO,
				true);
		Jugador segundoLibero = new Jugador("Libero2", "10000002", LocalDate.of(2007, 11, 5), 2, PosicionEnum.LIBERO,
				true);

		liga.agregarJugadorAlEquipo(primerLibero, "Boca");
		liga.agregarJugadorAlEquipo(segundoLibero, "Boca");
	}

	@Test(expected = CupoEquipoLlenoException.class)
	public void lanzarCupoLlenoExceptionCuandoNoHayLugarEnLaPlantilla()
			throws EquipoNoEncontradoException, JugadorNoFederadoException, PosicionInvalidaException,
			CupoEquipoLlenoException, DocumentoInvalidoException {
		liga.registrarEquipo("Boca");

		// Registro de 10 jugadores
		for (int i = 0; i < 10; i++) {
			Jugador jugador = new Jugador("Jugador" + i, "2000000" + i, LocalDate.of(2007, 6, 11), i + 1,
					PosicionEnum.PUNTA, true);
			liga.agregarJugadorAlEquipo(jugador, "Boca");
		}

		// Registro de 4 jugadores
		for (int i = 0; i < 4; i++) {
			Jugador jugador = new Jugador("Jugador" + i, "3000000" + i, LocalDate.of(2007, 6, 11), i + 1,
					PosicionEnum.PUNTA, true);
			liga.agregarJugadorAlEquipo(jugador, "Boca");
		}

		Jugador jugadorFueraDeCupo = new Jugador("Jugador15", "20000015", LocalDate.of(2007, 3, 3), 15,
				PosicionEnum.PUNTA, true);
		liga.agregarJugadorAlEquipo(jugadorFueraDeCupo, "Boca");
	}

	// Cálculo de Puntos y Viáticos

	@Test
	public void calcularLosPuntosTablaPorPartidoGanadoTresCero() throws PartidoInvalidoException {
		Equipo equipoGanador = liga.registrarEquipo("Boca");
		Equipo equipoPerdedor = liga.registrarEquipo("River");
		partido = new Partido(equipoGanador, equipoPerdedor, arbitro, true, 3, 0);

		partido.calcularPuntos();

		assertEquals(3, equipoGanador.getPuntosTotales(), 0.0);
		assertEquals(0, equipoPerdedor.getPuntosTotales(), 0.0);
	}

	@Test
	public void calcularLosPuntosTablaPorPartidoGanadoTresUno() throws PartidoInvalidoException {
		Equipo equipoGanador = liga.registrarEquipo("Boca");
		Equipo equipoPerdedor = liga.registrarEquipo("River");
		partido = new Partido(equipoGanador, equipoPerdedor, arbitro, true, 3, 1);

		partido.calcularPuntos();

		assertEquals(3, equipoGanador.getPuntosTotales(), 0.0);
		assertEquals(0, equipoPerdedor.getPuntosTotales(), 0.0);
	}

	@Test
	public void calcularLosPuntosTablaPorPartidoPerdidoEnTieBreak() throws PartidoInvalidoException {
		Equipo equipoGanador = liga.registrarEquipo("Boca");
		Equipo equipoPerdedor = liga.registrarEquipo("River");
		partido = new Partido(equipoPerdedor, equipoGanador, arbitro, true, 2, 3);

		partido.calcularPuntos();

		assertEquals(1, equipoPerdedor.getPuntosTotales(), 0.0);
		assertEquals(2, equipoGanador.getPuntosTotales(), 0.0);
	}

	@Test
	public void calcularElViaticoFinalDelArbitroConAumentoDePlayOffs() {
		Equipo equipoLocal = liga.registrarEquipo("Boca");
		Equipo equipoVisitante = liga.registrarEquipo("River");
		partido = new Partido(equipoLocal, equipoVisitante, arbitro, true, 2, 3);

		assertEquals(24000.0, partido.calcularViaticoArbitro(), 0.0);
	}

	// Alta de Usuario

	@Test
	public void registrarUnArbitroCorrectamente() throws DocumentoInvalidoException {

		assertTrue(liga.registrarMiembro(arbitro));
	}

	@Test(expected = DocumentoInvalidoException.class)
	public void lanzarDocumentoInvalidoException() throws DocumentoInvalidoException {
		Arbitro arbitro = new Arbitro("Arbitro", "1234A678", LocalDate.of(1985, 10, 1), CategoriaEnum.DIVISION_DE_HONOR,
				1000.0);

		liga.registrarMiembro(arbitro);
	}

	@Test
	public void noSePuedenRegistrarDosMiembrosConElMismoDNI() throws DocumentoInvalidoException {
		Arbitro arbitro = new Arbitro("Arbitro", "12345678", LocalDate.of(1985, 10, 1), CategoriaEnum.DIVISION_DE_HONOR,
				1000.0);
		Jugador jugador = new Jugador("Jugador", "12345678", LocalDate.of(2007, 8, 11), 1, PosicionEnum.PUNTA, true);

		assertTrue(liga.registrarMiembro(arbitro));
		assertFalse(liga.registrarMiembro(jugador));
	}

	// Ordenamiento

	@Test
	public void ordenarJugadoresPorNombreDeFormaAscendente() {
		Equipo boca = liga.registrarEquipo("Boca");
		Equipo river = liga.registrarEquipo("River");
		boca.registrarJugador(
				new Jugador("Sofia", "30000001", LocalDate.of(2007, 10, 12), 1, PosicionEnum.PUNTA, true));
		river.registrarJugador(
				new Jugador("Ana", "30000002", LocalDate.of(2007, 11, 4), 2, PosicionEnum.CENTRAL, true));
		boca.registrarJugador(
				new Jugador("Lucia", "30000003", LocalDate.of(2007, 3, 6), 3, PosicionEnum.OPUESTO, true));

		TreeSet<Jugador> jugadores = liga.obtenerJugadoresOrdenadosPorNombre();

		assertEquals("Ana", jugadores.getFirst().getNombre());
		assertEquals("Sofia", jugadores.getLast().getNombre());
	}

	@Test
	public void ordenarLosEquiposPorPuntosDescendenteYDiferenciaDeSets()
			throws EquipoIncompletoException, NoHayEquiposException, NoHayPartidosException, PartidoInvalidoException,
			EquipoNoEncontradoException, JugadorNoFederadoException, PosicionInvalidaException,
			CupoEquipoLlenoException, DocumentoInvalidoException {

		Equipo boca = liga.registrarEquipo("Boca");
		// Agregar 6 jugadores al equipo
		for (int i = 0; i < 6; i++) {
			Jugador jugador = new Jugador("Jugador" + i, "1234567" + i, LocalDate.of(2007, 6, 6), i + 1,
					PosicionEnum.PUNTA, true);
			liga.agregarJugadorAlEquipo(jugador, "Boca");
		}

		//
		Equipo river = liga.registrarEquipo("River");
		// Agregar 6 jugadores al equipo
		for (int i = 0; i < 6; i++) {
			Jugador jugador = new Jugador("Jugador" + i, "1234567" + i, LocalDate.of(2007, 6, 6), i + 1,
					PosicionEnum.PUNTA, true);
			liga.agregarJugadorAlEquipo(jugador, "River");
		}

		//
		Equipo lanus = liga.registrarEquipo("Lanus");
		// Agregar 6 jugadores al equipo
		for (int i = 0; i < 6; i++) {
			Jugador jugador = new Jugador("Jugador" + i, "1234567" + i, LocalDate.of(2007, 6, 6), i + 1,
					PosicionEnum.PUNTA, true);
			liga.agregarJugadorAlEquipo(jugador, "Lanus");
		}

		partido = new Partido(boca, river, arbitro, true, 3, 0);
		liga.realizarPartido(partido);
		partido.calcularPuntos();

		partido = new Partido(river, lanus, arbitro, true, 3, 1);
		liga.realizarPartido(partido);
		partido.calcularPuntos();

		TreeSet<Equipo> equipos = liga.obtenerTablaDePosiciones();

		assertEquals("boca", equipos.getFirst().getNombre());
		assertEquals("lanus", equipos.getLast().getNombre());
	}

	@Test(expected = NoHayEquiposException.class)
	public void lanzarNoHayEquiposException() throws NoHayEquiposException, NoHayPartidosException {
		liga.obtenerTablaDePosiciones();
	}

	@Test(expected = NoHayPartidosException.class)
	public void lanzarNoHayPartidosException() throws NoHayEquiposException, NoHayPartidosException {
		liga.registrarEquipo("Boca");
		liga.obtenerTablaDePosiciones();
	}

}
