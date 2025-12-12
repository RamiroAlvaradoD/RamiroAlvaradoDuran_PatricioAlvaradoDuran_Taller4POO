// Ramiro Alvarado Durán - 19.428.146-3 - ITI
// Patricio Alvarado Durán - 20.955.249-3 - ITI
// Taller 4 - Programación Orientada a Objetos
// Universidad Católica del Norte

package gui;

import javax.swing.*;
import java.awt.*;
import sistema.Sistema;
import dominio.Estudiante;

public class EstudianteFrame extends JFrame {

    public EstudianteFrame(Sistema sis, Estudiante est) {

        setTitle("Panel Estudiante");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);

        JButton btnInfo = new JButton("Información Personal");
        JButton btnMalla = new JButton("Malla Curricular");
        JButton btnProm = new JButton("Promedios");
        JButton btnIns = new JButton("Inscribir Certificación");
        JButton btnDash = new JButton("Dashboard Progreso");
        JButton btnVisit = new JButton("Aplicar Visitor");
        JButton btnPend = new JButton("Pendientes");

        JPanel botones = new JPanel(new GridLayout(4, 2));
        botones.add(btnInfo);
        botones.add(btnMalla);
        botones.add(btnProm);
        botones.add(btnIns);
        botones.add(btnDash);
        botones.add(btnVisit);
        botones.add(btnPend);

        add(botones, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);


        btnInfo.addActionListener(e -> {
            area.setText("");
            sis.mostrarInformacionPersonal(est.getRut());
        });

        btnProm.addActionListener(e -> {
            area.setText("");
            sis.mostrarPromedios(est.getRut());
        });

        btnPend.addActionListener(e -> {
            area.setText("");
            sis.mostrarAsignaturasPendientes(est.getRut());
        });

        btnDash.addActionListener(e -> {
            area.setText("");
            sis.mostrarDashboardProgreso(est.getRut());
        });

        btnVisit.addActionListener(e -> {
            area.setText("");
            sis.aplicarAccionesVisitor(est.getRut());
        });

        btnIns.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Ingrese ID de certificación:");
            if (id != null && !id.isEmpty()) {
                area.setText("");
                sis.inscribirCertificacion(est.getRut(), id);
            }
        });

        btnMalla.addActionListener(e -> MallaFX.mostrar(sis, est));


        setVisible(true);
    }
}
