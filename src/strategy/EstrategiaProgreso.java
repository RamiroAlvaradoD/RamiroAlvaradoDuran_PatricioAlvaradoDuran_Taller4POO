package strategy;
//Ramiro Alvarado Durán - RUT 19.428.146-3 - ITI
//Patricio Alvarado Durán - RUT 20.955.249-3 - ITI
//Taller 4 - Programación Orientada a Objetos
//Universidad Católica del Norte

import java.util.ArrayList;
import dominio.Estudiante;
import dominio.Certificacion;
import dominio.Nota;

public interface EstrategiaProgreso {
    int calcularProgreso(Estudiante e, Certificacion c, ArrayList<Nota> notas);
}

