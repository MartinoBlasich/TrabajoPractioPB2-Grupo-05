package ar.edu.unlam.pb2.domain;

import java.time.LocalDate;

public class Arbitro extends Miembro {

	private CategoriaEnum categoria;
	private Double sueldoBase;

	public Arbitro(String nombre, String dni, LocalDate fechaDeNacimiento, CategoriaEnum categoria, Double sueldoBase) {
		super(nombre, dni, fechaDeNacimiento);
		this.categoria = categoria;
		this.sueldoBase = sueldoBase;
	}

	public CategoriaEnum getNivelCertificacion() {
		return categoria;
	}

	public Double getSueldoBase() {
		return sueldoBase;
	}

	@Override
	public Double calcularPago() {
		return null;
	}

}
