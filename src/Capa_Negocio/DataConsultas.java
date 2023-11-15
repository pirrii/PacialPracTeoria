/*
 */
package Capa_Negocio;

/**
 *
 * @author pirri
 */
import Capa_Negocio.DataArea;
import Capa_Negocio.DataCargo;
import Capa_Negocio.DataContrato;
import Capa_Negocio.DataEmpleado;
import java.sql.*;
import Capa_Datos.Conexion;
import java.util.ArrayList;

public class DataConsultas {

    private int fkemple_id;
    private int fkarea_id;
    private int total_pago;

    private final String dec = "GEMPLEADO";

    public int ConsultarArea() {
        System.out.println("si me llamaron");

        Conexion objmod = new Conexion();
        String cad = ("SELECT SUM(cargo.cargo_pay) AS total_pago FROM cargo INNER JOIN contrato ON cargo.cargo_id = contrato.fkcargo_id WHERE contrato.contrato_state = 'ACTIVO' AND fkarea_id= " + this.getFkarea_id() + ";");
        return objmod.Consultar(cad, dec);
    }

/////////////////
    public int ConsultarEmpleado() {
        System.out.println("si me llamaron");

        Conexion objmod = new Conexion();
        String cad = ("select cargo_pay FROM cargo INNER JOIN contrato ON cargo_id = fkcargo_id WHERE contrato_state = 'ACTIVO' AND fkemple_id = " + this.getFkemple_id() + ";");
        return objmod.Consultar(cad, dec);
    }

    public int ConsultarPlanta() {
        System.out.println("si me llamaron");
        Conexion objmod = new Conexion();
        String cad = ("SELECT SUM(cargo.cargo_pay) AS total_pago FROM cargo INNER JOIN contrato ON cargo.cargo_id = contrato.fkcargo_id WHERE contrato.contrato_state = 'ACTIVO';");
        return objmod.Consultar(cad, dec);
    }

    public int getFkarea_id() {
        return fkarea_id;
    }

    public void setFkarea_id(int fkarea_id) {
        this.fkarea_id = fkarea_id;
    }

    public int getTotal_pago() {
        return total_pago;
    }

    public void setTotal_pago(int total_pago) {
        this.total_pago = total_pago;
    }

    public int getFkemple_id() {
        return fkemple_id;
    }

    public void setFkemple_id(int fkemple_id) {
        this.fkemple_id = fkemple_id;
    }

}
