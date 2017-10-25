
package app.model;


public class Criteria {
    private long id;
    private String nome;
    private String descricao;

    public Criteria(long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }
    
    public Criteria(long id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public Criteria(String nome) {
        this.nome = nome;
    }
    
    public Criteria(String nome, String descricao){
        this.descricao = descricao;
        this.nome = nome;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
}
