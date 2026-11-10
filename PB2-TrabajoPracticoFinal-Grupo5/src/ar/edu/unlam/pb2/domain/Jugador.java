package ar.edu.unlam.pb2.domain;

import java.time.LocalDate;

public class Jugador extends Miembro implements Comparable<Jugador>{

	private Integer numCamiseta;
	private PosicionEnum posicionEnum;
	private Boolean tieneAptoMedico;
	private int rematesTotales;
    private int rematesExitosos;

	public Jugador(String nombre, String dni, LocalDate fechaDeNacimiento, Integer numCamiseta,
			PosicionEnum posicionEnum, Boolean tieneAptoMedico) {
		super(nombre, dni, fechaDeNacimiento);
		this.numCamiseta = numCamiseta;
		this.posicionEnum = posicionEnum;
		this.tieneAptoMedico = tieneAptoMedico;
		this.rematesExitosos = 0;
		this.rematesTotales = 0;
	}

	@Override
	public Double calcularPago() {
		return null;
	}

	public Integer getNumCamiseta() {
		return numCamiseta;
	}

	public PosicionEnum getPosicionEnum() {
		return posicionEnum;
	}

	public Boolean getTieneAptoMedico() {
		return tieneAptoMedico;
	}

	public int getRematesTotales() {
		return rematesTotales;
	}

	public int getRematesExitosos() {
		return rematesExitosos;
	}

	public void cambiarNumeroCamiseta(Integer numero) {
		this.numCamiseta = numero;
	}
	
	@Override
	public int compareTo(Jugador o) {
		
		return this.nombre.compareTo(o.getNombre());
	}
	
}
