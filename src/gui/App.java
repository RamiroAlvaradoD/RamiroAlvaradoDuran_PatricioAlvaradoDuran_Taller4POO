// Ramiro Alvarado Durán - 19.428.146-3 - ITI
// Patricio Alvarado Durán - 20.955.249-3 - ITI
// Taller 4 - Programación Orientada a Objetos
// Universidad Católica del Norte

package gui;

import javax.swing.*;
import sistema.Sistema;
import dominio.*;

public class App {

    public static void main(String[] args) {

        Sistema sis = Sistema.getInstance();


        sis.cargarDatos();


        SwingUtilities.invokeLater(() -> new LoginFrame(sis));
    }
}
