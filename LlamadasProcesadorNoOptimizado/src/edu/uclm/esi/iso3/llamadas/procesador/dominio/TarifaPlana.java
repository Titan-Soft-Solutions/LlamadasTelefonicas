package edu.uclm.esi.iso3.llamadas.procesador.dominio;

import java.io.Serializable;
import java.util.Vector;

public class TarifaPlana extends Tarifas implements Serializable{
	
	private double FIJO_PLANA = 250;
	private double ESTABLECIMIENTO_PLANA = 0.15;
	private double MIN_PLANA = 0;
	
	public TarifaPlana(){}
	
	public double getImporteSinIVA(Cliente cliente, Vector<LineaFactura> lineas) {
		
		double result = FIJO_PLANA;

		if(lineas != null){
			for (LineaFactura l : lineas)
				result += l.getImporte();
		}		
		return Factura.redondear(result);
	}
	
	public double getCoste(Factura factura, Llamada call){
		return ESTABLECIMIENTO_PLANA;
	}
	

}
