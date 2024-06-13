package com.unip.aps_3_sem_lpoo.forms;

import com.unip.aps_3_sem_lpoo.Main;
import com.unip.aps_3_sem_lpoo.config.DbConnection;
import com.unip.aps_3_sem_lpoo.dao.SqlQueryManager;
import com.unip.aps_3_sem_lpoo.models.Casos;
import com.unip.aps_3_sem_lpoo.models.Cidade;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDuasCIdades extends Application {
    private Stage stage;
    private final DbConnection connection = new DbConnection();
    private final SqlQueryManager manager = new SqlQueryManager(connection.getConnection());

    public RelatorioDuasCIdades() throws SQLException {
    }

    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;
        double width = 200;
        double height = 40;

        ComboBox<String> cidade1ComboBox = new ComboBox<>();
        cidade1ComboBox.setMinSize(width,30);
        cidade1ComboBox.setPrefSize(width,30);
        cidade1ComboBox.getItems().addAll("São Paulo", "São Bernardo do Campo", "São Caetano", "Santo André", "Mauá");
        cidade1ComboBox.setValue("São Paulo");

        ComboBox<String> cidade2ComboBox = new ComboBox<>();
        cidade2ComboBox.setMinSize(width,30);
        cidade2ComboBox.setPrefSize(width,30);
        cidade2ComboBox.getItems().addAll("São Paulo", "São Bernardo do Campo", "São Caetano", "Santo André", "Mauá");
        cidade2ComboBox.setValue("São Paulo");

        DatePicker datePickerInicio = new DatePicker();
        datePickerInicio.setMinSize(width, 30);
        datePickerInicio.setPrefSize(width, 30);
        datePickerInicio.setValue(LocalDate.now());
        Label labelInicio = new Label("Início");

        DatePicker datePickerFinal = new DatePicker();
        datePickerFinal.setMinSize(width, 30);
        datePickerFinal.setPrefSize(width, 30);
        datePickerFinal.setValue(LocalDate.now());
        Label labelFinal = new Label("Final");

        Button btnGerarRelatorio = new Button("Gerar Relatório");
        Button btnVoltar = new Button("Voltar");

        btnGerarRelatorio.setMinSize(width, height);
        btnGerarRelatorio.setPrefSize(width, height);
        btnVoltar.setMinSize(width, height);
        btnVoltar.setPrefSize(width, height);

        Label mensagemLabel = new Label("");
        mensagemLabel.setTextFill(Color.RED);

        // Adicionando um evento ao botão "Gerar Relatório"
        btnGerarRelatorio.setOnAction(e -> {
            String nomeCidade1 = cidade1ComboBox.getValue();
            String nomeCidade2 = cidade2ComboBox.getValue();
            LocalDate dataInicio = datePickerInicio.getValue();
            LocalDate dataFinal = datePickerFinal.getValue();

            try {
                List<List<Casos>> casos = retornaListaCasos(nomeCidade1, nomeCidade2, dataInicio, dataFinal);
                criaSceneDeComparacaoDasCidades(casos);
            } catch (Exception ex) {
                mensagemLabel.setText("*Não há casos para estas datas*");
                throw new RuntimeException(ex);

            }

        });

        btnVoltar.setOnAction(e -> retornarTelaAnterior());

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        grid.add(new Label("Primeira Cidade:"), 0, 0);
        grid.add(cidade1ComboBox, 1, 0);

        grid.add(new Label("Segunda Cidade:"), 0, 1);
        grid.add(cidade2ComboBox, 1, 1);

        grid.add(labelInicio, 0, 2);
        grid.add(datePickerInicio, 1, 2);

        grid.add(labelFinal, 0, 3);
        grid.add(datePickerFinal, 1, 3);

        grid.add(btnGerarRelatorio, 1, 4);
        grid.add(btnVoltar, 0, 4);

        grid.add(mensagemLabel, 0, 5);


        Scene scene = new Scene(grid, 600, 500);

        stage.setTitle("Gerador de Relatórios");
        stage.setScene(scene);
        stage.show();
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

    private List<List<Casos>> retornaListaCasos(String nomeCidade1, String nomeCidade2, LocalDate dataInicio, LocalDate dataFinal) throws SQLException {

        Cidade cidade1 = manager.selectCidadeByNome(nomeCidade1);
        List<Casos> casos1 = manager.selectCasosByCidadeAndRangeDeData(cidade1, dataInicio, dataFinal);

        Cidade cidade2 = manager.selectCidadeByNome(nomeCidade2);
        List<Casos> casos2 = manager.selectCasosByCidadeAndRangeDeData(cidade2, dataInicio, dataFinal);

        List<List<Casos>> casosDasCidades  = new ArrayList<>();
        casosDasCidades.add(casos1);
        casosDasCidades.add(casos2);

        return casosDasCidades;
    }

    private void criaSceneDeComparacaoDasCidades(List<List<Casos>> casosDasCidades){


        List<Casos> casosCidade1 = casosDasCidades.get(0);
        List<Casos> casosCidade2 = casosDasCidades.get(1);

        int totalCasosCidade1 = 0, totalMortosCidade1 = 0, totalAltasCidade1 = 0;


        for (Casos caso : casosCidade1){
            totalCasosCidade1 += caso.getNumeroCasos();
            totalMortosCidade1 += caso.getNumeroMortos();
            totalAltasCidade1 += caso.getNumeroAltas();
        }


        int totalCasosCidade2 = 0, totalMortosCidade2 = 0, totalAltasCidade2 = 0;


        for (Casos caso : casosCidade2){
            totalCasosCidade2 += caso.getNumeroCasos();
            totalMortosCidade2 += caso.getNumeroMortos();
            totalAltasCidade2 += caso.getNumeroAltas();
        }

        TableView<DadosCidade> tableView = new TableView<>();

        // Criando colunas
        TableColumn<DadosCidade, String> totalColumn = new TableColumn<>("Totais");
        totalColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipo()));
        totalColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<DadosCidade, Integer> cidadeColumn1 = new TableColumn<>(casosCidade1.get(0).getCidade().getNome());
        cidadeColumn1.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getValorCidade1()).asObject());
        cidadeColumn1.setStyle("-fx-alignment: CENTER;");

        TableColumn<DadosCidade, Integer> cidadeColumn2 = new TableColumn<>(casosCidade2.get(0).getCidade().getNome());
        cidadeColumn2.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getValorCidade2()).asObject());
        cidadeColumn2.setStyle("-fx-alignment: CENTER;");

        tableView.getColumns().addAll(totalColumn, cidadeColumn1, cidadeColumn2);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Adicionando linhas
        tableView.getItems().addAll(
                new DadosCidade("Total de casos", totalCasosCidade1, totalCasosCidade2),
                new DadosCidade("Total de mortos", totalMortosCidade1, totalMortosCidade2),
                new DadosCidade("Total de altas", totalAltasCidade1, totalAltasCidade2)
        );

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setMinSize(200,40);

        HBox bottomBar = new HBox(10, btnVoltar);
        bottomBar.setAlignment(Pos.CENTER_LEFT); // Para alinhar à esquerda
        bottomBar.setPadding(new Insets(10));

        VBox root = new VBox(tableView, bottomBar);
        root.getChildren().add(btnVoltar);
        root.setPadding(new Insets(45));

        btnVoltar.setOnAction(e -> retornarTelaAnterior());

        // Criando cena
        Scene scene = new Scene(root, 800, 600);

        // Configurando e exibindo palco
        stage.setTitle("Comparação de Cidades");
        stage.setScene(scene);
        stage.show();
    }

    // Classe para representar os dados de uma cidade
    private static class DadosCidade {
        private final String tipo;
        private final int valorCidade1;
        private final int valorCidade2;

        public DadosCidade(String tipo, int valorCidade1, int valorCidade2) {
            this.tipo = tipo;
            this.valorCidade1 = valorCidade1;
            this.valorCidade2 = valorCidade2;
        }

        public String getTipo() {
            return tipo;
        }

        public int getValorCidade1() {
            return valorCidade1;
        }

        public int getValorCidade2() {
            return valorCidade2;
        }
    }
}