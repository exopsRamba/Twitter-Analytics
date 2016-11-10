import twitter4j.Query;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.QueryResult;
import twitter4j.Status;
import java.util.List;
//import twitter4j.GeoLocation;

public class TwitterInstance {
	ConfigurationBuilder cb = new ConfigurationBuilder();
	TwitterFactory tf;
	Twitter twitter;
	Query query;
	
	public TwitterInstance(String CK, String CS, String AT, String ATS){
	      cb.setDebugEnabled(true)
	        .setOAuthConsumerKey(CK)
	        .setOAuthConsumerSecret(CS)
	        .setOAuthAccessToken(AT)
	        .setOAuthAccessTokenSecret(ATS);
	      tf = new TwitterFactory(cb.build());
	      twitter = tf.getInstance();
	      query=new Query();
	}
	
	public List<Status> searchKeyword(String queryStr,int count){
		try{
			query = new Query(queryStr);
			
			//To set geoCode search area in query. Kind of most Americans seem to have geo data not included.
			//query.geoCode(new GeoLocation(36.062579,-94.157426), 50, "mi");
			query.setLang("en");
			//Max query return count is 100
			//if(count>=0&&count<=100)
				query.setCount(count);
			QueryResult result = twitter.search(query);
			return result.getTweets();
		}
		catch(Exception e){ System.out.println(e); return null;}
	}
	public List<Status> searchUsername(String queryStr,int count){
		try{
			query = new Query(queryStr);
			
			//To set geoCode search area in query. Kind of most Americans seem to have geo data not included.
			//query.geoCode(new GeoLocation(36.062579,-94.157426), 50, "mi");
			query.setLang("en");
			//Max query return count is 100
			if(count>=0&&count<=100)
				query.setCount(count);
			QueryResult result = twitter.search(query);
			return result.getTweets();
		}
		catch(Exception e){ System.out.println(e); return null;}
	}
	
}
