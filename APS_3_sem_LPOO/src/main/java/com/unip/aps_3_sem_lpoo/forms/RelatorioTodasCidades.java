package com.unip.aps_3_sem_lpoo.forms;

import com.unip.aps_3_sem_lpoo.Main;
import com.unip.aps_3_sem_lpoo.config.DbConnection;
import com.unip.aps_3_sem_lpoo.dao.SqlQueryManager;
import com.unip.aps_3_sem_lpoo.models.Casos;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RelatorioTodasCidades extends Application {
    private Stage stage;
    private final DbConnection connection = new DbConnection();
    private final SqlQueryManager manager = new SqlQueryManager(connection.getConnection());

    public RelatorioTodasCidades() throws SQLException {
    }

    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;
        double width = 200;
        double height = 40;

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
            LocalDate dataInicio = datePickerInicio.getValue();
            LocalDate dataFinal = datePickerFinal.getValue();

            try {
                List<List<Casos>> casos = retornaListaCasos(dataInicio, dataFinal);
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

        grid.add(labelInicio, 0, 2);
        grid.add(datePickerInicio, 1, 2);

        grid.add(labelFinal, 0, 3);
        grid.add(datePickerFinal, 1, 3);

        grid.add(btnGerarRelatorio, 1, 4);
        grid.add(btnVoltar, 0, 4);


        grid.add(mensagemLabel,0, 5 );


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

    private List<List<Casos>> retornaListaCasos(LocalDate dataInicio, LocalDate dataFinal) throws SQLException {

        return manager.selectCasosDeTodasAsCidades(dataInicio, dataFinal);
    }

    private void criaSceneDeComparacaoDasCidades(List<List<Casos>> casosDasCidades){


        List<Casos> casosCidade1 = casosDasCidades.get(0);
        List<Casos> casosCidade2 = casosDasCidades.get(1);
        List<Casos> casosCidade3 = casosDasCidades.get(2);
        List<Casos> casosCidade4 = casosDasCidades.get(3);
        List<Casos> casosCidade5 = casosDasCidades.get(4);

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

        int totalCasosCidade3 = 0, totalMortosCidade3 = 0, totalAltasCidade3 = 0;


        for (Casos caso : casosCidade3){
            totalCasosCidade3 += caso.getNumeroCasos();
            totalMortosCidade3 += caso.getNumeroMortos();
            totalAltasCidade3 += caso.getNumeroAltas();
        }

        int totalCasosCidade4 = 0, totalMortosCidade4 = 0, totalAltasCidade4 = 0;


        for (Casos caso : casosCidade4){
            totalCasosCidade4 += caso.getNumeroCasos();
            totalMortosCidade4 += caso.getNumeroMortos();
            totalAltasCidade4 += caso.getNumeroAltas();
        }

        int totalCasosCidade5 = 0, totalMortosCidade5 = 0, totalAltasCidade5 = 0;


        for (Casos caso : casosCidade5){
            totalCasosCidade5 += caso.getNumeroCasos();
            totalMortosCidade5 += caso.getNumeroMortos();
            totalAltasCidade5 += caso.getNumeroAltas();
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

        TableColumn<DadosCidade, Integer> cidadeColumn3 = new TableColumn<>(casosCidade3.get(0).getCidade().getNome());
        cidadeColumn3.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getValorCidade3()).asObject());
        cidadeColumn3.setStyle("-fx-alignment: CENTER;");

        TableColumn<DadosCidade, Integer> cidadeColumn4 = new TableColumn<>(casosCidade4.get(0).getCidade().getNome());
        cidadeColumn4.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getValorCidade4()).asObject());
        cidadeColumn4.setStyle("-fx-alignment: CENTER;");

        TableColumn<DadosCidade, Integer> cidadeColumn5 = new TableColumn<>(casosCidade5.get(0).getCidade().getNome());
        cidadeColumn5.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getValorCidade5()).asObject());
        cidadeColumn5.setStyle("-fx-alignment: CENTER;");

        tableView.getColumns().addAll(totalColumn, cidadeColumn1, cidadeColumn2, cidadeColumn3,cidadeColumn4, cidadeColumn5);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Adicionando linhas
        tableView.getItems().addAll(
                new DadosCidade("Total de casos", totalCasosCidade1, totalCasosCidade2,totalCasosCidade3,totalCasosCidade4,totalCasosCidade5),
                new DadosCidade("Total de mortos", totalMortosCidade1, totalMortosCidade2, totalMortosCidade3, totalMortosCidade4, totalMortosCidade5),
                new DadosCidade("Total de altas", totalAltasCidade1, totalAltasCidade2, totalAltasCidade3, totalAltasCidade4, totalAltasCidade5)
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

        private final int valorCidade3;

        private final int valorCidade4;

        private final int valorCidade5;


        public DadosCidade(String tipo, int valorCidade1, int valorCidade2, int valorCidade3, int valorCidade4, int valorCidade5) {
            this.tipo = tipo;
            this.valorCidade1 = valorCidade1;
            this.valorCidade2 = valorCidade2;
            this.valorCidade3 = valorCidade3;
            this.valorCidade4 = valorCidade4;
            this.valorCidade5 = valorCidade5;
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

        public int getValorCidade3() {
            return valorCidade3;
        }

        public int getValorCidade4() {
            return valorCidade4;
        }

        public int getValorCidade5() {
            return valorCidade5;
        }
    }
}