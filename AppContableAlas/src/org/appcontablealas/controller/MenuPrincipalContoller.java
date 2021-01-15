package org.appcontablealas.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.appcontablealas.bean.AutoCompleteComboBoxListener;
import org.appcontablealas.bean.CambioScene;
import org.appcontablealas.bean.Usuario;
import org.appcontablealas.db.Conexion;
import org.controlsfx.control.Notifications;


public class MenuPrincipalContoller implements Initializable {

    CambioScene cambioScene = new CambioScene();
    
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public Operacion tipoOperacion= Operacion.NINGUNO;
    public Operacion cancelar = Operacion.NINGUNO;
    Image imgError= new Image("org/appcontablealas/img/error.png");
    Image imgCorrecto= new Image("org/appcontablealas/img/correcto.png");

    private ObservableList<String>listaComboUsername;
    private ObservableList<Usuario>listaUsuario;
    private ObservableList<String>listaTipoUsuario;

        
    @FXML
    private AnchorPane anchor;
    @FXML
    private Pane paneBienvenida;
    @FXML
    private Label labelUsuario;
    @FXML
    private AnchorPane cajaClientes;
    @FXML
    private AnchorPane cajaMensajero;
    @FXML
    private AnchorPane cajaReportes;
    @FXML
    private AnchorPane cajaPedidos;
    @FXML
    private Tab tabAjustes;
    @FXML
    private AnchorPane paneUsuario;

    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXTextField txtApellidoUsuario;
    @FXML
    private JFXTextField txtNombreUsuario;
    @FXML
    private JFXPasswordField txtPasswordUsuario;
    @FXML
    private ComboBox<String> cmbTipoUsuario;
    @FXML
    private ComboBox<String> cmbUserName;
    @FXML
    private AnchorPane paneTabla;
    @FXML
    private TableView<Usuario> tblUsuario;
    @FXML
    private TableColumn<Usuario, Integer> colCodigoUsuario;
    @FXML
    private TableColumn<Usuario, String> colNombreUsuario;
    @FXML
    private TableColumn<Usuario, String> colApellidoUsuario;
    @FXML
    private TableColumn<Usuario, String> colUsername;
    @FXML
    private TableColumn<Usuario, String> colPasswordUsuario;
    @FXML
    private TableColumn<Usuario, String> colTipoUsuario;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private CheckBox checkBox;
    @FXML
    private MenuItem itemInventario;

    
    public Preferences prefsUsuario1 = Preferences.userRoot().node(this.getClass().getName());

    
     @FXML
    private void recordarContraseña(ActionEvent event) {
        if(checkBox.isSelected()){
            prefsUsuario1.put("validar", "recordar");
        }else{
            prefsUsuario1.put("validar", "no recordar");
        }
    }
    
    public void limpiarText(){
        txtNombreUsuario.setText("");
        txtApellidoUsuario.setText("");
        txtUserName.setText("");
        txtPasswordUsuario.setText("");
        cmbTipoUsuario.setValue("");
        cmbTipoUsuario.setPromptText("Seleccione un tipo de Usuario");
    }
    
    public void desactivarText(){
        txtNombreUsuario.setEditable(false);
        txtApellidoUsuario.setEditable(false);
        txtUserName.setEditable(false);
        txtPasswordUsuario.setEditable(false);
        cmbTipoUsuario.setDisable(true);
    }
    
    public void activarText(){
        txtNombreUsuario.setEditable(true);
        txtApellidoUsuario.setEditable(true);
        txtUserName.setEditable(true);
        txtPasswordUsuario.setEditable(true);
        cmbTipoUsuario.setDisable(false);
    }
    
