package app.view;

import app.Main;
import app.control.interfaces.PrivilegeTypeInterface;
import app.control.interfaces.PrivilegiesButtonsInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MenuView {
    private final ObservableList<Button> buttons = FXCollections.observableArrayList();
    private VBox vbox = new VBox(0);
    private PrivilegiesButtonsInterface privilegies;
    
    public MenuView(PrivilegiesButtonsInterface privilegies) {
        this.privilegies = privilegies;
        setButtons();
        vbox.getChildren().addAll(buttons);
    }
    
    public VBox menuShow()
    {
        return vbox;
    }
    
    private void setButtons() {
        for(String a : privilegies.getButtons()) {
            buttons.add(new Button(a));
        }
        
        switch(privilegies.getPrivilegeType()) {
            case PrivilegeTypeInterface.ADMIN:
                setButtonsAdmin();
                break;
            case PrivilegeTypeInterface.BOARD:
                setButtonsBoard();
                break;
            case PrivilegeTypeInterface.EVALUATOR:
                setButtonsEvaluator();
                break;
            case PrivilegeTypeInterface.NOTLOGGED:
                setButtonsNotLogged();
                break;
            case PrivilegeTypeInterface.USER:
                setButtonsUser();
                break;
        }
    }
    
    private void setButtonsAdmin() {
        for(int i = 0; i < privilegies.getAmountButtons(); i++) {
            buttons.get(i).setFont(Font.font("Segoe UI", 15));
            buttons.get(i).setPrefSize(100, 70);
            buttons.get(i).setCursor(Cursor.HAND);
        }
        
        buttons.get(PrivilegiesButtonsInterface.AdminButtons.EVENT).setOnAction(e->{
            Main.eventShow();
        });
        
        buttons.get(PrivilegiesButtonsInterface.AdminButtons.BOARD).setOnAction(e->{
            Main.boardShow();
        });
        
       // buttons.get(PrivilegiesButtonsInterface.AdminButtons.CALENDAR).setOnAction(e->{
            // TODO: chamar tela calendario
     //});
        
        buttons.get(PrivilegiesButtonsInterface.AdminButtons.CATEGORY).setOnAction(e->{
            Main.categoryShow();
        });
        
        buttons.get(PrivilegiesButtonsInterface.AdminButtons.SPEAKER).setOnAction(e->{
            Main.speakerShow();
        });
        
        buttons.get(PrivilegiesButtonsInterface.AdminButtons.EVALUATORS).setOnAction(e->{
            Main.evaluatorShow();
        });
        
        
    }
    
    private void setButtonsNotLogged() {
        for(int i = 0; i < privilegies.getAmountButtons(); i++) {
            buttons.get(i).setFont(Font.font("Segoe UI", 15));
            buttons.get(i).setPrefSize(100, 70);
            buttons.get(i).setCursor(Cursor.HAND);
        }
        
        buttons.get(PrivilegiesButtonsInterface.UserButtons.EVENT).setOnAction(e->{
            Main.eventShow();
        });
        
       // buttons.get(PrivilegiesButtonsInterface.UserButtons.CALENDAR).setOnAction(e->{
            // TODO: chamar tela calendario
        //});
        
        buttons.get(PrivilegiesButtonsInterface.UserButtons.SPEAKER).setOnAction(e->{
            Main.speakerShow();
        });
        
        buttons.get(PrivilegiesButtonsInterface.UserButtons.EVALUATORS).setOnAction(e->{
            Main.evaluatorShow();
        });
        
        buttons.get(PrivilegiesButtonsInterface.UserButtons.BOARD).setOnAction(e->{
            Main.boardShow();
        });
    }
    
    private void setButtonsUser() {
        setButtonsNotLogged();
    }
    
    private void setButtonsEvaluator() {
        for(int i = 0; i < privilegies.getAmountButtons(); i++) {
            buttons.get(i).setFont(Font.font("Segoe UI", 15));
            buttons.get(i).setPrefSize(100, 70);
            buttons.get(i).setCursor(Cursor.HAND);
        }
        
        buttons.get(PrivilegiesButtonsInterface.EvaluatorButtons.EVENT).setOnAction(e->{
            Main.eventShow();
        });
        
        
        buttons.get(PrivilegiesButtonsInterface.EvaluatorButtons.EVALUATION).setOnAction(e->{
            Main.evaluationShow();
        });
        
       
       // buttons.get(PrivilegiesButtonsInterface.EvaluatorButtons.CALENDAR).setOnAction(e->{
            // TODO: chamar tela calendario
        //});
        
        buttons.get(PrivilegiesButtonsInterface.EvaluatorButtons.SPEAKER).setOnAction(e->{
            Main.speakerShow();
        });
        
        buttons.get(PrivilegiesButtonsInterface.EvaluatorButtons.EVALUATORS).setOnAction(e->{
            Main.evaluatorShow();
        });
        
        buttons.get(PrivilegiesButtonsInterface.EvaluatorButtons.BOARD).setOnAction(e->{
            Main.boardShow();
        });
    }
    
    private void setButtonsBoard() {
        for(int i = 0; i < privilegies.getAmountButtons(); i++) {
            buttons.get(i).setFont(Font.font("Segoe UI", 15));
            buttons.get(i).setPrefSize(100, 70);
            buttons.get(i).setCursor(Cursor.HAND);
        }
        
        buttons.get(PrivilegiesButtonsInterface.BoardButtons.EVENT).setOnAction(e->{
            Main.eventShow();
        });
        
        
       // buttons.get(PrivilegiesButtonsInterface.BoardButtons.CALENDAR).setOnAction(e->{
            // TODO: chamar tela calendario
       // }); 
       
        buttons.get(PrivilegiesButtonsInterface.BoardButtons.EVALUATORS).setOnAction(e->{
            Main.evaluatorShow();
        });
        
        buttons.get(PrivilegiesButtonsInterface.BoardButtons.SPEAKER).setOnAction(e->{
            Main.speakerShow();
        });
        
        buttons.get(PrivilegiesButtonsInterface.BoardButtons.BOARD).setOnAction(e->{
            Main.boardShow();
        });
        
        buttons.get(PrivilegiesButtonsInterface.BoardButtons.CRITERIA).setOnAction(e
                -> {
            Main.criteriaShow();
        });
    }
}
