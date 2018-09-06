package joints;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import beans.PIDBuildingInfoBean;

public class PIDBuildingInfofromPIDManager {
	
	public static PIDBuildingInfoBean getRow(int PID,Connection conn) throws SQLException {
		String sql =  "select s.sizeid sid,br.brandid brid,sw.swmsgid swmsgid,tt.tiretypeid ttid,rs.rimsizeid rsid,wc.wheelcolorid wcid  "
                + "from srtspec.pid p "
                + "join srtspec.size s on s.sizeid = p.sizeid "
                + "join srtspec.sizebasic sb on sb.sizebasicid = s.sizebasicid "
                + "join srtspec.config c on c.configid = s.configid "
                + "join srtspec.lugtype l on l.lugtypeid = s.lugtypeid "
                + "join srtspec.rimsize rs on rs.rimsizeid = p.rimsizeid "
                + "join srtspec.tiretype tt on tt.tiretypeid = p.tiretypeid "
                + "join srtspec.swmsg sw on sw.swmsgid = p.swmsgid "
                + "join srtspec.brand br on br.brandid = p.brandid "
                + "join srtspec.wheelcolor wc on wc.wheelcolorid = p.wheelcolorid "
                + "where p.pid = "+PID+";";

		ResultSet rs = null;
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			
			rs = stmt.executeQuery();

			if (rs.next()) {
				PIDBuildingInfoBean bean = new PIDBuildingInfoBean();
				bean.setSizeid(rs.getInt("sid"));
				bean.setBrandid(rs.getInt("brid"));
				bean.setSwMsgID(rs.getInt("swmsgid"));
				bean.setTireTypeID(rs.getInt("ttid"));
				bean.setWheelColorID(rs.getInt("wcid"));
				bean.setRimSzeID(rs.getInt("rsid"));
				return bean;
			} else {
				JOptionPane.showMessageDialog(null, "PID is not in List");
				return null;
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,e);
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
}
