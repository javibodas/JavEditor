/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor.Elements;

import JavEditor.Collections.OwnTabs;
import JavEditor.Collections.OwnTextAreas;
import JavEditor.Elements.OwnTab;
import JavEditor.Elements.OwnTreeItem;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import sun.reflect.generics.tree.Tree;

/**
 *
 * @author javier
 */
public class OwnTreeView extends TreeView<String>{
    
    private TabPane tabPane;
    private OwnTreeItem projects;
    private OwnTreeItem items;
    private OwnTreeItem root;
    
    private OwnTextAreas areas;
    private OwnTabs tabs;
    
    public OwnTreeView(TabPane pane, OwnTreeItem root){
        super();
        this.tabPane = pane;
        this.root = root;
        
        //this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        //Se ejecuta cuando se pincha con el ratón en algun elemento del arbol
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                OwnTreeView tree = (OwnTreeView) event.getSource();
                //Si se pincha 1 vez, selecciona el archivo que se ha pinchado, en el caso de que
                //ya este abierto, si no esta abierto,no hace nada
                if (event.getClickCount() == 1) {
                  if(!event.isConsumed()){
                    if (!tree.getSelectionModel().isEmpty()) {
                        OwnTreeItem element = (OwnTreeItem) tree.getSelectionModel().getSelectedItem();
                        if (event.getButton() == MouseButton.PRIMARY) {
                            if (element.isLeaf()) {
                                tabPane.getSelectionModel().select(element.getTab());
                            }
                        }else if(event.getButton() == MouseButton.SECONDARY) {
                            if(element.equals(projects)) {
                                ContextMenu cnt = new ContextMenu();
                                MenuItem nuevo = new MenuItem("Nuevo Archivo");
                                //nuevo.setOnAction(_tabNew);
                                MenuItem proyecto = new MenuItem("Abrir Proyecto");
                                //proyecto.setOnAction(_projectOpen);
                                cnt.getItems().addAll(nuevo, proyecto);
                                cnt.show(tree, event.getScreenX(), event.getScreenY());
                            }else if(element.equals(items)) {
                                ContextMenu cnt = new ContextMenu();
                                MenuItem nuevo = new MenuItem("Nuevo Archivo");
                                //nuevo.setOnAction(_tabNew);
                                MenuItem cerrar = new MenuItem("Cerrar Archivo");
                                //cerrar.setOnAction(_fileClose);
                                MenuItem cerrartodo = new MenuItem("Cerrar todo");
                                //cerrartodo.setOnAction(_allClose);
                                cnt.getItems().addAll(nuevo, cerrar, cerrartodo);
                                cnt.show(tree, event.getScreenX(), event.getScreenY());
                            }else{
                                boolean noesdocumento = false;
                                /*
                                for (int i = 0; i < pathProyectos.size(); i++) {
                                    if (elemento.getValue().compareTo(pathProyectos.get(i)) == 0) {
                                        noesdocumento = true;
                                        ContextMenu cnt = new ContextMenu();
                                        MenuItem abrir = new MenuItem("Abrir");
                                        MenuItem renombrar = new MenuItem("Renombrar");
                                        cnt.getItems().addAll(abrir, renombrar);
                                        cnt.show(tree, event.getScreenX(), event.getScreenY());
                                        break;
                                    }
                                }*/
                                if (!noesdocumento) {
                                    ContextMenu cnt = new ContextMenu();
                                    MenuItem nuevo = new MenuItem("Nuevo Archivo");
                                    MenuItem cerrarproyecto = new MenuItem("Cerrar");
                                    cnt.getItems().addAll(nuevo, cerrarproyecto);
                                    cnt.show(tree, event.getScreenX(), event.getScreenY());
                                }
                            }
                        }
                      }
                    }
                    //Si se pinchan dos veces seguidas
                    //Obtiene el valor del archivo y comprueba si ya esta abierto, en cuyo caso, lo seleccionara
                    //En el caso de que no este abierto, lo abre en una nueva pestaña
                    //para esto sirve el array pathProyectos
                }else if (event.getClickCount() == 2) {
                  if(!event.isConsumed()){
                    if(!tree.getSelectionModel().isEmpty()) {
                        OwnTreeItem element = (OwnTreeItem) tree.getSelectionModel().getSelectedItem();
                        if (element.isLeaf()) {
                            if(element.getTab()!=null){
                                tabPane.getSelectionModel().select(element.getTab());
                            }else{
                                tabs.newTab(true);
                                OwnTextArea a = areas.seleccionarArea();
                                File f = new File(element.getPath());
                                a.getTab().setText(f.getName());
                                OwnTreeItem item = new OwnTreeItem(f.getName(),new ImageView( new Image("JavEditor/Images/Texto.gif")));
                                a.getTab().setTreeItem(item);
                                item.setTab(a.getTab());
                                item.setFather(items);
                                items.getChildren().add(item);
                                        
                                try {
                                    FileReader fr = new FileReader(f);
                                    BufferedReader br = new BufferedReader(fr);
                                    String parte = null;
                                    int contador = 1;
                                    while ((parte = br.readLine()) != null) {
                                        a.appendText(parte + "\n");
                                        if(contador!=1){
                                            a.getLines().add(contador-1,Integer.toString(contador));
                                            if(contador>40){
                                                a.getTab().getListView().setPrefHeight(17.2*a.getLines().size());
                                                a.setMaxHeight(17.2*a.getLines().size());
                                            }
                                        }
                                        contador++;
                                     }
                                 } catch (Exception e) {
                                            JOptionPane.showMessageDialog(null, "Error: No se ha podido abrir el archivo");
                                 }
                                     
                               }
                            }
                        }
                     }
                  }
                event.consume();
            }
        });
        
        this.setCellFactory( new Callback<TreeView<String>,
                TreeCell<String>>(){
                    @Override
                    public TreeCell<String> call(TreeView<String> view){
                        OwnTreeCell cell = new OwnTreeCell();
                        cell.getStyleClass().add("own-tree-cell");
                        return cell;
                    }
                }
         );
    }
    
    public void updateTreeView(OwnTab tab){
        
        OwnTreeItem item = tab.getTreeItem();
        item.setValue(tab.getText());
    }
    
    public void createChilds(){
        
        this.projects = new OwnTreeItem();
        this.items = new OwnTreeItem();
        this.setRoot(this.root);
        
        this.items.setValue(" Open files");
        this.projects.setValue(" Projects");
        this.root.getChildren().add(this.items);
        this.root.getChildren().add(this.projects);
    }
    
    public OwnTreeItem getProjects(){ return this.projects;}
    
    public OwnTreeItem getItems(){ return this.items;}
    
    public OwnTreeItem getRootItem(){ return this.root;}
    
    public void setAreas(OwnTextAreas areas){this.areas = areas;}
    
    public void setTabs(OwnTabs tabs){this.tabs = tabs;}
}
