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

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
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
