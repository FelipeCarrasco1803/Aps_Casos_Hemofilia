package com.unip.aps_3_sem_lpoo.forms;

import com.unip.aps_3_sem_lpoo.Main;
import com.unip.aps_3_sem_lpoo.config.DbConnection;
import com.unip.aps_3_sem_lpoo.dao.SqlQueryManager;
import com.unip.aps_3_sem_lpoo.enums.Gravidade;
import com.unip.aps_3_sem_lpoo.enums.TipoHemofilia;
import com.unip.aps_3_sem_lpoo.models.Casos;
import com.unip.aps_3_sem_lpoo.models.Cidade;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.SQLException;
import java.time.LocalDate;

public class NovoCadastroForm extends Application {

    private Stage stage;
    private final DbConnection connection = new DbConnection();
    private final SqlQueryManager manager = new SqlQueryManager(connection.getConnection());

    public NovoCadastroForm() throws SQLException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));
        int row = 1;

        // Criar a cena e associá-la ao GridPane
        Scene scene = new Scene(grid, 600, 500);
        primaryStage.setScene(scene);

        double width = 200;
        double height = 25;

        // Definir o título da cena
        Text scenetitle = new Text("Novo Registro");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        // Adicionar os componentes ao GridPane
        Label cidadeLabel = new Label("Cidade:");
        grid.add(cidadeLabel, 0, row);

        ComboBox<String> cidadeComboBox = new ComboBox<>();
        cidadeComboBox.getItems().addAll("São Paulo", "São Bernardo do Campo", "São Caetano", "Santo André","Mauá");
        cidadeComboBox.setValue("São Paulo");
        cidadeComboBox.setMinSize(width, height);
        cidadeComboBox.setPrefSize(width,height);
        grid.add(cidadeComboBox, 1, row);

        Label numeroCasosLabel = new Label("Número de casos:");
        grid.add(numeroCasosLabel, 0, row +1);

        TextField numeroCasosField = new TextField();
        grid.add(numeroCasosField,1 ,row + 1);

        Label tipoDeHemofiliaLabel = new Label("Tipo de Hemofilia:");
        grid.add(tipoDeHemofiliaLabel, 0, row + 2);

        RadioButton radioButton1 = new RadioButton("A");
        RadioButton radioButton2 = new RadioButton("B");
        RadioButton radioButton3 = new RadioButton("C");

        // Criar um ToggleGroup para agrupar os RadioButtons
        ToggleGroup tipoDeHemofiliatoggleGroup = new ToggleGroup();
        radioButton1.setToggleGroup(tipoDeHemofiliatoggleGroup);
        radioButton2.setToggleGroup(tipoDeHemofiliatoggleGroup);
        radioButton3.setToggleGroup(tipoDeHemofiliatoggleGroup);

        // Definir o RadioButton1 como selecionado por padrão
        radioButton1.setSelected(true);
        grid.add(radioButton1, 1, row + 2,1,1);
        grid.add(radioButton2, 1, row + 2,1,1);
        grid.add(radioButton3, 1, row + 2,1,1);

        GridPane.setMargin(radioButton1, new Insets(0, 0, 0, 20));
        GridPane.setMargin(radioButton2, new Insets(0, 60, 0 ,60));
        GridPane.setMargin(radioButton3, new Insets(0, 0, 0 ,100));

        Label gravidadeHemofiliaLabel = new Label("Gravidade:");
        grid.add(gravidadeHemofiliaLabel, 0, row + 3);

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Leve", "Moderado", "Grave");
        choiceBox.setValue("Leve");
        choiceBox.setMinSize(width,height);
        choiceBox.setPrefSize(width,height);
        grid.add(choiceBox, 1, row + 3);

        Label numeroInternadosLabel = new Label("Número de Internados:");
        grid.add(numeroInternadosLabel, 0, row +4);

        TextField numeroInteradosField = new TextField();
        grid.add(numeroInteradosField,1 ,row + 4);

        Label numeroMortosLabel = new Label("Número de Mortos:");
        grid.add(numeroMortosLabel, 0, row +5);

        TextField numeroMortosField = new TextField();
        grid.add(numeroMortosField,1 ,row + 5);

        Label numeroAltaLabel = new Label("Número de Altas:");
        grid.add(numeroAltaLabel, 0, row +6);

        TextField numeroAltaField = new TextField();
        grid.add(numeroAltaField,1 ,row + 6);


        Label DataOcorrenciaLabel = new Label("Data da Ocorrência:");
        grid.add(DataOcorrenciaLabel, 0, row + 7);

// Criar o DatePicker
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefSize(width,height);
        datePicker.setMinSize(width,height);
        datePicker.setValue(LocalDate.now());
        grid.add(datePicker, 1, row + 7);



        // Criar o botão de cadastro
        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.setMinSize(width, height + 15);
        btnCadastrar.setPrefSize(width, height + 15);
        grid.add(btnCadastrar, 1, row + 8, 1, 1);

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setMinSize(width, height + 15);
        btnVoltar.setPrefSize(width, height + 15);
        grid.add(btnVoltar, 0, row + 8, 1, 1);

        Label mensagemLabel = new Label("");
        mensagemLabel.setTextFill(Color.GREEN); // Define a cor do texto como verde
        grid.add(mensagemLabel, 0, row + 9, 2, 1); // Adiciona o Label abaixo dos botões

        btnVoltar.setOnAction(e -> retornarTelaAnterior());

        btnVoltar.setOnAction(e -> retornarTelaAnterior());

        // Exibir a cena
        primaryStage.setTitle("Casos de Hemofilia");
        primaryStage.show();

        btnCadastrar.setOnAction(e -> {
            // Obtendo os valores dos campos de entrada
            String nomeCidade = cidadeComboBox.getValue();
            String gravidadeSelecionada = choiceBox.getValue();
            int numeroCasos = Integer.parseInt(numeroCasosField.getText());
            int numeroInternados = Integer.parseInt(numeroInteradosField.getText());
            int numeroMortos = Integer.parseInt(numeroMortosField.getText());
            int numeroAltas = Integer.parseInt(numeroAltaField.getText());
            LocalDate dataOcorrencia = datePicker.getValue();

            TipoHemofilia tipoHemofilia = TipoHemofilia.A;

            if (radioButton2.isSelected()) {
                tipoHemofilia = TipoHemofilia.B;
            } else if (radioButton3.isSelected()) {
                tipoHemofilia = TipoHemofilia.C;}

            Gravidade gravidade = Gravidade.LEVE;

            if (gravidadeSelecionada.equals("Moderado")) {
            gravidade = Gravidade.MODERADO;}
            else if (gravidadeSelecionada.equals("Grave")) {
            gravidade = Gravidade.GRAVE;}

            Cidade cidade;

            try {
                cidade = manager.selectCidadeByNome(nomeCidade);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            Casos novoCaso = new Casos(dataOcorrencia, numeroCasos, tipoHemofilia, gravidade, numeroInternados, numeroMortos, numeroAltas, cidade);

            try {

                manager.insertCaso(novoCaso);
                mensagemLabel.setText("*Novo caso registrado com sucesso*");

                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(event -> mensagemLabel.setText("")); // Atualizar o texto para vazio após 3 segundos
                pause.play();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

    }

    private void retornarTelaAnterior() {
        // Fechar a janela atual
        stage.close();
        // Abrir a tela anterior, como exemplo, poderia ser a tela principal
        Main main = new Main();
        Stage stage = new Stage();
        try {
            manager.closeConnection();
            main.start(stage);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
