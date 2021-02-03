package org.appcontablealas.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.appcontablealas.bean.AutoCompleteComboBoxListener;
import org.appcontablealas.bean.CambioScene;
import org.appcontablealas.bean.Pedido;
import org.appcontablealas.db.Conexion;
import org.controlsfx.control.Notifications;


public class PedidosViewController implements Initializable {

    CambioScene cambioScene = new CambioScene();
    @FXML
    private ComboBox<String> cmbEstadoPedidoBusqueda;

    
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public Operacion tipoOperacion= Operacion.NINGUNO;
    public Operacion cancelar = Operacion.NINGUNO;
    Image imgError= new Image("org/appcontablealas/img/error.png");
    Image imgCorrecto= new Image("org/appcontablealas/img/correcto.png");

    private ObservableList<String>listaCmbMensajeros;
    private ObservableList<Pedido>listaPedidos;
    private ObservableList<Integer>listaCmbCodigoPedido;
    private ObservableList<String>listaCmbEstadoPedidoBusqueda;
    private ObservableList<String>listaCmbEstadoPedido;

    
    
    Integer codigoPedidos = 0;
    int codigoPedido = 0;
    
    @FXML
    private AnchorPane anchor;
    @FXML
    private Pane btnInicio;
    @FXML
    private Pane btnClientes;
    @FXML
    private Pane btnReportes;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private AnchorPane anchor1;
    @FXML
    private JFXTextField txtCodigoPedido;
    private JFXTextField txtEstadoPedido;
    @FXML
    private JFXTextField txtCostoPedido;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnFiltrar;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private TableView<Pedido> tblPedido;
    @FXML
    private TableColumn<Pedido, Integer> colCodigoPedido;
    @FXML
    private TableColumn<Pedido, Date> colFechaPedido;
    @FXML
    private TableColumn<Pedido, String> colDireccionPedido;
    @FXML
    private TableColumn<Pedido, String> colClientePedido;
    @FXML
    private TableColumn<Pedido, String> colTelefonoPedido;
    @FXML
    private JFXTextArea colDescPedido;
    @FXML
    private TableColumn<Pedido, String> colMensajeroPedido;
    @FXML
    private TableColumn<Pedido, Double> colMontoPedido;
    @FXML
    private TableColumn<Pedido, Double> colCostoPedido;
    @FXML
    private TableColumn<Pedido, String> colFormaPagoPedido;
    @FXML
    private TableColumn<Pedido, String> colEstadoPedido;
    @FXML
    private TableColumn<Pedido, String> colDireccionFinalPedido;
    @FXML
    private TableColumn<Pedido, String> colReceptorPedido;
    @FXML
    private ComboBox<String> cmbMensajero;
    @FXML
    private ComboBox<Integer> cmbCodigoPedido;
    @FXML
    private ComboBox<String> cmbEstadoPedido;
    
     public void limpiarText(){
        txtCodigoPedido.setText("");
        txtCostoPedido.setText("");
        cmbMensajero.setValue("");
        cmbEstadoPedido.setValue("");
        cmbMensajero.setPromptText("Seleccione un Mensajero");
    }
    
    public void desactivarText(){
        txtCodigoPedido.setEditable(false);
        txtCostoPedido.setEditable(false);
        cmbMensajero.setDisable(true);
        cmbEstadoPedido.setDisable(true);
    }
    
