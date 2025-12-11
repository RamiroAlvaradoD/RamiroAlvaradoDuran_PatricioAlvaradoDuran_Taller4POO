package sistema;

import dominio.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

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
	private UsuarioFactory factory;

	private Sistema() {
		usuarios = new ArrayList<>();
		estudiantes = new ArrayList<>();
		cursos = new ArrayList<>();
		notas = new ArrayList<>();
		registros = new ArrayList<>();
		certificaciones = new ArrayList<>();
		
		factory = new UsuarioFactoryImpl();

	}
	
	public void cargarUsuarios() {
		
		try {
			File archivo = new File("usuarios.txt");
			Scanner sc = new Scanner(archivo);
			
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String []datos = linea.split(";");
				
				Usuario u = factory.crearUsuario(datos);
				usuarios.add(u);
			}
		sc.close();	
			
		} catch (FileNotFoundException e) {
			System.out.println("Error al leer usuarios.txt") ;
		}
		

		
	}
	
	public void cargarEstudiantes() {
		try {
			File archivo = new File("estudiantes.txt");
			Scanner sc = new Scanner(archivo);
			
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[]datos = linea.split(";");
				
				String rut = datos[0];
				String nombre = datos[1];
				String carrera = datos[2];
				int semestre = Integer.parseInt(datos[3]);
				String correo = datos[4];
				String password = datos[5];
				
				Estudiante e = factory.crearEstudiante(rut, nombre, carrera, semestre, correo, password);
				estudiantes.add(e);
			}
			sc.close();
		}	catch (FileNotFoundException e) {
			System.out.println("Error al leer estudiantes.txt");
		}
	}
	
	public void cargarNotas() {
		try {
			File archivo = new File("notas.txt");
			Scanner sc = new Scanner(archivo);
			
			while(sc.hasNextLine()) {
				String linea = sc.nextLine();
				String [] datos = linea.split(";");
				
				String rut = datos[0];
				String codigo = datos[1];
				double calificacion = Double.parseDouble(datos[2]);
				String estado = datos[3];
				String semestre = datos[4];
				
				Nota n = new Nota(rut, codigo, calificacion, estado, semestre);
				notas.add(n);
			}
			sc.close();
		}	catch (FileNotFoundException e) {
			System.out.println("Error al leer notas.txt");
		}
		
	}
	
	public void cargarCertificaciones() {
		try {
			File archivo = new File("certificaciones.txt");
			Scanner sc = new Scanner(archivo);
			
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String []datos = linea.split(";");
				
				String id = datos[0];
				String nombre = datos[1];
				String descripcion = datos[2];
				int requisitos = Integer.parseInt(datos[3]);
				int validez = Integer.parseInt(datos[4]);
				
				ArrayList<String> listaVacia = new ArrayList<>();
				
				Certificacion c = new Certificacion(id, nombre, descripcion, requisitos, validez, listaVacia);
				certificaciones.add(c);
				
			} sc.close();
			
		}	catch (FileNotFoundException e) {
			System.out.println("Error al leer certificaciones.txt");
		}
	}
	
	public void cargarAsigCerts() {
		try {
			File archivo = new File("Asignaturas_certificaciones.txt");
			Scanner sc = new Scanner(archivo);
			
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[]datos = linea.split(";");
				
				String id = datos[0];
				String nrc = datos[1];
				
				//ahora toca buscar la certificacion asociada, con un for
				
				for(Certificacion c: certificaciones) {
					if(c.getId().equals(id)) {
						c.getAsigCerts().add(nrc);
					}
				}
						
			}
			sc.close();
		}	catch(FileNotFoundException e) {
			System.out.println("Error al leer Asignaturas_certificaciones.txt");
		}
	}
	
	public void cargarCursos() {
		
		

		try {
			File archivo = new File("cursos.txt");
			Scanner sc = new Scanner (archivo);
			
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[]datos = linea.split(";");
				
				String nrc = datos[0];
				String nombre = datos[1];
				int semestre = Integer.parseInt(datos[2]);
				int creditos = Integer.parseInt(datos[3]);
				String area = datos[4];
				
				ArrayList<String> prerreqs = new ArrayList<>();
				
				if (datos.length == 6) {
					String[]lista = datos[5].split("\\|");
					for (String p: lista) {
						prerreqs.add(p);
					}
					
				}
				
				Curso c = new Curso(nrc, nombre, semestre, creditos, area, prerreqs);
				cursos.add(c);
			}
			
			sc.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Error al leer cursos.txt");
		}
		
	}
	
	public void cargarRegistros() {
		try {
			File archivo = new File("registros.txt");
			Scanner sc = new Scanner(archivo);
			
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[]datos = linea.split(";");
				
				String rut = datos[0];
				String id = datos[1];
				String fecha = datos[2];
				String estado = datos[3];
				int progreso = Integer.parseInt(datos[4]);
				
				RegistroCertificacion rc = new RegistroCertificacion(rut, id, fecha, estado, progreso);
				registros.add(rc);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Error al leer registros.txt");
		}
	}
	
	public void cargarDatos() {
		cargarUsuarios();
		cargarEstudiantes();
		cargarCursos();
		cargarCertificaciones();
		cargarAsigCerts();
		cargarNotas();
		cargarRegistros();
		
		
		
		
		
	}
	

}
























