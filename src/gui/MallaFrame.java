package gui;

import javax.swing.*;
import java.awt.*;
import sistema.Sistema;
import dominio.Curso;
import dominio.Estudiante;

public class MallaFrame extends JFrame {

    public MallaFrame(Sistema sis, Estudiante est) {

        setTitle("Malla Curricular");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1, 8)); 

        int maxSem = 1;
        for (Curso c : sis.getCursos()) {
            if (c.getSemestre() > maxSem) maxSem = c.getSemestre();
        }

        for (int s = 1; s <= maxSem; s++) {

            JPanel col = new JPanel();
            col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
            col.setBorder(BorderFactory.createTitledBorder("Semestre " + s));

            for (Curso c : sis.getCursos()) {
                if (c.getSemestre() == s) {

                    JButton btn = new JButton("<html>" + c.getNrc() + "<br>" + c.getNombre() + "</html>");

                    String estado = sis.estadoCurso(est.getRut(), c.getNrc());

                    switch (estado.toLowerCase()) {
                        case "aprobado": btn.setBackground(Color.GREEN); break;
                        case "reprobado": btn.setBackground(Color.RED); break;
                        case "cursando": btn.setBackground(Color.YELLOW); break;
                        default: btn.setBackground(Color.LIGHT_GRAY); break;
                    }

                    btn.addActionListener(e -> {
                        JOptionPane.showMessageDialog(this,
                            "NRC: " + c.getNrc() + "\n" +
                            "Nombre: " + c.getNombre() + "\n" +
                            "Créditos: " + c.getCreditos() + "\n" +
                            "Semestre: " + c.getSemestre() + "\n" +
                            "Estado: " + estado
                        );
                    });

                    col.add(btn);
                }
            }

            add(col);
        }

        setVisible(true);
    }
}
