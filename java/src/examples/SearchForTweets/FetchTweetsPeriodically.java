package examples.SearchForTweets;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FetchTweetsPeriodically {
    private static final String SERVER_STARTUP_MESSAGE = "Started fetching tweets every hour!%n";
	private static final int SECONDS_IN_AN_HOUR = 3600;
	private static final int MILLISECONDS_IN_A_SECOND = 1000;
	private final TwitterInterfaceEngine twitterSearch = new TwitterInterfaceEngine();
	private static final String SOME_FIXED_TERM= "openjdk";
    private static final Logger COMMON_LOGGER = Logger.getLogger(FetchTweetsPeriodically.class.getName()); 
    
	private FetchTweetsPeriodically(int seconds) {
    	final Timer timer;    	
    	timer = new Timer();
        timer.schedule(new FetchTweetsPeriodicallyTask(), 0, seconds * MILLISECONDS_IN_A_SECOND);
	}

    final class FetchTweetsPeriodicallyTask extends TimerTask {
        public void run() {
        	String returnedTweets;
			try {
				returnedTweets = twitterSearch.searchTweets(SOME_FIXED_TERM);
	        	// this is only to show that the system is receiving feeds, not initial requirements  
	            // TODO: can be removed before production-ising        
	        	System.out.format(returnedTweets + "%n"); 
	        	saveThe(returnedTweets);
			} catch (Exception e) {
				COMMON_LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
        }
    }

    public static void main(String args[]) {
        new FetchTweetsPeriodically(SECONDS_IN_AN_HOUR);
        //        change this value ^^^^ to another lower value i.e. 10 and re-run the program
        //        to see what it does for a shorted duration
        
        System.out.format(SERVER_STARTUP_MESSAGE);
    }

    private void saveThe(String receivedTweets) {
		TweetsDataStorage tweetsStorage = new TweetsDataStorage();
		tweetsStorage.saveTweetMessage(receivedTweets);
	}
}