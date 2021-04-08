package org.appcontablealas.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import org.appcontablealas.bean.Clientes;
import org.appcontablealas.db.Conexion;
import org.controlsfx.control.Notifications;



public class ClientesViewController implements Initializable {

    @FXML
    private JFXButton btnInhabilitar;



     public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public Operacion tipoOperacion= Operacion.NINGUNO;
    public Operacion cancelar = Operacion.NINGUNO;
    
    CambioScene cambioScene = new CambioScene();
    ObservableList<Clientes> listaClientes;
    ObservableList<String> listaUsernameClientes;
    ObservableList<String> listaCorreos;

    
    Image imgError = new Image("org/appcontablealas/img/error.png");
    Image imgCorrecto= new Image("org/appcontablealas/img/correcto.png");
    
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    
    
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
    private JFXTextField txtNombreCliente;
    @FXML
    private JFXPasswordField txtPasswordCliente;
    @FXML
    private JFXTextField txtUserNameCliente;
    @FXML
    private JFXTextField txtApellidoCliente;
    @FXML
    private JFXTextField txtCorreoCliente;
    @FXML
    private JFXTextField txtCuentaCliente;
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private TableView<Clientes> tblCliente;
    @FXML
    private TableColumn<Clientes, Integer> colCodigoCliente;
    @FXML
    private TableColumn<Clientes, String> colNombreCliente;
    @FXML
    private TableColumn<Clientes, String> colApellidoCliente;
    @FXML
    private TableColumn<Clientes, String> colUsuarioCliente;
    @FXML
    private TableColumn<Clientes, String> colPasswordCliente;
    @FXML
    private TableColumn<Clientes, String> colCorreoCliente;
    @FXML
    private TableColumn<Clientes, String> colEmpresaCliente;
    @FXML
    private TableColumn<Clientes, String> colNumeroCuentaCliente;
    @FXML
    private TableColumn<Clientes, String> colTipoCuentaCliente;
    @FXML
    private TableColumn<Clientes, String> colNombreBanco;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private ComboBox<String> cmbUsuarioCliente;





    public void limpiarText(){
        txtNombreCliente.setText("");
        txtApellidoCliente.setText("");
        txtUserNameCliente.setText("");
        txtPasswordCliente.setText("");
        txtCorreoCliente.setText("");
    }
    
    public void desactivarText(){
        txtNombreCliente.setEditable(true);
        txtApellidoCliente.setEditable(true);
        txtUserNameCliente.setEditable(true);
        txtPasswordCliente.setEditable(true);
        txtCorreoCliente.setEditable(true);
    }
    
    public void activarText(){
        txtNombreCliente.setEditable(true);
        txtApellidoCliente.setEditable(true);
        txtUserNameCliente.setEditable(true);
        txtPasswordCliente.setEditable(true);
        txtCorreoCliente.setEditable(true);
    }
    
