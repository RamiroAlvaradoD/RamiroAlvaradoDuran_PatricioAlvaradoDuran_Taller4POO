package sistema;

import dominio.Administrador;
import dominio.Coordinador;
import dominio.Estudiante;
import dominio.Usuario;

public class UsuarioFactoryImpl implements UsuarioFactory {

	@Override
	public Administrador crearAdministrador(String username, String password) {
		return new Administrador(username, password);
	}

	@Override
	public Coordinador crearCoordinador(String username, String password, String area) {
		return new Coordinador(username, password, area);
	}

	@Override
	public Estudiante crearEstudiante(String rut, String nombre, String carrera, int semestre, String correo,
			String password) {
		return new Estudiante(rut, nombre, carrera, semestre, correo, password);
	}

	@Override
	public Usuario crearUsuario(String[] datos) {
		String username= datos[0];
		String password= datos[1];
		String rol = datos[2];
		
		switch(rol) {
		
		case "administrador":
        case "Admin":
            return crearAdministrador(username, password);

			
        case "Coordinador":
        case "coord":
			String area = datos[3];
			return crearCoordinador(username, password, area);
			
			default:
				throw new IllegalArgumentException("Rol invalido: " + rol);
		
		}
	}

}
