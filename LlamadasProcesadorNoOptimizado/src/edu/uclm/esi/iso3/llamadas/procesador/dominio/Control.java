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
		boolean encontrado = false;//AGREGADO PARA MEJORAR RENDIMIENTO
		//while (((linea = br.readLine()) != null))   {
		while (((linea = br.readLine()) != null) && !encontrado)   {
			
			String[] tokens = linea.split("\t");
			/*String sId=tokens[0];
			String nombre=tokens[1];*/
			String telefono = tokens[4];
			//...
			/*StringTokenizer st=new StringTokenizer(linea,"\t");
			String sId = st.nextToken();
			int id=Integer.parseInt(sId);
			String nombre = st.nextToken();
			String apellido = st.nextToken();
			String dni = st.nextToken();
			String telefono = st.nextToken();
			String sTarifa = st.nextToken();
			int tarifa=Integer.parseInt(sTarifa);*/
			
			/*String sId="";
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
			}*/
			
			/*i++;
			for (int j=i; j<linea.length(); j++, i++) {
				sTarifa=sTarifa+linea.charAt(i);
			}
			tarifa=Integer.parseInt(sTarifa);
			Cliente c=new Cliente(id, nombre, apellido, dni, telefono, tarifa);
			if (c.getTelefono().equals(numero)) 
				result=c;*/
			if (telefono.equals(numero))
			{
				encontrado = true;
				/*i++;
				for (int j=i; j<linea.length(); j++, i++) {
					sTarifa=sTarifa+linea.charAt(i);
				}*/
				/*String sId=tokens[0];
				int id=Integer.parseInt(sId);
				String nombre=tokens[1];
				String apellido = tokens[2];
				String dni = tokens[3];
				String sTarifa = tokens[5];
				int tarifa=Integer.parseInt(sTarifa);*/
				result = new Cliente(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], telefono, Integer.parseInt(tokens[5]));
			}
		}
		fis.close();
		return result;
	}


}
