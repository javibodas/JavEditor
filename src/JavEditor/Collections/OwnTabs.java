/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor.Collections;

import JavEditor.Elements.OwnListCell;
import JavEditor.Elements.OwnTab;
import JavEditor.Elements.OwnTextArea;
import JavEditor.Elements.OwnTreeItem;
import JavEditor.Elements.OwnTreeView;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author javier
 */
public class OwnTabs extends ArrayList<OwnTab>{
    
    private OwnTextAreas areas;
    private TabPane tabPane;
    private OwnTreeView tree;
    
    public OwnTabs(OwnTreeView tree,TabPane tabPane){
        super();
        this.tree = tree;
        this.tabPane = tabPane;
    
    }
    
    public void newTab(boolean _isForOpen){
        //Crea un nuevo area de texto para incluirlo en la nueva pestaña
                OwnTextArea areaNueva = new OwnTextArea();
                areaNueva.setWrapText(true);
                areaNueva.setAreas(areas);
                areaNueva.setPrefHeight(690);
                areaNueva.setPrefWidth(1024);
                areaNueva.setMinHeight(TextArea.USE_COMPUTED_SIZE);
                areaNueva.setMaxHeight(TextArea.USE_COMPUTED_SIZE);
                areaNueva.setMaxWidth(TextArea.USE_COMPUTED_SIZE);
                areaNueva.setMinWidth(TextArea.USE_COMPUTED_SIZE);
                areaNueva.getStyleClass().add("textArea");
                areas.add(areaNueva);
                
                 ScrollPane scrll = new ScrollPane();
                ScrollBar bar = new ScrollBar();
                bar.setOrientation(Orientation.VERTICAL);
                bar.setPrefHeight(680);
                /*Incluyo en el borderpane a la izquierda la lista de numeros y en el centro nada*/
                BorderPane bor = new BorderPane();
                /*VBox para poder darle un padding al lista de numeros*/
                VBox v = new VBox();
                ObservableList<String> tf = FXCollections.observableArrayList();
                ListView<String> l = new ListView<String>(); 
                l.setCellFactory(new Callback<ListView<String>, 
                ListCell<String>>() {
                    @Override 
                    public ListCell<String> call(ListView<String> list) {
                         OwnListCell c = new OwnListCell();
                         c.setPrefSize(20,17.2);
                         return c;
                    }
                  }
                );
                l.setItems(tf);
                l.setPrefWidth(50);
                l.setPrefHeight(688);
                l.getStyleClass().add("listview");
                tf.add(0,"1");
                v.getChildren().add(l);
                v.setPadding(new Insets(3,-1,0,0));
                v.getStyleClass().add("vox");
                bor.setLeft(v);
                bor.setCenter(areaNueva);
                bor.setPadding(new Insets(-1,-1,-1,-1));
                scrll.setContent(bar);
                scrll.setContent(bor);
                
                OwnTab tabNuevo = new OwnTab("Sin Titulo", areaNueva,l);
                
                //Añadimos el nuevo tab a la lista de tabs y le añadimos las caracteristicas

                this.add(tabNuevo);
                tabNuevo.setClosable(true);
                tabNuevo.setContent(scrll);
                tabNuevo.selectedProperty();
                tabNuevo.setAreas(areas);
                tabNuevo.setTabs(this);
                
                //Preseleccionamos el nuevo tab creado
                tabPane.getTabs().add(tabNuevo);
                tabPane.getSelectionModel().select(tabNuevo);
                areaNueva.setLines(tf);
                areaNueva.setTab(tabNuevo);

                //Si abrimos una nueva pestaña como nuevo archivo 
                if (!_isForOpen) {
                    OwnTreeItem item = new OwnTreeItem("Sin Titulo",new ImageView(new Image("JavEditor/Images/Texto.gif")));
                    tabNuevo.setTreeItem(item);
                    item.setTab(tabNuevo);
                    item.setFather(tree.getItems());
                    tree.getItems().getChildren().add(item);
                }
            
    }
    public void setAreas(OwnTextAreas areas){this.areas = areas;}
}
