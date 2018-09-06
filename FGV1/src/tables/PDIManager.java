package tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import beans.PDITireBean;

import java.sql.*;


public class PDIManager {
	public static boolean insert(PDITireBean bean, Connection conn, String TableName) throws Exception {

		String sql = "INSERT INTO `fg`.`" + TableName + "` ( `pid`, `r0`) " + "VALUES (?, ?)";
		ResultSet keys = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			stmt.setInt(1, bean.getPid());
			stmt.setInt(2, bean.getR0());

			int affected = stmt.executeUpdate();

			if (affected == 1) {
				stmt.getGeneratedKeys();
				return true;

			} else {
				JOptionPane.showMessageDialog(null, "No rows affected");
				return false;
			}
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
