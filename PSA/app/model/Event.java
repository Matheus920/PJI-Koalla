package app.model;

import java.time.LocalDateTime;

public class Event {
    private long id;
    private String titulo;
    private String resumo;
    private String descricao;
    private LocalDateTime dataInicio;
    private int duracao;
    private String local;
    private int capacidade;
    private boolean status;
    private int quantidadePalestrantes;
    private Board comite;
    
    
    public Event(){}
    
    public Event(long id, String titulo, String resumo, String descricao, LocalDateTime dataInicio, int duracao, String local, int capacidade, boolean status, int quantidadePalestrantes, Board comite) {
        this.id = id;
        this.titulo = titulo;
        this.resumo = resumo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.duracao = duracao;
        this.local = local;
        this.capacidade = capacidade;
        this.status = status;
        this.quantidadePalestrantes = quantidadePalestrantes;
        this.comite = comite;
    }

    public Event(String titulo, String resumo, String descricao, LocalDateTime dataInicio, int duracao, String local, int capacidade, boolean status, int quantidadePalestrantes, Board comite) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.duracao = duracao;
        this.local = local;
        this.capacidade = capacidade;
        this.status = status;
        this.quantidadePalestrantes = quantidadePalestrantes;
        this.comite = comite;
    }

    
    public String getTitulo(){
        return titulo;
    }
    
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getQuantidadePalestrantes() {
        return quantidadePalestrantes;
    }

    public void setQuantidadePalestrantes(int quantidadePalestrantes) {
        this.quantidadePalestrantes = quantidadePalestrantes;
    }

    public Board getComite() {
        return comite;
    }

    public void setComite(Board comite) {
        this.comite = comite;
    }
}
