package app.view;

import app.Main;
import app.control.CRUDArticle;
import app.control.CRUDCriteria;
import app.control.CRUDEvaluation;
import app.control.CRUDEvent;
import app.control.CRUDUser;
import app.control.EmailSender;
import app.control.PDFController;
import app.model.Article;
import app.model.CriteriaWithValue;
import app.model.Evaluation;
import app.model.Event;
import app.view.viewcontrollers.MaskField;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class EvaluationView {
    private final SplitPane splitPane = new SplitPane();
    private final CRUDArticle article = new CRUDArticle();
    private final ObservableList<Article> articleData = FXCollections.observableArrayList();
    private final ObservableList<String> articleDataString = FXCollections.observableArrayList();
    private final CRUDEvent events = new CRUDEvent();
    private final CRUDUser users = new CRUDUser();
    private final Label lblResult = new Label("");
    
    public EvaluationView() {
        Main.refreshBottom();
        setLeft();
        setCenter();
        splitPane.setDividerPositions(0.3333f, 0.67777f);
    }
    
    public SplitPane evaluationShow() {
        return splitPane;
    }
    
    private void setLeft() {
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        Label lblMembers = new Label("Lista de eventos");
        
        vbox2.getChildren().addAll(lblMembers);
        
        ObservableList<Event> eventData = FXCollections.observableArrayList(events.getAllEventsByEvaluator(Main.getEvaluator()));
        ObservableList<String> eventDataString = FXCollections.observableArrayList();
        
        for(Event a : eventData){
            eventDataString.add(a.getTitulo());
        }
        
        ListView<String> listView = new ListView<>(eventDataString);
        
        listView.setStyle("-fx-control-inner-background-alt: white; -fx-font-size: 12; "
                + "-fx-font-family: 'Segoe UI'");
        
        listView.prefHeightProperty().bind(vbox1.heightProperty().subtract(vbox2.heightProperty().get()));
        listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> obsv, String oldv, String nv) -> {
            if(nv == null) return;
            if(splitPane.getItems().size() == 3){
                splitPane.getItems().remove(2);
            }
            
            articleDataString.clear();
            articleData.setAll(article.getAllArticlesByEvaluatorAndEvent(eventData.get(listView.getSelectionModel().getSelectedIndex())));
            for(int i = 0; i < articleData.size(); i++){
                articleDataString.add(articleData.get(i).getTitulo());
            }
            if(oldv == null) return;
        });
                
        vbox1.getChildren().addAll(vbox2, listView);
        vbox1.prefHeightProperty().bind(splitPane.heightProperty());
        
        VBox.setMargin(vbox2, new Insets(10, 0, 10, 25));
        
        Main.setBackgroundWhite(vbox1, vbox2);
        
        splitPane.getItems().add(vbox1);
    }
    
    private void setCenter() {
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        Label lblMembers = new Label("Lista de artigos");
        
        vbox2.getChildren().addAll(lblMembers);
        
        ListView<String> listView = new ListView<>(articleDataString);
        
        listView.setStyle("-fx-control-inner-background-alt: white; -fx-font-size: 12; "
                + "-fx-font-family: 'Segoe UI'");
        
        listView.prefHeightProperty().bind(vbox1.heightProperty().subtract(vbox2.heightProperty().get()));
        
        listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> obsv, String oldv, String nv) -> {
            
            if(nv == null) return;
           
                if(splitPane.getItems().size() < 3) {
                    splitPane.getItems().add(getRight(articleData.get(listView.getSelectionModel().getSelectedIndex())));
                } else{
                    splitPane.getItems().set(2, getRight(articleData.get(listView.getSelectionModel().getSelectedIndex())));
                }
             if(oldv == null) return;
        });
        
        vbox1.getChildren().addAll(vbox2, listView);
        vbox1.prefHeightProperty().bind(splitPane.heightProperty());
        
        VBox.setMargin(vbox2, new Insets(10, 0, 10, 25));
        
        Main.setBackgroundWhite(vbox1, vbox2);
        
        splitPane.getItems().add(vbox1);
    }
    
    private VBox getRight(Article article) {
        VBox vbox1 = new VBox(5);
        HBox hbox1 = new HBox();
        Hyperlink pdf = new Hyperlink(article.getTitulo());
        HBox hbox = new HBox(10);
        ToggleGroup group = new ToggleGroup();
        RadioButton approved = new RadioButton("Aprovado");
        RadioButton disapproved = new RadioButton("Reprovado");
        TextArea comments = new TextArea();
        HBox hbox2 = new HBox();
        Button send = new Button("Enviar");
        Button confirm = new Button("Calcular");
        hbox2.getChildren().add(confirm);
        
        MaskField.maxLength(comments, 1024);
        
        approved.setToggleGroup(group);
        disapproved.setToggleGroup(group);
        
        hbox1.getChildren().add(new Label("Veredito"));
        
        hbox1.setBorder(new Border(new BorderStroke(Color.WHITE, Color.WHITE, Color.GRAY, Color.WHITE,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, 
                CornerRadii.EMPTY,new BorderWidths(1), Insets.EMPTY)));
        
        hbox1.setPrefHeight(40);
        
        hbox.getChildren().addAll(approved, disapproved);
        
        hbox.setAlignment(Pos.CENTER);
        hbox1.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER_RIGHT);
        
        group.selectToggle(approved);
        List<CriteriaWithValue> criteria = new CRUDCriteria().getAllCriteriaFromAnEvent(events.getEventById(article.getEvento().getId()));
        List<ToggleGroup> criteriaGroup = new ArrayList<>();
        
        for(int i = 0; i < criteria.size(); i++){
            criteriaGroup.add(new ToggleGroup());
        }
   
        
        VBox vboxCriteria = new VBox();
        
        vboxCriteria.setSpacing(10);
    
        
        for(int i = 0; i < criteria.size(); i++){
            CriteriaWithValue a = criteria.get(i);
            Label ab = new Label((char)0x2022 + a.getCriteria().getNome());
            Label lblValue = new Label("Cumprido: ");
            RadioButton rbYes = new RadioButton("Sim");
            RadioButton rbNo = new RadioButton("Não");
            
            rbYes.setToggleGroup(criteriaGroup.get(i));
            rbNo.setToggleGroup(criteriaGroup.get(i));
            
            criteriaGroup.get(i).selectToggle(rbNo);
            
            HBox hBoxTemp = new HBox();
            hBoxTemp.getChildren().addAll(ab, lblValue, rbYes, rbNo);

            hBoxTemp.setSpacing(15);
            vboxCriteria.getChildren().add(hBoxTemp);
        }
        
        pdf.setOnAction(e->{
            String selectedDirectoryString;
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Onde deseja salvar o artigo? ");
            directoryChooser.setInitialDirectory(new File("C:/Users/" + System.getProperty("user.name") + "/documents"));
            File selectedDirectory = directoryChooser.showDialog(null);
            
            if(selectedDirectory == null){
                selectedDirectoryString = "Nenhum diretório foi selecionado";
            }else{
                selectedDirectoryString = selectedDirectory.getAbsolutePath();
            }
            
            if(!selectedDirectoryString.equals("Nenhum diretório foi selecionado")){
                try {
                    PDFController.downloadDocument(article.getCaminho(), selectedDirectoryString, article.getTitulo());
                } catch (IOException ex) {
                    Logger.getLogger(EvaluationView.class.getName()).log(Level.SEVERE, null, ex);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Gravação realizada com sucesso.", ButtonType.CLOSE);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
       
                stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
       
                stage.showAndWait();
            }else{
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Nenhum diretório selecionado.", ButtonType.CLOSE);
                alerta.setTitle("Atenção");
                alerta.setHeaderText("Problema na gravação");
                Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
       
                stage.getIcons().add(new Image(getClass().getResourceAsStream("koala1.png")));
       
                stage.showAndWait();
            }
        });
       
        int[] pesos = new int[criteria.size()];
        int[] resultadonum = {0};
        
        confirm.setOnAction(e->{
            resultadonum[0] = 0;
                    
            for(int i = 0; i < criteria.size(); i++){
                pesos[i] = criteria.get(i).getPeso();
                
                List<Toggle> temp = criteriaGroup.get(i).getToggles();
                
                if(criteriaGroup.get(i).getSelectedToggle().equals(temp.get(0))){
                    resultadonum[0] += pesos[i];
                }
            }
            
            if(resultadonum[0] > 50){
                lblResult.setStyle("-fx-text-fill: green;");
            
                lblResult.setText("O cálculo a partir de critérios resultou num total de " +
                    resultadonum[0] + "%. \nA recomendação é que você aprove o candidato.");
            }
            else{
                lblResult.setStyle("-fx-text-fill: red;");
                lblResult.setText("O cálculo a partir de critérios resultou num total de " +
                        resultadonum[0] + "%. \nA recomendação é que você reprove o candidato.");
            }
            
            if(hbox2.getChildren().size() == 1){
                hbox2.setSpacing(5);
                hbox2.getChildren().add(send);
            }
        });
        
        
        
        send.setOnAction(e-> {
            
            if(comments.getText() != null || !comments.getText().equals("")){
            
                boolean resultado;
                if(group.getSelectedToggle().equals(approved)){
                   resultado = true;
                }else{
                   resultado = false;
                }

                String mensagem = "";

                if(resultado){

                mensagem = "Parabéns, " + article.getUsuario().getNome()+ "! "
                        + "Seu artigo para o evento :" + article.getEvento().getTitulo() + " foi aprovado com sucesso pelo avaliador " + Main.getEvaluator().getNome() + ".\n"
                        + "Segue abaixo a mensagem enviada pelo mesmo:\n" + 
                        comments.getText() + "\nATENÇÃO: seu artigo ainda pode ter que passar por outras avaliações.";
                } else {
                    mensagem = "Infelizmente, " + article.getUsuario().getNome() + " seu artigo foi reprovado pelo avaliador " + 
                            Main.getEvaluator().getNome() + ".\n"+
                            "Não desanime, pois ainda podem restar outras avaliações. Segue abaixo a justificativa do avaliador: \n" + 
                            comments.getText();
                }


                Evaluation evaluation = new Evaluation(resultado, comments.getText(), Main.getEvaluator(), article);
                CRUDEvaluation crudEvaluation = new CRUDEvaluation();
                crudEvaluation.addEvaluation(evaluation);
                EmailSender.sendEmail(mensagem, article.getUsuario().getLogin().getEmail(), "PSA - Avaliação para o Evento: " + article.getEvento().getTitulo());
                
               
                if(article.isPalestrante() && resultado){
                    users.turnSpeaker(article.getUsuario());
                    if(events.existsVacancies(article.getEvento())){
                        if(new CRUDEvaluation().isTheLastEvaluation(article)){
                            users.insertSpeakerInAnEvent(article.getEvento(), article.getUsuario());
                        }
                    }    
                }
                new CRUDEvaluation().makeArticleEvaluation(article);
                Main.evaluationShow();
            }
            
        });
        
        vbox1.getChildren().addAll(hbox1, pdf, hbox, comments, vboxCriteria,lblResult, hbox2);
        
        Main.setBackgroundWhite(vbox1, hbox);
        
        return vbox1;
    }
}
