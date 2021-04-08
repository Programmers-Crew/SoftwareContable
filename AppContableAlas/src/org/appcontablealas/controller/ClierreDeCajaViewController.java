
package org.appcontablealas.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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


public class ClierreDeCajaViewController implements Initializable {

        CambioScene cambioScene = new CambioScene();
    
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
    
    //CIERRE DE CAJA
    @FXML
    private Pane cajaCierreDeCaja;
    @FXML
    private JFXDatePicker txtFechaInicioC;
    @FXML
    private Button btnCierreDeCaja;
    @FXML
    private JFXDatePicker txtFechaFinalC;
    
    
    //CIERRE DE CAJA CLIENTE
    @FXML
    private Pane cajaCierreDeCajaC;
    @FXML
    private JFXDatePicker txtFechaInicioCC;
    @FXML
    private Button btnCierreDeCajaC;
    @FXML
    private JFXDatePicker txtFechaFinalCC;

    //CIERRE DE CAJA EMPRESA
    @FXML
    private Pane cajaCierreDeCajaE;
    @FXML
    private JFXDatePicker txtFechaInicioCE;
    @FXML
    private Button btnCierreDeCajaE;
    @FXML
    private JFXDatePicker txtFechaFinalCE;

    //CIERRE DE CAJA MENSAJERO
    @FXML
    private Pane cajaCierreDeCajaM;
    @FXML
    private JFXDatePicker txtFechaInicioCM;
    @FXML
    private Button btnCierreDeCajaM;
    @FXML
    private JFXDatePicker txtFechaFinalCM;
    
    // COMBO BOX
    @FXML
    private JFXComboBox<String> cmbCliente;
    @FXML
    private JFXComboBox<String> cmbEmpresa;
    @FXML
    private JFXComboBox<String> cmbMensajero;


    //LISTAS
    ObservableList<String> listaClientes;
    ObservableList<String> listaEmpresa;
    ObservableList<String> listaMensajero;
    
    // IMAGENES
    Image imgError = new Image("org/appcontablealas/img/error.png");
    Image imgCorrecto= new Image("org/appcontablealas/img/correcto.png");
    
    public void ActivarCombo(){
        cmbCliente.setDisable(false);
        cmbEmpresa.setDisable(false);
        cmbMensajero.setDisable(false);
        
        cmbCliente.setEditable(true);
        cmbEmpresa.setEditable(true);
        cmbMensajero.setEditable(true);
    }
    
    public void vaciarText(){
        cmbCliente.setValue(" ");
        cmbEmpresa.setValue(" ");
        cmbMensajero.setValue(" ");
    
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ActivarCombo();
        llenarComboMensajero();
        llenarComboClientes();
        llenarComboEmpresa();
        animacion();

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
       String sql= "{call Sp_ListarClientes()}";
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
        
    public void llenarComboEmpresa(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call SpListarEmpresa()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("empresaDesc"));
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
        listaEmpresa = FXCollections.observableList(lista);
        cmbEmpresa.setItems(listaEmpresa);
        new AutoCompleteComboBoxListener(cmbEmpresa);
    }
    
