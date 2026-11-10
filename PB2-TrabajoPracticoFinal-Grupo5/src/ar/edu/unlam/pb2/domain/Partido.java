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
	
}
