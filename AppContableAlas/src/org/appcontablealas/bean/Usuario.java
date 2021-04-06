package org.appcontablealas.bean;


public class Usuario {
    private int usuarioId;
    private String usuarioNombre;
    private String usuarioApellido;
    private String userName; 
    private String usuarioContrasena;
    private String tipoUsuarioDesc;
    private String usuarioCorreo;
    private String telefono;

    public Usuario() {
    }

    public Usuario(int usuarioId, String usuarioNombre, String usuarioApellido, String userName, String usuarioContrasena, String tipoUsuarioDesc, String usuarioCorreo, String telefono) {
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.usuarioApellido = usuarioApellido;
        this.userName = userName;
        this.usuarioContrasena = usuarioContrasena;
        this.tipoUsuarioDesc = tipoUsuarioDesc;
        this.usuarioCorreo = usuarioCorreo;
        this.telefono = telefono;
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

    public String getUsuarioCorreo() {
        return usuarioCorreo;
    }

    public void setUsuarioCorreo(String usuarioCorreo) {
        this.usuarioCorreo = usuarioCorreo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
    
}
