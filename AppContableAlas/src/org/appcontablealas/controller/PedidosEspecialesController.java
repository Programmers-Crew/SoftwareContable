
package org.appcontablealas.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.appcontablealas.bean.AutoCompleteComboBoxListener;
import org.appcontablealas.bean.CambioScene;
import org.appcontablealas.db.Conexion;
import org.appcontablealas.report.GenerarReporte;
import org.controlsfx.control.Notifications;

public class PedidosEspecialesController implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private Pane btnInicio;
    @FXML
    private Pane btnPedidos;
    @FXML
    private Pane btnReportes;
    @FXML
    private AnchorPane anchor1;
    @FXML
    private Pane cajaCierreDeCajaC;
    @FXML
    private JFXDatePicker txtFechaInicioCC;
    @FXML
    private Button btnCierreDeCajaC;
    @FXML
    private JFXDatePicker txtFechaFinalCC;
    @FXML
    private JFXComboBox<String> cmbCliente;
    
    CambioScene cambioScene = new CambioScene();
    ObservableList<String> listaClientes;

    
        // IMAGENES
    Image imgError = new Image("org/appcontablealas/img/error.png");
    Image imgCorrecto= new Image("org/appcontablealas/img/correcto.png");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarComboClientes();
    }    

   //pedido
    public void pedido() throws IOException{
        String inventarioUrl = "org/appcontablealas/view/pedidosView.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
    private void pedidoView(ActionEvent event) throws IOException {
        pedido();
    }
           
    @FXML
    private void pedidoAtajo(MouseEvent event) throws IOException {
        pedido();
    }
   
    
    //reportes
    public void reporte() throws IOException{
        String inventarioUrl = "org/appcontablealas/view/reporteView.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
    private void reporteView(ActionEvent event) throws IOException {
        reporte();
    }
        
    @FXML
    private void reporteAtajo(MouseEvent event) throws IOException {
        reporte();
    }
    
    //Menu
    public void menu() throws IOException{
        String inventarioUrl = "org/appcontablealas/view/menuPrincipal.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
    private void menuView(ActionEvent event) throws IOException {
        menu();
    }
    
    @FXML
    private void menuAtajo(MouseEvent event) throws IOException {
        menu();
    }

        public void llenarComboClientes(){
       ArrayList<String> lista = new ArrayList();
       String sql= "{call SpclientePEspeciales()}";
       int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("userName"));
                x++;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS CMB");
            noti.text("Error al cargar la base de datos");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        listaClientes = FXCollections.observableList(lista);
        cmbCliente.setItems(listaClientes);
        new AutoCompleteComboBoxListener(cmbCliente);
    }
        
    public void imprimirPedidosEspeciales(){
            if(txtFechaInicioCC.equals("")){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("Debe seleccionar una fecha de inicio");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }else if(txtFechaFinalCC.equals("")){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("Debe seleccionar una fecha final");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }else if(cmbCliente.equals("")){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("Debe seleccionar un cliente");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }else{
                try{
                    Map parametros = new HashMap();

                     String fechaInicio = txtFechaInicioCC.getValue().toString();
                     String fechaFinal = txtFechaFinalCC.getValue().toString();
                     String cliente = cmbCliente.getValue();

                     System.out.println(parametros);                

                    parametros.put("fechaInicio", "'"+fechaInicio+"'");
                    parametros.put("fechaFinal", "'"+fechaFinal+"'");
                    parametros.put("cliente", "'"+cliente+"'");


                     GenerarReporte.mostrarReporte("PedidosEspeciales.jasper", "PEDIDOS ESPECIALES", parametros);
                }catch(Exception e){
                    e.printStackTrace();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("ERROR AL GENERAR EL REPORTE");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                }
                }


    }
    
    @FXML
    private void generarPedidosEspeciales(MouseEvent event) {
        imprimirPedidosEspeciales();
    }
    
}
