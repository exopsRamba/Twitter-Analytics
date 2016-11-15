import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.text.DateFormat;
import java.sql.*;


public class SQLInstance {
	String dbAddress;
	String dbName;
	String userName;
	String password;
	
	public SQLInstance(String ad, String name, String user, String pass){
		dbAddress=ad;
		dbName=name;
		userName=user;
		password=pass;
	}
	
	public void insertTweets(Tweet[] tweets){
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://"+dbAddress+":3306/"+dbName,userName,password);   
			String query = " insert into Tweets values (?,?,?,?,?,?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			for(int i=0; i<tweets.length; i++){	
				preparedStmt.setString(1,Long.toString(tweets[i].getTweetID()));
				preparedStmt.setString(2,Long.toString(tweets[i].getUserID()));
				preparedStmt.setString(3,tweets[i].getCreatedAt().toString());
				preparedStmt.setString(4,Integer.toString(tweets[i].getFavoriteCount()));
				preparedStmt.setString(5,Integer.toString(tweets[i].getRetweetCount()));
				preparedStmt.setString(6,tweets[i].getContent());
				preparedStmt.setString(7,Boolean.toString(tweets[i].getContainsProfanity()));
				preparedStmt.execute();
			}
			con.close();
		}catch(Exception e){ System.out.println(e);} 
	}
	
	public void updateTweetProf(boolean prof, long tweetID){
		PreparedStatement preparedStatement = null;
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://"+dbAddress+":3306/"+dbName,userName,password);
			String updateTableSQL = "UPDATE Tweets SET ContainsProfanity='"+Boolean.toString(prof)+"' WHERE TweetID = " + tweetID;			
			preparedStatement = con.prepareStatement(updateTableSQL);
			preparedStatement.executeUpdate();
			con.close();
			System.out.println("Added Profanity tag");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}
	
	public void insertUser(String uid, String un){
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://"+dbAddress+":3306/"+dbName,userName,password);   
			String query = " insert into Users values (?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1,uid);
				preparedStmt.setString(2, un);
		    preparedStmt.execute();
			con.close();
		}catch(Exception e){ System.out.println(e);} 
	}

	public Tweet[] selectKeyword(String keyword, int numResults, boolean showProfan){
		//returns 2d array of results from query as Strings
		try{			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://"+dbAddress+":3306/"+dbName,userName,password);   
			Statement stmt=con.createStatement();
			String query = "SELECT * FROM Tweets WHERE content LIKE '%" + keyword + "%' ";
			if(!showProfan)
				query+=" AND ContainsProfanity='false' ";
			query+=" LIMIT " + numResults;
			
			System.out.println(Boolean.toString(showProfan));
			System.out.println(query);
			
			ResultSet rs=stmt.executeQuery(query);  
			
		    rs.last();
		    int rowCount = rs.getRow(); //get number of records returned
		    rs.beforeFirst();
		    
		    Tweet[] result = new Tweet[rowCount];
		    		    
		    int i=0;
			while(rs.next()){
				result[i] = new Tweet(rs.getLong(1), rs.getLong(2), new Date(rs.getString(3)), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7));
				i++;
			}		
			
			con.close();
			return result;
		}catch(Exception e){ System.out.println(e); return null;} 
	}
	
	public Tweet[] selectUser(String user, String keyword, int numResults, boolean showProfan){
		//returns 2d array of results from query as Strings
		try{			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://"+dbAddress+":3306/"+dbName,userName,password);   
			Statement stmt=con.createStatement();
			String query = "SELECT * FROM Tweets t "
						  +"JOIN Users u ON t.userID=u.userID "
						  +"WHERE u.userName='"+user+"' ";
				if(!showProfan)
						 query += "AND t.ContainsProfanity='false' ";
				
						 query += "AND t.content LIKE '%"+keyword+"%' LIMIT " + numResults;
					
			ResultSet rs=stmt.executeQuery(query);  
			
		    rs.last();
		    int rowCount = rs.getRow(); //get number of records returned
		    rs.beforeFirst();
		    
		    Tweet[] result = new Tweet[rowCount];
		    
		    int i=0;
			while(rs.next()){
				result[i] = new Tweet(rs.getLong(1), rs.getLong(2), new Date(rs.getString(3)), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7));
				i++;
			}		
			
			con.close();
			return result;
		}catch(Exception e){ System.out.println(e); return null;} 
	}	
}
