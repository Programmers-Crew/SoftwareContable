package org.appcontablealas.bean;


public class Clientes {
    
    private int usuarioId;
    private String usuarioNombre;
    private String usuarioApellido;
    private String userName; 
    private String usuarioContrasena;
    private String usuarioCorreo;

    public Clientes() {
    }

    public Clientes(int usuarioId, String usuarioNombre, String usuarioApellido, String userName, String usuarioContrasena, String usuarioCorreo) {
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.usuarioApellido = usuarioApellido;
        this.userName = userName;
        this.usuarioContrasena = usuarioContrasena;
        this.usuarioCorreo = usuarioCorreo;
    }

    public String getUsuarioCorreo() {
        return usuarioCorreo;
    }

    public void setUsuarioCorreo(String usuarioCorreo) {
        this.usuarioCorreo = usuarioCorreo;
    }


    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUsuarioContrasena() {
        return usuarioContrasena;
    }

    public void setUsuarioContrasena(String usuarioContrasena) {
        this.usuarioContrasena = usuarioContrasena;
    }


    
}
