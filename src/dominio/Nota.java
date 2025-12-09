package dominio;

public class Nota {
	private String rut;
	private String codigo;
	private double calificacion;
	private String estado;
	private String semestre;
	
	public Nota(String rut, String codigo, double calificacion, String estado, String semestre) {
		this.rut = rut;
		this.codigo = codigo;
		this.calificacion = calificacion;
		this.estado = estado;
		this.semestre = semestre;
	}

	public double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(double calificacion) {
		this.calificacion = calificacion;
	}

	public String getRut() {
		return rut;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getEstado() {
		return estado;
	}

	public String getSemestre() {
		return semestre;
	}
	
	
	
	

}
