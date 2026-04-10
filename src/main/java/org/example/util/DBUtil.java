package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Clase de utilidad para manejar la conexión a la base de datos.
 * Esta clase se encarga de establecer la conexión a la base de datos H2 en memoria y
 * crear la tabla de empleados si no existe. No contiene lógica de negocio ni validaciones,
 * solo se encarga de proporcionar una conexión a la base de datos para que las clases DAO
 * puedan interactuar con ella.
 */
public class DBUtil {

    private static final String URL = "jdbc:h2:mem:empleadosdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASS = "";

    static {
        try {
            Connection conn = getConnection();
            conn.createStatement().execute(
                    "CREATE TABLE empleados (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "nombre VARCHAR(100), " +
                            "apellido VARCHAR(100), " +
                            "rut VARCHAR(20), " +
                            "cargo VARCHAR(50), " +
                            "salario DOUBLE)"
            );
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
