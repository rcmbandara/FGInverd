package guis;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class BarCodePrinting extends JFrame {

	private JPanel contentPane;
	private JTextField txtSN;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BarCodePrinting frame = new BarCodePrinting();
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
	public BarCodePrinting() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(-10, -10, 1380, 790);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtSN = new JTextField();
		txtSN.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSN.setBounds(41, 26, 291, 39);
		contentPane.add(txtSN);
		txtSN.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(41, 244, 577, 471);
		contentPane.add(panel);
		
		JLabel label = new JLabel("Tire Size");
		label.setForeground(Color.BLUE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label.setBounds(34, 24, 114, 20);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Lug Type");
		label_1.setForeground(Color.BLUE);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_1.setBounds(34, 68, 114, 20);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Configuration");
		label_2.setForeground(Color.BLUE);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_2.setBounds(34, 112, 114, 20);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("Rim Size");
		label_3.setForeground(Color.BLUE);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_3.setBounds(34, 156, 114, 20);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("Tire Type");
		label_4.setForeground(Color.BLUE);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_4.setBounds(34, 200, 114, 20);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("Brand Name");
		label_5.setForeground(Color.BLUE);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_5.setBounds(34, 244, 114, 20);
		panel.add(label_5);
		
		JLabel label_6 = new JLabel("SideWall Msg");
		label_6.setForeground(Color.BLUE);
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_6.setBounds(34, 288, 114, 20);
		panel.add(label_6);
		
		JLabel label_7 = new JLabel("PID");
		label_7.setForeground(Color.BLUE);
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_7.setBounds(34, 332, 114, 20);
		panel.add(label_7);
		
		JLabel label_8 = new JLabel("Quality Grade");
		label_8.setForeground(Color.BLUE);
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_8.setBounds(34, 376, 114, 20);
		panel.add(label_8);
		
		JLabel label_9 = new JLabel("SN");
		label_9.setForeground(Color.BLUE);
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_9.setBounds(34, 420, 114, 20);
		panel.add(label_9);
		
		JLabel lblTireSize = new JLabel("---------");
		lblTireSize.setForeground(Color.BLUE);
		lblTireSize.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTireSize.setBounds(237, 24, 223, 20);
		panel.add(lblTireSize);
		
		JLabel lblLugType = new JLabel("---------");
		lblLugType.setForeground(Color.BLUE);
		lblLugType.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLugType.setBounds(237, 68, 223, 20);
		panel.add(lblLugType);
		
		JLabel lblConfig = new JLabel("---------");
		lblConfig.setForeground(Color.BLUE);
		lblConfig.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblConfig.setBounds(237, 118, 223, 20);
		panel.add(lblConfig);
		
		JLabel lblRimSize = new JLabel("---------");
		lblRimSize.setForeground(Color.BLUE);
		lblRimSize.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRimSize.setBounds(237, 162, 223, 20);
		panel.add(lblRimSize);
		
		JLabel lblTireType = new JLabel("---------");
		lblTireType.setForeground(Color.BLUE);
		lblTireType.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTireType.setBounds(237, 206, 223, 20);
		panel.add(lblTireType);
		
		JLabel lblBrandName = new JLabel("---------");
		lblBrandName.setForeground(Color.BLUE);
		lblBrandName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBrandName.setBounds(237, 250, 223, 20);
		panel.add(lblBrandName);
		
		JLabel lblSWMsg = new JLabel("---------");
		lblSWMsg.setForeground(Color.BLUE);
		lblSWMsg.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSWMsg.setBounds(237, 294, 223, 20);
		panel.add(lblSWMsg);
		
		JLabel lblPID = new JLabel("---------");
		lblPID.setForeground(Color.BLUE);
		lblPID.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPID.setBounds(237, 338, 223, 20);
		panel.add(lblPID);
		
		JLabel lblQG = new JLabel("---------");
		lblQG.setForeground(Color.BLUE);
		lblQG.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblQG.setBounds(237, 382, 223, 20);
		panel.add(lblQG);
		
		JLabel lblSN = new JLabel("---------");
		lblSN.setForeground(Color.BLUE);
		lblSN.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSN.setBounds(237, 420, 223, 20);
		panel.add(lblSN);
		
		JLabel label_20 = new JLabel("-----------------------------------");
		label_20.setForeground(new Color(0, 0, 205));
		label_20.setFont(new Font("Tahoma", Font.PLAIN, 35));
		label_20.setBounds(57, 202, 678, 14);
		contentPane.add(label_20);
		
		JLabel label_21 = new JLabel("-----------------------------------");
		label_21.setForeground(new Color(0, 0, 128));
		label_21.setFont(new Font("Tahoma", Font.PLAIN, 35));
		label_21.setBounds(57, 155, 678, 14);
		contentPane.add(label_21);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(779, 76, 524, 556);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JCheckBox chbLDamage = new JCheckBox("Lug Damage");
		chbLDamage.setBounds(368, 21, 97, 23);
		panel_1.add(chbLDamage);
		
		JCheckBox chbBeadOut = new JCheckBox("Bead Out");
		chbBeadOut.setBounds(18, 72, 97, 23);
		panel_1.add(chbBeadOut);
		
		JCheckBox chbBG = new JCheckBox("Back Grinding");
		chbBG.setBounds(18, 111, 97, 23);
		panel_1.add(chbBG);
		
		JCheckBox checkBox_2 = new JCheckBox("Base Flow Marks");
		checkBox_2.setBounds(18, 157, 97, 23);
		panel_1.add(checkBox_2);
		
		JCheckBox checkBox_3 = new JCheckBox("TR Damage");
		checkBox_3.setBounds(18, 205, 97, 23);
		panel_1.add(checkBox_3);
		
		JCheckBox checkBox_4 = new JCheckBox("Lug Damage");
		checkBox_4.setBounds(18, 256, 97, 23);
		panel_1.add(checkBox_4);
		
		JCheckBox checkBox_5 = new JCheckBox("Lug Damage");
		checkBox_5.setBounds(18, 293, 97, 23);
		panel_1.add(checkBox_5);
		
		JCheckBox checkBox_6 = new JCheckBox("Lug Damage");
		checkBox_6.setBounds(18, 334, 97, 23);
		panel_1.add(checkBox_6);
		
		JCheckBox checkBox_7 = new JCheckBox("Lug Damage");
		checkBox_7.setBounds(18, 380, 97, 23);
		panel_1.add(checkBox_7);
		
		JCheckBox checkBox_8 = new JCheckBox("Lug Damage");
		checkBox_8.setBounds(18, 426, 97, 23);
		panel_1.add(checkBox_8);
		
		JCheckBox checkBox_9 = new JCheckBox("Lug Damage");
		checkBox_9.setBounds(18, 479, 97, 23);
		panel_1.add(checkBox_9);
	}
}
