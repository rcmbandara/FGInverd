package tables;

import beans.WorkOrderDetailBean;
import db.DBType;
import db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class WorkOrderDetailManager {

	public static boolean insert(WorkOrderDetailBean bean, Connection conn) throws Exception {
		String sql = "INSERT INTO `fg`.`workoderdetails` "
				+ "(`wo`, `revision`, `customer`, `suborderno`, `invoiceno`,`pids`,`nos`) "
				+ "VALUES (?, ?, ?, ?, ?,?,?);";

		ResultSet keys = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			stmt.setInt(1, bean.getWo());
			stmt.setInt(2, bean.getRevision());
			stmt.setString(3, bean.getCustomer());
			stmt.setString(4, bean.getSuborderno());
			stmt.setString(5, bean.getInvoiceno());
			stmt.setInt(6, bean.getPids());
			stmt.setInt(7, bean.getNos());

			int affected = stmt.executeUpdate();

			if (affected == 1) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Error in inserting data to WorkOrdrManager table");
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

	public static WorkOrderDetailBean getRow_WOandRevIsZero(int WO, int lastu,Connection conn) throws SQLException {

		String sql = "SELECT * FROM fg.workoderdetails WHERE wo = ? and revision = ? ";
		ResultSet rs = null;

		try (
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, WO);
			stmt.setInt(2, lastu);
			rs = stmt.executeQuery();

			if (rs.next()) {
				WorkOrderDetailBean bean = new WorkOrderDetailBean();
				bean.setWo(WO);
				bean.setWoid(rs.getInt("woid"));
				bean.setRevision(rs.getInt("revision"));
				bean.setCustomer(rs.getString("customer"));
				bean.setSuborderno(rs.getString("suborderno"));
				bean.setInvoiceno(rs.getString("invoiceno"));
				bean.setDate(rs.getDate("date"));
				bean.setPids(rs.getInt("pids"));
				bean.setNos(rs.getInt("nos"));

				return bean;
			} else {
				System.err.println("Error");
				return null;
			}

		} catch (Exception e) {
			System.err.println(e);
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public static WorkOrderDetailBean LastRevision(String WO,Connection conn) {
		try {
			String sql = "SELECT max(revision) rev FROM fg.workoderdetails where wo =" + WO + "";
			ResultSet rs = null;

			try (
					PreparedStatement stmt = conn.prepareStatement(sql);) {

				rs = stmt.executeQuery();

				if (rs.next()) {
					WorkOrderDetailBean bean = new WorkOrderDetailBean();
					bean.setLastRevision(rs.getInt("rev"));

					return bean;

				} else {
					System.err.println("Error");
					return null;
				}
			} catch (Exception e) {
				System.err.println(e);
				return null;
			} finally {
				if (rs != null) {
					rs.close();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}

	}

}
