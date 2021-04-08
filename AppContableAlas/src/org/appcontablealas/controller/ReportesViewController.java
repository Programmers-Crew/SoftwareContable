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


public class ReportesViewController implements Initializable {

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
    
        
    //Reporte Ventas
    @FXML
    private Pane cajaReporteVentas;
    @FXML
    private JFXDatePicker txtFechaInicioRV;
    @FXML
    private Button btnReporteVentas;
    @FXML
    private JFXDatePicker txtFechaFinalRV;
    
    //Formulario
    @FXML
    private Pane cajaFormulario;
    @FXML
    private Button btnFormulario;
    
    //Creditos
    @FXML
    private Pane cajaCreditos;
    @FXML
    private JFXDatePicker txtFechaInicioC;
    @FXML
    private Button btnCreditos;
    @FXML
    private JFXDatePicker txtFechaFinalC;
    @FXML
    private JFXComboBox<String> cmbEmpresaCreditos;


    // IMAGENES
    Image imgError = new Image("org/appcontablealas/img/error.png");
    Image imgCorrecto= new Image("org/appcontablealas/img/correcto.png");
    
    CambioScene cambioScene = new CambioScene();


    //LISTAS
    ObservableList<String> listaEmpresas;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbEmpresaCreditos.setEditable(true);
        cmbEmpresaCreditos.setDisable(false);
        llenarComboEmpresa();
        animaciones();
        
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
   
    //cierreDeCaja
    public void cierreDeCaja() throws IOException{
        String inventarioUrl = "org/appcontablealas/view/cierreCajaView.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
    private void cierreDeCajaView(ActionEvent event) throws IOException {
        cierreDeCaja();
    }
           
    @FXML
    private void cierreDeCajaAtajo(MouseEvent event) throws IOException {
        cierreDeCaja();
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
        listaEmpresas = FXCollections.observableList(lista);
        cmbEmpresaCreditos.setItems(listaEmpresas);
        new AutoCompleteComboBoxListener(cmbEmpresaCreditos);
    }
    
    //REPORTE VENTAS
            public void imprimirReporteVentas(){
            if(txtFechaInicioRV.equals("")){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("Debe seleccionar una fecha de inicio");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }else if(txtFechaFinalRV.equals("")){
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

                 String fechaInicio = txtFechaInicioRV.getValue().toString();
                 String fechaFinal = txtFechaFinalRV.getValue().toString();

                 System.out.println(parametros);                
                 
                parametros.put("fechaInicio", "'"+fechaInicio+"'");
                parametros.put("fechaFinal", "'"+fechaFinal+"'");


                 GenerarReporte.mostrarReporte("CierreDeCaja.jasper", "REPORTE VENTAS", parametros);

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
    private void generarReporteVentas(MouseEvent event) {
        imprimirReporteVentas();
    } 
    
    //FORMULARIO
    public void imprimirFormulario(){
       try{
        Map parametros = new HashMap();

         String relleno = "";

         System.out.println(parametros);                

        parametros.put("relleno", "'"+relleno+"'");


         GenerarReporte.mostrarReporte("FormularioMensajero.jasper", "FORMULARIO", parametros);

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

    @FXML
    private void generarFormulario(MouseEvent event) {
        imprimirFormulario();
    }
    
    //CREDITOS
    public void imprimirCreditos(){
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
            }else if(cmbEmpresaCreditos.equals("")){
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

                     String fechaInicio = txtFechaInicioC.getValue().toString();
                     String fechaFinal = txtFechaFinalC.getValue().toString();
                     String empresa = cmbEmpresaCreditos.getValue();

                    parametros.put("fechaInicio", "'"+fechaInicio+"'");
                    parametros.put("fechaFinal", "'"+fechaFinal+"'");
                    parametros.put("empresa", "'"+empresa+"'");


                     GenerarReporte.mostrarReporte("ReporteCreditos.jasper", "REPORTE CREDITOS", parametros);
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
    private void generarCreditos(MouseEvent event) {
        imprimirCreditos();
    }
    
    //Animaciones
    public void animaciones(){
       //Caja reporte ventas
       FadeTransition ftReporteVentas = new FadeTransition();
       ftReporteVentas.setFromValue(0);
       ftReporteVentas.setToValue(1);
       ftReporteVentas.setDuration(Duration.seconds(2));
       ftReporteVentas.setNode(cajaReporteVentas);
       ftReporteVentas.setCycleCount(1);
       ftReporteVentas.play();
       
       
       TranslateTransition ttReporteVentas = new TranslateTransition();
       ttReporteVentas.setFromY(-20);
       ttReporteVentas.setToY(0);
       ttReporteVentas.setDuration(Duration.seconds(2.0));
       ttReporteVentas.setNode(cajaReporteVentas);
       ttReporteVentas.setCycleCount(1);
       ttReporteVentas.play();
       
       //Caja reporte formulario
       FadeTransition ftReporteF = new FadeTransition();
       ftReporteF.setFromValue(0);
       ftReporteF.setToValue(1);
       ftReporteF.setDuration(Duration.seconds(2));
       ftReporteF.setNode(cajaFormulario);
       ftReporteF.setCycleCount(1);
       ftReporteF.play();
       
       
       TranslateTransition ttReporteF = new TranslateTransition();
       ttReporteF.setFromY(-30);
       ttReporteF.setToY(0);
       ttReporteF.setDuration(Duration.seconds(2.0));
       ttReporteF.setNode(cajaFormulario);
       ttReporteF.setCycleCount(1);
       ttReporteF.play();
       
        //Caja reporte creditos
       FadeTransition ftReporteC = new FadeTransition();
       ftReporteC.setFromValue(0);
       ftReporteC.setToValue(1);
       ftReporteC.setDuration(Duration.seconds(2));
       ftReporteC.setNode(cajaCreditos);
       ftReporteC.setCycleCount(1);
       ftReporteC.play();
       
       
       TranslateTransition ttReporteC = new TranslateTransition();
       ttReporteC.setFromY(-30);
       ttReporteC.setToY(0);
       ttReporteC.setDuration(Duration.seconds(2.0));
       ttReporteC.setNode(cajaCreditos);
       ttReporteC.setCycleCount(1);
       ttReporteC.play();
    }
}

