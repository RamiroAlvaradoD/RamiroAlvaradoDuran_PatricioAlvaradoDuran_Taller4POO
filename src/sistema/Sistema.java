package sistema;

import dominio.*;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.spi.RegisterableService;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import strategy.EstrategiaProgreso;
import strategy.ProgresoSimple;
import strategy.ProgresoPonderado;


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
	private EstrategiaProgreso estrategia;


	private Sistema() {
		usuarios = new ArrayList<>();
		estudiantes = new ArrayList<>();
		cursos = new ArrayList<>();
		notas = new ArrayList<>();
		registros = new ArrayList<>();
		certificaciones = new ArrayList<>();
		
		factory = new UsuarioFactoryImpl();
		estrategia = new ProgresoSimple();

	}
	
	public void setEstrategia(EstrategiaProgreso e) {
	    this.estrategia = e;
	}
	
	public int calcularProgresoCertificacion(Estudiante est, Certificacion cert) {
	    return estrategia.calcularProgreso(est, cert, notas);
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
	
	
	public void restablecerPassCoordinador(String usernameBuscado, Scanner sc) {
		
		Coordinador objetivo = null;
		
		for (Usuario u : usuarios) {
			if (u instanceof Coordinador && u.getUsername().equals(usernameBuscado)) {
				objetivo = (Coordinador)u;
				break;
			}
		}
		
		if (objetivo == null) {
			System.out.println("No se encontró un coordinador con ese username.");
			return;
		}
		
		System.out.print("Ingrese la nueva contraseña: ");
		String nuevaPass = sc.nextLine();
		
		objetivo.setPassword(nuevaPass);
		
		guardarUsuarios();
		
		System.out.println("Contraseña restablecida exitosamente");
	}
	
	public void restablecerPassEstudiante(String rutBuscado, Scanner sc) {
		
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
		
		System.out.print("Ingrese la nueva contraseña: ");
		String nuevaPass = sc.nextLine();
		
		objetivo.setPassword(nuevaPass);
		
		guardarUsuarios();
		
		System.out.println("Contraseña restablecida exitosamente");
	}
	
	
	public void guardarCertificaciones() {
		try {
			FileWriter fw = new FileWriter("certificaciones.txt", false);
			
			for (Certificacion c : certificaciones) {
				fw.write(c.getId() + ";"
						+ c.getNombre() + ";"
						+ c.getDescripcion() + ";"
						+ c.getCreditos() + ";" 
						+ c.getValidez() + "\n");
			}
			
			fw.close();
		} catch(IOException e) {
			System.out.println("Error al guardar certificaciones.txt");
		}
	}
	
	public void modificarCertificacion(String idBuscado, Scanner sc) {
		
		Certificacion objetivo = null;
		
		for (Certificacion c : certificaciones) {
			if (c.getId().equals(idBuscado)) {
				objetivo = c;
				break;
			}
		}
		
		if (objetivo == null) {
			System.out.println("No se encontró una certificación con ese ID.");
			return;
		}
		
		System.out.print("Nuevo nombre: ");
		String nombre = sc.nextLine();
		
		System.out.print("Nueva descripcion: ");
		String descripcion = sc.nextLine();
		
		System.out.print("Nueva validez: ");
		int validez = Integer.parseInt(sc.nextLine());
		
		System.out.print("Nuevos créditos: ");
		int creditos = Integer.parseInt(sc.nextLine());
		
//		objetivo.setNombre(nombre);
//		objetivo.setDescripcion(descripcion);
//		objetivo.setValidez(validez);
//		objetivo.setCreditos(creditos);
		
		guardarCertificaciones();
		
		System.out.println("Certificación modificada exitosamente.");
	}
	
	public void generarCertificados(String idCertificacion) {
		Certificacion cert = null;
		
		for (Certificacion c : certificaciones) {
			if (c.getId().equals(idCertificacion)) {
				cert = c;
				break;
			}
		}
		
		if (cert == null) {
			System.out.println("Certificacion no encontrada");
			return;
		}
		
		for (RegistroCertificacion rc : registros) {
			if (rc.getId().equals(idCertificacion)) {
				Estudiante est = null;
				
				for (Estudiante e : estudiantes) {
					if (e.getRut().equals(rc.getRut())) {
						est = e;
						break;
					}
				}
				
				if (est == null) continue; {
					int progreso = calcularProgresoCertificacion(est,cert);
					
					if (progreso == 100) {
						System.out.println("===================================");
						System.out.println("     CERTIFICADO DE COMPLECIÓN     ");
						System.out.println("===================================");
						System.out.println("Estudiante: " + est.getNombre() + "  (" + est.getRut() + ")");
						System.out.println("Certificación : " + cert.getNombre());
						System.out.println("Estado : COMPLETADA");
						System.out.println("Fecha: " + java.time.LocalDate.now());
						System.out.println("===================================");
						System.out.println();
					}
				}
			}
		}
	}
	
	public void estadisticasInscripcion(String idCertificacion) {
		
		int total = 0;
		int completadas = 0;
		int enProgreso = 0;
		int pendientes = 0;
		
		for(RegistroCertificacion rc :registros) {
			if (rc.getId().equals(idCertificacion)) {
				
			
			total++;
			
			if (rc.getProgreso() ==100) {
				completadas++;
			}else if (rc.getProgreso() > 0) {
				enProgreso++;
			}else {
				pendientes++;
			}
		}
	}
	
	if (total == 0) {
		System.out.println("No hay estudiantes inscritos en esta certificación.");
		return;
	}
	System.out.println("=== ESTADÍSTICAS CERTIFICACIÓN " + idCertificacion + "===");
	System.out.println("Inscritos totales: " + total);
	System.out.println("Completadas: " + completadas + " (" + (completadas*100/total) + "%)");
	System.out.println("En progreso: " + enProgreso + " (" + (enProgreso*100/total) + "%)");
	System.out.println("Pendientes/Reprobadas: " + pendientes + "(" + (pendientes*100/total) + "%)");
	System.out.println();
	}
	
	public void analizarAsignaturasCriticas(String idCertificacion) {
		
		Certificacion cert = null;
		
		for (Certificacion c : certificaciones) {
			if (c.getId().equals(idCertificacion)) { 
				cert = c;
				break;
			}
		}
		if (cert == null) {
			System.out.println("Certificación no encontrada.");
			return;
			
		}
		String asignaturaCritica = null;
		int peorPorcentaje = -1;
		
		for (String nrc : cert.getAsigCerts()) {
			int total = 0;
			int reprobados = 0;
			
			for (Nota n : notas) {
				if (n.getCodigo().equals(nrc)) {
					total++;
					if (!n.isEstado()) {
						reprobados++;
					}
				}
			}
			if (total>0) {
				int porcentaje = (reprobados*100)/total;
				
				if (porcentaje > peorPorcentaje) {
					peorPorcentaje = porcentaje;
					asignaturaCritica = nrc;
				}
			}
		}
		if (asignaturaCritica == null) {
			System.out.println("No hay datos suficientes para análisis.");
			return;
		}
		Curso cursoCritico = null;
		
		for (Curso c :cursos) {
			if (c.getNrc().equals(asignaturaCritica)) {
				cursoCritico = c;
				break;
			}
		}
		System.out.println("=== ASIGNATURA MÁS CRITICA ===");
		System.out.println("NRC: " + asignaturaCritica);
		if (cursoCritico !=null)
			System.out.println("Nombre: "+cursoCritico.getNombre());
		System.out.println("Tasa de reprobación: "+peorPorcentaje + "%");
		System.out.println();
	}
	
	public void mostrarPerfilEstudiante(String rutBuscado) {
		Estudiante est = null;
		
		for (Estudiante e : estudiantes) {
			if (e.getRut().equals(rutBuscado));{
				est = e;
				break;
				
			}
		}
		if (est == null) {
			System.out.println("Estudiante no encontrado.");
			return;
		}
		
		System.out.println("=== PERFIL DEL ESTUDIANTE ===");
		System.out.println("RUT: " + est.getRut() );
		System.out.println("Nombre: "+est.getNombre());
		System.out.println("Carrera: " + est.getCarrera());
		System.out.println("Semestre: " + est.getSemestre());
		System.out.println("Correo: " + est.getCorreo());
		System.out.println();
		
		System.out.println("--- Certificaciones Inscritas ---");
		
		for (RegistroCertificacion rc : registros) {
			if (rc.getRut().equals(est.getRut())) {
				
				Certificacion cert = null;
				
				for (Certificacion c : certificaciones) {
					if (c.getId().equals(rc.getId())) {
						cert = c;
						break;
						
					}
				}
				if (cert == null) {
					int progreso = calcularProgresoCertificacion(est, cert);
					System.out.println(cert.getId() + " - " + cert.getNombre());
					System.out.println("Progreso: " + progreso + "%");
					System.out.println();
				}
			}
		}
		
		System.out.println("--- Cursos y Notas ---");
		
		for (Nota n : notas) {
			if (n.getRut().equals(est.getRut())) {
				Curso curso = null;
				
				for (Curso c : cursos) {
					if (c.getNrc().equals(n.getCodigo())) {
						curso = c;
						break;
					}
				}
				if (curso != null) {
					String estado = n.isEstado() ? "Aprobado" : "Reprobado";
					System.out.println(curso.getNrc() + " - " + curso.getNombre()
							+ " - Nota: " + n.getCalificacion()
							+ " - Estado: " + estado
							+ " - Semestre: " + n.getSemestre());
				}
			}
		}
		
		System.out.println();
	}
	
	public void validarAvance(String rutBuscado, String idCertificacion) {
		Estudiante est = null;

	    for (Estudiante e : estudiantes) {
	        if (e.getRut().equals(rutBuscado)) {
	            est = e;
	            break;
	        }
	    }

	    if (est == null) {
	        System.out.println("Estudiante no encontrado.");
	        return;
	    }

	    Certificacion cert = null;

	    for (Certificacion c : certificaciones) {
	        if (c.getId().equals(idCertificacion)) {
	            cert = c;
	            break;
	        }
	    }

	    if (cert == null) {
	        System.out.println("Certificación no encontrada.");
	        return;
	    }

	    int progreso = calcularProgresoCertificacion(est, cert);

	    if (progreso == 100) {

	        for (RegistroCertificacion rc : registros) {
	            if (rc.getRut().equals(rutBuscado) && rc.getId().equals(idCertificacion)) {
	                rc.setEstado("Completada");
	                rc.setProgreso(100);
	                break;
	            }
	        }

	        System.out.println("El estudiante ha completado la certificación.");
	    } 
	    else {
	        System.out.println("El estudiante no ha completado la certificación. Progreso: " + progreso + "%");
	    }
	}
	
	public void mostrarInformacionPersonal(String rutBuscado) {

	    Estudiante est = null;

	    for (Estudiante e : estudiantes) {
	        if (e.getRut().equals(rutBuscado)) {
	            est = e;
	            break;
	        }
	    }

	    if (est == null) {
	        System.out.println("Estudiante no encontrado.");
	        return;
	    }

	    System.out.println("=== INFORMACIÓN PERSONAL ===");
	    System.out.println("RUT: " + est.getRut());
	    System.out.println("Nombre: " + est.getNombre());
	    System.out.println("Carrera: " + est.getCarrera());
	    System.out.println("Semestre: " + est.getSemestre());
	    System.out.println("Correo: " + est.getCorreo());
	    System.out.println();
	}
	
	public void mostrarMallaCurricular(String rutBuscado) {

	    Map<Integer, ArrayList<String>> semestres = new LinkedHashMap<>();

	    for (Curso c : cursos) {
	        int sem = c.getSemestre();
	        if (!semestres.containsKey(sem)) {
	            semestres.put(sem, new ArrayList<>());
	        }

	        String estado = "Pendiente";

	        for (Nota n : notas) {
	            if (n.getRut().equals(rutBuscado) && n.getCodigo().equals(c.getNrc())) {
	                estado = n.isEstado() ? "Aprobado" : "Reprobado";
	                break;
	            }
	        }

	        String linea = c.getNrc() + " - " + c.getNombre() + " (" + estado + ")";
	        semestres.get(sem).add(linea);
	    }

	    System.out.println("=== MALLA CURRICULAR ===");

	    for (Integer sem : semestres.keySet()) {
	        System.out.println("Semestre " + sem + ":");

	        for (String s : semestres.get(sem)) {
	            System.out.println(" - " + s);
	        }

	        System.out.println();
	    }
	}
	
	public void mostrarPromedios(String rutBuscado) {

	    double sumaGeneral = 0;
	    int cantidadGeneral = 0;

	    Map<String, ArrayList<Double>> porSemestre = new LinkedHashMap<>();

	    for (Nota n : notas) {
	        if (n.getRut().equals(rutBuscado)) {

	            sumaGeneral += n.getCalificacion();
	            cantidadGeneral++;

	            String sem = n.getSemestre();

	            if (!porSemestre.containsKey(sem)) {
	                porSemestre.put(sem, new ArrayList<>());
	            }

	            porSemestre.get(sem).add(n.getCalificacion());
	        }
	    }

	    if (cantidadGeneral == 0) {
	        System.out.println("No hay notas registradas.");
	        return;
	    }

	    double promedioGeneral = sumaGeneral / cantidadGeneral;

	    System.out.println("=== PROMEDIOS DEL ESTUDIANTE ===");
	    System.out.println("Promedio General: " + String.format("%.2f", promedioGeneral));
	    System.out.println();

	    System.out.println("Promedio por Semestre:");

	    for (String sem : porSemestre.keySet()) {
	        double suma = 0;
	        int cant = porSemestre.get(sem).size();

	        for (Double d : porSemestre.get(sem)) {
	            suma += d;
	        }

	        double prom = suma / cant;

	        System.out.println(sem + " → " + String.format("%.2f", prom));
	    }

	    System.out.println();
	}
	
	public void listarCertificaciones() {

	    System.out.println("=== CERTIFICACIONES DISPONIBLES ===");

	    for (Certificacion c : certificaciones) {
	        System.out.println(c.getId() + " - " + c.getNombre()
	            + " | Créditos: " + c.getCreditos()
	            + " | Validez: " + c.getValidez() + " meses");
	    }

	    System.out.println();
	}
	
	public void mostrarDetalleCertificacion(String idCert) {

	    Certificacion cert = null;

	    for (Certificacion c : certificaciones) {
	        if (c.getId().equals(idCert)) {
	            cert = c;
	            break;
	        }
	    }

	    if (cert == null) {
	        System.out.println("Certificación no encontrada.");
	        return;
	    }

	    System.out.println("=== DETALLE DE CERTIFICACIÓN ===");
	    System.out.println("ID: " + cert.getId());
	    System.out.println("Nombre: " + cert.getNombre());
	    System.out.println("Descripción: " + cert.getDescripcion());
	    System.out.println("Créditos totales: " + cert.getCreditos());
	    System.out.println("Validez: " + cert.getValidez() + " meses");
	    System.out.println();
	    System.out.println("Asignaturas requeridas:");

	    for (String nrc : cert.getAsigCerts()) {
	        Curso curso = null;

	        for (Curso c : cursos) {
	            if (c.getNrc().equals(nrc)) {
	                curso = c;
	                break;
	            }
	        }

	        if (curso != null) {
	            System.out.println(" - " + curso.getNrc() + " | " + curso.getNombre());
	        } else {
	            System.out.println(" - " + nrc + " (No existe el curso en cursos.txt)");
	        }
	    }

	    System.out.println();
	}
	
	public void inscribirCertificacion(String rut, String idCert) {
		
		if (!cumplePrerrequisitos(rut, idCert)) {
	        return;
	    }
		
	    Certificacion cert = null;

	    for (Certificacion c : certificaciones) {
	        if (c.getId().equals(idCert)) {
	            cert = c;
	            break;
	        }
	    }

	    if (cert == null) {
	        System.out.println("Certificación no encontrada.");
	        return;
	    }

	    for (RegistroCertificacion rc : registros) {
	        if (rc.getRut().equals(rut) && rc.getId().equals(idCert)) {
	            System.out.println("Ya estás inscrito en esta certificación.");
	            return;
	        }
	    }

	    String fecha = java.time.LocalDate.now().toString();

	    RegistroCertificacion nuevo = new RegistroCertificacion(
	            rut,
	            idCert,
	            fecha,
	            "En progreso",
	            0
	    );

	    registros.add(nuevo);

	    System.out.println("Inscripción exitosa en: " + cert.getNombre());
	    System.out.println("Fecha: " + fecha);
	    System.out.println();
	}
	
	public boolean cumplePrerrequisitos(String rut, String idCert) {

	    Certificacion cert = null;

	    for (Certificacion c : certificaciones) {
	        if (c.getId().equals(idCert)) {
	            cert = c;
	            break;
	        }
	    }

	    if (cert == null) {
	        System.out.println("Certificación no encontrada.");
	        return false;
	    }

	    ArrayList<String> faltantes = new ArrayList<>();

	    for (String nrcCurso : cert.getAsigCerts()) {

	        Curso curso = null;

	        for (Curso c : cursos) {
	            if (c.getNrc().equals(nrcCurso)) {
	                curso = c;
	                break;
	            }
	        }

	        if (curso == null) continue;

	        for (String pre : curso.getPrerrequisitos()) {

	            boolean aprobado = false;

	            for (Nota n : notas) {
	                if (n.getRut().equals(rut) &&
	                    n.getCodigo().equals(pre) &&
	                    n.isEstado()) {
	                    aprobado = true;
	                    break;
	                }
	            }

	            if (!aprobado) {
	                faltantes.add(pre);
	            }
	        }
	    }

	    if (faltantes.isEmpty()) {
	        return true;
	    }

	    System.out.println("No cumple prerrequisitos. Faltan:");

	    for (String f : faltantes) {
	        System.out.println(" - " + f);
	    }

	    return false;
	}
	
	public void mostrarDashboardProgreso(String rutBuscado) {

	    Estudiante est = null;

	    for (Estudiante e : estudiantes) {
	        if (e.getRut().equals(rutBuscado)) {
	            est = e;
	            break;
	        }
	    }

	    if (est == null) {
	        System.out.println("Estudiante no encontrado.");
	        return;
	    }

	    System.out.println("=== TU PROGRESO EN CERTIFICACIONES ===");

	    for (RegistroCertificacion rc : registros) {
	        if (rc.getRut().equals(rutBuscado)) {

	            Certificacion cert = null;

	            for (Certificacion c : certificaciones) {
	                if (c.getId().equals(rc.getId())) {
	                    cert = c;
	                    break;
	                }
	            }

	            if (cert == null) continue;

	            int progreso = calcularProgresoCertificacion(est, cert);

	            System.out.println(cert.getId() + " - " + cert.getNombre());
	            System.out.println("Progreso: " + progreso + "%");

	            String estado = (progreso == 100 ? "Completada" : "En progreso");
	            System.out.println("Estado: " + estado);
	            System.out.println();
	        }
	    }
	}
	
	public void aplicarVisitor(String rut, CertificacionVisitor v) {

	    for (RegistroCertificacion rc : registros) {
	        if (rc.getRut().equals(rut)) {

	            for (Certificacion c : certificaciones) {
	                if (c.getId().equals(rc.getId())) {
	                    c.accept(v);
	                }
	            }
	        }
	    }
	}
}
























