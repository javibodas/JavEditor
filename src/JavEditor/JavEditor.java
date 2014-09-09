/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor;

import JavEditor.Collections.*;
import JavEditor.Elements.*;
import JavEditor.Windows.About;
import JavEditor.HelpClasses.OwnFile;

import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author javier
 */
public class JavEditor extends Application {
  
    private BorderPane root;
    private OwnTreeView tree;
    private MenuBar bar;
    private TabPane tabPane;
    private OwnTab defaultTab;
    private Menu menuFile;
    private Menu menuEdit;
    private Menu menuHelp;
    private Menu menuPreferences;
    private MenuItem open;
    private MenuItem openProject;
    private MenuItem newTab;
    private MenuItem save;
    private MenuItem saveAs;
    private MenuItem closeFile;
    private MenuItem closeAll;
    private MenuItem close;
    private MenuItem undo;
    private MenuItem copy;
    private MenuItem paste;
    private MenuItem cut;
    private MenuItem delete;
    private MenuItem selectAll;
    private MenuItem deselect;
    private MenuItem font;
    private MenuItem about;
    
    //Variables para el correcto funcionamiento del programa
    private OwnTextAreas areas;
    private OwnTabs tabs; 
    private Projects projects;
    private OwnFile file;
     
    private SeparatorMenuItem[] separate = new SeparatorMenuItem[10];     // Separators on MenuItem
    
    //ArrayList
    private ArrayList<KeyCode> keyCodes = new ArrayList<KeyCode>();        // Codes of keys pressed
    
    //Booleans
    private boolean _isForOpen = false;              // If newTab is for open file (true) but (false)
   
    
    //EventHandlers
    private EventHandler<ActionEvent> _fileClose;     // Close selected tab
    private EventHandler<ActionEvent> _allClose;      // Close all tabs
    private EventHandler<ActionEvent> _tabNew;        // Open new tab with an area,scrollbar...
    private EventHandler<ActionEvent> _asSave;        // Open filechooser and save file
    private EventHandler<ActionEvent> _openFile;      // Open filechooser and open file
    private EventHandler<ActionEvent> _saveFile;      // Save an exists file                                                 
    private EventHandler<ActionEvent> _projectOpen;   // Open filechooser and open a directory

