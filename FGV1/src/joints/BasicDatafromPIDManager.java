package joints;

import db.DBType;
import db.DBUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import java.sql.*;
import beans.BasicDatafromPIDBean;

public class BasicDatafromPIDManager {
	public static BasicDatafromPIDBean getRow(int PID,Connection conn) throws SQLException {
		String sql =  "select tiresizebasic,config,lugtype,rimsize,tiretype,swmsg,brand,pid,color "
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
				BasicDatafromPIDBean bean = new BasicDatafromPIDBean();
				bean.setSizebasic(rs.getString("tiresizebasic"));
				bean.setConfig(rs.getString("config"));
				bean.setLugtype(rs.getString("lugtype"));
				bean.setRimsize(rs.getString("rimsize"));
				bean.setTiretype(rs.getString("tiretype"));
				bean.setSwmsg(rs.getString("swmsg"));
				bean.setBrand(rs.getString("brand"));
				bean.setPID(rs.getInt("pid"));
				bean.setWheelcolor(rs.getString("color"));
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
