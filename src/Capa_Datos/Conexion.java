/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Datos;

/**
 *
 * @author jumem
 */
import java.sql.*;

public class Conexion {
    // SE crean variables para 
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String user = "root";
    private final String pwd = "";

    public Conexion() //constructor
    {
    }
//=> no se XD
    public ResultSet Listar(String Cad, String dec) {
        try {
           
            Connection cn = DriverManager.getConnection(url+dec, user, pwd);
            PreparedStatement da = cn.prepareStatement(Cad);
            ResultSet tbl = da.executeQuery();
            return tbl;
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }
//=> muesta si el comado se ingreso correctamente.
    public String AnalisisUpdates(int r) {  
        if (r == 0) {
            return "No se afecto ninguna fila, verifique la información";
        } else {
            if (r == 1) {
                return "Se afecto " + r + " fila";
            } else {
                return "Se afectaron " + r + " filas";
            }
        }
    }
    //=>  Entablamos el enlase con la baser de datos.
    public String Ejecutar(String Cad, String dec) {
        try {
            Connection cn = DriverManager.getConnection(url+dec, user, pwd);
            PreparedStatement da = cn.prepareStatement(Cad);
            int r = da.executeUpdate();
            return AnalisisUpdates(r);

        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage()+"error aca");
            return "Error " + e.getMessage();
        }
    }
}
