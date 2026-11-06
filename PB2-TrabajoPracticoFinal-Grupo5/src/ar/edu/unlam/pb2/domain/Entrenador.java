package ar.edu.unlam.pb2.domain;

import java.time.LocalDate;

public class Entrenador extends Miembro {

	private NivelCertificacionEnum nivelCertificacion;
	private Double sueldoBase;

	public Entrenador(String nombre, String dni, LocalDate fechaDeNacimiento, NivelCertificacionEnum nivelCertificacion,
			Double sueldoBase) {
		super(nombre, dni, fechaDeNacimiento);
		this.nivelCertificacion = nivelCertificacion;
		this.sueldoBase = sueldoBase;
	}

	public NivelCertificacionEnum getNivelCertificacion() {
		return nivelCertificacion;
	}

	public Double getSueldoBase() {
		return sueldoBase;
	}

	@Override
	public Double calcularPago() {
		// TODO Auto-generated method stub
		return null;
	}

}
