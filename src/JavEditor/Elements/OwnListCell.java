/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor.Elements;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Usuario
 */
public class OwnListCell extends ListCell<String> {
    
    private TextField textfield;
    @Override
    public void setPrefSize(double d, double d1) {
        super.setPrefSize(d, d1); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void startEdit(){
        super.startEdit();
        if(isEditing()){
            if(textfield==null){
                textfield = new TextField(getItem());
            }
        }
        
        textfield.setText(getItem());
        setText(null);
        textfield.selectAll();
    }
    
    @Override
    public void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        if (isEmpty()) {
            setText(null);
        } else {
            if (!isEditing()) {
                if (textfield != null) {
                    Label l = new Label(textfield.getText());
                    l.setFont(Font.font("Times New Roman",11.35));
                    l.setStyle("-fx-text-fill:white;");
                    setText(null);
                    setGraphic(l);
                } else {
                    setText(null);
                    Label l = new Label(item);
                    l.setFont(Font.font("Times New Roman",11.35));
                    l.setStyle("-fx-text-fill:white;");
                    setText(null);
                    setGraphic(l);
                }
            }
        }
    }
    
}
