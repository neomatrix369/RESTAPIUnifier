package examples.SearchForTweets;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TweetsDataStorage {
	private static final String ERROR_WHILE_INTERATING_THROUGH_TWEETS = "Error while going through tweets: %s. %n";
	private static final String ERROR_WHILE_SAVING_TWEETS = "Error while saving tweets: %s. %n";
	private static final String ERROR_WHILE_LOADING_TWEETS = "Error while loading tweets: %s. %n";
	private static final String ERROR_WHILE_PARSING_TWEETS = "Error while parsing tweets: %s. %n";
	private static final String TWITTER_JSON_RESULTS_TAG = "results";
	private static final String BY_FROM_USERNAME = "from_user_name";
	private static final String BY_FROM_USER_ID = "from_user_id";
	private static final String BY_TO_USERNAME = "to_user_name";
	private static final String BY_TO_USER_ID = "to_user_id";
	private static final String BY_MESSAGE_BODY = "text";
		
	private static final boolean TERM_WAS_FOUND = true;
	private static final boolean TERM_WAS_NOT_FOUND = false;
	
    private static final Logger COMMON_LOGGER = Logger.getLogger(TweetsDataStorage.class.getName()); 

    private String localStorageFile = "savedTweets.json";
	private JSONArray tweetMessages;
	
	
	TweetsDataStorage() {
	}
	
	TweetsDataStorage(String storageFilename) {
		localStorageFile = storageFilename;
	}

	public Boolean searchTermInUserNameInTweetMessage(String usingTerm) {		
		return (findTermBy(usingTerm, BY_FROM_USERNAME) || 
			   findTermBy(usingTerm, BY_TO_USERNAME));
	}
	
	public boolean searchTermInUserIDInTweetMessage(String usingTerm) {
		return (findTermBy(usingTerm, BY_FROM_USER_ID) || 
				findTermBy(usingTerm, BY_TO_USER_ID));
	}
	
	public boolean searchTermInBodyOfTheTweetMessage(String usingTerm) {
		return findTermBy(usingTerm, BY_MESSAGE_BODY);	
	}

	public boolean searchTermAnyPartOfTheTweetMessage(String usingTerm) {
        return searchTermInUserNameInTweetMessage(usingTerm) ||
        		searchTermInUserIDInTweetMessage(usingTerm) ||
        		searchTermInBodyOfTheTweetMessage(usingTerm);
	}

	private boolean findTermBy(String usingTerm, String byField) {
		if (tweetMessages == null) {
			tweetMessages = loadTweetMessage();
			if (tweetMessages == null) {
				return TERM_WAS_NOT_FOUND;
			}
		}
				
		for (int tweetCounter=0; tweetCounter < tweetMessages.length(); tweetCounter++) {
			try {
				JSONObject eachTweet = tweetMessages.getJSONObject(tweetCounter);
				if (!eachTweet.has(byField)) {
					return TERM_WAS_NOT_FOUND;
				}

				String fieldValueAsString = eachTweet.getString(byField);
				if (fieldValueAsString.toLowerCase().contains(usingTerm.toLowerCase())) {
					return TERM_WAS_FOUND;
				}
			} catch (JSONException ex) {
				COMMON_LOGGER.log(Level.SEVERE, String.format(ERROR_WHILE_INTERATING_THROUGH_TWEETS, ex.getMessage()));		
			}
		}
		return TERM_WAS_NOT_FOUND;
	}
	
	public void saveTweetMessage(String receivedTweets) {
		saveTweetMessage(receivedTweets, localStorageFile);
	}
	
	private void saveTweetMessage(String receivedTweets, String localFileName) {
		try {
			FileUtils.writeStringToFile(new File(localFileName), receivedTweets);
		} catch (IOException ex) {
			COMMON_LOGGER.log(Level.SEVERE, String.format(ERROR_WHILE_SAVING_TWEETS, ex.getMessage()));
		}	
	}

	public JSONArray loadTweetMessage() {
		return loadTweetMessage(localStorageFile);
	}
	
	private JSONArray loadTweetMessage(String localFilename) {
		return loadSavedMessagesFromFile(localFilename);
	}

	private JSONArray loadSavedMessagesFromFile(String localFile) {
		JSONArray resultsArray = null;
		try {
			String tweetsAsString = FileUtils.readFileToString(new File(localFile));
			
			try {
				JSONObject jsonObject = new JSONObject (tweetsAsString);  
		        resultsArray = jsonObject.getJSONArray(TWITTER_JSON_RESULTS_TAG);
			} catch ( JSONException ex) {
				COMMON_LOGGER.log(Level.SEVERE, String.format(ERROR_WHILE_PARSING_TWEETS, ex.getMessage()));
			}
		} catch (IOException ex) {
			COMMON_LOGGER.log(Level.SEVERE, String.format(ERROR_WHILE_LOADING_TWEETS, ex.getMessage()));
		}
		return resultsArray;
	}

	public boolean verifyIfTheMessagesMatch(String[] tweetMessageRead, String[] tweetMessageWritten) {
		String readMessage = Arrays.toString(tweetMessageRead);
		String writtenMessage = Arrays.toString(tweetMessageWritten);
		
		return verifyIfTheMessagesMatch(readMessage, writtenMessage);
	}
	
	public boolean verifyIfTheMessagesMatch(String tweetMessageRead, String tweetMessageWritten) {
		return tweetMessageRead.equals(tweetMessageWritten);
	}	
}