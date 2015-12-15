package edu.uclm.esi.iso3.llamadas.procesador.dominio;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
		while ((linea = br.readLine()) != null)   {
			String sId="";
			int id;
			String nombre="", apellido="", dni="", telefono="";
			String sTarifa="";
			int tarifa;
			int i=0;
			for (int j=i; linea.charAt(i)!='\t'; j++, i++) {
				sId=sId+linea.charAt(i);
			}
			i++;
			id=Integer.parseInt(sId);
			for (int j=i; linea.charAt(i)!='\t'; j++, i++) {
				nombre=nombre+linea.charAt(i);
			}
			i++;
			for (int j=i; linea.charAt(i)!='\t'; j++, i++) {
				apellido=apellido+linea.charAt(i);
			}
			i++;
			for (int j=i; linea.charAt(i)!='\t'; j++, i++) {
				dni=dni+linea.charAt(i);
			}
			i++;
			for (int j=i; linea.charAt(i)!='\t'; j++, i++) {
				telefono=telefono+linea.charAt(i);
			}
			i++;
			for (int j=i; j<linea.length(); j++, i++) {
				sTarifa=sTarifa+linea.charAt(i);
			}
			tarifa=Integer.parseInt(sTarifa);
			Cliente c=new Cliente(id, nombre, apellido, dni, telefono, tarifa);
			if (c.getTelefono().equals(numero)) 
				result=c;
		}
		fis.close();
		return result;
	}


}