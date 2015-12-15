package edu.uclm.esi.iso3.llamadas.procesador.dominio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

public class Llamada implements Serializable {
	private String origen, destino;
	private int duracion;
	private Calendar fecha;
	
	public Llamada(int ultimoNumero) {
		Random r=new Random();
		int rango=ultimoNumero-600000000;
		int iOrigen=600000000+r.nextInt(rango);
		this.origen="" + iOrigen;
		int iDestino=600000000 + r.nextInt(99999999);
		this.destino="" + iDestino;
		double auxi=r.nextDouble();
		if (auxi<0.4) {
			this.duracion=r.nextInt(200);
		} else if (auxi<0.6) {
			this.duracion=r.nextInt(800);
		} else if (auxi<0.8) {
			this.duracion=r.nextInt(1800);
		} else {
			this.duracion=r.nextInt(3000);
		}
		//this.fecha=this.fechaAleatoriaCalendar(2012, 10);
		this.fecha=Calendar.getInstance();
	}

	public Llamada(String origen, String destino, int duracion, int year, int mes, int dia, int hora, int minuto, int segundo) {
		this.origen=origen;
		this.destino=destino;
		this.duracion=duracion;
		this.fecha=Calendar.getInstance();
		this.fecha.set(year, mes, dia, hora, minuto, segundo);
	}

	/**
     * MŽtodo que genera un Objeto <strong>Calendar</strong> con una fecha
     * aleatoria dentro de un rango espec’fico.<br />
     * Por ejemplo:<br />
     * fechaAleatoriaCalendar(1965, 2011).toString();<br />
     * Generar’a: "30/10/1980" o cualquier otra posibilidad entre los a–os indicados.
     * @param a–oMin (int) <strong>A–o m’nimo</strong> dentro del rango
     * @param a–oMax (int) <strong>A–o m‡ximo</strong> dentro del rango
     * @return (Calendar) <strong>Objeto</strong> con la fecha aleatoria.
     * @author Paco - Francisco Garc’a Lamela
     */
    private Calendar fechaAleatoriaCalendar(int year, int mes) {
        Calendar calendario = Calendar.getInstance();
        int dia=0;
        if (mes == 2) {//Mes de febrero
            if (year % 400 == 0 || year % 4 == 0) {//es bisiesto
                dia = (int) (29 * Math.random());
            } else {//No es a–o bisiesto
                dia = (int) (28 * Math.random());
            }
        } else {
            if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                //Mes de 31 d’as
                dia = (int) (31 * Math.random());
            } else {//Mes de 30 d’as
                dia = (int) Math.random();
            }
        }
        int hora=(int) (24*Math.random());
        int minuto=(int) (60*Math.random());
        int segundo=(int) (60*Math.random());
        calendario.set(year, mes, dia, hora, minuto, segundo);
        return calendario;
    }

	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}

	public int getDuracion() {
		return duracion;
	}

	public Calendar getFecha() {
		return fecha;
	}
	
	@Override
	public String toString() {
		String fechaYHora = this.fecha.get(Calendar.DATE) + "/" + (1+this.fecha.get(Calendar.MONTH)) + "/" + this.fecha.get(Calendar.YEAR) + "; " + 
			this.fecha.get(Calendar.HOUR) + ":" + this.fecha.get(Calendar.MINUTE) + ":" + this.fecha.get(Calendar.SECOND);
		return "De " + this.origen + " a " + this.destino + ", a las " + fechaYHora + "; duraci—n: " + this.duracion + " sg.";
	}
}
