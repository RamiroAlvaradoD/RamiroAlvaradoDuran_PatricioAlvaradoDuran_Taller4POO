package sistema;
//Ramiro Alvarado Durán - RUT 19.428.146-3 - ITI
//Patricio Alvarado Durán - RUT 20.955.249-3 - ITI
//Taller 4 - Programación Orientada a Objetos
//Universidad Católica del Norte

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
