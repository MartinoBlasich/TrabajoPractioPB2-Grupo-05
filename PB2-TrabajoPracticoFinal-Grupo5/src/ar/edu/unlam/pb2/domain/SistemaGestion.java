package ar.edu.unlam.pb2.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SistemaGestion {
	private List<Partido> partidos;
	private Map<String, Equipo> equipos;

	public SistemaGestion() {
		this.partidos = new ArrayList<Partido>();
		this.equipos = new TreeMap<String, Equipo>();
	}

	public void registrarEquipo(String equipo) {
		this.equipos.put(equipo, new Equipo(equipo));
	}

	public Boolean agregarJugadorAlEquipo(Jugador jugador, String equipo)
			throws EquipoNoEncontradoException, JugadorNoFederadoException {

		validarJugador(jugador);
		buscarEquipo(equipo).registrarJugador(jugador);

		return buscarEquipo(equipo).getJugadores().containsKey(jugador.getDni());
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
	
	public Boolean actualizarNumeroCamisetaJugador(String dniJugador, Integer numeroCamiseta) throws JugadorNoEncontradoException {
		
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

	private void validarJugador(Jugador jugador) throws JugadorNoFederadoException {
		if (!jugador.getTieneAptoMedico()) {
			throw new JugadorNoFederadoException("No se puede registrar un jugador sin apto medico");
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

}
