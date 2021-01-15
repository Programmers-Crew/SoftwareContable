package org.appcontablealas.bean;


public class Clientes {
    
    private int usuarioId;
    private String usuarioNombre;
    private String usuarioApellido;
    private String userName; 
    private String usuarioContrasena;

    public Clientes() {
    }

    public Clientes(int usuarioId, String usuarioNombre, String usuarioApellido, String userName, String usuarioContrasena) {
       this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.usuarioApellido = usuarioApellido;
        this.userName = userName;
        this.usuarioContrasena = usuarioContrasena;    }

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
