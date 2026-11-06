package ar.edu.unlam.pb2.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Equipo {
	private Map<String, Jugador> jugadores;
	private String nombre;
	private Integer partidosJugados;
	private Integer partidosGanados;
	private Integer puntosTotales;

	public Equipo(String nombre) {
		this.nombre = nombre;
		this.jugadores = new HashMap<String, Jugador>();
		this.partidosGanados = 0;
		this.partidosJugados = 0;
		this.puntosTotales = 0;
	}

	public Map<String, Jugador> getJugadores() {
		return jugadores;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getPartidosJugados() {
		return partidosJugados;
	}

	public Integer getPartidosGanados() {
		return partidosGanados;
	}

	public Integer getPuntosTotales() {
		return puntosTotales;
	}

	public Boolean registrarJugador(Jugador jugador) {
		this.jugadores.put(jugador.getDni(), jugador);
		return jugadores.containsKey(jugador.getDni());
	}

	@Override
	public int hashCode() {
		return Objects.hash(jugadores, nombre, partidosGanados, partidosJugados, puntosTotales);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipo other = (Equipo) obj;
		return Objects.equals(jugadores, other.jugadores) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(partidosGanados, other.partidosGanados)
				&& Objects.equals(partidosJugados, other.partidosJugados)
				&& Objects.equals(puntosTotales, other.puntosTotales);
	}

	
}
