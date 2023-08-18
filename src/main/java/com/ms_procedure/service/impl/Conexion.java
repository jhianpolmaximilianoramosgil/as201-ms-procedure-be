package com.ms_procedure.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Data;

@Data
public class Conexion {

    private static Connection cnx = null;

    public static Connection conectar() {
        try {
            /*String user = "postgres";
            String pwd = "12345";
            String driver = "org.postgresql.Driver";
            String url = "jdbc:postgresql://localhost:5432/ms_procedure";*/

            String user = "postgres";
            String pwd = "*\\PHd-D0c.|\"u(ZI";
            String driver = "org.postgresql.Driver";
            String url = "jdbc:postgresql://104.154.162.191/dbprocedure";

            Class.forName(driver).newInstance();
            cnx = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, "Error de Conexión/Conectar ", e.getMessage());
        }
        return cnx;
    }

    public void cerrar() {
        try {
            if (cnx != null) {
                cnx.close();
            }
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "Error en Cerrar ", e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        conectar();
        try {
            if (cnx != null) {
                System.out.println("CONEXIÓN EXITOSA");
                JOptionPane.showMessageDialog(null, "CONEXIÓN EXITOSA", "CORRECTO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("SIN CONEXIÓN REVISA");
                JOptionPane.showMessageDialog(null, "SIN CONEXIÓN REVISA", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Error en " + e.getMessage());
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
