/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor.HelpClasses;

import JavEditor.Elements.OwnFileChooser;
import JavEditor.Elements.OwnTreeView;
import JavEditor.Elements.OwnTreeItem;
import JavEditor.Elements.OwnTextArea;
import JavEditor.Elements.OwnTab;
import JavEditor.Collections.OwnTextAreas;
import JavEditor.Collections.OwnTabs;
import JavEditor.Collections.Projects;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;

/**
 *
 * @author javier
 */
public class OwnFile {
    
    private OwnFileChooser chooser;
    private OwnTextAreas areas;
    private OwnTabs tabs;
    private OwnTreeView tree;
    private Projects projects;
    
    public OwnFile(OwnTextAreas areas,OwnTabs tabs, OwnTreeView tree, Projects projects){
        this.areas = areas;
        this.tabs = tabs;
        this.tree = tree;
        this.projects = projects;
    }
    
    public void open(){
        
        boolean _isForOpen = false;
        chooser = new OwnFileChooser();
        chooser.setInitialDirectory(new File("C:\\Users\\Usuario\\Desktop"));
        File f = chooser.ownShowOpenDialog();
        
        if (f != null)  
            try {
                _isForOpen = true;
                tabs.newTab(_isForOpen);
                OwnTextArea a = areas.seleccionarArea();
                _isForOpen = false;
                        
                        
                //Actualizazo el arraylist pathArchivoActual e incluyo el nombre en el treeview
                //actualizarArchivo(f.getAbsolutePath(), f.getName());
                //Update file in area and title in tab
                a.setFile(f);
                OwnTab ta = a.getTab();
                ta.updateNameTab(f.getName());
                OwnTreeItem item = new OwnTreeItem(f.getName(),new ImageView( new Image("JavEditor/Images/Texto.gif")));
                ta.setTreeItem(item);
                item.setTab(ta);
                item.setFather(tree.getItems());
                tree.getItems().getChildren().add(item);
                       
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);

                String parte = null;
                int i = 1;
                while ((parte = br.readLine()) != null) {
                             a.appendText(parte + "\n");
                   if(i!=1){
                       a.getLines().add(Integer.toString(i));
                   }
                   i++;
                }
                
                if(i>39){
                          ta.getListView().setPrefHeight(17.2*a.getLines().size());
                          a.setPrefHeight(17.2*a.getLines().size());
                }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error: No se ha podido abrir el archivo");
        }
    }
    
    public void openProject(){
        chooser = new OwnFileChooser();
        chooser.setInitialDirectory(new File("C:\\Users\\Usuario\\Desktop"));
        File f = chooser.ownShowOpenProjectsDialog();

        try {
            OwnTreeItem item = new OwnTreeItem(f.getName(),new ImageView( new Image("JavEditor/Images/Carpeta.gif")));
            item.setFather(tree.getProjects());
            tree.getProjects().getChildren().add(item);
            Project pro = new Project(f.getAbsolutePath());
            pro.open(f,item);
            projects.add(pro);

         } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: No se ha podido abrir el proyecto");
         }
    }
    
    public void save(){
        OwnTextArea a = areas.seleccionarArea();
        if (a.getFile() == null) {
            this.saveAs();
        } else {
            String arch = a.getFile().getAbsolutePath();
            File f = new File(arch);
            a.setFile(f);
            OwnTab ta = a.getTab();
            ta.updateNameTab(f.getName());
            try {
                FileWriter fw = new FileWriter(f);
                BufferedWriter bw = new BufferedWriter(fw);
                String texto = a.getText();
                bw.write(texto);
                bw.close();
                fw.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: No se puedo guardar el archivo");
            }
        }
    }

    public void saveAs() {
       chooser = new OwnFileChooser();
       chooser.setInitialDirectory(new File("C:\\Users\\Usuario\\Desktop"));
       File f = chooser.ownShowSaveDialog();
       
       try {
            //Obtengo la extension con la que se quiere guardar el archivo
            //eliminando del FileFilter los caracteres que no son necesarios
            // "Texto (*.txt)" --> .txt
            String[] ext = chooser.getFileFilter().getDescription().split(" ");//Divido el FileFilter en dos por el espacio
            int u = ext[1].lastIndexOf(")");                                   //Obtengo el indice del ultimo ")"
            String extension = ext[1].substring(2, u);                         //Obtengo el susbtring desde el . (indice 2 siempre) hasta el ultimo ")"
                    
            //Update file in area and title in tab
            f = new File(f.getAbsoluteFile() + extension);
            OwnTextArea a = areas.seleccionarArea();
            a.setFile(f);
            OwnTab ta = a.getTab();
            ta.updateNameTab(f.getName());
                           
            tree.updateTreeView(ta);

            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            String texto = a.getText();
            bw.write(texto);
            bw.close();
            fw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: No se puedo guardar el archivo");
        }
    }
}
