package edu.uclm.esi.iso3.llamadas.procesador.dominio;

import java.io.File;
import java.io.FileNotFoundException;

import edu.uclm.esi.iso3.llamadas.procesador.gui.IVentana;


public class ProcesadorDeLlamadas implements Runnable {
	private String directorio;
	private IVentana ventana;

	public ProcesadorDeLlamadas(String directorio, IVentana v) {
		this.directorio=directorio;
		this.ventana=v;
	}
	
	@Override
	public void run() {
		String[] ficheros=this.leerFicheros();
		if (ficheros!=null && ficheros.length>0) {
			procesar(ficheros);
		} else {
			this.ventana.setProgresoMaximo(100);
			this.ventana.showProgreso(100);
		}
		this.ventana.showInfo("Proceso terminado. Se procesaron " + ficheros.length + " llamadas.");
	}
	
	private void procesar(String[] ficheros) {
		int total=ficheros.length;
		int cont=1;
		this.ventana.setProgresoMaximo(total);
		for (String fichero : ficheros) {
			try {
				Factura.procesarLlamada(this.directorio, fichero, this.ventana);
				this.ventana.showProgreso(cont++);
			} catch (Exception e) {
				this.ventana.showError(e.toString());
			}
		}
	}
	
	private String[] leerFicheros() {
		File f = new File(this.directorio + Constantes.llamadasRecibidas);
		String[] result = f.list();
		return result;
	}

}
