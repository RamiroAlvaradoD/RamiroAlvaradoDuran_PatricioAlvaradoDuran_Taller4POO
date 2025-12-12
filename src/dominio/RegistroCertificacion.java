package dominio;
//Ramiro Alvarado Durán - RUT 19.428.146-3 - ITI
//Patricio Alvarado Durán - RUT 20.955.249-3 - ITI
//Taller 4 - Programación Orientada a Objetos
//Universidad Católica del Norte

public class RegistroCertificacion {
	private String rut;
	private String id;
	private String fechaRegistro;
	private String estado;
	private int progreso;
	
	public RegistroCertificacion(String rut, String id, String fechaRegistro, String estado, int progreso) {
		this.rut = rut;
		this.id = id;
		this.fechaRegistro = fechaRegistro;
		this.estado = estado;
		this.progreso = progreso;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRut() {
		return rut;
	}

	public String getId() {
		return id;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public int getProgreso() {
		return progreso;
	}
	
	
	
	

}
