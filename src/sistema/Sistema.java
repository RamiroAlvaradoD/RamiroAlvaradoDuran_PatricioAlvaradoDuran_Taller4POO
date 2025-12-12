package sistema;

import dominio.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

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
			
			sc.close();
			
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
	
	
	//metodos para guardar los cambios de los metodos relacionados a escritura de archivos
	public void guardarUsuarios() {
		
		try {
			FileWriter fw = new FileWriter("usuarios.txt", false); 
			
			for (Usuario u: usuarios) {
				fw.write(u.getUsername() + ";"
				+ u.getPassword() + ";" 
				+ u.getRol() + "\n");
				
			}
			
			fw.close();
			
		} catch (IOException e) {
			System.out.println("Error al guardar usuarios.txt");
		}
		
	}
	
	public void guardarEstudiantes() {
		
		try {
			FileWriter fw = new FileWriter("estudiantes.txt", false);
			for(Estudiante e: estudiantes) {
				fw.write(e.getRut() + ";"
				+ e.getNombre() + ";"
				+ e.getCarrera() + ";"
				+ e.getSemestre() + ";"
				+ e.getCorreo() + ";"
				+ e.getPassword() + "\n");
			}
			
			fw.close();
		} catch (IOException e) {
			System.out.println("Error al guardar estudiantes.txt");
		}
		
	}
	
	
	//crear usuario coordinador
	
	public void crearCoordinador(String username, String password, String area) {
		
		Coordinador c = factory.crearCoordinador(username, password, area);
		usuarios.add(c);
		guardarUsuarios();
		System.out.println("Coordinador creado con exito.");
	}
	
	public void crearEstudiante(String rut, String nombre, String carrera, int semestre, String correo, String password) {
		Estudiante e = factory.crearEstudiante(rut, nombre, carrera, semestre, correo, password);
		estudiantes.add(e);
		
		guardarEstudiantes();
		
		System.out.println("Estudiante creado con exito.");
	}
	
	public void crearCuenta(int tipo, Scanner sc) {
		if (tipo == 1) {
			System.out.println("RUT: ");
			String rut = sc.nextLine();
			
			System.out.println("Nombre: ");
			String nombre = sc.nextLine();
			
			System.out.println("Carrera: ");
			String carrera = sc.nextLine();
			
			System.out.println("Semestre: ");
			int semestre = Integer.parseInt(sc.nextLine());
			
			System.out.println("Correo: ");
			String correo = sc.nextLine();
			
			System.out.println("Contraseña: ");
			String password = sc.nextLine();
			
			crearEstudiante(rut, nombre, carrera, semestre, correo, password);
		}
		
		else if (tipo == 2) {
			
			System.out.println("Nombre de usuario: ");
			String username = sc.nextLine();
			
			System.out.println("Contraseña: ");
			String password = sc.nextLine();
			
			System.out.println("Area: ");
			String area = sc.nextLine();
			
			crearCoordinador(username, password, area);
		}
	}
	
	public void modificarCoordinador (String usernameBuscado, Scanner sc) {
		Coordinador objetivo = null;
		
		for (Usuario u: usuarios) {
			if (u instanceof Coordinador && u.getUsername().equals(usernameBuscado)) {
				objetivo = (Coordinador) u;
				break;
			}
		}
		
		if (objetivo == null) {
			System.out.println("No existe un coordinador con ese nombre de usuario.");
			return;
		}
		
		System.out.println("Nueva contraseña: ");
		String nuevaPass = sc.nextLine();
		
		String nuevaArea = sc.nextLine();
		
		objetivo.setPassword(nuevaPass);
		objetivo.setArea(nuevaArea);
		
		System.out.println("Coordinador modificado.");
	}
	
	
	public void modificarEstudiante(String rutBuscado, Scanner sc) {
	    Estudiante objetivo = null;

	    for (Estudiante e : estudiantes) {
	        if (e.getRut().equals(rutBuscado)) {
	            objetivo = e;
	            break;
	        }
	    }

	    if (objetivo == null) {
	        System.out.println("No existe estudiante con ese rut.");
	        return;
	    }

	    System.out.print("Nuevo nombre: ");
	    String nombre = sc.nextLine();

	    System.out.print("Nueva carrera: ");
	    String carrera = sc.nextLine();

	    System.out.print("Nuevo semestre: ");
	    int semestre = Integer.parseInt(sc.nextLine());

	    System.out.print("Nueva contraseña: ");
	    String pass = sc.nextLine();

	    objetivo.setNombre(nombre);
	    objetivo.setCarrera(carrera);
	    objetivo.setSemestre(semestre);
	    objetivo.setPassword(pass);

	    guardarUsuarios();
	    guardarEstudiantes();

	    System.out.println("Estudiante modificado con éxito.");
	}
	
	public void eliminarCoordinador(String usernameBuscado) {
		
		Usuario objetivo = null;
		
		for (Usuario u :usuarios) {
			if (u instanceof Coordinador && u.getUsername().equals(usernameBuscado)) {
				objetivo = u;
				break;
			}
		}
		
		if (objetivo == null) {
			System.out.println("No se encontró un coordinador con ese username.");
			return;
		}
		
		
		usuarios.remove(objetivo);
		
		
		guardarUsuarios();
		
		System.out.println("Coordinador eliminado exitosamente");
	}
	
	public void eliminarEstudiante(String rutBuscado) {
		
		Estudiante objetivo = null;
		
		for (Estudiante e : estudiantes) {
			if (e.getRut().equals(rutBuscado)) {
				objetivo = e;
				break;
			}
		}
		
		if (objetivo == null) {
			System.out.println("No se encontró un estudiante con ese RUT.");
			return;
		}
		
		estudiantes.remove(objetivo);
		
		guardarEstudiantes();
		
		System.out.println("Estudiante eliminado exitosamente.");
	}
	

}
























