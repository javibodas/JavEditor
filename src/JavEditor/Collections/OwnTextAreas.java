/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor.Collections;

import JavEditor.Collections.OwnTabs;
import JavEditor.Elements.OwnTextArea;
import JavEditor.Elements.OwnTreeView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TabPane;

/**
 *
 * @author javier
 */
public class OwnTextAreas  extends ArrayList<OwnTextArea>{
    
    private OwnTabs tabs;
    private TabPane tabPane;
    private OwnTreeView tree;
    private Map<OwnTextArea, String> modificates = new HashMap<OwnTextArea, String>();
    
    public OwnTextAreas(OwnTreeView tree, TabPane tabPane){
        super();
        this.tree = tree;
        this.tabPane = tabPane;
    }
    
    /**
     * seleccionarArea()
     *
     * Comprueba que tab esta seleccionado de los que es posible crear. Si no
     * esta seleccionado ninguno o no hay, el area que esta seleccionada es el
     * area de la primera ventana, la principal.
     */
    public OwnTextArea seleccionarArea() {
        boolean hay = false;
        OwnTextArea area = new OwnTextArea();
        if (tabs.size() > 0) {
            for (int i = 0; i < tabs.size(); i++) {
                if (tabs.get(i).isSelected()) {
                    area = tabs.get(i).getArea();
                    hay = true;
                    break;
                }
            }
        }
        if (!hay) {
            area = null;
        }
        return area;
    }
    
    public boolean isModificated(OwnTextArea area){
        boolean control = false;
        if(!modificates.containsKey(area)){
            modificates.put(area, "True");
            control = true;
        }
        
        return control;
    }
    
    public void setTabs(OwnTabs tabs){this.tabs = tabs;}
}
