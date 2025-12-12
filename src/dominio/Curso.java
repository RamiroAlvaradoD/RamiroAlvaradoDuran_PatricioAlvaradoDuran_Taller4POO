package dominio;
//Ramiro Alvarado Durán - RUT 19.428.146-3 - ITI
//Patricio Alvarado Durán - RUT 20.955.249-3 - ITI
//Taller 4 - Programación Orientada a Objetos
//Universidad Católica del Norte

import java.util.ArrayList;

public class Curso {
	
	private String nrc;
	private String nombre;
	private int semestre;
	private int creditos;
	private String area;
	private ArrayList<String> prerrequisitos;
	
	public Curso(String nrc, String nombre, int semestre, int creditos, String area, ArrayList<String> prerrequisitos) {
		this.nrc = nrc;
		this.nombre = nombre;
		this.semestre = semestre;
		this.creditos = creditos;
		this.area = area;
		this.prerrequisitos = prerrequisitos;
	}

	public String getNrc() {
		return nrc;
	}

	public String getNombre() {
		return nombre;
	}

	public int getSemestre() {
		return semestre;
	}

	public int getCreditos() {
		return creditos;
	}

	public String getArea() {
		return area;
	}

	public ArrayList<String> getPrerrequisitos() {
		return prerrequisitos;
	}
	
	
	
	

}
