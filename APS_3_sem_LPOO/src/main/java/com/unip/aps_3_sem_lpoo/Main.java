package com.unip.aps_3_sem_lpoo;
import com.unip.aps_3_sem_lpoo.forms.NovoCadastroForm;
import com.unip.aps_3_sem_lpoo.forms.RelatoriosForm;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private StackPane root;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        // Criar botões para as opções
        this.stage = primaryStage;
        Button btnRegistrarCasos = new Button("Registrar Novos Casos");
        Button btnRelatorios = new Button("Relatórios");

        // Definir ações dos botões
        btnRegistrarCasos.setOnAction(e -> {
            // Lógica para abrir a tela de registro de novos casos
            try {
                mostrarNovoCadastroForm();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        btnRelatorios.setOnAction(e -> {
            // Lógica para abrir a tela de relatórios
            try {
                mostrarRelatoriosForm();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        double buttonWidth = 200;
        double buttonHeight = 40;
        btnRegistrarCasos.setMinSize(buttonWidth, buttonHeight);
        btnRelatorios.setMinSize(buttonWidth, buttonHeight);
        btnRegistrarCasos.setPrefWidth(buttonWidth);
        btnRelatorios.setPrefWidth(buttonWidth);

        // Criar layout StackPane e adicionar botões
        root = new StackPane();
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(btnRegistrarCasos,btnRelatorios);
        vbox.setPadding(new Insets(20)); // Margem de 20 pixels em todos os lados
        vbox.setAlignment(Pos.CENTER); // Centralizar o VBox na cena
        root.getChildren().add(vbox);



        // Criar a cena principal
        Scene scene = new Scene(root, 600, 500);

        // Definir a cena principal do palco (janela)
        primaryStage.setScene(scene);
        primaryStage.setTitle("Casos de Hemofilia");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void mostrarNovoCadastroForm() throws Exception {
        root.getChildren().clear();
        NovoCadastroForm novoCadastroForm = new NovoCadastroForm();
        novoCadastroForm.start(this.stage);
    }

    private void mostrarRelatoriosForm() throws Exception {
        root.getChildren().clear();
        RelatoriosForm relatoriosForm = new RelatoriosForm();
        relatoriosForm.start(this.stage);
    }
}

