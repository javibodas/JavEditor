/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor.Windows;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Javier González Bodas
 */
public class About extends Application {
    
    private TextArea area;
    
    @Override
    public void start(Stage primaryStage) {
        
        
        Image imagen = new Image("JavEditor/Images/Picture.jpg",600,550,false,false);
        ImageView img = new ImageView(imagen);
        StackPane root = new StackPane();
        root.getChildren().add(img);
        root.setMinSize(600, 550);
        root.setMaxSize(600,550);
        root.setDisable(true);
        Scene scene = new Scene(root, 590, 540);
        primaryStage.setTitle("Sobre JavEditor");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
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
