package org.neomatrix369;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.neomatrix369.apiworld.APIBuilderBehaviours;
import org.neomatrix369.apiworld.APIReaderBehaviours;
import org.neomatrix369.apiworld.UtilityFunctionsBehaviours;
import org.neomatrix369.apiworld.SearchForTweets.FetchTweetsPeriodicallyBehaviours;
import org.neomatrix369.apiworld.SearchForTweets.SearchTermFilterBehaviours;
import org.neomatrix369.apiworld.SearchForTweets.TweetsDataStorageBehaviours;
import org.neomatrix369.apiworld.SearchForTweets.TwitterSearchBehaviours;

/**
 * Suite class AllTests.
 *
 * @author helio frota http://www.heliofrota.com
 *
 */
@RunWith(value = Suite.class)
@SuiteClasses(value = {
    UtilityFunctionsBehaviours.class,
    APIBuilderBehaviours.class,
    APIReaderBehaviours.class,
   
    FetchTweetsPeriodicallyBehaviours.class,
    SearchTermFilterBehaviours.class,
    TweetsDataStorageBehaviours.class,
    TwitterSearchBehaviours.class
})
public class AllBehavioursTest {

}
