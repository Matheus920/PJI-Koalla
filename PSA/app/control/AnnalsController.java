package app.control;

import app.control.interfaces.AnnalsControllerInterface;
import app.data.ArticleDAO;
import app.model.Article;
import app.model.Event;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AnnalsController implements AnnalsControllerInterface {
    
    @Override
    public void makeAnnals(Event event, String caminhoSaida){
        List<Article> artigos = new ArticleDAO().getAllArticlesByEvent(event);
        List<String> caminhos = new ArrayList<>();
        
        for(Article a : artigos){
            caminhos.add(a.getCaminho());
        }
        
        
        
        caminhoSaida = caminhoSaida + "\\" +event.getTitulo().replaceAll(" ", "") + "-anais.pdf";
        
        String[] conteudoPadrao = {"Anais gerados automaticamente para o Evento - " + event.getTitulo(), 
                " aqui estão disponíveis todos os artigos enviados para o mesmo."};
        
        
        try {
            PDFController.mergeDocuments(caminhos, conteudoPadrao, caminhoSaida);
        } catch (IOException ex) {
            Logger.getLogger(AnnalsController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }    
}
