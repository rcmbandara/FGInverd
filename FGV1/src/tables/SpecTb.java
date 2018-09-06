package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import beans.Spec;

public class SpecTb {

	public static Spec getRow(int specid, Connection conn) throws SQLException {

		String sql = "SELECT rndaproval,edc1sttire FROM srtspec.spec WHERE specid = ? ; ";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, specid);
			rs = stmt.executeQuery();

			if (rs.next()) {
				Spec bean = new Spec();
				bean.setEdc1sttire(rs.getInt("edc1sttire"));
				bean.setRndaproval(rs.getInt("rndaproval"));
				return bean;
			} else {
				JOptionPane.showMessageDialog(null, "Pls check the Spec ID");
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
	
	public static boolean UpdateSpec(int specid, Connection conn) {
		String sql = "UPDATE srtspec.spec SET edc1sttire='2', rndaproval='2' WHERE specid='"+specid+"';";

		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			int affected = stmt.executeUpdate();

			if (affected == 1) {

				return true;
			} else {
				System.err.println("Now rows affected");
				return false;
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
			return false;
		}
	}

}
