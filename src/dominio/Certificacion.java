package dominio;
//Ramiro Alvarado Durán - RUT 19.428.146-3 - ITI
//Patricio Alvarado Durán - RUT 20.955.249-3 - ITI
//Taller 4 - Programación Orientada a Objetos
//Universidad Católica del Norte

import java.util.ArrayList;

import visitor.CertificacionVisitor;

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


	public void setId(String id) {
		this.id = id;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}


	public void setValidez(int validez) {
		this.validez = validez;
	}


	public void setAsigCerts(ArrayList<String> asigCerts) {
		this.asigCerts = asigCerts;
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
	
	public void aceptar(CertificacionVisitor v) {
	    v.visitar(this);
	}
	
	

}
