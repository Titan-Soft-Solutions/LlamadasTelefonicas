package edu.uclm.esi.iso3.llamadas.procesador.dominio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Vector;

public class TarifaCincuentaMinutos extends Tarifas implements Serializable{
	
	private double FIJO_CINCUENTA_MINUTOS = 15;
	private double ESTABLECIMIENTO_CINCUENTA_MINUTOS = 0.15;
	private double MIN_CINCUENTA_MINUTOS = 0.01; //FURA DE HORARIO
	
	public TarifaCincuentaMinutos(){}
	
	public double getImporteSinIVA(Cliente cliente, Vector<LineaFactura> lineas) {
		double result = FIJO_CINCUENTA_MINUTOS;
		
		if(lineas != null){
			for (LineaFactura l : lineas)
				result += l.getImporte();
		}	
		
		return Factura.redondear(result);
	}
	
	public double getCoste(Factura factura, Llamada call){

		double result= ESTABLECIMIENTO_CINCUENTA_MINUTOS;
		
		if (factura.getTiempoTotal() >=60*50)
			result+=MIN_CINCUENTA_MINUTOS *call.getDuracion();
		
		return result;
	}
}
