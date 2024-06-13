package com.unip.aps_3_sem_lpoo.models;

import com.unip.aps_3_sem_lpoo.enums.Gravidade;
import com.unip.aps_3_sem_lpoo.enums.TipoHemofilia;

import java.time.LocalDate;

public class Casos {

    private int idCasos, numeroCasos, numeroInternados, numeroMortos,numeroAltas;

    private LocalDate dataOcorrencia;
    private TipoHemofilia tipoHemofilia;
    private Gravidade gravidade;

    private Cidade cidade;

    public Casos( LocalDate dataOcorrencia, int numeroCasos, TipoHemofilia tipoHemofilia, Gravidade gravidade, int numeroInternados, int numeroMortos, int numeroAltas, Cidade cidade) {

        this.dataOcorrencia = dataOcorrencia;
        this.numeroCasos = numeroCasos;
        this.tipoHemofilia = tipoHemofilia;
        this.gravidade = gravidade;
        this.numeroInternados = numeroInternados;
        this.numeroMortos = numeroMortos;
        this.numeroAltas = numeroAltas;
        this.cidade = cidade;

    }


    public int getIdCasos() {
        return idCasos;
    }

    public int getNumeroCasos() {
        return numeroCasos;
    }

    public int getNumeroInternados() {
        return numeroInternados;
    }

    public int getNumeroMortos() {
        return numeroMortos;
    }

    public int getNumeroAltas() {
        return numeroAltas;
    }

    public Cidade getCidade() {
        return this.cidade;
    }

    public TipoHemofilia getTipoHemofilia() {
        return tipoHemofilia;
    }

    public Gravidade getGravidade() {
        return gravidade;
    }

    public LocalDate getDataOcorrencia() {return dataOcorrencia;}

    public void setId(int id) {this.idCasos = id;}

    public int getId() {
        return this.idCasos;
    }
}
