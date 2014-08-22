/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor.Elements;

import JavEditor.Elements.OwnTab;
import JavEditor.Collections.OwnTextAreas;
import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author javier
 */
public class OwnTextArea extends TextArea {
    
    private ObservableList<String> lineas;
    private String seleccion;       // Selected Text
    private OwnTab tab;
    private File file;
    private OwnTextAreas areas;
    
    public OwnTextArea(){
        super();
        //this.lineas = lineas;
        //this.file = file;
        this.textProperty().addListener(new ChangeListener<String>(){
             @Override
             public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue){
              /*
              OwnTextArea area = areas.seleccionarArea();
              if(areas.isModificated(area)){
                   area.getTab().setText(area.getTab().getText()+"*");
              }
             
             */}
            
         });
        
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
             @Override
             public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                        OwnTextArea source = (OwnTextArea) event.getSource();
                        if(source.getLines().size()>20){
                           source.getTab().getListView().setPrefHeight(17.2*source.getLines().size());
                           source.setPrefHeight(17.2*source.getLines().size()+400);
                         }
                         source.getLines().add(source.getLines().size(), Integer.toString(source.getLines().size()+1));
                     }
                 }
          });
    }
    
    
    public void undo(){
        if (!seleccion.isEmpty()) {    
            this.deselect();
        }
    }
    
    @Override
    public void cut(){
        seleccion = this.getSelectedText();
        this.cut();
    }
    
    @Override
    public void copy(){
        seleccion = this.getSelectedText();
    }
    
    @Override
    public void paste(){
        this.appendText(seleccion);
    }
    
    public void delete(){
        this.cut();
    }
    
    
    public void setFile(File file){this.file = file;}
    
    public void setTab(OwnTab tab){this.tab = tab;}
    
    public void setLines(ObservableList<String> lineas){this.lineas = lineas;}
    
    public void setAreas(OwnTextAreas areas){this.areas = areas;}
    
    public void setSeleccion(String seleccion) {this.seleccion = seleccion;}
    
    public ObservableList<String> getLines(){return this.lineas;}
    
    public OwnTab getTab(){return this.tab;}
    
    public String getSeleccion(){ return this.seleccion;}
    
    public File getFile(){ return this.file;}

}
