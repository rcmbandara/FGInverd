package guis;

import java.awt.BorderLayout;
import beans.Spec;
import beans.WorkOrderDetailBean;
import db.DBType;
import db.DBUtil;
import tables.SpecTb;
import tables.WorkOrderDetailManager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.awt.event.ActionEvent;

public class SpecUpdate extends JFrame {

	private JPanel contentPane;
	private JTextField txtSpecID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpecUpdate frame = new SpecUpdate();
					//// IPaddress
					String M_CONN_STRING = "";

					FileReader fr = new FileReader("C:\\Program Files\\MySQL\\MySQL Server 5.7\\DBUtil.txt");
					// FileReader fr = new
					// FileReader("/home/fg-admin/DBUtil/DBUtil.txt");

					BufferedReader br = new BufferedReader(fr);
					int lineNo;

					for (lineNo = 1; lineNo < 10; lineNo++) {
						if (lineNo == 4) {
							M_CONN_STRING = br.readLine();
						} else
							br.readLine();
					}

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
	 */
	public SpecUpdate() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 638, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtSpecID = new JTextField();
		txtSpecID.setBounds(46, 83, 86, 20);
		contentPane.add(txtSpecID);
		txtSpecID.setColumns(10);

		JLabel lblSpecId = new JLabel("Spec ID");
		lblSpecId.setBounds(65, 60, 46, 14);
		contentPane.add(lblSpecId);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					String strspecid = txtSpecID.getText();
					int specid = Integer.parseInt(strspecid);

					Spec bean = SpecTb.getRow(specid, conn);

					if (bean != null) {

						int rndaproval = bean.getRndaproval();
						int edc1stTire = bean.getEdc1sttire();

						if ((rndaproval == 1) && (edc1stTire == 1)) {

							boolean x = SpecTb.UpdateSpec(specid, conn);
							if (x) {
								JOptionPane.showMessageDialog(null, "Done");

							} else {
								JOptionPane.showMessageDialog(null, "Error");
							}

						} else if ((rndaproval == 2) && (edc1stTire == 2)) {

							JOptionPane.showMessageDialog(null, "Regular Tire no need to update");

						} else if ((rndaproval == 0) && (edc1stTire == 0)) {

							JOptionPane.showMessageDialog(null,
									"Pls produce the 1st tire . Then check Flash and update");

						} else {
							JOptionPane.showMessageDialog(null, "Error" + rndaproval + "   " + edc1stTire);
						}
					} else {

					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e+"Error" );
				}

			}
		});
		btnOk.setBounds(188, 79, 89, 23);
		contentPane.add(btnOk);
	}
}
