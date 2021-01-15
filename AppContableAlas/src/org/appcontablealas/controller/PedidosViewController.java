/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appcontablealas.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Diego
 */
public class PedidosViewController implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private Pane btnInicio;
    @FXML
    private Pane btnProveedores;
    @FXML
    private Pane btnProductos;
    @FXML
    private AnchorPane anchor1;
    @FXML
    private JFXTextField txtCodigoPedido;
    @FXML
    private JFXTextField txtEstadoPedido;
    @FXML
    private JFXTextField txtCostoPedido;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private ComboBox<?> cmbMensajero;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private TableView<?> tblPedido;
    @FXML
    private TableColumn<?, ?> colCodigoPedido;
    @FXML
    private TableColumn<?, ?> colFechaPedido;
    @FXML
    private TableColumn<?, ?> colDireccionPedido;
    @FXML
    private TableColumn<?, ?> colClientePedido;
    @FXML
    private TableColumn<?, ?> colTelefonoPedido;
    @FXML
    private TableColumn<?, ?> colDescPedido;
    @FXML
    private TableColumn<?, ?> colMensajeroPedido;
    @FXML
    private TableColumn<?, ?> colMontoPedido;
    @FXML
    private TableColumn<?, ?> colCostoPedido;
    @FXML
    private TableColumn<?, ?> colFormaPagoPedido;
    @FXML
    private TableColumn<?, ?> colEstadoPedido;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private ComboBox<?> cmbCodigoPedido;
    @FXML
    private ComboBox<?> cmbEstadoPedido;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void validarUsuario(KeyEvent event) {
    }

    @FXML
    private void btnAgregar(MouseEvent event) {
    }

    @FXML
    private void btnEditar(MouseEvent event) {
    }

    @FXML
    private void btnEliminar(MouseEvent event) {
    }

    @FXML
    private void seleccionarElementosProductos(MouseEvent event) {
    }

    @FXML
    private void btnBuscar(MouseEvent event) {
    }

    @FXML
    private void atajosInventario(KeyEvent event) {
    }

    @FXML
    private void cargarPedidos(Event event) {
    }

    
}
