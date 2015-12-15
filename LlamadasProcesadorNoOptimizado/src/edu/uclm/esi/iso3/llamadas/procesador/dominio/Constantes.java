package edu.uclm.esi.iso3.llamadas.procesador.dominio;

public class Constantes {
	//public static String directorioRaiz = "/Users/Maco/Documents/workspaceAspectJ/LlamadasGenerador/resources";
	public static String llamadasRecibidas="/llamadasRecibidas/";
	public static String llamadasProcesadas="/llamadasProcesadas/";
	public static String facturas="/facturas/";
	
	public static final int PLANA = 0;	// Cuota fija: 250 euros; establec. llamada: 0,15
	public static final int CINCUENTA_MINUTOS = 1;	// Cuota fija: 25 euros; 50 minutos gratis; luego, establecimiento: 0,20 + 0,01 euros/segundo
	public static final int FIN_DE_SEMANA = 2;	// Cuota fija: 25 euros; gratis en fin de semana; resto, establecimiento: 0,35 + 0,01 euros/segundo
	public static final int TARDES = 3;	// Cuota fija: 25 euros; Gratis de 16 a 24; otras, establecimiento: 0,30 + 0,008 euros/segundo

}
