/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEdit;

import JavEditor.Elements.OwnTreeItem;
import java.io.File;
import java.util.ArrayList;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author javier
 */
public class Project {
    
    private String path;
    private ArrayList<String> pathsFiles;
    
    public Project(String path){
        this.path = path;
        
        pathsFiles = new ArrayList<String>();
    }
    
    
    /**
     * open()
     *
     * @param file
     * @param item
     *
     * Guardo en un array los archivos que contiene el directorio que me pasan
     * por parámetro y voy incluyendo los nombres de los archivos en el TreeItem
     * que me pasa como parametro y que actua como nodo raiz y nombre del
     * proyecto. Además compruebo si los archivos que voy incluyendo en el arbol
     * son o no directorios, en el caso de que lo sean, llamo a la propia
     * funcion pasandole como parametros dicho archivo y el item que he creado
     * para incluirlo en el arbol. Y si el archivo es un fichero lo inclyo en el
     * array de direcciones de proyectos para mas tarde poder abrirlo.
     */
    public void open(File file, OwnTreeItem item){
        File[] list = file.listFiles();
        String[] listNames = file.list();

        for (int i = 0; i < list.length; i++) {
            OwnTreeItem item2 = new OwnTreeItem(list[i].getName()); 
            item.getChildren().add(item2);
            if (list[i].isDirectory()) {
                item2.setGraphic(new ImageView(new Image("CarpetaSecundaria.gif")));
                open(list[i], item2);
            }
            if (list[i].isFile()) {
                item2.setGraphic(new ImageView(new Image("Texto.gif")));
                item2.setPath(list[i].getAbsolutePath());
                pathsFiles.add(list[i].getAbsolutePath());
            }
        }
    }
    
    
    public ArrayList<String> getPathsFiles(){return this.pathsFiles;}
    
}