    public void desactivarControles(){
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);    
    }
    
    public void activarControles(){
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
    }
    
         @FXML
    private void validarUsuario(KeyEvent event) {
        char letra = event.getCharacter().charAt(0);
        
        if(!Character.isLetterOrDigit(letra)){
            event.consume();
        }else{
        
        }
    }

    @FXML
    private void validarcontraseña(KeyEvent event) {
        char letra = event.getCharacter().charAt(0);
        
        if(!Character.isLetterOrDigit(letra)){
            event.consume();
        }else{
        
        }
    }
    
    public ObservableList<Usuario> getUsuario(){
        ArrayList<Usuario> lista = new ArrayList();
        ArrayList<String> listaUsername = new ArrayList();
        String sql = "{call Sp_ListarMesajeros()}";
        int x=0;
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Usuario(
                        rs.getInt("usuarioId"),
                        rs.getString("userName"),
                        rs.getString("usuarioNombre"),
                        rs.getString("usuarioApellido"),
                        rs.getString("usuarioContrasena"),
                        rs.getString("tipoUsuarioDesc")
                ));
              listaUsername.add(x,rs.getString("userName"));
              x++;
            }
            listaComboUsername = FXCollections.observableList(listaUsername);
            cmbUserName.setItems(listaComboUsername);
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
        
        return listaUsuario = FXCollections.observableList(lista);
    }
    
    
    public void llenarComboBox(){
        String sql = "{call Sp_LLenarTipoUsuario()}";
        int x = 0;
        ArrayList<String>lista= new ArrayList<>();
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x,rs.getString("tipoUsuarioDesc"));
                x++;
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        listaTipoUsuario = FXCollections.observableList(lista);
        cmbTipoUsuario.setItems(listaTipoUsuario);
    }
        
    
    public void cargarDatos(){
        tblUsuario.setItems(getUsuario());
        
            colCodigoUsuario.setCellValueFactory(new PropertyValueFactory("usuarioId"));
            colNombreUsuario.setCellValueFactory(new PropertyValueFactory("usuarioNombre"));
            colApellidoUsuario.setCellValueFactory(new PropertyValueFactory("usuarioApellido"));
            colUsername.setCellValueFactory(new PropertyValueFactory("userName"));
            colPasswordUsuario.setCellValueFactory(new PropertyValueFactory("usuarioContrasena"));
            colTipoUsuario.setCellValueFactory(new PropertyValueFactory("tipoUsuarioDesc"));
            new AutoCompleteComboBoxListener<>(cmbUserName);
            desactivarControles();
            desactivarText();
            llenarComboBox();
            limpiarText();
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbUserName.setValue("");
        
        // caja de bienvenida
       FadeTransition ft = new FadeTransition();
       ft.setFromValue(0);
       ft.setToValue(1);
       ft.setDuration(Duration.seconds(1));
       ft.setNode(paneBienvenida);
       ft.setCycleCount(1);
       ft.play();
       
       
       TranslateTransition tt = new TranslateTransition();
       tt.setFromY(-20);
       tt.setToY(1);
       tt.setDuration(Duration.seconds(1.5));
       tt.setNode(paneBienvenida);
       tt.setCycleCount(1);
       tt.play();
       
        
       //caja de clientes
       FadeTransition ftCliente = new FadeTransition();
       ftCliente.setFromValue(0);
       ftCliente.setToValue(1);
       ftCliente.setDuration(Duration.seconds(2));
       ftCliente.setNode(cajaClientes);
       ftCliente.setCycleCount(1);
       ftCliente.play();
       
       
       TranslateTransition ttCliente = new TranslateTransition();
       ttCliente.setFromY(-40);
       ttCliente.setToY(0);
       ttCliente.setDuration(Duration.seconds(2.0));
       ttCliente.setNode(cajaClientes);
       ttCliente.setCycleCount(1);
       ttCliente.play();
       
       // caja de Mensajero
       
        FadeTransition ftInventario = new FadeTransition();
       ftInventario.setFromValue(0);
       ftInventario.setToValue(1);
       ftInventario.setDuration(Duration.seconds(2));
       ftInventario.setNode(cajaMensajero);
       ftInventario.setCycleCount(1);
       ftInventario.play();
       
       
       TranslateTransition tInventario = new TranslateTransition();
       tInventario.setFromY(-60);
       tInventario.setToY(0);
       tInventario.setDuration(Duration.seconds(2.5));
       tInventario.setNode(cajaMensajero);
       tInventario.setCycleCount(1);
       tInventario.play();
       
       //caja de pedidos
          FadeTransition ftPrecios = new FadeTransition();
       ftPrecios.setFromValue(0);
       ftPrecios.setToValue(1);
       ftPrecios.setDuration(Duration.seconds(2.5));
       ftPrecios.setNode(cajaPedidos);
       ftPrecios.setCycleCount(1);
       ftPrecios.play();
       
       
       TranslateTransition ttPrecios = new TranslateTransition();
       ttPrecios.setFromY(-80);
       ttPrecios.setToY(1);
       ttPrecios.setDuration(Duration.seconds(3));
       ttPrecios.setNode(cajaPedidos);
       ttPrecios.setCycleCount(1);
       ttPrecios.play();
       
       
       // caja de reportes
       FadeTransition ftFacturas = new FadeTransition();
       ftFacturas.setFromValue(0);
       ftFacturas.setToValue(1);
       ftFacturas.setDuration(Duration.seconds(3));
       ftFacturas.setNode(cajaReportes);
       ftFacturas.setCycleCount(1);
       ftFacturas.play();
       
       
       TranslateTransition ttFacturas = new TranslateTransition();
       ttFacturas.setFromY(-80);
       ttFacturas.setToY(0);
       ttFacturas.setDuration(Duration.seconds(3.5));
       ttFacturas.setNode(cajaReportes);
       ttFacturas.setCycleCount(1);
       ttFacturas.play();   
    }
    
    
    @FXML
    private void tabAjustesEvent(Event event) {
        // caja de usuario
       FadeTransition ftUsuario = new FadeTransition();
       ftUsuario.setFromValue(0);
       ftUsuario.setToValue(1);
       ftUsuario.setDuration(Duration.seconds(2));
       ftUsuario.setNode(paneUsuario);
       ftUsuario.setCycleCount(1);
       ftUsuario.play();
       
       
       TranslateTransition ttUsuario = new TranslateTransition();
       ttUsuario.setFromY(-80);
       ttUsuario.setToY(0);
       ttUsuario.setDuration(Duration.seconds(1.0));
       ttUsuario.setNode(paneUsuario);
       ttUsuario.setCycleCount(1);
       ttUsuario.play();
        
       //caja de tabla
       FadeTransition ftTablaUsuario = new FadeTransition();
       ftTablaUsuario.setFromValue(0);
       ftTablaUsuario.setToValue(1);
       ftTablaUsuario.setDuration(Duration.seconds(2));
       ftTablaUsuario.setNode(paneTabla);
       ftTablaUsuario.setCycleCount(1);
       ftTablaUsuario.play();
       
       
       TranslateTransition ttTabla = new TranslateTransition();
       ttUsuario.setFromY(-80);
       ttUsuario.setToY(0);
       ttUsuario.setDuration(Duration.seconds(2.0));
       ttUsuario.setNode(paneTabla);
       ttUsuario.setCycleCount(1);
       ttUsuario.play();
       cargarDatos();
    }
    
    @FXML
    private void tabBienvenida(Event event) {
       
        // caja de bienvenida
       FadeTransition ft = new FadeTransition();
       ft.setFromValue(0);
       ft.setToValue(1);
       ft.setDuration(Duration.seconds(1));
       ft.setNode(paneBienvenida);
       ft.setCycleCount(1);
       ft.play();
       
       
       TranslateTransition tt = new TranslateTransition();
       tt.setFromY(-20);
       tt.setToY(1);
       tt.setDuration(Duration.seconds(1.5));
       tt.setNode(paneBienvenida);
       tt.setCycleCount(1);
       tt.play();
       
        
       //caja de clientes
       FadeTransition ftCliente = new FadeTransition();
       ftCliente.setFromValue(0);
       ftCliente.setToValue(1);
       ftCliente.setDuration(Duration.seconds(2));
       ftCliente.setNode(cajaClientes);
       ftCliente.setCycleCount(1);
       ftCliente.play();
       
       
       TranslateTransition ttCliente = new TranslateTransition();
       ttCliente.setFromY(-40);
       ttCliente.setToY(0);
       ttCliente.setDuration(Duration.seconds(2.0));
       ttCliente.setNode(cajaClientes);
       ttCliente.setCycleCount(1);
       ttCliente.play();
       
       // caja de Mensajero
       
        FadeTransition ftInventario = new FadeTransition();
       ftInventario.setFromValue(0);
       ftInventario.setToValue(1);
       ftInventario.setDuration(Duration.seconds(2));
       ftInventario.setNode(cajaMensajero);
       ftInventario.setCycleCount(1);
       ftInventario.play();
       
       
       TranslateTransition tInventario = new TranslateTransition();
       tInventario.setFromY(-60);
       tInventario.setToY(0);
       tInventario.setDuration(Duration.seconds(2.5));
       tInventario.setNode(cajaMensajero);
       tInventario.setCycleCount(1);
       tInventario.play();
       
       //caja de pedidos
          FadeTransition ftPrecios = new FadeTransition();
       ftPrecios.setFromValue(0);
       ftPrecios.setToValue(1);
       ftPrecios.setDuration(Duration.seconds(2.5));
       ftPrecios.setNode(cajaPedidos);
       ftPrecios.setCycleCount(1);
       ftPrecios.play();
       
       
       TranslateTransition ttPrecios = new TranslateTransition();
       ttPrecios.setFromY(-80);
       ttPrecios.setToY(1);
       ttPrecios.setDuration(Duration.seconds(3));
       ttPrecios.setNode(cajaPedidos);
       ttPrecios.setCycleCount(1);
       ttPrecios.play();
       
       
       // caja de reportes
       FadeTransition ftFacturas = new FadeTransition();
       ftFacturas.setFromValue(0);
       ftFacturas.setToValue(1);
       ftFacturas.setDuration(Duration.seconds(3));
       ftFacturas.setNode(cajaReportes);
       ftFacturas.setCycleCount(1);
       ftFacturas.play();
       
       
       TranslateTransition ttFacturas = new TranslateTransition();
       ttFacturas.setFromY(-80);
       ttFacturas.setToY(0);
       ttFacturas.setDuration(Duration.seconds(3.5));
       ttFacturas.setNode(cajaReportes);
       ttFacturas.setCycleCount(1);
       ttFacturas.play();
    }
    
    
    //Clientes
    public void clientes() throws IOException{
        String clienteUrl = "org/appcontablealas/view/clientesView.fxml";
        cambioScene.Cambio(clienteUrl,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void Clientesview(ActionEvent event) throws IOException {
        clientes();
    }
    
     @FXML
    private void ClientesAtajo(MouseEvent event) throws IOException {
        clientes();
    }
    
    
    //Pedidos
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

    
    //Reportes
    public void reportes() throws IOException{
        String inventarioUrl = "org/appcontablealas/view/reportesView.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void reportesView(ActionEvent event) throws IOException {
        reportes();
    }
    
    @FXML
    private void reportesAtajo(MouseEvent event) throws IOException {
        reportes();
    }
    
    
    //atajos de menu de bienvenida
    @FXML
    private void AtajosInicio(KeyEvent event) {
    }
    
    //atajos de configuracion
    @FXML
    private void AtajosConfiguracion(KeyEvent event) {
        if(cmbUserName.isFocused()){
                if(event.getCode() == KeyCode.ENTER){
                   // buscar();
                }
        }
    }
    
    
    //atajos de vista en general
    @FXML
    private void AtajosVista(KeyEvent event) throws IOException {
        //modulos
       if(event.getCode() == KeyCode.F1){
           clientes();
       }else{
           if(event.getCode() == KeyCode.F2){
               reportes();
           }else{
               if(event.getCode() == KeyCode.F3){
                   pedidos();
               }
           }
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
                cmbUserName.setDisable(true);
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
                cmbUserName.setDisable(false);
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
                        txtNombreUsuario.setText(rs.getString("usuarioNombre"));
                        txtApellidoUsuario.setText(rs.getString("usuarioApellido"));
                        txtUserName.setText(rs.getString("userName"));
                        txtPasswordUsuario.setText(rs.getString("usuarioContrasena"));
                        cmbTipoUsuario.setValue(rs.getString("tipoUsuarioDesc"));
                        codigo = rs.getInt("usuarioId");
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblUsuario.getItems().size(); i++){
                            if(colCodigoUsuario.getCellData(i) == codigo){
                                tblUsuario.getSelectionModel().select(i);
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
            if(cmbUserName.getValue().equals("")){
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
                    String sql = "{call Sp_BuscarMensajero('"+cmbUserName.getValue()+"')}";
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
                String sql = "{call Sp_EliminarUsuarioPorNombre('"+txtUserName.getText()+"')}";
                tipoOperacion = Operacion.ELIMINAR;
                accion(sql);
            }
        }
    
    
    @FXML
    private void seleccionarElementos(MouseEvent event) {
        int index = tblUsuario.getSelectionModel().getSelectedIndex();
        try{
            activarText();
           
            txtNombreUsuario.setText(colNombreUsuario.getCellData(index));
            txtApellidoUsuario.setText(colApellidoUsuario.getCellData(index));
            txtUserName.setText(colUsername.getCellData(index));
            txtPasswordUsuario.setText(colPasswordUsuario.getCellData(index));
            cmbTipoUsuario.setValue(colTipoUsuario.getCellData(index));
            
            btnEditar.setDisable(false);
            btnEliminar.setDisable(false);
            cmbTipoUsuario.setDisable(false);
        }catch(Exception e){
           e.printStackTrace();
        }
    }
    
    
    @FXML
    private void btnAgregar(MouseEvent event) {
             if(tipoOperacion == Operacion.GUARDAR){
                if(txtUserName.getText().isEmpty() || txtPasswordUsuario.getText().isEmpty() || cmbTipoUsuario.getValue() == "" || txtNombreUsuario.getText().isEmpty() || txtApellidoUsuario.getText().isEmpty()){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("HAY CAMPOS VACÍOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                    
                }else{
                    if((txtUserName.getText().length() < 30 ) && (txtPasswordUsuario.getText().length() < 30)){
                        int tipoUsuario;
                        Usuario nuevoUsuario = new Usuario();
                        nuevoUsuario.setUsuarioNombre(txtNombreUsuario.getText());
                        nuevoUsuario.setUsuarioApellido(txtApellidoUsuario.getText());
                        nuevoUsuario.setUserName(txtUserName.getText());
                        nuevoUsuario.setUsuarioContrasena(txtPasswordUsuario.getText());
                        if(cmbTipoUsuario.getValue().equals("Administrador")){
                               tipoUsuario = 1;
                        }else{
                            tipoUsuario = 2;
                        }
                        String sql = "{call Sp_AgregarUsuario('"+nuevoUsuario.getUsuarioNombre()+"','"+nuevoUsuario.getUsuarioApellido()+"','"+nuevoUsuario.getUserName()+"','"+nuevoUsuario.getUsuarioContrasena()+"','"+tipoUsuario+"')}";
                        accion(sql);
                    }else{
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("USUARIO Y/O CONTRASEÑA NO TIENEN UNA LONGITUD ADECUADA (DEBE CONTENER MENOS DE 30 CARACTERES)");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                    }
                }
            }else{
                tipoOperacion = Operacion.AGREGAR;
                accion();
            }             
    }
    
    
    @FXML
    private void btnEditar(MouseEvent event) {
        int tipoUsuario;
        int index = tblUsuario.getSelectionModel().getSelectedIndex();
        int codigoBuscado = 0;
        
        codigoBuscado = colCodigoUsuario.getCellData(index);
        System.out.println(codigoBuscado);
        
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsuarioNombre(txtNombreUsuario.getText());
        nuevoUsuario.setUsuarioApellido(txtApellidoUsuario.getText());
        nuevoUsuario.setUserName(txtUserName.getText());
        nuevoUsuario.setUsuarioContrasena(txtPasswordUsuario.getText());
        if(cmbTipoUsuario.getValue().equals("Administrador")){
               tipoUsuario = 1;
        }else{
            tipoUsuario = 2;
        }
        tipoOperacion = Operacion.ACTUALIZAR;
        String sql = "{call Sp_ActualizarUsuario('"+codigoBuscado+"','"+nuevoUsuario.getUsuarioNombre()+"','"+nuevoUsuario.getUsuarioApellido()+"','"+nuevoUsuario.getUserName()+"','"+nuevoUsuario.getUsuarioContrasena()+"')}";
        accion(sql);
}
    
}
    

    
