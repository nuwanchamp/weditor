package org.api;
import javafx.scene.control.TextArea;


public class TextAreaController {
    private final TextArea ta;

    public TextAreaController(TextArea textArea) {
        ta = textArea;
    }


    public void insert(String text){
        ta.setText(text);
    }
}
