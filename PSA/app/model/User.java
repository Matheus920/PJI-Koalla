package app.model;

import java.time.LocalDate;


public class User {
    private long id;
    private String nome;
    private String email;
    private String instituicao;
    private String senha;
    private LocalDate dataNascimento;
    private String caminhoImagem;
    private boolean palestrante;
    private int privilegios;

    public User(String nome, String email, String instituicao, String senha, LocalDate dataNascimento, String caminhoImagem, boolean palestrante) {
        this.nome = nome;
        this.email = email;
        this.instituicao = instituicao;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.caminhoImagem = caminhoImagem;
        this.palestrante = palestrante;
    }

    public User(long id, String nome, String email, String instituicao, String senha, LocalDate dataNascimento, String caminhoImagem, boolean palestrante, int privilegios) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.instituicao = instituicao;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.caminhoImagem = caminhoImagem;
        this.palestrante = palestrante;
        this.privilegios = privilegios;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    public boolean isPalestrante() {
        return palestrante;
    }

    public void setPalestrante(boolean palestrante) {
        this.palestrante = palestrante;
    }

    public int getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(int privilegios) {
        this.privilegios = privilegios;
    }
    
    
}
