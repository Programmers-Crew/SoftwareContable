package org.appcontablealas.bean;

import java.util.Date;


public class Pedido {
    
    private int pedidoId;
    private Date pedidoFecha;
    private String pedidoDireccionInicio;
    private String pedidoDireccionFinal;
    private String cliente;
    private String nombreReceptor;
    private String pedidoTelefonoReceptor;
    private String  pedidoDesc;
    private String mensajero;
    private Double pedidoMonto;
    private Double pedidoCosto;
    private String formaPagoDesc;
    private String  estadoPedidoDesc;

    public Pedido() {
    }

    public Pedido(int pedidoId, Date pedidoFecha, String pedidoDireccionInicio, String pedidoDireccionFinal, String cliente, String nombreReceptor, String pedidoTelefonoReceptor, String pedidoDesc, String mensajero, Double pedidoMonto, Double pedidoCosto, String formaPagoDesc, String estadoPedidoDesc) {
        this.pedidoId = pedidoId;
        this.pedidoFecha = pedidoFecha;
        this.pedidoDireccionInicio = pedidoDireccionInicio;
        this.pedidoDireccionFinal = pedidoDireccionFinal;
        this.cliente = cliente;
        this.nombreReceptor = nombreReceptor;
        this.pedidoTelefonoReceptor = pedidoTelefonoReceptor;
        this.pedidoDesc = pedidoDesc;
        this.mensajero = mensajero;
        this.pedidoMonto = pedidoMonto;
        this.pedidoCosto = pedidoCosto;
        this.formaPagoDesc = formaPagoDesc;
        this.estadoPedidoDesc = estadoPedidoDesc;
    }
 
    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Date getPedidoFecha() {
        return pedidoFecha;
    }

    public void setPedidoFecha(Date pedidoFecha) {
        this.pedidoFecha = pedidoFecha;
    }

    public String getPedidoDireccionInicio() {
        return pedidoDireccionInicio;
    }

    public void setPedidoDireccionInicio(String pedidoDireccionInicio) {
        this.pedidoDireccionInicio = pedidoDireccionInicio;
    }

    public String getPedidoDireccionFinal() {
        return pedidoDireccionFinal;
    }

    public void setPedidoDireccionFinal(String pedidoDireccionFinal) {
        this.pedidoDireccionFinal = pedidoDireccionFinal;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNombreReceptor() {
        return nombreReceptor;
    }

    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }

    public String getPedidoTelefonoReceptor() {
        return pedidoTelefonoReceptor;
    }

    public void setPedidoTelefonoReceptor(String pedidoTelefonoReceptor) {
        this.pedidoTelefonoReceptor = pedidoTelefonoReceptor;
    }

    public String getPedidoDesc() {
        return pedidoDesc;
    }

    public void setPedidoDesc(String pedidoDesc) {
        this.pedidoDesc = pedidoDesc;
    }

    public String getMensajero() {
        return mensajero;
    }

    public void setMensajero(String mensajero) {
        this.mensajero = mensajero;
    }

    public Double getPedidoMonto() {
        return pedidoMonto;
    }

    public void setPedidoMonto(Double pedidoMonto) {
        this.pedidoMonto = pedidoMonto;
    }

    public Double getPedidoCosto() {
        return pedidoCosto;
    }

    public void setPedidoCosto(Double pedidoCosto) {
        this.pedidoCosto = pedidoCosto;
    }

    public String getFormaPagoDesc() {
        return formaPagoDesc;
    }

    public void setFormaPagoDesc(String formaPagoDesc) {
        this.formaPagoDesc = formaPagoDesc;
    }

    public String getEstadoPedidoDesc() {
        return estadoPedidoDesc;
    }

    public void setEstadoPedidoDesc(String estadoPedidoDesc) {
        this.estadoPedidoDesc = estadoPedidoDesc;
    }

    
    
}
