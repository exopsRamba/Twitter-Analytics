import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.*;
import javax.swing.JScrollPane;
public class Results {

	private JFrame frmResults;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void execute(Tweet[] tweets) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Results window = new Results(tweets);
					window.frmResults.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Results(Tweet[] tweets) {
		initialize(tweets);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Tweet[] tweets) {
		int tweetLength;
		if(tweets[0] == null){
			tweetLength = 0;
		}else{
			tweetLength = tweets.length;
		}
		frmResults = new JFrame();
		frmResults.setTitle("Results");
		frmResults.setBounds(100, 100, 1200, 800);
		frmResults.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Object[][] o = new Object[tweets.length][5];
		for(int y=0; y<tweetLength; y++){
			o[y][0]=tweets[y].getTweetID();
			o[y][1]=tweets[y].getContent();
			o[y][2]=tweets[y].getRetweetCount();
			o[y][3]=tweets[y].getFavoriteCount();
			o[y][4]=tweets[y].getCreatedAt();
		}

		frmResults.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 16, 1163, 728);
		frmResults.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			o,
			new String[] {
				"Tweet ID", "Content", "RT #", "Fav. #", "Date Created"
			}
		));
		TableColumnModel tableModel=table.getColumnModel();
		tableModel.getColumn(0).setPreferredWidth(125);
		tableModel.getColumn(1).setPreferredWidth(700);
		tableModel.getColumn(2).setPreferredWidth(25);
		tableModel.getColumn(3).setPreferredWidth(25);
		tableModel.getColumn(4).setPreferredWidth(150);
	}
}
