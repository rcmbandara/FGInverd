package guis;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;



import beans.BasicDatafromPIDBean;
import beans.LocationBean;
import beans.NotokBean;
import beans.PIDBuildingInfoBean;
import beans.StkTireBean;
import beans.StocktbldgBean;
import db.DBType;
import db.DBUtil;
import joints.BasicDatafromPIDManager;
import tables.LocationTbl;
import tables.NotOkTbl;
import tables.PIDManager;
import tables.StkTbl;
import tables.StockTblDGTbl;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import joints.PIDBuildingInfofromPIDManager;
import javax.swing.JComboBox;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.SwingConstants;

public class FGDownGrade extends JFrame {

	private JPanel contentPane;
	private JTextField txtBarCode;
	private JButton btnRefresh;
	private JPanel pnlTireDate;
	private JLabel lblCap1;
	private JButton btnEnter;
	private JLabel lblCap2;

	private JLabel lblTireSize;
	private JLabel lblLugType;
	private JLabel lblConfig;
	private JLabel lblRimSize;
	private JLabel lblTireType;
	private JLabel lblBrandName;
	private JLabel lblSWMsg;
	private JLabel lblPID;
	private JLabel lblQG;
	private JLabel lblSN;
	private JLabel lblSNC;
	private JLabel lblQGC;
	private JLabel lblStkCount;
	private JComboBox<String> jcbLoc;
	private JLabel lblStockLoc;
	private JLabel lblStockStk;

	private boolean ExsistingTire = false;
	private boolean RePrinted = false;

	private int lid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FGDownGrade frame = new FGDownGrade();
					//// IPaddress
					String P_CONN_STRING = "";

					FileReader fr = new FileReader("C:\\Program Files\\MySQL\\MySQL Server 5.7\\DBUtil.txt");
					// FileReader fr = new
					// FileReader("/home/fg-admin/DBUtil/DBUtil.txt");

					BufferedReader br = new BufferedReader(fr);
					int lineNo;

					for (lineNo = 1; lineNo < 10; lineNo++) {
						if (lineNo == 4) {
							P_CONN_STRING = br.readLine();
						} else
							br.readLine();
					}

					frame.setTitle(P_CONN_STRING);
					// IP address
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void Intitalize() {
		textChangeEvent();
		fillCombo();
	}

	public FGDownGrade() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(00, 00, 1380, 813);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnEnter = new JButton("Enter");
		btnEnter.setVisible(false);
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//contentPane.setBackground(new Color(255, 0, 0));
				int pid = 0;
				String s = txtBarCode.getText();
				String SLet = s.substring(2, 3);

				String strPID = s.substring(2, 8);
				// to correct the 5digit pids
				if ("0".equals(SLet)) {
					strPID = "2" + s.substring(3, 8);// Add.2
				}
				pid = Integer.parseInt(strPID);// Convert.to.int
				String qg = txtBarCode.getText().substring(18, 19);
				String strSN = txtBarCode.getText().substring(9, 18);
				int sn = Integer.parseInt(strSN);

				StocktbldgBean stkbean =  new StocktbldgBean();
				stkbean.setSn(sn);
				stkbean.setPid(pid);
				stkbean.setQualitygrade(qg);
				stkbean.setLid(lid);

				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					boolean commitQueryies = false;
					conn.setAutoCommit(false);

