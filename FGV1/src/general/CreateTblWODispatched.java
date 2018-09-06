package general;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class CreateTblWODispatched {

	  public static boolean CreateWO(String tableName,Connection conn) throws SQLException {

	        String sql ="CREATE TABLE dispatches."+tableName+"(sn integer,pid integer, qg text,   CONSTRAINT a"+tableName+" PRIMARY KEY (sn) )  WITH (   OIDS = FALSE );";
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
