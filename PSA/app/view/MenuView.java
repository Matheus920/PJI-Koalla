package app.view;

import app.Main;
import app.control.PrivilegeType;
import app.control.interfaces.PrivilegiesButtonsInterface;
import app.control.PrivilegiesTest;
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
    public MenuView(PrivilegiesButtonsInterface privilegies)
    {
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
        buttons.get(PrivilegeType.Admin.EVENT).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.Admin.EVENT).setPrefSize(100, 70);
        buttons.get(PrivilegeType.Admin.EVENT).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.Admin.EVENT).setOnAction(e->{
            Main.eventShow();
        });
        
        buttons.get(PrivilegeType.Admin.BOARD).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.Admin.BOARD).setPrefSize(100, 70);
        buttons.get(PrivilegeType.Admin.BOARD).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.Admin.BOARD).setOnAction(e->{
            // TODO: chamar tela comite academico
        });
        
        buttons.get(PrivilegeType.Admin.CALENDAR).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.Admin.CALENDAR).setPrefSize(100, 70);
        buttons.get(PrivilegeType.Admin.CALENDAR).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.Admin.CALENDAR).setOnAction(e->{
            // TODO: chamar tela calendario
        });
        
        buttons.get(PrivilegeType.Admin.CATEGORY).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.Admin.CATEGORY).setPrefSize(100, 70);
        buttons.get(PrivilegeType.Admin.CATEGORY).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.Admin.CATEGORY).setOnAction(e->{
            Main.categoryShow();
        });
        
        buttons.get(PrivilegeType.Admin.SPEAKER).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.Admin.SPEAKER).setPrefSize(100, 70);
        buttons.get(PrivilegeType.Admin.SPEAKER).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.Admin.SPEAKER).setOnAction(e->{
            // TODO: chamar tela palestrante
        });
    }
    
    private void setButtonsNotLogged() {
        buttons.get(PrivilegeType.User.EVENT).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.User.EVENT).setPrefSize(100, 70);
        buttons.get(PrivilegeType.User.EVENT).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.User.EVENT).setOnAction(e->{
            Main.eventShow();
        });
        
        buttons.get(PrivilegeType.User.CALENDAR).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.User.CALENDAR).setPrefSize(100, 70);
        buttons.get(PrivilegeType.User.CALENDAR).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.User.CALENDAR).setOnAction(e->{
            // TODO: chamar tela calendario
        });
    }
    
    private void setButtonsUser() {
        setButtonsNotLogged();
    }
    
    private void setButtonsEvaluator() {
        buttons.get(PrivilegeType.Evaluator.EVENT).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.Evaluator.EVENT).setPrefSize(100, 70);
        buttons.get(PrivilegeType.Evaluator.EVENT).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.Evaluator.EVENT).setOnAction(e->{
            Main.eventShow();
        });
        
        buttons.get(PrivilegeType.Evaluator.EVALUATION).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.Evaluator.EVALUATION).setPrefSize(100, 70);
        buttons.get(PrivilegeType.Evaluator.EVALUATION).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.Evaluator.EVALUATION).setOnAction(e->{
            // TODO: chamar tela comite academico
        });
        
        buttons.get(PrivilegeType.Evaluator.CALENDAR).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.Evaluator.CALENDAR).setPrefSize(100, 70);
        buttons.get(PrivilegeType.Evaluator.CALENDAR).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.Evaluator.CALENDAR).setOnAction(e->{
            // TODO: chamar tela calendario
        });
    }
    
    private void setButtonsBoard() {
        buttons.get(PrivilegeType.Board.EVENT).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.Board.EVENT).setPrefSize(100, 70);
        buttons.get(PrivilegeType.Board.EVENT).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.Board.EVENT).setOnAction(e->{
            // TODO: chamar tela calendario
        });
        
        buttons.get(PrivilegeType.Board.CALENDAR).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.Board.CALENDAR).setPrefSize(100, 70);
        buttons.get(PrivilegeType.Board.CALENDAR).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.Board.CALENDAR).setOnAction(e->{
            // TODO: chamar tela calendario
        });
        
        buttons.get(PrivilegeType.Board.EVALUATORS).setFont(Font.font("Segoe UI", 15));
        buttons.get(PrivilegeType.Board.EVALUATORS).setPrefSize(100, 70);
        buttons.get(PrivilegeType.Board.EVALUATORS).setCursor(Cursor.HAND);
        buttons.get(PrivilegeType.Board.EVALUATORS).setOnAction(e->{
            // TODO: chamar tela calendario
        });
        
        
    }
}
