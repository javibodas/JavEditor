/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor.Elements;

import JavEditor.Collections.OwnTextAreas;
import JavEditor.Collections.OwnTabs;
import java.io.File;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javax.swing.JOptionPane;

/**
 *
 * @author javier
 */
public class OwnTab extends Tab{
    
    private OwnTextArea area;
    private String archivoUtilizando; // Opened file path and using file 
    private String archivoGuardado;  // Saved file path 
    private ListView<String> list;
    private OwnTreeItem item;
    
    private OwnTabs tabs;
    private OwnTextAreas areas;
    public OwnTab(String title,OwnTextArea area,ListView list){
        
        super(title);
        this.setContent(area);
        this.area = area;
        this.list = list;
        
        this.setOnClosed(new EventHandler<Event>(){
            @Override
            public void handle(Event t){
                
                OwnTab source = (OwnTab) t.getSource();
                areas.remove(source.getArea());
                tabs.remove(source);
                OwnTreeItem item = source.getTreeItem();
                OwnTreeItem father = item.getFather();
                father.getChildren().remove(item);
           }         
       });
    }
    
    
    public OwnTextArea getArea(){return this.area;}
    
    public OwnTreeItem getTreeItem(){return this.item;}
    
    public ListView<String> getListView(){return this.list;}
    
    public void setArea(OwnTextArea area){this.area = area;}
    
    public void setAreas(OwnTextAreas areas){this.areas = areas;}
    
    public void setTabs(OwnTabs tabs){this.tabs = tabs;}
    
    public void setTreeItem(OwnTreeItem item){this.item = item;}
    
    public void updateNameTab(String name){this.setText(name);}
}
