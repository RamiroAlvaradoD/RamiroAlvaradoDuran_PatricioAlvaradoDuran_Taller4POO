package dominio;

import java.util.ArrayList;

public class Certificacion {
	
	private String id;
	private String nombre;
	private String descripcion;
	private int creditos;
	private int validez;
	private ArrayList<String> asigCerts;
	
	
	public Certificacion(String id, String nombre, String descripcion, int creditos, int validez,
			ArrayList<String> asigCerts) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.creditos = creditos;
		this.validez = validez;
		this.asigCerts = asigCerts;
	}


	public String getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public int getCreditos() {
		return creditos;
	}


	public int getValidez() {
		return validez;
	}


	public ArrayList<String> getAsigCerts() {
		return asigCerts;
	}
	
	

}