    public void desactivarControles(){
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);    
    }
    
    public void activarControles(){
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
    }
    
   
   public ObservableList<Clientes> getClientes(){
        ArrayList<Clientes> listaCliente = new ArrayList();
        ArrayList<String> listaUsername = new ArrayList();
        String sql = "{call Sp_ListarClientes()}";
        int x=0;
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                listaCliente.add(new Clientes(
                        rs.getInt("usuarioId"),
                        rs.getString("usuarioNombre"),
                        rs.getString("usuarioApellido"),
                        rs.getString("userName"),
                        rs.getString("usuarioContrasena"),
                        rs.getString("usuarioCorreo"),
                        rs.getString("empresaDesc"),
                        rs.getString("empresaNumeroCuenta"),
                        rs.getString("tipoCuentaDesc"),
                        rs.getString("bancoDesc")
                ));
              listaUsername.add(x,rs.getString("userName"));
              x++;
            }
            listaUsernameClientes = FXCollections.observableList(listaUsername);
            cmbUsuarioCliente.setItems(listaUsernameClientes);
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
        return listaClientes = FXCollections.observableList(listaCliente);
    }
   
       public void cargarDatos(){        
        tblCliente.setItems(getClientes());
        
            colCodigoCliente.setCellValueFactory(new PropertyValueFactory("usuarioId"));
            colNombreCliente.setCellValueFactory(new PropertyValueFactory("usuarioNombre"));
            colApellidoCliente.setCellValueFactory(new PropertyValueFactory("usuarioApellido"));
            colUsuarioCliente.setCellValueFactory(new PropertyValueFactory("userName"));
            colPasswordCliente.setCellValueFactory(new PropertyValueFactory("usuarioContrasena"));
            colCorreoCliente.setCellValueFactory(new PropertyValueFactory("usuarioCorreo"));
            colEmpresaCliente.setCellValueFactory(new PropertyValueFactory("empresaDesc"));
            colNumeroCuentaCliente.setCellValueFactory(new PropertyValueFactory("empresaNumeroCuenta"));
            colTipoCuentaCliente.setCellValueFactory(new PropertyValueFactory("tipoCuentaDesc"));
            colNombreBanco.setCellValueFactory(new PropertyValueFactory("bancoDesc"));
            
            new AutoCompleteComboBoxListener<>(cmbUsuarioCliente);
            desactivarText();
            limpiarText();
    }

    @FXML
    private void seleccionarElementos(MouseEvent event) {
        int index = tblCliente.getSelectionModel().getSelectedIndex();
        try{
            activarText();
            txtNombreCliente.setText(colNombreCliente.getCellData(index));
            txtApellidoCliente.setText(colApellidoCliente.getCellData(index));
            txtUserNameCliente.setText(colUsuarioCliente.getCellData(index));
            txtPasswordCliente.setText(colPasswordCliente.getCellData(index));
            txtCorreoCliente.setText(colCorreoCliente.getCellData(index));
            txtCuentaCliente.setText(colNumeroCuentaCliente.getCellData(index)+ " , " + colTipoCuentaCliente.getCellData(index)+ " ," + colNombreBanco.getCellData(index));
            activarControles();
            
        }catch(Exception e){
           e.printStackTrace();
        }
    }   

    public void accion(){
        switch(tipoOperacion){
            case AGREGAR:
                tipoOperacion = Operacion.GUARDAR;
                cancelar = Operacion.CANCELAR;
                desactivarControles();
                btnAgregar.setText("GUARDAR");
                btnEliminar.setText("CANCELAR");
                btnEliminar.setDisable(false);
                activarText();
                btnBuscar.setDisable(true);
                limpiarText();
                break;
            case CANCELAR:
                tipoOperacion = Operacion.NINGUNO;
                desactivarControles();
                desactivarText();
                btnAgregar.setText("AGREGAR");
                btnEliminar.setText("ELIMINAR");
                limpiarText();
                btnBuscar.setDisable(false);
                cancelar = Operacion.NINGUNO;
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
                alert.setTitle("AGREGAR REGISTRO");
                alert.setHeaderText("AGREGAR REGISTRO");
                alert.setContentText("¿Está seguro que desea guardar este registro?");
                
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == buttonTypeSi ){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA AGREGADO EXITOSAMENTE EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                        cargarDatos();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                    }
                }else{
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacion = Operacion.CANCELAR;
                    accion();
                }
                break;

            case ELIMINAR:
                alert.setTitle("ELIMINAR REGISTRO");
                alert.setHeaderText("ELIMINAR REGISTRO");
                alert.setContentText("¿Está seguro que desea Eliminar este registro?");
               
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> resultEliminar = alert.showAndWait();
                
                if(resultEliminar.get() == buttonTypeSi){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA ELIMINADO EXITOSAMENTE EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        cargarDatos();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                    }
                }else{
                     noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacion = Operacion.CANCELAR;
                    accion();
                }
                break;
                
            case ACTUALIZAR:
                alert.setTitle("ACTUALIZAR REGISTRO");
                alert.setHeaderText("ACTUALIZAR REGISTRO");
                alert.setContentText("¿Está seguro que desea Actualizar este registro?");
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> resultactualizar = alert.showAndWait();
                if(resultactualizar.get() == buttonTypeSi ){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA ACTUALIZADO EXITOSAMENTE EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                        cargarDatos();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ACTUALIZAR EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacion = Operacion.CANCELAR;
                    accion();
                }
                break;
                
            case BUSCAR:
                try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int codigo=0;
                    while(rs.next()){
                        txtNombreCliente.setText(rs.getString("usuarioNombre"));
                        txtApellidoCliente.setText(rs.getString("usuarioApellido"));
                        txtUserNameCliente.setText(rs.getString("userName"));
                        txtPasswordCliente.setText(rs.getString("usuarioContrasena"));
                        txtCorreoCliente.setText(rs.getString("usuarioCorreo"));
                        txtCuentaCliente.setText(rs.getString("empresaNumeroCuenta") + " , "+ rs.getString("tipoCuentaDesc") + "," + rs.getString("bancoDesc") );
                        codigo = rs.getInt("usuarioId");
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblCliente.getItems().size(); i++){
                            if(colCodigoCliente.getCellData(i) == codigo){
                                tblCliente.getSelectionModel().select(i);
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
                        activarControles();
                    }else{
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
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
                    tipoOperacion = Operacion.CANCELAR;
                    accion();
                }
                break;
        }
    }
    
        public void buscar(){
            if(cmbUsuarioCliente.getValue().equals("")){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    tipoOperacion = Operacion.BUSCAR;
                    String sql = "{call Sp_BuscarMensajero('"+cmbUsuarioCliente.getValue()+"')}";
                    accion(sql);
                }
            }
    
    
    @FXML
    private void btnBuscar(MouseEvent event) {
      buscar();
    }
    
        @FXML
    private void btnEliminar(MouseEvent event) {    
        if(tipoOperacion == Operacion.GUARDAR){
            tipoOperacion = Operacion.CANCELAR;
            accion();
            }else{
                String sql = "{call Sp_EliminarUsuarioPorNombre('"+txtUserNameCliente.getText()+"')}";
                tipoOperacion = Operacion.ELIMINAR;
                accion(sql);
            }
        }
    
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
                }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
    }    
    
    
    @FXML
    private void btnEditar(MouseEvent event) {
         int index = tblCliente.getSelectionModel().getSelectedIndex();
        int codigoBuscado = 0;
        String pass = "";
        
        codigoBuscado = colCodigoCliente.getCellData(index);
        
        
        Clientes nuevoClientes = new Clientes();
        nuevoClientes.setUsuarioNombre(txtNombreCliente.getText());
        
        pass = getMD5(txtPasswordCliente.getText());
        
        nuevoClientes.setUsuarioApellido(txtApellidoCliente.getText());
        nuevoClientes.setUserName(txtUserNameCliente.getText());
        nuevoClientes.setUsuarioContrasena(pass);
        nuevoClientes.setUsuarioCorreo(txtCorreoCliente.getText());
        
        System.out.println(pass);

        tipoOperacion = Operacion.ACTUALIZAR;
        String sql = "{call SpUpdareUserSoftware('"+codigoBuscado+"','"+nuevoClientes.getUsuarioNombre()+"','"+nuevoClientes.getUsuarioApellido()+"','"+nuevoClientes.getUserName()+"','"+nuevoClientes.getUsuarioContrasena()+"','"+nuevoClientes.getUsuarioCorreo()+"')}";
        accion(sql);
}
    
   @FXML
   private void btnInhabilitar(MouseEvent event) throws SQLException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        
        
       String username = txtUserNameCliente.getText();
       System.out.println(username);

        
        alert.setTitle("INHABILITAR CLIENTE");
        alert.setHeaderText("INHABILITAR CLIENTE");
        alert.setContentText("¿Está seguro que desea inhabilitar a este cliente?");
        alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
        Optional<ButtonType> resultactualizar = alert.showAndWait();
        
            if(resultactualizar.get() == buttonTypeSi ){
                try{
                String sql = "{call SpInhabilitarCliente('"+username+"')}";
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
                
                noti.graphic(new ImageView(imgCorrecto));
                noti.title("CLIENTE INHABILITADO");
                noti.text(username+" "+"ha sido inhabilitado");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
                    
                }catch(Exception e){
                    e.printStackTrace();
                 noti.graphic(new ImageView(imgError));
                 noti.title("CLIENTE NO HA SIDO INHABILITADO");
                 noti.text(username+" "+" no se ha podido inhabilitar");
                 noti.position(Pos.BOTTOM_RIGHT);
                 noti.hideAfter(Duration.seconds(4));
                 noti.darkStyle();
                 noti.show();
                }
                
            }

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
    
   
    //clientes
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
    
    
           @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        txtNombreCliente.setEditable(true);
        txtApellidoCliente.setEditable(true);
        txtUserNameCliente.setEditable(true);
        txtPasswordCliente.setEditable(true);
        txtCorreoCliente.setEditable(true);
    }    
    
    
}
