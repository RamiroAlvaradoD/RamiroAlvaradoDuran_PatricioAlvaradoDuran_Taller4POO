package visitor;
//Ramiro Alvarado Durán - RUT 19.428.146-3 - ITI
//Patricio Alvarado Durán - RUT 20.955.249-3 - ITI
//Taller 4 - Programación Orientada a Objetos
//Universidad Católica del Norte
import dominio.Certificacion;

public interface CertificacionVisitor {
	void visitar(Certificacion c);

}
