package tables;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import beans.WorkOrderDetailBean;
import beans.BasicDatafromPIDBean;
import beans.CountsOfTiresinWOBean;
import beans.PDITireBean;
import beans.StkTireBean;
import db.DBType;
import db.DBUtil;
import general.ColorColumnRenderer;
import general.MyCellRenderer;
import joints.BasicDatafromPIDManager;

public class IndividualWO {

	public static CountsOfTiresinWOBean getFinalCountfrmTbl(String WOName, Connection conn) throws SQLException {

		String sql = "SELECT count(pid) as x FROM fg." + WOName + "";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			rs = stmt.executeQuery();

			if (rs.next()) {
				CountsOfTiresinWOBean bean = new CountsOfTiresinWOBean();

				bean.setCount(rs.getInt("x"));

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

	// Total no of tires in r0
	public static CountsOfTiresinWOBean getFinalSumtfrmTbl(String WOName, int lastu, Connection conn2)
			throws SQLException {

		String sql = "SELECT sum(r" + lastu + ") as x FROM fg." + WOName + ";";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn2.prepareStatement(sql);) {

			rs = stmt.executeQuery();

			if (rs.next()) {
				CountsOfTiresinWOBean bean = new CountsOfTiresinWOBean();

				bean.setSum(rs.getInt("x"));

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

	public static void addCol(int lastu, String WOName, Connection conn) throws Exception {// Add
		// new
		// column
		// in
		// revise
		// work
		// order
		int newu = lastu + 1;
		String sql = "ALTER TABLE fg." + WOName + " ADD COLUMN r" + newu + " INT(1) NULL DEFAULT NULL AFTER r"
				+ lastu + ";";

		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		) {
			stmt.execute();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			// return false;
		}
	}

	public static void delCol(int lastu, String WOName, Connection conn) throws Exception {// Add
		// new
		// column
		// in
		// revise
		// work
		// order
		// int newu = lastu ;
		String sql = "ALTER TABLE  fg." + WOName + " Drop COLUMN  r" + lastu + "; ";

		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		) {
			stmt.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			// return false;
		}
	}

	public static void CopyCol(int lastu, String WOName, Connection conn) throws Exception {// Copy

		int newu = lastu + 1;
		String sql = "UPDATE fg." + WOName + " SET r" + newu + " = r" + lastu + ";";

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static JTable createTableWithNewRevision(String WOName, Connection conn) throws SQLException {
		// get the data from database and add empty column for tire size
		Vector data = new Vector();
		Vector columns = new Vector();
		PreparedStatement ps = null;
		int columnCount = 0;

		try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery("SELECT * FROM fg.`" + WOName + "`");) {

			ResultSetMetaData md = rs.getMetaData();
			columnCount = md.getColumnCount();
			// store column names
			for (int i = 1; i <= columnCount; i++) {
				columns.add(md.getColumnName(i));
			}
			columns.addElement("r" + (columnCount - 3));// Add_new_Column
			columns.ensureCapacity(columnCount);

			Vector row;
			while (rs.next()) {
				row = new Vector(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					row.add(rs.getString(i));

				}
				row.add(rs.getString(columnCount));// AddData_to_New_Column
				data.add(row);
			}
		}

		final int intLastColNoTbl = columnCount + 1;// LastColNo
		DefaultTableModel tableModel = new DefaultTableModel(data, columns) {

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return columnIndex == intLastColNoTbl - 1; // column index
															// you.want.to.be.editable
			}
		};

		JTable table = new JTable(tableModel);

		// Color the last column
		TableColumn tm = table.getColumnModel().getColumn(columnCount);
		tm.setCellRenderer(new ColorColumnRenderer(Color.cyan, Color.RED));

		// Color the Scanned Qty
		TableColumn tm2 = table.getColumnModel().getColumn(2);
		tm2.setCellRenderer(new ColorColumnRenderer(Color.yellow, Color.BLUE));

		// Set the column widhts
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(395);
		table.getColumnModel().getColumn(2).setResizable(true);
		table.getColumnModel().getColumn(2).setPreferredWidth(35);
		for (int i = 3; i < columnCount + 1; i++) {
			table.getColumnModel().getColumn(i).setResizable(false);
			table.getColumnModel().getColumn(i).setPreferredWidth(30);
		}
		table.getTableHeader().setReorderingAllowed(false); // ColumnOrderChanging
		return table;
	}

	@SuppressWarnings("unchecked")
	public static JTable createTablePDI(String WOName, Connection conn) throws SQLException {
		// get the data from database and add empty column for tire size
		Vector data = new Vector();
		Vector columns = new Vector();
		PreparedStatement ps = null;
		int columnCount = 0;
		JTable table = null;

		try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery("SELECT * FROM fg.`" + WOName + "`");) {

			ResultSetMetaData md = rs.getMetaData();
			columnCount = md.getColumnCount();
			// store column names

			columns.add("PID");// PID
			columns.add("Tire Size");// TireSize

			columns.addElement("Stock");// Add_new_Column

			columns.add("WO");// LastRevision
			columns.add("Scan Qty");// ScannedNos
			columns.add("Balance");
			columns.ensureCapacity(columnCount);
			Vector row;
			while (rs.next()) {
				row = new Vector(5);

				row.add(rs.getInt(1));
				row.add(rs.getString(2));
				row.add(StkTbl.getCountofPID((rs.getInt(1)), conn));

				row.add(rs.getString(columnCount));// AddData_to_New_Column
				row.add(rs.getString(3));

				int sc = Integer.parseInt(rs.getString(3));

				row.add(rs.getInt(columnCount) - sc);
				data.add(row);
			}

			final int intLastColNoTbl = columnCount + 1;// LastColNo
			DefaultTableModel tableModel = new DefaultTableModel(data, columns) {

				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return columnIndex == 1; // column.index.you.want.
												// to.be.editable
				}
			};
			tableModel.addTableModelListener(new TableModelListener() {
				
				@Override
				public void tableChanged(TableModelEvent e) {
					System.out.println("Changed");
					
				}
			});

			table = new JTable(tableModel);
			table.setFont(new Font("Tahoma", Font.PLAIN, 15));
			// Color the last column
			TableColumn tm = table.getColumnModel().getColumn(3);
			tm.setCellRenderer(new ColorColumnRenderer(Color.cyan, Color.RED));

			// Color the Scanned Qty
			TableColumn tm2 = table.getColumnModel().getColumn(4);
			tm2.setCellRenderer(new ColorColumnRenderer(Color.white, Color.BLUE));

			// Color the Scanned Qty
			TableColumn tm3 = table.getColumnModel().getColumn(5);
			tm3.setCellRenderer(new ColorColumnRenderer(Color.green, Color.BLUE));

			// table.setDefaultRenderer(Object.class, new MyCellRenderer());

			// Set the column widhts
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(80);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(455);
			table.getColumnModel().getColumn(2).setResizable(true);
			table.getColumnModel().getColumn(2).setPreferredWidth(50);
			table.getColumnModel().getColumn(3).setResizable(true);
			table.getColumnModel().getColumn(3).setPreferredWidth(50);
			table.getColumnModel().getColumn(4).setResizable(true);
			table.getColumnModel().getColumn(4).setPreferredWidth(50);
			table.getColumnModel().getColumn(5).setResizable(true);
			table.getColumnModel().getColumn(5).setPreferredWidth(50);

			table.getTableHeader().setReorderingAllowed(false); // ColumnOrderChanging
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
			table.setVisible(false);
		}
		return table;
	}

	public static int getNoOfUpdates(String WOName, Connection conn) throws Exception {
		int lastu = 0;
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM fg.`" + WOName + "`");

			//
			// The ResultSetMetaData is where all metadata related information
			// for a result set is stored.
			//
			ResultSetMetaData metadata = resultSet.getMetaData();
			int columnCount = metadata.getColumnCount();

			//
			// To get the column names we do a loop for a number of column count
			// returned above. And please remember a JDBC operation is 1-indexed
			// so every index begin from 1 not 0 as in array.
			//
			ArrayList<String> columns = new ArrayList<String>();
			for (int i = 1; i < columnCount + 1; i++) {
				String columnName = metadata.getColumnName(i);
				columns.add(columnName);
			}
			int itemCount = columns.size();
			lastu = itemCount - 4;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastu;

	}

	public static PDITireBean findPID(String WOName, int PID, Connection conn) throws SQLException {

		String sql = "SELECT * FROM fg.`" + WOName + "` where PID = " + PID + ";";

		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {
			int lastu = IndividualWO.getNoOfUpdates(WOName, conn);
			rs = stmt.executeQuery();

			if (rs.next()) {
				PDITireBean bean = new PDITireBean();
				switch (lastu) {
				case 0:
					bean.setR0(rs.getInt("r0"));
					bean.setLastUVal(rs.getInt("r0"));
					break;
				case 1:
					bean.setR1(rs.getInt("r1"));
					bean.setLastUVal(rs.getInt("r1"));

					break;
				case 2:
					bean.setR2(rs.getInt("r2"));
					bean.setLastUVal(rs.getInt("r2"));
					break;
				case 3:
					bean.setR3(rs.getInt("r3"));
					bean.setLastUVal(rs.getInt("r3"));
					break;
				case 4:
					bean.setR4(rs.getInt("r4"));
					bean.setLastUVal(rs.getInt("r4"));
					break;
				case 5:
					bean.setR5(rs.getInt("r5"));
					bean.setLastUVal(rs.getInt("r5"));
					break;
				case 6:
					bean.setR6(rs.getInt("r6"));
					bean.setLastUVal(rs.getInt("r6"));
					break;
				case 7:
					bean.setR7(rs.getInt("r7"));
					bean.setLastUVal(rs.getInt("r7"));
					break;
				case 8:
					bean.setR8(rs.getInt("r8"));
					bean.setLastUVal(rs.getInt("r8"));
					break;
				case 9:
					bean.setR9(rs.getInt("r9"));
					bean.setLastUVal(rs.getInt("r9"));
					break;
				case 10:
					bean.setR10(rs.getInt("r10"));
					bean.setLastUVal(rs.getInt("r10"));
					break;
				case 11:
					bean.setR11(rs.getInt("r11"));
					bean.setLastUVal(rs.getInt("r11"));
					break;
				case 12:
					bean.setR12(rs.getInt("r12"));
					bean.setLastUVal(rs.getInt("r12"));
					break;
				case 13:
					bean.setR13(rs.getInt("r13"));
					bean.setLastUVal(rs.getInt("r13"));
					break;
				case 14:
					bean.setR14(rs.getInt("r14"));
					bean.setLastUVal(rs.getInt("r14"));
					break;
				case 15:
					bean.setR15(rs.getInt("r15"));
					bean.setLastUVal(rs.getInt("r15"));
					break;
				}
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

	@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
	public static JTable createPIDLIstJTbl(String ip, Connection conn) throws SQLException {
		// get the data from database and add empty column for tire size
		Vector data = new Vector();
		Vector columns = new Vector();
		PreparedStatement ps = null;
		int columnCount = 0;
		JTable table = null;

		try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery("SELECT * FROM stock.temppid" + ip + ";");) {

			ResultSetMetaData md = rs.getMetaData();
			columnCount = md.getColumnCount();
			// store column names

			columns.add("PID");// PID
			columns.add("Tire Size");// TireSize

			columns.addElement("LugType");// Add_new_Column

			columns.add("Config");// LastRevision
			columns.add("RimSize");// ScannedNos
			columns.add("TireType");
			columns.add("Brand");
			columns.add("SWMsg");
			columns.add("WheelColor");

			columns.ensureCapacity(columnCount);
			Vector row;
			while (rs.next()) {
				row = new Vector(7);

				row.add(rs.getInt(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				row.add(rs.getString(5));
				row.add(rs.getString(6));
				row.add(rs.getString(7));
				row.add(rs.getString(8));
				row.add(rs.getString(9));
				data.add(row);
			}

			DefaultTableModel tableModel = new DefaultTableModel(data, columns) {

				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return columnIndex == 1; // column.index.you.want.
												// to.be.editable
				}
			};

			table = new JTable(tableModel);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
			table.setVisible(false);
		}
		return table;
	}

	public static boolean UpdateReviceWO(String WOName, int lastCol, int nos, int PID, Connection conn) {
		String sql = "UPDATE `fg`.`" + WOName + "` SET `r" + lastCol + "`='" + nos + "' WHERE `pid`='" + PID + "';";

		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			int affected = stmt.executeUpdate();

			if (affected == 1) {

				return true;
			} else {
				System.err.println("Now rows affected");
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean insertToRWO(String WOName, int lastCol, int nos, int PID, Connection conn) {

		String sql = "INSERT INTO `fg`.`" + WOName + "` (`pid`, `r" + lastCol + "`) VALUES ('" + PID + "', '" + nos
				+ "');";

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

	public static boolean deleteFrmRWO(String WOName, int PID, Connection conn) {

		String sql = "DELETE FROM `fg`.`" + WOName + "` WHERE `pid`='" + PID + "';";

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

	@SuppressWarnings("unused")
	public void UpdateTireSizes(int intNotEmptyRows, DefaultTableModel dm, int ColumnNo, JTable table,
			Connection conn) {

		for (int i = 0; i < intNotEmptyRows; i++) {
			int pid = Integer.parseInt(table.getValueAt(i, 0) + "");
			try {
				String sizebasic;
				String lugtype;
				String config;
				String rimsize;
				String tiretype;
				String swmsg;
				String brand;

				try {
					BasicDatafromPIDBean bean = BasicDatafromPIDManager.getRow(pid, conn);

					if (bean != null) {
						sizebasic = bean.getSizebasic();
						lugtype = bean.getLugtype();
						config = bean.getConfig();
						rimsize = bean.getRimsize();
						tiretype = bean.getTiretype();
						brand = bean.getBrand();
						swmsg = bean.getSwmsg();

						String tiresize = sizebasic + " " + lugtype + " " + config + " " + rimsize + " " + tiretype
								+ " " + brand + " " + swmsg;

						dm.setValueAt(tiresize, i, ColumnNo);

					}
				} catch (SQLException ex) {
					System.out.println("Error");
				}
			} catch (Exception e) {
				System.out.println("Error");
			}
		}
	}

	public static boolean UpdateWOforScan(String WOName, int nos, int PID, Connection conn) {
		String sql = "UPDATE `fg`.`" + WOName + "` SET `sqty`='" + nos + "' WHERE `pid`='" + PID + "';";

		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			int affected = stmt.executeUpdate();

			if (affected == 1) {

				return true;
			} else {
				System.err.println("Now rows affected");
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}
}
