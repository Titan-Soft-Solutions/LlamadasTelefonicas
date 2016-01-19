package edu.uclm.esi.iso3.llamadas.procesador.dominio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Vector;

public class TarifaTardes extends Tarifas implements Serializable{
	
	private double FIJO_TARDES = 25;
	private double ESTABLECIMIENTO_TARDES = 0.30; //FURA DE HORARIO
	private double MIN_TARDES = 0.08; //FURA DE HORARIO
	
	public TarifaTardes(){}
	
	public double getImporteSinIVA(Cliente cliente, Vector<LineaFactura> lineas) {
		double result = FIJO_TARDES;
		
		for (LineaFactura l : lineas)
			result += l.getImporte();
		
		return Factura.redondear(result);
	}
	public double getCoste(Factura factura, Llamada call){

		if ( call.getFecha().get(Calendar.HOUR_OF_DAY) >=16)
			return 0;
		
		return ESTABLECIMIENTO_TARDES + MIN_TARDES*call.getDuracion();
	}
	

}
