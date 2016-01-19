package edu.uclm.esi.iso3.llamadas.procesador.dominio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Vector;

import edu.uclm.esi.iso3.llamadas.procesador.gui.IVentana;


public class Factura implements Serializable {
	private Cliente cliente;
	private Vector<LineaFactura> lineas;
	private Tarifas tarifa;
	
	private transient static String directorio;
	
	public Factura(Cliente cli) throws IOException{
		this.cliente=cli;
		this.lineas=new Vector<LineaFactura>();		
		
		switch(cliente.getTarifa()){
		case Constantes.PLANA:
			tarifa = new TarifaPlana();
			break;
		case Constantes.CINCUENTA_MINUTOS:
			tarifa = new TarifaCincuentaMinutos();
			break;
		case Constantes.FIN_DE_SEMANA:
			tarifa = new TarifaFinDeSemana();
			break;
		case Constantes.TARDES:
			tarifa = new TarifaTardes();
			break;
		}
		
		String fichero=Factura.directorio + Constantes.facturas + cli.getTelefono() + ".txt";
		FileOutputStream fos=new FileOutputStream(fichero);
		ObjectOutputStream oos=new ObjectOutputStream(fos);
		oos.writeObject(this);
		fos.close();
	}

	public Factura(Llamada call) throws IOException {
		this.cliente=Control.getControl(this.directorio).findClienteByNumero(call.getOrigen());
		this.lineas=new Vector<LineaFactura>();
		
		switch(cliente.getTarifa()){
		case Constantes.PLANA:
			tarifa = new TarifaPlana();
			break;
		case Constantes.CINCUENTA_MINUTOS:
			tarifa = new TarifaCincuentaMinutos();
			break;
		case Constantes.FIN_DE_SEMANA:
			tarifa = new TarifaFinDeSemana();
			break;
		case Constantes.TARDES:
			tarifa = new TarifaTardes();
			break;
		}
		
		String fichero=Factura.directorio + Constantes.facturas + call.getOrigen() + ".txt";
		FileOutputStream fos=new FileOutputStream(fichero);
		ObjectOutputStream oos=new ObjectOutputStream(fos);
		oos.writeObject(this);
		fos.close();
		
		
	}

	public Factura(String directorio, Llamada call) {
		String fichero=directorio + Constantes.facturas + call.getOrigen() + ".txt";
		boolean existe=false;
		try {
			FileInputStream fi=new FileInputStream(fichero);
			fi.close();
			existe=true;
		} catch (FileNotFoundException e1) {
			existe=false;
		}
		catch (IOException e) {
		}
		if (existe) {
			try {
				FileWriter fis=new FileWriter(directorio + Constantes.facturas + call.getOrigen() + ".txt", true);
				fis.append(call.toString());
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				FileWriter fis=new FileWriter(directorio + Constantes.facturas + call.getOrigen() + ".txt", true);
				fis.append("Factura");
				fis.append(call.toString());
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void procesarLlamada(String directorioRaiz, String fichero, IVentana ventana) throws IOException, ClassNotFoundException {
		
		Factura.directorio=directorioRaiz;
		mover(directorioRaiz, fichero);
		
		FileInputStream fis=new FileInputStream(directorioRaiz + Constantes.llamadasProcesadas + fichero);
		ObjectInputStream ois=new ObjectInputStream(fis);
		Llamada call=(Llamada) ois.readObject();
		fis.close();
		
		Control col = Control.getControl(directorioRaiz);
		Cliente cliente = col.findClienteByNumero(call.getOrigen());
		Factura f=tieneFactura(directorioRaiz, cliente);
		if (f==null) {
			f=new Factura(call);
		}
		ventana.showInfo(call.toString());
		f.add(call);
		
		fichero=Factura.directorio + Constantes.facturas + call.getOrigen() + ".txt";
		FileOutputStream fos=new FileOutputStream(fichero);
		ObjectOutputStream oos=new ObjectOutputStream(fos);
		oos.writeObject(f);
		fos.close();
	}
	
	public static void procesarFacturasSinLlamadas(String directorioRaiz, Cliente cli) throws IOException{	
		Factura.directorio=directorioRaiz;
		Factura f= new Factura(cli);		
	}
	

	private void add(Llamada call) {
		LineaFactura linea=new LineaFactura(this, call);
		this.lineas.add(linea);
	}

	private static Factura tieneFactura(String directorio, Cliente cliente) {
		Factura result=null;
		String fichero=directorio + Constantes.facturas + cliente.getTelefono() + ".txt";
		try {
			FileInputStream fis=new FileInputStream(fichero);
			ObjectInputStream ois=new ObjectInputStream(fis);
			result=(Factura) ois.readObject();
			fis.close();
			return result;
		} catch (FileNotFoundException e1) {
			return result;
		}
		catch (IOException e) {
			return result;
		}
		catch (ClassNotFoundException e) {
			return null;
		}
	}

	private static void mover(String directorio, String fichero) {
		File fi=new File(directorio + Constantes.llamadasRecibidas + fichero);
		File fo=new File(directorio + Constantes.llamadasProcesadas + fichero);
		fi.renameTo(fo);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public int getTiempoTotal() {
		int result=0;
		for (LineaFactura l : this.lineas)
			result+=l.getDuracion();
		return result;
	}
	public double getImporteSinIVA(){
		return tarifa.getImporteSinIVA(this.cliente, this.lineas);
	}

	public static double redondear(double x) {
			return Math.rint(x*100)/100;
	}

	public int getNumeroDeLineas() {
		return this.lineas.size();
	}

	public double getCoste(Factura factura, Llamada call) {
		return tarifa.getCoste(factura,call);
	}
}
