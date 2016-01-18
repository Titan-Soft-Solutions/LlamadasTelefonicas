package edu.uclm.esi.iso3.llamadas.procesador.dominio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Vector;

public class TarifaFinDeSemana extends Tarifas implements Serializable{
	
	private double FIJO_FIN_DE_SEMANA = 25;
	private double ESTABLECIMIENTO_FIN_DE_SEMANA = 0.35; //FUERA DE HORARIO
	private double MIN_FIN_DE_SEMANA = 0.01; //FURA DE HORARIO
	
	public TarifaFinDeSemana(){}
	
	public double getImporteSinIVA(Cliente cliente, Vector<LineaFactura> lineas) {
		double result= FIJO_FIN_DE_SEMANA;
		
		for (LineaFactura l : lineas)
			result += l.getImporte();
		
		return Factura.redondear(result);
	}
	
	public double getCoste(Factura factura, Llamada call){
		
		int diaSemana=call.getFecha().get(Calendar.DAY_OF_WEEK);
		double result = 0;
		
		if ( diaSemana!=Calendar.SATURDAY && diaSemana!=Calendar.SUNDAY)
			result= ESTABLECIMIENTO_FIN_DE_SEMANA + MIN_FIN_DE_SEMANA *call.getDuracion();

		return result;
	}
}
