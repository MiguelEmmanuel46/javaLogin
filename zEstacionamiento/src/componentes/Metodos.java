package componentes;

import javax.swing.table.DefaultTableModel;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
//import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

public class Metodos 
{
    //public static  DefaultTableModel modeloF = new DefaultTableModel();
   // FTPClient ftp = new FTPClient();
    Conexion o = new Conexion();
    public int datoSeleccionado;
    DefaultTableModel modelo;
    public static String fileNameToBase;
    public static String consultaFiltrarToExcel;
    public static String[] vect2;
    
        public boolean login() throws SQLException {
        boolean matched2 = false;
        String correoBD = null, passwordBD = null;
        char[] arrayC = vistas.Login.passwordU.getPassword();
        String originalPassword = String.valueOf(arrayC);
        String correo = vistas.Login.correoU.getText();
        PreparedStatement stmt2 = null;
        ResultSet rs2 = null;
        boolean matched = false;
        if (arrayC.equals(null) || arrayC.equals("")) {
            JOptionPane.showMessageDialog(null, "Ingresa Password");
        }
        if (correo.equals(null) || correo.equals("")) {
            JOptionPane.showMessageDialog(null, "Ingresa Correo");
        }
        stmt2 = Conexion.conectar().prepareStatement("SELECT correo,password "
                + "FROM usuarios WHERE correo='" + correo + "' ");

        try {
            rs2 = stmt2.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex);
        }
        try {
            while (rs2.next()) {
                correoBD = rs2.getString("correo");
                passwordBD = rs2.getString("password");

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex);
        }

        try {
            matched = Password.validatePassword(originalPassword, passwordBD);
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex);
        } catch (InvalidKeySpecException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex);
        }
        //Panel.usuarioTS=correoBD;
        //Vistas.Consulta.usuarioTS = correoBD;
        matched2 = matched;
        return matched2;
    }

    public void registro() {
        String correoBD = null, passwordBD = null, passRoot = null, nombre = vistas.Register.campoNombre.getText(), apellidop = vistas.Register.campoApellidoP.getText(), apellidom = vistas.Register.campoApellidoM.getText(), area = vistas.Register.campoArea.getText();
        char[] arrayC = vistas.Register.passwordR.getPassword();
        //char[] arr = { 'p', 'q', 'r', 's' };
        String originalPassword = String.valueOf(arrayC);
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        String correo = vistas.Register.correoR.getText();
        Matcher mather = pattern.matcher(correo);
        String generatedSecuredPasswordHash = null;
        ResultSet rs1 = null;
        ResultSet rs3 = null;
        Vector<String> v = new Vector<String>();
        boolean usuarioExistente = false;
        char[] passURoot = vistas.Register.passwordRRoot.getPassword();
        //char[] arr = { 'p', 'q', 'r', 's' };
        String originalPasswordRoot = String.valueOf(passURoot);
        boolean matched = false;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        try {
            PreparedStatement smt3 = Conexion.conectar().prepareStatement("SELECT pass from super_user");
            rs3 = smt3.executeQuery();
            while (rs3.next()) {
                passRoot = rs3.getString("pass");

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error:" + e);
        }
        try {
            matched = Password.validatePassword(originalPasswordRoot, passRoot);
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex);
        } catch (InvalidKeySpecException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex);
        }

        if (mather.find() == true) {
            if (matched == true) {
                try {
                    generatedSecuredPasswordHash = Password.generateStorngPasswordHash(originalPassword);
                } catch (NoSuchAlgorithmException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex);
                } catch (InvalidKeySpecException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex);
                }

                try {
                    PreparedStatement smt1 = Conexion.conectar().prepareStatement("SELECT correo from usuarios");
                    rs1 = smt1.executeQuery();
                    while (rs1.next()) {
                        v.add(rs1.getString("correo"));

                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error:" + e);
                }

                for (int i = 0; i < v.size(); i++) {
                    if (v.elementAt(i).equals(correo)) {
                        usuarioExistente = true;
                    } else {
                        usuarioExistente = false;
                    }
                }

                try {
                    matched = Password.validatePassword(originalPasswordRoot, passRoot);
                } catch (NoSuchAlgorithmException ex) {
                    JOptionPane.showMessageDialog(null, "Error:" + ex);
                } catch (InvalidKeySpecException ex) {
                    JOptionPane.showMessageDialog(null, "Error:" + ex);
                }

                if (usuarioExistente == false) {
                    try {

                        PreparedStatement stmt = Conexion.conectar().prepareStatement("insert into usuarios values('" + correo + "','" + nombre + "','" + apellidop + "','" + apellidom + "','" + generatedSecuredPasswordHash + "','" + area + "',NOW(),NOW())");
                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Correo: " + correo + " registrado correctamente en la base de datos");

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error:" + ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario registrado previamente");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Password root equivocada");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Error formato de correo invalido");
        }
    }
}
