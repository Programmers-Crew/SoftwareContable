package org.appcontablealas.bean;


import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class CambioScene {    
    //método donde cambia de una escena a otra.
    public void Cambio(String url, Stage primaryStage) throws IOException{
        
      
       
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(url));
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
       
        
        primaryStage.show();
        
        
    }
}