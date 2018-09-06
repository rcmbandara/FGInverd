package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import db.DBType;
import db.DBUtil;
import beans.NotokBean;
import beans.StkTireBean;

public class NotOkTbl {

	public static NotokBean getRow(int SN,Connection conn) {
		String sql = "SELECT * FROM stock.notok WHERE sn = ?";
		ResultSet rs = null;

		try (
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, SN);
			rs = stmt.executeQuery();

			if (rs.next()) {
				NotokBean bean = new NotokBean();
				bean.setSn(rs.getInt("sn"));
				bean.setPid(rs.getInt("pid"));
				bean.setQg(rs.getString("qg"));
				bean.setBcdate(rs.getDate("bcdate"));

				return bean;
			} else {
				return null;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"NotOKTbl-e01");
			return null;
		}

	}

	public static boolean delete(int sn, Connection conn) throws Exception {

		String sql = "DELETE FROM stock.notok WHERE sn = "+sn+";";
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {
			int affected = stmt.executeUpdate();
			if (affected == 1) {
				return true;
			} else { 
				return false;
			} 
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e+"NotOKTbl-e02");
			System.err.println(e);
			return false;
		}
	}
	
	public static boolean insertFGIn(NotokBean bean, Connection conn) throws Exception {
		String sql = "INSERT INTO stock.notok "
				+ "(sn, pid,qg) "
				+ "VALUES (?, ?,?);";

		ResultSet keys = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			stmt.setInt(1, bean.getSn());
			stmt.setInt(2, bean.getPid());
			stmt.setString(3, bean.getQg());

			int affected = stmt.executeUpdate();

			if (affected == 1) {
				
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Error in inserting data to WorkOrdrManager table"+" NotOKTbl-e03");
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error"+"NotOKTbl-e04");
			return false;
		} finally {
			if (keys != null) {
				keys.close();
			}
		}
	}

}
