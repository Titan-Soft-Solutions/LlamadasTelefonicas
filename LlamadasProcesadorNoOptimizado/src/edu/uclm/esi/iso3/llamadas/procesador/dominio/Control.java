package edu.uclm.esi.iso3.llamadas.procesador.dominio;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;


public class Control {
	private static Control instancia;
	private String directorio;
	
	private Control(String directorio) {
		this.directorio=directorio;
	}
	
	public static Control getControl(String directorio) throws IOException {
		if (instancia==null) {
			instancia=new Control(directorio);
		}
		return instancia;
	}
	
	public Cliente findClienteByNumero(String numero) throws IOException {
		Cliente result=null;
		FileInputStream fis=new FileInputStream(this.directorio + "/clientes.txt");
		DataInputStream dis = new DataInputStream(fis);
		BufferedReader br = new BufferedReader(new InputStreamReader(dis));
		String linea = br.readLine();
		boolean encontrado = false;
		while (((linea = br.readLine()) != null) && !encontrado)   {			
			String[] tokens = linea.split("\t");
			String telefono = tokens[4];
			if (telefono.equals(numero))
			{
				encontrado = true;
				result = new Cliente(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], telefono, Integer.parseInt(tokens[5]));
			}
		}
		fis.close();
		return result;
	}


}
