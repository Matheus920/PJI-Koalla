package app.model;

public class Article {
    
    private long id;
    private String titulo;
    private String caminho;
    private Event evento;
    private boolean palestrante;
    private User usuario;
    private boolean aprovacao;

    public Article(String titulo, String caminho, Event evento, boolean palestrante, User usuario, boolean aprovacao) {
        this.titulo = titulo;
        this.caminho = caminho;
        this.evento = evento;
        this.palestrante = palestrante;
        this.usuario = usuario;
        this.aprovacao = aprovacao;
    }
    
    public Article(long id, String titulo, String caminho, Event evento, boolean palestrante, User usuario, boolean aprovacao) {
        this.id = id;
        this.titulo = titulo;
        this.caminho = caminho;
        this.evento = evento;
        this.palestrante = palestrante;
        this.usuario = usuario;
        this.aprovacao = aprovacao;
    }

    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Event getEvento() {
        return evento;
    }

    public void setEvento(Event evento) {
        this.evento = evento;
    }

    public boolean isPalestrante() {
        return palestrante;
    }

    public void setPalestrante(boolean palestrante) {
        this.palestrante = palestrante;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
    
   public boolean isAprovacao(){
       return aprovacao;
   }
   
   public void setAprovacao(boolean aprovacao){
       this.aprovacao = aprovacao;
   }
    
}
