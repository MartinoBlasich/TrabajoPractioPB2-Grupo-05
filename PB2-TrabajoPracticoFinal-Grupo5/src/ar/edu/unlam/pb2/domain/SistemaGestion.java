package ar.edu.unlam.pb2.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SistemaGestion {
	private static final int CUPO_MAXIMO_JUGADORES = 14;
	private List<Partido> partidos;
	private Map<String, Equipo> equipos;
	private Map<String, Miembro> miembros;

	public SistemaGestion() {
		this.partidos = new ArrayList<Partido>();
		this.equipos = new TreeMap<String, Equipo>();
		this.miembros = new TreeMap<String, Miembro>();
	}

	public Equipo registrarEquipo(String equipo) {
		Equipo nuevoEquipo = new Equipo(equipo.toLowerCase());
		this.equipos.put(equipo.toLowerCase(), nuevoEquipo);
		return nuevoEquipo;
	}

	public Boolean agregarJugadorAlEquipo(Miembro jugador1, String equipo)
			throws EquipoNoEncontradoException, JugadorNoFederadoException, PosicionInvalidaException,
			CupoEquipoLlenoException, DocumentoInvalidoException {

		validarJugador(jugador1);
		Equipo equipoEncontrado = buscarEquipo(equipo.toLowerCase());
		Jugador jugador = (Jugador) jugador1;

		if (equipoEncontrado.getJugadores().size() >= CUPO_MAXIMO_JUGADORES) {
			throw new CupoEquipoLlenoException("El equipo ya alcanzó el cupo máximo de jugadores");
		}

		if (jugador.getPosicionEnum() == PosicionEnum.LIBERO) {
			for (Jugador jugadorRegistrado : equipoEncontrado.getJugadores().values()) {
				if (jugadorRegistrado.getPosicionEnum() == PosicionEnum.LIBERO) {
					throw new PosicionInvalidaException("El equipo no puede tener más de un líbero");
				}
			}
		}

		equipoEncontrado.registrarJugador(jugador);

		return equipoEncontrado.getJugadores().containsKey(jugador1.getDni());
	}

	public boolean darDeBajaJugador(String dniJugador) throws JugadorNoEncontradoException {
		buscarJugador(dniJugador);

		for (Map.Entry<String, Equipo> entry : equipos.entrySet()) {
			Equipo val = entry.getValue();

			if (val.getJugadores().containsKey(dniJugador)) {
				val.getJugadores().remove(dniJugador);
				if (!val.getJugadores().containsKey(dniJugador)) {
					return true;
				}
			}
		}
		return false;
	}

	public Boolean actualizarNumeroCamisetaJugador(String dniJugador, Integer numeroCamiseta)
			throws JugadorNoEncontradoException {

		buscarJugador(dniJugador).cambiarNumeroCamiseta(numeroCamiseta);

		return buscarJugadorEnEquipo(dniJugador).getJugadores().get(dniJugador).getNumCamiseta().equals(numeroCamiseta);
	}

	// Metodos privados

	private Equipo buscarEquipo(String equipo) throws EquipoNoEncontradoException {

		if (!equipos.containsKey(equipo)) {
			throw new EquipoNoEncontradoException("No se encontró el equipo ingresado");
		}

		return this.equipos.get(equipo);

	}

	private void validarJugador(Miembro jugador) throws JugadorNoFederadoException, DocumentoInvalidoException {
		if (!(jugador instanceof Jugador) || !((Jugador) jugador).getTieneAptoMedico()) {
			throw new JugadorNoFederadoException("No se puede registrar un jugador sin apto medico");
		}
		validarDocumento(jugador.getDni());
	}

	private void validarDocumento(String dni) throws DocumentoInvalidoException {
		if (dni.length() < 7 || dni.length() > 8) {
			throw new DocumentoInvalidoException("El DNI debe tener siete u ocho digitos");
		}

		for (int i = 0; i < dni.length(); i++) {
			if (dni.charAt(i) < '0' || dni.charAt(i) > '9') {
				throw new DocumentoInvalidoException("El DNI solo puede contener numeros");
			}
		}
	}


	private Jugador buscarJugador(String dniJugador) throws JugadorNoEncontradoException {

		for (Map.Entry<String, Equipo> entry : equipos.entrySet()) {
			Equipo val = entry.getValue();

			if (val.getJugadores().containsKey(dniJugador)) {
				return val.getJugadores().get(dniJugador);
			}
		}

		throw new JugadorNoEncontradoException("Jugador no encontrado");
	}

	private Equipo buscarJugadorEnEquipo(String dniJugador) throws JugadorNoEncontradoException {
		for (Map.Entry<String, Equipo> entry : equipos.entrySet()) {
			Equipo val = entry.getValue();

			if (val.getJugadores().containsKey(dniJugador)) {
				return val;
			}
		}

		throw new JugadorNoEncontradoException("Jugador no encontrado");
	}

	public Integer obtenerCantidadJugadoresEquipo(String string) {
		return equipos.get(string.toLowerCase()).getJugadores().size();
	}

	public void realizarPartido(Partido partido) throws EquipoIncompletoException {
		if (partido.getEquipoLocal().getJugadores().size() < 6
				|| partido.getEquipoVisitante().getJugadores().size() < 6) {
			throw new EquipoIncompletoException("Cada equipo debe tener al menos seis jugadores");
		}

		this.partidos.add(partido);
		partido.getEquipoLocal().incrementarPartidosJugados();
		partido.getEquipoVisitante().incrementarPartidosJugados();
	}

	public Boolean registrarMiembro(Miembro miembro) throws DocumentoInvalidoException {
		validarDocumento(miembro.getDni());

		if (miembros.containsKey(miembro.getDni())) {
			return false;
		}

		miembros.put(miembro.getDni(), miembro);
		return true;
	}
}
