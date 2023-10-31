/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Negocio;

import Capa_Datos.Conexion;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author final
 */
public class DataArea {
    
    private int area_id;
    private String area_nom;
    private String area_state;
    private final String dec = "GEMPLEADO";
    private int a ;

    public DataArea() {
    }

    /*=> Hace una consulta a la tabla area de la base de datos, convierte las tuplas en objetos 
    y introduce estos en un arraylist.*/
    public ArrayList<DataArea> ListaAreas() {
        ArrayList lista2 = new ArrayList();
        try {
            Conexion objmod = new Conexion();
            ResultSet tabla = objmod.Listar("select * from area", dec);
            DataArea objart;

            while (tabla.next()) {
                objart = new DataArea();
                objart.setArea_id(tabla.getInt("area_id"));
                objart.setArea_nom(tabla.getString("area_nom"));
                objart.setArea_state(tabla.getString("area_state"));
                lista2.add(objart);
            }
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return lista2;
    }
    
    //=> Busca el nombre del area usando su ID.
    public String BuscarArea(int IdArea) {
        ArrayList<DataArea> lista2 = new ArrayList();
        lista2 = ListaAreas();
        String nombreArea =" ";
        for (DataArea x : lista2) {
            if ((x.getArea_id() == IdArea)) {
                nombreArea = x.getArea_nom();
            }
        }
        return nombreArea;
    }
    
    //=> Busca el ID del area usando su nombre.
    public int BuscarArea(String NameArea) {
        ArrayList<DataArea> lista2 = new ArrayList();
        lista2 = ListaAreas();
        int codigoArea = 0;
        for (DataArea x : lista2) {
            if ((NameArea.equals(x.getArea_nom()))) {
                codigoArea = x.getArea_id();
            }
        }
        return codigoArea;
    }

    //=> Getters y setters.
    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getArea_nom() {
        return area_nom;
    }

    public void setArea_nom(String area_nom) {
        this.area_nom = area_nom;
    }

    public String getArea_state() {
        return area_state;
    }

    public void setArea_state(String area_state) {
        this.area_state = area_state;
    }

}
