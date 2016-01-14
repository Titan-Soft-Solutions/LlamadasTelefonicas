package edu.uclm.esi.iso3.llamadas.procesador.dominio;

import java.util.Calendar;
import java.util.Vector;

public class TarifasActuales extends Tarifas{
	
	public TarifasActuales(){}
	
	public double getImporteSinIVA(Cliente cliente, Vector<LineaFactura> lineas) {
		double result=0;
		
		switch (cliente.getTarifa()) {
		
		case Constantes.PLANA :
			result = FIJO_PLANA;
	    break;
		case Constantes.CINCUENTA_MINUTOS :
			result = FIJO_CINCUENTA_MINUTOS;
		break;
		case Constantes.FIN_DE_SEMANA :
			result = FIJO_FIN_DE_SEMANA;
		break;
		case Constantes.TARDES :
			result = FIJO_TARDES;
		break;
		default:
			return 0;
		}		
		for (LineaFactura l : lineas)
			result += l.getImporte();
		
		result = Factura.redondear(result);
		return result;
	}
	public double getCoste(Factura factura, Llamada call){
		double result = 0;
		
		switch (factura.getCliente().getTarifa()){
		
		case Constantes.PLANA:
			result = getCostePlana();
		break;
		case Constantes.CINCUENTA_MINUTOS:
			result = getCosteCincuentaMinutos(factura,call);
		break;
		case Constantes.FIN_DE_SEMANA:
			result = getCosteFinDeSemana(call);
		break;
		case Constantes.TARDES:
			result = getCosteTardes(call);
		break;
		
		}
		return result;
	}
	
	
	public double getCostePlana(){
		return ESTABLECIMIENTO_PLANA;
	}
	public double getCosteCincuentaMinutos(Factura factura, Llamada call){
		double result= ESTABLECIMIENTO_CINCUENTA_MINUTOS;
		
		if (factura.getTiempoTotal() >=60*50)
			result+=MIN_CINCUENTA_MINUTOS *call.getDuracion();
		
		return result;
	}
	public double getCosteFinDeSemana(Llamada call){
		int diaSemana=call.getFecha().get(Calendar.DAY_OF_WEEK);
		double result = 0;
		
		if ( diaSemana!=Calendar.SATURDAY && diaSemana!=Calendar.SUNDAY)
			result= ESTABLECIMIENTO_TARDES + MIN_TARDES *call.getDuracion();

		return result;
	}
	public double getCosteTardes(Llamada call){
		if ( call.getFecha().get(Calendar.HOUR_OF_DAY) >=16)
			return 0;
		
		return ESTABLECIMIENTO_FIN_DE_SEMANA + MIN_FIN_DE_SEMANA*call.getDuracion();
	}
	

}
