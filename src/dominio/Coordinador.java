package dominio;
//Ramiro Alvarado Durán - RUT 19.428.146-3 - ITI
//Patricio Alvarado Durán - RUT 20.955.249-3 - ITI
//Taller 4 - Programación Orientada a Objetos
//Universidad Católica del Norte

public class Coordinador extends Usuario {
	
	private String area;

	public String getArea() {
		return area;
	}

	public Coordinador(String username, String password, String area) {
		super(username, password, "Coordinador");
		this.area = area;
		
	}

	public void setArea(String area) {
		this.area = area;
	}

}
