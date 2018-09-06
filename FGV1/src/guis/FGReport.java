package guis;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import db.DBType;
import db.DBUtil;
import tables.IndividualWO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.event.PopupMenuListener;

import DataFiltering.CreateTempTblStockReport;

import javax.swing.event.PopupMenuEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class FGReport extends JFrame implements KeyListener {

	private JPanel contentPane;
	private JComboBox<String> cmbTireSize;
	private JComboBox cmbLugType;
	private JComboBox cmbConfig;
	private JComboBox cmbRimSize;
	private JComboBox cmbTireType;
	private JComboBox cmbSWMsg;
	private JComboBox cmbBrandName;
	private String ipStart;
	private static FGReport frame;
	private JScrollPane scpPID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new FGReport();
					//// IPaddress
					String M_CONN_STRING = "";

					FileReader fr = new FileReader("C:\\Program Files\\MySQL\\MySQL Server 5.7\\DBUtil.txt");
					// FileReader fr = new
					// FileReader("/home/fg-admin/DBUtil/DBUtil.txt");

					BufferedReader br = new BufferedReader(fr);
					int lineNo;

					for (lineNo = 1; lineNo < 10; lineNo++) {
						if (lineNo == 1) {
							M_CONN_STRING = br.readLine();
						} else
							br.readLine();
					}
					br.close();

					frame.setTitle(M_CONN_STRING);
					// IP address
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws UnknownHostException
	 */
	public FGReport() {
		try {
			getIPIni();
			initializeTempTable();

		} catch (Exception ex) {
			cmbTireSize.setVisible(false);
			JOptionPane.showMessageDialog(null, ex+"-FGR-E01");
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1086, 575);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPid = new JLabel("Tire Size");
		lblPid.setBounds(67, 27, 59, 14);
		lblPid.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblPid);

		JLabel lblLugType = new JLabel("Lug Type");
		lblLugType.setBounds(166, 27, 59, 14);
		lblLugType.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblLugType);

		JLabel lblConfig = new JLabel("Config");
		lblConfig.setBounds(257, 27, 53, 14);
		lblConfig.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblConfig);

		JLabel label_4 = new JLabel("Rim Size");
		label_4.setBounds(342, 27, 57, 14);
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label_4);

		JLabel label_1 = new JLabel("TireType");
		label_1.setBounds(420, 27, 52, 14);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("SW Msg");
		label_2.setBounds(482, 27, 69, 14);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("BrandName");
		label_3.setBounds(561, 27, 75, 14);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label_3);

		cmbLugType = new JComboBox();

		cmbLugType.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		JTextField editorLT = (JTextField) cmbLugType.getEditor().getEditorComponent();
		editorLT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateTempTbl();
			}
		});
		cmbLugType.setEditable(true);
		cmbLugType.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				updateTempTbl();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					fillCombo(conn, "lt");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e+"-FGR-E02");
				}
			}
		});
		cmbLugType.setBounds(166, 45, 75, 20);
		contentPane.add(cmbLugType);

		cmbConfig = new JComboBox();
		cmbConfig.setEditable(true);
		cmbConfig.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				updateTempTbl();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					fillCombo(conn, "config");
				} catch (Exception es) {
					JOptionPane.showMessageDialog(null, es+"-FGR-E03");
				}
			}
		});
		cmbConfig.setBounds(245, 45, 75, 20);
		contentPane.add(cmbConfig);

		cmbRimSize = new JComboBox();
		cmbRimSize.setEditable(true);
		cmbRimSize.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				updateTempTbl();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					fillCombo(conn, "rs");
				} catch (Exception es) {
					JOptionPane.showMessageDialog(null, es+"-FGR-E04");
				}
			}
		});
		cmbRimSize.setBounds(324, 45, 75, 20);
		contentPane.add(cmbRimSize);

		cmbTireType = new JComboBox();
		cmbTireType.setEditable(true);
		cmbTireType.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				updateTempTbl();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					fillCombo(conn, "tt");
				} catch (Exception es) {
					JOptionPane.showMessageDialog(null, es+"-FGR-E05");
				}
			}
		});
		cmbTireType.setBounds(403, 45, 75, 20);
		contentPane.add(cmbTireType);

		cmbSWMsg = new JComboBox();
		cmbSWMsg.setEditable(true);
		cmbSWMsg.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				updateTempTbl();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					fillCombo(conn, "swmsg");
				} catch (Exception es) {
					JOptionPane.showMessageDialog(null, es+"-FGR-E06");
				}
			}
		});
		cmbSWMsg.setBounds(482, 45, 75, 20);
		contentPane.add(cmbSWMsg);

		cmbBrandName = new JComboBox();
		cmbBrandName.setEditable(true);
		cmbBrandName.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				updateTempTbl();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					fillCombo(conn, "brand");
				} catch (Exception es) {
					JOptionPane.showMessageDialog(null, es+"-FGR-E07");
				}
			}
		});
		cmbBrandName.setBounds(561, 45, 75, 20);
		contentPane.add(cmbBrandName);

		cmbTireSize = new JComboBox();
		cmbTireSize.setEditable(true);

		// get the jtextfield of combo box first
		JTextField editor = (JTextField) cmbTireSize.getEditor().getEditorComponent();
		editor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					initializeTempTable();
					updateTempTbl();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e+"-FGR-E08");
				}
			}
		});

		// cmbTireSize.
		cmbTireSize.setBounds(31, 45, 129, 20);
		cmbTireSize.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				updateTempTbl();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					fillCombo(conn, "ts");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e+"-FGR-E09");
				}
			}
		});
		contentPane.add(cmbTireSize);

		scpPID = new JScrollPane();
		scpPID.setBounds(31, 94, 562, 251);
		contentPane.add(scpPID);
	}

	private void tempTblsStock(Connection conn, String ip, String filter) {
		try {

			String sql = "CREATE or replace view stock.tempreportstk" + ip
					+ " as select  stk.sn,p.pid,sb.tiresizebasic tsize,lt.lugtypecap ltypecap,con.config config,rs.rimsize, tt.tiretypecap ttypecap,br.brand,sw.swmsg, tt.tiretype,lt.lugtype,"
					+ "stk.pid pidcap,stk.pidbc pidbccap,stk.qg qgcap,stk.mfgdate mfgdatecap,stk.tc tccap,stk.bcdate bcdatecap,stk.fgindate fgindatecap, "
					+ "stk.fgoutdate fgoutdatecap,stk.avl avlcap,stk.stkvfy stkvfycap,stk.wo wocap,stk.customer customercap,stk.customersubno customersubnocap,stk.invoiceno invoicenocap, "
					+ "wc.color wcolor  FROM stock.stk stk  join srtspec.pid p on p.pid = stk.pid "
					+ "join srtspec.size s on s.sizeid = p.sizeid "
					+ "join srtspec.sizebasic sb on sb.sizebasicid = s.sizebasicid "
					+ "join srtspec.lugtype lt on lt.lugtypeid= s.lugtypeid "
					+ "join srtspec.config con on con.configid= s.configid "
					+ "join srtspec.tiretype tt on tt.tiretypeid = p.tiretypeid "
					+ "join srtspec.rimsize rs on rs.rimsizeid = p.rimsizeid "
					+ "join srtspec.swmsg sw on sw.swmsgid = p.swmsgid "
					+ "join srtspec.wheelcolor wc on wc.wheelcolorid= p.wheelcolorid "
					+ "join srtspec.brand br on br.brandid = p.brandid " + "where " + filter + "  ";

			// System.out.println(sql);
			CreateTempTblStockReport.tempTblCreator(sql, conn);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"-FGR-E010");
		}

	}

	private void fillCombo(Connection conn, String parameter) throws UnknownHostException {// Fill
		// the
		// combo
		// box
		// as
		// check

		InetAddress IP;
		IP = InetAddress.getLocalHost();

		String ip = IP.getHostAddress();

		ip = ip.substring(ip.length() - 3);

		switch (parameter) {
		case "ts":
			fillTSizeCmb(conn, ip);
			break;
		case "lt":
			fillLTypeCmb(conn, ip);
			break;
		case "config":
			fillConfigCmb(conn, ip);
			break;
		case "rs":
			fillRSizCmb(conn, ip);
			break;
		case "tt":
			fillTypeCmb(conn, ip);
			break;
		case "brand":
			fillBrandCmb(conn, ip);
			break;

		case "swmsg":
			fillSWMsgCmb(conn, ip);

			break;
		}

	}

	private void fillTSizeCmb(Connection conn, String ip) {
		// Tire Size
		cmbTireSize.removeAllItems();

		String sqlts = "SELECT distinct tsize FROM stock.tempreportstk" + ip + " order by tsize";
		try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sqlts);) {
			while (rs.next()) {

				String TireSize = rs.getString("tsize");
				cmbTireSize.addItem(TireSize);

			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"-FGR-E011");
		}
	}

	private void fillLTypeCmb(Connection conn, String ip) {
		// LugType
		cmbLugType.removeAllItems();

		String sqllt = "SELECT distinct ltypecap FROM stock.tempreportstk" + ip + " order by ltypecap";
		try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sqllt);) {
			while (rs.next()) {

				String x = rs.getString("ltypecap");
				cmbLugType.addItem(x);

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"-FGR-E012");
		}

	}

	private void fillConfigCmb(Connection conn, String ip) {
		// config
		cmbConfig.removeAllItems();
		String sqlconfig = "SELECT distinct config FROM stock.tempreportstk" + ip + " order by config";
		try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sqlconfig);) {
			while (rs.next()) {

				String x = rs.getString("config");
				cmbConfig.addItem(x);

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"-FGR-E013");
		}
	}

	private void fillRSizCmb(Connection conn, String ip) {
		// rimsize
		cmbRimSize.removeAllItems();
		String sqlrs = "SELECT distinct rimsize FROM stock.tempreportstk" + ip + " order by rimsize";
		try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sqlrs);) {
			while (rs.next()) {

				String x = rs.getString("rimsize");
				cmbRimSize.addItem(x);

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"-FGR-E014");
		}
	}

	private void fillTypeCmb(Connection conn, String ip) {
		// tiretype
		cmbTireType.removeAllItems();

		String sqltt = "SELECT distinct ttypecap FROM stock.tempreportstk" + ip + " order by ttypecap";
		try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sqltt);) {
			while (rs.next()) {

				String x = rs.getString("ttypecap");
				cmbTireType.addItem(x);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"-FGR-E015");
		}
	}

	private void fillBrandCmb(Connection conn, String ip) {
		// brand
		cmbBrandName.removeAllItems();
		String sqlbrand = "SELECT distinct brand FROM stock.tempreportstk" + ip + " order by brand";
		try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sqlbrand);) {
			while (rs.next()) {

				String x = rs.getString("brand");
				cmbBrandName.addItem(x);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"-FGR-E016");
		}
	}

	private void fillSWMsgCmb(Connection conn, String ip) {
		// Swmsg
		cmbSWMsg.removeAllItems();
		String sqlswmsg = "SELECT distinct swmsg FROM stock.tempreportstk" + ip + " order by swmsg";
		try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sqlswmsg);) {
			while (rs.next()) {

				String x = rs.getString("swmsg");
				cmbSWMsg.addItem(x);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"-FGR-E017");
		}
	}

	private void initializeTempTable() throws UnknownHostException {
		InetAddress IP;
		IP = InetAddress.getLocalHost();

		String ip = IP.getHostAddress();

		ip = ip.substring(ip.length() - 3);

		if (ip.equals(ipStart)) {
			try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
				tempTblsStock(conn, ip, "sb.tiresizebasic like '%%'");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e+"-FGR-E018");
			}
		} else {
			JOptionPane.showMessageDialog(null, "IPNumber Conflict");
		}
	}

	private void updateTempTbl() {
		try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
			InetAddress IP;
			IP = InetAddress.getLocalHost();

			String ip = IP.getHostAddress();
			ip = ip.substring(ip.length() - 3);
			if (ip.equals(ipStart)) {
				String tiresize = "";
				String lugtype = "";

				String config = "";
				String rimsize = "";
				String tiretype = "";
				String brand = "";
				String swmsg = "";

				tiresize = cmbTireSize.getEditor().getItem().toString();

				lugtype = cmbLugType.getEditor().getItem().toString();

				config = cmbConfig.getEditor().getItem().toString();

				rimsize = cmbRimSize.getEditor().getItem().toString();

				tiretype = cmbTireType.getEditor().getItem().toString();

				brand = cmbBrandName.getEditor().getItem().toString();

				swmsg = cmbSWMsg.getEditor().getItem().toString();

				String filter = "sb.tiresizebasic like '%" + tiresize + "%' and lt.lugtype like '%" + lugtype
						+ "%' and con.config like '%" + config + "%' and  rs.rimsize like '%" + rimsize
						+ "%' and tt.tiretypecap like '%" + tiretype + "%' and br.brand like '%" + brand + "%'  "
						+ "and sw.swmsg like '%" + swmsg + "%';";

				tempTblsStock(conn, ip, filter);

				createTempPIDTbl(conn);
			} else {
				JOptionPane.showMessageDialog(null, "IP coflict");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"-FGR-E019");
		}

	}

	private void createTempPIDTbl(Connection conn) throws UnknownHostException {
		InetAddress IP;
		IP = InetAddress.getLocalHost();

		String ip = IP.getHostAddress();
		ip = ipStart.substring(ipStart.length() - 3);
		if (ip.equals(ipStart)) {

			try {

				String filter = "stock.tempreportstk" + ip + ".pid like '%%'";

				String sql = "CREATE or replace view stock.tempPID" + ip + " as SELECT distinct tempreportstk" + ip
						+ ".pid ,tsize,ltypecap,config,rimsize,ttypecap,brand,swmsg,wcolor FROM stock.tempreportstk"
						+ ip + " " + "where " + filter + "order by tsize limit 100; ";

				//CreateTempTbl
				CreateTempTblStockReport.tempTblCreator(sql, conn);

				//Create jtable for PID
				JTable jtblPIDList = IndividualWO.createPIDLIstJTbl(ip, conn);// get_Data_From_DB_and_addColumnforTireSize
				// Put table in to the scrol pane
				scpPID.setViewportView(jtblPIDList);
				
				
				//create field views in order to speedupthe selection process of combo box
				String sqlts = "CREATE or replace view stock.tiresize" + ip + " as select distinct tsize from stock.tempreportstk" + ip + " order by tsize; ";
				CreateTempTblStockReport.tempTblCreator(sqlts, conn);
				
				String sqllt = "CREATE or replace view stock.lugtype" + ip + " as select distinct ltypecap from stock.tempreportstk" + ip + " order by ltypecap; ";
				CreateTempTblStockReport.tempTblCreator(sqllt, conn);
				

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e+"-FGR-E20");
			}

		} else {
			JOptionPane.showMessageDialog(null, "IP address conflict");
		}

	}

	private void getIPIni() throws UnknownHostException {
		InetAddress IP;
		IP = InetAddress.getLocalHost();

		ipStart = IP.getHostAddress();
		ipStart = ipStart.substring(ipStart.length() - 3);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
