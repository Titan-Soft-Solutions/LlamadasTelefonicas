package edu.uclm.esi.iso3.llamadas.procesador.dominio;

import java.io.Serializable;
import java.util.Vector;

abstract class Tarifas implements Serializable {

	abstract double getImporteSinIVA(Cliente cliente, Vector<LineaFactura> lineas);
	abstract double getCoste( Factura factura, Llamada call);
	

}

