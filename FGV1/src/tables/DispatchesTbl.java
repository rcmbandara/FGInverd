package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import beans.DispatchTblBean;

public class DispatchesTbl {

	public static boolean insertDispatches(String tblName,DispatchTblBean bean, Connection conn) throws Exception {
		String sql = "INSERT INTO dispatches."+tblName+" (sn, pid, qg) VALUES (?, ?, ?);";

		ResultSet keys = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			stmt.setInt(1, bean.getSn());
			stmt.setInt(2, bean.getPid());
			stmt.setString(3, bean.getQg());
			

			int affected = stmt.executeUpdate();

			if (affected == 1) {

				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Error in inserting data to  table");
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "PDI_Temp E-2");
			return false;
		} finally {
			if (keys != null) {
				keys.close();
			}
		}
	}

}
