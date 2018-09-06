package tables;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.*;
import javax.swing.JOptionPane;

import db.DBType;
import db.DBUtil;

public class CreateWOTbl {

    public static boolean CreateWO(int tableName,Connection conn) throws SQLException {

        String sql = "CREATE TABLE fg." + tableName + " ( "
                + "  pid INT(1) NOT NULL, "
        		+ "TireSize VARCHAR(55) NULL, "
                + "  sqty INT(1) NULL DEFAULT 0, "
                + "  r0 INT(1) NULL DEFAULT NULL, "
                + " PRIMARY KEY (pid)); ";
        ResultSet keys = null;
        try (
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            // stmt.setString(1, tableName);
            int x = stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        } finally {
            if (keys != null) {
                keys.close();
            }
        }
    }

}