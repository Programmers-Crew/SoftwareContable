package org.appcontablealas.db;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    
    private java.sql.Connection conexion;
    public static Conexion instancia;
    
    public Conexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
                conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBAlasGt?useSSL=False", "root" , "Fernando2003");
                    }catch(ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e){
                            e.printStackTrace();
                    }
}


    public static Conexion getIntance(){
        if(instancia == null){
               instancia = new Conexion();   
        }
        return instancia;
    }

    public java.sql.Connection getConexion(){
        return conexion;   
    }

    public void setConexion(java.sql.Connection conexion){
        this.conexion = conexion;
    }
}
