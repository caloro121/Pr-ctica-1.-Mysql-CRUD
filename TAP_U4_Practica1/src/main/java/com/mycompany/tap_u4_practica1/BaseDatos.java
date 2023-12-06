
package com.mycompany.tap_u4_practica1;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
        
public class BaseDatos {
    Connection conexion;
    Statement transaccion;
    ResultSet cursor;
    
    String cadenaConexion = "mysql://ux3qjlzkfkjwas3u:vWTHWEWyBkENg2dUbAvk@bb5u63jnhrpdbnm9blun-mysql.services.clever-cloud.com:3306/bb5u63jnhrpdbnm9blun";
    String usuario = "ux3qjlzkfkjwas3u";
    String pass = "vWTHWEWyBkENg2dUbAvk";
    
    public BaseDatos(){
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(cadenaConexion, usuario, pass);
            transaccion = conexion.createStatement();
            
        }catch(SQLException e){
        
        }catch(ClassNotFoundException e){
        
        }
    }
    
    public boolean insertar(Persona p){
        String SQL_Insertar = "INSERT INTO `Persona` (`ID`, `NOMBRE`, `DOMICILIO`, `TELEFONO`) VALUES (NULL, '%Nom%', '%DOM%', '%TEL%');";
        
        SQL_Insertar = SQL_Insertar.replace("%NOM%", p.nombre);
        SQL_Insertar = SQL_Insertar.replace("%DOM%", p.domicilio);
        SQL_Insertar = SQL_Insertar.replace("%TEL%", p.telefono);
        
        try{
            transaccion.execute(SQL_Insertar);
        }catch(SQLException e){
            return false;
        }
        
        
        return true;
    }
    
    public ArrayList<Persona> mostrarTodos(){
     ArrayList<Persona> datos = new ArrayList<Persona>();
    String SQL_consulta = "SELECT * FROM 'persona'";
    
        try {
            
            cursor = transaccion.executeQuery(SQL_consulta);
            
            if(cursor.next()){
                do{
                    Persona p = new Persona(
                            cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4)
                    );
                    datos.add(p);
                }while(cursor.next());
            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datos;
    }

    public Persona obtenerporID(String IDaBuscar){
        String SQL_consulta = "SELECT * FROM 'persona' WHERE 'ID='"+IDaBuscar;
    
        try {
            
            cursor = transaccion.executeQuery(SQL_consulta);
            
            if(cursor.next()){
                
                    Persona p = new Persona(
                            cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4)
                    );
                    return p;
                
            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Persona(-1,"","","");
    }
    
    public boolean eliminar(String IDaEliminar){
        String SQL_eliminar = "DELETE FROM 'persona' WHERE 'ID' = "+IDaEliminar;
        
        try{
        transaccion.execute(SQL_eliminar);
        }catch(SQLException ex){
            return false;
        }
        return true;
    }
    
    public boolean actualizar(Persona p){
    
        String SQL_Actualizar = "UPDATE `Persona` SET `ID`, `NOMBRE`='%NOM%', `DOMICILIO`='%DOM%', `TELEFONO`='%TEL%' WHERE  `ID`="+p.id;
        
        SQL_Actualizar = SQL_Actualizar.replace("%NOM%", p.nombre);
        SQL_Actualizar = SQL_Actualizar.replace("%DOM%", p.domicilio);
        SQL_Actualizar = SQL_Actualizar.replace("%TEL%", p.telefono);
        
        try {
            transaccion.executeUpdate(SQL_Actualizar);
        } catch (SQLException e) {
            return false;
        }
        
        return true;
    }

}