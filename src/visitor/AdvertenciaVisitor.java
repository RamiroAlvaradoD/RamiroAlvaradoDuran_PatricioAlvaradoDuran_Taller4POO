package visitor;

import dominio.Certificacion;

public class AdvertenciaVisitor implements CertificacionVisitor {

    private int progreso;

    public AdvertenciaVisitor(int progreso) {
        this.progreso = progreso;
    }

    @Override
    public void visitar(Certificacion c) {

        if (progreso < 30) {
            System.out.println("Advertencia: tu avance en \"" + c.getNombre() + "\" es bajo. Considera priorizar esta certificación.");
        } else {
            System.out.println("Buen trabajo en \"" + c.getNombre() + "\". Sigue avanzando.");
        }

        System.out.println();
    }
}
