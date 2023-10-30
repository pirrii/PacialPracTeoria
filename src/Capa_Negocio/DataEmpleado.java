/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Negocio;

/**
 *
 * @author jumem
 */
import java.util.ArrayList;
import java.sql.*;
import Capa_Datos.Conexion;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DataEmpleado {
    
    private int emple_id;
    private int emple_doc;
    private String emple_nom;
    private String emple_ape;
    private String emple_tel;
    private String emple_state;
    private final String dec = "GEMPLEADO";

    public DataEmpleado() {
    }

    //=> Manda la instruccion para crea un empleado en la base de datos.
    public String GrabarEmpleado() {
        Conexion objmod = new Conexion();
        String cad = "insert into EMPLEADO values(default," + this.getEmple_doc()
                + ",'" + this.getEmple_nom() + "','" + this.getEmple_ape()
                + "','" + this.getEmple_tel() + "','" + this.getEmple_state() + "')";
        System.out.println(cad);
        return objmod.Ejecutar(cad, dec);
    }
    
    //=> Manda la instruccion para hacer cambios a la tabla empleado en la base de datos.
    public String EditarEmpleado() {
        Conexion objmod = new Conexion();
        String cad = "update empleado set emple_doc=" + this.getEmple_doc() + ",emple_nom='"
                + this.getEmple_nom() + "',emple_ape='" + this.getEmple_ape() + "',emple_tel="
                + this.getEmple_tel() + ",emple_state='" + this.getEmple_state()
                + "' where emple_id=" + this.getEmple_id();
        System.out.println(cad);
        return objmod.Ejecutar(cad, dec);
    }
    
    //=> Trae fecha actual y lo cambia a un formato legible para la base de datos.
    public Date FechaDateSat(String Fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = sdf.parse(Fecha);
        java.sql.Date fechaActualSQL = new java.sql.Date(utilDate.getTime());

        return fechaActualSQL;
    }
    
    //=> Manda la instruccion para desactivar el empleado.
    public String DesactivarEmpleado(String cod) {
        Conexion objmod = new Conexion();
        String cad = "update empleado set emple_state='INACTIVO' where emple_id=" + cod;
        System.out.println(cad);
        return objmod.Ejecutar(cad, dec);
    }
    
    /*=> Hace una consulta a la tabla empleado de la base de datos, convierte las tuplas en objetos 
    y introduce estos en un arraylist.*/
    public ArrayList<DataEmpleado> ListaEmpleadosGenerales(){
        ArrayList lista2 = new ArrayList();
        try {
            Conexion objmod = new Conexion();
            ResultSet tabla = objmod.Listar("select * from empleado", dec);
            DataEmpleado objart;

            while (tabla.next()) {
                    objart = new DataEmpleado();
                    objart.setEmple_id(tabla.getInt("emple_id"));
                    objart.setEmple_doc(tabla.getInt("emple_doc"));
                    objart.setEmple_nom(tabla.getString("emple_nom"));
                    objart.setEmple_ape(tabla.getString("emple_ape"));
                    objart.setEmple_tel(tabla.getString("emple_tel"));
                    objart.setEmple_state(tabla.getString("emple_state"));
                    lista2.add(objart);
            }
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return lista2;
    }
    
    /*=> Hace una consulta a la tabla empleado de la base de datos, identifica las tuplas con atributo INACTIVO, 
    las convierte en objetos y introduce estos en un arraylist.*/
    public ArrayList<DataEmpleado> ListaEmpleadosInactivos() {
        ArrayList lista2 = new ArrayList();
        try {
            Conexion objmod = new Conexion();
            ResultSet tabla = objmod.Listar("select * from empleado", dec);
            DataEmpleado objart;

            while (tabla.next()) {
                if (tabla.getString("emple_state").equals("INACTIVO")) {
                    objart = new DataEmpleado();
                    objart.setEmple_id(tabla.getInt("emple_id"));
                    objart.setEmple_doc(tabla.getInt("emple_doc"));
                    objart.setEmple_nom(tabla.getString("emple_nom"));
                    objart.setEmple_ape(tabla.getString("emple_ape"));
                    objart.setEmple_tel(tabla.getString("emple_tel"));
                    objart.setEmple_state(tabla.getString("emple_state"));
                    lista2.add(objart);
                }
            }
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return lista2;
    }
    
    /*=> Hace una consulta a la tabla empleado de la base de datos, identifica las tuplas con atributo ACTIVO, 
    las convierte en objetos y introduce estos en un arraylist.*/
    public ArrayList<DataEmpleado> ListaEmpleadosActivos() {
        ArrayList lista2 = new ArrayList();
        try {
            Conexion objmod = new Conexion();
            ResultSet tabla = objmod.Listar("select * from empleado", dec);
            DataEmpleado objart;

            while (tabla.next()) {
                if (tabla.getString("emple_state").equals("ACTIVO")) {
                    objart = new DataEmpleado();
                    objart.setEmple_id(tabla.getInt("emple_id"));
                    objart.setEmple_doc(tabla.getInt("emple_doc"));
                    objart.setEmple_nom(tabla.getString("emple_nom"));
                    objart.setEmple_ape(tabla.getString("emple_ape"));
                    objart.setEmple_tel(tabla.getString("emple_tel"));
                    objart.setEmple_state(tabla.getString("emple_state"));
                    lista2.add(objart);
                }
            }

        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return lista2;
    }
    
    //=> Busca la primary key de un empleado haciendo uso de su documento de identidad.
public int buscarEmpleDoc(String Documento) {
        ArrayList<DataEmpleado> lista2 = new ArrayList();
        lista2 = ListaEmpleadosGenerales();
        int CodigoEmpleado = 0;
       
        for (DataEmpleado x : lista2) {
            System.out.println("el codigo es antes de confirmar: "+CodigoEmpleado);
            if ((Integer.parseInt(Documento)) == x.getEmple_doc()){
                CodigoEmpleado = x.getEmple_id();
                System.out.println("el codigo es DESPUES: "+CodigoEmpleado);
            }
        }
        
        return CodigoEmpleado;
    }
    
    //=> Getters y setters.
    public int getEmple_id() {
        return emple_id;
    }

    public void setEmple_id(int emple_id) {
        this.emple_id = emple_id;
    }

    public int getEmple_doc() {
        return emple_doc;
    }

    public void setEmple_doc(int emple_doc) {
        this.emple_doc = emple_doc;
    }

    public String getEmple_nom() {
        return emple_nom;
    }

    public void setEmple_nom(String emple_nom) {
        this.emple_nom = emple_nom;
    }

    public String getEmple_ape() {
        return emple_ape;
    }

    public void setEmple_ape(String emple_ape) {
        this.emple_ape = emple_ape;
    }

    public String getEmple_tel() {
        return emple_tel;
    }

    public void setEmple_tel(String emple_tel) {
        this.emple_tel = emple_tel;
    }

    public String getEmple_state() {
        return emple_state;
    }

    public void setEmple_state(String emple_state) {
        this.emple_state = emple_state;
    }
}