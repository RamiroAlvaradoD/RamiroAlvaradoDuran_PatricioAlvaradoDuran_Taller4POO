package sistema;

import dominio.Administrador;
import dominio.Coordinador;
import dominio.Estudiante;
import dominio.Usuario;

public interface UsuarioFactory {
	
	Administrador crearAdministrador(String username, String password);
	Coordinador crearCoordinador(String username, String password, String area);
	Estudiante crearEstudiante(String rut, String nombre, String carrera, int semestre, String correo, String password);
	
	Usuario crearUsuario(String[]datos);

}
