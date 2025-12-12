package sistema;
//Ramiro Alvarado Durán - RUT 19.428.146-3 - ITI
//Patricio Alvarado Durán - RUT 20.955.249-3 - ITI
//Taller 4 - Programación Orientada a Objetos
//Universidad Católica del Norte

import dominio.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JTextArea;

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
	
	public ArrayList<Estudiante> getEstudiantesLista() {
	    return estudiantes;
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
	
	public void modificarCertificacion(String id, String nombre, String descripcion, int validez, int creditos) {

	    Certificacion objetivo = null;

	    for (Certificacion c : certificaciones) {
	        if (c.getId().equals(id)) {
	            objetivo = c;
	            break;
	        }
	    }

	    if (objetivo == null) {
	        System.out.println("No se encontró certificación.");
	        return;
	    }

	    objetivo.setNombre(nombre);
	    objetivo.setDescripcion(descripcion);
	    objetivo.setValidez(validez);
	    objetivo.setCreditos(creditos);

	    guardarCertificaciones();
	}

	
	public void generarCertificados(String idCertificacion, JTextArea area) {

	    Certificacion cert = null;

	    for (Certificacion c : certificaciones) {
	        if (c.getId().equals(idCertificacion)) {
	            cert = c;
	            break;
	        }
	    }

	    if (cert == null) {
	        area.append("Certificación no encontrada.\n");
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

	            if (est == null) continue;

	            int progreso = calcularProgresoCertificacion(est, cert);

	            if (progreso == 100) {
	                area.append("\n--- CERTIFICADO ---\n");
	                area.append("Estudiante: " + est.getNombre() + " (" + est.getRut() + ")\n");
	                area.append("Certificación: " + cert.getNombre() + "\n");
	                area.append("Fecha: " + java.time.LocalDate.now() + "\n");
	            }
	        }
	    }
	}

	
	public void mostrarAsignaturasPendientes(String rutBuscado) {

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

	    System.out.println("=== ASIGNATURAS PENDIENTES ===");

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

	            System.out.println(cert.getId() + " - " + cert.getNombre() + ":");

	            for (String nrc : cert.getAsigCerts()) {

	                boolean aprobada = false;

	                for (Nota n : notas) {
	                    if (n.getRut().equals(rutBuscado) &&
	                        n.getCodigo().equals(nrc) &&
	                        n.getEstado().equalsIgnoreCase("Aprobado")) {
	                        aprobada = true;
	                        break;
	                    }
	                }

	                if (!aprobada) {

	                    Curso curso = null;

	                    for (Curso c : cursos) {
	                        if (c.getNrc().equals(nrc)) {
	                            curso = c;
	                            break;
	                        }
	                    }

	                    if (curso != null) {
	                        System.out.println(" - " + curso.getNrc() + " " + curso.getNombre());
	                    } else {
	                        System.out.println(" - " + nrc);
	                    }
	                }
	            }

	            System.out.println();
	        }
	    }
	}

	public Object login(String username, String password) {

	    // 1. Buscar en usuarios.txt → Admin y Coordinador
	    for (Usuario u : usuarios) {
	        if (u.getUsername().equals(username) &&
	            u.getPassword().equals(password)) {
	            return u;  // Devuelve Usuario
	        }
	    }

	    // 2. Buscar en estudiantes.txt → Estudiantes
	    for (Estudiante e : estudiantes) {
	        // Login con correo
	        if (e.getCorreo().equals(username) &&
	            e.getPassword().equals(password)) {
	            return e;  // Devuelve Estudiante
	        }
	    }

	    return null; // No existe
	}

	
	public String estadoCurso(String rut, String nrc) {

	    for (Nota n : notas) {
	        if (n.getRut().equals(rut) && n.getCodigo().equals(nrc)) {
	            return n.getEstado();   // "Aprobado", "Reprobado", "Cursando"
	        }
	    }

	    return "No cursado";
	}
	
	public ArrayList<Curso> getCursos() {
	    return cursos;
	}
	
	public void mostrarInformacionPersonal(String rut) {

	    Estudiante est = null;

	    for (Estudiante e : estudiantes) {
	        if (e.getRut().equals(rut)) {
	            est = e;
	            break;
	        }
	    }

	    if (est == null) {
	        System.out.println("Estudiante no encontrado.");
	        return;
	    }

	    System.out.println("=== INFORMACIÓN PERSONAL ===");
	    System.out.println("Nombre: " + est.getNombre());
	    System.out.println("RUT: " + est.getRut());
	    System.out.println("Carrera: " + est.getCarrera());
	    System.out.println("Semestre: " + est.getSemestre());
	    System.out.println("Correo: " + est.getCorreo());
	}

	public void mostrarPromedios(String rut) {

	    double sumaGeneral = 0;
	    int contadorGeneral = 0;

	    // Para almacenar semestres únicos
	    ArrayList<String> semestres = new ArrayList<>();

	    // Primero: encontrar todos los semestres donde el estudiante tiene notas aprobadas
	    for (Nota n : notas) {
	        if (n.getRut().equals(rut) && n.getEstado().equalsIgnoreCase("Aprobado")) {
	            if (!semestres.contains(n.getSemestre())) {
	                semestres.add(n.getSemestre());
	            }

	            sumaGeneral += n.getCalificacion();
	            contadorGeneral++;
	        }
	    }

	    if (contadorGeneral == 0) {
	        System.out.println("No hay notas aprobadas.");
	        return;
	    }

	    System.out.println("=== PROMEDIOS ===");
	    System.out.println("Promedio general: " + (sumaGeneral / contadorGeneral));
	    System.out.println();
	    System.out.println("Promedio por semestre:");

	    // Segundo: para cada semestre, calcular su propio promedio
	    for (String sem : semestres) {

	        double suma = 0;
	        int contador = 0;

	        for (Nota n : notas) {
	            if (n.getRut().equals(rut)
	                && n.getEstado().equalsIgnoreCase("Aprobado")
	                && n.getSemestre().equals(sem)) {

	                suma += n.getCalificacion();
	                contador++;
	            }
	        }

	        System.out.println("Semestre " + sem + ": " + (suma / contador));
	    }
	}



	public void listarCertificaciones() {
	    System.out.println("=== CERTIFICACIONES DISPONIBLES ===");
	    for (Certificacion c : certificaciones) {
	        System.out.println(c.getId() + " - " + c.getNombre());
	    }
	}
	
	public void mostrarDetalleCertificacion(String id) {

	    Certificacion cert = null;

	    for (Certificacion c : certificaciones) {
	        if (c.getId().equals(id)) {
	            cert = c;
	            break;
	        }
	    }

	    if (cert == null) {
	        System.out.println("Certificación no encontrada.");
	        return;
	    }

	    System.out.println("=== DETALLE CERTIFICACIÓN ===");
	    System.out.println("ID: " + cert.getId());
	    System.out.println("Nombre: " + cert.getNombre());
	    System.out.println("Descripción: " + cert.getDescripcion());
	    System.out.println("Créditos: " + cert.getCreditos());
	    System.out.println("Validez: " + cert.getValidez());
	    System.out.println("Asignaturas requeridas: " + cert.getAsigCerts());
	}


	public boolean cumpleRequisitos(String rut, String idCert) {

	    Certificacion cert = null;

	    for (Certificacion c : certificaciones) {
	        if (c.getId().equals(idCert)) {
	            cert = c;
	            break;
	        }
	    }

	    if (cert == null) return false;

	    for (String nrc : cert.getAsigCerts()) {

	        boolean aprobado = false;

	        for (Nota n : notas) {
	            if (n.getRut().equals(rut)
	             && n.getCodigo().equals(nrc)
	             && n.getEstado().equalsIgnoreCase("Aprobado")) {
	                aprobado = true;
	                break;
	            }
	        }

	        if (!aprobado) return false;
	    }

	    return true;
	}
	
	public void inscribirCertificacion(String rut, String idCert) {

	    Estudiante est = null;

	    for (Estudiante e : estudiantes) {
	        if (e.getRut().equals(rut)) {
	            est = e;
	            break;
	        }
	    }

	    if (est == null) {
	        System.out.println("Estudiante no existe.");
	        return;
	    }

	    // verificar si ya está inscrito
	    for (RegistroCertificacion rc : registros) {
	        if (rc.getRut().equals(rut) && rc.getId().equals(idCert)) {
	            System.out.println("El estudiante ya está inscrito en esta certificación.");
	            return;
	        }
	    }

	    // verificar requisitos
	    if (!cumpleRequisitos(rut, idCert)) {
	        System.out.println("No cumple los prerrequisitos.");
	        return;
	    }

	    RegistroCertificacion nuevo =
	        new RegistroCertificacion(rut, idCert,
	            java.time.LocalDate.now().toString(), "En progreso", 0);

	    registros.add(nuevo);

	    System.out.println("Inscripción realizada con éxito.");
	}
	
	public void estadisticasInscripcion(String idCert, JTextArea area) {

	    int total = 0;

	    for (RegistroCertificacion rc : registros) {
	        if (rc.getId().equals(idCert)) {
	            total++;
	        }
	    }

	    area.append("Estudiantes inscritos en " + idCert + ": " + total + "\n");
	}

	
	public void mostrarDashboardProgreso(String rut) {

	    Estudiante est = null;

	    for (Estudiante e : estudiantes) {
	        if (e.getRut().equals(rut)) {
	            est = e;
	            break;
	        }
	    }

	    if (est == null) {
	        System.out.println("Estudiante no encontrado.");
	        return;
	    }

	    System.out.println("=== DASHBOARD DE PROGRESO ===");

	    for (RegistroCertificacion rc : registros) {
	        if (rc.getRut().equals(rut)) {

	            Certificacion cert = null;

	            for (Certificacion c : certificaciones) {
	                if (c.getId().equals(rc.getId())) {
	                    cert = c;
	                    break;
	                }
	            }

	            if (cert == null) continue;

	            int p = calcularProgresoCertificacion(est, cert);

	            System.out.println(cert.getNombre() + ": " + p + "%");

	            if (p == 100) System.out.println("Estado: COMPLETADA\n");
	            else System.out.println("Estado: En progreso\n");
	        }
	    }
	}
	
	public void aplicarAccionesVisitor(String rut) {

	    Estudiante est = null;

	    for (Estudiante e : estudiantes) {
	        if (e.getRut().equals(rut)) {
	            est = e;
	            break;
	        }
	    }

	    if (est == null) {
	        System.out.println("Estudiante no encontrado.");
	        return;
	    }

	    for (RegistroCertificacion rc : registros) {
	        if (rc.getRut().equals(rut)) {

	            Certificacion cert = null;

	            for (Certificacion c : certificaciones) {
	                if (c.getId().equals(rc.getId())) {
	                    cert = c;
	                    break;
	                }
	            }

	            if (cert == null) continue;

	            int progreso = calcularProgresoCertificacion(est, cert);

	            System.out.println("Certificación: " + cert.getNombre());

	            cert.aceptar(new visitor.RecomendacionVisitor());
	            cert.aceptar(new visitor.AdvertenciaVisitor(progreso));

	            System.out.println();
	        }
	    }
	}
	
	public void analizarAsignaturasCriticas(String idCert, JTextArea area) {

	    Certificacion cert = null;

	    for (Certificacion c : certificaciones) {
	        if (c.getId().equals(idCert)) {
	            cert = c;
	            break;
	        }
	    }

	    if (cert == null) {
	        area.append("Certificación no encontrada.\n");
	        return;
	    }

	    String peor = "";
	    int maxReprobados = 0;

	    for (String nrc : cert.getAsigCerts()) {

	        int reprobados = 0;

	        for (Nota n : notas) {
	            if (n.getCodigo().equals(nrc)
	             && n.getEstado().equalsIgnoreCase("Reprobado")) {
	                reprobados++;
	            }
	        }

	        if (reprobados > maxReprobados) {
	            maxReprobados = reprobados;
	            peor = nrc;
	        }
	    }

	    if (maxReprobados == 0) {
	        area.append("No hay asignaturas críticas.\n");
	    } else {
	        area.append("Asignatura crítica: " + peor + 
	                    " con " + maxReprobados + " reprobaciones.\n");
	    }
	}
	
	public void mostrarPerfilEstudiante(String rut, JTextArea area) {

	    Estudiante est = null;

	    for (Estudiante e : estudiantes) {
	        if (e.getRut().equals(rut)) {
	            est = e;
	            break;
	        }
	    }

	    if (est == null) {
	        area.append("Estudiante no encontrado.\n");
	        return;
	    }

	    area.append("\n=== PERFIL DEL ESTUDIANTE ===\n");
	    area.append("Nombre: " + est.getNombre() + "\n");
	    area.append("RUT: " + est.getRut() + "\n");
	    area.append("Carrera: " + est.getCarrera() + "\n");
	    area.append("Semestre: " + est.getSemestre() + "\n");
	    area.append("Correo: " + est.getCorreo() + "\n\n");

	    area.append("Notas:\n");

	    for (Nota n : notas) {
	        if (n.getRut().equals(rut)) {
	            area.append(n.getCodigo() + " - " + 
	            n.getCalificacion() + " (" + n.getEstado() + ")\n");
	        }
	    }
	}
	
	public void validarAvance(String rut, String idCert, JTextArea area) {

	    Estudiante est = null;

	    for (Estudiante e : estudiantes) {
	        if (e.getRut().equals(rut)) {
	            est = e;
	            break;
	        }
	    }

	    if (est == null) {
	        area.append("Estudiante no encontrado.\n");
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
	        area.append("Certificación no encontrada.\n");
	        return;
	    }

	    int p = calcularProgresoCertificacion(est, cert);

	    area.append("\n=== VALIDACIÓN DE AVANCE ===\n");
	    area.append("Estudiante: " + est.getNombre() + "\n");
	    area.append("Certificación: " + cert.getNombre() + "\n");
	    area.append("Progreso: " + p + "%\n");

	    area.append(p == 100 ? "Estado: COMPLETADA\n" : "Estado: En progreso\n");
	}



	
}