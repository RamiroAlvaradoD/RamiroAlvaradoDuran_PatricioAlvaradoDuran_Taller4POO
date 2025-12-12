package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import sistema.Sistema;
import dominio.Curso;
import dominio.Estudiante;

public class MallaFX extends Application {

    private static Sistema sis;
    private static Estudiante est;

    public static void mostrar(Sistema sistema, Estudiante estudiante) {
        sis = sistema;
        est = estudiante;

        new JFXPanel();

        Platform.runLater(() -> {
            try {
                new MallaFX().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();

        Label titulo = new Label("Malla Curricular - " + est.getNombre());
        titulo.setFont(new Font("Arial", 24));
        titulo.setPadding(new Insets(15));
        titulo.setAlignment(Pos.CENTER);

        root.setTop(titulo);

        GridPane grid = new GridPane();
        grid.setHgap(40);
        grid.setVgap(18);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.TOP_CENTER);

        int[] filas = new int[20];

        for (Curso c : sis.getCursos()) {

            int sem = c.getSemestre();
            int fila = filas[sem]++;

            String estado = sis.estadoCurso(est.getRut(), c.getNrc());

            Label label = new Label(c.getNrc() + " - " + c.getNombre());
            label.setFont(new Font("Arial", 14));
            label.setPadding(new Insets(8, 14, 8, 14));
            label.setMinWidth(260);
            label.setMaxWidth(260);
            label.setWrapText(true);
            label.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-width: 1;");

            switch (estado.toLowerCase()) {
                case "aprobado":
                    label.setTextFill(Color.FORESTGREEN);
                    label.setStyle(label.getStyle() + "-fx-background-color: #d6ffd6;");
                    break;
                case "reprobado":
                    label.setTextFill(Color.DARKRED);
                    label.setStyle(label.getStyle() + "-fx-background-color: #ffd6d6;");
                    break;
                case "cursando":
                    label.setTextFill(Color.DARKORANGE);
                    label.setStyle(label.getStyle() + "-fx-background-color: #ffe9cc;");
                    break;
                default:
                    label.setTextFill(Color.GRAY);
                    label.setStyle(label.getStyle() + "-fx-background-color: #eeeeee;");
            }

            Tooltip.install(label, new Tooltip(
                "NRC: " + c.getNrc() + "\n" +
                "Nombre: " + c.getNombre() + "\n" +
                "Créditos: " + c.getCreditos() + "\n" +
                "Semestre: " + c.getSemestre() + "\n" +
                "Estado: " + estado
            ));

            grid.add(label, sem - 1, fila);
        }

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setPannable(true);
        scroll.setStyle("-fx-background: #ffffff;");

        root.setCenter(scroll);

        Scene scene = new Scene(root, 1200, 700);
        stage.setScene(scene);
        stage.setTitle("Malla Curricular");
        stage.show();
    }
}
