// Ramiro Alvarado Durán - 19.428.146-3 - ITI
// Patricio Alvarado Durán - 20.955.249-3 - ITI
// Taller 4 - Programación Orientada a Objetos
// Universidad Católica del Norte

package gui;

import javax.swing.*;
import java.awt.*;
import sistema.Sistema;
import dominio.Usuario;

public class CoordinadorFrame extends JFrame {

    public CoordinadorFrame(Sistema sis, Usuario coord) {

        setTitle("Panel Coordinador");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);

        JButton btnModCert = new JButton("Modificar Certificación");
        JButton btnCertificados = new JButton("Generar Certificados");
        JButton btnStats = new JButton("Estadísticas Inscripción");
        JButton btnCriticas = new JButton("Asignaturas Críticas");
        JButton btnPerfil = new JButton("Perfil Estudiante");
        JButton btnValidar = new JButton("Validar Avance");

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(btnModCert);
        panel.add(btnCertificados);
        panel.add(btnStats);
        panel.add(btnCriticas);
        panel.add(btnPerfil);
        panel.add(btnValidar);

        add(panel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);


      btnModCert.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Ingrese ID de la certificación:");

            if (id != null && !id.isBlank()) {

                JTextField nombre = new JTextField();
                JTextField descr = new JTextField();
                JTextField val = new JTextField();
                JTextField credit = new JTextField();

                Object[] msg = {
                    "Nuevo nombre:", nombre,
                    "Nueva descripción:", descr,
                    "Nueva validez (meses):", val,
                    "Nuevos créditos:", credit
                };

                int opc = JOptionPane.showConfirmDialog(this, msg, 
                        "Modificar Certificación", JOptionPane.OK_CANCEL_OPTION);

                if (opc == JOptionPane.OK_OPTION) {
                    sis.modificarCertificacion(id,
                            nombre.getText(),
                            descr.getText(),
                            Integer.parseInt(val.getText()),
                            Integer.parseInt(credit.getText()));

                    area.append("\nCertificación modificada: " + id + "\n");
                }
            }
        });


      btnCertificados.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("ID certificación:");

            if (id != null && !id.isBlank()) {
                area.append("\n=== CERTIFICADOS ===\n");
                sis.generarCertificados(id, area); 
            }
        });


       btnStats.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("ID certificación:");

            if (id != null && !id.isBlank()) {
                area.append("\n=== ESTADÍSTICAS ===\n");
                sis.estadisticasInscripcion(id, area);
            }
        });


        btnCriticas.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("ID certificación:");

            if (id != null && !id.isBlank()) {
                area.append("\n=== ASIGNATURAS CRÍTICAS ===\n");
                sis.analizarAsignaturasCriticas(id, area);
            }
        });


       btnPerfil.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog("RUT del estudiante:");

            if (rut != null && !rut.isBlank()) {
                area.append("\n=== PERFIL ===\n");
                sis.mostrarPerfilEstudiante(rut, area);
            }
        });


      btnValidar.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog("RUT estudiante:");
            String id = JOptionPane.showInputDialog("ID certificación:");

            if (rut != null && id != null &&
                !rut.isBlank() && !id.isBlank()) {

                area.append("\n=== VALIDACIÓN DE AVANCE ===\n");
                sis.validarAvance(rut, id, area);
            }
        });

        setVisible(true);
    }
}
