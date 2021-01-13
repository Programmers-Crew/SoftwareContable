package org.appcontablealas.bean;


public class Usuario {
    private int usuarioId;
    private String userName; 
    private String usuarioNombre;
    private String usuarioApellido;
    private String usuarioContrasena;
    private String tipoUsuarioDesc;

    public Usuario() {
    }

    public Usuario(int usuarioId, String userName, String usuarioNombre, String usuarioApellido, String usuarioContrasena, String tipoUsuarioDesc) {
        this.usuarioId = usuarioId;
        this.userName = userName;
        this.usuarioNombre = usuarioNombre;
        this.usuarioApellido = usuarioApellido;
        this.usuarioContrasena = usuarioContrasena;
        this.tipoUsuarioDesc = tipoUsuarioDesc;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getUsuarioApellido() {
        return usuarioApellido;
    }

    public void setUsuarioApellido(String usuarioApellido) {
        this.usuarioApellido = usuarioApellido;
    }

    public String getUsuarioContrasena() {
        return usuarioContrasena;
    }

    public void setUsuarioContrasena(String usuarioContrasena) {
        this.usuarioContrasena = usuarioContrasena;
    }

    public String getTipoUsuarioDesc() {
        return tipoUsuarioDesc;
    }

    public void setTipoUsuarioDesc(String tipoUsuarioDesc) {
        this.tipoUsuarioDesc = tipoUsuarioDesc;
    }
    
    
}
