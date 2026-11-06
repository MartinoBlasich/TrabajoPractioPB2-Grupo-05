package ar.edu.unlam.pb2.domain;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Miembro {
	protected String nombre;
	protected String dni;
	protected LocalDate fechaDeNacimiento;

	public Miembro(String nombre, String dni, LocalDate fechaDeNacimiento) {
		this.nombre = nombre;
		this.dni = dni;
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDni() {
		return dni;
	}

	public LocalDate getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}
	
	public abstract Double calcularPago();

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Miembro other = (Miembro) obj;
		return Objects.equals(dni, other.dni);
	}

	
}
