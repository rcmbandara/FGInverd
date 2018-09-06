package guis;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.SoftBevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import beans.CountsOfTiresinWOBean;
import beans.NotokBean;
import beans.PDITireBean;
import beans.StkTireBean;
import beans.WorkOrderDetailBean;
import db.DBType;
import db.DBUtil;
import general.Counts;
import tables.IndividualWO;
import tables.NotOkTbl;
import tables.StkTbl;
import tables.WorkOrderDetailManager;

import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class PDI extends JFrame {

	private JPanel contentPane;
	private JLabel lblWOPdi;
	private JLabel lblCustomerPdi;
	private JLabel lblInvoicePdi;
	private JLabel lblOrderDatePdi;
	private JTable jtblPDI;
	private JComboBox cmbxWOPdi;
	Vector originalTableModel;

	private JScrollPane scpWo;
	private JPanel pnlWODetailPdi;

	private JTextField txtBarCodePid;
	boolean flag = false;
	static PDI frame = new PDI();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String M_CONN_STRING="";
					try {
						FileReader fr = new FileReader("C:\\Program Files\\MySQL\\MySQL Server 5.7\\DBUtil.txt");
						//FileReader fr = new FileReader("/home/fg-admin/DBUtil/DBUtil.txt");
						
						BufferedReader br = new BufferedReader(fr);
						int lineNo;
						
						for (lineNo = 1; lineNo < 10; lineNo++) {
							if (lineNo == 1) {
								M_CONN_STRING = br.readLine();
							} else
								br.readLine();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

					
					frame.setTitle(M_CONN_STRING);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PDI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(-010, 00, 1380, 797);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel pnlTireDate = new JPanel();
		pnlTireDate.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlTireDate.setBounds(0, 335, 502, 423);
		contentPane.add(pnlTireDate);
		pnlTireDate.setLayout(null);

		JLabel label = new JLabel("Tire Size");
		label.setForeground(Color.BLUE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label.setBounds(28, 29, 114, 20);
		pnlTireDate.add(label);

		JLabel label_1 = new JLabel("Lug Type");
		label_1.setForeground(Color.BLUE);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_1.setBounds(28, 73, 114, 20);
		pnlTireDate.add(label_1);

		JLabel label_2 = new JLabel("Configuration");
		label_2.setForeground(Color.BLUE);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_2.setBounds(28, 117, 114, 20);
		pnlTireDate.add(label_2);

		JLabel label_3 = new JLabel("Rim Size");
		label_3.setForeground(Color.BLUE);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_3.setBounds(28, 161, 114, 20);
		pnlTireDate.add(label_3);

		JLabel label_4 = new JLabel("Tire Type");
		label_4.setForeground(Color.BLUE);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_4.setBounds(28, 205, 114, 20);
		pnlTireDate.add(label_4);

		JLabel label_5 = new JLabel("Brand Name");
		label_5.setForeground(Color.BLUE);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_5.setBounds(28, 249, 114, 20);
		pnlTireDate.add(label_5);

		JLabel label_6 = new JLabel("SideWall Msg");
		label_6.setForeground(Color.BLUE);
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_6.setBounds(28, 293, 114, 20);
		pnlTireDate.add(label_6);

		JLabel label_7 = new JLabel("PID");
		label_7.setForeground(Color.BLUE);
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_7.setBounds(28, 337, 114, 20);
		pnlTireDate.add(label_7);

		JLabel label_8 = new JLabel("Quality Grade");
		label_8.setForeground(Color.BLUE);
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_8.setBounds(28, 381, 114, 20);
		pnlTireDate.add(label_8);

		JLabel lblTireSizePdi = new JLabel("---------");
		lblTireSizePdi.setForeground(Color.BLUE);
		lblTireSizePdi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTireSizePdi.setBounds(231, 29, 223, 20);
		pnlTireDate.add(lblTireSizePdi);

		JLabel lblLugTypePdi = new JLabel("---------");
		lblLugTypePdi.setForeground(Color.BLUE);
		lblLugTypePdi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLugTypePdi.setBounds(231, 73, 223, 20);
		pnlTireDate.add(lblLugTypePdi);

		JLabel lblConfigPdi = new JLabel("---------");
		lblConfigPdi.setForeground(Color.BLUE);
		lblConfigPdi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblConfigPdi.setBounds(231, 123, 223, 20);
		pnlTireDate.add(lblConfigPdi);

		JLabel lblRimSizePdi = new JLabel("---------");
		lblRimSizePdi.setForeground(Color.BLUE);
		lblRimSizePdi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRimSizePdi.setBounds(231, 167, 223, 20);
		pnlTireDate.add(lblRimSizePdi);

		JLabel lblTireTypePdi = new JLabel("---------");
		lblTireTypePdi.setForeground(Color.BLUE);
		lblTireTypePdi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTireTypePdi.setBounds(231, 211, 223, 20);
		pnlTireDate.add(lblTireTypePdi);

		JLabel lblBrandNamePdi = new JLabel("---------");
		lblBrandNamePdi.setForeground(Color.BLUE);
		lblBrandNamePdi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBrandNamePdi.setBounds(231, 255, 223, 20);
		pnlTireDate.add(lblBrandNamePdi);

		JLabel lblSWMsgPdi = new JLabel("---------");
		lblSWMsgPdi.setForeground(Color.BLUE);
		lblSWMsgPdi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSWMsgPdi.setBounds(231, 299, 223, 20);
		pnlTireDate.add(lblSWMsgPdi);

		JLabel lblPIDPdi = new JLabel("---------");
		lblPIDPdi.setForeground(Color.BLUE);
		lblPIDPdi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPIDPdi.setBounds(231, 343, 223, 20);
		pnlTireDate.add(lblPIDPdi);

		JLabel lblQGPdi = new JLabel("---------");
		lblQGPdi.setForeground(Color.BLUE);
		lblQGPdi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblQGPdi.setBounds(231, 387, 223, 20);
		pnlTireDate.add(lblQGPdi);

		JLabel lblCap2Pdi = new JLabel("--------------");
		lblCap2Pdi.setForeground(new Color(0, 0, 205));
		lblCap2Pdi.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblCap2Pdi.setBounds(10, 53, 1312, 50);
		contentPane.add(lblCap2Pdi);

		JLabel lblCap1Pdi = new JLabel("-----------------------------------");
		lblCap1Pdi.setForeground(new Color(0, 0, 128));
		lblCap1Pdi.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblCap1Pdi.setBounds(10, 11, 1303, 43);
		contentPane.add(lblCap1Pdi);

		txtBarCodePid = new JTextField();
		txtBarCodePid.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtBarCodePid.setBounds(291, 148, 178, 31);
		contentPane.add(txtBarCodePid);
		txtBarCodePid.setColumns(10);

		scpWo = new JScrollPane();
		scpWo.setBounds(543, 114, 776, 644);
		contentPane.add(scpWo);

		cmbxWOPdi = new JComboBox();
		cmbxWOPdi.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				int intWO;
				String customer;
				String suborderno;
				String invoiceno;
				Date date;
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					// Load work order details to the lables.

					String strwo = String.valueOf(cmbxWOPdi.getSelectedItem());
					if (strwo != "") {// this if is for avoid null value
										// seletion.
						try {
							intWO = Integer.parseInt(strwo);

							WorkOrderDetailBean bean = WorkOrderDetailManager.getRow_WOandRevIsZero(intWO, 0, conn);
							if (bean != null) {
								intWO = bean.getWo();
								customer = bean.getCustomer();
								suborderno = bean.getSuborderno();
								invoiceno = bean.getInvoiceno();
								date = bean.getDate();

								lblCustomerPdi.setText(customer);
								lblInvoicePdi.setText(invoiceno);

								if (date != null) {
									java.util.Date utilDate = new java.util.Date(date.getTime());
									DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
									lblOrderDatePdi.setText(dateFormat.format(utilDate));
									lblWOPdi.setText(strwo);
									CreatePDIWO(conn);
								}
							}

						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, e);
						}
					} else {
						lblCustomerPdi.setText("-------");
						lblInvoicePdi.setText("-------");
						lblOrderDatePdi.setText("-------");
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {

				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					cmbxWOPdi.removeAllItems();

					fillCombo(conn);

					lblWOPdi.setText("----------");
					lblCustomerPdi.setText("----------");
					lblInvoicePdi.setText("----------");
					lblOrderDatePdi.setText("----------");
					// hideComponentRWO();

					// Clear table

					DefaultTableModel dm = (DefaultTableModel) jtblPDI.getModel();
					for (int i = 0; i < dm.getRowCount(); i++) {
						for (int j = 0; j < dm.getColumnCount(); j++) {
							dm.setValueAt(null, i, j);
						}
					}
				} catch (Exception e) {
					// no exception handling
				}
			}
		});
		cmbxWOPdi.setBounds(20, 114, 131, 20);
		contentPane.add(cmbxWOPdi);

		pnlWODetailPdi = new JPanel();
		pnlWODetailPdi.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		pnlWODetailPdi.setBounds(0, 148, 256, 187);
		contentPane.add(pnlWODetailPdi);
		pnlWODetailPdi.setLayout(null);

		JLabel label_9 = new JLabel("Work Order");
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_9.setBounds(33, 33, 101, 19);
		pnlWODetailPdi.add(label_9);

		lblWOPdi = new JLabel("-----------");
		lblWOPdi.setForeground(Color.BLUE);
		lblWOPdi.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblWOPdi.setBounds(144, 33, 98, 19);
		pnlWODetailPdi.add(lblWOPdi);

		JLabel label_11 = new JLabel("Customer");
		label_11.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_11.setBounds(33, 65, 101, 19);
		pnlWODetailPdi.add(label_11);

		lblCustomerPdi = new JLabel("-----------");
		lblCustomerPdi.setForeground(Color.BLUE);
		lblCustomerPdi.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCustomerPdi.setBounds(144, 65, 98, 19);
		pnlWODetailPdi.add(lblCustomerPdi);

		JLabel label_13 = new JLabel("Invoice No");
		label_13.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_13.setBounds(33, 89, 101, 19);
		pnlWODetailPdi.add(label_13);

		lblInvoicePdi = new JLabel("-----------");
		lblInvoicePdi.setForeground(Color.BLUE);
		lblInvoicePdi.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInvoicePdi.setBounds(144, 89, 98, 19);
		pnlWODetailPdi.add(lblInvoicePdi);

		JLabel label_15 = new JLabel("Order Placed Date");
		label_15.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_15.setBounds(33, 119, 146, 19);
		pnlWODetailPdi.add(label_15);

		lblOrderDatePdi = new JLabel("-----------");
		lblOrderDatePdi.setForeground(Color.BLUE);
		lblOrderDatePdi.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblOrderDatePdi.setBounds(141, 136, 101, 19);
		pnlWODetailPdi.add(lblOrderDatePdi);

	}

	private void CreatePDIWO(Connection conn) {
		String WOName = String.valueOf(cmbxWOPdi.getSelectedItem());

		// Add new column to the table
		try {
			int lastu = IndividualWO.getNoOfUpdates(WOName, conn);
			// Check for maximum Revision(r15 is the highest )
			if (lastu < 15) {
				// IndividualWO.addCol(lastu, WOName);
				// IndividualWO.CopyCol(lastu, WOName);

				jtblPDI = new JTable() {
					public boolean isCellEditable(int row, int column) {
						if (flag) {
							return false;
						}
						return true;
					}
				};

				jtblPDI = IndividualWO.createTablePDI(WOName, conn);// get_Data_From_DB_and_addColumnforTireSize

				// Put table in to the scrol pane
				scpWo.setViewportView((jtblPDI));
				jtblPDI.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				// Import tire sizes to 3rd column

				Counts counts = new Counts();
				int intNotEmptyRows = counts.NotEmptyRows(jtblPDI);

				DefaultTableModel dm = (DefaultTableModel) jtblPDI.getModel();
				IndividualWO in = new IndividualWO();
				in.UpdateTireSizes(intNotEmptyRows, dm, 1, jtblPDI, conn);

				// Serching table Model
				originalTableModel = (Vector) ((DefaultTableModel) jtblPDI.getModel()).getDataVector().clone();// Searching-Purpose

			} else {
				JOptionPane.showMessageDialog(null, "You have reached maximum Work Order Reviedisons", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void fillCombo(Connection conn) {// Fill the combo box as check
		// boxes are ticked
		// combo box option checking
		String sql = "SELECT distinct wo FROM fg.workoderdetails where activeStatus = 1 order by wo asc";

		try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);) {

			while (rs.next()) {
				int workorder = rs.getInt("wo");
				cmbxWOPdi.addItem(workorder);
			}
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, e + "  E13");
		}
	}

	private void showComponentPDI() {

		// scpDelrwo.setVisible(true);
		pnlWODetailPdi.setVisible(true);

	}

	private void TextChangeEvent() {

		txtBarCodePid.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					conn.setAutoCommit(false);
					String s = txtBarCodePid.getText();
					int l = s.length();
					if (l == 19) {// Check.for.barcode.
						try {
							String WOName = String.valueOf(cmbxWOPdi.getSelectedItem());
							txtBarCodePid.setEnabled(false);

							int pid = 0;
							String SLet = s.substring(2, 3);

							String strPID = s.substring(2, 8);
							// to correct the 5digit pids(Add 2 instead of 0)
							if ("0".equals(SLet)) {
								strPID = "2" + s.substring(3, 8);// Add.2
							}
							pid = Integer.parseInt(strPID);// Convert.to.int

							String strSN = txtBarCodePid.getText().substring(9, 18);
							int sn = Integer.parseInt(strSN);

							PDITireBean bean = new PDITireBean();
							// find pid in workorder table
							bean = IndividualWO.findPID(WOName, pid, conn);

							if (bean != null) {
								int stk = bean.getPid();
								int wo = bean.getLastUVal();
								int sc = bean.getSqty();
								// If work order reqriment is less than scaned
								// qty
								if (wo > sc) {
									// update the work order
									boolean result = IndividualWO.UpdateWOforScan(WOName, (sc + 1), pid, conn);
									// updatse the availability column in stock
									// table
									StkTireBean bean2 = new StkTireBean();
									bean2.setSn(sn);
									bean2.setAvl(3);
									boolean result2 = StkTbl.updateAvlnPID(bean2, conn);
									if ((result) && (result2)) {
										// Updated the WO table
										conn.commit();
										CreatePDIWO(conn);
									} else {
										conn.rollback();
									}
								} else {
									// Scan qty and work order qty eqeal
									JOptionPane.showMessageDialog(null, "Completed This PID");
									conn.rollback();
								}
							} else {
								// PID is not in work order
								JOptionPane.showMessageDialog(null, "No orders for this PID ");
								conn.rollback();
							}
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex);
						}

					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	private static JTable getNewRenderedTable(final JTable table) {
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int col) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

				Integer balance = (Integer) table.getModel().getValueAt(row, 5);

				if (balance == 1) {
					setBackground(Color.RED);
					setForeground(Color.WHITE);
				} else {
					setBackground(table.getBackground());
					setForeground(table.getForeground());
				}
				return this;
			}
		});
		return table;
	}
}
