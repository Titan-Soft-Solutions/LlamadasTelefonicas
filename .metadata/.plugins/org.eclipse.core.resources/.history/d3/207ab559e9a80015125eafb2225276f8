package edu.uclm.esi.iso3.llamadas.procesador.dominio.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import edu.uclm.esi.iso3.llamadas.procesador.dominio.Cliente;
import edu.uclm.esi.iso3.llamadas.procesador.dominio.Constantes;
import edu.uclm.esi.iso3.llamadas.procesador.dominio.Control;
import edu.uclm.esi.iso3.llamadas.procesador.dominio.Llamada;
import edu.uclm.esi.iso3.llamadas.procesador.dominio.ProcesadorDeLlamadas;
import edu.uclm.esi.iso3.llamadas.procesador.dominio.Factura;
import edu.uclm.esi.iso3.llamadas.procesador.gui.IVentana;


public class ProcesadorDeLlamadasTest extends TestCase implements IVentana {
	private String directorioRaiz="C:\\Users\\SrKlein\\workspace\\resources";
	private Cliente clienteTarifaPlana, cliente50Minutos, clienteFinDeSemana, clienteTardes;
	private ProcesadorDeLlamadas procesador;

	public void setUp() throws Exception {
		limpiar(this.directorioRaiz + Constantes.llamadasRecibidas);
		limpiar(this.directorioRaiz + Constantes.llamadasProcesadas);
		limpiar(this.directorioRaiz + Constantes.facturas);
		
		this.clienteTarifaPlana=new Cliente(280700, "Nohely", "SUAREZ", "452998602", "600280699", 0);
		this.cliente50Minutos=new Cliente(280300, "Ismael", "PEREZ", "142833997", "600280299", 1);
		this.clienteFinDeSemana=new Cliente(176700, "Natali", "MANZANO", "878916669", "600176699", 2);
		this.clienteTardes=new Cliente(168900, "Janina", "FERNANDEZ", "83406357", "600168899", 3);
		this.procesador=new ProcesadorDeLlamadas(this.directorioRaiz, this);
	}

	private void limpiar(String directorio) {
		File f = new File(directorio);
		String[] ficheros = f.list();
		for (int i=ficheros.length-1; i>=0; i--) {
			File file=new File(directorio + ficheros[i]);
			file.delete();
		}
	}

	public void tearDown() throws Exception {
	}
	
