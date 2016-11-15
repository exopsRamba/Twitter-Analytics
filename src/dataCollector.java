import twitter4j.*;
import twitter4j.Status;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

class dataCollector{
	static final String[] profanity = {"anal","anus","arse","ass","ballsack","balls","bastard","bitch","biatch","bloody",
										"blowjob","blow job","bollock","bollok","boner","boob","bugger","bum","butt","buttplug",
										"clitoris","cock","coon","crap","cunt","damn","dick","dildo","dyke","fag",
										"feck","fellate","fellatio","felching","fuck","f u c k","fudgepacker","fudge packer","flange","goddamn",
										"God damn","hell","homo","jerk","jizz","knobend","knob end","labia",
										"muff","nigger","nigga","penis","piss","poop","prick","pube","pussy",
										"queer","scrotum","s hit","shit","slut","sh1t","smegma","spunk","tit","tosser",
										"turd","twat","vagina","wank","whore"};
	static SQLInstance db1= new SQLInstance(	"twitteranalytics.cs6rrovfokv1.us-west-2.rds.amazonaws.com","twitteranalytics", //AWS db info
			"twitteranalytics","uark3513");
	//Set up Twitter API connection. Consumer Key, Consumer Secret, Access Token, Access Token Secret
	static TwitterInstance tw=new TwitterInstance("yJMv83AdNdZ2carDj0Qgk9Rxr","X1hmu8T9iFmcbKkKWuq2EWzRB6m3pmIVXDQwVtOMzPc8989icH",
											"626995873-PifAe9js72HOSdao6T7aU2Pf0djWs6n0DgY3bpV6","c1Xi6O7UIAOsyepKJZnXnALoGlWzmrUoNXYDxY1OAYIeQ");
	
	
	public static Tweet[] insertToDatabase(List<Status> res, String input){
		Tweet[] tweets = new Tweet[res.size()];
		int z = 0;
		for(Status s : res){
			//Print handle of poster
			System.out.println("@"+s.getUser().getScreenName()+":");

			//Print content of tweet
			String content = s.getText();
			boolean profan = false;
			
			for(int i = 0; i < profanity.length; i++){
				if(content.contains(profanity[i])){
					profan = true;
					System.out.println("Found profanity!  " + profan);
					//Change characters to ***** later
					break;
				}
			}
				System.out.println(profan);
				tweets[z] = new Tweet(s.getId(), s.getUser().getId(), s.getCreatedAt(), s.getFavoriteCount(), s.getRetweetCount(), content, Boolean.toString(profan));
				System.out.println(tweets[z].getContainsProfanity());
				
				System.out.println(content);
				System.out.println();
				
				if(input.equals("Keyword"))
				db1.insertUser(Long.toString(s.getUser().getId()), s.getUser().getScreenName());
			z++;
		}
	db1.insertTweet(tweets);//Insert tweets	
	return tweets;
	}
	
	public static Tweet[] twitterCollectorKeyword(String searchKeyword, int numSearches){
		//Perform API search with search(query,number of tweets to return)
				if(searchKeyword == null)
					System.exit(0);
				
			List<Status> res = tw.searchKeyword(searchKeyword + " -filter:retweets AND -filter:replies", numSearches);
			System.out.println(searchKeyword + " -filter:retweets");
			Tweet [] tweets = insertToDatabase(res, "Keyword");
	//Java date constructor takes in a string and parses each piece of the date into a java date format
	//Constructor: date(String s)
			return tweets;
	}

	public static Tweet[] twitterCollectorUser(String searchUser, int numResult){
		try{
			Paging page = new Paging(1, numResult);
			List<Status> res = tw.twitter.getUserTimeline(searchUser, page);
			System.out.println(searchUser);
			System.out.println(Long.toString(res.get(0).getUser().getId()));
			db1.insertUser(Long.toString(res.get(0).getUser().getId()), searchUser);
			Tweet [] tweets = insertToDatabase(res, "Username");
			return tweets;
		}catch(Exception e){
			System.out.println("ERROR ENCOUNTERED IN SEARCH USERNAME: " + e);
			return null;
		}	
	}
	
	
	public static Tweet[] dbCollectorKeyword(String keyword, int numRes, boolean showProfan){
		Tweet[] tweets;
		tweets = db1.selectKeyword(keyword, numRes, showProfan);
		return tweets;
	}
	
	public static Tweet[] dbCollectorUser(String username, String keyword, int numResults, boolean showProfan){
		Tweet[] tweets;
		tweets = db1.selectUser(username, keyword, numResults, showProfan);
		return tweets;
	}
	
	public void execute(int location, String keyword, String username, int resultCount, boolean showProfan){
		if(location == 65){
			if(username.equals("")){
				Results.execute(twitterCollectorKeyword(keyword, resultCount));//Twitter doesn't filter profan in table.
			}else if(keyword.equals("")){
				Results.execute(twitterCollectorUser(username, resultCount));
			}else{
				System.out.println("Username: " + username + " Keyword: " + keyword);
			}
		}else if(location == 66){
			if(username.equals("")){
				Results.execute(dbCollectorKeyword(keyword, resultCount, showProfan));
			}else{
				Results.execute(dbCollectorUser(username, keyword, resultCount, showProfan));
			}
		}
	}	
} 