        public void llenarComboMensajero(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call SpListarMensajeros()}";
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
        listaMensajero = FXCollections.observableList(lista);
        cmbMensajero.setItems(listaMensajero);
        new AutoCompleteComboBoxListener(cmbMensajero);
    }
    
    //CIERRE DE CAJA
        public void imprimirCierreDeCaja(){
            if(txtFechaInicioC.equals("")){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("Debe seleccionar una fecha de inicio");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }else if(txtFechaFinalC.equals("")){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("Debe seleccionar una fecha final");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }else{
                
               try{
                Map parametros = new HashMap();

                 String fechaInicio = txtFechaInicioC.getValue().toString();
                 String fechaFinal = txtFechaFinalC.getValue().toString();

                 System.out.println(parametros);                
                 
                parametros.put("fechaInicio", "'"+fechaInicio+"'");
                parametros.put("fechaFinal", "'"+fechaFinal+"'");


                 GenerarReporte.mostrarReporte("ReporteVentas.jasper", "CIERRE DE CAJA", parametros);

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
    private void generarReporteCierreDeCaja(MouseEvent event) {
        imprimirCierreDeCaja();
        
    } 
        
        
            
    //CIERRE DE CAJA CLIENTE
       public void imprimirCierreDeCajaC(){
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


                     GenerarReporte.mostrarReporte("CierreCajaMensajeroC.jasper", "CIERRE DE CAJA (CLIENTE)", parametros);
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
    private void generarReporteCierreDeCajaC(MouseEvent event) {
        imprimirCierreDeCajaC();
    }
    
    //CIERRE DE CAJA EMPRESA
       public void imprimirCierreDeCajaE(){
            if(txtFechaInicioCE.equals("")){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("Debe seleccionar una fecha de inicio");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }else if(txtFechaFinalCE.equals("")){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("Debe seleccionar una fecha final");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }else if(cmbEmpresa.equals("")){
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

                     String fechaInicio = txtFechaInicioCE.getValue().toString();
                     String fechaFinal = txtFechaFinalCE.getValue().toString();
                     String empresa = cmbEmpresa.getValue();

                     System.out.println(parametros);                

                    parametros.put("fechaInicio", "'"+fechaInicio+"'");
                    parametros.put("fechaFinal", "'"+fechaFinal+"'");
                    parametros.put("empresa", "'"+empresa+"'");


                     GenerarReporte.mostrarReporte("CierreCajaMensajeroE.jasper", "CIERRE DE CAJA (EMPRESA)", parametros);
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
    private void generarReporteCierreDeCajaE(MouseEvent event) {
        imprimirCierreDeCajaE();
    }
    
    //CIERRE DE CAJA MENSAJERO
           public void imprimirCierreDeCajaM(){
            if(txtFechaInicioCM.equals("")){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("Debe seleccionar una fecha de inicio");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }else if(txtFechaFinalCM.equals("")){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("Debe seleccionar una fecha final");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }else if(cmbMensajero.equals("")){
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

                     String fechaInicio = txtFechaInicioCM.getValue().toString();
                     String fechaFinal = txtFechaFinalCM.getValue().toString();
                     String mensajero = cmbMensajero.getValue();


                    parametros.put("fechaInicio", "'"+fechaInicio+"'");
                    parametros.put("fechaFinal", "'"+fechaFinal+"'");
                    parametros.put("mensajero", "'"+mensajero+"'");
                     System.out.println(parametros);                


                     GenerarReporte.mostrarReporte("CierreCajaMensajero.jasper", "CIERRE DE CAJA (MENSAJERO)", parametros);
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
    private void generarReporteCierreDeCajaM(MouseEvent event) {
        imprimirCierreDeCajaM();
    }
    
    //animaciones
    public void animacion(){
       //Caja cierre de caja
       FadeTransition ftCierreDeCaja = new FadeTransition();
       ftCierreDeCaja.setFromValue(0);
       ftCierreDeCaja.setToValue(1);
       ftCierreDeCaja.setDuration(Duration.seconds(2));
       ftCierreDeCaja.setNode(cajaCierreDeCaja);
       ftCierreDeCaja.setCycleCount(1);
       ftCierreDeCaja.play();
       
       
       TranslateTransition ttCierreDeCaja = new TranslateTransition();
       ttCierreDeCaja.setFromY(-20);
       ttCierreDeCaja.setToY(0);
       ttCierreDeCaja.setDuration(Duration.seconds(2.0));
       ttCierreDeCaja.setNode(cajaCierreDeCaja);
       ttCierreDeCaja.setCycleCount(1);
       ttCierreDeCaja.play();
    
       //Caja cierre de caja cliente
       FadeTransition ftCierreDeCajaC = new FadeTransition();
       ftCierreDeCajaC.setFromValue(0);
       ftCierreDeCajaC.setToValue(1);
       ftCierreDeCajaC.setDuration(Duration.seconds(2));
       ftCierreDeCajaC.setNode(cajaCierreDeCajaC);
       ftCierreDeCajaC.setCycleCount(1);
       ftCierreDeCajaC.play();
       
       
       TranslateTransition ttCierreDeCajaC = new TranslateTransition();
       ttCierreDeCajaC.setFromY(-30);
       ttCierreDeCajaC.setToY(0);
       ttCierreDeCajaC.setDuration(Duration.seconds(2.0));
       ttCierreDeCajaC.setNode(cajaCierreDeCajaC);
       ttCierreDeCajaC.setCycleCount(1);
       ttCierreDeCajaC.play();  
       
       //Caja cierre de caja empresa
       FadeTransition ftCierreDeCajaEE = new FadeTransition();
       ftCierreDeCajaEE.setFromValue(0);
       ftCierreDeCajaEE.setToValue(1);
       ftCierreDeCajaEE.setDuration(Duration.seconds(2));
       ftCierreDeCajaEE.setNode(cajaCierreDeCajaE);
       ftCierreDeCajaEE.setCycleCount(1);
       ftCierreDeCajaEE.play();
       
       
       TranslateTransition ttCierreDeCajaEE = new TranslateTransition();
       ttCierreDeCajaEE.setFromY(-40);
       ttCierreDeCajaEE.setToY(0);
       ttCierreDeCajaEE.setDuration(Duration.seconds(2.0));
       ttCierreDeCajaEE.setNode(cajaCierreDeCajaE);
       ttCierreDeCajaEE.setCycleCount(1);
       ttCierreDeCajaEE.play();  
       
              //Caja cierre de caja empresa
       FadeTransition ftCierreDeCajaM = new FadeTransition();
       ftCierreDeCajaM.setFromValue(0);
       ftCierreDeCajaM.setToValue(1);
       ftCierreDeCajaM.setDuration(Duration.seconds(2));
       ftCierreDeCajaM.setNode(cajaCierreDeCajaM);
       ftCierreDeCajaM.setCycleCount(1);
       ftCierreDeCajaM.play();
       
       
       TranslateTransition ttCierreDeCajaM = new TranslateTransition();
       ttCierreDeCajaM.setFromY(-50);
       ttCierreDeCajaM.setToY(0);
       ttCierreDeCajaM.setDuration(Duration.seconds(2.0));
       ttCierreDeCajaM.setNode(cajaCierreDeCajaM);
       ttCierreDeCajaM.setCycleCount(1);
       ttCierreDeCajaM.play(); 
    }
    
}
