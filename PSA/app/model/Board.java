package app.model;

import java.time.LocalDate;

public class Board {
    private long id;
    private String prontuario;
    private String nome;
    private LocalDate dataNascimento;
    private String funcao;
    private Login login;
    private String caminhoDaImagem;

    
    public Board(){
        
    }
    
    public Board(long id, String prontuario, LocalDate dataNascimento, String funcao, Login login, String caminhoDaImagem, String nome) {
        this.id = id;
        this.prontuario = prontuario;
        this.dataNascimento = dataNascimento;
        this.funcao = funcao;
        this.login = login;
        this.caminhoDaImagem = caminhoDaImagem;
        this.nome = nome;
    }

    public Board(String prontuario, LocalDate dataNascimento, String funcao, Login login, String caminhoDaImagem, String nome) {
        this.prontuario = prontuario;
        this.dataNascimento = dataNascimento;
        this.funcao = funcao;
        this.login = login;
        this.caminhoDaImagem = caminhoDaImagem;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProntuario() {
        return prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getCaminhoDaImagem() {
        return caminhoDaImagem;
    }

    public void setCaminhoDaImagem(String caminhoDaImagem) {
        this.caminhoDaImagem = caminhoDaImagem;
    }
    
    
}
