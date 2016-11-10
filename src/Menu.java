import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class Menu {

	private JFrame frame;
	private JTextField txtSearchKeyword;
	private JTextField txtSearchUsername;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final dataCollector dc = new dataCollector();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 518, 403);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtSearchKeyword = new JTextField();
		txtSearchKeyword.setBounds(15, 58, 170, 31);
		frame.getContentPane().add(txtSearchKeyword);
		txtSearchKeyword.setColumns(10);
		
		txtSearchUsername = new JTextField();
		txtSearchUsername.setBounds(15, 122, 170, 31);
		frame.getContentPane().add(txtSearchUsername);
		txtSearchUsername.setColumns(10);
		
		JRadioButton rdbtnDatabase = new JRadioButton("Database");
		rdbtnDatabase.setMnemonic('B');
		buttonGroup.add(rdbtnDatabase);
		rdbtnDatabase.setBounds(366, 65, 119, 29);
		frame.getContentPane().add(rdbtnDatabase);
		
		JRadioButton rdbtnTwitter = new JRadioButton("Twitter");
		rdbtnTwitter.setMnemonic('A');
		buttonGroup.add(rdbtnTwitter);
		rdbtnTwitter.setBounds(239, 65, 120, 29);
		frame.getContentPane().add(rdbtnTwitter);
		
		JLabel lblSearchFrom = new JLabel("Search From:");
		lblSearchFrom.setBounds(298, 34, 104, 19);
		frame.getContentPane().add(lblSearchFrom);
		

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancel.setBounds(366, 287, 115, 29);
		frame.getContentPane().add(btnCancel);
		
		JLabel lblSearchKeyword = new JLabel("Search Keyword:");
		lblSearchKeyword.setBounds(36, 33, 120, 20);
		frame.getContentPane().add(lblSearchKeyword);
		
		JLabel lblSearchUsername = new JLabel("Search Username:");
		lblSearchUsername.setBounds(34, 97, 135, 20);
		frame.getContentPane().add(lblSearchUsername);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "5", "10", "20", "50", "100", "500"}));
		comboBox.setBounds(138, 185, 47, 26);
		frame.getContentPane().add(comboBox);
		
		JLabel lblDesiredResults = new JLabel("Desired Results:");
		lblDesiredResults.setBounds(15, 184, 120, 29);
		frame.getContentPane().add(lblDesiredResults);
		
		JCheckBox chckbxFilterProfanity = new JCheckBox("Show Profanity");
		chckbxFilterProfanity.setSelected(true);
		chckbxFilterProfanity.setBounds(358, 184, 139, 29);
		frame.getContentPane().add(chckbxFilterProfanity);
		
		JButton btnRunSearch = new JButton("Run Search");
		btnRunSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					int loc = 0;
					int resultCount = 0;
					boolean showProfan;
					try{
						loc = buttonGroup.getSelection().getMnemonic();
					}catch(Exception e){JOptionPane.showMessageDialog(null,"You have an empty field.", "Error!", JOptionPane.ERROR_MESSAGE); return;}
					String keyword = txtSearchKeyword.getText();
					String username = txtSearchUsername.getText();
					try{
						resultCount = Integer.parseInt(comboBox.getSelectedItem().toString());
					}catch(Exception e){JOptionPane.showMessageDialog(null,"You have an empty field.", "Error!", JOptionPane.ERROR_MESSAGE);return;}
					
					try{
						showProfan = chckbxFilterProfanity.isSelected();
					}catch(Exception e){JOptionPane.showMessageDialog(null,"You have an empty field.", "Error!", JOptionPane.ERROR_MESSAGE);return;}
					
					dc.initialize(loc, keyword, username, resultCount, showProfan);
					
			}
		});
		btnRunSearch.setBounds(366, 242, 115, 29);
		frame.getContentPane().add(btnRunSearch);
		
		JTextArea txtrwhenSearchingThe = new JTextArea();
		txtrwhenSearchingThe.setWrapStyleWord(true);
		txtrwhenSearchingThe.setBackground(UIManager.getColor("Panel.background"));
		txtrwhenSearchingThe.setLineWrap(true);
		txtrwhenSearchingThe.setText("*When searching the Database you may seach by Username and Keyword simultaneously.");
		txtrwhenSearchingThe.setBounds(15, 307, 344, 40);
		frame.getContentPane().add(txtrwhenSearchingThe);
		

		
	}
}
