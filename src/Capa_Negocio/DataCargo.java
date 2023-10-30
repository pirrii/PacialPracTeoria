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
 * @author jumem
 */
public class DataCargo {

    private int cargo_id;
    private String cargo_nom;
    private int cargo_pay;
    private String cargo_state;
    private final String dec = "GEMPLEADO";
    private int Jefe = 1;
    private int Gerente = 5;
    private int Slave = 10;

    public DataCargo() {
    }

    /*=> Hace una consulta a la tabla cargo de la base de datos, convierte las tuplas en objetos 
    y introduce estos en un arraylist.*/
    public ArrayList<DataCargo> ListaCargos() {
        ArrayList lista2 = new ArrayList();
        try {
            Conexion objmod = new Conexion();
            ResultSet tabla = objmod.Listar("select * from cargo", dec);
            DataCargo objart;

            while (tabla.next()) {
                objart = new DataCargo();
                objart.setCargo_id(tabla.getInt("cargo_id"));
                objart.setCargo_nom(tabla.getString("cargo_nom"));
                objart.setCargo_pay(tabla.getInt("cargo_pay"));
                objart.setCargo_state(tabla.getString("cargo_state"));
                lista2.add(objart);
            }
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return lista2;
    }
    
    //=> Busca el nombre del cargo usando su id.
    public String BuscarCarg(int IdCarg) {
        ArrayList<DataCargo> lista2 = new ArrayList();
        lista2 = ListaCargos();
        String nombreCargo = "";
        for (DataCargo x : lista2) {
            if ((x.getCargo_id() == IdCarg)) {
                nombreCargo = x.getCargo_nom();
            }
        }
        return nombreCargo;
    }

    //=> Busca el id de cargo usando su nombre.
    public int BuscarCarg(String NameCarg) {
        ArrayList<DataCargo> lista2 = new ArrayList();
        lista2 = ListaCargos();
        int codigoCargo = 0;
        for (DataCargo x : lista2) {
            if (NameCarg.equals(x.getCargo_nom())) {
                codigoCargo = x.getCargo_id();
            }
        }
        return codigoCargo;
    }
    
    //=> Getters y setters.
    public int getCargo_id() {
        return cargo_id;
    }

    public void setCargo_id(int cargo_id) {
        this.cargo_id = cargo_id;
    }

    public String getCargo_nom() {
        return cargo_nom;
    }

    public void setCargo_nom(String cargo_nom) {
        this.cargo_nom = cargo_nom;
    }

    public int getCargo_pay() {
        return cargo_pay;
    }

    public void setCargo_pay(int cargo_pay) {
        this.cargo_pay = cargo_pay;
    }

    public String getCargo_state() {
        return cargo_state;
    }

    public void setCargo_state(String cargo_state) {
        this.cargo_state = cargo_state;
    }

    public int getJefe() {
        return Jefe;
    }

    public void setJefe(int Jefe) {
        this.Jefe = Jefe;
    }

    public int getGerente() {
        return Gerente;
    }

    public void setGerente(int Gerente) {
        this.Gerente = Gerente;
    }

    public int getSlave() {
        return Slave;
    }

    public void setSlave(int Slave) {
        this.Slave = Slave;
    }

    
    

}
