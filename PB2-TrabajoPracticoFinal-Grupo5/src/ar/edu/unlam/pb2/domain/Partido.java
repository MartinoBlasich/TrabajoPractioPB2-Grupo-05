package ar.edu.unlam.pb2.domain;

public class Partido {
	private Equipo equipoLocal;
	private Equipo equipoVisitante;
	private Arbitro arbitro;
	private Boolean esPlayOff;
	private Integer setsEquipoLocal;
	private Integer setsEquipoVisitante;

	public Partido(Equipo equipoLocal, Equipo equipoVisitante, Arbitro arbitro, Boolean esPlayOff,
			Integer setsEquipoLocal, Integer setsEquipoVisitante) {
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.setsEquipoLocal = setsEquipoLocal;
		this.setsEquipoVisitante = setsEquipoVisitante;
		this.arbitro = arbitro;
		this.esPlayOff = esPlayOff;
	}

	public Equipo getEquipoLocal() {
		return equipoLocal;
	}

	public Equipo getEquipoVisitante() {
		return equipoVisitante;
	}
	
	public Double calcularViaticoArbitro() {
		if (esPlayOff) {
			return arbitro.getSueldoBase() * 1.2;
		}
		return arbitro.getSueldoBase();
	}

	public void calcularPuntos() throws PartidoInvalidoException {
		if (setsEquipoLocal == null || setsEquipoVisitante == null
				|| !((setsEquipoLocal == 3 && setsEquipoVisitante >= 0 && setsEquipoVisitante < 3)
						|| (setsEquipoVisitante == 3 && setsEquipoLocal >= 0 && setsEquipoLocal < 3))) {
			throw new PartidoInvalidoException("El resultado del partido no es válido");
		}

		equipoLocal.actualizarDiferenciaDeSets(setsEquipoLocal, setsEquipoVisitante);
		equipoVisitante.actualizarDiferenciaDeSets(setsEquipoVisitante, setsEquipoLocal);

		if (setsEquipoLocal == 3 && setsEquipoVisitante >= 0 && setsEquipoVisitante < 3) {
			equipoLocal.incrementarPartidosGanados();
			if (setsEquipoVisitante == 2) {
				equipoLocal.agregarPuntos(2);
				equipoVisitante.agregarPuntos(1);
			} else {
				equipoLocal.agregarPuntos(3);
			}
		} else if (setsEquipoVisitante == 3 && setsEquipoLocal >= 0 && setsEquipoLocal < 3) {
			equipoVisitante.incrementarPartidosGanados();
			if (setsEquipoLocal == 2) {
				equipoVisitante.agregarPuntos(2);
				equipoLocal.agregarPuntos(1);
			} else {
				equipoVisitante.agregarPuntos(3);
			}
		}
	}
	
}
