import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Doctor {

	private JFrame frame;
	private JTextField txtDno;
	private JTextField txtDname;
	private JTextField txtID;
	private JTable table;
	private JTextField txtSearchDNO;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Doctor window = new Doctor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//form constructor
    public Doctor() {
        initialize();
        Connect();
        table_load();
    }

	Connection con;
    PreparedStatement pst;
    ResultSet rs;

	public void Connect() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:" + DatabaseInfo.SID, "system", "subin2172001");
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }   
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

	public void table_load() {
        try  {
            pst = con.prepareStatement("select * from DOCTOR");
            rs = pst.executeQuery();
            //table.setModel(DbUtils.resultSetToTableModel(rs));
        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DOCTOR");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel.setBounds(184, 11, 68, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 50, 206, 112);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("DNO");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 26, 73, 20);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("DNAME");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setBounds(10, 53, 73, 20);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("ID");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setBounds(10, 79, 73, 20);
		panel.add(lblNewLabel_1_2);
		
		txtDno = new JTextField();
		txtDno.setBounds(93, 26, 86, 20);
		panel.add(txtDno);
		txtDno.setColumns(10);
		
		txtDname = new JTextField();
		txtDname.setColumns(10);
		txtDname.setBounds(93, 53, 86, 20);
		panel.add(txtDname);
		
		txtID = new JTextField();
		txtID.setColumns(10);
		txtID.setBounds(93, 79, 86, 20);
		panel.add(txtID);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(226, 57, 198, 146);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			//insert
			public void actionPerformed(ActionEvent e) {
				String Dno,Dname,ID;
        		Dno = txtDno.getText();
        		Dname = txtDname.getText();
        		ID = txtID.getText();
        		try {
            		pst = con.prepareStatement("insert into DEPT(DNO,DNAME,ID)values(?,?,?)");
            		pst.setString(1, Dno);
            		pst.setString(2, Dname);
            		pst.setString(3, ID);
            		pst.executeUpdate();
            		JOptionPane.showMessageDialog(null, "Record Added.");
            		table_load();
              
            		txtDno.setText("");
            		txtDname.setText("");
            		txtID.setText("");
            		txtDno.requestFocus();
       	 		}
        		catch (SQLException e1) {
            		e1.printStackTrace();
        		}
			}
		});
		btnNewButton.setBounds(10, 173, 66, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExit.setBounds(78, 173, 68, 23);
		frame.getContentPane().add(btnExit);
		
		JButton btnNewButton_1_1 = new JButton("Clear");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1_1.setBounds(148, 173, 68, 23);
		frame.getContentPane().add(btnNewButton_1_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 207, 206, 43);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("DNO");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setBounds(10, 17, 54, 14);
		panel_1.add(lblNewLabel_1_1_1);
		
		txtSearchDNO = new JTextField();
		txtSearchDNO.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String id = txtSearchDNO.getText();
					pst = con.prepareStatement("select DNO, DNAME, ID from DEPT where DNO = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					if(rs.next() == true) {
						String Dno = rs.getString(1);
						String Dname = rs.getString(2);
						String ID = rs.getString(3);

						txtDno.setText(Dno);
						txtDname.setText(Dname);
						txtID.setText(ID);
					}
					else {
						txtDno.setText("");
						txtDname.setText("");
						txtID.setText("");
					}
				}
				catch (SQLException e2) {
            		e2.printStackTrace();
        		}
			}
		});
		txtSearchDNO.setColumns(10);
		txtSearchDNO.setBounds(86, 14, 95, 20);
		panel_1.add(txtSearchDNO);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			//update
			public void actionPerformed(ActionEvent e) {
				String Dno,Dname;
        		Dno = txtDno.getText();
        		Dname = txtDname.getText();
        		try {
            		pst = con.prepareStatement("update DEPT set DNAME= ? where DNO =?");
            		pst.setString(1, Dname);
            		pst.setString(2, Dno);
            		pst.executeUpdate();
            		JOptionPane.showMessageDialog(null, "Record Updated.");
            		table_load();
          
            		txtDno.setText("");
            		txtDname.setText("");
            		txtID.setText("");
            		txtDno.requestFocus();
        		}
        		catch (SQLException e1) {
            		e1.printStackTrace();
        		}
			}
		});
		btnUpdate.setBounds(238, 216, 82, 23);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			//delete
			public void actionPerformed(ActionEvent e) {
				String bid;
    			bid  = txtSearchDNO.getText();
    			try {
        			pst = con.prepareStatement("delete from DEPT where DNO =?");
       			 	pst.setString(1, bid);
        			pst.executeUpdate();
        			JOptionPane.showMessageDialog(null, "Record Deleted.");
        			table_load();
       
        			txtDno.setText("");
        			txtDname.setText("");
        			txtID.setText("");
        			txtDno.requestFocus();
    			}
    			catch (SQLException e1) {    
        			e1.printStackTrace();
    			}
			}
		});
		btnDelete.setBounds(333, 216, 82, 23);
		frame.getContentPane().add(btnDelete);
	}
}