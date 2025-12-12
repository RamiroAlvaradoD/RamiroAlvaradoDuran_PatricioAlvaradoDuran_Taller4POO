package dominio;

public class Estudiante extends Usuario {

	private String rut;
	private String nombre;
	private String carrera;
	private int semestre;
	private String correo;

	public Estudiante(String rut, String nombre, String carrera, int semestre, String correo, String password) {

		super(correo, password, "Estudiante");

		this.rut = rut;
		this.nombre = nombre;
		this.carrera = carrera;
		this.semestre = semestre;
		this.correo = correo;
	}

	public String getRut() {
		return rut;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCarrera() {
		return carrera;
	}

	public int getSemestre() {
		return semestre;
	}

	public String getCorreo() {
		return correo;
	}
	


}
