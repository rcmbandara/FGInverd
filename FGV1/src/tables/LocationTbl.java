package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.swing.JOptionPane;

import beans.LocationBean;

public class LocationTbl {

	public static LocationBean getRow(String Loc, Connection conn) throws SQLException {

		String sql ="SELECT * FROM stock.locations_dg WHERE location = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setString(1, Loc);
			rs = stmt.executeQuery();

			if (rs.next()) {
				LocationBean bean = new LocationBean();
				bean.setLid(rs.getInt("lid"));
				bean.setLocation(rs.getString("location"));
				return bean;
			} else {
				JOptionPane.showMessageDialog(null, "Issue with Location Table in DB");
				return null;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
	
	
}
