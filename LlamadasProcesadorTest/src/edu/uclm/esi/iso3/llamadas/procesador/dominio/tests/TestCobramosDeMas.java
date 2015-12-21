package edu.uclm.esi.iso3.llamadas.procesador.dominio.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
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


public class TestCobramosDeMas extends TestCase implements IVentana {
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
	
	public void test50MinutosClienteQueNoConsume() {
		// Comprobamos que se ha creado el fichero de factura
		Factura factura=null;
		String fileName=this.directorioRaiz + Constantes.facturas + cliente50Minutos.getTelefono() + ".txt";
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
		double esperado=Factura.redondear(15);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
	}
	
	public void testFinDeSemanaClienteQueNoConsume() {
		// Comprobamos que se ha creado el fichero de factura
		Factura factura=null;
		String fileName=this.directorioRaiz + Constantes.facturas + clienteFinDeSemana.getTelefono() + ".txt";
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
		double esperado=Factura.redondear(25);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
	}
	
	public void testTardesClienteQueNoConsume() {
		// Comprobamos que se ha creado el fichero de factura
		Factura factura=null;
		String fileName=this.directorioRaiz + Constantes.facturas + clienteTardes.getTelefono() + ".txt";
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
		double esperado=Factura.redondear(25);
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
	/**
	 * Solo tiene que cobrar la cuota fija y el establecimient de llamada
	 * Lunes 21/15/2015 a las 15:59:59 3000seg
	 */
	public void testTarifa50Minutos1(){
		// 
		Llamada call1 = crearLlamada(cliente50Minutos, 3000,2015,Calendar.DECEMBER,21,15,59,59);
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		
		
		this.procesador.run();
		
			String fileName=this.directorioRaiz + Constantes.llamadasRecibidas +  "1.txt";
			comprobarQueNoExiste(fileName);
		
		Factura factura=null;
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
		double cuotaPorEstablecimiento=0.15;//MODIFICADO, ESPERABA 10 CENTS
		double cuotaPorSegundos=0;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==1);
	}
	
	/**
	 * Las dos tienes que ser gratis xq la primera no sobrepasa los 3000
	 * solo cobrara dos establecimientos de llamada y la cuota fija 
	 * Lunes 21/12/2015 a las 15:59:59 2999seg
	 * Viernes 25/12/2015 a las 16:0:0 2999seg
	 */
	public void testTarifa50Minutos2(){
		// 
		Llamada call1 = crearLlamada(cliente50Minutos, 2999,2015,Calendar.DECEMBER,21,15,59,59);
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		
		Llamada call2 = crearLlamada(cliente50Minutos, 2999,2015,Calendar.DECEMBER,25,16, 0, 0);
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
		double cuotaPorSegundos=0;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==2);
	}
	/**
	 * Tiene que cobrar la cuota fija, 3 establecimientos de llamada y 3000 seg
	 * Lunes 21/12/2015 a las 23:59:59 dura 2999seg
	 * Sabado 26/12/2015 a las 16:0:0 dira 1 seg
	 * Viernes 1/1/2016 a las 15:59:59 y dura 3000 seg
	 */
	public void testTarifa50Minutos3(){
		// 
		Llamada call1 = crearLlamada(cliente50Minutos, 2999,2015,Calendar.DECEMBER,21,23,59,59);
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		
		Llamada call2 = crearLlamada(cliente50Minutos, 1,2015,Calendar.DECEMBER,26,16, 0, 0);
		String fileName2=this.directorioRaiz + Constantes.llamadasRecibidas + "2.txt";
		guardarLlamada(call2, fileName2);
		
		Llamada call3 = crearLlamada(cliente50Minutos, 3000,2016,Calendar.JANUARY,1,15,59,59);
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
		double cuotaPorEstablecimiento=3*0.15;//MODIFICADO, ESPERABA 10 CENTS
		double cuotaPorSegundos=3000*0.01;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==3);
	}
	/**
	 * Cobrara la cuota fija, 3 establecimientos de llamada y 2999seg
	 * Domingo 20/12/2015 a las 0:0:0 y dura 1seg
	 * Lunes 21/12/2015 a las 16:0:0 y dura 5000 seg
	 * Sabado 26/12/2015 a las 23:59:58 y dura 2999seg
	 */
	public void testTarifa50Minutos4(){
		
		Llamada call1 = crearLlamada(cliente50Minutos, 1,2015,Calendar.DECEMBER,20,0,0,0);
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		
		Llamada call2 = crearLlamada(cliente50Minutos, 5000,2015,Calendar.DECEMBER,21,16, 0, 0);
		String fileName2=this.directorioRaiz + Constantes.llamadasRecibidas + "2.txt";
		guardarLlamada(call2, fileName2);
		
		Llamada call3 = crearLlamada(cliente50Minutos, 2999,2015,Calendar.DECEMBER,26,23, 59,59);
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
		double cuotaPorEstablecimiento=3*0.15;//MODIFICADO, ESPERABA 10 CENTS
		double cuotaPorSegundos=2999*0.01;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==3);
	}
	
	/**
	 * En este caso de prueba se comprobará que el comportamiento del programa
	 * sea el esperado con respecto a la ordenación de los ficheros de llamadas
	 * 
	 * Se realizarán las siguientes llamadas:
	 * Domingo 20/12/2015 a las 0:0:0 y dura 1seg, almacenado en 1.txt
	 * Lunes 21/12/2015 a las 16:0:0 y dura 5000 seg, almacenado en 2.txt
	 * Sabado 26/12/2015 a las 23:59:58 y dura 2999seg, almacenado en 11.txt
	 * 
	 * El comportamiento esperado es:
	 * Cobrara la cuota fija, 3 establecimientos de llamada y 2999seg
	 */	
