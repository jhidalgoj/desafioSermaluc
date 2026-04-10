package org.example.dao;

import org.example.model.Empleado;
import org.example.util.DBUtil;

import java.sql.*;
import java.util.*;

/**
 * Clase DAO para manejar operaciones CRUD de empleados en la base de datos.
 * Esta clase no maneja validaciones ni lógica de negocio, solo se encarga de interactuar con la base de datos.
 */
public class EmpleadoDAO {
    public List<Empleado> listar() throws Exception {
        List<Empleado> lista = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM empleados")) {
            while (rs.next()) {
                Empleado e = new Empleado();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                e.setApellido(rs.getString("apellido"));
                e.setRut(rs.getString("rut"));
                e.setCargo(rs.getString("cargo"));
                e.setSalario(rs.getDouble("salario"));
                lista.add(e);
            }
        }
        return lista;
    }

    public void agregar(Empleado e) throws Exception {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO empleados(nombre,apellido,rut,cargo,salario) VALUES(?,?,?,?,?)")) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellido());
            ps.setString(3, e.getRut());
            ps.setString(4, e.getCargo());
            ps.setDouble(5, e.getSalario());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws Exception {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM empleados WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public boolean existeRut(String rut) throws Exception {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM empleados WHERE rut=?")) {
            ps.setString(1, rut);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}
