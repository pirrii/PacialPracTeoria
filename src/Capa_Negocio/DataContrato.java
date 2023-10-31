/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Negocio;

import Capa_Datos.Conexion;
import com.toedter.calendar.JDateChooser;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author jumem
 */
public class DataContrato {
    
    private int contrato_id;
    private java.sql.Date contrato_fechaI;
    private java.sql.Date contrato_fechaF;
    private String contrato_state;
    private int fkemple_id;
    private int fkarea_id;
    private int fkcargo_id;
    private final String dec = "GEMPLEADO";

    public DataContrato() {
    }

    //=> Manda la instruccion para crea un contrato en la base de datosa.
    public String GrabarContrato() {
        Conexion objmod = new Conexion();
        String cad = "insert into contrato values(default,'" + this.getContrato_fechaI()
                + "','" + this.getContrato_fechaF() + "','" + this.getContrato_state()
                + "'," + this.getFkemple_id() + "," + this.getFkarea_id() + ","
                + this.getFkcargo_id() + ");";
        System.out.println(cad);
        return objmod.Ejecutar(cad, dec);
    }
    
    //=> Manda la instruccion para hacer cambios a la tabla contrato en la base de datos.
    public String EditarContrato() {
        Conexion objmod = new Conexion();
        String cad = "update contrato set contrato_fechaI='" + this.getContrato_fechaI() + "',contrato_fechaF='"
                + this.getContrato_fechaF() + "',contrato_state='" + this.getContrato_state() + "',fkemple_id="
                + this.getFkemple_id() + ",fkarea_id=" + this.getFkarea_id() + ",fkcargo_id="
                + this.getFkcargo_id() + " where contrato_id=" + this.getContrato_id();
        System.out.println(cad);
        return objmod.Ejecutar(cad, dec);
    }

    //=> Manda la instruccion para desactivar el contrato.
    public String DesactivarContrato() {
        Conexion objmod = new Conexion();
        String cad = "update contrato set contrato_state='INACTIVO' where contrato_id=" + this.getContrato_id();
        System.out.println(cad);
        return objmod.Ejecutar(cad, dec);
    }
    
    /*=> Hace una consulta a la tabla contrato de la base de datos, convierte las tuplas en objetos 
    y introduce estos en un arraylist.*/
    public ArrayList<DataContrato> ListaContratos() {
        ArrayList lista2 = new ArrayList();
        try {
            Conexion objmod = new Conexion();
            ResultSet tabla = objmod.Listar("select * from contrato", dec);
            DataContrato objart;

            while (tabla.next()) {
                objart = new DataContrato();
                objart.setContrato_id(tabla.getInt("contrato_id"));
                objart.setContrato_fechaI(tabla.getDate("contrato_fechaI"));
                objart.setContrato_fechaF(tabla.getDate("contrato_fechaF"));
                objart.setContrato_state(tabla.getString("contrato_state"));
                objart.setFkemple_id(tabla.getInt("fkemple_id"));
                objart.setFkarea_id(tabla.getInt("fkarea_id"));
                objart.setFkcargo_id(tabla.getInt("fkcargo_id"));
                lista2.add(objart);
            }
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return lista2;
    }
    
    //=> Busca el ID de contrato de aquel que esta ACTIVO por la foreign key ID empleado.
    public int BuscarContra(int emple_id) {
        ArrayList<DataContrato> lista2 = new ArrayList();
        lista2 = ListaContratos();
        int codigoContra = 0;
        if (!lista2.isEmpty()) {
            for (DataContrato x : lista2) {
                if ((x.getFkemple_id()==emple_id )) {
                    if (x.getContrato_state().equals("ACTIVO")) {
                        codigoContra = x.getContrato_id();
                    }
                }
            }
        }
        return codigoContra;
    }
    
    //=> Busca el ID de area del contrato asociado por la ID de contrato.
    public int BuscarContraArea(int contra_id) {
        ArrayList<DataContrato> lista2 = new ArrayList();
        lista2 = ListaContratos();
        int codigoContra = 0;
        for (DataContrato x : lista2) {
            if ((x.getContrato_id() == contra_id)) {
                codigoContra = x.getFkarea_id();
            }
        }
        return codigoContra;
    }
    
    //=> Busca el ID de cargo del contrato asociado por la ID de contrato.
    public int BuscarContraCarg(int contra_id) {
        ArrayList<DataContrato> lista2 = new ArrayList();
        lista2 = ListaContratos();
        int codigoContra = 0;
        for (DataContrato x : lista2) {
            if ((x.getContrato_id() == contra_id)) {
                codigoContra = x.getFkcargo_id();
            }
        }
        return codigoContra;
    }
    
    //=> Getters y setters.
    public int getContrato_id() {
        return contrato_id;
    }

    public void setContrato_id(int contrato_id) {
        this.contrato_id = contrato_id;
    }

    public Date getContrato_fechaI() {
        return contrato_fechaI;
    }

    public void setContrato_fechaI(Date contrato_fechaI) {
        this.contrato_fechaI = contrato_fechaI;
    }

    public Date getContrato_fechaF() {
        return contrato_fechaF;
    }

    public void setContrato_fechaF(Date contrato_fechaF) {
        this.contrato_fechaF = contrato_fechaF;
    }

    public String getContrato_state() {
        return contrato_state;
    }

    public void setContrato_state(String contrato_state) {
        this.contrato_state = contrato_state;
    }

    public int getFkemple_id() {
        return fkemple_id;
    }

    public void setFkemple_id(int fkemple_id) {
        this.fkemple_id = fkemple_id;
    }

    public int getFkarea_id() {
        return fkarea_id;
    }

    public void setFkarea_id(int fkarea_id) {
        this.fkarea_id = fkarea_id;
    }

    public int getFkcargo_id() {
        return fkcargo_id;
    }

    public void setFkcargo_id(int fkcargo_id) {
        this.fkcargo_id = fkcargo_id;
    }

}
