package app.view.viewcontrollers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public abstract class MaskField {
    private static List<KeyCode> ignoredKeyCodes;
    static {
        ignoredKeyCodes = new ArrayList<>();
        Collections.addAll(ignoredKeyCodes, new KeyCode[]{KeyCode.F1, KeyCode.F2, KeyCode.F3, KeyCode.F4, 
            KeyCode.F5, KeyCode.F6, KeyCode.F7, KeyCode.F8, KeyCode.F9, KeyCode.F10, KeyCode.F11, KeyCode.F12, KeyCode.CONTROL});
    }
    
    public static void ignoreKeys(final TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_PRESSED,  new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(ignoredKeyCodes.contains(event.getCode())) {
                    event.consume();
                }
            }
        });
    }
    
    
    public static void numericField(final TextField textField) {
        textField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    char ch = textField.getText().charAt(oldValue.intValue());
                    if (!(ch >= '0' && ch <= '9')) {
                        textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                    }
                }
            }
        });
    }
    
    public static void emailField(final TextField textField) {
        textField.setOnKeyTyped((KeyEvent event)->{
            if("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz._-@".contains(event.getCharacter()) == false) {
                event.consume();
            }
            
            if("@".equals(event.getCharacter())&&textField.getText().contains("@")){
                event.consume();
            }

            if("@".equals(event.getCharacter())&&textField.getText().length()==0){
                event.consume();
            }
        });
    }
    
    public static void idField(final TextField textField) {
        textField.setOnKeyTyped((KeyEvent event) ->{
           if("0123456789xX".contains(event.getCharacter())== false) {
               event.consume();
           }
           if(textField.getText().length() == 7) {
               event.consume();
           }
        });
    }
    
    public static void timeField(final TextField textField) {
        
        textField.setOnKeyTyped((KeyEvent event)->{
            if(event.getCharacter().contains(" ")) {
                event.consume();
            }
            if(textField.getText().length() == 5) {
                event.consume();
            } else if(event.getCharacter().trim().length() == 0) {
               if(textField.getText().length()== 3) {
                   textField.setText(textField.getText().substring(0, textField.getText().length()-2));
                   textField.positionCaret(1);
                   event.consume();
               }
            } else if(textField.getText().length()==0) {
                    if("012".contains(event.getCharacter()) == false) {
                        event.consume();
                    }
                } else if(textField.getText().length() == 1) {
                    if("01".contains(textField.getText())) {
                        if("0123456789".contains(event.getCharacter())) {
                            textField.setText(textField.getText() + event.getCharacter() + ":");
                            textField.positionCaret(textField.getText().length());
                            event.consume();
                        }
                    } else {
                        if("0123".contains(event.getCharacter())) {
                            textField.setText(textField.getText() + event.getCharacter() + ":");
                            textField.positionCaret(textField.getText().length());
                            event.consume();
                        }
                    }
                    
                    event.consume();
                } else if(textField.getText().length() == 3) {
                    if("012345".contains(event.getCharacter()) == false) {
                        event.consume();
                    }
                } else if(textField.getText().length() == 4) {
                    if("0123456789".contains(event.getCharacter()) == false) {
                        event.consume();
                } 
            }
        });
    }
}
