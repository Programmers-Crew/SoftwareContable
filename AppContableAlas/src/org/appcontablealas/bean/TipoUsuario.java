package org.appcontablealas.bean;


public class TipoUsuario {
    
    private int tipoUsuarioId;
    private String tipoUsuarioDesc;

    public TipoUsuario() {
    }

    public TipoUsuario(int tipoUsuarioId, String tipoUsuarioDesc) {
        this.tipoUsuarioId = tipoUsuarioId;
        this.tipoUsuarioDesc = tipoUsuarioDesc;
    }

    public int getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(int tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
    }

    public String getTipoUsuarioDesc() {
        return tipoUsuarioDesc;
    }

    public void setTipoUsuarioDesc(String tipoUsuarioDesc) {
        this.tipoUsuarioDesc = tipoUsuarioDesc;
    }
    
    
    
}
