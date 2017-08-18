package app.view;

import app.Main;
import app.control.PrivilegeType;
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
            case PrivilegeType.ADMIN:
                setButtonsAdmin();
                break;
            case PrivilegeType.BOARD:
                setButtonsBoard();
                break;
            case PrivilegeType.EVALUATOR:
                setButtonsEvaluator();
                break;
            case PrivilegeType.NOTLOGGED:
                setButtonsNotLogged();
                break;
            case PrivilegeType.USER:
                setButtonsUser();
                break;
        }
    }
    
    private void setButtonsAdmin() {
        buttons.get(PrivilegeType.AdminButtons.EVENT).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.AdminButtons.EVENT).setPrefSize(100, 70);
        buttons.get(PrivilegeType.AdminButtons.EVENT).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.AdminButtons.EVENT).setOnAction(e->{
            Main.eventShow();
        });
        
        buttons.get(PrivilegeType.AdminButtons.BOARD).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.AdminButtons.BOARD).setPrefSize(100, 70);
        buttons.get(PrivilegeType.AdminButtons.BOARD).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.AdminButtons.BOARD).setOnAction(e->{
            // TODO: chamar tela comite academico
        });
        
        buttons.get(PrivilegeType.AdminButtons.CALENDAR).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.AdminButtons.CALENDAR).setPrefSize(100, 70);
        buttons.get(PrivilegeType.AdminButtons.CALENDAR).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.AdminButtons.CALENDAR).setOnAction(e->{
            // TODO: chamar tela calendario
        });
        
        buttons.get(PrivilegeType.AdminButtons.CATEGORY).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.AdminButtons.CATEGORY).setPrefSize(100, 70);
        buttons.get(PrivilegeType.AdminButtons.CATEGORY).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.AdminButtons.CATEGORY).setOnAction(e->{
            Main.categoryShow();
        });
        
        buttons.get(PrivilegeType.AdminButtons.SPEAKER).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.AdminButtons.SPEAKER).setPrefSize(100, 70);
        buttons.get(PrivilegeType.AdminButtons.SPEAKER).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.AdminButtons.SPEAKER).setOnAction(e->{
            // TODO: chamar tela palestrante
        });
    }
    
    private void setButtonsNotLogged() {
        buttons.get(PrivilegeType.UserButtons.EVENT).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.UserButtons.EVENT).setPrefSize(100, 70);
        buttons.get(PrivilegeType.UserButtons.EVENT).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.UserButtons.EVENT).setOnAction(e->{
            Main.eventShow();
        });
        
        buttons.get(PrivilegeType.UserButtons.CALENDAR).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.UserButtons.CALENDAR).setPrefSize(100, 70);
        buttons.get(PrivilegeType.UserButtons.CALENDAR).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.UserButtons.CALENDAR).setOnAction(e->{
            // TODO: chamar tela calendario
        });
    }
    
    private void setButtonsUser() {
        setButtonsNotLogged();
    }
    
    private void setButtonsEvaluator() {
        buttons.get(PrivilegeType.EvaluatorButtons.EVENT).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.EvaluatorButtons.EVENT).setPrefSize(100, 70);
        buttons.get(PrivilegeType.EvaluatorButtons.EVENT).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.EvaluatorButtons.EVENT).setOnAction(e->{
            Main.eventShow();
        });
        
        buttons.get(PrivilegeType.EvaluatorButtons.EVALUATION).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.EvaluatorButtons.EVALUATION).setPrefSize(100, 70);
        buttons.get(PrivilegeType.EvaluatorButtons.EVALUATION).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.EvaluatorButtons.EVALUATION).setOnAction(e->{
            // TODO: chamar tela comite avaliação
        });
        
        buttons.get(PrivilegeType.EvaluatorButtons.CALENDAR).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.EvaluatorButtons.CALENDAR).setPrefSize(100, 70);
        buttons.get(PrivilegeType.EvaluatorButtons.CALENDAR).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.EvaluatorButtons.CALENDAR).setOnAction(e->{
            // TODO: chamar tela calendario
        });
    }
    
    private void setButtonsBoard() {
        buttons.get(PrivilegeType.BoardButtons.EVENT).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.BoardButtons.EVENT).setPrefSize(100, 70);
        buttons.get(PrivilegeType.BoardButtons.EVENT).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.BoardButtons.EVENT).setOnAction(e->{
            Main.eventShow();
        });
        
        buttons.get(PrivilegeType.BoardButtons.CALENDAR).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.BoardButtons.CALENDAR).setPrefSize(100, 70);
        buttons.get(PrivilegeType.BoardButtons.CALENDAR).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.BoardButtons.CALENDAR).setOnAction(e->{
            // TODO: chamar tela calendario
        });
        
        buttons.get(PrivilegeType.BoardButtons.EVALUATORS).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.BoardButtons.EVALUATORS).setPrefSize(100, 70);
        buttons.get(PrivilegeType.BoardButtons.EVALUATORS).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.BoardButtons.EVALUATORS).setOnAction(e->{
            // TODO: chamar tela avaliadores
        });
        
        
    }
}
