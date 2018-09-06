package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import beans.PIDBuildingInfoBean;

public class PIDManager {

	public static PIDBuildingInfoBean getRow(PIDBuildingInfoBean bean2, Connection conn) throws SQLException {
		String sql = "SELECT pid FROM srtspec.pid where sizeid='"+bean2.getSizeid()+"' and brandid='"+bean2.getBrandid()+"' "
				+"and swmsgid = '"+bean2.getSwMsgID()+"' and tiretypeid = '"+bean2.getTireTypeID()+"' and rimsizeid ='"+bean2.getRimSzeID()+"' and wheelcolorid = '"+ bean2.getWheelColorID()+"';";

		ResultSet rs = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {
			
			//stmt.setInt(2, bean2.getBrandid());
			//stmt.setInt(3, bean2.getSwMsgID());
			//stmt.setInt(4, bean2.getTireTypeID());
			//stmt.setInt(5, bean2.getWheelColorID());
			
			rs = stmt.executeQuery();

			if (rs.next()) {
				PIDBuildingInfoBean bean = new PIDBuildingInfoBean();
				bean.setPid(rs.getInt("pid"));

				return bean;
			} else {
				JOptionPane.showMessageDialog(null, "PID is not in List");
				return null;
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

}