/*public void testTarifa50MinutosOrdenacionFicheros(){
		
		Llamada call1 = crearLlamada(cliente50Minutos, 1,2015,Calendar.DECEMBER,20,0,0,0);
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		
		Llamada call2 = crearLlamada(cliente50Minutos, 5000,2015,Calendar.DECEMBER,21,16, 0, 0);
		String fileName2=this.directorioRaiz + Constantes.llamadasRecibidas + "2.txt";
		guardarLlamada(call2, fileName2);
		
		Llamada call3 = crearLlamada(cliente50Minutos, 2999,2015,Calendar.DECEMBER,26,23, 59,59);
		String fileName3=this.directorioRaiz + Constantes.llamadasRecibidas + "11.txt";
		guardarLlamada(call3, fileName3);
		
		
		this.procesador.run();
		
		for (int i=1; i<=3; i++) {
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
		double cuotaPorEstablecimiento=3*0.15;//MODIFICADO, ESPERABA 10 CENTS
		double cuotaPorSegundos=2999*0.01;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==3);
	}*/
	
	public void testTarifaFinDeSemanaHablaUnSabado() {
		// El 19-12-2015 es sabado a las 0:0:0, 15:15:15 y 23:59:59
		Llamada call1 = crearLlamada(clienteFinDeSemana, 60, 2015, Calendar.DECEMBER, 19,0,0,0);	
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		Llamada call2 = crearLlamada(clienteFinDeSemana, 60, 2015, Calendar.DECEMBER, 19,15,15,15);
		String fileName2=this.directorioRaiz + Constantes.llamadasRecibidas + "2.txt";
		guardarLlamada(call2, fileName2);
		Llamada call3 = crearLlamada(clienteFinDeSemana, 60, 2015, Calendar.DECEMBER, 19,23,59,59);
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
		double cuotaPorEstablecimiento=0;
		double cuotaPorSegundos=0;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==3);
	}
	
	public void testTarifaFinDeSemanaHablaUnDomingo() {
		// El 20-12-2015 es domingo a las 0:0:0, 15:59:59 y 23:59:59
		Llamada call1 = crearLlamada(clienteFinDeSemana, 60, 2015, Calendar.DECEMBER, 20,0,0,0);	
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		Llamada call2 = crearLlamada(clienteFinDeSemana, 60, 2015, Calendar.DECEMBER, 20,15,59,59);
		String fileName2=this.directorioRaiz + Constantes.llamadasRecibidas + "2.txt";
		guardarLlamada(call2, fileName2);
		Llamada call3 = crearLlamada(clienteFinDeSemana, 60, 2015, Calendar.DECEMBER, 20,23,59,59);
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
		double cuotaPorEstablecimiento=0;
		double cuotaPorSegundos=0;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==3);
	}
	
	public void testTarifaFinDeSemanaHablaUnViernesSabadoDomingoLunes() {
		// El 18-12-2015 es viernes a las 23:59:59
		// El 19-12-2015 es sabado a las 0:0:0
		// El 20-12-2015 es sabado a las 23:59:59
		// El 21-12-2015 es lunes a las 0:0:0
		Llamada call1 = crearLlamada(clienteFinDeSemana, 60, 2015, Calendar.DECEMBER, 18,23,59,59);	
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		Llamada call2 = crearLlamada(clienteFinDeSemana, 60, 2015, Calendar.DECEMBER, 19,0,0,0);
		String fileName2=this.directorioRaiz + Constantes.llamadasRecibidas + "2.txt";
		guardarLlamada(call2, fileName2);
		Llamada call3 = crearLlamada(clienteFinDeSemana, 60, 2015, Calendar.DECEMBER, 20,23,59,59);
		String fileName3=this.directorioRaiz + Constantes.llamadasRecibidas + "3.txt";
		guardarLlamada(call3, fileName3);
		Llamada call4 = crearLlamada(clienteFinDeSemana, 60, 2015, Calendar.DECEMBER, 21,0,0,0);
		String fileName4=this.directorioRaiz + Constantes.llamadasRecibidas + "4.txt";
		guardarLlamada(call4, fileName4);
		
		this.procesador.run();
		
		for (int i=1; i<=4; i++) {
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
		double cuotaPorEstablecimiento=0.35*2;
		double cuotaPorSegundos=120*0.01;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==4);
	}
	
	
	public void testTarifaTardes() {
		// 20/12/2015 es domingo a las 15:59:59
		// 20/12/2015 es domingo a las 16:00:00
		// 21/12/2015 es lunes a las 23:59:59
		// 25/12/2015 es viernes a las 0:0:0
		Llamada call1 = crearLlamada(clienteTardes, 2999, 2015, 12, 20, 15, 59, 59);
		String fileName1=this.directorioRaiz + Constantes.llamadasRecibidas + "1.txt";
		guardarLlamada(call1, fileName1);
		Llamada call2 = crearLlamada(clienteTardes, 3000, 2015, 12, 20, 16, 0, 0);
		String fileName2=this.directorioRaiz + Constantes.llamadasRecibidas + "2.txt";
		guardarLlamada(call2, fileName2);
		
		Llamada call3 = crearLlamada(clienteTardes, 5000, 2015, 12, 21, 23, 59, 59);
		String fileName3=this.directorioRaiz + Constantes.llamadasRecibidas + "3.txt";
		guardarLlamada(call3, fileName3);
		
		Llamada call4 = crearLlamada(clienteTardes, 1, 2015, 12, 25, 0, 0, 0);
		String fileName4=this.directorioRaiz + Constantes.llamadasRecibidas + "4.txt";
		guardarLlamada(call4, fileName4);
		
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
		double cuotaPorEstablecimiento=0.30*2;
		double cuotaPorSegundos=0.08*3000;
		double esperado=Factura.redondear(cuotaFija+cuotaPorEstablecimiento+cuotaPorSegundos);
		double obtenido=Factura.redondear(factura.getImporteSinIVA());
		assertTrue("Esperaba: " + esperado + "; obtenido: " + obtenido, obtenido==esperado);
		assertTrue(factura.getNumeroDeLineas()==4);
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
