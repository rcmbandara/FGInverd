package guis;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import beans.hrtrackBean;
import db.DBType;
import db.DBUtil;
import tables.BuilderTbl;
import tables.hrtrackTbl;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;

public class ProductionReport extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductionReport frame = new ProductionReport();
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

	public ProductionReport() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int stHr;
				int endHr ;
				
				Date date = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);

				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				date = cal.getTime();
				
				DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
				String endDate = readFormat.format(date);
				endHr = cal.get(cal.HOUR_OF_DAY);//GetEndHr
				// cal.add(Calendar.HOUR, -1);
				cal.add(cal.HOUR, -0);
				date = cal.getTime();
				String stDate = readFormat.format(date);
				stHr = cal.get(cal.HOUR_OF_DAY);//GetStartHr
				String timerange = ""+stHr+"-"+endHr;
				
				try (Connection conn = DBUtil.getConnection(DBType.POSTGRESQL);) {
					// Trunckat the hrtrack tbl
					hrtrackTbl.TruncatehrtrackTbl(conn);

					hrtrackBean bean = new hrtrackBean();
					
					
					bean = BuilderTbl.getRow(stDate, endDate,timerange, conn);
					
					System.out.println(timerange);
					System.out.println(bean.getNos()+"");
					System.out.println(bean.getWgt());
					

					

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e + "Pro Rep E1");
				}
			}
		});
		btnNewButton.setBounds(93, 116, 89, 23);
		contentPane.add(btnNewButton);
	}
}
