/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;



/**
 *
 * @author soporte
 */
public class Conexion {
    public static Connection conectar() {
        Connection con = null;
        try {
            String url = "jdbc:mysql://localhost:3306/estacionamiento?user=root&password=sombrass";
            con = (Connection) DriverManager.getConnection(url);
            if (con != null) {
                System.out.println("Conexion Satisfactoria");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Conexion con el servidor falló" + e.getMessage());
        }
        return con;
    }
}
