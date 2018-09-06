package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;

import beans.BasicDatafromPIDBean;
import beans.DispatchTblBean;
import beans.LocationBean;
import beans.NotokBean;
import beans.StkTireBean;
import beans.StocktbldgBean;
import db.DBType;
import db.DBUtil;
import general.CreateTblWODispatched;
import joints.BasicDatafromPIDManager;
import tables.DispatchesTbl;
import tables.LocationTbl;
import tables.NotOkTbl;
import tables.StkTbl;
import tables.StockTblDGTbl;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PDI_Temp extends JFrame {

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
	private JButton btnWO;
	private JLabel lblWODetail;
	private boolean ExsistingTire = false;
	private boolean RePrinted = false;

	private int lid;
	private JTextField txtWO;
	private JTextField txtCustomer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PDI_Temp frame = new PDI_Temp();
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
		// fillCombo();
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
						String qg = txtBarCode.getText().substring(18, 19);

						String strSN = txtBarCode.getText().substring(9, 18);
						int sn = Integer.parseInt(strSN);

						StkTireBean bean = new StkTireBean();

						bean = StkTbl.getRow(sn, conn);

						int avl = bean.getAvl();
						int tDispatch = bean.getTdispatch();

						if (avl == 1) {
							txtBarCode.setEnabled(false);
							updateLables(pid, qg, sn, conn);
							setVisibleComponents();
						} else {
							JOptionPane.showMessageDialog(null,
									"Can not send this tire to customer.Avlable Location is(Avl):- " + avl);
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

	public PDI_Temp() {
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
				// contentPane.setBackground(new Color(255, 0, 0));
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

				DispatchTblBean dispatchTblBean = new DispatchTblBean();
				dispatchTblBean.setSn(sn);
				dispatchTblBean.setPid(pid);
				dispatchTblBean.setQg(qg);

				StkTireBean stkbean = new StkTireBean();
				stkbean.setSn(sn);

				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					boolean commitQueryies = false;
					conn.setAutoCommit(false);

					boolean insertedtoDispatches = DispatchesTbl.insertDispatches(lblWODetail.getText() + "",
							dispatchTblBean, conn);
					boolean insertedtoStk = StkTbl.updatetDispatchesinStkTbl(stkbean, conn);
					if ((insertedtoDispatches == true) && (insertedtoStk == true)) {
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
		btnEnter.setBounds(639, 188, 246, 77);
		contentPane.add(btnEnter);

		txtBarCode = new JTextField();
		txtBarCode.setVisible(false);
		txtBarCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		txtBarCode.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtBarCode.setBounds(23, 29, 178, 31);
		contentPane.add(txtBarCode);
		txtBarCode.setColumns(10);

		lblCap1 = new JLabel(
				"--------------------------------------------------------------------------------------------------");
		lblCap1.setForeground(new Color(0, 0, 128));
		lblCap1.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblCap1.setBounds(53, 71, 1301, 43);
		contentPane.add(lblCap1);
		lblCap1.setVisible(false);

		lblCap2 = new JLabel(
				"--------------------------------------------------------------------------------------------------");
		lblCap2.setForeground(new Color(0, 0, 205));
		lblCap2.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblCap2.setBounds(53, 127, 1274, 50);
		contentPane.add(lblCap2);
		lblCap2.setVisible(false);

		pnlTireDate = new JPanel();
		pnlTireDate.setBackground(new Color(255, 255, 153));
		pnlTireDate.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlTireDate.setBounds(23, 239, 403, 372);
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
		btnRefresh.setBounds(921, 188, 235, 77);
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

		lblWODetail = new JLabel("Dispatch Area");
		lblWODetail.setForeground(Color.BLACK);
		lblWODetail.setBackground(Color.PINK);
		lblWODetail.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblWODetail.setHorizontalAlignment(SwingConstants.CENTER);
		lblWODetail.setBounds(556, 1, 303, 59);
		contentPane.add(lblWODetail);

		JLabel lblXxx = new JLabel("xxx");
		lblXxx.setBounds(23, 760, 46, 14);
		contentPane.add(lblXxx);

		txtWO = new JTextField();
		txtWO.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				int x = txtCustomer.getDocument().getLength();
				int y = txtWO.getDocument().getLength();

				if ((x > 0 && y > 0)) {
					btnWO.setVisible(true);
				}

			}
		});
		txtWO.setBounds(133, 666, 86, 20);
		contentPane.add(txtWO);
		txtWO.setColumns(10);

		txtCustomer = new JTextField();
		txtCustomer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				int x = txtCustomer.getDocument().getLength();
				int y = txtWO.getDocument().getLength();

				if ((x > 0 && y > 0)) {
					btnWO.setVisible(true);
				}

			}
		});
		txtCustomer.setColumns(10);
		txtCustomer.setBounds(133, 633, 86, 20);
		contentPane.add(txtCustomer);

		JLabel lblCustomer = new JLabel("Customer");
		lblCustomer.setBounds(23, 636, 46, 14);
		contentPane.add(lblCustomer);

		JLabel lblWorkorder = new JLabel("WorkOrder");
		lblWorkorder.setBounds(23, 669, 69, 14);
		contentPane.add(lblWorkorder);

		btnWO = new JButton("Enter");
		btnWO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtBarCode.setVisible(true);
				String woDetail = txtWO.getText() + "_" + txtCustomer.getText();
				lblWODetail.setText(woDetail);

				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					boolean x = CreateTblWODispatched.CreateWO(woDetail, conn);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2 + "PDITemp-E04");
				}
				btnWO.setVisible(false);
				txtWO.setEnabled(false);
				txtCustomer.setEnabled(false);
			}

		});
		btnWO.setVisible(false);
		btnWO.setBounds(97, 713, 89, 23);
		contentPane.add(btnWO);
		lblSN.setVisible(false);
		lblQG.setVisible(false);
		Intitalize();
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
		txtBarCode.setVisible(true);
		txtBarCode.setText("");
		txtBarCode.requestFocus();
		contentPane.setBackground(new Color(204, 255, 255));

		// int countTtl = StkTbl.getTotal(conn);

		// lblStkCount.setText(countTtl + "");

	}
}
