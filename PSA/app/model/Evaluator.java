package app.model;

import java.time.LocalDate;

public class Evaluator {
    private long id;
    private String nome;
    private String prontuario;
    private LocalDate dataNascimento;
    private String area;
    private Login login;
    private boolean avaliador;

    public Evaluator(){
        
    }
    
    public Evaluator(long id, Login login, String nome, String prontuario, LocalDate dataNascimento, String area, boolean avaliador) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.prontuario = prontuario;
        this.dataNascimento = dataNascimento;
        this.area = area;
        this.avaliador = avaliador;
    }

    public Evaluator(String nome, Login login, String prontuario, LocalDate dataNascimento, String area, boolean avaliador) {
        this.nome = nome;
        this.prontuario = prontuario;
        this.login = login;
        this.dataNascimento = dataNascimento;
        this.area = area;
        this.avaliador = avaliador;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean isAvaliador() {
        return avaliador;
    }

    public void setAvaliador(boolean avaliador) {
        this.avaliador = avaliador;
    }
    
    public Login getLogin(){
        return login;
    }
    
    public void setLogin(Login login){
        this.login = login;
    }
    
}
