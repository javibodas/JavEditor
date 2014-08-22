/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor.Elements;

import JavEditor.Elements.OwnTab;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

/**
 *
 * @author javier
 */
public class OwnTreeItem extends TreeItem<String>{
    
    private OwnTab tab;
    private OwnTreeItem father;
    private String path;
    
    public OwnTreeItem(){}
    
    public OwnTreeItem(String name){
        super(name);
    }
    public OwnTreeItem(String name,Node image){
        super(name,image);
    }
    
    public void setTab(OwnTab tab){this.tab = tab;}
    
    public void setPath(String path){this.path = path;}
    
    public void setFather(OwnTreeItem father){this.father = father;}
    
    public OwnTab getTab(){return this.tab;}
    
    public String getPath(){return this.path;}
    
    public OwnTreeItem getFather(){return this.father;}
}