					boolean inserted = StockTblDGTbl.insertFGDGIn(stkbean, conn);
					if (inserted == true) {
						commitQueryies = true;
					}
					if (commitQueryies) {
						conn.commit();
						refreshGUI(conn);
					} else {
						conn.rollback();
						btnRefresh.setVisible(false);
						JOptionPane.showMessageDialog(null, "Pls ReScan!!!!!!!!!");
					    btnRefresh.setVisible(true);
					    btnEnter.setVisible(true);
						
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex + "FGDG-E01");
				}
			}
		});
		btnEnter.setBackground(new Color(0, 255, 0));
		btnEnter.setFont(new Font("Tahoma", Font.BOLD, 40));
		btnEnter.setBounds(617, 239, 246, 77);
		contentPane.add(btnEnter);

		txtBarCode = new JTextField();
		txtBarCode.setVisible(false);
		txtBarCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// insertUpdates();
			}
		});
		txtBarCode.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtBarCode.setBounds(23, 29, 178, 31);
		contentPane.add(txtBarCode);
		txtBarCode.setColumns(10);

		lblCap1 = new JLabel("-----------------------------------");
		lblCap1.setForeground(new Color(0, 0, 128));
		lblCap1.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblCap1.setBounds(53, 71, 1124, 43);
		contentPane.add(lblCap1);
		lblCap1.setVisible(false);

		lblCap2 = new JLabel("-----------------------------------");
		lblCap2.setForeground(new Color(0, 0, 205));
		lblCap2.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblCap2.setBounds(53, 127, 1176, 50);
		contentPane.add(lblCap2);
		lblCap2.setVisible(false);

		pnlTireDate = new JPanel();
		pnlTireDate.setBackground(new Color(255, 255, 153));
		pnlTireDate.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlTireDate.setBounds(23, 329, 577, 412);
		contentPane.add(pnlTireDate);
		pnlTireDate.setLayout(null);
		pnlTireDate.setVisible(false);

		JLabel lblNewLabel = new JLabel("Tire Size");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setBounds(34, 24, 114, 20);
		pnlTireDate.add(lblNewLabel);

		JLabel label_2 = new JLabel("Lug Type");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_2.setForeground(Color.BLUE);
		label_2.setBounds(34, 68, 114, 20);
		pnlTireDate.add(label_2);

		JLabel label_3 = new JLabel("Configuration");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_3.setForeground(Color.BLUE);
		label_3.setBounds(34, 112, 114, 20);
		pnlTireDate.add(label_3);

		JLabel label_4 = new JLabel("Rim Size");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_4.setForeground(Color.BLUE);
		label_4.setBounds(34, 156, 114, 20);
		pnlTireDate.add(label_4);

		JLabel label_5 = new JLabel("Tire Type");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_5.setForeground(Color.BLUE);
		label_5.setBounds(34, 200, 114, 20);
		pnlTireDate.add(label_5);

		JLabel label_6 = new JLabel("Brand Name");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_6.setForeground(Color.BLUE);
		label_6.setBounds(34, 244, 114, 20);
		pnlTireDate.add(label_6);

		JLabel label_7 = new JLabel("SideWall Msg");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_7.setForeground(Color.BLUE);
		label_7.setBounds(34, 288, 114, 20);
		pnlTireDate.add(label_7);

		JLabel label_8 = new JLabel("PID");
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_8.setForeground(Color.BLUE);
		label_8.setBounds(34, 332, 114, 20);
		pnlTireDate.add(label_8);

		lblTireSize = new JLabel("---------");
		lblTireSize.setForeground(Color.BLUE);
		lblTireSize.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTireSize.setBounds(237, 24, 223, 20);
		pnlTireDate.add(lblTireSize);

		lblLugType = new JLabel("---------");
		lblLugType.setForeground(Color.BLUE);
		lblLugType.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLugType.setBounds(237, 68, 223, 20);
		pnlTireDate.add(lblLugType);

		lblConfig = new JLabel("---------");
		lblConfig.setForeground(Color.BLUE);
		lblConfig.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblConfig.setBounds(237, 118, 223, 20);
		pnlTireDate.add(lblConfig);

		lblRimSize = new JLabel("---------");
		lblRimSize.setForeground(Color.BLUE);
		lblRimSize.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRimSize.setBounds(237, 162, 223, 20);
		pnlTireDate.add(lblRimSize);

		lblTireType = new JLabel("---------");
		lblTireType.setForeground(Color.BLUE);
		lblTireType.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTireType.setBounds(237, 206, 223, 20);
		pnlTireDate.add(lblTireType);

		lblBrandName = new JLabel("---------");
		lblBrandName.setForeground(Color.BLUE);
		lblBrandName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBrandName.setBounds(237, 250, 223, 20);
		pnlTireDate.add(lblBrandName);

		lblSWMsg = new JLabel("---------");
		lblSWMsg.setForeground(Color.BLUE);
		lblSWMsg.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSWMsg.setBounds(237, 294, 223, 20);
		pnlTireDate.add(lblSWMsg);

		lblPID = new JLabel("---------");
		lblPID.setForeground(Color.BLUE);
		lblPID.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPID.setBounds(237, 338, 223, 20);
		pnlTireDate.add(lblPID);

		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					refreshGUI(conn);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e + "FGDG-E02");
				}
			}
		});
		btnRefresh.setBackground(new Color(255, 255, 0));
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 40));
		btnRefresh.setBounds(924, 239, 235, 77);
		contentPane.add(btnRefresh);

		lblSNC = new JLabel("SN");
		lblSNC.setBounds(260, 31, 114, 20);
		contentPane.add(lblSNC);
		lblSNC.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblSNC.setForeground(Color.BLUE);
		lblSNC.setVisible(false);

		lblSN = new JLabel("---------");
		lblSN.setBounds(384, 31, 223, 20);
		contentPane.add(lblSN);
		lblSN.setForeground(Color.BLUE);
		lblSN.setFont(new Font("Tahoma", Font.PLAIN, 22));

		lblQGC = new JLabel("Quality Grade");
		lblQGC.setBounds(63, 188, 223, 40);
		contentPane.add(lblQGC);
		lblQGC.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblQGC.setForeground(Color.BLUE);
		lblQGC.setVisible(false);

		lblQG = new JLabel("---------");
		lblQG.setBounds(323, 198, 138, 20);
		contentPane.add(lblQG);
		lblQG.setForeground(Color.BLUE);
		lblQG.setFont(new Font("Tahoma", Font.PLAIN, 30));

		lblStkCount = new JLabel("...");
		lblStkCount.setForeground(new Color(255, 0, 0));
		lblStkCount.setBackground(new Color(255, 0, 0));
		lblStkCount.setFont(new Font("Tahoma", Font.PLAIN, 60));
		lblStkCount.setBounds(860, 142, 317, 65);
		contentPane.add(lblStkCount);

		jcbLoc = new JComboBox();
		jcbLoc.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {

				lblStockLoc.setText("");
				lblStockStk.setText("");
				String loc = (String) jcbLoc.getSelectedItem();
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					LocationBean bean = LocationTbl.getRow(loc, conn);
					lid = bean.getLid();
					GetSums();
					
					

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
			}
		});
		jcbLoc.setBounds(831, 481, 263, 43);
		contentPane.add(jcbLoc);

		JLabel lblLocation = new JLabel("Location");
		lblLocation.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblLocation.setBounds(872, 394, 194, 56);
		contentPane.add(lblLocation);

		JLabel lblNewLabel_1 = new JLabel("Total Tires in Location");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(617, 656, 266, 56);
		contentPane.add(lblNewLabel_1);

		JLabel lblTotalTiresIn = new JLabel("Total tires in Stock");
		lblTotalTiresIn.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTotalTiresIn.setBounds(625, 590, 258, 55);
		contentPane.add(lblTotalTiresIn);

		lblStockLoc = new JLabel("----");
		lblStockLoc.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblStockLoc.setBounds(933, 656, 71, 56);
		contentPane.add(lblStockLoc);

		lblStockStk = new JLabel("------");
		lblStockStk.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblStockStk.setBounds(924, 600, 114, 35);
		contentPane.add(lblStockStk);
		
		JLabel lblNewLabel_2 = new JLabel("BER STOCK");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 93));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(617, 0, 529, 88);
		contentPane.add(lblNewLabel_2);
		lblSN.setVisible(false);
		lblQG.setVisible(false);
		Intitalize();
	}

	private void findPIDofAPW(int wheelColorid) {

		try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
			String s = txtBarCode.getText();
			int l = s.length();

			if (l == 19) {// Check.for.barcode.
				txtBarCode.setEnabled(false);
				int pid = 0;
				String SLet = s.substring(2, 3);

				String strPID = s.substring(2, 8);
				// to correct the 5digit pids
				if ("0".equals(SLet)) {
					strPID = "2" + s.substring(3, 8);// Add.2
				}
				pid = Integer.parseInt(strPID);// Convert.to.int

				PIDBuildingInfoBean bean = PIDBuildingInfofromPIDManager.getRow(pid, conn);
				// IF color ids catch only enter button is vissible
				boolean APWCIDDiff = true;
				if (wheelColorid == bean.getWheelColorID()) {
					APWCIDDiff = false;
				}
				// Irrespective of pid barcoded selected color is sent to get
				// the correct pid value
				bean.setWheelColorID(wheelColorid);

				// bean2 returns pid of adjusted PID
				PIDBuildingInfoBean bean2 = PIDManager.getRow(bean, conn);
				pid = bean2.getPid();

				ShowCommandButtons(pid, APWCIDDiff, conn);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void textChangeEvent() {

		txtBarCode.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				insertUpdates();
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// asdf
				;
			}
		});
	}

	private void insertUpdates() {

		Runnable doHighlight = new Runnable() {
			@Override
			public void run() {

				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					String s = txtBarCode.getText();
					int l = s.length();

					if (l == 19) {// Check.for.barcode.
						txtBarCode.setEnabled(false);
						int pid = 0;
						String SLet = s.substring(2, 3);

						String strPID = s.substring(2, 8);
						// to correct the 5digit pids
						if ("0".equals(SLet)) {
							strPID = "2" + s.substring(3, 8);// Add.2
						}
						pid = Integer.parseInt(strPID);// Convert.to.int

						// Get tire type and if tire type is APW get the PID
						// according to the paint color choosen
						BasicDatafromPIDBean beanBasicDataFromPID = BasicDatafromPIDManager.getRow(pid, conn);
						String config = beanBasicDataFromPID.getConfig();
						String wheelColor = beanBasicDataFromPID.getWheelcolor();
						// below bool is to hide enter button if color of the
						// barcode and actual color paint is different.
						boolean apwPIDChange = false;
						if (config.equals("APW")) {

						} else {
							// Passed the pid which is assigned by
							boolean APWCIDDiff = false;
							ShowCommandButtons(pid, APWCIDDiff, conn);
						}

					} else if (l > 19) {
						txtBarCode.setEnabled(false);
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2 + "FGDG-E04");
				}
			}

		};
		SwingUtilities.invokeLater(doHighlight);

	}

	private void fillCombo() {
		jcbLoc.removeAllItems();
		String sql = "SELECT location FROM stock.locations_dg  ; ";
		ResultSet rs = null;
		try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			rs = stmt.executeQuery();

			while (rs.next()) {
				jcbLoc.addItem(rs.getString("location"));

			}

		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2 + "FGDG-E06");
		}

	}

	private void GetSums() {

		int Stock = tables.StockTblDGTbl.getSum("Total", lid);
		int Locations = tables.StockTblDGTbl.getSum("Locaion", lid);

		lblStockLoc.setText(Integer.toString(Locations));
		lblStockStk.setText(Integer.toString(Stock));
		
		lblStkCount.setText(Integer.toString(Stock));
		
		txtBarCode.setVisible(true);
		txtBarCode.setEditable(true);
		txtBarCode.requestFocus();
		
	}

	private void ShowCommandButtons(int pid, boolean APWCIDDiff, Connection conn) {

		try {
			String s = txtBarCode.getText();
			int l = s.length();
			RePrinted = false;
			ExsistingTire = false;

			txtBarCode.setEnabled(false);

			String SLet = s.substring(2, 3);
			String SYearnMonth = s.substring(9, 13);
			int YearnMonth = Integer.parseInt(SYearnMonth);

			String qg = txtBarCode.getText().substring(18, 19);

			String strSN = txtBarCode.getText().substring(9, 18);
			int sn = Integer.parseInt(strSN);

			// Check avaialbility in stk table and notok table.
			StkTireBean bean = StkTbl.getRow(sn, conn);
			NotokBean bean2 = NotOkTbl.getRow(sn, conn);

			// Get the location of the tire.Manufactured,fg stores
			// or shipped

			// PID from not ok table--> first pid when tire is
			// marked as not ok
			int avlLoccation = 0;
			int pidfrmNokTbl = 0;
			String qgfrmNokTbl = "";
			if (bean != null) {
				avlLoccation = bean.getAvl();
				ExsistingTire = true;
			}
			if (bean2 != null) {
				pidfrmNokTbl = bean2.getPid();
				qgfrmNokTbl = bean2.getQg();
			}
			
				if (bean != null) {// Tire.is.available.in.stock.DB
					if (avlLoccation > 1) {// IN.Manufctruring.Area
						if (bean2 != null) {// Tire.is.in.NotOKlist.also

							if ((pid == pidfrmNokTbl) && (qg.equals(qgfrmNokTbl))) {// SamePIDs

								JOptionPane.showMessageDialog(null, "Please ReBarCode", "RePrint",
										JOptionPane.ERROR_MESSAGE);
								refreshGUI(conn);
							} else {// Not Equal
								// Reprinted
								RePrinted = true;
								setVisibleComponents();
								// APW wheel color change and no way to enter
								// tire to db
								if (APWCIDDiff) {
									btnEnter.setVisible(false);
								}
								updateLables(pid, qg, sn, conn);
							}
						} else {// Tire.is.NOT.in.NotOKlist.also
							setVisibleComponents();
							// APW wheel color change and no way to enter tire
							// to db
							if (APWCIDDiff) {
								btnEnter.setVisible(false);
							}
							updateLables(pid, qg, sn, conn);
						}

					} else {// IN.FG.or.Dispatched.Area
						if (bean2 != null) {// Tire.is.in.NotOKlist.also

							JOptionPane.showMessageDialog(null, "Please Contact Stores Manager", "Serious Error",
									JOptionPane.ERROR_MESSAGE);
							refreshGUI(conn);

						} else {// Tire.is.NOT.in.NotOKlist.also
							refreshGUI(conn);
							JOptionPane.showMessageDialog(null, "Already BarCoded Tire", "Tire In FGStores",
									JOptionPane.ERROR_MESSAGE);
							btnEnter.setVisible(false);
							
						}
					}

				} else {// Tire.is.NOT.available.in.stock.DB

					if (bean2 != null) {// Tire.is.in.NotOKlist.also
						JOptionPane.showMessageDialog(null, "Please Contact Stores Manager", "Serious Error",
								JOptionPane.ERROR_MESSAGE);
						refreshGUI(conn);
					} else {// Tire.is.NOT.in.NotOKlist.also

						setVisibleComponents();
						// APW wheel color change and no way to enter tire to db
						if (APWCIDDiff) {
							btnEnter.setVisible(false);
						}
						updateLables(pid, qg, sn, conn);
					}

				}

			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + "FGDG-E05");
		}

	}

	private boolean insertDatatoStkTbl(StkTireBean bean, Connection conn) {

		// StkTireBean bean = new StkTireBean();

		// bean.setSn(sn);
		// bean.setPid(pid);
		// bean.setQg(qg);
		// bean.setAvl(1);

		try {
			boolean result = StkTbl.insertFGIn(bean, conn);
			return result;
		} catch (Exception ex) {
			JOptionPane.showInternalMessageDialog(null, ex);
			return false;
		}
	}

	private boolean updateStkTbl(StkTireBean bean, Connection conn) {
		try {

			boolean updated = StkTbl.updateAvlnPID(bean, conn);

			return updated;

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}

	}

	private boolean insertDatatoNotOkTbl(NotokBean bean, Connection conn) {

		try {
			boolean result = NotOkTbl.insertFGIn(bean, conn);
			return result;
		} catch (Exception ex) {
			JOptionPane.showInternalMessageDialog(null, ex);
			return false;
		}
	}

	private void setVisibleComponents() {
		btnRefresh.setVisible(true);
		btnEnter.setVisible(true);
		

		lblCap1.setVisible(true);
		lblCap2.setVisible(true);
		pnlTireDate.setVisible(true);

		lblSN.setVisible(true);
		lblSNC.setVisible(true);

		lblQG.setVisible(true);
		lblQGC.setVisible(true);

		contentPane.setBackground(new Color(102, 255, 153));
	}

	private void refreshGUI(Connection conn) {
		// btnRefresh.setVisible(false);
		btnEnter.setVisible(false);
		
		lblCap1.setVisible(false);
		lblCap2.setVisible(false);
		pnlTireDate.setVisible(false);
		lblSN.setVisible(false);
		lblSNC.setVisible(false);
		lblQG.setVisible(false);
		lblQGC.setVisible(false);

		txtBarCode.setEnabled(true);
		txtBarCode.setText("");
		txtBarCode.requestFocus();
		contentPane.setBackground(new Color(204, 255, 255));

	    //	int countTtl = StkTbl.getTotal(conn);

		//lblStkCount.setText(countTtl + "");
		
		GetSums();
		

	}

	@SuppressWarnings("unused")
	private void updateLables(int pid, String qg, int SN, Connection conn) {

		String tireSize = "N/A";
		String lugType = "N/A";
		String config = "N/A";
		String rimSize = "N/A";
		String tireType = "N/A";
		String brandName = "N/A";
		String sWMsg = "N/A";

		try {
			BasicDatafromPIDBean bean = BasicDatafromPIDManager.getRow(pid, conn);
			if (bean != null) {
				tireSize = bean.getSizebasic();
				lugType = bean.getLugtype();
				config = bean.getConfig();
				rimSize = bean.getRimsize();
				tireType = bean.getTiretype();
				brandName = bean.getBrand();
				sWMsg = bean.getSwmsg();

			} else {
				refreshGUI(conn);
			}

			lblTireSize.setText(tireSize);
			lblLugType.setText(lugType);
			lblConfig.setText(config);
			lblRimSize.setText(rimSize);
			lblTireType.setText(tireType);
			lblBrandName.setText(brandName);
			lblSWMsg.setText(sWMsg);
			lblQG.setText(qg);
			lblSN.setText(Integer.toString(SN));
			lblPID.setText(Integer.toString(pid));

			String all = tireSize + "" + lugType + "  " + config + " " + rimSize + " " + tireType;
			String swAndBrand = brandName + " " + sWMsg;
			lblCap1.setText(all);
			lblCap2.setText(swAndBrand);

		} catch (Exception ex) {

		}

	}
}
