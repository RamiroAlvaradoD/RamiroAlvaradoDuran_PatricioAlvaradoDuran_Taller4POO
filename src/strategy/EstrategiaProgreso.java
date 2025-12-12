package strategy;

import java.util.ArrayList;
import dominio.Estudiante;
import dominio.Certificacion;
import dominio.Nota;

public interface EstrategiaProgreso {
    int calcularProgreso(Estudiante e, Certificacion c, ArrayList<Nota> notas);
}

