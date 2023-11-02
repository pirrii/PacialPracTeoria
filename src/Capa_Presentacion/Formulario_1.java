/*
────────────────╔═.✰.═══╗
──▄████▄▄░─✰ＨＯＬＡ
─▄▀█▀▐└─┐░░╚═══.✰.═╝
─█▄▐▌▄█▄┘██────────
─└▄▄▄▄▄┘███────────
▄██▒█▒███▀─────────.
 */
package Capa_Presentacion;

import Capa_Negocio.DataArea;
import Capa_Negocio.DataCargo;
import Capa_Negocio.DataContrato;
import Capa_Negocio.DataEmpleado;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 *
 * @author jumem
 */
public final class Formulario_1 extends javax.swing.JFrame {

    //=> Inicializa la clase formulario.
    public Formulario_1() {
        initComponents();
        llenaComboArea();
        llenaComboCargo();
        LimpiarCajasTexto();
        ListarEmpleados();
        SetearMinFecha();
        this.jTabbedPane3.setEnabledAt(0, false);
        this.jTabbedPane3.setEnabledAt(1, false);
    }

    public void SetearMinFecha() {

        try {
            int a;
            this.jDFechas.setMinSelectableDate(FechaString_aDate(FechaActual()));
        } catch (ParseException ex) {

        }
    }

    //=> Validar que los datos ingresados coincidan con el tipo de dato solicitado
    public boolean ValidarInt(String Dato) {
        return Dato.matches("[a-zA-Z]{1,25}");
    }

    public boolean ValidarString(String Dato) {
        return Dato.matches("[0,9]{1,25}");
    }

    //=> Limpia la información de las cajas de texto.
    public void LimpiarCajasTexto() {
        this:jTF_DocumentoE.setText("");
        this:jTF_NombreE.setText("");
        this:jTF_ApellidosE.setText("");
        this:jTF_TelefonoE.setText("");
        this.jL_IDContrato.setText("");
        this.jLIDEmpleado.setText("");
    }

    public void LimpiarCajasTextocont() {
        this:jL_IDContrato.setText("");
        this:jTFDocCrearContra.setText("");

    }

    //=> Hace un filtro entre los contratos ACTIVOS/empleado ACTIVOS, el area y el cargo, contado cuantos empleados con cargos activos existen.
    public int ContadorCargosEnContratos() {
        DataArea objArea = new DataArea();
        DataCargo objCargo = new DataCargo();
        DataContrato objContrato = new DataContrato();
        ArrayList<DataContrato> lista2 = new ArrayList();
        lista2 = objContrato.ListaContratos();
        int NumCargo = 0;
        for (DataContrato x : lista2) {
            if (x.getContrato_state().equals("ACTIVO")) {
                if (objArea.BuscarArea(x.getFkarea_id()).equals(this.jComboBoxArea.getSelectedItem().toString())) {
                    if ((objCargo.BuscarCarg(x.getFkcargo_id()).equals(this.jComboBoxCargo.getSelectedItem().toString()))) {
                        NumCargo++;
                    }
                }
            }
        }
        return NumCargo;
    }

    //=> Implementa un switch-case para filtrar entre los diversos cargos, y comprobar si hay espacio.
    public boolean comprobarCargo() {

        DataCargo objCargo = new DataCargo();
        switch (this.jComboBoxCargo.getSelectedItem().toString()) {
            case "JEFE":
                if (objCargo.getJefe() > ContadorCargosEnContratos()) {
                    return true;
                } else {
                    return false;
                }
            case "GERENTE":
                if (objCargo.getGerente() > ContadorCargosEnContratos()) {
                    return true;
                } else {
                    return false;
                }
            case "SLAVE":
                if (objCargo.getSlave() > ContadorCargosEnContratos()) {
                    return true;
                } else {
                    return false;
                }
            default:
                throw new AssertionError();
        }
    }

    //=> Implementa un switch-case para filtrar entre las diversas area.
    public boolean ComprobarArea() {
        boolean Resultado;
        switch (this.jComboBoxArea.getSelectedItem().toString()) {
            case "OPERACION":
                Resultado = comprobarCargo();
                return Resultado;
            case "CALIDAD":
                Resultado = comprobarCargo();
                return Resultado;
            case "ADMINISTRACION":
                Resultado = comprobarCargo();
                return Resultado;
            default:
                throw new AssertionError();
        }
    }

    //=> Consulta las areas existentes en la tabla area y las inserta en el comboBox como items.
    public void llenaComboArea() {
        DataArea objArea = new DataArea();
        DefaultTableModel tablaArea = new DefaultTableModel();
        ArrayList<DataArea> lista2 = new ArrayList();
        lista2 = objArea.ListaAreas();
        for (DataArea x : lista2) {
            this.jComboBoxArea.addItem(x.getArea_nom());
        }
    }

