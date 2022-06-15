import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Patient {

	private JFrame frame;
	private JTextField txtPno;
	private JTextField txtPname;
	private JTextField txtPAge;
	private JTextField txtDno;
	private JTextField textField;

	//Launching the application
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Patient window = new Patient();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Patient() {
		initialize();
		Connect();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void Connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:" + DatabaseInfo.SID, "system", "subin2172001");
		}
		catch (ClassNotFoundException ex){
			ex.printStackTrace();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public void table_load() {
        try  {
            pst = con.prepareStatement("select * from PATIENT");
            rs = pst.executeQuery();
            //table.setModel(DbUtils.resultSetToTableModel(rs));
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	//Initializing the contents of the frame
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("PATIENT");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel.setBounds(190, 23, 65, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Out Patient", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 48, 240, 165);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("PNO");
		lblNewLabel_1.setBounds(23, 29, 63, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("PNAME");
		lblNewLabel_1_1.setBounds(23, 62, 63, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("AGE");
		lblNewLabel_1_2.setBounds(23, 97, 63, 14);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("DNO");
		lblNewLabel_1_3.setBounds(23, 131, 63, 14);
		panel.add(lblNewLabel_1_3);
		
		txtPno = new JTextField();
		txtPno.setBounds(96, 26, 108, 20);
		panel.add(txtPno);
		txtPno.setColumns(10);
		
		txtPname = new JTextField();
		txtPname.setColumns(10);
		txtPname.setBounds(96, 59, 108, 20);
		panel.add(txtPname);
		
		txtPAge = new JTextField();
		txtPAge.setColumns(10);
		txtPAge.setBounds(96, 94, 108, 20);
		panel.add(txtPAge);
		
		txtDno = new JTextField();
		txtDno.setColumns(10);
		txtDno.setBounds(96, 128, 108, 20);
		panel.add(txtDno);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
            //insert
			public void actionPerformed(ActionEvent e) {
				String Pno,Pname,PAge,Dno;
	        	Pno = txtPno.getText();
	    		Pname = txtPname.getText();
	    		PAge = txtPAge.getText();
        		Dno = txtDno.getText();
	        	try {
	        		pst = con.prepareStatement("insert into PATIENT(PNO,PNAME,PAGE,DNO)values(?,?,?,?)");
	        		pst.setString(1, Pno);
	        		pst.setString(2, Pname);
            		pst.setString(3, PAge);
	            	pst.setString(4, Dno);
	            	pst.executeUpdate();
	        		JOptionPane.showMessageDialog(null, "Record Added.");
	        		table_load();
	          
            		txtPno.setText("");
	            	txtPname.setText("");
	            	txtPAge.setText("");
	        		txtDno.setText("");
	        		txtPno.requestFocus();
	   	 		}
        		catch (SQLException e1) {
	            	e1.printStackTrace();
	        	}
			}
        });
		btnNewButton.setBounds(301, 48, 89, 30);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExit.setBounds(301, 85, 89, 30);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnClear.setBounds(301, 126, 89, 30);
		frame.getContentPane().add(btnClear);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
            //update
			public void actionPerformed(ActionEvent e) {
				String Pno,Pname;
        		Pno = txtPno.getText();
        		Pname = txtPname.getText();
        		try {
            		pst = con.prepareStatement("update PATIENT set PNAME= ? where PNO =?");
            		pst.setString(1, Pname);
            		pst.setString(2, Pno);
            		pst.executeUpdate();
            		JOptionPane.showMessageDialog(null, "Record Updated.");
            		table_load();
          
            		txtPno.setText("");
            		txtPname.setText("");
            		txtPAge.setText("");
            		txtDno.setText("");
            		txtPno.requestFocus();
        		}
        		catch (SQLException e1) {
            		e1.printStackTrace();
        		}
			}
		});
		btnUpdate.setBounds(301, 165, 89, 30);
		frame.getContentPane().add(btnUpdate);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(20, 214, 224, 47);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_4 = new JLabel("PNO");
		lblNewLabel_1_4.setBounds(26, 14, 47, 22);
		panel_1.add(lblNewLabel_1_4);
		
		textField = new JTextField();
        textField.addKeyListener(new KeyAdapter() {
            //select
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String id = textField.getText();
					pst = con.prepareStatement("select PNO, PNAME, PAGE, DNO from PATIENT where PNO = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					if(rs.next() == true) {
						String Pno = rs.getString(1);
						String Pname = rs.getString(2);
						String PAge = rs.getString(3);
                        String Dno = rs.getString(4);

						txtPno.setText(Pno);
						txtPname.setText(Pname);
						txtPAge.setText(PAge);
                        txtDno.setText(Dno);
					}
					else {
						txtPno.setText("");
						txtPname.setText("");
						txtPAge.setText("");
                        txtDno.setText("");
					}
				}
				catch (SQLException e2) {
            		e2.printStackTrace();
        		}
			}
		});
		textField.setColumns(10);
		textField.setBounds(83, 16, 115, 20);
		panel_1.add(textField);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
            //delete
			public void actionPerformed(ActionEvent e) {
				String bid;
    			bid  = textField.getText();
    			try {
        			pst = con.prepareStatement("delete from PATIENT where PNO =?");
       			 	pst.setString(1, bid);
        			pst.executeUpdate();
        			JOptionPane.showMessageDialog(null, "Record Deleted.");
        			table_load();
       
        			txtPno.setText("");
        			txtPname.setText("");
        			txtPAge.setText("");
        			txtDno.setText("");
        			txtPno.requestFocus();
    			}
    			catch (SQLException e1) {    
        			e1.printStackTrace();
    			}
			}
		});
		btnDelete.setBounds(301, 206, 89, 30);
		frame.getContentPane().add(btnDelete);
	}
}