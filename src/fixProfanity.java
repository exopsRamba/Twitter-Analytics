
public class fixProfanity {
	static String[] profanity = {"anal","anus","arse","ass","ballsack","balls","bastard","bitch","biatch","bloody",
			"blowjob","blow job","bollock","bollok","boner","boob","bugger","bum","butt","buttplug",
			"clitoris","cock","coon","crap","cunt","damn","dick","dildo","dyke","fag",
			"feck","fellate","fellatio","felching","fuck","f u c k","fudgepacker","fudge packer","flange","goddamn",
			"God damn","hell","homo","jerk","jizz","knobend","knob end","labia",
			"muff","nigger","nigga","penis","piss","poop","prick","pube","pussy",
			"queer","scrotum","s hit","shit","slut","sh1t","smegma","spunk","tit","tosser",
			"turd","twat","vagina","wank","whore"};
	
	public static void main(String[] args){
		SQLInstance db1= new SQLInstance(	"twitteranalytics.cs6rrovfokv1.us-west-2.rds.amazonaws.com","twitteranalytics", //AWS db info
				"twitteranalytics","uark3513");
		
		Tweet[] ts = db1.selectKeyword("", 5000, true);
		for(Tweet t : ts){
			String content = t.getContent();
			for(int i = 0; i < profanity.length; i++){
				if(content.contains(profanity[i])){
					System.out.println(t.getContent());					
					db1.updateTweetProf(true, t.getTweetID());
					break;
				}
			}
			
		}
		
	}
}
