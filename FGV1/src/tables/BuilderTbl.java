package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import beans.hrtrackBean;
import javax.swing.JOptionPane;

import beans.LocationBean;

public class BuilderTbl {

	public static hrtrackBean getRow(String stDate, String endDate,String timeRange, Connection conn) throws SQLException {

		hrtrackBean bean = new hrtrackBean();

				String sql = "SELECT count(sn),sum(actwgt) co FROM mfg.builder WHERE mfgdate >= '" + stDate + "' AND mfgdate <= '" + endDate
				+ "'";
		ResultSet rs = null;
		System.out.println(sql);
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			rs = stmt.executeQuery();
			
			if (rs.next()) {
				bean.setTimerange(timeRange);
				bean.setNos(rs.getInt(1));
				bean.setWgt(rs.getDouble(2));
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
