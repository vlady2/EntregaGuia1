/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.awt.event.ActionListener;
import Modelo.*;
import Vista.*;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author HP
 */
public class Controlador implements ActionListener /*, DocumentListener*/ {
    
    PersonaDAO dao = new PersonaDAO();
    Persona p = new Persona();
    principal v = new principal();
    DefaultTableModel modelo = new DefaultTableModel();
    Conexion conectar = new Conexion();
    Connection con;
    
    public Controlador(principal v){
        this.v = v;
        this.v.btListar.addActionListener(this);
        this.v.btGuardar.addActionListener(this);
        this.v.btEditar.addActionListener(this);
        this.v.btEliminar.addActionListener(this);
        this.v.btBuscar.addActionListener(this);
        //this.v.tffiltrar.getDocument().addDocumentListener(this);
        
        this.v.jTable.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent mouseEv)
            {
                JTable tb = (JTable) mouseEv.getSource();
                Point p = mouseEv.getPoint();
                int row = tb.rowAtPoint(p);
                if (mouseEv.getClickCount() == 1){
                    v.tfId.setText(v.jTable.getValueAt(v.jTable.getSelectedRow(), 0).toString());
                    v.tfNombre.setText(v.jTable.getValueAt(v.jTable.getSelectedRow(), 1).toString());
                    v.tfCorreo.setText(v.jTable.getValueAt(v.jTable.getSelectedRow(), 2).toString());
                    v.tfTelefono.setText(v.jTable.getValueAt(v.jTable.getSelectedRow(), 3).toString());
                }
                v.btEditar.setEnabled(true);
                v.btEliminar.setEnabled(true);
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == v.btListar){
            limpiaTabla();
            Listar(v.jTable);
        } else if (e.getSource() == v.btGuardar){
            Agregar();
            limpiaTabla();
            Listar(v.jTable);
        } else if (e.getSource() == v.btEditar){
            Modificar();
            limpiaTabla();
            Listar(v.jTable);
        } else if (e.getSource() == v.btEliminar){
            Eliminar();
            limpiaTabla();
            Listar(v.jTable);
        } else if (e.getSource() == v.btBuscar){
            limpiaTabla();
            buscar(v.jTable);
        }  
    }
    
    public void Listar(JTable tabla){
        modelo = (DefaultTableModel)tabla.getModel();
        List<Persona> lista = dao.listar();
        
        Object[] object = new Object[4];
        
        for (int i = 0; i < lista.size(); i++){
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getNombre();
            object[2] = lista.get(i).getCorreo();
            object[3] = lista.get(i).getTelefono();
            modelo.addRow(object);
        }
    }
    
    public void buscar(JTable tabla){
        modelo = (DefaultTableModel)tabla.getModel();
        List<Persona> lista = dao.buscar(v.tffiltrar.getText());
        
        Object[] object = new Object[4];
        
        for (int i = 0; i < lista.size(); i++){
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getNombre();
            object[2] = lista.get(i).getCorreo();
            object[3] = lista.get(i).getTelefono();
            modelo.addRow(object);
        }
    }
        
    public void Agregar(){
        String nom = v.tfNombre.getText();
        String cor = v.tfCorreo.getText();
        String tel = v.tfTelefono.getText();
         
        p.setNombre(nom);
        p.setCorreo(cor);
        p.setTelefono(tel);
         
        int respuesta = dao.Agreagar(p);
        if (respuesta == 1){
            JOptionPane.showMessageDialog(v, "Usuario Agregado");
        }
        else {
            JOptionPane.showMessageDialog(v, "Error");
        }
    }
    
    public void Modificar(){
        int id = Integer.parseInt(v.tfId.getText());
        String nom = v.tfNombre.getText();
        String cor = v.tfCorreo.getText();
        String tel = v.tfTelefono.getText();
         
        p.setId(id);
        p.setNombre(nom);
        p.setCorreo(cor);
        p.setTelefono(tel);
         
        int respuesta = dao.Modificar(p);
        if (respuesta == 1){
            JOptionPane.showMessageDialog(v, "Usuario Modificado");
        }
        else {
            JOptionPane.showMessageDialog(v, "Error");
        }
    }
    
    public void Eliminar(){
        int id = Integer.parseInt(v.tfId.getText());
         
        int respuesta = dao.Eliminar(id);
        if (respuesta == 1){
            JOptionPane.showMessageDialog(v, "Usuario Eliminado");
        }
        else {
            JOptionPane.showMessageDialog(v, "Eroro");
        }
    }
    void limpiaTabla(){
        
        int cant = v.jTable.getRowCount();
        DefaultTableModel tabla = (DefaultTableModel) v.jTable.getModel();
        tabla.setNumRows(0); //pones la cant de filas en cero, y de paso limpias el contenido
        v.btEditar.setEnabled(false);
        v.btEliminar.setEnabled(false);
        v.tfId.setText("");
        v.tfNombre.setText("");
        v.tfCorreo.setText("");
        v.tfTelefono.setText("");
    }     

    /*@Override
    public void insertUpdate(DocumentEvent e) {
        ejecutarBusqueda();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        ejecutarBusqueda();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        ejecutarBusqueda();
    }
    
    public void ejecutarBusqueda(){
        String input = v.tffiltrar.getText();
        dao.filtrar(input, v.jTable);
    }*/
    
}
