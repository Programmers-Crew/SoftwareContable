package org.appcontablealas.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.appcontablealas.bean.CambioScene;

public class ReportesViewController implements Initializable {

        CambioScene cambioScene = new CambioScene();

    
    @FXML
    private AnchorPane anchor;
    @FXML
    private Pane btnInicio;
    @FXML
    private Pane btnProveedores;
    @FXML
    private Pane btnProductos;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

            //Menu
    public void menu() throws IOException{
        String inventarioUrl = "org/appcontablealas/view/menuPrincipal.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void menuView(ActionEvent event) throws IOException {
        menu();
    }
    
    @FXML
    private void menuAtajo(MouseEvent event) throws IOException {
        menu();
    }
    
   
    //clientes
    public void cliente() throws IOException{
        String inventarioUrl = "org/appcontablealas/view/clientesView.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void clienteView(ActionEvent event) throws IOException {
        cliente();
    }
           
    @FXML
    private void clienteAtajo(MouseEvent event) throws IOException {
        cliente();
    }
    
    //pedidos
    public void pedidos() throws IOException{
        String inventarioUrl = "org/appcontablealas/view/pedidosView.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void pedidosView(ActionEvent event) throws IOException {
        pedidos();
    }
           
    @FXML
    private void pedidosAtajo(MouseEvent event) throws IOException {
        pedidos();
    }
    
    @FXML
    private void regresar(MouseEvent event) {
    }

    @FXML
    private void btnProveedores(MouseEvent event) {
    }

    @FXML
    private void btnProductos(MouseEvent event) {
    }

    @FXML
    private void cargarProductos(Event event) {
    }
    
    

}
