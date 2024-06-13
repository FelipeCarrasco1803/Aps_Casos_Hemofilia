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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioUmaCidade extends Application {

    private Stage stage;
    private final DbConnection connection = new DbConnection();
    private final SqlQueryManager manager = new SqlQueryManager(connection.getConnection());

    public RelatorioUmaCidade() throws SQLException {
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        double width = 200;
        double height = 40;

        ComboBox<String> cidadeComboBox = new ComboBox<>();
        cidadeComboBox.setMinSize(width,30);
        cidadeComboBox.setPrefSize(width,30);
        cidadeComboBox.getItems().addAll("São Paulo", "São Bernardo do Campo", "São Caetano", "Santo André", "Mauá");
        cidadeComboBox.setValue("São Paulo");

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

        Label mensagemLabel = new Label("");
        mensagemLabel.setTextFill(Color.RED);


        btnGerarRelatorio.setMinSize(width, height);
        btnGerarRelatorio.setPrefSize(width, height);
        btnVoltar.setMinSize(width, height);
        btnVoltar.setPrefSize(width, height);

        // Adicionando um evento ao botão "Gerar Relatório"
        btnGerarRelatorio.setOnAction(e -> {
            String nomeCidade = cidadeComboBox.getValue();
            LocalDate dataInicio = datePickerInicio.getValue();
            LocalDate dataFinal = datePickerFinal.getValue();
            try {
                List<Casos> casos = retornaListaCasos(nomeCidade, dataInicio, dataFinal);
                Cidade cidade = manager.selectCidadeByNome(nomeCidade);
                listaCasosScene(casos);
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

        grid.add(new Label("Cidade:"), 0, 0);
        grid.add(cidadeComboBox, 1, 0);

        grid.add(labelInicio, 0, 1);
        grid.add(datePickerInicio, 1, 1);

        grid.add(labelFinal, 0, 2);
        grid.add(datePickerFinal, 1, 2);

        grid.add(btnGerarRelatorio, 1, 3);
        grid.add(btnVoltar, 0, 3);
        grid.add(mensagemLabel, 0, 4, 1, 3);


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

    private List<Casos> retornaListaCasos(String nomeCidade, LocalDate dataInicio, LocalDate dataFinal) throws SQLException {

        Cidade cidade = manager.selectCidadeByNome(nomeCidade);
        List<Casos> casos = manager.selectCasosByCidadeAndRangeDeData(cidade, dataInicio, dataFinal);
        return casos;
    }

    private void listaCasosScene(List<Casos> casos){
        TableView<Casos> tableView = new TableView<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Definição das colunas de dados
        TableColumn<Casos, String> cidadeColumn = new TableColumn<>("Cidade");
        cidadeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCidade().getNome()));
        cidadeColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Casos, Integer> populacaoColumn = new TableColumn<>("População");
        populacaoColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCidade().getPopulacao()).asObject());
        populacaoColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Casos, String> dataColumn = new TableColumn<>("Data de Ocorrência");
        dataColumn.setCellValueFactory(cellData -> {
            LocalDate dataOcorrencia = cellData.getValue().getDataOcorrencia();
            String dataFormatada = dataOcorrencia.format(formatter);
            return new SimpleStringProperty(dataFormatada);
        });
        dataColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Casos, Integer> numeroCasosColumn = new TableColumn<>("Número de Casos");
        numeroCasosColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumeroCasos()).asObject());
        numeroCasosColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Casos, Integer> numeroMortosColumn = new TableColumn<>("Número de Mortos");
        numeroMortosColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumeroMortos()).asObject());
        numeroMortosColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Casos, Integer> numeroInternadosColumn = new TableColumn<>("Número de Internados");
        numeroInternadosColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumeroInternados()).asObject());
        numeroInternadosColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Casos, Integer> numeroAltasColumn = new TableColumn<>("Número de Altas");
        numeroAltasColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumeroAltas()).asObject());
        numeroAltasColumn.setStyle("-fx-alignment: CENTER;");


        // Coluna de botões
        TableColumn<Casos, Void> acoesColumn = new TableColumn<>("Ações");
        acoesColumn.setCellFactory(param -> new TableCell<>() {
            private final Button alterarButton = new Button("Alterar");
            private final Button deletarButton = new Button("Deletar");

            {
                deletarButton.setOnAction(event -> {
                    Casos caso = getTableView().getItems().get(getIndex());
                    casos.remove(caso); // Remova o caso da lista
                    tableView.getItems().remove(caso); // Remova o caso da tabela
                    tableView.refresh();
                    try {
                        manager.deletarCasoPeloId(caso.getIdCasos());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                alterarButton.setOnAction(event -> {
                    Casos caso = getTableView().getItems().get(getIndex());
                    try {
                        AlterarCadastroForm alterarCadastroForm = new AlterarCadastroForm(caso);
                        alterarCadastroForm.start(stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }


                    tableView.refresh();
                    // Lógica para alterar o caso
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5,alterarButton , new Region(), deletarButton);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.setPadding(new Insets(5));
                    setGraphic(buttons);
                }
            }
        });

        tableView.getColumns().addAll(cidadeColumn, populacaoColumn, dataColumn,
                numeroCasosColumn, numeroMortosColumn, numeroInternadosColumn,
                numeroAltasColumn, acoesColumn);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getItems().addAll(casos);

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setPrefSize(200, 40);
        btnVoltar.setPadding(new Insets(10));

        long porcentagem = retornaPorcentagemCasosDaPopulacao(casos);

// Campo para exibir a porcentagem
        Label porcentagemLabel = new Label("Numero de casos por 100 mil habitantes:");
        TextField porcentagemField = new TextField();
        porcentagemField.setPrefSize(100, 40);
        porcentagemField.setText(String.valueOf(porcentagem));
        porcentagemField.setEditable(false); // Bloqueia a edição do TextField

// Layout para agrupar o botão "Voltar" e o campo da porcentagem
        HBox bottomBar = new HBox(10, porcentagemLabel, porcentagemField, btnVoltar);
        bottomBar.setAlignment(Pos.CENTER_LEFT); // Para alinhar à esquerda
        bottomBar.setPadding(new Insets(10));

        VBox root = new VBox(tableView, bottomBar);
        root.getChildren().add(btnVoltar);
        root.setPadding(new Insets(45));

        btnVoltar.setOnAction(e -> retornarTelaAnterior());

        Scene scene = new Scene(root, 1200, 800);

        // Configurar o palco e exibir a cena
        stage.setTitle("Casos de Hemofilia");
        stage.setScene(scene);
        stage.show();
    }

    private long retornaPorcentagemCasosDaPopulacao(List<Casos> casos){

        int totalCasos = 0;
        for(Casos caso : casos){
            totalCasos += caso.getNumeroCasos();
        };
        int populacao = casos.get(0).getCidade().getPopulacao();
        System.out.println(totalCasos);
        System.out.println(populacao);
        double casosPorMil =  ((double) totalCasos / populacao) * 100000;
        long casosPorMilArredondado = Math.round(casosPorMil);
        System.out.println(casosPorMil);
        return casosPorMilArredondado;
    }

}