    @Override
    public void start(Stage primaryStage) {
            
        tabPane = new TabPane();
        tree = new OwnTreeView(tabPane, new OwnTreeItem());
        tabs = new OwnTabs(tree,tabPane);
        areas = new OwnTextAreas(tree,tabPane);
        projects = new Projects();
        file = new OwnFile(areas,tabs,tree,projects);
        areas.setTabs(tabs);
        tabs.setAreas(areas);
        tree.setAreas(areas);
        tree.setTabs(tabs);

        //Creo un array de separadores de menuitems
        for (int i = 0; i < separate.length; i++) {
            separate[i] = new SeparatorMenuItem();
        }

        Point3D punto = new Point3D(0, 0, 1);

        //Panel que contiene todos los demás contenidos
        root = new BorderPane();
        root.autosize();
        root.setMaxHeight(BorderPane.USE_COMPUTED_SIZE);
        root.setMaxWidth(BorderPane.USE_COMPUTED_SIZE);
        root.setMinHeight(BorderPane.USE_COMPUTED_SIZE);
        root.setMinWidth(BorderPane.USE_COMPUTED_SIZE);
        root.setPrefHeight(BorderPane.USE_COMPUTED_SIZE);
        root.setPrefWidth(BorderPane.USE_COMPUTED_SIZE);
        root.setScaleX(1);
        root.setScaleY(1);
        root.setScaleZ(1);
        root.setRotationAxis(punto);

        //Barra principal con las pestañas de los menús
        bar = new MenuBar();
        bar.autosize();
        bar.setMaxHeight(MenuBar.USE_COMPUTED_SIZE);
        bar.setMaxWidth(MenuBar.USE_COMPUTED_SIZE);
        bar.setMinHeight(24);
        bar.setMinWidth(MenuBar.USE_COMPUTED_SIZE);
        bar.setPrefHeight(22);
        bar.setPrefWidth(1024);
        bar.setScaleX(1);
        bar.setScaleY(1);
        bar.setScaleZ(1);
        bar.setRotationAxis(punto);

        //Menús
        menuFile = new Menu("Archivo");

        //MenuItems del menú "Archivo"
        open = new MenuItem("Abrir                                            Ctrl+O");
        open.getStyleClass().add("menuitem");
        /**
         * Abre un archivo en un nuevo tab que crea y escribe el texto de dicho
         * archivo en el area correspondiente al tab seleccionado, que es el tab
         * creado. ATAJO : CTRL+0
         */
        open.setOnAction(_openFile = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                file.open();
            }
        });
        
        /*
         * Abre un proyecto seleccionado,con todas sus subcarpetas
         * y archivos llamando a la función abrirProyecto(explicada mas abajo)
         */
        openProject = new MenuItem("Abrir proyecto");
        openProject.getStyleClass().add("menuitem");
        openProject.setOnAction(_projectOpen = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                file.openProject();
            }
        });
        /**
         * Guarda un archivo, en el caso de que el archivo haya sido abierto y
         * ya existiese, se guardará en la dirección que le corresponda, en el
         * caso de que no existiese se llamará a la función guardarComo
         * guardandose en la dirección que se le indique. ATAJO : CTRL + S
         */
        save = new MenuItem("Guardar                                       Ctrl+S");
        save.getStyleClass().add("menuitem");
        save.setOnAction(_saveFile = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                file.save();
            }
        });

        /**
         * Guarda un archivo en una dirección determinada, ya estuviese o no
         * creado el archivo, al realizar el guardado con otro nombre
         * actualizara el nombre del tab y el nombre que aparezca en el
         * TreeView.
         */
        saveAs = new MenuItem("Guardar Como...");
        saveAs.getStyleClass().add("menuitem");
        saveAs.setOnAction(_asSave = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                file.saveAs();
            }
        });

        /**
         * Cierra el programa JavEditor
         */
        close = new MenuItem("Cerrar");
        close.getStyleClass().add("menuitem");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });

        /**
         * Abre una nueva pestaña para crear un nuevo archivo. ATAJO : CTRL+N
         */
        newTab = new MenuItem("Nuevo Archivo                            Ctrl+N");
        newTab.getStyleClass().add("menuitem");
        newTab.setOnAction(_tabNew = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                tabs.newTab(_isForOpen);
                    }
        });
                

        /*
         * Cierra el tab que se encuentra seleccionado. Buscamos el tab seleccionado, y cuando lo encontramos
         * borramos del TreeView el nombre del archivo correspondiente con actualizarArbol, y eliminamos
         * la posición creada para la dirección del archivo, el area y el tab de las listas tabs y areas, 
         * a parte de eliminarlo del tabPane ya que al ser un atajo no se realiza por defecto. ATAJO : CTRL+A
         */
        closeFile = new MenuItem("Cerrar Archivo                             Ctrl+T");
        closeFile.getStyleClass().add("menuitem");
        closeFile.setOnAction(_fileClose = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                String s;
                boolean noestab = false;
                OwnTextArea a = areas.seleccionarArea();
                if(areas.isModificated(a)){
                   int n = JOptionPane.showConfirmDialog(null, "Do you want save the file?");
                   if(n==JOptionPane.CANCEL_OPTION){
                       return;
                   }else if(n==JOptionPane.YES_OPTION){
                       _asSave.handle(null);
                   }
                }
                for(int i=0;i<tabs.size();i++){
                    if(!tabs.get(i).isSelected()){
                        continue;
                    }else{
                        tree.getItems().getChildren().remove(tabs.get(i).getTreeItem());
                        tabPane.getTabs().remove(tabs.get(i));
                        areas.remove(a);
                        tabs.remove(tabs.get(i));
                        break;
                    }
                }
            }
        });

        /*
         * Cierra todos los tabs abiertos del programa, recorre todos los tabs del tabPane y los va eleminando
         * de la lista uno por uno, ademas, se borran las correspondientes direcciones de archivos guardadas y
         * las areas creadas para los tabs.Además se van borrando los nombres del TreeView
         */
        closeAll = new MenuItem("Cerrar Todo                                 Ctrl+Q");
        closeAll.getStyleClass().add("menuitem");
        closeAll.setOnAction(_allClose = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                int longitudTabPane = tabPane.getTabs().size();
                for (int i = 0; i < longitudTabPane; i++) {
                    OwnTextArea a = areas.seleccionarArea();
                    if(areas.isModificated(a)){
                      int n = JOptionPane.showConfirmDialog(null, "Do you want save the file?");
                      if(n==JOptionPane.CANCEL_OPTION){
                         return;
                      }else if(n==JOptionPane.YES_OPTION){
                         _asSave.handle(null);
                      }
                    }
                    if (tree.getItems().getChildren().size() > 0) {
                        tree.getItems().getChildren().remove(0);
                    }
                    tabPane.getTabs().remove(0);
                }
                for (int i = 0; i < tabs.size(); i++) {
                    tabs.remove(0);
                    areas.remove(0);
                }
            }
        });
        menuFile.getItems().addAll(newTab, separate[0], open, openProject, save, saveAs, separate[1], closeFile, closeAll, separate[2], close);
        menuFile.getStyleClass().add("menu");
        menuEdit = new Menu("Edición");

        //Submenús de "Edición"
        undo = new MenuItem("Deshacer                                          Ctrl+Z");
        /*
         *  Deshace la acción previa hecha
         */
        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                OwnTextArea a = areas.seleccionarArea();
                a.undo();
            }
        });

        selectAll = new MenuItem("Seleccionar Todo                              Ctrl+A");

        /*
         *  Selecciona todo el texto del area del tab en el que se encuentra en el momento
         */
        selectAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                OwnTextArea a = areas.seleccionarArea();
                a.selectAll();
            }
        });

        deselect = new MenuItem("Deseleccionar");
        /*
         *  Deselecciona lo seleccionado previamente
         */
        deselect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                OwnTextArea a = areas.seleccionarArea();
                a.deselect();
            }
        });

        cut = new MenuItem("Cortar                                               Ctrl+X");
        /*
         *  Corta lo seleccionado, elimina de la pantalla lo seleccionado, almacenandolo en la variable seleccion
         * para su posible posterior pegado
         */
        cut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                OwnTextArea a = areas.seleccionarArea();
                a.cut();
            }
        });

        copy = new MenuItem("Copiar                                              Ctrl+C");
        /**
         * Copia lo seleccionado, almacena dicha selección en la variable
         * seleccion para su posible posterior pegado
         */
        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                OwnTextArea a = areas.seleccionarArea();
                a.copy();
            }
        });

        paste = new MenuItem("Pegar                                                Ctrl+V");
        /*
         * Añade al texto del area del tab seleccionado lo que se encuentra en la variable selección.
         */
        paste.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                OwnTextArea a = areas.seleccionarArea();
                a.paste();   
            }
        });

        delete = new MenuItem("Eliminar");
        /*
         *  Elimina lo seleccionado, a diferencia de cortar, este no guarda el texto en ninguna variable.
         */
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                OwnTextArea a = areas.seleccionarArea();
                a.delete();
            }
        });

        menuEdit.getItems().addAll(undo, separate[3], copy, cut, paste, delete, separate[4], selectAll,deselect);
        menuHelp = new Menu("Ayuda");

        //Submenús de "Ayuda"
        about = new MenuItem("Sobre JavEditor");

        /*
         * Abre una nueva ventana donde se da información de JavEditor
         */
        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Stage stage = new Stage();
                About sobre = new About();
                sobre.start(stage);
            }
        });

        menuHelp.getItems().addAll(about);
        menuPreferences = new Menu("Preferencias");

        //SubMenús de "Preferencias"
        font = new MenuItem("Fuente");
        menuPreferences.getItems().addAll(font);
        bar.getMenus().addAll(menuFile, menuEdit, menuPreferences, menuHelp);

        //TabPane
        
        tabPane.getStyleClass().add("tabpane");
        tabPane.autosize();
        tabPane.setLayoutX(0);
        tabPane.setLayoutY(24);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabPane.setMinHeight(TabPane.USE_COMPUTED_SIZE);
        tabPane.setMinWidth(TabPane.USE_COMPUTED_SIZE);
        tabPane.setPrefHeight(378);
        tabPane.setPrefWidth(600);
        tabPane.setScaleX(1);
        tabPane.setScaleY(1);
        tabPane.setScaleZ(1);
        tabPane.setRotationAxis(punto);
        tabPane.setPrefWidth(1024);

        //TreeView
        
        tree.createChilds();
        tree.setPrefHeight(20);
        tree.getStyleClass().add("tree-view");
        
        
        //Si una tecla es pulsada se almacena el codigo en la lista codigoTeclas y si hay mas de 1
        //se realiza la comprobacion de las teclas pulsadas.
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keyCodes.add(event.getCode());
                if (keyCodes.size() > 1) {
                    comprobarTeclas();
                }
            }
        });
        //Si alguna tecla se suelta deja de ser pulsada se elimina de la lista
        root.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keyCodes.remove(event.getCode());
            }
        });

        //El panel de tabs y la barra de herramientas se añaden al panel root
        root.setLeft(tree);
        root.setTop(bar);
        root.setCenter(tabPane);
       
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/JavEditor/JavEditor.css");
        Image imagen = new Image("JavEditor/Images/Icono.gif", 10, 10, false, false);
        primaryStage.getIcons().add(imagen);
        primaryStage.setTitle("JavEditor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * comprobarTeclas()
     *
     * Función creada para el manejo de las teclas pulsadas, las teclas que se
     * pulsan y no se sueltan se almacenan en una lista, cuando esa lista tiene
     * mas de un elemento llamamos a esta funcion que comprueba las teclas
     * pulsadas, si la primera es la tecla CTRL pasa a comprobar la segunda y si
     * es alguna de las utilizadas para atajos realiza la operación
     * correspondiente, si no, elimina las teclas guardadas de la lista.
     */
    private void comprobarTeclas() {
        if (keyCodes.get(0).equals(KeyCode.CONTROL)) {
            if (keyCodes.get(1) == KeyCode.N) {
                _tabNew.handle(null);
            } else if (keyCodes.get(1) == KeyCode.Z) {
                OwnTextArea a = areas.seleccionarArea();
                a.undo();
            } else if (keyCodes.get(1) == KeyCode.X) {
                OwnTextArea a = areas.seleccionarArea();
                a.cut();
            } else if (keyCodes.get(1) == KeyCode.C) {
                OwnTextArea a = areas.seleccionarArea();
                a.copy();
            } else if (keyCodes.get(1) == KeyCode.Q) {
                _allClose.handle(null);
            } else if (keyCodes.get(1) == KeyCode.V) {
                OwnTextArea a = areas.seleccionarArea();
                a.paste();
            } else if (keyCodes.get(1) == KeyCode.T) {
                _fileClose.handle(null);
            } else if (keyCodes.get(1) == KeyCode.O) {
                _openFile.handle(null);
            } else if (keyCodes.get(1) == KeyCode.S) {
                _saveFile.handle(null);
            } else if (keyCodes.get(1) == KeyCode.P) {
                _projectOpen.handle(null);
            }
        }
        keyCodes.remove(0);
        keyCodes.remove(0);

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }
}