package org.appcontablealas.bean;


public class formaPago{
    
    private int formaPagoId;
    private String formaPagoDesc;

    public formaPago() {
    }

    public formaPago(int formaPagoId, String formaPagoDesc) {
        this.formaPagoId = formaPagoId;
        this.formaPagoDesc = formaPagoDesc;
    }

    public int getFormaPagoId() {
        return formaPagoId;
    }

    public void setFormaPagoId(int formaPagoId) {
        this.formaPagoId = formaPagoId;
    }

    public String getFormaPagoDesc() {
        return formaPagoDesc;
    }

    public void setFormaPagoDesc(String formaPagoDesc) {
        this.formaPagoDesc = formaPagoDesc;
    }
}
