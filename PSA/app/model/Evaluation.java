package app.model;


public class Evaluation {
    private long id;
    private boolean resultado;
    private String mensagem;
    private Evaluator avaliador;
    private Article artigo;

    public Evaluation(long id, boolean resultado, String mensagem, Evaluator avaliador, Article artigo) {
        this.id = id;
        this.resultado = resultado;
        this.mensagem = mensagem;
        this.avaliador = avaliador;
        this.artigo = artigo;
    }

    public Evaluation(boolean resultado, String mensagem, Evaluator avaliador, Article artigo) {
        this.resultado = resultado;
        this.mensagem = mensagem;
        this.avaliador = avaliador;
        this.artigo = artigo;
    }

    
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Evaluator getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(Evaluator avaliador) {
        this.avaliador = avaliador;
    }

    public Article getArtigo() {
        return artigo;
    }

    public void setArtigo(Article artigo) {
        this.artigo = artigo;
    }
    
    
}
