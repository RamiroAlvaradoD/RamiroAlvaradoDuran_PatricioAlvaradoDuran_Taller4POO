package sistema;

import dominio.*;
import java.util.ArrayList;

public class Sistema {
	private static Sistema instancia = null;

	public static Sistema getInstance() {
		if (instancia == null) {
			instancia = new Sistema();

		}

		return instancia;

	}

	private ArrayList<Usuario> usuarios;
	private ArrayList<Estudiante> estudiantes;
	private ArrayList<Curso> cursos;
	private ArrayList<Nota> notas;
	private ArrayList<RegistroCertificacion> registros;
	private ArrayList<Certificacion> certificaciones;

	private Sistema() {
		usuarios = new ArrayList<>();
		estudiantes = new ArrayList<>();
		cursos = new ArrayList<>();
		notas = new ArrayList<>();
		registros = new ArrayList<>();
		certificaciones = new ArrayList<>();

	}
	

}
