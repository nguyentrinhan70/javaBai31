package nguyentrinhan70.example.com.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SinhVienTimKiemUI extends JDialog {
	JTextField txtTim;
	JButton btnBatDauTim;
	DefaultTableModel dtm;
	JTable tblSinhVien;
	
	public SinhVienTimKiemUI(String title){
		this.setTitle(title);
		addControls();
		addEvents();
	}

	public void addEvents() {
		// TODO Auto-generated method stub
		btnBatDauTim.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				xuLyTimKiem();
			}
		});
		
	}

	protected void xuLyTimKiem() {
		// TODO Auto-generated method stub
		try{
			String dataBase = "csdl/dbSinhVien.accdb";
			String strConn = "jdbc:ucanaccess://" + dataBase;
			Connection conn = DriverManager.getConnection(strConn);
			String keyWord = txtTim.getText();
			String sql = "select * from sinhvien where ten like'%"+keyWord+"%' or ma like '%"+keyWord+"'";
			Statement statement = conn.createStatement();
			ResultSet resultSet =statement.executeQuery(sql);
			dtm.setRowCount(0);//Xóa bảng
			while(resultSet.next()){
				Vector<String> vec = new Vector<>();
				vec.add(resultSet.getString("Ma"));
				vec.add(resultSet.getString("ten"));
				vec.add(resultSet.getInt("tuoi")+"");
				dtm.addRow(vec);
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void addControls() {
		// TODO Auto-generated method stub
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		JPanel pnTim = new JPanel();
		JLabel lblTim = new JLabel("Nhập nội dung cần tìm");
		txtTim = new JTextField(20);
		btnBatDauTim = new JButton("Bắt đầu");
		pnTim.add(lblTim);
		pnTim.add(txtTim);
		pnTim.add(btnBatDauTim);
		con.add(pnTim, BorderLayout.NORTH);
		
		dtm = new DefaultTableModel();
		dtm.addColumn("Mã:");
		dtm.addColumn("Tên:");
		dtm.addColumn("Tuổi:");
		tblSinhVien = new JTable(dtm);
		
		JScrollPane sc = new JScrollPane(tblSinhVien, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		con.add(sc, BorderLayout.CENTER);
	}
	public void showWindow(){
		this.setSize(500, 500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setVisible(true);
	}

}
