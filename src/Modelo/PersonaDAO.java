/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.*;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class PersonaDAO {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel modelo = new DefaultTableModel();
    
    public List listar(){
        
        List<Persona> datos = new ArrayList<>();
        
        String sql = "Select ID, Nombre, Correo, Telefono from users";
        try {
            con = conectar.getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Persona p = new Persona();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setCorreo(rs.getString(3));
                p.setTelefono(rs.getString(4));
                datos.add(p);
            }
        }
        catch (Exception ex){
            System.out.println("Error: " + ex);
        }
        return datos;
    }
    
    public List buscar(String fil){
        
        List<Persona> datos = new ArrayList<>();
        
        String sql = "Select ID, Nombre, Correo, Telefono from users where Nombre = '"+fil+"' or Correo = '"+fil+"' or Telefono = '"+fil+"'";;
        try {
            con = conectar.getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Persona p = new Persona();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setCorreo(rs.getString(3));
                p.setTelefono(rs.getString(4));
                datos.add(p);
            }
        }
        catch (Exception ex){
            System.out.println("Error: " + ex);
        }
        return datos;
    }
    public void filtrar(String fil, JTable tabla){
        
        modelo = (DefaultTableModel)tabla.getModel();
        String consulta = "Select ID, Nombre, Correo, Telefono from users where Nombre = '"+fil+"' or Correo = '"+fil+"' or Telefono = '"+fil+"'";
        con = conectar.getCon();
            
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while(rs.next()){
                Object[] lista = {rs.getInt(1), rs.getString(2), rs.getShort(3), rs.getString(4)};
                modelo.addRow(lista);
            }
            tabla.setModel(modelo);
        }
        catch (Exception ex){
            System.out.println("Error"+ex.getMessage());
        }
    }
    
    public int Agreagar (Persona p){
        String sql = "insert into users (Nombre, Correo, Telefono) values (?, ?, ?)";
        try {
            con = conectar.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCorreo());
            ps.setString(3, p.getTelefono());
            ps.executeUpdate();
        }
        catch (Exception ex){
            System.out.println("Error: "+ ex);
            return 0;
        }
        return 1;
    }
    
    public int Modificar (Persona p){
        String sql = "update users set Nombre=?, Correo=?, Telefono=? where ID=?";
        try {
            con = conectar.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCorreo());
            ps.setString(3, p.getTelefono());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        }
        catch (Exception ex){
            System.out.println("Error: "+ ex);
            return 0;
        }
        return 1;
    }
    public int Eliminar (int id){
        String sql = "Delete from users where ID = "+id;
        try {
            con = conectar.getCon();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        }
        catch (Exception ex){
            System.out.println("Error: "+ ex);
            return 0;
        }
        return 1;
    }
}
