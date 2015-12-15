package edu.uclm.esi.iso3.llamadas.procesador.dominio;

import java.io.Serializable;

public class Cliente implements Serializable {
	private int id;
	private String nombre;
	private String apellido;
	private String dni;
	private String telefono;
	private int tarifa;

	public Cliente(int id, String nombre, String apellido, String dni, String telefono, int tarifa) {
		this.id=id;
		this.nombre=nombre;
		this.apellido=apellido;
		this.dni=dni;
		this.telefono=telefono;
		this.tarifa=tarifa;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getDni() {
		return dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public int getTarifa() {
		return this.tarifa;
	}
}
