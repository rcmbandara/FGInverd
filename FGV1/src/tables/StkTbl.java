package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import db.DBType;
import db.DBUtil;
import joints.BasicDatafromPIDManager;
import beans.BasicDatafromPIDBean;
import beans.StkTireBean;
import beans.WorkOrderDetailBean;

public class StkTbl {
	public static StkTireBean getRow(int SN, Connection conn) {
		String sql = "SELECT * FROM stock.stk WHERE sn = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, SN);
			rs = stmt.executeQuery();

			if (rs.next()) {
				StkTireBean bean = new StkTireBean();
				bean.setSn(rs.getInt("sn"));
				bean.setPid(rs.getInt("pid"));
				bean.setPidbc(rs.getInt("pidbc"));
				bean.setQg(rs.getString("qg"));
				bean.setMfgdate(rs.getDate("mfgdate"));
				bean.setTc(rs.getInt("tc"));
				bean.setBcdate(rs.getDate("bcdate"));
				bean.setFgindate(rs.getDate("fgindate"));
				bean.setFgoutdate(rs.getDate("fgoutdate"));
				bean.setAvl(rs.getInt("avl"));
				bean.setStkvfy(rs.getInt("stkvfy"));
				bean.setTdispatch(rs.getInt("tdispatch"));
				return bean;

			} else {
				return null;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"StkTbl-e01");
			return null;
		}

	}

	public static int getCountofPID(int pid, Connection conn) {
		String sql = "SELECT count(pid) cnt FROM stock.checked where pid = '" + pid + "'and avl='1';";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {
			// stmt.setInt(1, pid);
			rs = stmt.executeQuery();

			if (rs.next()) {
				int count = rs.getInt("cnt");

				return count;

			} else {
				return 0;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"StkTbl-e02");
			return 0;
		}
	}

	public static boolean insertFGIn(StkTireBean bean, Connection conn) throws Exception {
		String sql = "INSERT INTO stock.stk " + "(sn, pid, qg,fgindate,avl) "
				+ "VALUES (?, ?, ?,now(),?);";

		ResultSet keys = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			stmt.setInt(1, bean.getSn());
			stmt.setInt(2, bean.getPid());
			stmt.setString(3, bean.getQg());
			stmt.setInt(4, bean.getAvl());

			int affected = stmt.executeUpdate();

			if (affected == 1) {

				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Error in inserting data to WorkOrdrManager table");
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e+"StkTbl-e03");
			return false;
		} finally {
			if (keys != null) {
				keys.close();
			}
		}
	}

	public static boolean insertDelfrmStkTbl(StkTireBean bean, Connection conn,String Cat,String Remarkx) throws Exception {
		String sql = "INSERT INTO stock.delfrmstk " + "(sn, pid, qg,date,catogery,remarx) "
				+ "VALUES (?, ?, ?,now(),?,?);";

		ResultSet keys = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			stmt.setInt(1, bean.getSn());
			stmt.setInt(2, bean.getPid());
			stmt.setString(3, bean.getQg());
			stmt.setString(4, Cat);	
			stmt.setString(5, Remarkx);

			int affected = stmt.executeUpdate();

			if (affected == 1) {
				//JOptionPane.showMessageDialog(null,"Inserted");
				return true;
				
			} else {
				JOptionPane.showMessageDialog(null, "Error in inserting data to WorkOrdrManager table");
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e+"StkTbl-e03");
			return false;
		} finally {
			if (keys != null) {
				keys.close();
			}
		}
	}
	
	
	public static boolean updateAvlnPID(StkTireBean bean, Connection conn) throws Exception {
		// updatse the availability column in stock table
		int sn = bean.getSn();
		int avl = bean.getAvl();
		int pid = bean.getPid();
		String qg = bean.getQg();

		String sql = "UPDATE stock.stk SET pid='" + pid + "', avl='" + avl + "',fgindate = now(),qg='" + qg + "' WHERE sn='"
				+ sn + "';";
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			int affected = stmt.executeUpdate();
			if (affected > 0) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e+"StkTbl-e04");
			return false;
		}
	}
	
	
	public static boolean DeleteRow(int sn, Connection conn) throws Exception {
		String sql = "DELETE FROM stock.stk WHERE sn='"+sn+"';";
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			int affected = stmt.executeUpdate();
			if (affected > 0) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e+"StkTbl-e04");
			return false;
		}
	}
	
	
	
	public static boolean updatetDispatchesinStkTbl(StkTireBean bean, Connection conn) throws Exception {
		// updatse the availability column in stock table
		int sn = bean.getSn();
		
		String sql = "UPDATE stock.stk SET  avl=0,tdispatch=1,fgindate = now() WHERE sn='"
				+ sn + "';";
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			int affected = stmt.executeUpdate();
			if (affected > 0) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e+"PDITemp - 5");
			return false;
		}

	}
	public static boolean updatetEDCCutTire(int sn, Connection conn) throws Exception {
		// updatse the availability column in stock table for EDC Cut Tires
		
		
		String sql = "UPDATE stock.stk SET  avl=0,qg = 'R',customer='EDC Cut Tire',fgindate = now()  WHERE sn='"
				+ sn + "';";
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			int affected = stmt.executeUpdate();
			if (affected > 0) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e+"PDI - 5");
			return false;
		}

	}

	public static int getTotal(Connection conn) {

		String sql = "SELECT count(sn) cnt FROM stock.stk where  avl='1';";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {
			// stmt.setInt(1, pid);
			rs = stmt.executeQuery();

			if (rs.next()) {
				int count = rs.getInt("cnt");

				return count;

			} else {
				return 0;
			}
		} catch  (Exception e) {
			JOptionPane.showMessageDialog(null, e+"StkTbl-e05");
			return 0;
		}

	}

	
}
