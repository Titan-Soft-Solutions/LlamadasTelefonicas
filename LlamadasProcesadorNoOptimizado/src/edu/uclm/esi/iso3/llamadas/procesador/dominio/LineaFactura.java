package edu.uclm.esi.iso3.llamadas.procesador.dominio;

import java.io.Serializable;
import java.util.Calendar;


public class LineaFactura implements Serializable {
	private Calendar fecha;
	private String numeroDestino;
	private int duracionHoras, duracionMinutos, duracionSegundos;
	private Factura factura;
	private double importe;

	public LineaFactura(Factura factura, Llamada call) {
		this.fecha=call.getFecha();
		this.numeroDestino=call.getDestino();
		setDuracion(call.getDuracion());
		this.factura=factura;
		this.importe=Factura.redondear(this.factura.getCoste(this.factura, call));
	}
	
	private void setDuracion(int duracion) {
		this.duracionHoras=duracion/3600;
		this.duracionMinutos=(duracion-60*this.duracionHoras)/60;
		this.duracionSegundos=duracion-this.duracionHoras*3600-this.duracionMinutos*60;
	}

	@Override
	public String toString() {
		String fechaYHora = this.fecha.get(Calendar.DATE) + "/" + (1+this.fecha.get(Calendar.MONTH)) + "/" + this.fecha.get(Calendar.YEAR) + "; " + 
			this.fecha.get(Calendar.HOUR) + ":" + this.fecha.get(Calendar.MINUTE) + ":" + this.fecha.get(Calendar.SECOND);
		String result=fechaYHora + "\t" + this.numeroDestino + "\t" + (this.duracionHoras>0 ? this.duracionHoras + ":" : "") + 
			this.duracionMinutos + ":" + this.duracionSegundos + "\t" + this.importe;
		return result;
	}

	public int getDuracion() {
		return this.duracionHoras*3600 + this.duracionMinutos*60 + this.duracionSegundos;
	}

	public double getImporte() {
		return importe;
	}
}
