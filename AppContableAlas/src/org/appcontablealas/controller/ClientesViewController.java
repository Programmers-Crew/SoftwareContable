package org.appcontablealas.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
import javafx.util.Duration;
import org.appcontablealas.bean.Animations;
import org.appcontablealas.bean.AutoCompleteComboBoxListener;
import org.appcontablealas.bean.CambioScene;
import org.appcontablealas.bean.Clientes;
import org.appcontablealas.db.Conexion;
import org.controlsfx.control.Notifications;


public class ClientesViewController implements Initializable {

    CambioScene cambioScene = new CambioScene();
    ObservableList<Clientes> listaClientes;
    ObservableList<String> listaUsernameClientes;
    
    Image imgError = new Image("org/appcontablealas/img/error.png");
    Image imgCorrecto= new Image("org/appcontablealas/img/correcto.png");
    
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    
    @FXML
    private Pane btnRegresar;
    @FXML
    private Pane btnInicio;

    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public Operacion tipoOperacionProveedores= Operacion.NINGUNO;
    public Operacion cancelar = Operacion.NINGUNO;
    
    Animations animacion = new Animations();
    
    @FXML
    private AnchorPane anchor;
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
    private JFXButton btnBuscar;
    @FXML
    private ComboBox<String> cmbUsuarioCliente;


    public void limpiarText(){
        txtNombreCliente.setText("");
        txtApellidoCliente.setText("");
        txtUserNameCliente.setText("");
        txtPasswordCliente.setText("");
    }
    
    public void desactivarText(){
        txtNombreCliente.setEditable(false);
        txtApellidoCliente.setEditable(false);
        txtUserNameCliente.setEditable(false);
        txtPasswordCliente.setEditable(false);
    }
    
    public void activarText(){
        txtNombreCliente.setEditable(true);
        txtApellidoCliente.setEditable(true);
        txtUserNameCliente.setEditable(true);
        txtPasswordCliente.setEditable(true);
    }
    
    public void desactivarControles(){
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);    
    }
    
    public void activarControles(){
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
   public ObservableList<Clientes> getClientes(){
        ArrayList<Clientes> listaCliente = new ArrayList();
        ArrayList<String> listaUsernameCliente = new ArrayList();
        String sql = "{call Sp_ListarClientes()}";
        int x=0;
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                listaCliente.add(new Clientes(
                        rs.getInt("usuarioId"),
                        rs.getString("userName"),
                        rs.getString("usuarioNombre"),
                        rs.getString("usuarioApellido"),
                        rs.getString("usuarioContrasena")
                ));
              listaUsernameCliente.add(x,rs.getString("userName"));
              x++;
            }
            listaUsernameClientes = FXCollections.observableList(listaUsernameCliente);
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

            new AutoCompleteComboBoxListener<>(cmbUsuarioCliente);
            desactivarControles();
            desactivarText();
            limpiarText();
    }
    
}