    public void ImprimirDatosContratoCBOX() {
        DataArea objArea = new DataArea();
        DataCargo objCargo = new DataCargo();
        DataContrato objContrato = new DataContrato();
        DataEmpleado objEmpleado = new DataEmpleado();
        String h = this.jTFDocCrearContra.getText();
        ArrayList<DataEmpleado> listaActivo = new ArrayList();
        listaActivo = objEmpleado.ListaEmpleadosActivos();

        for (DataEmpleado x : listaActivo) {
            if (x.getEmple_doc() == (Integer.parseInt(h))) {

                this.jComboBoxArea.setSelectedItem(objArea.BuscarArea(objContrato.BuscarContraArea(objContrato.BuscarContra(x.getEmple_id()))));
                System.out.println("si fui yo : " + objArea.BuscarArea(objContrato.BuscarContraArea(objContrato.BuscarContra(x.getEmple_id()))));

                this.jComboBoxCargo.setSelectedItem(objCargo.BuscarCarg(objContrato.BuscarContraCarg(objContrato.BuscarContra(x.getEmple_id()))));
                System.out.println("tambien fui yo : " + objCargo.BuscarCarg(objContrato.BuscarContraCarg(objContrato.BuscarContra(x.getEmple_id()))));
            }
        }
    }

    public void BuscarEmpleado() {
        DataArea objArea = new DataArea();
        DataCargo objCargo = new DataCargo();
        DataContrato objContrato = new DataContrato();
        DataEmpleado objEmpleado = new DataEmpleado();
        String h = this.jTF_DocumentoE.getText();
        ArrayList<DataEmpleado> listaActivo = new ArrayList();
        listaActivo = objEmpleado.ListaEmpleadosActivos();

        for (DataEmpleado x : listaActivo) {
        if (x.getEmple_doc() == (Integer.parseInt(h))) {
            
            this.jTF_NombreE.setText(x.getEmple_nom());
            this.jTF_ApellidosE.setText(x.getEmple_ape());
            this.jTF_TelefonoE.setText(x.getEmple_tel());

        }
      
        }
    }

    //=> Consulta los cargos existentes en la tabla cargo y los inserta en el comboBox como items.
    public void llenaComboCargo() {

        DataCargo objCargo = new DataCargo();
        DefaultTableModel tablaCargo = new DefaultTableModel();
        ArrayList<DataCargo> lista2 = new ArrayList();
        lista2 = objCargo.ListaCargos();
        for (DataCargo x : lista2) {
            this.jComboBoxCargo.addItem(x.getCargo_nom());
        }
    }

    //=> Traer la fecha actual, modifica su formato y lo retorna.
    public String FechaActual() {
        Date fechaActual = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActualSQL = sdf.format(fechaActual);
        return fechaActualSQL;
    }

