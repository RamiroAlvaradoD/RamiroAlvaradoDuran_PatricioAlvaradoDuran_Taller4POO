package strategy;

import java.util.ArrayList;

import dominio.Certificacion;
import dominio.Curso;
import dominio.Estudiante;
import dominio.Nota;

public class ProgresoPonderado implements EstrategiaProgreso {

    private ArrayList<Curso> cursos;

    public ProgresoPonderado(ArrayList<Curso> cursos) {
        this.cursos = cursos;
    }

    @Override
    public int calcularProgreso(Estudiante e, Certificacion c, ArrayList<Nota> notas) {

        int creditosTotales = 0;
        int creditosAprobados = 0;

        for (String nrc : c.getAsigCerts()) {

            Curso curso = buscarCurso(nrc);

            if (curso != null) {
                creditosTotales += curso.getCreditos();
            }

            for (Nota n : notas) {
                if (n.getRut().equals(e.getRut()) &&
                    n.getCodigo().equals(nrc) &&
                    n.getEstado().equalsIgnoreCase("Aprobado")) {

                    if (curso != null) {
                        creditosAprobados += curso.getCreditos();
                    }
                }
            }
        }

        if (creditosTotales == 0) return 0;

        return (creditosAprobados * 100) / creditosTotales;
    }

    private Curso buscarCurso(String nrc) {
        for (Curso c : cursos) {
            if (c.getNrc().equals(nrc)) return c;
        }
        return null;
    }
}


