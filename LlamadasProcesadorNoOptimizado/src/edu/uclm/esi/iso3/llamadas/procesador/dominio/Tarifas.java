package edu.uclm.esi.iso3.llamadas.procesador.dominio;

import java.util.Vector;

abstract class Tarifas {


	protected double FIJO_PLANA = 250;
	protected double ESTABLECIMIENTO_PLANA = 0.15;
	protected double MIN_PLANA = 0;
	
	protected double FIJO_CINCUENTA_MINUTOS = 15;
	protected double ESTABLECIMIENTO_CINCUENTA_MINUTOS = 0.15;
	protected double MIN_CINCUENTA_MINUTOS = 0.01; //FURA DE HORARIO
	
	protected double FIJO_FIN_DE_SEMANA = 25;
	protected double ESTABLECIMIENTO_FIN_DE_SEMANA = 0.35; //FUERA DE HORARIO
	protected double MIN_FIN_DE_SEMANA = 0.01; //FURA DE HORARIO
    
	protected double FIJO_TARDES = 25;
	protected double ESTABLECIMIENTO_TARDES = 0.30; //FURA DE HORARIO
	protected double MIN_TARDES = 0.08; //FURA DE HORARIO

	abstract double getImporteSinIVA(Cliente cliente, Vector<LineaFactura> lineas);
	abstract double getCoste( Factura factura, Llamada call);
	
	abstract double getCostePlana();
	abstract double getCosteCincuentaMinutos(Factura factura, Llamada call);
	abstract double getCosteFinDeSemana(Llamada call);
	abstract double getCosteTardes(Llamada call);
	

}