    //=> Extraer la fecha final del jchoose.
    public String FechaFinalizacionContrato(String FechaIn) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date apcd = formato.parse(FechaIn);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nuevaFechaSQL = sdf.format(apcd);
        return nuevaFechaSQL;
    }

    //=> Cambia el formato de la fecha de String a Date 
    public Date FechaString_aDate(String Fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = sdf.parse(Fecha);
        java.sql.Date fechaActualSQL = new java.sql.Date(utilDate.getTime());
        return fechaActualSQL;
    }

    /*METODOS "TRASBANBALINAS" 
METODOS DE VERIFICACION DE EXISTENCIAS TANTO EN TABLA COMO EN TEXT_FIELDS*/
    //=> Verifica que el documento ingresado exista en la tabla empleado.
    public boolean verificar(String Documento) {
        DataEmpleado objEmpleado = new DataEmpleado();
        ArrayList<DataEmpleado> lista2 = new ArrayList();
        lista2 = objEmpleado.ListaEmpleadosActivos();
        for (DataEmpleado x : lista2) {
            if ((Documento.equals(x.getEmple_doc()))) {
                return true;
            }
        }
        return false;
    }

    //=> Verifica que el contrato exista.
    public boolean verificarContrato(int idContrato) {
        DataContrato objContrato = new DataContrato();
        ArrayList<DataContrato> lista2 = new ArrayList();
        lista2 = objContrato.ListaContratos();
        for (DataContrato x : lista2) {
            if (idContrato == (x.getContrato_id())) {
                return true;
            }
        }
        return false;
    }

    //=> Verifica que los datos esten llenos en el formulario.
    public boolean VerificarCampos() {
        if ((this.jTF_DocumentoE.getText().equals("")) || (this.jTF_ApellidosE.getText().equals("")) || (this.jTF_NombreE.getText().equals("")) || (this.jTF_TelefonoE.getText().equals(""))) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VerificarCamposContra() {
        if (this.jTFDocCrearContra.getText().equals("") || (((JTextField) jDFechas.getDateEditor().getUiComponent()).getText()).equals("")) {
            return false;
        } else {
            return true;
        }

    }

    public void CrearContrato(String DocEmpleado) {
        DataArea objArea = new DataArea();
        DataCargo objCargo = new DataCargo();
        DataContrato objContrato = new DataContrato();
        DataEmpleado objEmpleado = new DataEmpleado();
        if (ComprobarArea() == true) {
            String fecha = ((JTextField) jDFechas.getDateEditor().getUiComponent()).getText();
            int busqueda = objEmpleado.buscarEmpleDoc(DocEmpleado);
            System.out.println("este es el codigo emp: " + busqueda);

            try {

                objContrato.setContrato_fechaI(FechaString_aDate(FechaActual()));
                objContrato.setContrato_fechaF(FechaString_aDate(FechaFinalizacionContrato((fecha))));
                objContrato.setContrato_state("ACTIVO");
                objContrato.setFkemple_id(objEmpleado.buscarEmpleDoc(DocEmpleado));
                objContrato.setFkarea_id(objArea.BuscarArea(this.jComboBoxArea.getSelectedItem().toString()));
                objContrato.setFkcargo_id(objCargo.BuscarCarg(this.jComboBoxCargo.getSelectedItem().toString()));
                JOptionPane.showMessageDialog(null, objContrato.GrabarContrato());

            } catch (ParseException ex) {
                JOptionPane.showInternalMessageDialog(null, ex);
            }
        } else {
            JOptionPane.showInternalMessageDialog(null, "Supero el limite de trabajadores que pueden sostener el cargo de esta area");
        }

    }

    //=> Crea un empleado.
    public void GuardarEmpleado() {
        DataEmpleado objEmpleado = new DataEmpleado();
        ArrayList<DataEmpleado> lista2 = new ArrayList();
        lista2 = objEmpleado.ListaEmpleadosGenerales();
        objEmpleado.setEmple_doc(Integer.parseInt(this.jTF_DocumentoE.getText()));
        objEmpleado.setEmple_nom(this.jTF_NombreE.getText());
        objEmpleado.setEmple_ape(this.jTF_ApellidosE.getText());
        objEmpleado.setEmple_tel(this.jTF_TelefonoE.getText());
        objEmpleado.setEmple_state("ACTIVO");

        JOptionPane.showMessageDialog(null, objEmpleado.GrabarEmpleado());
        ListarEmpleados();
    }

    public void ContratosEmpleado() {
        DataArea objArea = new DataArea();
        DataCargo objCargo = new DataCargo();
        DataContrato objContrato = new DataContrato();
        DataEmpleado objEmpleado = new DataEmpleado();
        DefaultTableModel tablaCont = new DefaultTableModel();
        ArrayList<DataContrato> lista2 = new ArrayList();
        lista2 = objContrato.ListaContratos();
        int h = objEmpleado.buscarEmpleDoc(this.jTFDocCrearContra.getText());
        tablaCont.addColumn("Id Contrato");
        tablaCont.addColumn("Cargo");
        tablaCont.addColumn("Area");
        tablaCont.addColumn("Fecha Inicio");
        tablaCont.addColumn("Fecha Fin");
        tablaCont.addColumn("Estado");
        tablaCont.setRowCount(lista2.size());
        int i = 0;
        for (DataContrato x : lista2) {
            if (x.getFkemple_id() == (h)) {
                tablaCont.setValueAt(x.getContrato_id(), i, 0);
                tablaCont.setValueAt(objCargo.BuscarCarg(x.getFkcargo_id()), i, 1);
                tablaCont.setValueAt(objArea.BuscarArea(x.getFkarea_id()), i, 2);
                tablaCont.setValueAt(x.getContrato_fechaI(), i, 3);
                tablaCont.setValueAt(x.getContrato_fechaF(), i, 4);
                tablaCont.setValueAt(x.getContrato_state(), i, 5);
                i++;
            }
        }
        this.jTablaContratos.setModel(tablaCont);
    }

    //=> Establece el formato de las jTable de empleado tanto para ACTIVOS como INACTIVOS, y las llena con la información en los arraylists.
    public void ListarEmpleados() {
        DataArea objArea = new DataArea();
        DataCargo objCargo = new DataCargo();
        DataContrato objContrato = new DataContrato();
        DataEmpleado objEmpleado = new DataEmpleado();
        DefaultTableModel tablaAct = new DefaultTableModel();
        DefaultTableModel tablaDes = new DefaultTableModel();
        ArrayList<DataEmpleado> listaActivo = new ArrayList();
        listaActivo = objEmpleado.ListaEmpleadosActivos();
        ArrayList<DataEmpleado> listaInactivo = new ArrayList();
        listaInactivo = objEmpleado.ListaEmpleadosInactivos();

        tablaAct.addColumn("Codigo");
        tablaAct.addColumn("Documento");
        tablaAct.addColumn("Nombre");
        tablaAct.addColumn("Apellido");
        tablaAct.addColumn("Telefono");
        tablaAct.addColumn("Cargo");
        tablaAct.addColumn("Area");
        tablaAct.addColumn("Id contrato actual");
        tablaAct.addColumn("Estado");
        tablaAct.setRowCount(listaActivo.size());

        tablaDes.addColumn("Codigo");
        tablaDes.addColumn("Documento");
        tablaDes.addColumn("Nombre");
        tablaDes.addColumn("Apellido");
        tablaDes.addColumn("Telefono");
        tablaDes.addColumn("Cargo");
        tablaDes.addColumn("Area");
        tablaDes.addColumn("Estado");
        tablaDes.setRowCount(listaInactivo.size());

        int i = 0;
        for (DataEmpleado x : listaActivo) {
            tablaAct.setValueAt(x.getEmple_id(), i, 0);
            tablaAct.setValueAt(x.getEmple_doc(), i, 1);
            tablaAct.setValueAt(x.getEmple_nom(), i, 2);
            tablaAct.setValueAt(x.getEmple_ape(), i, 3);
            tablaAct.setValueAt(x.getEmple_tel(), i, 4);
            tablaAct.setValueAt(objCargo.BuscarCarg(objContrato.BuscarContraCarg(objContrato.BuscarContra(x.getEmple_id()))), i, 5);
            tablaAct.setValueAt(objArea.BuscarArea(objContrato.BuscarContraArea(objContrato.BuscarContra(x.getEmple_id()))), i, 6);
            tablaAct.setValueAt(objContrato.BuscarContra(x.getEmple_id()), i, 7);
            tablaAct.setValueAt(x.getEmple_state(), i, 8);
            i++;
        }
        i = 0;

        for (DataEmpleado x : listaInactivo) {
            tablaDes.setValueAt(x.getEmple_id(), i, 0);
            tablaDes.setValueAt(x.getEmple_doc(), i, 1);
            tablaDes.setValueAt(x.getEmple_nom(), i, 2);
            tablaDes.setValueAt(x.getEmple_ape(), i, 3);
            tablaDes.setValueAt(x.getEmple_tel(), i, 4);
            tablaDes.setValueAt(objCargo.BuscarCarg(objContrato.BuscarContraCarg(objContrato.BuscarContra(x.getEmple_id()))), i, 5);
            tablaDes.setValueAt(objArea.BuscarArea(objContrato.BuscarContraArea(objContrato.BuscarContra(x.getEmple_id()))), i, 6);
            tablaDes.setValueAt(x.getEmple_state(), i, 7);
            i++;
        }
        this.jTableEActivos.setModel(tablaAct);
        this.jTablaEInactivos.setModel(tablaDes);
    }

    public void DesactivarEmpleado() {
        DataContrato objContrato = new DataContrato();
        DataEmpleado objEmpleado = new DataEmpleado();
        if (verificar(this.jLIDEmpleado.getText()) == false) {
            if (JOptionPane.showConfirmDialog(null, "Esta seguro que quiere eliminar el empleado " + this.jLIDEmpleado.getText()) == 0) {
                objEmpleado.setEmple_id(Integer.parseInt(this.jLIDEmpleado.getText()));
                JOptionPane.showMessageDialog(null, objEmpleado.DesactivarEmpleado(this.jLIDEmpleado.getText()));
                objContrato.setContrato_id(Integer.parseInt(this.jL_IDContrato.getText()));
                objContrato.setContrato_state("INACTIVO");
                objContrato.DesactivarContrato();
                ListarEmpleados();
            }
        } else {
            JOptionPane.showMessageDialog(null, "El empleado no existe.");
        }
    }

    public void ModificarEmpleado() {
        DataEmpleado objEmpleado = new DataEmpleado();
        if (verificar(this.jTF_DocumentoE.getText()) == false) {
            objEmpleado.setEmple_doc(Integer.parseInt(this.jTF_DocumentoE.getText()));
            objEmpleado.setEmple_nom(this.jTF_NombreE.getText());
            objEmpleado.setEmple_ape(this.jTF_ApellidosE.getText());
            objEmpleado.setEmple_tel(this.jTF_TelefonoE.getText());
            objEmpleado.setEmple_state("ACTIVO");
            objEmpleado.setEmple_id(Integer.parseInt(this.jLIDEmpleado.getText()));
            JOptionPane.showMessageDialog(null, objEmpleado.EditarEmpleado());
            ListarEmpleados();
        } else {
            JOptionPane.showMessageDialog(null, "El Documento no coincide con ningun registro");
        }
    }

    public void ModificarContrato() {
        DataContrato objContrato = new DataContrato();
        DataEmpleado objEmpleado = new DataEmpleado();
        if (verificarContrato(objContrato.BuscarContra(objEmpleado.buscarEmpleDoc(this.jTFDocCrearContra.getText()))) == true) {
            String fecha = ((JTextField) jDFechas.getDateEditor().getUiComponent()).getText();
            ArrayList<DataContrato> lista2 = new ArrayList();
            lista2 = objContrato.ListaContratos();

            objContrato.setContrato_id(objContrato.BuscarContra(objEmpleado.buscarEmpleDoc(this.jTFDocCrearContra.getText())));

            objContrato.setContrato_state("INACTIVO");
            objContrato.DesactivarContrato();
            CrearContrato((this.jTFDocCrearContra.getText()));
            ContratosEmpleado();
            ListarEmpleados();
        } else {
            JOptionPane.showMessageDialog(null, "El codigo no coincide con ningun registro");
        }
    }

    public void DesactivarContrato() {
        DataContrato objContrato = new DataContrato();
        DataEmpleado objEmpleado = new DataEmpleado();
        if (verificarContrato(objContrato.BuscarContra(objEmpleado.buscarEmpleDoc(this.jTFDocCrearContra.getText()))) == true) {
            if (JOptionPane.showConfirmDialog(null, "Esta seguro que quiere eliminar el contrato " + objContrato.BuscarContra(objEmpleado.buscarEmpleDoc(this.jTFDocCrearContra.getText()))) == 0) {
                objContrato.setContrato_id(objContrato.BuscarContra(objEmpleado.buscarEmpleDoc(this.jTFDocCrearContra.getText())));
                objContrato.setContrato_state("INACTIVO");
                objContrato.DesactivarContrato();
            }
        } else {
            JOptionPane.showMessageDialog(null, "El empleado no existe.");
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablaEInactivos = new javax.swing.JTable();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jBLimpiar1 = new javax.swing.JButton();
        jTF_TelefonoE = new javax.swing.JTextField();
        jLTitulo1 = new javax.swing.JLabel();
        jLIDEmpleado1 = new javax.swing.JLabel();
        jLDocumentoE1 = new javax.swing.JLabel();
        jLNombreE1 = new javax.swing.JLabel();
        jLApellidosE1 = new javax.swing.JLabel();
        jLIDEmpleado = new javax.swing.JLabel();
        jLTelefonoE1 = new javax.swing.JLabel();
        jTF_DocumentoE = new javax.swing.JTextField();
        jTF_NombreE = new javax.swing.JTextField();
        jTF_ApellidosE = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableEActivos = new javax.swing.JTable();
        jBSalir2 = new javax.swing.JButton();
        jBDesactivar = new javax.swing.JButton();
        jBModificarEmple = new javax.swing.JButton();
        jBGrabarCreaEmpleado = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jL_IDContrato = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jBbuscarEmp = new javax.swing.JButton();
        jPCreaContrato = new javax.swing.JPanel();
        jLTituloCreaContrato = new javax.swing.JLabel();
        jLDocCrearContra = new javax.swing.JLabel();
        jLAreaCreaContra = new javax.swing.JLabel();
        jLCargoCreaContra = new javax.swing.JLabel();
        jLFechaFCreaContra = new javax.swing.JLabel();
        jBGrabarCreaContra = new javax.swing.JButton();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTFDocCrearContra = new javax.swing.JTextPane();
        jDFechas = new com.toedter.calendar.JDateChooser();
        jComboBoxArea = new javax.swing.JComboBox<>();
        jComboBoxCargo = new javax.swing.JComboBox<>();
        jBLimpiar2 = new javax.swing.JButton();
        jBSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaContratos = new javax.swing.JTable();
        jBModificarContra = new javax.swing.JButton();
        jBDesactivar1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jTablaEInactivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTablaEInactivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTablaEInactivosMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTablaEInactivos);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        jBLimpiar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/escoba1.png"))); // NOI18N
        jBLimpiar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBLimpiar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBLimpiar1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/escobaB.png"))); // NOI18N
        jBLimpiar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimpiar1ActionPerformed(evt);
            }
        });

        jTF_TelefonoE.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jTF_TelefonoE.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTF_TelefonoE.setDragEnabled(true);
        jTF_TelefonoE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_TelefonoEActionPerformed(evt);
            }
        });

        jLTitulo1.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        jLTitulo1.setText("Gestion de Empleados TeoConstructions S.A.S");

        jLIDEmpleado1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLIDEmpleado1.setText("ID de Empleado:");

        jLDocumentoE1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLDocumentoE1.setText("Documento:");

        jLNombreE1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLNombreE1.setText("Nombre:");

        jLApellidosE1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLApellidosE1.setText("Apellidos:");

        jLTelefonoE1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLTelefonoE1.setText("Telefono:");

        jTF_DocumentoE.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jTF_DocumentoE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_DocumentoEActionPerformed(evt);
            }
        });

        jTF_NombreE.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jTF_NombreE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_NombreEActionPerformed(evt);
            }
        });

        jTF_ApellidosE.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jTF_ApellidosE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_ApellidosEActionPerformed(evt);
            }
        });

        jTabbedPane1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        jTableEActivos.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jTableEActivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableEActivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableEActivosMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTableEActivos);

        jTabbedPane1.addTab("Empleados Activos", jScrollPane2);

        jBSalir2.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jBSalir2.setText("Salir");
        jBSalir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalir2ActionPerformed(evt);
            }
        });

        jBDesactivar.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jBDesactivar.setText("Desactivar");
        jBDesactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDesactivarActionPerformed(evt);
            }
        });

        jBModificarEmple.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jBModificarEmple.setText("Modificar");
        jBModificarEmple.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBModificarEmpleActionPerformed(evt);
            }
        });

        jBGrabarCreaEmpleado.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jBGrabarCreaEmpleado.setText("Grabar");
        jBGrabarCreaEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGrabarCreaEmpleadoActionPerformed(evt);
            }
        });

        jButton2.setText("Contratos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("id contrato:");

        jBbuscarEmp.setText("Buscar");
        jBbuscarEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBbuscarEmpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLTitulo1)
                .addGap(365, 365, 365)
                .addComponent(jButton2)
                .addGap(29, 29, 29))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLTelefonoE1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTF_TelefonoE, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jL_IDContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLNombreE1)
                            .addComponent(jLDocumentoE1)
                            .addComponent(jLApellidosE1)
                            .addComponent(jLIDEmpleado1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTF_DocumentoE, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLIDEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jBbuscarEmp))
                                .addComponent(jTF_NombreE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTF_ApellidosE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBLimpiar1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jBGrabarCreaEmpleado)
                        .addGap(87, 87, 87)
                        .addComponent(jBModificarEmple)
                        .addGap(103, 103, 103)
                        .addComponent(jBDesactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(jBSalir2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLTitulo1)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLIDEmpleado1)
                            .addComponent(jLIDEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBbuscarEmp))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLDocumentoE1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTF_DocumentoE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTF_NombreE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLNombreE1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTF_ApellidosE, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLApellidosE1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTF_TelefonoE, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLTelefonoE1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(jL_IDContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(62, 62, 62))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jBSalir2)
                                .addComponent(jBDesactivar)
                                .addComponent(jBModificarEmple)
                                .addComponent(jBGrabarCreaEmpleado))
                            .addComponent(jBLimpiar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 30, Short.MAX_VALUE))))
        );

        jTabbedPane3.addTab("Empleados", jPanel2);

        jPCreaContrato.setBackground(new java.awt.Color(153, 153, 255));

        jLTituloCreaContrato.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        jLTituloCreaContrato.setText("Gestion de Contratos");

        jLDocCrearContra.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLDocCrearContra.setText("Documento del empleado:");

        jLAreaCreaContra.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLAreaCreaContra.setText("Area:");

        jLCargoCreaContra.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLCargoCreaContra.setText("Cargo:");

        jLFechaFCreaContra.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLFechaFCreaContra.setText("Fecha de finalización:");

        jBGrabarCreaContra.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jBGrabarCreaContra.setText("Grabar");
        jBGrabarCreaContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGrabarCreaContraActionPerformed(evt);
            }
        });

        jScrollPane20.setViewportView(jTFDocCrearContra);

        jDFechas.setToolTipText("8888");
        jDFechas.setDateFormatString("y-MM-d");
        jDFechas.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        jComboBoxArea.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        jComboBoxCargo.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        jBLimpiar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/escoba1.png"))); // NOI18N
        jBLimpiar2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBLimpiar2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBLimpiar2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/escobaB.png"))); // NOI18N
        jBLimpiar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimpiar2ActionPerformed(evt);
            }
        });

        jBSalir.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jBSalir.setText("Salir");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });

        jTablaContratos.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jTablaContratos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTablaContratos);

        jBModificarContra.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jBModificarContra.setText("Modificar");
        jBModificarContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBModificarContraActionPerformed(evt);
            }
        });

        jBDesactivar1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jBDesactivar1.setText("Desactivar");
        jBDesactivar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDesactivar1ActionPerformed(evt);
            }
        });

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Volver");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPCreaContratoLayout = new javax.swing.GroupLayout(jPCreaContrato);
        jPCreaContrato.setLayout(jPCreaContratoLayout);
        jPCreaContratoLayout.setHorizontalGroup(
            jPCreaContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCreaContratoLayout.createSequentialGroup()
                .addGroup(jPCreaContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPCreaContratoLayout.createSequentialGroup()
                        .addGroup(jPCreaContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPCreaContratoLayout.createSequentialGroup()
                                .addGap(248, 248, 248)
                                .addComponent(jButton1))
                            .addGroup(jPCreaContratoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPCreaContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPCreaContratoLayout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(jLFechaFCreaContra))
                                    .addComponent(jLCargoCreaContra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLAreaCreaContra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLDocCrearContra, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPCreaContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxArea, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPCreaContratoLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPCreaContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboBoxCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPCreaContratoLayout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(jDFechas, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPCreaContratoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBLimpiar2)
                        .addGap(20, 20, 20)))
                .addGroup(jPCreaContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPCreaContratoLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jBGrabarCreaContra, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(jBModificarContra, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(jBDesactivar1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97)
                        .addComponent(jBSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80))
            .addGroup(jPCreaContratoLayout.createSequentialGroup()
                .addGap(459, 459, 459)
                .addComponent(jLTituloCreaContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(23, 23, 23))
        );
        jPCreaContratoLayout.setVerticalGroup(
            jPCreaContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPCreaContratoLayout.createSequentialGroup()
                .addGap(12, 53, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addGroup(jPCreaContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLDocCrearContra))
                .addGap(18, 18, 18)
                .addGroup(jPCreaContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLAreaCreaContra)
                    .addComponent(jComboBoxArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPCreaContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLCargoCreaContra))
                .addGap(18, 18, 18)
                .addGroup(jPCreaContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDFechas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLFechaFCreaContra, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(7, 7, 7)
                .addComponent(jBLimpiar2)
                .addGap(29, 29, 29))
            .addGroup(jPCreaContratoLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPCreaContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jLTituloCreaContrato))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(jPCreaContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBModificarContra)
                    .addComponent(jBDesactivar1)
                    .addComponent(jBSalir)
                    .addComponent(jBGrabarCreaContra))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("contratos", jPCreaContrato);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1124, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBGrabarCreaEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGrabarCreaEmpleadoActionPerformed
        if ((ValidarInt(this.jTF_DocumentoE.getText().trim())) || (ValidarString(this.jTF_NombreE.getText().trim())) || (ValidarString(this.jTF_ApellidosE.getText().trim())) || (ValidarInt(this.jTF_TelefonoE.getText().trim()))) {
            if (VerificarCampos() == true) {
                GuardarEmpleado();
                int r = JOptionPane.showConfirmDialog(null, "¿Desea crear un contrato al empleado?");
                System.out.println(r);
                if (r == 0) {
                    this.jTabbedPane3.setSelectedIndex(1);
                    this:jTFDocCrearContra.setText(this.jTF_DocumentoE.getText());
                    ContratosEmpleado();
                } else {
                    LimpiarCajasTexto();
                }
            } else {
                JOptionPane.showMessageDialog(null, "La información esta incompleta, por favor verifique");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Verifique los datos ingresados que correspondan al tipo de dato y no exedan el tamaño minimo");
        }
    }//GEN-LAST:event_jBGrabarCreaEmpleadoActionPerformed

    private void jBDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDesactivarActionPerformed
        if ((ValidarInt(this.jTF_DocumentoE.getText().trim()))) {
            DesactivarEmpleado();
        } else {
            JOptionPane.showMessageDialog(null, "Verifique los datos ingresados que correspondan al tipo de dato y no exedan el tamaño minimo");
        }
    }//GEN-LAST:event_jBDesactivarActionPerformed

    private void jTableEActivosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEActivosMousePressed
        int rec = this.jTableEActivos.getSelectedRow();
        this.jLIDEmpleado.setText(jTableEActivos.getValueAt(rec, 0).toString());
        this.jTF_DocumentoE.setText(jTableEActivos.getValueAt(rec, 1).toString());
        this.jTFDocCrearContra.setText(jTableEActivos.getValueAt(rec, 1).toString());
        this.jTF_NombreE.setText(jTableEActivos.getValueAt(rec, 2).toString());
        this.jTF_ApellidosE.setText(jTableEActivos.getValueAt(rec, 3).toString());
        this.jTF_TelefonoE.setText(jTableEActivos.getValueAt(rec, 4).toString());
        this.jL_IDContrato.setText(jTableEActivos.getValueAt(rec, 7).toString());
        this.jComboBoxCargo.setSelectedItem(jTableEActivos.getValueAt(rec, 5).toString());
        this.jComboBoxArea.setSelectedItem(jTableEActivos.getValueAt(rec, 6).toString());
        ContratosEmpleado();//INVOCAMOS EL METODO QUE NOS PERMITIRA VIZUALISAR LOS CONTRATOS POR EMPLEADO ACTIVO.

    }//GEN-LAST:event_jTableEActivosMousePressed

    private void jTablaEInactivosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaEInactivosMousePressed
        LimpiarCajasTexto();
        LimpiarCajasTextocont();
    }//GEN-LAST:event_jTablaEInactivosMousePressed

    private void jTF_DocumentoEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTF_DocumentoEActionPerformed

    }//GEN-LAST:event_jTF_DocumentoEActionPerformed

    private void jBLimpiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimpiar1ActionPerformed
        LimpiarCajasTexto();
    }//GEN-LAST:event_jBLimpiar1ActionPerformed

    private void jBSalir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalir2ActionPerformed
        int r = JOptionPane.showConfirmDialog(null, "¿Estas Seguro?");
        if (r == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_jBSalir2ActionPerformed

    private void jTF_NombreEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTF_NombreEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTF_NombreEActionPerformed

    private void jTF_ApellidosEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTF_ApellidosEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTF_ApellidosEActionPerformed

    private void jTF_TelefonoEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTF_TelefonoEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTF_TelefonoEActionPerformed

    private void jBModificarEmpleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModificarEmpleActionPerformed
        if ((ValidarInt(this.jTF_DocumentoE.getText().trim())) || (ValidarString(this.jTF_NombreE.getText().trim())) || (ValidarString(this.jTF_ApellidosE.getText().trim())) || (ValidarInt(this.jTF_TelefonoE.getText().trim()))) {
            if (VerificarCampos() == true) {
                ModificarEmpleado();
            } else {
                JOptionPane.showMessageDialog(null, "llenne correctamente los datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Verifique los datos ingresados que correspondan al tipo de dato y no exedan el tamaño minimo");
        }
    }//GEN-LAST:event_jBModificarEmpleActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (this.jTFDocCrearContra.getText().equals("")) {
            this.jTabbedPane3.setSelectedIndex(1);

        } else {
            this.jTabbedPane3.setSelectedIndex(1);
            //this.jTFDocCrearContra.setText(this.jTF_DocumentoE.getText());
            ContratosEmpleado();

        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.jTabbedPane3.setSelectedIndex(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//     if ((ValidarInt(this.jTF_DocumentoE.getText().trim()))) {
        ContratosEmpleado();
        ImprimirDatosContratoCBOX();
        this.jTF_DocumentoE.setText(this.jTFDocCrearContra.getText());
        BuscarEmpleado();
  //       } else {
        JOptionPane.showMessageDialog(null, "Verifique los datos ingresados que correspondan al tipo de dato y no exedan el tamaño minimo");
    //    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jBDesactivar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDesactivar1ActionPerformed
        if ((ValidarInt(this.jTF_DocumentoE.getText().trim()))) {
            if (VerificarCamposContra() == true) {
                DesactivarContrato();
                ContratosEmpleado();
                ListarEmpleados();
            } else {
                JOptionPane.showMessageDialog(null, "La información esta incompleta, por favor verifique");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Verifique los datos ingresados que correspondan al tipo de dato y no exedan el tamaño minimo");
        }
    }//GEN-LAST:event_jBDesactivar1ActionPerformed

    private void jBModificarContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModificarContraActionPerformed
        if ((ValidarInt(this.jTF_DocumentoE.getText().trim()))) {
            if (VerificarCamposContra() == true) {
                ModificarContrato();
                ContratosEmpleado();
                ListarEmpleados();
            } else {
                JOptionPane.showMessageDialog(null, "La información esta incompleta, por favor verifique");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Verifique los datos ingresados que correspondan al tipo de dato y no exedan el tamaño minimo");
        }
    }//GEN-LAST:event_jBModificarContraActionPerformed

    private void jBSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalirActionPerformed
        int r = JOptionPane.showConfirmDialog(null, "¿Estas Seguro?");
        if (r == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_jBSalirActionPerformed

    private void jBLimpiar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimpiar2ActionPerformed
        LimpiarCajasTextocont();
    }//GEN-LAST:event_jBLimpiar2ActionPerformed

    private void jBGrabarCreaContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGrabarCreaContraActionPerformed
        if ((ValidarInt(this.jTF_DocumentoE.getText().trim()))) {
            DataContrato objContrato = new DataContrato();
            DataEmpleado objEmpleado = new DataEmpleado();
            if (VerificarCamposContra() == true) {
                if (verificarContrato(objContrato.BuscarContra(objEmpleado.buscarEmpleDoc(this.jTFDocCrearContra.getText()))) == true) {
                    JOptionPane.showMessageDialog(null, "Ya hay un contrato activo");
                } else {
                    CrearContrato(this.jTFDocCrearContra.getText());
                    ContratosEmpleado();
                    ListarEmpleados();
                }
            } else {
                JOptionPane.showMessageDialog(null, "La información esta incompleta, por favor verifique");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Verifique los datos ingresados que correspondan al tipo de dato y no exedan el tamaño minimo");
        }
    }//GEN-LAST:event_jBGrabarCreaContraActionPerformed

    private void jBbuscarEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBbuscarEmpActionPerformed
         BuscarEmpleado();
    }//GEN-LAST:event_jBbuscarEmpActionPerformed

    /**
     *
     *
     *
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Formulario_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Formulario_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Formulario_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Formulario_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Formulario_1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBDesactivar;
    private javax.swing.JButton jBDesactivar1;
    private javax.swing.JButton jBGrabarCreaContra;
    private javax.swing.JButton jBGrabarCreaEmpleado;
    private javax.swing.JButton jBLimpiar1;
    private javax.swing.JButton jBLimpiar2;
    private javax.swing.JButton jBModificarContra;
    private javax.swing.JButton jBModificarEmple;
    private javax.swing.JButton jBSalir;
    private javax.swing.JButton jBSalir2;
    private javax.swing.JButton jBbuscarEmp;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBoxArea;
    private javax.swing.JComboBox<String> jComboBoxCargo;
    private com.toedter.calendar.JDateChooser jDFechas;
    private javax.swing.JLabel jLApellidosE1;
    private javax.swing.JLabel jLAreaCreaContra;
    private javax.swing.JLabel jLCargoCreaContra;
    private javax.swing.JLabel jLDocCrearContra;
    private javax.swing.JLabel jLDocumentoE1;
    private javax.swing.JLabel jLFechaFCreaContra;
    private javax.swing.JLabel jLIDEmpleado;
    private javax.swing.JLabel jLIDEmpleado1;
    private javax.swing.JLabel jLNombreE1;
    private javax.swing.JLabel jLTelefonoE1;
    private javax.swing.JLabel jLTitulo1;
    private javax.swing.JLabel jLTituloCreaContrato;
    private javax.swing.JLabel jL_IDContrato;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPCreaContrato;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane jTFDocCrearContra;
    private javax.swing.JTextField jTF_ApellidosE;
    private javax.swing.JTextField jTF_DocumentoE;
    private javax.swing.JTextField jTF_NombreE;
    private javax.swing.JTextField jTF_TelefonoE;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTablaContratos;
    private javax.swing.JTable jTablaEInactivos;
    private javax.swing.JTable jTableEActivos;
    // End of variables declaration//GEN-END:variables

}
