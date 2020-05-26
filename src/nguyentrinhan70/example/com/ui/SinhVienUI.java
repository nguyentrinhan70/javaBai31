package nguyentrinhan70.example.com.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SinhVienUI extends JFrame {
	Connection conn = null;

	DefaultTableModel dtm;
	JTable tblSinhVien;

	JTextField txtMa, txtTen, txtTuoi;
	JButton btnLuu, btnXoaTrang, btnXoaSinhVien, btnTimKiem;
	public SinhVienUI(String title){
		super(title);
		addControls();
		addEvents();
		hienThiDanhSachSinhVien();

	}

	public void addControls() {
		Container con = getContentPane();
		con.setLayout(new BorderLayout());

		dtm = new DefaultTableModel();
		dtm.addColumn("Mã sinh viên");
		dtm.addColumn("Tên sinh viên");
		dtm.addColumn("Tuổi sinh viên");
		tblSinhVien = new JTable(dtm);

		JScrollPane sc = new JScrollPane(tblSinhVien,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		con.add(sc, BorderLayout.CENTER);

		JPanel pnChiTiet = new JPanel();
		pnChiTiet.setLayout(new BoxLayout(pnChiTiet, BoxLayout.Y_AXIS));
		con.add(pnChiTiet, BorderLayout.SOUTH);

		JPanel pnMa = new JPanel();
		pnMa.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblMa = new JLabel("Mã: ");
		txtMa = new JTextField(20);
		pnMa.add(lblMa);
		pnMa.add(txtMa);
		pnChiTiet.add(pnMa);

		JPanel pnTen = new JPanel();
		pnTen.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTen = new JLabel("Tên: ");
		txtTen = new JTextField(20);
		pnTen.add(lblTen);
		pnTen.add(txtTen);
		pnChiTiet.add(pnTen);

		JPanel pnTuoi=new JPanel();
		pnTuoi.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTuoi=new JLabel("Tuổi:");
		txtTuoi=new JTextField(20);
		pnTuoi.add(lblTuoi);
		pnTuoi.add(txtTuoi);
		pnChiTiet.add(pnTuoi);

		JPanel pnButton = new JPanel();
		pnButton.setLayout(new FlowLayout(FlowLayout.LEFT));

		btnLuu = new JButton("Lưu mới sinh viên");
		btnXoaTrang = new JButton("Xóa trắng");
		btnXoaSinhVien = new JButton("Xóa sinh viên");
		btnTimKiem = new JButton("Tìm kiếm sinh viên");

		pnButton.add(btnLuu);
		pnButton.add(btnXoaTrang);
		pnButton.add(btnXoaSinhVien);
		pnButton.add(btnTimKiem);
		pnChiTiet.add(pnButton);
		
		lblMa.setPreferredSize(lblTuoi.getPreferredSize());
		lblTen.setPreferredSize(lblTuoi .getPreferredSize());

	}

	public void addEvents() {
		// TODO Auto-generated method stub
		btnLuu.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				xuLyLuuMoiSinhVien();

			}
		});

		tblSinhVien.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int row = tblSinhVien.getSelectedRow();
				if(row ==-1) return;
				String ma = tblSinhVien.getValueAt(row, 0)+"";
				hienThiDanhSachSinhVien(ma);

			}
		});
		btnXoaTrang.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				txtMa.setText("");
				txtTen.setText("");
				txtTuoi.setText("");
				txtMa.requestFocus();
			}
		});
		
		btnXoaSinhVien.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				xuLyXoaSinhVien();
			}
		});
		btnTimKiem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				SinhVienTimKiemUI ui = new SinhVienTimKiemUI("Tìm kiếm sinh viên");
				ui.showWindow();
			}
		});
	}
	protected void xuLyXoaSinhVien() {
		int ret = JOptionPane.showConfirmDialog(null, "Bạn có muốn chắc chắn xóa ['"+txtMa.getText()+"' ]"
				+ "này không?"," xác nhận xóa ",JOptionPane.YES_NO_OPTION);
		if(ret==JOptionPane.YES_OPTION){
			try{
				String sqlDelete =  "delete from sinhvien where ma ='"+txtMa.getText()+"'";
				Statement statement = conn.createStatement();
				int kq = statement.executeUpdate(sqlDelete);
				if(kq>0){
					hienThiDanhSachSinhVien();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
	}

	public boolean	 kiemTraMaTonTai(String ma) {
		try{
			if(conn==null) return false;
			Statement statement = conn.createStatement();
			String sql = "Select * from sinhvien where ma ='"+ma+"'";
			ResultSet resultSet = statement.executeQuery(sql);
			if(resultSet.next()){
				return true;
			}

		}catch(Exception ex){

		}

		return false;
	}
	protected void hienThiDanhSachSinhVien(String ma) {
		// TODO Auto-generated method stub

		try{
			if(conn==null) return;
			Statement statement = conn.createStatement();
			String sql = "Select * from sinhvien where ma ='"+ma+"'";
			ResultSet resultSet = statement.executeQuery(sql);
			if(resultSet.next()){
				txtMa.setText(resultSet.getString("ma"));
				txtTen.setText(resultSet.getString("ten"));
				txtTuoi.setText(resultSet.getInt("tuoi")+"");
			}

		}catch(Exception ex){

		}
	}


	protected void xuLyLuuMoiSinhVien() {
		// TODO Auto-generated method stub
		if(kiemTraMaTonTai(txtMa.getText())==false)//chưa có mã
		{
			if(conn==null)return;
			try
			{
				Statement statement=conn.createStatement();
				String sqlInsert="insert into sinhvien(ma,ten,tuoi) "
						+ "values('"+txtMa.getText()+"','"+txtTen.getText()+"',"+txtTuoi.getText()+")";
				int kq=statement.executeUpdate(sqlInsert);
				if(kq<0)
				{
					JOptionPane.showMessageDialog(null, "Thêm mới sinh viên thất bại");
				}
				else
				{
					hienThiDanhSachSinhVien();
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else{
			{
				try{
					if(conn==null) return;
					Statement statement = conn.createStatement();
					String sqlUpdate = "update sinhvien set ten ='"+txtTen.getText()+"',tuoi = "+txtTuoi.getText()+" "
							+ "where ma = '"+txtMa.getText()+"' ";
					ResultSet resultSet = statement.executeQuery(sqlUpdate);
					if(resultSet.next()){
						txtMa.setText(resultSet.getString("ma"));
						txtTen.setText(resultSet.getString("ten"));
						txtTuoi.setText(resultSet.getInt("tuoi")+"");
					}

				}catch(Exception ex){

				}

			}
		}
	}
	public void showWindow(){
		this.setSize(800, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}
	public void hienThiDanhSachSinhVien(){
		try{
			String dataBase = "csdl/dbSinhVien.accdb";
			String strConn = "jdbc:ucanaccess://"+dataBase;
			conn= DriverManager.getConnection(strConn);

			if(conn!=null){
				java.sql.Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery("select * from sinhvien");
				dtm.setRowCount(0);//xóa dữ liệu cũ
				while(resultSet.next()){
					String ma =resultSet.getString("Ma");
					String ten = resultSet.getString("Ten");
					int tuoi =resultSet.getInt("Tuoi");
					Object []arr = {ma,ten,tuoi};
					dtm.addRow(arr);
				}

			}
		}catch(Exception ex){
			ex.printStackTrace();

		}
	}
}