	public void testTarifaPlanaClienteQueNoConsume() {
		// Comprobamos que se ha creado el fichero de factura
		Factura factura=null;
		String fileName=this.directorioRaiz + Constantes.facturas + clienteTarifaPlana.getTelefono() + ".txt";
		try {
			FileInputStream fis=new FileInputStream(fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			factura=(Factura) ois.readObject();
			fis.close();
		} catch (FileNotFoundException e) {
			fail("Esperaba encontrar el fichero: " + fileName);
		}
		catch (IOException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());
		}
		catch (ClassNotFoundException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());;
		}
		double esperado=Factura.redondear(250);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
	}

	public void testTarifaPlana1Llamada() {
		// Creamos una llamada de 100 segundos
		
		Llamada call = crearLlamada(clienteTarifaPlana, 100);
		// La vamos a guardar en el directorio de llamadas recibidas
		String fileName=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		
		// La guardamos
		guardarLlamada(call, fileName);
		
		// Procesamos el directorio de llamadas y hacemos una pausita
		this.procesador.run();
		
		// Comprobamos que la llamada ha desaparecido del directorio de llamadas recibidas
		comprobarQueNoExiste(fileName);
		
		// Comprobamos que se ha movido al directorio de llamadas procesadas
		fileName=this.directorioRaiz + Constantes.llamadasProcesadas + "1.txt";
		comprobarQueSeHaMovido(fileName);
		
		// Comprobamos que se ha creado el fichero de factura
		Factura factura=null;
		try {
			fileName=this.directorioRaiz + Constantes.facturas + clienteTarifaPlana.getTelefono() + ".txt";
			FileInputStream fis=new FileInputStream(fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			factura=(Factura) ois.readObject();
			fis.close();
		} catch (FileNotFoundException e) {
			fail("Esperaba encontrar el fichero: " + fileName);
		}
		catch (IOException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());
		}
		catch (ClassNotFoundException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());;
		}
		double esperado=Factura.redondear(250.15);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
	}
	
	public void testTarifaPlana2Llamadas() {
		Llamada call1 = crearLlamada(clienteTarifaPlana, 100);
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		
		Llamada call2 = crearLlamada(clienteTarifaPlana, 100);
		String fileName2=this.directorioRaiz + Constantes.llamadasRecibidas + "2.txt";
		guardarLlamada(call2, fileName2);
		
		this.procesador.run();
		
		comprobarQueNoExiste(fileName1);
		comprobarQueNoExiste(fileName2);
		
		fileName1=this.directorioRaiz + Constantes.llamadasProcesadas + "1.txt";
		comprobarQueSeHaMovido(fileName1);
		fileName2=this.directorioRaiz + Constantes.llamadasProcesadas + "2.txt";
		comprobarQueSeHaMovido(fileName2);
		
		Factura factura=null;
		String fileName=null;
		try {
			fileName=this.directorioRaiz + Constantes.facturas + clienteTarifaPlana.getTelefono() + ".txt";
			FileInputStream fis=new FileInputStream(fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			factura=(Factura) ois.readObject();
			fis.close();
		} catch (FileNotFoundException e) {
			fail("Esperaba encontrar el fichero: " + fileName);
		}
		catch (IOException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());
		}
		catch (ClassNotFoundException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());;
		}
		double esperado=Factura.redondear(250+2*0.15);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
	}
	
	public void testTarifa50MinutosNoSePasaConsume3Minutos() {
		Llamada call1 = crearLlamada(cliente50Minutos, 120);
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		
		Llamada call2 = crearLlamada(cliente50Minutos, 60);
		String fileName2=this.directorioRaiz + Constantes.llamadasRecibidas + "2.txt";
		guardarLlamada(call2, fileName2);
		
		this.procesador.run();

		comprobarQueNoExiste(fileName1);
		comprobarQueNoExiste(fileName2);
		
		fileName1=this.directorioRaiz + Constantes.llamadasProcesadas + "1.txt";
		comprobarQueSeHaMovido(fileName1);
		fileName2=this.directorioRaiz + Constantes.llamadasProcesadas + "2.txt";
		comprobarQueSeHaMovido(fileName2);
		
		Factura factura=null;
		String fileName=null;
		try {
			fileName=this.directorioRaiz + Constantes.facturas + cliente50Minutos.getTelefono() + ".txt";
			FileInputStream fis=new FileInputStream(fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			factura=(Factura) ois.readObject();
			fis.close();
		} catch (FileNotFoundException e) {
			fail("Esperaba encontrar el fichero: " + fileName);
		}
		catch (IOException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());
		}
		catch (ClassNotFoundException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());;
		}
		double esperado=Factura.redondear(15+2*0.15);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
	}
	
	public void testTarifa50MinutosConsume100Minutos() {
		// Hace 100 llamadas de 60 segundos cada una (100 llamadas de 1 minuto)
		// Se le deben facturar 50 establecimientos de llamada y 3000 segundos 
		
		//PARTE1 GENERACION 100 LLAMADAS
		long initTime=System.currentTimeMillis();
		
		for (int i=1; i<=100; i++) {
			Llamada call = crearLlamada(cliente50Minutos, 60);
			String fileName=this.directorioRaiz + Constantes.llamadasRecibidas + i + ".txt";
			guardarLlamada(call, fileName);
		}
		
		long endTime=System.currentTimeMillis();
		System.out.println("PARTE 1: "+(endTime-initTime)+" milesimas");
		
		//PARTE2 PROCESO DE FACTURACION
		initTime=System.currentTimeMillis();
		this.procesador.run();
		endTime=System.currentTimeMillis();
		System.out.println("PARTE 2: "+(endTime-initTime)+" milesimas");

		//PARTE 3 100 ORACULOS
		initTime=System.currentTimeMillis();
		for (int i=1; i<=100; i++) {
			String fileName=this.directorioRaiz + Constantes.llamadasRecibidas + i + ".txt";
			comprobarQueNoExiste(fileName);
			fileName=this.directorioRaiz + Constantes.llamadasProcesadas + i + ".txt";
			comprobarQueSeHaMovido(fileName);
		}
		endTime=System.currentTimeMillis();
		System.out.println("PARTE 3: "+(endTime-initTime)+" milesimas");
		
		Factura factura=null;
		String fileName=null;
		try {
			fileName=this.directorioRaiz + Constantes.facturas + cliente50Minutos.getTelefono() + ".txt";
			FileInputStream fis=new FileInputStream(fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			factura=(Factura) ois.readObject();
			fis.close();
		} catch (FileNotFoundException e) {
			fail("Esperaba encontrar el fichero: " + fileName);
		}
		catch (IOException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());
		}
		catch (ClassNotFoundException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());;
		}
		double cuotaFija=15;
		double cuotaPorEstablecimiento=100*0.15;//MODIFICADO,ANTES ESPERABA 10 CENTS POR ESTABL.
		double cuotaPorSegundos=3000*0.01;//50 llamadas despues del limite
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==100);//MODIFICADO,NO EXISTIA ANTES
	}
	
	public void testTarifa50MinutosConsume51Minutos() {
		// Hace 1 llamada de 50 minutos y otra de 1 minuto.
		// Se le deben facturar 2 establecimientos de llamada y 60 segundos 
		Llamada call1 = crearLlamada(cliente50Minutos, 3000);
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		Llamada call2 = crearLlamada(cliente50Minutos, 60);
		String fileName2=this.directorioRaiz + Constantes.llamadasRecibidas + "2.txt";
		guardarLlamada(call2, fileName2);
		
		this.procesador.run();
		
		for (int i=1; i<=2; i++) {
			String fileName=this.directorioRaiz + Constantes.llamadasRecibidas + i + ".txt";
			comprobarQueNoExiste(fileName);
		}
		
		Factura factura=null;
		String fileName=null;
		try {
			fileName=this.directorioRaiz + Constantes.facturas + cliente50Minutos.getTelefono() + ".txt";
			FileInputStream fis=new FileInputStream(fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			factura=(Factura) ois.readObject();
			fis.close();
		} catch (FileNotFoundException e) {
			fail("Esperaba encontrar el fichero: " + fileName);
		}
		catch (IOException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());
		}
		catch (ClassNotFoundException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());;
		}
		double cuotaFija=15;
		double cuotaPorEstablecimiento=2*0.15;//MODIFICADO, ESPERABA 10 CENTS
		double cuotaPorSegundos=60*0.01;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==2);
	}
	
	public void testTarifaFinDeSemanaHablaSoloEnFinDeSemana() {
		// Hace 2 llamadas de 50 minutos en s‡bado (1-12-2012).
		// Se le debe facturar solamente la cuota fija 
		Llamada call1 = crearLlamada(clienteFinDeSemana, 3000, 2012, 12, 1);
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		Llamada call2 = crearLlamada(clienteFinDeSemana, 60, 2012, 12, 1);
		String fileName2=this.directorioRaiz + Constantes.llamadasRecibidas + "2.txt";
		guardarLlamada(call2, fileName2);
		
		this.procesador.run();
		
		for (int i=1; i<=2; i++) {
			String fileName=this.directorioRaiz + Constantes.llamadasRecibidas + i + ".txt";
			comprobarQueNoExiste(fileName);
		}
		
		Factura factura=null;
		String fileName=null;
		try {
			fileName=this.directorioRaiz + Constantes.facturas + clienteFinDeSemana.getTelefono() + ".txt";
			FileInputStream fis=new FileInputStream(fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			factura=(Factura) ois.readObject();
			fis.close();
		} catch (FileNotFoundException e) {
			fail("Esperaba encontrar el fichero: " + fileName);
		}
		catch (IOException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());
		}
		catch (ClassNotFoundException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());;
		}
		double cuotaFija=25;
		double cuotaPorEstablecimiento=0;
		double cuotaPorSegundos=0;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==2);
	}
	
	public void testTarifaFinDeSemanaHablaUnLunes() {
		// Hace 2 llamadas de 50 minutos en s‡bado (1-12-2012) y otra de 10 minutos el lunes (3-12-2012).
		// Se le debe facturar la cuota fija y la llamada del lunes. 
		Llamada call1 = crearLlamada(clienteFinDeSemana, 3000, 2012, 12, 1);	
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		Llamada call2 = crearLlamada(clienteFinDeSemana, 60, 2012, 12, 1);
		String fileName2=this.directorioRaiz + Constantes.llamadasRecibidas + "2.txt";
		guardarLlamada(call2, fileName2);
		Llamada call3 = crearLlamada(clienteFinDeSemana, 600, 2012, 12, 3);
		String fileName3=this.directorioRaiz + Constantes.llamadasRecibidas + "3.txt";
		guardarLlamada(call3, fileName3);
		
		this.procesador.run();
		
		for (int i=1; i<=3; i++) {
			String fileName=this.directorioRaiz + Constantes.llamadasRecibidas + i + ".txt";
			comprobarQueNoExiste(fileName);
		}
		
		Factura factura=null;
		String fileName=null;
		try {
			fileName=this.directorioRaiz + Constantes.facturas + clienteFinDeSemana.getTelefono() + ".txt";
			FileInputStream fis=new FileInputStream(fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			factura=(Factura) ois.readObject();
			fis.close();
		} catch (FileNotFoundException e) {
			fail("Esperaba encontrar el fichero: " + fileName);
		}
		catch (IOException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());
		}
		catch (ClassNotFoundException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());;
		}
		double cuotaFija=25;
		double cuotaPorEstablecimiento=1*0.35;
		double cuotaPorSegundos=600*0.01;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==3);
	}
	
	public void testTarifaTardesHablaPorLaTarde() {
		// Hace 1 llamada de 50 minutos a las 17 h y otra de 1 minuto a las 20 h.
		// Se le debe facturar solamente la cuota fija 
		Llamada call1 = crearLlamada(clienteTardes, 3000, 2012, 10, 15, 17, 0, 0);
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		Llamada call2 = crearLlamada(clienteTardes, 60, 2012, 10, 15, 20, 0, 0);
		String fileName2=this.directorioRaiz + Constantes.llamadasRecibidas + "2.txt";
		guardarLlamada(call2, fileName2);
		
		this.procesador.run();
		
		for (int i=1; i<=3; i++) {
			String fileName=this.directorioRaiz + Constantes.llamadasRecibidas + i + ".txt";
			comprobarQueNoExiste(fileName);
		}
		
		Factura factura=null;
		String fileName=null;
		try {
			fileName=this.directorioRaiz + Constantes.facturas + clienteTardes.getTelefono() + ".txt";
			FileInputStream fis=new FileInputStream(fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			factura=(Factura) ois.readObject();
			fis.close();
		} catch (FileNotFoundException e) {
			fail("Esperaba encontrar el fichero: " + fileName);
		}
		catch (IOException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());
		}
		catch (ClassNotFoundException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());;
		}
		double cuotaFija=25;
		double cuotaPorEstablecimiento=0;
		double cuotaPorSegundos=0;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==2);
	}
	
	public void testTarifaTardesHablaPorLaTardeYPorLaManana() {
		// Hace 1 llamada de 50 minutos a las 17 h, otra de 1 minuto a las 20 h y otra de 1 minuto a las 14
		// Se le debe facturar la cuota fija, 1 estblecimiento y 60 segundos 
		Llamada call1 = crearLlamada(clienteTardes, 3000, 2012, 10, 15, 17, 0, 0);
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		Llamada call2 = crearLlamada(clienteTardes, 60, 2012, 10, 15, 20, 0, 0);
		String fileName2=this.directorioRaiz + Constantes.llamadasRecibidas + "2.txt";
		guardarLlamada(call2, fileName2);
		Llamada call3 = crearLlamada(clienteTardes, 60, 2012, 10, 15, 14, 0, 0);
		String fileName3=this.directorioRaiz + Constantes.llamadasRecibidas + "3.txt";//ANTES ERA 2.txt
		guardarLlamada(call3, fileName3);
		
		this.procesador.run();
		
		for (int i=1; i<=3; i++) {
			String fileName=this.directorioRaiz + Constantes.llamadasRecibidas + i + ".txt";
			comprobarQueNoExiste(fileName);
		}
		
		Factura factura=null;
		String fileName=null;
		try {
			fileName=this.directorioRaiz + Constantes.facturas + clienteTardes.getTelefono() + ".txt";
			FileInputStream fis=new FileInputStream(fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			factura=(Factura) ois.readObject();
			fis.close();
		} catch (FileNotFoundException e) {
			fail("Esperaba encontrar el fichero: " + fileName);
		}
		catch (IOException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());
		}
		catch (ClassNotFoundException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());;
		}
		double cuotaFija=25;
		double cuotaPorEstablecimiento=1*0.30;
		double cuotaPorSegundos=60*0.08;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==3);//MODIFICADO, ESPERABA 2 LINEAS
	}
	
	public void testTarifaTardesHace100LlamadasPorLaManana() {
		// Hace 100 llamada de 5 minutos a las 11:00:00
		// Se le debe facturar la cuota fija, 100 establecimientos y 30000 segundos 
		for (int i=1; i<=100; i++) {
			Llamada call = crearLlamada(clienteTardes, 300, 2012, 10, 15, 11, 0, 0);
			String fileName=this.directorioRaiz + Constantes.llamadasRecibidas + i + ".txt";
			guardarLlamada(call, fileName);
		}
			
		this.procesador.run();

		for (int i=1; i<=100; i++) {
			String fileName=this.directorioRaiz + Constantes.llamadasRecibidas + i + ".txt";
			comprobarQueNoExiste(fileName);
			fileName=this.directorioRaiz + Constantes.llamadasProcesadas + i + ".txt";
			comprobarQueSeHaMovido(fileName);
		}
		
		Factura factura=null;
		String fileName=null;
		try {
			fileName=this.directorioRaiz + Constantes.facturas + clienteTardes.getTelefono() + ".txt";
			FileInputStream fis=new FileInputStream(fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			factura=(Factura) ois.readObject();
			fis.close();
		} catch (FileNotFoundException e) {
			fail("Esperaba encontrar el fichero: " + fileName);
		}
		catch (IOException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());
		}
		catch (ClassNotFoundException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());;
		}
		double cuotaFija=25;
		double cuotaPorEstablecimiento=100*0.30;
		double cuotaPorSegundos=30000*0.08;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==100);
	}

	private void comprobarQueSeHaMovido(String fileName) {
		try {
			FileInputStream fis=new FileInputStream(fileName);
			fis.close();
		} catch (FileNotFoundException e) {
			fail("Esperaba encontrar el fichero: " + fileName);
		}
		catch (IOException e) {
			fail("Error al abrir el fichero: " + fileName + "; " + e.toString());
		}
	}

	private void comprobarQueNoExiste(String fileName) {
		try {
			FileInputStream fis=new FileInputStream(fileName);
			fail("Esperaba que el fichero hubiera desaparecido: " + fileName);
		} catch (FileNotFoundException e) {
		}
	}

	private void guardarLlamada(Llamada call, String fileName) {
		try {
			FileOutputStream fos=new FileOutputStream(fileName);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(call);
			fos.close();
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	/**
	 * Crea una llamada de la duraci—n especificada
	 */
	private Llamada crearLlamada(Cliente cliente, int duracion) {
		Llamada call=new Llamada(cliente.getTelefono(), "699999999", duracion, 2012, 10, 15, 15, 0, 0);
		return call;
	}

	/**
	 * Crea una llamada de la duraci—n especificada en la fecha pasada
	 */
	private Llamada crearLlamada(Cliente cliente, int duracion, int year, int month, int day) {
		Llamada call=new Llamada(cliente.getTelefono(), "699999999", duracion, year, month-1, day, 15, 0, 0);
		return call;
	}
	
	/**
	 * Crea una llamada de la duraci—n especificada en la fecha y hora pasadas
	 */
	private Llamada crearLlamada(Cliente cliente, int duracion, int year, int month, int day, int hora, int minuto, int segundo) {
		Llamada call=new Llamada(cliente.getTelefono(), "699999999", duracion, year, month, day, hora, minuto, segundo);
		return call;
	}

	@Override
	public void setProgresoMaximo(int arg0) {
	}

	@Override
	public void showError(String msg) {
		System.err.println(msg);		
	}

	@Override
	public void showInfo(String msg) {
		System.out.println(msg);
	}

	@Override
	public void showProgreso(int arg0) {
	}
}
