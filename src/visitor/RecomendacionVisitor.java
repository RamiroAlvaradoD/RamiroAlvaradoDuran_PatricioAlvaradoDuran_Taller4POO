package visitor;

import dominio.Certificacion;

public class RecomendacionVisitor implements CertificacionVisitor {

    @Override
    public void visitar(Certificacion c) {

        String id = c.getId().toUpperCase();

        if (id.startsWith("DS")) {
            System.out.println("Recomendación: practica estructuras de datos y patrones de diseño.");
        }
        else if (id.startsWith("IA")) {
            System.out.println("Recomendación: refuerza álgebra lineal y probabilidad.");
        }
        else if (id.startsWith("CS")) {
            System.out.println("Recomendación: estudia protocolos de red y ciberseguridad básica.");
        }
        else {
            System.out.println("Recomendación general: mantén un ritmo constante de estudio.");
        }

        System.out.println();
    }
}
