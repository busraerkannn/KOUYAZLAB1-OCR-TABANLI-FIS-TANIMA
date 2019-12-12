package Proje;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

public class Arayuz extends JFrame {

	private JPanel contentPane;
	private JTextField text_IsletmeAd;
	private JTextField text_Tarih;
	private JTable table;
	
	public String img;
	
	DefaultTableModel modelim = new DefaultTableModel();
	Object[] kolonlar = {"İşletme Adı","Tarih","Fiş No","Ürün Adı","Ürün KDV","Ürün Fiyat","Toplam"};
	Object[] satirlar = new Object[7];
	
	ArrayList<String> veri = new ArrayList<String>();
	ArrayList<String> urun = new ArrayList<String>();
	ArrayList<String> kdv = new ArrayList<String>();
	ArrayList<String> fiyat = new ArrayList<String>();

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Arayuz frame = new Arayuz();
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
	
	JButton btn2;
	private JTable table_1;
	private JTable table_2;
	private JTable tablo;
	
	public void  fisYukle(String img)
    {
		JButton button = new JButton(new ImageIcon(((new ImageIcon(img)).getImage()).getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH)));
		button.setBounds(71, 26, 367, 405);
		contentPane.add(button);
    }
	
	public Arayuz() {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 800);
		setLocation(300, 100);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(95, 158, 160));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	    JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(71, 26, 350, 400);
		contentPane.add(scrollPane);
		scrollPane.setSize(350, 400);
	
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(81, 495, 826, 234);
		//scrollPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		//scrollPane_1.setLayout(null);
		contentPane.add(scrollPane_1);
		
		
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(521, 26, 376, 400);
		contentPane.add(scrollPane_2);
		
		JList list = new JList();
		list.setFont(new Font("Tahoma", Font.BOLD, 16));
		scrollPane_2.setViewportView(list);
		
		JLabel lblNewLabel = new JLabel("ARAMA");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(1064, 57, 77, 21);
		contentPane.add(lblNewLabel);
		
		text_IsletmeAd = new JTextField();
		text_IsletmeAd.setBounds(1113, 117, 116, 22);
		contentPane.add(text_IsletmeAd);
		text_IsletmeAd.setColumns(10);
		
		text_Tarih = new JTextField();
		text_Tarih.setBounds(1113, 174, 116, 22);
		contentPane.add(text_Tarih);
		text_Tarih.setColumns(10);
		
		JLabel lbl_IsletmeAd = new JLabel("İşletme Adı");
		lbl_IsletmeAd.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_IsletmeAd.setBounds(976, 117, 121, 21);
		contentPane.add(lbl_IsletmeAd);
		
		JLabel lbl_Tarih = new JLabel("Tarih");
		lbl_Tarih.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_Tarih.setBounds(1024, 174, 77, 21);
		contentPane.add(lbl_Tarih);

		JButton btnFis = new JButton("Fiş Seç");
		btnFis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				int returnValue = fileChooser.showOpenDialog(null);
				File selectedFile=null;

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					
					selectedFile = fileChooser.getSelectedFile();
					//System.out.println(selectedFile.getAbsolutePath());
					img=selectedFile.getAbsolutePath();
					scrollPane.setVisible(false);
					fisYukle(img);						
				}
				
			
			}
		});
		
		btnFis.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnFis.setBounds(192, 439, 97, 38);
		contentPane.add(btnFis);
		
		tablo = new JTable();
		modelim.setColumnIdentifiers(kolonlar);
		tablo.setBounds(81, 495, 826, 234);
		scrollPane_1.setViewportView(tablo);
		
		JButton btnTablo = new JButton("Tablo");
		btnTablo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				modelim.setRowCount(0);
				ResultSet myRs = VeriBaglanti.baglan();
				
				try {
					
					while(myRs.next())
					{
						satirlar[0] = myRs.getString("isletmeAdi");
						satirlar[1] = myRs.getString("tarih");
						satirlar[2] = myRs.getString("fisNo");
						satirlar[3] = myRs.getString("urunAdi");
						satirlar[4] = myRs.getString("urunKDV");
						satirlar[5] = myRs.getString("urunFiyat");
						satirlar[6] = myRs.getString("toplam");
						
						modelim.addRow(satirlar);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				tablo.setModel(modelim);
				
			}
		});
		btnTablo.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTablo.setBounds(953, 579, 128, 38);
		contentPane.add(btnTablo);
		
		
		JButton btnVeriEkle = new JButton("Veri Ekle");
		btnVeriEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String sql_sorgu, ad,tarih,fisNo,urunAdi,urunKDV,urunFiyat,toplam;
				
				/*ad = veri.get(0);
				tarih = veri.get(1);
				fisNo = veri.get(2);
				urunAdi = urun.get(1);
				urunKDV = kdv.get(1);
				urunFiyat = fiyat.get(1);
				toplam = veri.get(1);
				
				sql_sorgu = "INSERT INTO fisveri (isletmeAdi,tarih,fisNo,urunAdi,urunKDV,urunFiyat,toplam) VALUES (" + "'" +  ad +  "'," + "'" + tarih + "','" + fisNo + "','" + urunAdi + "','"  + urunKDV + "','" + urunFiyat + "','" + toplam + "')";
      			VeriBaglanti.ekle(sql_sorgu);*/

				ad = veri.get(0);
				tarih = veri.get(1);
				fisNo = veri.get(2);
				toplam = veri.get(3);
	
				for (int i = 0; i < urun.size(); i++) {

					urunAdi = urun.get(i);
					urunKDV = kdv.get(i);
					urunFiyat = fiyat.get(i);
					
					sql_sorgu = "INSERT INTO fisveri (isletmeAdi,tarih,fisNo,urunAdi,urunKDV,urunFiyat,toplam) VALUES (" + "'" +  ad +  "'," + "'" + tarih + "','" + fisNo + "','" + urunAdi + "','"  + urunKDV + "','" + urunFiyat + "','" + toplam + "')";
					//System.out.println(sql_sorgu);
					
					try {
		      			VeriBaglanti.ekle(sql_sorgu);
						//Thread.sleep(5000);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
				
				
			}
		});
		btnVeriEkle.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnVeriEkle.setBounds(632, 439, 121, 38);
		contentPane.add(btnVeriEkle);

		JButton btn_Secim = new JButton("Ara");
		btn_Secim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								
				String bilgi = null;
				String isletmeAdi = null;
				String tarih = null;
				isletmeAdi = text_IsletmeAd.getText();
				tarih = text_Tarih.getText();

				ResultSet myRs = null;
				
				if(tarih.isEmpty())
				{
					bilgi = isletmeAdi;
					modelim.setRowCount(0);		
					myRs = VeriBaglanti.araIsletmeAdi(bilgi);

					try {
						while(myRs.next())
						{
							satirlar[0] = myRs.getString("isletmeAdi");
							satirlar[1] = myRs.getString("tarih");
							satirlar[2] = myRs.getString("fisNo");
							satirlar[3] = myRs.getString("urunAdi");
							satirlar[4] = myRs.getString("urunKDV");
							satirlar[5] = myRs.getString("urunFiyat");
							satirlar[6] = myRs.getString("toplam");
							
							modelim.addRow(satirlar);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					table_1.setModel(modelim);
				}
				
				if(isletmeAdi.isEmpty())
				{
					bilgi = tarih;
					modelim.setRowCount(0);		
					myRs = VeriBaglanti.araTarih(bilgi);

					try {
						while(myRs.next())
						{
							satirlar[0] = myRs.getString("isletmeAdi");
							satirlar[1] = myRs.getString("tarih");
							satirlar[2] = myRs.getString("fisNo");
							satirlar[3] = myRs.getString("urunAdi");
							satirlar[4] = myRs.getString("urunKDV");
							satirlar[5] = myRs.getString("urunFiyat");
							satirlar[6] = myRs.getString("toplam");
							
							modelim.addRow(satirlar);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					table_1.setModel(modelim);
				}
			}
		});
		
		btn_Secim.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_Secim.setBounds(1056, 240, 97, 41);
		contentPane.add(btn_Secim);
		
		JButton btnParseEt = new JButton("Liste");
		btnParseEt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
				Thread.sleep(5000);
				DefaultListModel DLM = new DefaultListModel();
				/*String isletmeAdi = veri.get(0);
				String tarih= veri.get(1);
				String fisNo = veri.get(0);*/
				DLM.addElement(veri.get(0));
				DLM.addElement(veri.get(1));
				DLM.addElement(veri.get(2));
				
				for (int i = 0; i < urun.size(); i++) {
					DLM.addElement(urun.get(i));
					DLM.addElement(kdv.get(i));
					DLM.addElement(fiyat.get(i));
				}
				
				DLM.addElement(veri.get(3));
				list.setModel(DLM);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		});
		btnParseEt.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnParseEt.setBounds(412, 439, 97, 38);
		contentPane.add(btnParseEt);
		
		

	}
}
