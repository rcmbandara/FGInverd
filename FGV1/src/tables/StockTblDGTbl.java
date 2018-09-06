package tables;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import beans.StkTireBean;
import beans.StocktbldgBean;
import db.DBType;
import db.DBUtil;

public class StockTblDGTbl {

	public static int getSum(String Loc, int lid) {
		int Total = 0;

		String sql = "";

		if ("Locaion".equals(Loc)) {
			sql = "SELECT count(lid) x FROM stock.stocktbldg WHERE lid = " + lid + ";";
		} else if ("Total".equals(Loc)) {
			sql = "SELECT count(sn) x FROM stock.stocktbldg ;";
		} else {
			JOptionPane.showMessageDialog(null, "error");
		}

		ResultSet rs = null;

		try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			rs = stmt.executeQuery();

			if (rs.next()) {
				Total = rs.getInt("x");
			}

		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2 + "FGDGStockTblDG-E01");
		}

		return Total;
	}
	
	public static boolean insertFGDGIn(StocktbldgBean bean, Connection conn) throws Exception {
		String sql = "INSERT INTO stock.stocktbldg (sn, pid, qualitygrade,  lid) VALUES (?, ?, ?, ?);";

		ResultSet keys = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			 stmt.setInt(1, bean.getSn());
	            stmt.setInt(2, bean.getPid());
	            stmt.setString(3, bean.getQualitygrade());
	            stmt.setInt(4, bean.getLid());

	            int affected = stmt.executeUpdate();

			if (affected == 1) {

				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Error in inserting data to  table");
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e+"FGDGStockTblDG-e02");
			return false;
		} finally {
			if (keys != null) {
				keys.close();
			}
		}
	}
	
}
