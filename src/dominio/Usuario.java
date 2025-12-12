package dominio;
//Ramiro Alvarado Durán - RUT 19.428.146-3 - ITI
//Patricio Alvarado Durán - RUT 20.955.249-3 - ITI
//Taller 4 - Programación Orientada a Objetos
//Universidad Católica del Norte

public abstract class Usuario {
	protected String username;
	protected String password;
	protected String rol;
	
	
	public Usuario(String username, String password, String rol) {
		this.username = username;
		this.password = password;
		this.rol = rol;
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}


	public String getRol() {
		return rol;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	
	

}
