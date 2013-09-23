/**
 *
 *  Copyright (c) 2013. All rights reserved.
 *
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 *  This code is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License version 2 only, as
 *  published by the Free Software Foundation.  Oracle designates this
 *  particular file as subject to the "Classpath" exception as provided
 *  by Oracle in the LICENSE file that accompanied this code.
 *
 *  This code is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 *  version 2 for more details (a copy is included in the LICENSE file that
 *  accompanied this code).
 *
 *  You should have received a copy of the GNU General Public License version
 *  2 along with this work; if not, write to the Free Software Foundation,
 *  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.neomatrix369;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.neomatrix369.apiworld.UriBuilderIntBehaviours;
import org.neomatrix369.apiworld.APIReaderBehaviours;
import org.neomatrix369.apiworld.UtilityFunctionsBehaviours;
import org.neomatrix369.apiworld.heroku.HerokuAPIBehaviours;
import org.neomatrix369.apiworld.twitter.FetchTweetsPeriodicallyBehaviours;
import org.neomatrix369.apiworld.twitter.SearchTermFilterBehaviours;
import org.neomatrix369.apiworld.twitter.TweetsDataStorageBehaviours;
import org.neomatrix369.apiworld.twitter.TwitterSearchBehaviours;

/**
 * Suite class AllTests.
 *
 * @author helio frota http://www.heliofrota.com
 *
 */
@RunWith(value = Suite.class)
@SuiteClasses(value = {
    UtilityFunctionsBehaviours.class,
    UriBuilderIntBehaviours.class,
    APIReaderBehaviours.class,
   
    FetchTweetsPeriodicallyBehaviours.class,
    SearchTermFilterBehaviours.class,
    TweetsDataStorageBehaviours.class,
    TwitterSearchBehaviours.class,
    
    HerokuAPIBehaviours.class
})

public class AllBehavioursTest {
}