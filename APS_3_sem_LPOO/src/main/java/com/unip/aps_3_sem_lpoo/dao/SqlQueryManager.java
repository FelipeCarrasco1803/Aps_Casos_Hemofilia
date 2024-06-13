package com.unip.aps_3_sem_lpoo.dao;

import com.unip.aps_3_sem_lpoo.enums.Gravidade;
import com.unip.aps_3_sem_lpoo.enums.TipoHemofilia;
import com.unip.aps_3_sem_lpoo.models.Casos;
import com.unip.aps_3_sem_lpoo.models.Cidade;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlQueryManager {

    private final Connection connection;

    public SqlQueryManager(Connection connection) {
        this.connection = connection;
    }

    public void insertCaso(Casos casos) throws SQLException {
        String sql = "INSERT INTO Casos (Data_Ocorrencia, Numero_Casos, Tipo_Hemofilia, Gravidade_Hemofilia, numero_internados, numero_mortos, numero_alta, Cidade_idCidade) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(casos.getDataOcorrencia()));
        statement.setInt(2, casos.getNumeroCasos());
        statement.setString(3, casos.getTipoHemofilia().toString());
        statement.setString(4, casos.getGravidade().toString());
        statement.setInt(5, casos.getNumeroInternados());
        statement.setInt(6, casos.getNumeroMortos());
        statement.setInt(7, casos.getNumeroAltas());
        statement.setInt(8, casos.getCidade().getIdCidade());

        // Executar a consulta de atualização (insert)
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Inserção realizada com sucesso.");
        } else {
            System.out.println("Falha ao inserir o registro.");
        }
    }

    public List<Casos> selectCasosByCidadeAndRangeDeData(Cidade cidade, LocalDate dataInicio, LocalDate dataFinal) throws SQLException {
        List<Casos> casosList = new ArrayList<>();
        String sql = "SELECT * FROM casos WHERE Cidade_idCidade = ? AND Data_Ocorrencia >= ? AND Data_Ocorrencia <= ? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, cidade.getIdCidade());
        statement.setDate(2, Date.valueOf(dataInicio));
        statement.setDate(3, Date.valueOf(dataFinal));
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("idCasos");
            LocalDate dataOcorrencia = resultSet.getDate("Data_Ocorrencia").toLocalDate();
            int numeroCasos = resultSet.getInt("Numero_Casos");
            TipoHemofilia tipoHemofilia = TipoHemofilia.valueOf(resultSet.getString("Tipo_Hemofilia"));
            Gravidade gravidade = Gravidade.valueOf(resultSet.getString("Gravidade_Hemofilia"));
            int numeroInternados = resultSet.getInt("numero_internados");
            int numeroMortos = resultSet.getInt("numero_mortos");
            int numeroAltas = resultSet.getInt("numero_alta");


            Casos casos = new Casos(dataOcorrencia, numeroCasos, tipoHemofilia, gravidade, numeroInternados, numeroMortos, numeroAltas, cidade);
            casos.setId(id);

            casosList.add(casos);
        }

        return casosList;
    }


    public Cidade selectCidadeByNome(String nome) throws SQLException {
        String sql = "SELECT * FROM cidade WHERE Nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Avança para a primeira linha do resultado
                return new Cidade(resultSet.getInt("idCidade"), resultSet.getString("Nome"), resultSet.getInt("Populacao"));
            } else {
                // Não há resultados para o nome fornecido
                return null;
            }
        }
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }

    public List<List<Casos>> selectCasosDeTodasAsCidades(LocalDate dataInicio, LocalDate dataFinal) throws SQLException {
        String sql = "SELECT * FROM casos WHERE Data_Ocorrencia >= ? AND Data_Ocorrencia <= ? ";

        Map<Integer, List<Casos>> casosPorCidade = new HashMap<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(dataInicio));
        statement.setDate(2, Date.valueOf(dataFinal));
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("idCasos");
            LocalDate dataOcorrencia = resultSet.getDate("Data_Ocorrencia").toLocalDate();
            int numeroCasos = resultSet.getInt("Numero_Casos");
            TipoHemofilia tipoHemofilia = TipoHemofilia.valueOf(resultSet.getString("Tipo_Hemofilia"));
            Gravidade gravidade = Gravidade.valueOf(resultSet.getString("Gravidade_Hemofilia"));
            int numeroInternados = resultSet.getInt("numero_internados");
            int numeroMortos = resultSet.getInt("numero_mortos");
            int numeroAltas = resultSet.getInt("numero_alta");
            int cidadeId = resultSet.getInt("Cidade_idCidade");

            Cidade cidade = selectCidadeById(cidadeId);

            Casos casos = new Casos(dataOcorrencia, numeroCasos, tipoHemofilia, gravidade, numeroInternados, numeroMortos, numeroAltas, cidade);
            casos.setId(id);

            // Verifica se já existe uma lista de casos para essa cidade no mapa
            if (!casosPorCidade.containsKey(cidadeId)) {
                // Se não existir, cria uma nova lista de casos para essa cidade
                casosPorCidade.put(cidadeId, new ArrayList<>());
            }
            // Adiciona o caso à lista de casos correspondente à cidade
            casosPorCidade.get(cidadeId).add(casos);
        }

        // Adiciona as listas de casos de cada cidade à lista casosDeTodasAsCidades


        return new ArrayList<>(casosPorCidade.values());
    }

    public Cidade selectCidadeById(int cidadeId) throws SQLException {
        String sql = "SELECT * FROM cidade WHERE idCidade = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cidadeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Avança para a primeira linha do resultado
                return new Cidade(resultSet.getInt("idCidade"), resultSet.getString("Nome"), resultSet.getInt("Populacao"));
            } else {
                // Não há resultados para o nome fornecido
                return null;
            }
        }

    }

    public void atualizarCasos(Casos caso) throws SQLException {



        String sql = "UPDATE Casos SET Data_Ocorrencia = ?, Numero_Casos = ?, Tipo_Hemofilia = ?, " +
                "Gravidade_Hemofilia = ?, numero_internados = ?, numero_mortos = ?, numero_alta = ?, Cidade_idCidade = ? " +
                "WHERE idCasos = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(caso.getDataOcorrencia()));
        statement.setInt(2, caso.getNumeroCasos());
        statement.setString(3, caso.getTipoHemofilia().toString());
        statement.setString(4, caso.getGravidade().toString());
        statement.setInt(5, caso.getNumeroInternados());
        statement.setInt(6, caso.getNumeroMortos());
        statement.setInt(7, caso.getNumeroAltas());
        statement.setInt(8, caso.getCidade().getIdCidade());
        statement.setInt(9, caso.getId());

        int rowsUpadated = statement.executeUpdate();

        if (rowsUpadated > 0) {
            System.out.println("Caso atualizado com sucesso.");
        } else {
            System.out.println("Nenhum caso foi atualizado. Verifique o ID do caso.");
        }
    }

    public void deletarCasoPeloId(int idCasos) throws SQLException {

        String sql = "DELETE FROM Casos WHERE idCasos = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idCasos);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Caso deletado com sucesso.");
        } else {
            System.out.println("Nenhum caso foi deletado. Verifique o ID do caso.");
        }
    }
}
