
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jaffe
 */
public class ConexionBD {
    
    Connection conectar = null;
    
    private String usuario = "postgres";
    private String contrasenia = "Lafamilia";
    private String db = "CarWashDB";
    private String ip = "localhost";
    private String puerto = "5432";
    
    private String url = "jdbc:postgresql://" + ip + ":" + puerto + "/" + db;
    
    public Connection EstablecerConeccion(){
        try{
            Class.forName("org.postgresql.Driver");
            conectar = DriverManager.getConnection(url,usuario,contrasenia);
             //JOptionPane.showMessageDialog(null,"Se ha realizado la coneccion correctamente.");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al conectar a la base de datos."+ e.toString());
        }
        
        return conectar;
    }
    
}
