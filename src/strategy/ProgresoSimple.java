package strategy;

import java.util.ArrayList;
import dominio.Estudiante;
import dominio.Certificacion;
import dominio.Nota;

public class ProgresoSimple implements EstrategiaProgreso {

    @Override
    public int calcularProgreso(Estudiante e, Certificacion c, ArrayList<Nota> notas) {

        int total = c.getAsigCerts().size();
        int aprobados = 0;

        for (String nrc : c.getAsigCerts()) {
            for (Nota n : notas) {
                if (n.getRut().equals(e.getRut()) && n.getCodigo().equals(nrc)) {

                    if (n.getEstado().equalsIgnoreCase("Aprobado")) {
                        aprobados++;
                    }

                }
            }
        }

        if (total == 0) return 0;

        return (aprobados * 100) / total;
    }
}