    public void activarText(){
        txtCodigoPedido.setEditable(true);
        txtCostoPedido.setEditable(true);
        cmbMensajero.setDisable(false);
        cmbEstadoPedido.setDisable(false);
    }

    
     public ObservableList<Pedido> getPedido(){
        ArrayList<Pedido> lista = new ArrayList();
        ArrayList<Integer> listaCodigoPedido = new ArrayList();
        String sql = "{call Sp_ListarPedido()}";
        int x=0;
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Pedido(
                        rs.getInt("pedidoId"),
                        rs.getDate("pedidoFecha"),
                        rs.getString("pedidoDireccionInicio"),
                        rs.getString("pedidoDireccionFinal"),
                        rs.getString("cliente"),
                        rs.getString("nombreReceptor"),
                        rs.getString("pedidoTelefonoReceptor"),
                        rs.getString("pedidoDesc"),
                        rs.getString("mensajero"),
                        rs.getDouble("pedidoMonto"),
                        rs.getDouble("pedidoCosto"),
                        rs.getString("formaPagoDesc"),
                        rs.getString("estadoPedidoDesc")
                ));
              listaCodigoPedido.add(x,rs.getInt("pedidoId"));
              x++;
            }
            listaCmbCodigoPedido = FXCollections.observableList(listaCodigoPedido);
            cmbCodigoPedido.setItems(listaCmbCodigoPedido);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Image imgError = new Image("org/appcontablealas/img/error.png");
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS");
            noti.text("Error al cargar la base de datos");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        return listaPedidos = FXCollections.observableList(lista);
    }
     
     
         
    public void llenarComboBoxMensajero(){
        String sql = "{call Sp_ListarMensajero()}";
        int x = 0;
        ArrayList<String>lista= new ArrayList<>();
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x,rs.getString("userName"));
                x++;
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        listaCmbMensajeros = FXCollections.observableList(lista);
        cmbMensajero.setItems(listaCmbMensajeros);
    }
    
             
    public void llenarComboBoxEstadoPedido(){
        String sql = "{call Sp_ListarEstadoPedido()}";
        int x = 0;
        ArrayList<String>lista= new ArrayList<>();
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x,rs.getString("estadoPedidoDesc"));
                x++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        listaCmbEstadoPedido = FXCollections.observableList(lista);
        listaCmbEstadoPedidoBusqueda = FXCollections.observableList(lista);
        cmbEstadoPedidoBusqueda.setItems(listaCmbEstadoPedidoBusqueda);
        cmbEstadoPedido.setItems(listaCmbEstadoPedido);
    }
    
        public void cargarDatos(){
        tblPedido.setItems(getPedido());
        
            colCodigoPedido.setCellValueFactory(new PropertyValueFactory("pedidoId"));
            colFechaPedido.setCellValueFactory(new PropertyValueFactory("pedidoFecha"));
            colReceptorPedido.setCellValueFactory(new PropertyValueFactory("nombreReceptor"));
            colDireccionPedido.setCellValueFactory(new PropertyValueFactory("pedidoDireccionInicio"));
            colDireccionFinalPedido.setCellValueFactory(new PropertyValueFactory("pedidoDireccionFinal"));
            colClientePedido.setCellValueFactory(new PropertyValueFactory("cliente"));
            colTelefonoPedido.setCellValueFactory(new PropertyValueFactory("pedidoTelefonoReceptor"));
            colMensajeroPedido.setCellValueFactory(new PropertyValueFactory("mensajero"));
            colMontoPedido.setCellValueFactory(new PropertyValueFactory("pedidoMonto"));
            colCostoPedido.setCellValueFactory(new PropertyValueFactory("pedidoCosto"));
            colFormaPagoPedido.setCellValueFactory(new PropertyValueFactory("formaPagoDesc"));
            colEstadoPedido.setCellValueFactory(new PropertyValueFactory("estadoPedidoDesc"));
            
            new AutoCompleteComboBoxListener<>(cmbMensajero);
            desactivarText();
            llenarComboBoxMensajero();
            llenarComboBoxEstadoPedido();
    }
        
        
    public ObservableList<Pedido> getPedidoBuscadoPorId(){
        ArrayList<Pedido> lista = new ArrayList();
        String sql = "{call Sp_BuscarPedido('"+cmbCodigoPedido.getValue()+"')}";
        System.out.println(cmbCodigoPedido.getValue());
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Pedido(
                        rs.getInt("pedidoId"),
                        rs.getDate("pedidoFecha"),
                        rs.getString("pedidoDireccionInicio"),
                        rs.getString("pedidoDireccionFinal"),
                        rs.getString("cliente"),
                        rs.getString("nombreReceptor"),
                        rs.getString("pedidoTelefonoReceptor"),
                        rs.getString("pedidoDesc"),
                        rs.getString("mensajero"),
                        rs.getDouble("pedidoMonto"),
                        rs.getDouble("pedidoCosto"),
                        rs.getString("formaPagoDesc"),
                        rs.getString("estadoPedidoDesc")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Image imgError = new Image("org/appcontablealas/img/error.png");
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS");
            noti.text("Error al cargar la base de datos");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        return listaPedidos = FXCollections.observableList(lista);
    }    
    
    
    public void cargarDatosPorId(){
        tblPedido.setItems(getPedidoBuscadoPorId());
        
            colCodigoPedido.setCellValueFactory(new PropertyValueFactory("pedidoId"));
            colFechaPedido.setCellValueFactory(new PropertyValueFactory("pedidoFecha"));
            colReceptorPedido.setCellValueFactory(new PropertyValueFactory("nombreReceptor"));
            colDireccionPedido.setCellValueFactory(new PropertyValueFactory("pedidoDireccionInicio"));
            colDireccionFinalPedido.setCellValueFactory(new PropertyValueFactory("pedidoDireccionFinal"));
            colClientePedido.setCellValueFactory(new PropertyValueFactory("cliente"));
            colTelefonoPedido.setCellValueFactory(new PropertyValueFactory("pedidoTelefonoReceptor"));
            colMensajeroPedido.setCellValueFactory(new PropertyValueFactory("mensajero"));
            colMontoPedido.setCellValueFactory(new PropertyValueFactory("pedidoMonto"));
            colCostoPedido.setCellValueFactory(new PropertyValueFactory("pedidoCosto"));
            colFormaPagoPedido.setCellValueFactory(new PropertyValueFactory("formaPagoDesc"));
            colEstadoPedido.setCellValueFactory(new PropertyValueFactory("estadoPedidoDesc"));
            
            new AutoCompleteComboBoxListener<>(cmbMensajero);
            desactivarText();
            llenarComboBoxMensajero();
            llenarComboBoxEstadoPedido();
    }
    
    
    public ObservableList<Pedido> getPedidoBuscadoPorEstado(){
        ArrayList<Pedido> lista = new ArrayList();
        String sql = "{call Sp_ListarPedidoPorEstado('"+cmbEstadoPedidoBusqueda.getValue()+"')}";
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Pedido(
                        rs.getInt("pedidoId"),
                        rs.getDate("pedidoFecha"),
                        rs.getString("pedidoDireccionInicio"),
                        rs.getString("pedidoDireccionFinal"),
                        rs.getString("cliente"),
                        rs.getString("nombreReceptor"),
                        rs.getString("pedidoTelefonoReceptor"),
                        rs.getString("pedidoDesc"),
                        rs.getString("mensajero"),
                        rs.getDouble("pedidoMonto"),
                        rs.getDouble("pedidoCosto"),
                        rs.getString("formaPagoDesc"),
                        rs.getString("estadoPedidoDesc")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Image imgError = new Image("org/appcontablealas/img/error.png");
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS");
            noti.text("Error al cargar la base de datos");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        return listaPedidos = FXCollections.observableList(lista);
    }    
    
     public void cargarDatosPorEstado(){
        tblPedido.setItems(getPedidoBuscadoPorEstado());
        
            colCodigoPedido.setCellValueFactory(new PropertyValueFactory("pedidoId"));
            colFechaPedido.setCellValueFactory(new PropertyValueFactory("pedidoFecha"));
            colReceptorPedido.setCellValueFactory(new PropertyValueFactory("nombreReceptor"));
            colDireccionPedido.setCellValueFactory(new PropertyValueFactory("pedidoDireccionInicio"));
            colDireccionFinalPedido.setCellValueFactory(new PropertyValueFactory("pedidoDireccionFinal"));
            colClientePedido.setCellValueFactory(new PropertyValueFactory("cliente"));
            colTelefonoPedido.setCellValueFactory(new PropertyValueFactory("pedidoTelefonoReceptor"));
            colMensajeroPedido.setCellValueFactory(new PropertyValueFactory("mensajero"));
            colMontoPedido.setCellValueFactory(new PropertyValueFactory("pedidoMonto"));
            colCostoPedido.setCellValueFactory(new PropertyValueFactory("pedidoCosto"));
            colFormaPagoPedido.setCellValueFactory(new PropertyValueFactory("formaPagoDesc"));
            colEstadoPedido.setCellValueFactory(new PropertyValueFactory("estadoPedidoDesc"));
            
            new AutoCompleteComboBoxListener<>(cmbMensajero);
            desactivarText();
            llenarComboBoxMensajero();
            llenarComboBoxEstadoPedido();
    }
     
     
@FXML
    private void btnBuscar(MouseEvent event) {
            cargarDatosPorId();
}
    
@FXML
    private void btnFiltrar(MouseEvent event) {
         cargarDatosPorEstado(); 
}
    
     
        public void accion(){
        switch(tipoOperacion){
            case AGREGAR:
                tipoOperacion = PedidosViewController.Operacion.GUARDAR;
                cancelar = PedidosViewController.Operacion.CANCELAR;
                btnAgregar.setText("GUARDAR");
                activarText();
                cmbMensajero.setDisable(true);
                btnBuscar.setDisable(true);
                limpiarText();
                break;
            case CANCELAR:
                tipoOperacion = PedidosViewController.Operacion.NINGUNO;
                desactivarText();
                btnAgregar.setText("AGREGAR");
                limpiarText();
                cmbMensajero.setDisable(false);
                btnBuscar.setDisable(false);
                cancelar = PedidosViewController.Operacion.NINGUNO;
                break;
        }
    }
        
        public void accion(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        switch(tipoOperacion){
            case GUARDAR: 
                alert.setTitle("ACTUALIZAR PEDIDO");
                alert.setHeaderText("ACTUALIZAR PEDIDO");
                alert.setContentText("¿Está seguro que desea actualizar este pedido?");
                
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == buttonTypeSi ){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA ACTUALIZADO EXITOSAMENTE EL PEDIDO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = PedidosViewController.Operacion.CANCELAR;
                        accion();
                        cargarDatos();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL PEDIDO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = PedidosViewController.Operacion.CANCELAR;
                        accion();
                    }
                }else{
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA CANCELADO LA OPERACION");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacion = PedidosViewController.Operacion.CANCELAR;
                    accion();
                }
                break;
            case BUSCAR:
                try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int codigo=0;                
                    if(rs.first()){
                        for(int i=0; i<tblPedido.getItems().size(); i++){
                            if(colCodigoPedido.getCellData(i) == codigo){
                                tblPedido.getSelectionModel().select(i);
                                break;
                            }
                        }
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SU OPERACIÓN SE HA REALIZADO CON EXITO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                    }else{
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = PedidosViewController.Operacion.CANCELAR;
                        accion();
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR AL BUSCAR");
                    noti.text("HA OCURRIDO UN ERROR EN LA BASE DE DATOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacion = PedidosViewController.Operacion.CANCELAR;
                    accion();
                }
                break;
        }
    }
        
        
        public void buscarDescripcion(){
           Notifications noti = Notifications.create();

            String sql = "{call Sp_BuscarDescPedido('"+txtCodigoPedido.getText()+"')}";
            accion(sql);
            
            PreparedStatement ps;
            ResultSet rs;
            
            try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int numero=0;
                    
                    while(rs.next()){
                        colDescPedido.setText(rs.getString("pedidoDesc"));                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblPedido.getItems().size(); i++){
                            if(colCodigoPedido.getCellData(i) == codigoPedidos){
                                tblPedido.getSelectionModel().select(i);
                                break;
                            }
                        }
                    }else{
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR AL BUSCAR");
                    noti.text("HA OCURRIDO UN ERROR EN LA BASE DE DATOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacion = Operacion.CANCELAR;
                }
        }      
        
    public int verificarEstadoProducto(String estadoProducto){
        
        String sql = "{call Sp_BuscarEstadoPorNombre('"+estadoProducto+"')}";
        int codigoEstado=0;
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                codigoEstado = rs.getInt("estadoPedidoId");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return codigoEstado;
    }
      
    
    public int verificarMensajero(String mensajero){
        
        String sql = "{call Sp_BuscarCodigoUsuario('"+mensajero+"')}";
        int codigoUsername=0;
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                codigoUsername = rs.getInt("usuarioId");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return codigoUsername;
    }
    

        
        @FXML
    private void seleccionarElementos(MouseEvent event) {
        int index = tblPedido.getSelectionModel().getSelectedIndex();
        try{
            activarText();
            txtCodigoPedido.setText(colCodigoPedido.getCellData(index).toString());
            txtCostoPedido.setText(colCostoPedido.getCellData(index).toString());
            cmbEstadoPedido.setValue(colEstadoPedido.getCellData(index));
            cmbMensajero.setValue(colMensajeroPedido.getCellData(index));
            
            buscarDescripcion();
        }catch(Exception e){
           e.printStackTrace();
        }
    }

    
        @FXML
    private void btnAgregar(MouseEvent event) {
        int index = tblPedido.getSelectionModel().getSelectedIndex();
        
        Integer codigoBuscado = 0;
        codigoBuscado = colCodigoPedido.getCellData(index);
        
        Pedido asignarPedido = new Pedido();
        asignarPedido.setPedidoCosto(Double.parseDouble(txtCostoPedido.getText()));
        asignarPedido.setEstadoPedidoDesc(cmbEstadoPedido.getValue());
        asignarPedido.setMensajero(cmbMensajero.getValue());
        

        tipoOperacion = Operacion.GUARDAR;
        String sql = "{call Sp_ConfirmarPedido('"+codigoBuscado+"','"+verificarMensajero(asignarPedido.getMensajero())+"','"+asignarPedido.getPedidoCosto()+"','"+verificarEstadoProducto(asignarPedido.getEstadoPedidoDesc())+"')}";
        accion(sql);
}
    

    
        
    @FXML
    public void menuprincipal() throws IOException{
        String menuprincipalUrl = "org/appcontablealas/view/menuPrincipal.fxml";
        cambioScene.Cambio(menuprincipalUrl,(Stage) anchor.getScene().getWindow());
    }
        
    private void btnInicio(MouseEvent event) throws IOException{
        menuprincipal();
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
        menu();
    }
           
    @FXML
    private void clienteAtajo(MouseEvent event) throws IOException {
        cliente();
    }
    
    //reportes
    public void reporte() throws IOException{
        String inventarioUrl = "org/appcontablealas/view/reporteView.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void reporteView(ActionEvent event) throws IOException {
        reporte();
    }
        
    @FXML
    private void reporteAtajo(MouseEvent event) throws IOException {
        reporte();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    
    
}
