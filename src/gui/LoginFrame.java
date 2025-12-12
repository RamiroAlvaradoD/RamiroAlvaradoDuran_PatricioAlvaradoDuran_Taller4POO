// Ramiro Alvarado Durán - 19.428.146-3 - ITI
// Patricio Alvarado Durán - 20.955.249-3 - ITI
// Taller 4 - Programación Orientada a Objetos
// Universidad Católica del Norte

package gui;

import javax.swing.*;
import java.awt.*;
import sistema.Sistema;
import dominio.*;

public class LoginFrame extends JFrame {

    public LoginFrame(Sistema sis) {

        setTitle("Login Taller 4");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblUser = new JLabel("Usuario:");
        JLabel lblPass = new JLabel("Contraseña:");

        JTextField txtUser = new JTextField();
        JPasswordField txtPass = new JPasswordField();

        JButton btnLogin = new JButton("Ingresar");

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(lblUser);
        panel.add(txtUser);
        panel.add(lblPass);
        panel.add(txtPass);
        panel.add(new JLabel());
        panel.add(btnLogin);

        add(panel);


        btnLogin.addActionListener(e -> {

            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

            Object obj = sis.login(user, pass);

            if (obj == null) {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
                return;
            }


            if (obj instanceof Administrador) {
                dispose();
                new AdministradorFrame(sis, (Administrador) obj);
                return;
            }


            if (obj instanceof Coordinador) {
                dispose();
                new CoordinadorFrame(sis, (Coordinador) obj);
                return;
            }

 
            if (obj instanceof Estudiante) {
                dispose();
                new EstudianteFrame(sis, (Estudiante) obj);
                return;
            }

            JOptionPane.showMessageDialog(this, "Error: rol desconocido.");
        });

        setVisible(true);
    }
}
