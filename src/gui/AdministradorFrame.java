// Ramiro Alvarado Durán - 19.428.146-3 - ITI
// Patricio Alvarado Durán - 20.955.249-3 - ITI
// Taller 4 - Programación Orientada a Objetos
// Universidad Católica del Norte

package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import sistema.Sistema;
import dominio.Usuario;

public class AdministradorFrame extends JFrame {

    public AdministradorFrame(Sistema sis, Usuario admin) {

        setTitle("Panel Administrador");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnCrear = new JButton("Crear Usuario");
        JButton btnModificar = new JButton("Modificar Usuario");
        JButton btnEliminar = new JButton("Eliminar Usuario");
        JButton btnReset = new JButton("Restablecer Contraseña");

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(btnCrear);
        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnReset);

        add(panel);


        btnCrear.addActionListener(e -> {

            String tipo = JOptionPane.showInputDialog("Tipo de usuario (Estudiante / Coordinador):");

            if (tipo == null) return;

            if (tipo.equalsIgnoreCase("Coordinador")) {

                String user = JOptionPane.showInputDialog("Nombre de usuario:");
                String pass = JOptionPane.showInputDialog("Contraseña:");
                String area = JOptionPane.showInputDialog("Área:");

                if (user != null && pass != null && area != null) {
                    sis.crearCoordinador(user, pass, area);
                }
            }
            else if (tipo.equalsIgnoreCase("Estudiante")) {

                String rut = JOptionPane.showInputDialog("RUT:");
                String nombre = JOptionPane.showInputDialog("Nombre:");
                String carrera = JOptionPane.showInputDialog("Carrera:");
                String sem = JOptionPane.showInputDialog("Semestre:");
                String correo = JOptionPane.showInputDialog("Correo:");
                String pass = JOptionPane.showInputDialog("Contraseña:");

                if (rut != null && nombre != null && carrera != null &&
                    sem != null && correo != null && pass != null) {

                    int semestre = Integer.parseInt(sem);
                    sis.crearEstudiante(rut, nombre, carrera, semestre, correo, pass);
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Tipo inválido.");
            }
        });


        btnModificar.addActionListener(e -> {

            String tipo = JOptionPane.showInputDialog("Tipo (Estudiante / Coordinador):");

            if (tipo == null) return;

            if (tipo.equalsIgnoreCase("Coordinador")) {

                String username = JOptionPane.showInputDialog("Username del coordinador:");
                if (username != null) {
                    sis.modificarCoordinador(username, new Scanner(System.in));
                }
            }
            else if (tipo.equalsIgnoreCase("Estudiante")) {

                String rut = JOptionPane.showInputDialog("RUT del estudiante:");
                if (rut != null) {
                    sis.modificarEstudiante(rut, new Scanner(System.in));
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Tipo inválido.");
            }
        });


        btnEliminar.addActionListener(e -> {

            String tipo = JOptionPane.showInputDialog("Tipo (Estudiante / Coordinador):");

            if (tipo == null) return;

            if (tipo.equalsIgnoreCase("Coordinador")) {
                String username = JOptionPane.showInputDialog("Username a eliminar:");
                if (username != null) sis.eliminarCoordinador(username);
            }
            else if (tipo.equalsIgnoreCase("Estudiante")) {
                String rut = JOptionPane.showInputDialog("RUT a eliminar:");
                if (rut != null) sis.eliminarEstudiante(rut);
            }
            else {
                JOptionPane.showMessageDialog(this, "Tipo inválido.");
            }
        });

        btnReset.addActionListener(e -> {

            String tipo = JOptionPane.showInputDialog("Tipo (Estudiante / Coordinador):");

            if (tipo == null) return;

            if (tipo.equalsIgnoreCase("Coordinador")) {
                String username = JOptionPane.showInputDialog("Username del coordinador:");
                if (username != null) {
                    sis.restablecerPassCoordinador(username, new Scanner(System.in));
                }
            }
            else if (tipo.equalsIgnoreCase("Estudiante")) {
                String rut = JOptionPane.showInputDialog("RUT del estudiante:");
                if (rut != null) {
                    sis.restablecerPassEstudiante(rut, new Scanner(System.in));
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Tipo inválido.");
            }
        });

        setVisible(true);
    }
}
