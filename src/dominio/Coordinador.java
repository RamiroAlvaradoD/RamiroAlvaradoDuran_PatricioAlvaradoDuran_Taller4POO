package dominio;

public class Coordinador extends Usuario {
	
	private String area;

	public Coordinador(String username, String password, String area) {
		super(username, password, "Coordinador");
		this.area = area;
		
	}

}
