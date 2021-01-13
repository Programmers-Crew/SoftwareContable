package org.appcontablealas.bean;


public class EstadoPedido {
    
    private int estadoPedidoId;
    private String estadoPedidoDesc;

    public EstadoPedido() {
    }

    public EstadoPedido(int estadoPedidoId, String estadoPedidoDesc) {
        this.estadoPedidoId = estadoPedidoId;
        this.estadoPedidoDesc = estadoPedidoDesc;
    }

    public int getEstadoPedidoId() {
        return estadoPedidoId;
    }

    public void setEstadoPedidoId(int estadoPedidoId) {
        this.estadoPedidoId = estadoPedidoId;
    }

    public String getEstadoPedidoDesc() {
        return estadoPedidoDesc;
    }

    public void setEstadoPedidoDesc(String estadoPedidoDesc) {
        this.estadoPedidoDesc = estadoPedidoDesc;
    }
}
