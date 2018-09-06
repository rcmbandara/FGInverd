package general;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import beans.CountsOfTiresinWOBean;
import tables.IndividualWO;

public class Counts {

	public int NotEmptyRows(JTable table) {
		int NotemptyRows = 0;
		rowSearch: for (int row = 0; row < table.getRowCount(); row++) { // Iterate
																			// trough
																			// //
																			// rows
			//for (int col = 1; col < 2; col++) { // Iterate through all the
												// columns in the row
				if (table.getValueAt(row, 0) == null) { // Check if the box is
															// empty
					continue rowSearch; // If the value is not null, the row
										// contains stuff so go onto the next
										// row
				}
			//}
			NotemptyRows++; // Conditional never evaluated to true so none of
							// the columns in the row contained anything
		}
		return NotemptyRows;
	}

	public int GetTtl(JTable tbl,int col) {
		try {
			int total = 0;
			Counts counts= new Counts();
			int intNotEmptyRows = counts.NotEmptyRows(tbl);

			for (int i = 0; i < intNotEmptyRows; i++) {
				int Amount = Integer.parseInt(tbl.getValueAt(i, col) + "");
				total = total + Amount;
			}
			return total;

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, e);
			return 0;
		}
	}
	public  int[] getWOStatistics(String WOName, int lastu, Connection conn) {
		// Get the values form the database too in order to ensure final values

		int noOfTires = 0;
		int noOfPIDs = 0;
		int[] x = new int[2];
		// PID Count
		CountsOfTiresinWOBean bean;
		try  {
			bean = IndividualWO.getFinalCountfrmTbl(WOName, conn);
			noOfPIDs = bean.getCount();

			CountsOfTiresinWOBean bean2 = IndividualWO.getFinalSumtfrmTbl(WOName, lastu,conn);
			noOfTires = bean2.getSum();

			x[0] = noOfPIDs;
			x[1] = noOfTires;
			return x;
		} catch (SQLException e) {

			e.printStackTrace();
			// return null;
			return null;
		}
	}
}
