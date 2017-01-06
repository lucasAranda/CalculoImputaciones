/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maquina0
 */
public class ConexionSQLServer {

    public Connection conn;
    private Statement statement;
    public static ConexionSQLServer db;
    //private static final String CONEXION_TANGO = "jdbc:sqlserver://192.168.2.10:1433;databaseName=HORMICON_DOS;user=sa;password=root";
    private static String CONEXION_TANGO = "jdbc:sqlserver://192.168.2.205:1433;databaseName=HORMICON;user=axoft;password=Axoft";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    private ConexionSQLServer() {
        try {
            Class.forName(DRIVER).newInstance();
            this.conn = (Connection) DriverManager.getConnection(CONEXION_TANGO);
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    public static ConexionSQLServer getConexion() {
        if (db == null) {
            db = new ConexionSQLServer();
        }
        return db;
    }

    public ResultSet ejecutarConsulta(String query) throws SQLException {
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }

    public ResultSet obtenerBases() throws SQLException {
        ResultSet res = db.conn.getMetaData().getCatalogs();
        return res;
    }

    public boolean modificarConexion(String dbName) {
        try {
            CONEXION_TANGO = "jdbc:sqlserver://192.168.2.205:1433;databaseName=" + dbName + ";user=axoft;password=Axoft";
            Class.forName(DRIVER).newInstance();
            this.conn = (Connection) DriverManager.getConnection(CONEXION_TANGO);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionSQLServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (InstantiationException ex) {
            Logger.getLogger(ConexionSQLServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ConexionSQLServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionSQLServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
