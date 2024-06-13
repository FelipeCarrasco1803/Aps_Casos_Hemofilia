package com.unip.aps_3_sem_lpoo.models;

public class Cidade {

    private int IdCidade, populacao;
    private String nome;

    public Cidade(int id, String nome, int populacao) {
        this.IdCidade = id;
        this.populacao = populacao;
        this.nome = nome;
    }

    public int getIdCidade() {
        return IdCidade;
    }

    public int getPopulacao() {
        return populacao;
    }

    public String getNome() {
        return nome;
    }
}
