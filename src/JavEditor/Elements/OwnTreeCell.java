/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor.Elements;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.layout.HBox;

/**
 *
 * @author Usuario
 */
public class OwnTreeCell extends TreeCell<String>{
    
     private boolean expandido = false;
     
     public OwnTreeCell(){}
     @Override
    public void updateItem(String str ,boolean bool){
        super.updateItem(str, bool);
        if(isEmpty()){
            setText(null);
        }else{
            setText(null);
            HBox h = new HBox();
            Label l = new Label(str);
            if(super.getTreeItem().getGraphic()!=null){
               h.getChildren().addAll(super.getTreeItem().getGraphic(),l);
            }else{
            h.getChildren().addAll(l);
            }
            setGraphic(h);
        }
    }

     
    
    
}
