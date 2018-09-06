package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.Robot;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import javax.swing.JButton;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.PopupMenuListener;

import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;



import beans.WorkOrderDetailBean;
import beans.CountsOfTiresinWOBean;
import db.DBType;
import db.DBUtil;
import general.ExcelAdapter;
import general.numericTest;
import joints.BasicDatafromPIDManager;
import tables.WorkOrderDetailManager;
import tables.CreateWOTbl;
import tables.IndividualWO;
import tables.PDIManager;
import beans.BasicDatafromPIDBean;
import beans.PDITireBean;
import general.Counts;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class FGReviceWOi extends JFrame {

	private JPanel contentPane;
	private JCheckBox chbLiveOrders;
	private JCheckBox chbCompletedOrders;
	private JTable jtblReviceWO;
	private JComboBox<Integer> cmbxWORwo;
	JLabel lblWORwo;
	private JLabel lblCustomerRwo;
	private JLabel lblInvoiceRwo;
	private JLabel lblOrderDateRwo;
	private JScrollPane scpMainrwo;

	private JTable jtblWorkOrder;
	private JTextField txtPIDnwo;
	private JTextField txtNosnwo;
	private JTextField txtCustomernwo;
	private JTextField txtSubOrdernwo;
	private JTextField txtInvoiceNonwo;
	private JPanel pnlWODetailsnwo;
	private JLabel lblPIDnwo;
	private JLabel lblCount;
	private JLabel lblNosnwo;
	private JButton btnUpdateTireSizenwo;
	private JButton btnDelAllRowsnwo;

	private JScrollPane scpDelrwo;
	boolean flag = false;
	private int newPID;
	private int newNos;
	private String name;
	private JPanel pnlInputnwo;
	private JButton btnCrWOnwo;
	private JTextField txtSearchrwo;

	private JButton btnUpdateWorkOrdernwo;

	private JTable jtblDel;
	private JLabel lblCustomernwo;
	private JLabel lblInvoicenwo;
	private JLabel lblSubnwo;
	private JPanel pnlTireDetailnwo;
	Vector originalTableModel;

	DocumentListener documentListener;

	public static FGReviceWOi frame = new FGReviceWOi();
	private JButton Refresh;
	private JLabel lblWOnwo;
	private JLabel label_6;
	private JLabel label_10;
	private JLabel lblPIDxNwo;
	private JLabel lblNosxNwo;
	private JScrollPane scpMainnwo;
	private JTextField txtWOnwo;
	private JButton btnAddNwRow;
	private JPanel pnlWODetailrwo;
	private JButton btnDelRow;
	private JButton btnUpdaterwo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Set the title with the IP address
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

	public void searchTableContents(String searchString) {
		// originalTableModel = (Vector) ((DefaultTableModel)
		// jtblReviceWO.getModel()).getDataVector().clone();
		DefaultTableModel currtableModel = (DefaultTableModel) jtblReviceWO.getModel();
		// To empty the table before search
		currtableModel.setRowCount(0);
		// To search for contents from original table content
		for (Object rows : originalTableModel) {
			Vector rowVector = (Vector) rows;
			for (Object column : rowVector) {
				if (column.toString().contains(searchString)) {
					// content found so adding to table
					currtableModel.addRow(rowVector);
					break;
				}
			}
		}
	}

	private void Initialize() {
		
	}

	public FGReviceWOi() {

		// add document listener to jtextfield to search contents as soon as
		// something typed on it
		// addDocumentListener();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(00, 00, 1380, 779);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1364, 746);
		contentPane.add(tabbedPane);

		JPanel panel_4 = new JPanel();
		panel_4.setForeground(new Color(255, 0, 0));
		panel_4.setBackground(new Color(255, 240, 245));
		tabbedPane.addTab("Revice  WO      ", null, panel_4, null);
		panel_4.setLayout(null);

		scpMainrwo = new JScrollPane();
		scpMainrwo.setBounds(237, 0, 974, 716);
		panel_4.add(scpMainrwo);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 255, 204));
		panel.setBounds(10, 11, 217, 269);
		panel_4.add(panel);
		panel.setLayout(null);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		chbLiveOrders = new JCheckBox("Live Orders");
		chbLiveOrders.setSelected(true);
		chbLiveOrders.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chbLiveOrders.setBounds(6, 7, 145, 23);
		panel.add(chbLiveOrders);

		chbCompletedOrders = new JCheckBox("Completed Orders");
		chbCompletedOrders.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chbCompletedOrders.setBounds(6, 33, 145, 23);
		panel.add(chbCompletedOrders);

		cmbxWORwo = new JComboBox();
		cmbxWORwo.addPopupMenuListener(new PopupMenuListener() {
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

					String strwo = String.valueOf(cmbxWORwo.getSelectedItem());
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

								lblCustomerRwo.setText(customer);
								lblInvoiceRwo.setText(invoiceno);

								if (date != null) {
									java.util.Date utilDate = new java.util.Date(date.getTime());
									DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
									lblOrderDateRwo.setText(dateFormat.format(utilDate));
									lblWORwo.setText(strwo);
									// Show Table
									reviceWorkOrder(conn);
								}
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, e);
						}
					} else {
						lblCustomerRwo.setText("-------");
						lblInvoiceRwo.setText("-------");
						lblOrderDateRwo.setText("-------");
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					cmbxWORwo.removeAllItems();
					fillCombo(conn);

					lblWORwo.setText("----------");
					lblCustomerRwo.setText("----------");
					lblInvoiceRwo.setText("----------");
					lblOrderDateRwo.setText("----------");
					hideComponentRWO();

					// Clear table

					DefaultTableModel dm = (DefaultTableModel) jtblReviceWO.getModel();
					for (int i = 0; i < dm.getRowCount(); i++) {
						for (int j = 0; j < dm.getColumnCount(); j++) {
							dm.setValueAt(null, i, j);
						}
					}
				} catch (Exception e) {

				}
			}
		});
		cmbxWORwo.setBounds(63, 102, 90, 20);
		panel.add(cmbxWORwo);

		JLabel label = new JLabel("WorkOrder");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label.setBounds(78, 76, 60, 14);
		panel.add(label);

		btnAddNwRow = new JButton("Add New PID");
		btnAddNwRow.setBackground(new Color(30, 144, 255));
		btnAddNwRow.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAddNwRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean dup = false;// Check-duplicate-pid
				boolean PIDOK = false;

				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL)) {

					Counts countts = new Counts();
					int intNotEmptyRows = countts.NotEmptyRows(jtblReviceWO);// No

					String PIDNw = JOptionPane.showInputDialog("PID");

					if ((PIDNw != null) && (numericTest.isInteger(PIDNw))) {

						for (int i = 0; i < intNotEmptyRows; i++) {
							String pidTbl = jtblReviceWO.getModel().getValueAt(i, 0).toString();// Get
							if (pidTbl.equals(PIDNw)) {
								dup = true;
							}
						}
						// Check for the PID avalable in table
						if (dup == false) {
							DefaultTableModel model = (DefaultTableModel) jtblReviceWO.getModel();
							model.addRow(new Object[] { PIDNw, null, "0" });
							PIDOK = true;
						} else {
							JOptionPane.showMessageDialog(null, "Available");
						}

					} else {
						JOptionPane.showMessageDialog(null, "Error Input");
					}
					// No of tires
					if (PIDOK == true) {
						String NosNw = JOptionPane.showInputDialog("No of TIREs");

						if ((NosNw != null) && (numericTest.isInteger(NosNw))) {

							String WOName = String.valueOf(cmbxWORwo.getSelectedItem());
							int lastu = IndividualWO.getNoOfUpdates(WOName, conn);
							// Add No of tires
							DefaultTableModel dm2 = (DefaultTableModel) jtblReviceWO.getModel();
							dm2.setValueAt(NosNw, intNotEmptyRows, lastu + 4);
							// Get Tire Sizes
							IndividualWO in = new IndividualWO();
							in.UpdateTireSizes(intNotEmptyRows + 1, dm2, 1, jtblReviceWO, conn);

						} else {
							DefaultTableModel dm2 = (DefaultTableModel) jtblReviceWO.getModel();

							dm2.removeRow(intNotEmptyRows);
							JOptionPane.showMessageDialog(null, "Error Input");
						}
					}
				} catch (Exception e1) {

					JOptionPane.showMessageDialog(null, e1);
				}
				originalTableModel = (Vector) ((DefaultTableModel) jtblReviceWO.getModel()).getDataVector().clone();// Again_get_theTabletoVector
			}
		});

		btnAddNwRow.setBounds(32, 197, 171, 23);
		panel.add(btnAddNwRow);

		btnDelRow = new JButton("Delete Row");
		btnDelRow.setBackground(new Color(255, 69, 0));
		btnDelRow.setBounds(32, 235, 171, 23);
		panel.add(btnDelRow);

		txtSearchrwo = new JTextField();
		txtSearchrwo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSearchrwo.setBounds(48, 151, 139, 20);
		panel.add(txtSearchrwo);
		txtSearchrwo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				searchTableContents(txtSearchrwo.getText());
			}
		});
		txtSearchrwo.setColumns(10);

		JLabel lblSearch = new JLabel("Search");
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSearch.setBounds(82, 133, 70, 14);
		panel.add(lblSearch);
		btnDelRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL)) {
					dellRow(conn);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
				}

			}
		});

		pnlWODetailrwo = new JPanel();
		pnlWODetailrwo.setBackground(new Color(255, 250, 205));
		pnlWODetailrwo.setBounds(10, 302, 217, 234);
		panel_4.add(pnlWODetailrwo);
		pnlWODetailrwo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlWODetailrwo.setLayout(null);

		JLabel label_3 = new JLabel("Work Order");
		label_3.setBounds(2, 32, 101, 19);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlWODetailrwo.add(label_3);

		lblWORwo = new JLabel("-----------");
		lblWORwo.setForeground(Color.BLUE);
		lblWORwo.setBounds(113, 32, 98, 19);
		lblWORwo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlWODetailrwo.add(lblWORwo);

		JLabel label_5 = new JLabel("Customer");
		label_5.setBounds(2, 56, 101, 19);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlWODetailrwo.add(label_5);

		lblCustomerRwo = new JLabel("-----------");
		lblCustomerRwo.setForeground(Color.BLUE);
		lblCustomerRwo.setBounds(113, 56, 98, 19);
		lblCustomerRwo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlWODetailrwo.add(lblCustomerRwo);

		JLabel label_7 = new JLabel("Invoice No");
		label_7.setBounds(2, 80, 101, 19);
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlWODetailrwo.add(label_7);

		lblInvoiceRwo = new JLabel("-----------");
		lblInvoiceRwo.setForeground(Color.BLUE);
		lblInvoiceRwo.setBounds(113, 80, 98, 19);
		lblInvoiceRwo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlWODetailrwo.add(lblInvoiceRwo);

		JLabel label_9 = new JLabel("Order Placed Date");
		label_9.setBounds(2, 104, 146, 19);
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlWODetailrwo.add(label_9);

		lblOrderDateRwo = new JLabel("-----------");
		lblOrderDateRwo.setForeground(Color.BLUE);
		lblOrderDateRwo.setBounds(110, 121, 101, 19);
		lblOrderDateRwo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlWODetailrwo.add(lblOrderDateRwo);

		scpDelrwo = new JScrollPane();
		scpDelrwo.setBounds(1221, 11, 128, 269);
		panel_4.add(scpDelrwo);
		scpDelrwo.setVisible(false);

		jtblDel = new JTable();
		jtblDel.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "PID" }));
		scpDelrwo.setViewportView(jtblDel);

		btnUpdaterwo = new JButton("Update");
		btnUpdaterwo.setBounds(1221, 320, 128, 73);
		panel_4.add(btnUpdaterwo);
		btnUpdaterwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL)) {

					conn.setAutoCommit(false);
					boolean allOK = true;

					Counts countts = new Counts();
					int intNotEmptyRows = countts.NotEmptyRows(jtblReviceWO);// No

					String WOName = String.valueOf(cmbxWORwo.getSelectedItem());
					int lastu = IndividualWO.getNoOfUpdates(WOName, conn);
					String lastCol = "r" + lastu;

					// Delete data in DB first
					int intNotEmptyRowsDel = jtblDel.getRowCount();
					if (intNotEmptyRowsDel > 0) {

						for (int i = 0; i < intNotEmptyRowsDel + 2; i++) {
							intNotEmptyRowsDel = jtblDel.getRowCount();
							if (intNotEmptyRowsDel > 0) {

								int piddel = Integer.parseInt(jtblDel.getModel().getValueAt(0, 0).toString());// Get_PIDDelete
								boolean results = IndividualWO.deleteFrmRWO(WOName, piddel, conn);

								if (!results) {
									allOK = false;
								} else {
									DefaultTableModel model = (DefaultTableModel) jtblDel.getModel();
									model.removeRow(0);
								}
							}
						}
					}
					// Add new Column to the WO
					try {
						IndividualWO.addCol(lastu, WOName, conn);
						IndividualWO.CopyCol(lastu, WOName, conn);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2);
						allOK = false;
					}
					// Check the value in table
					for (int i = 0; i < intNotEmptyRows; i++) {
						String strNOsTbl = jtblReviceWO.getModel().getValueAt(i, (lastu + 4)).toString();// Get_NO_OF_tires-in-addedRow
						int NosTbl = Integer.parseInt(strNOsTbl);
						int pid = Integer.parseInt(jtblReviceWO.getModel().getValueAt(i, 0).toString());// Get_PIDof-LastUpdate
						// // Check the value in database
						PDITireBean bean;
						bean = IndividualWO.findPID(WOName, pid, conn);
						if (bean != null) {// PID_IS_IN_DATA_BASE_UPDATE
							int NosDB = getNosDB(bean, lastu);// GET_NOS_FROM_DB_FOR_PID_OF_THIS_i

							if (NosTbl != NosDB) {
								// Update the WO table with new value
								boolean result = IndividualWO.UpdateReviceWO(WOName, lastu + 1, NosTbl, pid, conn);
								if (!result) {
									allOK = false;
								}
							}
						} else {// NEW_PID

							boolean result = IndividualWO.insertToRWO(WOName, lastu + 1, NosTbl, pid, conn);
							if (!result) {
								allOK = false;
							}
						}
					}
					if (allOK) {
						conn.commit();
						JOptionPane.showMessageDialog(null, "Updated");
						// Check DataBase Values and uploaded values

					} else {
						conn.rollback();
						// Delete added New column
						try {
							IndividualWO.delCol(lastu + 1, WOName, conn);
						} catch (Exception se) {

						}
					}
					conn.close();
					// Delete jtblDel data first of all

					DefaultTableModel modeldel = (DefaultTableModel) jtblDel.getModel();
					int rcdel = modeldel.getRowCount();
					// If jtblDel has rows delete them
					if (rcdel > 0) {
						flushjtblDell();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				} finally {
				}
			}
		});

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.GREEN);
		tabbedPane.addTab("New WO", null, panel_3, null);
		panel_3.setLayout(null);

		scpMainnwo = new JScrollPane();
		scpMainnwo.setBounds(423, 11, 691, 679);
		panel_3.add(scpMainnwo);
		scpMainnwo.setVisible(false);

		// Table for new workorder
		jtblWorkOrder = new JTable();

		scpMainnwo.setViewportView(jtblWorkOrder);
		jtblWorkOrder.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					Counts counts = new Counts();
					int total = counts.GetTtl(jtblWorkOrder, 1);// 1-is-qty-in-jtblWorkOrder-tbl

					int notEmptyRowNos = counts.NotEmptyRows(jtblWorkOrder);

					lblPIDnwo.setText(Integer.toString(notEmptyRowNos));
					lblNosnwo.setText(Integer.toString(total));

					int total1 = Integer.parseInt(txtNosnwo.getText());
					int pid1 = Integer.parseInt(txtPIDnwo.getText());

					int total2 = Integer.parseInt(lblNosnwo.getText());
					int pid2 = Integer.parseInt(lblPIDnwo.getText());

					btnDelAllRowsnwo.setVisible(true);

					if ((total1 == total2) && (pid1 == pid2)) {
						btnUpdateTireSizenwo.setVisible(true);
						btnUpdateTireSizenwo.setVisible(true);

						pnlWODetailsnwo.setVisible(true);
						pnlWODetailsnwo.setVisible(true);
						btnDelAllRowsnwo.setVisible(true);
						btnUpdateWorkOrdernwo.setVisible(true);

					} else {
						btnUpdateTireSizenwo.setVisible(false);
						btnUpdateTireSizenwo.setVisible(false);
					}
					jtblWorkOrder.setEnabled(false);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}

			}
		});
		jtblWorkOrder.setBackground(new Color(175, 238, 238));
		jtblWorkOrder.setModel(new DefaultTableModel(new Object[][] { { null, null, null }, },
				new String[] { "PID", "Nos", "Tire Size" }) {
			Class[] columnTypes = new Class[] { Integer.class, Integer.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		jtblWorkOrder.getTableHeader().setReorderingAllowed(false); // ColumnOrderChanging

		jtblWorkOrder.getColumnModel().getColumn(0).setResizable(false);
		jtblWorkOrder.getColumnModel().getColumn(0).setPreferredWidth(100);
		jtblWorkOrder.getColumnModel().getColumn(1).setResizable(false);
		jtblWorkOrder.getColumnModel().getColumn(1).setPreferredWidth(50);
		jtblWorkOrder.getColumnModel().getColumn(2).setResizable(false);
		jtblWorkOrder.getColumnModel().getColumn(2).setPreferredWidth(523);

		DefaultTableModel model = (DefaultTableModel) jtblWorkOrder.getModel();
		ExcelAdapter myAd = new ExcelAdapter(jtblWorkOrder);// Copy and past

		jtblWorkOrder.setRowSelectionAllowed(false);// Entire row selection
		// false now

		jtblWorkOrder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// Auto resize

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(10, 11, 403, 409);
		panel_5.setBackground(new Color(204, 255, 204));
		panel_5.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_3.add(panel_5);
		panel_5.setLayout(null);

		JLabel lblWorkOrderNo = new JLabel("Work Order No");
		lblWorkOrderNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWorkOrderNo.setBounds(10, 40, 114, 14);
		panel_5.add(lblWorkOrderNo);

		JLabel lblNewLabel = new JLabel("Customer");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 86, 114, 14);
		panel_5.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Invoice No");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 129, 114, 14);
		panel_5.add(lblNewLabel_1);

		pnlInputnwo = new JPanel();
		pnlInputnwo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (scpMainnwo.isShowing()) {
					try {
						Robot bot = new Robot();
						bot.mouseMove(420, 420);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
		pnlInputnwo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlInputnwo.setBounds(10, 188, 264, 114);
		panel_5.add(pnlInputnwo);
		pnlInputnwo.setLayout(null);
		pnlInputnwo.setVisible(false);

		JLabel lblNewLabel_2 = new JLabel("PIDs");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(25, 23, 46, 14);
		pnlInputnwo.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Tires");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(25, 65, 46, 14);
		pnlInputnwo.add(lblNewLabel_3);

		txtPIDnwo = new JTextField();
		txtPIDnwo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				int key = evt.getKeyCode();
				if ((key >= evt.VK_0 && key <= evt.VK_9) || (key >= evt.VK_NUMPAD0 && key <= evt.VK_NUMPAD9)
						|| key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE) {
					txtPIDnwo.setEditable(true);
				} else {
					txtPIDnwo.setEditable(false);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				ShowCreateWOButton();
			}
		});
		txtPIDnwo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtPIDnwo.setBounds(127, 20, 86, 20);
		pnlInputnwo.add(txtPIDnwo);
		txtPIDnwo.setColumns(10);

		txtNosnwo = new JTextField();
		txtNosnwo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				int key = evt.getKeyCode();
				if ((key >= evt.VK_0 && key <= evt.VK_9) || (key >= evt.VK_NUMPAD0 && key <= evt.VK_NUMPAD9)
						|| key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE) {
					txtNosnwo.setEditable(true);
				} else {
					txtNosnwo.setEditable(false);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				ShowCreateWOButton();
			}
		});
		txtNosnwo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtNosnwo.setBounds(127, 62, 86, 20);
		pnlInputnwo.add(txtNosnwo);
		txtNosnwo.setColumns(10);

		txtCustomernwo = new JTextField();
		txtCustomernwo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					ShowPnlInputNwo(conn);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});

		txtCustomernwo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtCustomernwo.setBounds(134, 86, 114, 20);
		panel_5.add(txtCustomernwo);
		txtCustomernwo.setColumns(10);

		txtSubOrdernwo = new JTextField();
		txtSubOrdernwo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSubOrdernwo.setBounds(260, 84, 140, 20);
		panel_5.add(txtSubOrdernwo);
		txtSubOrdernwo.setColumns(10);

		txtInvoiceNonwo = new JTextField();
		txtInvoiceNonwo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtInvoiceNonwo.setBounds(134, 129, 114, 20);
		panel_5.add(txtInvoiceNonwo);
		txtInvoiceNonwo.setColumns(10);

		btnCrWOnwo = new JButton("Creat Work Order");
		btnCrWOnwo.setVisible(false);
		btnCrWOnwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					String WOName = txtWOnwo.getText();
					DatabaseMetaData dbm = (DatabaseMetaData) conn.getMetaData();
					// check if "employee" table is there
					ResultSet tables = dbm.getTables(null, null, WOName, null);
					if (tables.next()) {
						JOptionPane.showMessageDialog(null, "Work Order Already Available");
						scpMainnwo.setVisible(false);
					} else {
						scpMainnwo.setVisible(true);

						btnCrWOnwo.setVisible(false);
						txtNosnwo.setEditable(false);
						txtPIDnwo.setEditable(false);
						txtCustomernwo.setEditable(false);
						txtWOnwo.setEditable(false);
						txtSubOrdernwo.setEditable(false);
						txtInvoiceNonwo.setEditable(false);
						pnlTireDetailnwo.setVisible(true);

						lblWOnwo.setText(txtWOnwo.getText());
						lblCustomernwo.setText(txtCustomernwo.getText());
						lblSubnwo.setText(txtSubOrdernwo.getText());
						lblInvoicenwo.setText(txtInvoiceNonwo.getText());
						lblPIDxNwo.setText(txtPIDnwo.getText());
						lblNosxNwo.setText(txtNosnwo.getText());
						jtblWorkOrder.setEnabled(true);
						// pnlInputnwo.setVisible(false);
					}
				} catch (SQLException e) {

					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnCrWOnwo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCrWOnwo.setToolTipText("");
		btnCrWOnwo.setBounds(60, 313, 264, 85);
		panel_5.add(btnCrWOnwo);

		txtWOnwo = new JTextField();
		txtWOnwo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (scpMainnwo.isShowing()) {
					try {
						Robot bot = new Robot();
						bot.mouseMove(420, 420);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		txtWOnwo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					ShowPnlInputNwo(conn);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}

			@Override
			public void keyPressed(KeyEvent evt) {
				int key = evt.getKeyCode();
				if ((key >= evt.VK_0 && key <= evt.VK_9) || (key >= evt.VK_NUMPAD0 && key <= evt.VK_NUMPAD9)
						|| key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE) {
					txtWOnwo.setEditable(true);
				} else {
					txtWOnwo.setEditable(false);
				}
			}
		});
		txtWOnwo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtWOnwo.setColumns(10);
		txtWOnwo.setBounds(134, 39, 114, 20);
		panel_5.add(txtWOnwo);

		pnlWODetailsnwo = new JPanel();
		pnlWODetailsnwo.setForeground(new Color(0, 0, 255));
		pnlWODetailsnwo.setBackground(new Color(0, 250, 154));
		pnlWODetailsnwo.setVisible(false);
		pnlWODetailsnwo.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlWODetailsnwo.setBounds(1124, 11, 225, 209);
		panel_3.add(pnlWODetailsnwo);
		pnlWODetailsnwo.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("PIDs");
		lblNewLabel_4.setForeground(new Color(0, 0, 255));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(10, 70, 46, 14);
		pnlWODetailsnwo.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Tires");
		lblNewLabel_5.setForeground(new Color(0, 0, 255));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(10, 110, 46, 14);
		pnlWODetailsnwo.add(lblNewLabel_5);

		lblPIDnwo = new JLabel("------");
		lblPIDnwo.setForeground(new Color(0, 0, 255));
		lblPIDnwo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPIDnwo.setBounds(116, 70, 46, 14);
		pnlWODetailsnwo.add(lblPIDnwo);

		lblNosnwo = new JLabel("-----");
		lblNosnwo.setForeground(new Color(0, 0, 255));
		lblNosnwo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNosnwo.setBounds(116, 110, 46, 14);
		pnlWODetailsnwo.add(lblNosnwo);

		lblCount = new JLabel("Count");
		lblCount.setForeground(new Color(0, 0, 255));
		lblCount.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCount.setBounds(10, 25, 68, 14);
		pnlWODetailsnwo.add(lblCount);

		Refresh = new JButton("Refresh");
		Refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshNewWOTab();
			}
		});
		Refresh.setBackground(Color.YELLOW);
		Refresh.setForeground(Color.BLUE);
		Refresh.setFont(new Font("Tahoma", Font.BOLD, 16));
		Refresh.setBounds(1162, 615, 151, 64);
		panel_3.add(Refresh);

		pnlTireDetailnwo = new JPanel();
		pnlTireDetailnwo.setVisible(false);
		pnlTireDetailnwo.setLayout(null);
		pnlTireDetailnwo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlTireDetailnwo.setBackground(new Color(255, 250, 205));
		pnlTireDetailnwo.setBounds(20, 431, 288, 248);
		panel_3.add(pnlTireDetailnwo);

		JLabel label_4 = new JLabel("Work Order");
		label_4.setForeground(new Color(0, 0, 205));
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_4.setBounds(2, 26, 101, 19);
		pnlTireDetailnwo.add(label_4);

		lblWOnwo = new JLabel("-----------");
		lblWOnwo.setForeground(new Color(0, 0, 205));
		lblWOnwo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblWOnwo.setBounds(138, 26, 110, 19);
		pnlTireDetailnwo.add(lblWOnwo);

		JLabel label_8 = new JLabel("Customer");
		label_8.setForeground(new Color(0, 0, 205));
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_8.setBounds(2, 56, 101, 19);
		pnlTireDetailnwo.add(label_8);

		lblCustomernwo = new JLabel("-----------");
		lblCustomernwo.setForeground(new Color(0, 0, 205));
		lblCustomernwo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCustomernwo.setBounds(138, 56, 110, 19);
		pnlTireDetailnwo.add(lblCustomernwo);

		JLabel label_11 = new JLabel("Invoice No");
		label_11.setForeground(new Color(0, 0, 205));
		label_11.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_11.setBounds(2, 124, 101, 19);
		pnlTireDetailnwo.add(label_11);

		lblInvoicenwo = new JLabel("-----------");
		lblInvoicenwo.setForeground(new Color(0, 0, 205));
		lblInvoicenwo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInvoicenwo.setBounds(138, 124, 110, 19);
		pnlTireDetailnwo.add(lblInvoicenwo);

		lblSubnwo = new JLabel("-----------");
		lblSubnwo.setForeground(new Color(0, 0, 205));
		lblSubnwo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSubnwo.setBounds(138, 86, 110, 19);
		pnlTireDetailnwo.add(lblSubnwo);

		label_6 = new JLabel("PIDs");
		label_6.setForeground(new Color(0, 0, 205));
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_6.setBounds(2, 187, 46, 14);
		pnlTireDetailnwo.add(label_6);

		label_10 = new JLabel("Tires");
		label_10.setForeground(new Color(0, 0, 205));
		label_10.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_10.setBounds(2, 223, 46, 14);
		pnlTireDetailnwo.add(label_10);

		lblPIDxNwo = new JLabel("-----------");
		lblPIDxNwo.setForeground(new Color(0, 0, 205));
		lblPIDxNwo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPIDxNwo.setBounds(138, 185, 110, 19);
		pnlTireDetailnwo.add(lblPIDxNwo);

		lblNosxNwo = new JLabel("-----------");
		lblNosxNwo.setForeground(new Color(0, 0, 205));
		lblNosxNwo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNosxNwo.setBounds(138, 218, 110, 19);
		pnlTireDetailnwo.add(lblNosxNwo);

		btnUpdateWorkOrdernwo = new JButton("Update Work Order");
		btnUpdateWorkOrdernwo.setBounds(1143, 404, 193, 74);
		panel_3.add(btnUpdateWorkOrdernwo);
		btnUpdateWorkOrdernwo.setForeground(Color.YELLOW);
		btnUpdateWorkOrdernwo.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnUpdateWorkOrdernwo.setVisible(false);
		btnUpdateWorkOrdernwo.setBackground(Color.BLUE);

		btnDelAllRowsnwo = new JButton("Delete");
		btnDelAllRowsnwo.setBounds(1159, 341, 160, 52);
		panel_3.add(btnDelAllRowsnwo);
		btnDelAllRowsnwo.setVisible(false);
		btnDelAllRowsnwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel dm = (DefaultTableModel) jtblWorkOrder.getModel();

				for (int i = 0; i < dm.getRowCount(); i++) {
					for (int j = 0; j < dm.getColumnCount(); j++) {
						dm.setValueAt(null, i, j);
					}
				}
				// Select first cell
				jtblWorkOrder.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				jtblWorkOrder.setColumnSelectionAllowed(true);
				jtblWorkOrder.setRowSelectionAllowed(true);

				int row = 0;
				int col = 0;
				boolean toggle = false;
				boolean extend = false;
				jtblWorkOrder.changeSelection(row, col, toggle, extend);

				//
				updateCounts();// Update lables of no of tires and PID
				txtNosnwo.setEditable(false);
				txtPIDnwo.setEditable(false);
				btnUpdateTireSizenwo.setVisible(false);
				btnUpdateWorkOrdernwo.setVisible(false);

				jtblWorkOrder.setEnabled(true);

			}
		});
		btnDelAllRowsnwo.setForeground(new Color(0, 0, 0));
		btnDelAllRowsnwo.setBackground(new Color(255, 0, 0));
		btnDelAllRowsnwo.setFont(new Font("Tahoma", Font.PLAIN, 16));

		btnUpdateTireSizenwo = new JButton("Update Tire Sizes");
		btnUpdateTireSizenwo.setBounds(1159, 282, 160, 48);
		panel_3.add(btnUpdateTireSizenwo);
		btnUpdateTireSizenwo.setVisible(false);
		btnUpdateTireSizenwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					Counts counts = new Counts();
					int intNotEmptyRows = counts.NotEmptyRows(jtblWorkOrder);
					DefaultTableModel dm = (DefaultTableModel) jtblWorkOrder.getModel();
					IndividualWO in = new IndividualWO();
					in.UpdateTireSizes(intNotEmptyRows, dm, 2, jtblWorkOrder, conn);
					// tblWorkOder.getColumnModel().moveColumn(2, 1);

					// Change the font styles
					Counts counts2 = new Counts();
					int x = counts2.NotEmptyRows(jtblWorkOrder);

					for (int r = 0; r < x; r++) {// Tire sizes
						jtblWorkOrder.setValueAt("<html><i>" + jtblWorkOrder.getValueAt(r, 2) + "</i></html>", r, 2);
					}

					unhideUpdateWObtn();

				} catch (Exception ev) {
					JOptionPane.showMessageDialog(null, ev + "  E02");
				}
			}
		});
		btnUpdateTireSizenwo.setForeground(new Color(0, 0, 0));
		btnUpdateTireSizenwo.setBackground(new Color(0, 102, 51));
		btnUpdateTireSizenwo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnUpdateWorkOrdernwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean blnOK = true;
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {

					String WOName = lblWOnwo.getText();
					int lastu = 0;
					int count = 0;// This-counts-the-inserted-no-of-rows
					int sum = 0;
					WorkOrderDetailBean bean2 = new WorkOrderDetailBean();
					try {
						lastu = IndividualWO.getNoOfUpdates(WOName, conn);
						conn.setAutoCommit(false);
						panel_3.setBackground(Color.red);

						bean2.setWo(Integer.parseInt(lblWOnwo.getText()));
						bean2.setRevision(0);
						bean2.setCustomer(lblCustomernwo.getText());
						bean2.setSuborderno(lblSubnwo.getText());
						bean2.setInvoiceno(lblInvoicenwo.getText());

						if (JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

							boolean b = CreateWOTbl.CreateWO(bean2.getWo(), conn);// Table_Created
							if (b == true) {
								// Upload data

								Counts counts = new Counts();
								int intNotEmptyRows = counts.NotEmptyRows(jtblWorkOrder);// No

								for (int i = 0; i < intNotEmptyRows; i++) {
									int pid = Integer.parseInt((jtblWorkOrder.getModel().getValueAt(i, 0).toString()));// Get-the-pid

									int r0 = Integer.parseInt((jtblWorkOrder.getModel().getValueAt(i, 1).toString()));// Set-the-r0

									PDITireBean bean = new PDITireBean();
									bean.setPid(pid);// Set-the-pid
									bean.setR0(r0);

									boolean result = PDIManager.insert(bean, conn, WOName);

									if (result) {
										System.out.println("Done");
										// Get the uploaded values.
										count++;// PID-nos
										sum = sum + r0;// Sum
									} else {
										System.out.println("Not done");
										blnOK = false;
									}
								} // For_LOOP

							} else { // b==TRUE-create-workorder-table
								blnOK = false;
								JOptionPane.showMessageDialog(null, "Tablen not created");
							} // b==TRUE-create-workorder-table
								// Insert-Data-In-To-WorkOderDetailManager

						} else {// ConfirmDialog
							JOptionPane.showMessageDialog(null, "Issue!!!");
							blnOK = false;
						} // ConfirmDialog
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2 + "  E02");
						blnOK = false;
					}
					// execute the quary
					try {
						boolean blnDroptbl = true;
						if (blnOK) {
							conn.commit();
							panel_3.setBackground(Color.GREEN);
							// CrossCheck with DB values
							// Confirm database data Vs. table data
							int noOfTires = 0;
							int noOfPIDs = 0;
							int[] x = new int[2];
							Counts counts = new Counts();
							x = counts.getWOStatistics(WOName, lastu, conn);// Get-statistics-from-DB
							noOfPIDs = x[0];
							noOfTires = x[1];
							// This is the final execution
							if ((sum == noOfTires) && (count == noOfPIDs)
									&& (sum == Integer.parseInt(lblNosnwo.getText()))
									&& (count == Integer.parseInt(lblPIDnwo.getText()))) {

								// Update WorkOrderManager Table

								bean2.setNos(sum);
								bean2.setPids(count);
								boolean result2 = WorkOrderDetailManager.insert(bean2, conn);

								if (result2) {
									// EveryThing is OK!!!!!!!
									conn.commit();

									refreshNewWOTab();
									panel_3.setBackground(Color.GREEN);
									// btn
								} else {
									{// Insert-Data-In-To-WorkOderDetailManager
										blnDroptbl = false;
									}
								}
								JOptionPane.showMessageDialog(null, "Done");
							} else {// Chack-DB-Table-values
								blnDroptbl = false;
							} // Chack-DB-Table-values
						} else {// blnOk-NotOK-False
							blnDroptbl = false;
						} // blnOk

						if (!blnDroptbl) {
							conn.rollback();
							try (Connection conn2 = DBUtil.getConnection(DBType.POSTGRESQL);) {

								Statement statement = conn2.createStatement();
								int result = statement.executeUpdate("DROP TABLE fg." + WOName + ";");
								JOptionPane.showMessageDialog(null, "Something Wrong");
							} catch (Exception e3) {
								JOptionPane.showMessageDialog(null, e3);
							}
						}

					} catch (Exception e3) {
						JOptionPane.showMessageDialog(null, e3);
					}
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex);

				}
			}
		});

		// Add 10000 rows to table
		for (int i = 0; i < 10000; i++) {
			model.addRow(new Object[] { null, null });
		}
		Initialize();
		hideComponentRWO();
	}// END_OF_CLASS_CONSTRUCTOR

	private void reviceWorkOrder(Connection conn) {
		String WOName = String.valueOf(cmbxWORwo.getSelectedItem());

		// Add new column to the table
		try {
			int lastu = IndividualWO.getNoOfUpdates(WOName, conn);
			// Check for maximum Revision(r15 is the highest )
			if (lastu < 15) {
				// IndividualWO.addCol(lastu, WOName);
				// IndividualWO.CopyCol(lastu, WOName);

				jtblReviceWO = new JTable() {
					public boolean isCellEditable(int row, int column) {
						if (flag) {
							return false;
						}
						return true;
					}
				};

				jtblReviceWO = IndividualWO.createTableWithNewRevision(WOName, conn);// get_Data_From_DB_and_addColumnforTireSize

				// Put table in to the scrol pane
				scpMainrwo.setViewportView(jtblReviceWO);
				jtblReviceWO.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				// Import tire sizes to 3rd column

				Counts counts = new Counts();
				int intNotEmptyRows = counts.NotEmptyRows(jtblReviceWO);

				DefaultTableModel dm = (DefaultTableModel) jtblReviceWO.getModel();
				IndividualWO in = new IndividualWO();
				in.UpdateTireSizes(intNotEmptyRows, dm, 1, jtblReviceWO, conn);

				// Validate the Data-DB,WorkOrderManager tbl, WorkOrder
				// tbl,jtable;
				// Get data from jtbl
				int noOfTiresNewCol = counts.GetTtl(jtblReviceWO, lastu + 4);
				int noOfTiresOldCol = counts.GetTtl(jtblReviceWO, lastu + 3);
				int pidsTbl = counts.NotEmptyRows(jtblReviceWO);

				// Get data from WorkOrderTable
				int noOfTiresWOTbl = 0;
				int noOfPIDsWOTbl = 0;
				int[] x = new int[2];
				Counts countx = new Counts();
				x = countx.getWOStatistics(WOName, lastu, conn);// Get-statistics-from-DB-LastUpdate-is-not-addedColumn.
				noOfPIDsWOTbl = x[0];
				noOfTiresWOTbl = x[1];

				// Get data from WorkOrdeManager Table
				int intWO = Integer.parseInt(WOName);

				WorkOrderDetailBean bean = WorkOrderDetailManager.getRow_WOandRevIsZero(intWO, lastu, conn);
				int noofTiresWOMgr = 0;
				int noofPidsWOMgr = 0;
				if (bean != null) {
					noofTiresWOMgr = bean.getNos();
					noofPidsWOMgr = bean.getPids();
				}

				if ((noOfTiresNewCol == noOfTiresOldCol) && (noOfTiresNewCol == noofTiresWOMgr)
						&& (noOfTiresNewCol == noOfTiresWOTbl) && (pidsTbl == noOfPIDsWOTbl)
						&& (pidsTbl == noofPidsWOMgr)) {
					showComponentRWO();

				} else {

					jtblReviceWO.setVisible(false);
					DefaultTableModel dm2 = (DefaultTableModel) jtblReviceWO.getModel();

					for (int i = 0; i < dm2.getRowCount(); i++) {
						for (int j = 0; j < dm2.getColumnCount(); j++) {
							dm2.setValueAt(null, i, j);
						}
					}
					jtblReviceWO.setVisible(false);
					JOptionPane.showMessageDialog(null, "Tally Issue");
				}

				// Serching table Model
				originalTableModel = (Vector) ((DefaultTableModel) jtblReviceWO.getModel()).getDataVector().clone();// Searching-Purpose

			} else {
				JOptionPane.showMessageDialog(null, "You have reached maximum Work Order Reviedisons", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void hideComponentRWO() {
		btnAddNwRow.setVisible(false);
		btnDelRow.setVisible(false);
		btnUpdaterwo.setVisible(false);

		scpDelrwo.setVisible(false);
		pnlWODetailrwo.setVisible(false);
		txtSearchrwo.setVisible(false);
	}

	private void showComponentRWO() {

		btnAddNwRow.setVisible(true);
		btnDelRow.setVisible(true);
		btnUpdaterwo.setVisible(true);

		// scpDelrwo.setVisible(true);
		pnlWODetailrwo.setVisible(true);
		txtSearchrwo.setVisible(true);
	}

	private void ShowPnlInputNwo(Connection conn) {
		// This method will show the input pannel
		int x = txtWOnwo.getText().length();
		int y = txtCustomernwo.getText().length();
		boolean z = false;// CHECK_FOR_TABLE_AVAILABILITY
		try {
			String WOName = txtWOnwo.getText();
			DatabaseMetaData dbm = (DatabaseMetaData) conn.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, WOName, null);
			if (tables.next()) {
				z = false;
			} else {
				z = true;
			}

			if (x > 0 && y > 0 && z == true) {
				pnlInputnwo.setVisible(true);

			} else {
				pnlInputnwo.setVisible(false);
			}

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void refreshNewWOTab() {

		txtWOnwo.setText("");
		txtCustomernwo.setText("");
		txtSubOrdernwo.setText("");
		txtInvoiceNonwo.setText("");

		txtPIDnwo.setText("");
		txtNosnwo.setText("");

		lblWOnwo.setText("");
		lblCustomernwo.setText("");
		lblSubnwo.setText("");
		lblInvoicenwo.setText("");
		lblPIDxNwo.setText("");
		lblNosxNwo.setText("");
		lblPIDnwo.setText("");
		lblNosnwo.setText("");
		lblPIDnwo.setText("");
		lblNosnwo.setText("");

		txtWOnwo.setEditable(true);
		txtCustomernwo.setEditable(true);
		txtSubOrdernwo.setEditable(true);
		txtInvoiceNonwo.setEditable(true);
		txtPIDnwo.setEditable(true);
		txtNosnwo.setEditable(true);

		pnlTireDetailnwo.setVisible(false);
		btnUpdateTireSizenwo.setVisible(false);
		btnDelAllRowsnwo.setVisible(false);
		btnUpdateWorkOrdernwo.setVisible(false);
		scpMainnwo.setVisible(false);
		pnlWODetailsnwo.setVisible(false);

		DefaultTableModel dm = (DefaultTableModel) jtblWorkOrder.getModel();

		for (int i = 0; i < dm.getRowCount(); i++) {
			for (int j = 0; j < dm.getColumnCount(); j++) {
				dm.setValueAt(null, i, j);
			}
		}

	}

	private void ShowCreateWOButton() {
		int xlen = txtPIDnwo.getText().length();
		int ylen = txtNosnwo.getText().length();
		int x = 0;
		int y = 0;

		if (xlen > 0) {
			x = Integer.parseInt(txtPIDnwo.getText());
		} else {
			x = 0;
		}
		if (ylen > 0) {
			y = Integer.parseInt(txtNosnwo.getText());
		}

		if ((y > x) && (xlen > 0) && (ylen > 0)) {
			btnCrWOnwo.setVisible(true);
		} else {
			btnCrWOnwo.setVisible(false);
		}
	}

	private int getNosDB(PDITireBean bean, int lastu) {
		int NosDB = 0;
		switch (lastu) {
		case 0:
			NosDB = bean.getR0();
			break;
		case 1:
			NosDB = bean.getR1();
			break;
		case 2:
			NosDB = bean.getR2();
			break;
		case 3:
			NosDB = bean.getR3();
			break;
		case 4:
			NosDB = bean.getR4();
			break;
		case 5:
			NosDB = bean.getR5();
			break;
		case 6:
			NosDB = bean.getR6();
			break;
		case 7:
			NosDB = bean.getR7();
			break;
		case 8:
			NosDB = bean.getR8();
			break;
		case 9:
			NosDB = bean.getR9();
			break;
		case 10:
			NosDB = bean.getR10();
			break;
		case 11:
			NosDB = bean.getR11();
			break;
		case 12:
			NosDB = bean.getR12();
			break;
		case 13:
			NosDB = bean.getR13();
			break;
		case 14:
			NosDB = bean.getR14();
			break;
		case 15:
			NosDB = bean.getR15();
			break;
		}
		return NosDB;
	}

	private void flushjtblDell() {
		try {
			DefaultTableModel model = (DefaultTableModel) jtblReviceWO.getModel();
			int rc = model.getRowCount();

			for (int i = 0; i < rc; i++) {
				model.removeRow(i);

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void dellRow(Connection conn) {

		DefaultTableModel model = (DefaultTableModel) jtblReviceWO.getModel();
		try {
			int SelectedRowIndex = jtblReviceWO.getSelectedRow();

			// Update jtblDel
			// Get the PID
			int pid = Integer.parseInt(jtblReviceWO.getModel().getValueAt(SelectedRowIndex, 0).toString());// Get_PID
			// **** table update only if PID is in DB
			// Check the availability of PID in database If not available tblDel
			// will not be updated

			String WOName = String.valueOf(cmbxWORwo.getSelectedItem());
			PDITireBean bean;
			bean = IndividualWO.findPID(WOName, pid, conn);

			if (bean != null) {
				DefaultTableModel modeldel = (DefaultTableModel) jtblDel.getModel();
				modeldel.addRow(new Object[] { pid + "" });
			}

			// Remove rows
			model.removeRow(SelectedRowIndex);

			scpDelrwo.setVisible(true);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Not Deleted");
		}
	}

	private void updateCounts() {
		Counts counts = new Counts();
		int total = counts.GetTtl(jtblWorkOrder, 1);// 1-is-qty-in-jtblWorkOrder-tbl

		int notEmptyRowNos = counts.NotEmptyRows(jtblWorkOrder);

		lblPIDnwo.setText(Integer.toString(notEmptyRowNos));
		lblNosnwo.setText(Integer.toString(total));

		int total1 = Integer.parseInt(txtNosnwo.getText());
		int pid1 = Integer.parseInt(txtPIDnwo.getText());

		int total2 = Integer.parseInt(lblNosnwo.getText());
		int pid2 = Integer.parseInt(lblPIDnwo.getText());

		btnDelAllRowsnwo.setVisible(true);

		if ((total1 == total2) && (pid1 == pid2)) {
			btnUpdateTireSizenwo.setVisible(true);

		} else {
			btnUpdateTireSizenwo.setVisible(false);
		}
	}

	public void positionColumn(JTable table, int co_Index) {
		table.moveColumn(table.getColumnCount() - 1, co_Index);
	}

	private void fillCombo(Connection conn) {// Fill the combo box as check
												// boxes are ticked
		// combo box option checking
		String sql = "";
		sql = "SELECT distinct wo date FROM fg.workoderdetails where activeStatus = 5 order by wo asc";

		if (chbCompletedOrders.isSelected()) {
			sql = "SELECT distinct wo FROM fg.workoderdetails where activeStatus = 0 order by wo asc";
		}
		if (chbLiveOrders.isSelected()) {
			sql = "SELECT distinct wo FROM fg.workoderdetails where activeStatus = 1 order by wo asc";
		}
		if ((chbLiveOrders.isSelected()) && (chbCompletedOrders.isSelected())) {
			sql = "SELECT distinct wo FROM fg.workoderdetails where activeStatus = 1 or activeStatus = 0 order by wo asc ";
		}

		try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				int workorder = rs.getInt("wo");
				cmbxWORwo.addItem(workorder);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + "  E13");
		}
	}

	private void unhideUpdateWObtn() {
		int total1 = Integer.parseInt(txtNosnwo.getText());
		int pid1 = Integer.parseInt(txtPIDnwo.getText());

		int total2 = Integer.parseInt(lblNosnwo.getText());
		int pid2 = Integer.parseInt(lblPIDnwo.getText());

		btnDelAllRowsnwo.setVisible(true);

		if ((total1 == total2) && (pid1 == pid2)) {
			txtNosnwo.setVisible(true);

		} else {
			txtNosnwo.setVisible(false);
		}
	}
	
	
	
	

	public int getNewPID() {
		return newPID;
	}

	public void setNewPID(int newPID) {
		this.newPID = newPID;
	}

	public int getNewNos() {
		return newNos;
	}

	public void setNewNos(int newNos) {
		this.newNos = newNos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
