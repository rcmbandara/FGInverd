package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import beans.BasicDatafromPIDBean;
import beans.StkTireBean;
import db.DBType;
import db.DBUtil;
import joints.BasicDatafromPIDManager;
import tables.StkTbl;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class TireRemoveFromStock extends JFrame {

	private JPanel contentPane;
	private JTextField txtSN;
	private JLabel lblTireDetail;
	private JLabel lblQg;
	private JLabel lblDispatchStatus;
	private JLabel lblTireSize;
	private JLabel lblQualityGrade;
	private JLabel label;
	private JButton btnEnter;
	private JButton btnRefresh;
	private JButton btnEdcCuttire;
	private JTextField txtRemarx;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TireRemoveFromStock frame = new TireRemoveFromStock();
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
	public TireRemoveFromStock() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtSN = new JTextField();
		txtSN.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtSN.setBounds(10, 21, 143, 20);
		contentPane.add(txtSN);
		txtSN.setColumns(10);

		lblTireDetail = new JLabel("");
		lblTireDetail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTireDetail.setBounds(177, 116, 380, 34);
		contentPane.add(lblTireDetail);

		lblQg = new JLabel("");
		lblQg.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblQg.setBounds(177, 169, 46, 32);
		contentPane.add(lblQg);

		lblDispatchStatus = new JLabel("");
		lblDispatchStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDispatchStatus.setBounds(177, 228, 66, 22);
		contentPane.add(lblDispatchStatus);

		lblTireSize = new JLabel("Tire Size");
		lblTireSize.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTireSize.setBounds(10, 121, 91, 24);
		contentPane.add(lblTireSize);

		lblQualityGrade = new JLabel("Quality Grade");
		lblQualityGrade.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblQualityGrade.setBounds(10, 173, 136, 24);
		contentPane.add(lblQualityGrade);

		label = new JLabel("Dispatch Status");
		label.setFont(new Font("Tahoma", Font.BOLD, 18));
		label.setBounds(10, 228, 143, 22);
		contentPane.add(label);

		btnEnter = new JButton("Delete Tire");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pid = 0;
				String qg = "";

				String x = txtSN.getText();
				String rem = txtRemarx.getText();

				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					conn.setAutoCommit(false);
					int sn = Integer.parseInt(x);
					// Insesrt new row in to delfrmstk table

					// Getthe pid and qg of the tire , row data for insert the
					// tire
					StkTireBean bean2 = StkTbl.getRow(sn, conn);
					if (bean2 != null) {
						boolean inserted = StkTbl.insertDelfrmStkTbl(bean2, conn, "Removed",rem);
						if (inserted) {

							boolean deleted = StkTbl.DeleteRow(sn, conn);

							if (deleted) {
								conn.commit();
								RefreshGUI();
							} else {
								JOptionPane.showMessageDialog(null, "Not Deleted");
								conn.rollback();
							}
						}else{
							conn.rollback();
						}
					}else{
						conn.rollback();
					}

				} catch (Exception ee) {
					JOptionPane.showMessageDialog(null, ee);
				}
			}
		});
		btnEnter.setBackground(Color.RED);
		btnEnter.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnEnter.setBounds(188, 11, 157, 41);
		contentPane.add(btnEnter);
		btnEnter.setVisible(false);

		btnRefresh = new JButton("Refresh");
		btnRefresh.setBackground(Color.YELLOW);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				RefreshGUI();

			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRefresh.setBounds(308, 63, 149, 41);
		contentPane.add(btnRefresh);

		btnEdcCuttire = new JButton("EDC CutTire");
		btnEdcCuttire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String x = txtSN.getText();
				String rem = txtRemarx.getText();

				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					conn.setAutoCommit(false);
					int sn = Integer.parseInt(x);
					// Insesrt new row in to delfrmstk table

					// Getthe pid and qg of the tire , row data for insert the
					// tire
					StkTireBean bean2 = StkTbl.getRow(sn, conn);
					if (bean2 != null) {
						boolean inserted = StkTbl.insertDelfrmStkTbl(bean2, conn, "EDC Cut Tire",rem);
						if (inserted) {

							boolean Updated = StkTbl.updatetEDCCutTire(sn, conn);

							if (Updated) {
								RefreshGUI();
								conn.commit();
							} else {
								JOptionPane.showMessageDialog(null, "Not Deleted");
								conn.rollback();
							}
						}else{
							conn.rollback();
						}
					}else{
						
					}
				} catch (Exception ee) {
					JOptionPane.showMessageDialog(null, ee);
				}
			}
		});
		btnEdcCuttire.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnEdcCuttire.setBackground(new Color(255, 127, 80));
		btnEdcCuttire.setBounds(358, 11, 149, 41);
		contentPane.add(btnEdcCuttire);
		
		txtRemarx = new JTextField();
		txtRemarx.setBounds(10, 85, 143, 20);
		contentPane.add(txtRemarx);
		txtRemarx.setColumns(10);
		
		JLabel lblRemarks = new JLabel("Remarks");
		lblRemarks.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRemarks.setBounds(10, 62, 112, 14);
		contentPane.add(lblRemarks);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtSN, txtRemarx, btnEnter, btnRefresh, btnEdcCuttire}));
		btnEdcCuttire.setVisible(false);

		txtSN.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				TextChangetxt();

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				TextChangetxt();
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				TextChangetxt();

			}
		});

		txtSN.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				// Change the color of pannel

				char c = e.getKeyChar();
				// Limit the no of charactors ot 9
				if (txtSN.getText().length() >= 9) // limit textfield to 3
													// characters
					e.consume();

				// only numeric inputs taken
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
	}

	private void TextChangetxt() {
		String tireSize = "N/A";
		String lugType = "N/A";
		String config = "N/A";
		String rimSize = "N/A";
		String tireType = "N/A";
		String brandName = "N/A";
		String sWMsg = "N/A";
		String QualityGrade = "";
		int DispatchStatus = 0;
		int pid = 0;
		try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
			String x = txtSN.getText();

			if (x.length() == 9) {

				int sn = Integer.parseInt(x);

				StkTireBean bean2 = StkTbl.getRow(sn, conn);
				if (bean2 != null) {

					pid = bean2.getPid();
					QualityGrade = bean2.getQg();
					DispatchStatus = bean2.getTdispatch();

					////////////////////////////////////////////////////////

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

						}

						String all = tireSize + "" + lugType + "  " + config + " " + rimSize + " " + tireType
								+ brandName + " " + sWMsg;

						lblTireDetail.setText(all);
						lblQg.setText(QualityGrade.toUpperCase());
						switch (DispatchStatus) {

						case 1:
							lblDispatchStatus.setText("IN FG Stores");
							break;
						case 2:
							lblDispatchStatus.setText("Production Area");
							break;
						case 3:
							lblDispatchStatus.setText("Production Area");
							break;
						case 0:
							lblDispatchStatus.setText("Shipped");
							break;
						}

						lblDispatchStatus.setText(DispatchStatus + "");
						btnEnter.setVisible(true);
						btnEdcCuttire.setVisible(true);

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex);
					}
					///////////////////////////////////////////////////////

				} else {
					JOptionPane.showMessageDialog(null, "Wrong SN or SN is not available");
					;
				}

			}
		} catch (Exception e) {
			if (e instanceof NumberFormatException) {

			} else {
				JOptionPane.showMessageDialog(null, e);
			}
		}

	}

	private void RefreshGUI() {

		txtSN.setText("");
		lblTireDetail.setText("");
		lblQg.setText("");
		lblDispatchStatus.setText("");
		txtSN.requestFocus();
		btnEnter.setVisible(false);
		btnEdcCuttire.setVisible(false);
		txtRemarx.setText("");

	}
}
