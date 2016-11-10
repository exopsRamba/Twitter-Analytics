import java.util.Date;

public class Tweet {
	private String content;
	private long tweetID;
	private long userID;
	private Date createdAt;
	private int favoriteCount;
	private int retweetCount;
	private boolean containsProfanity;
	
	Tweet(long tID, long uID, Date date, int favCount, int reCount, String con, String pro){
		tweetID = tID;
		userID = uID;
		createdAt = date;
		favoriteCount = favCount;
		retweetCount = reCount;
		if(pro.equals("TRUE")){
			containsProfanity = true;
		}else{
			containsProfanity = false;
		}
		content = con;
	}
	
	public String getSQLQuery(){
		String query = null;
		
		return query;
	}
	
	public void print(){
		System.out.println("Tweet ID: " + tweetID);
		System.out.println("User ID: " + userID);
		System.out.println("Creation Date: " + createdAt);
		System.out.println("Favorite Count: " + favoriteCount);
		System.out.println("Retweet Count: " + retweetCount);
		System.out.println("Contains Profanity: " + containsProfanity);
		System.out.println("Content: " + content + "\n");
	}
	
	public String getContent(){
		return content;
	}
	public long getTweetID(){
		return tweetID;
	}
	public long getUserID(){
		return userID;
	}
	public Date getCreatedAt(){
		return createdAt;
	}
	public int getFavoriteCount(){
		return favoriteCount;
	}
	public int getRetweetCount(){
		return retweetCount;
	}
	public boolean getContainsProfanity(){
		return containsProfanity;
	}
	
}
