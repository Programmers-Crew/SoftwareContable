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
                conexion = DriverManager.getConnection("jdbc:mysql://173.255.247.91:3306/alasgt_DBAlasGt?noAccessToProcedureBodies=true","alasgt_alasgt","Alasgt2020");
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
