package org.appcontablealas.system;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.appcontablealas.controller.MenuPrincipalContoller;
import org.appcontablealas.db.Conexion;

public class MainApp extends Application {
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
   
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Conexion c = new Conexion();
        if(c.getConexion() != null){
            System.out.println("CONEXIO CORRECTA 1");
        }else{
            System.out.println("CONEXION INCORRECTA");
        }        
        Parent root;
        
            root = FXMLLoader.load(getClass().getClassLoader().getResource("org/appcontablealas/view/menuPrincipal.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("ALAS GT");
            stage.getIcons().add(new Image(getClass().getResource("/org/appcontablealas/img/logoAlas.png").toExternalForm()));
            stage.setWidth(1100);
            stage.setHeight(597);
            stage.setMinWidth(1100);
            stage.setMinHeight(597);
            stage.setScene(scene);

            
            stage.show();
    }

     public static void main(String[] args) {
        launch(args);
       

    }
    
}

