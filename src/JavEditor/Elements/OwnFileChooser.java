/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor.Elements;


import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Javier González Bodas
 */
public class OwnFileChooser extends JFileChooser{
    
    public OwnFileChooser(){
        super();
        try{
            //UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            
        }
       
        //Con esto podemos modificar los elementos del filechooser
        UIManager.put("FileChooser.openDialogTitleText", "Abrir");
        UIManager.put("FileChooser.saveDialogTitleText", "Guardar Como...");
        final ArrayList<String> filters = new ArrayList<String>();
        FileFilter txt = new FileFilter() {

                   @Override
                   public boolean accept(File f) {
                       return f.getName().endsWith("txt"); //To change body of generated methods, choose Tools | Templates.
                   }

                   @Override
                   public String getDescription() {
                       String str = "Texto (*.txt)"; 
                       return str;//To change body of generated methods, choose Tools | Templates.
                   }
               };
        filters.add("txt");
        FileFilter javascript = new FileFilter(){
             @Override
                   public boolean accept(File f) {
                       return f.getName().endsWith("js"); //To change body of generated methods, choose Tools | Templates.
                   }

                   @Override
                   public String getDescription() {
                       String str = "JavaScript (*.js)"; 
                       return str;//To change body of generated methods, choose Tools | Templates.
                   }
               
        };
        filters.add("js");
          FileFilter html = new FileFilter(){
             @Override
                   public boolean accept(File f) {
                       return f.getName().endsWith("html"); //To change body of generated methods, choose Tools | Templates.
                   }

                   @Override
                   public String getDescription() {
                       String str = "HTML (*.html)"; 
                       return str;//To change body of generated methods, choose Tools | Templates.
                   }
               
        };
          FileFilter php = new FileFilter(){
             @Override
                   public boolean accept(File f) {
                       return f.getName().endsWith("php"); //To change body of generated methods, choose Tools | Templates.
                   }

                   @Override
                   public String getDescription() {
                       String str = "Php (*.php)"; 
                       return str;//To change body of generated methods, choose Tools | Templates.
                   }
               
        };
          filters.add("php");
          FileFilter css = new FileFilter(){
             @Override
                   public boolean accept(File f) {
                       return f.getName().endsWith("css"); //To change body of generated methods, choose Tools | Templates.
                   }

                   @Override
                   public String getDescription() {
                       String str = "CSS (*.css)"; 
                       return str;//To change body of generated methods, choose Tools | Templates.
                   }
               
        };
          filters.add("css");
          FileFilter xml = new FileFilter(){
             @Override
                   public boolean accept(File f) {
                       return f.getName().endsWith("xml"); //To change body of generated methods, choose Tools | Templates.
                   }

                   @Override
                   public String getDescription() {
                       String str = "Xml (*.xml)"; 
                       return str;//To change body of generated methods, choose Tools | Templates.
                   }
               
        };
          filters.add("xml");
           FileFilter c = new FileFilter(){
             @Override
                   public boolean accept(File f) {
                       return f.getName().endsWith("c"); //To change body of generated methods, choose Tools | Templates.
                   }

                   @Override
                   public String getDescription() {
                       String str = "C (*.c)"; 
                       return str;//To change body of generated methods, choose Tools | Templates.
                   }
               
        };
           filters.add("c");
           FileFilter java = new FileFilter(){
             @Override
                   public boolean accept(File f) {
                       return f.getName().endsWith("java"); //To change body of generated methods, choose Tools | Templates.
                   }

                   @Override
                   public String getDescription() {
                       String str = "Java (*.java)"; 
                       return str;//To change body of generated methods, choose Tools | Templates.
                   }
               
        };
           FileFilter todos = new FileFilter(){

            @Override
            public boolean accept(File f) {
                boolean entra = false;
                if(f.getName().getBytes().length>0){
                    entra = true;
                } //To change body of generated methods, choose Tools | Templates.
                return entra;
            }
            

            @Override
            public String getDescription() {
                String str= "Todos los archivos";
                return str; //To change body of generated methods, choose Tools | Templates.
            }
               
           };
                   
 
        setAcceptAllFileFilterUsed(false);
        addChoosableFileFilter(todos);
        addChoosableFileFilter(txt);
        addChoosableFileFilter(java);
        addChoosableFileFilter(html);
        addChoosableFileFilter(javascript);
        addChoosableFileFilter(php);
        addChoosableFileFilter(css);
        addChoosableFileFilter(c);
        SwingUtilities.updateComponentTreeUI(this);
        
        
    }

    public static void main(String[] args){
        OwnFileChooser h = new OwnFileChooser();
    }
    
    public void setInitialDirectory(File file){
        super.setCurrentDirectory(file);
        
    }
    
    public File ownShowSaveDialog(){
        File f = null;
        int returnval;
        returnval = super.showSaveDialog(this);
        if(returnval==JFileChooser.APPROVE_OPTION){
            f = super.getSelectedFile();  
        }
        return f;
    }
    public File ownShowOpenDialog(){
        File f = null;
        int returnval;
        returnval = super.showOpenDialog(this);
        if(returnval==JFileChooser.APPROVE_OPTION){
          f = super.getSelectedFile();
        }
        
        return f;
        
    }
    
    public File ownShowOpenProjectsDialog(){
         File directory=null;
         int returnval;
         super.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
         returnval = super.showOpenDialog(this);
         if(returnval==JFileChooser.APPROVE_OPTION){
            directory = super.getSelectedFile(); 
         }
          
        
        return directory;
    }
}
