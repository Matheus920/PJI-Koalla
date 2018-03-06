package app.model;

import java.time.LocalDate;


public class User {
    private long id;
    private String nome;
    private Login login;
    private String instituicao;
    private LocalDate dataNascimento;
    private String caminhoImagem;
    private boolean palestrante;

    public User(){
        
    }
    
    
    public User(String nome, Login login, String instituicao, LocalDate dataNascimento, String caminhoImagem, boolean palestrante) {
        this.nome = nome;
        this.login = login;
        this.instituicao = instituicao;
        this.dataNascimento = dataNascimento;
        this.caminhoImagem = caminhoImagem;
        this.palestrante = palestrante;
    }

    public User(long id, String nome, Login login, String instituicao, LocalDate dataNascimento, String caminhoImagem, boolean palestrante) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.instituicao = instituicao;
        this.dataNascimento = dataNascimento;
        this.caminhoImagem = caminhoImagem;
        this.palestrante = palestrante;
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

    public Login getLogin() {
        return login;
    }
    
    public void setLogin(Login login){
        this.login = login;
    }

    public void setEmail(Login login) {
        this.login = login;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
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
}
