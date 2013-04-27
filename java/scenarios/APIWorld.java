package r.workspace_Indigo_APIAggregator.APIAggregator.scenarios;

import org.jbehave.scenario.Scenario;

public class APIWorld extends Scenario {
	
		public APIWorld()  {
                   
                   addSteps(new APIWorldSteps());
			
		}
}
