/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.beans.Statement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 *
 * @author jaffe
 */
public class CrudSQL {

    java.sql.Statement st;
    ResultSet rs;
    variables var = new variables();
    ArrayList<AtencionesPantalla> listaVariables = new ArrayList<>();
    ArrayList<AtencionesSec> listaVariablesSec = new ArrayList<>();
    ConexionBD conexion = new ConexionBD();

    public void Insertar(String id, String nombre, String apellido, String direccion, String telefono, String email, String placa, String fechaNac) {
        try {
            Connection conectar = conexion.EstablecerConeccion();
            PreparedStatement st = conectar.prepareStatement(
                    "INSERT INTO cliente (identidad, nombre_cliente, apellido_cliente, direccion, telefono, email, placa, fecha_nac) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            st.setString(1, id);
            st.setString(2, nombre);
            st.setString(3, apellido);
            st.setString(4, direccion);
            st.setString(5, telefono);
            st.setString(6, email);
            st.setString(7, placa);
            st.setString(8, fechaNac);

            int filasAfectadas = st.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            st.close();
            conectar.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar el registro en la base de datos: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void InsertarReservacion(int cubiculo, int cliente, String lavado, String shampoo, String estado) {
        try {
            Connection conectar = conexion.EstablecerConeccion();
            PreparedStatement st = conectar.prepareStatement(
                    "INSERT INTO reservaciones (id_cubiculo, id_cliente,tipo_lavado,tipo_shampoo, estado) "
                    + "VALUES (?, ?, ?::tipo_lavado, ?::tipo_shampoo, ?::estado_lavado)"
            );

            st.setInt(1, cubiculo);
            st.setInt(2, cliente);
            st.setString(3, lavado);
            st.setString(4, shampoo);
            st.setString(5, estado);

            int filasAfectadas = st.executeUpdate();

            if (filasAfectadas > 0) {
                //JOptionPane.showMessageDialog(null, "Registro insertado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            st.close();
            conectar.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar el registro en la base de datos: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void InsertarAtenciones(String estado, String shampoo) {
        try {
            Connection conectar = conexion.EstablecerConeccion();
            PreparedStatement st = conectar.prepareStatement(
                    "INSERT INTO reservaciones (estado, shampoo) "
                    + "VALUES (?, ?::tipo_shampoo)"
            );

            st.setString(1, estado);
            st.setString(2, shampoo);

            int filasAfectadas = st.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            st.close();
            conectar.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar el registro en la base de datos: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void InsertarFactura(int cliente, double total, double subtotal, double isv) {
        try {
            Connection conectar = conexion.EstablecerConeccion();

            String consultaEmpleado = "SELECT * FROM reservaciones WHERE id_cliente = ?";
            PreparedStatement stConsulta = conectar.prepareStatement(consultaEmpleado);
            stConsulta.setInt(1, cliente);
            ResultSet rs = stConsulta.executeQuery();

            int empleado = -1; 
            int reservacion = -1; 

            if (rs.next()) {
                empleado = rs.getInt("id_empleado");
                reservacion = rs.getInt("id_reservaciones");
            }

            rs.close();
            stConsulta.close();

            PreparedStatement st = conectar.prepareStatement(
                    "INSERT INTO facturacion (id_reservaciones, id_cliente, id_empleado, total, subtotal, isv) "
                    + "VALUES (?, ?, ?, ?, ?, ?)"
            );

            st.setInt(1, reservacion);
            st.setInt(2, cliente);
            st.setInt(3, empleado);
            st.setDouble(4, total);
            st.setDouble(5, subtotal);
            st.setDouble(6, isv);

            int filasAfectadas = st.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            st.close();
            conectar.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar el registro en la base de datos: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void Mostrar(String identidad) {
        try {
            Connection conectar = conexion.EstablecerConeccion();
            st = conectar.createStatement();
            String sql = "select * from cliente Where identidad ='" + identidad + "'";
            rs = st.executeQuery(sql);

            if (rs.next()) {
                var.setCodigoCliente(rs.getString("id_cliente"));
                var.setNombre(rs.getString("nombre_cliente"));
                var.setApellido(rs.getString("apellido_cliente"));
                var.setPlaca(rs.getString("placa"));
            } else {
                var.setCodigoCliente("");
                var.setNombre("");
                var.setApellido("");
                var.setPlaca("");
                JOptionPane.showMessageDialog(null, "No se encontró ningún registro.", "Sin registro", JOptionPane.INFORMATION_MESSAGE);
            }
            rs.close();
            st.close();
            conectar.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en el sistema de búsqueda." + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<AtencionesPantalla> ObtenerAtencionesEsperaLav() {
        try {
            Connection conectar = conexion.EstablecerConeccion();
            st = conectar.createStatement();
            String sql = "select * from reservaciones as r\n"
                    + "inner join cliente as c on c.id_cliente=r.id_cliente\n"
                    + "inner join empleados as e on e.id_empleado=r.id_empleado\n"
                    + "WHERE r.estado = 'No Iniciado'\n"
                    + "Order by r.fecha_reservacion asc;";

            rs = st.executeQuery(sql);

            while (rs.next()) {

                AtencionesPantalla varP = new AtencionesPantalla();

                varP.setCodigo(rs.getString("numero_ticket"));
                varP.setNombre(rs.getString("nombre_cliente"));
                varP.setEmpleado(rs.getString("nombre_empleado"));
                varP.setPlaca(rs.getString("placa"));
                varP.setIdAtencion(rs.getInt("id_reservaciones"));
                varP.setEstado(rs.getString("estado"));
                varP.setTipoLavado(rs.getString("tipo_lavado"));

                listaVariables.add(varP);

            }

            rs.close();
            st.close();
            conectar.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en el sistema de búsqueda." + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return listaVariables;
    }
    
    public ArrayList<AtencionesSec> ObtenerAtencionesEsperaSec() {
        try {
            Connection conectar = conexion.EstablecerConeccion();
            st = conectar.createStatement();
            String sql = "select * from reservaciones as r\n"
                    + "inner join cliente as c on c.id_cliente=r.id_cliente\n"
                    + "inner join empleados as e on e.id_empleado=r.id_empleado\n"
                    + "WHERE r.estado = 'En Espera Secado'\n"
                    + "Order by r.fecha_reservacion asc;";

            rs = st.executeQuery(sql);

            while (rs.next()) {

                AtencionesSec varSec = new AtencionesSec();

                varSec.setCodigo(rs.getString("numero_ticket"));
                varSec.setNombre(rs.getString("nombre_cliente"));
                varSec.setEmpleado(rs.getString("nombre_empleado"));
                varSec.setPlaca(rs.getString("placa"));
                varSec.setIdAtencion(rs.getInt("id_reservaciones"));
                varSec.setEstado(rs.getString("estado"));
                varSec.setTipoLavado(rs.getString("tipo_lavado"));

                listaVariablesSec.add(varSec);

            }

            rs.close();
            st.close();
            conectar.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en el sistema de búsqueda." + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return listaVariablesSec;
    }

    public String Update(String estado, int idReservacion) {
        try {
            Connection conectar = conexion.EstablecerConeccion();
            st = conectar.createStatement();
            String sql = "UPDATE reservaciones "
                    + "SET estado = '" + estado + "' "
                    + "WHERE numero_ticket = " + idReservacion;
            int filasAfectadas = st.executeUpdate(sql);

            if (filasAfectadas > 0) {
                //JOptionPane.showMessageDialog(null, "Actualización exitosa.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar ningún registro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            st.close();
            conectar.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la actualización: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return "Actualizado";
    }

    public String ObtenerEstado(String estado) {
        try {
            Connection conectar = conexion.EstablecerConeccion();
            st = conectar.createStatement();
            String sql = "select * from atenciones as a\n"
                    + "inner join reservaciones as r on r.id_reservaciones=a.id_reservaciones\n"
                    + "WHERE a.estado = 'En Lavado';";
            rs = st.executeQuery(sql);

            rs.close();
            st.close();
            conectar.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la actualizacion." + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return estado;
    }

    public int Delete(int estado) {
        try {
            Connection conectar = conexion.EstablecerConeccion();
            st = conectar.createStatement();
            String sql = "select * from reservaciones\n"
                    + "WHERE id_reservaciones = " + estado + ";";
            rs = st.executeQuery(sql);

            rs.close();
            st.close();
            conectar.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la actualizacion." + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return estado;
    }

    public String ObtenerTipoLavado(int idAtencion) {
        String tipoLavado = "";

        try {
            Connection conectar = conexion.EstablecerConeccion();
            st = conectar.createStatement();
            String sql = "select * from reservaciones\n"
                    + "WHERE numero_ticket = " + idAtencion + ";";
            rs = st.executeQuery(sql);

            if (rs.next()) {

                tipoLavado = rs.getString("tipo_lavado");

            }

            rs.close();
            st.close();
            conectar.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la actualizacion." + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return tipoLavado;
    }

}
