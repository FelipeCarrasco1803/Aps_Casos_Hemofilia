package com.unip.aps_3_sem_lpoo.forms;

import com.unip.aps_3_sem_lpoo.Main;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RelatoriosForm extends Application  {

    private Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        Button btnRelatorioUnico = new Button("Relatório de uma cidade");
        Button btnRelatorioComparativo = new Button("Comparativo entre duas cidades");
        Button btnRelatorioAll = new Button("Comparativo entre todas cidades");
        Button btnVoltar = new Button("Voltar");


        StackPane root = new StackPane();
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(btnRelatorioUnico,btnRelatorioComparativo,btnRelatorioAll,btnVoltar);
        vbox.setPadding(new Insets(20)); // Margem de 20 pixels em todos os lados
        vbox.setAlignment(Pos.CENTER); // Centralizar o VBox na cena
        root.getChildren().add(vbox);

        double buttonWidth = 200;
        double buttonHeight = 40;
        btnRelatorioUnico.setMinSize(buttonWidth, buttonHeight);
        btnRelatorioAll.setMinSize(buttonWidth, buttonHeight);
        btnVoltar.setMinSize(buttonWidth,buttonHeight);
        btnRelatorioUnico.setPrefWidth(buttonWidth);
        btnVoltar.setPrefWidth(buttonWidth);
        btnRelatorioAll.setPrefWidth(buttonWidth);
        btnRelatorioComparativo.setMinSize(buttonWidth,buttonHeight);
        btnRelatorioComparativo.setPrefSize(buttonWidth,buttonHeight);
        btnVoltar.setPrefSize(buttonWidth,buttonHeight);

        btnVoltar.setOnAction(e -> retornarTelaAnterior());

        btnRelatorioUnico.setOnAction(e -> {
            try {
                RelatorioUmaCidade relatorioUmaCidade = new RelatorioUmaCidade();
                relatorioUmaCidade.start(this.stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        btnRelatorioComparativo.setOnAction(e -> {
            try {
                RelatorioDuasCIdades relatorioDuasCIdades = new RelatorioDuasCIdades();
                relatorioDuasCIdades.start(this.stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });

        btnRelatorioAll.setOnAction(e -> {
            try {
                RelatorioTodasCidades relatorioTodasCidades = new RelatorioTodasCidades();
                relatorioTodasCidades.start(this.stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });

        // Criar a cena principal
        Scene scene = new Scene(root, 600, 500);

        // Definir a cena principal do palco (janela)
        primaryStage.setScene(scene);
        primaryStage.setTitle("Casos de Hemofilia");
        primaryStage.show();
    }

    private void retornarTelaAnterior() {
        // Fechar a janela atual
        stage.close();
        // Abrir a tela anterior, como exemplo, poderia ser a tela principal
        Main main = new Main();
        Stage stage = new Stage();
        try {
            main.start(stage);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
