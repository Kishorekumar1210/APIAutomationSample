
package com.productup.stepdefinitions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;


import com.productsup.api.BaseClass;
import com.productsup.payload.PayloadMapping;
import com.productsup.utils.FileUtilities;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks extends BaseClass {
	public static String featureName;
	public static String scenarioName;

	PayloadMapping payloadMapping = new PayloadMapping();
	FileUtilities fileUtils = new FileUtilities();

	/*****
	 * This Hooks executes before each and every scenario and gets the scenario and
	 * feature name
	 * 
	 * @param scenario
	 */
	@Before(order = 0)
	public void getFeatureDetails(Scenario scenario) {

		scenarioName = scenario.getName();
		String[] featurePath = scenario.getId().split("/");
		int rawFeatureNameLength = featurePath.length;
		String rawFeatureName = featurePath[rawFeatureNameLength - 1].split(":")[0];
		featureName = rawFeatureName.substring(0, rawFeatureName.indexOf('.'));
		scenarioName = scenarioName.replace("\"", "");

	}

	/****
	 * This Hook executes only if the scenarios are tagged with "@setPayload" and it
	 * replaces the content in Json Payload based on the keys maintained
	 * 
	 */
	@Before("@setPayload")
	public void setPayload() {
		Map<String, String> retrievedPayload = new HashMap<String, String>();
		try {
			retrievedPayload = payloadMapping.payloadData();
			fileUtils.replaceContentInFile(BaseClass.requestPayloads.get(scenarioName), retrievedPayload, true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/******
	 * This Hook executes after the scenarios only if the scenarios are created with
	 * "@resetPayload" and it will revert the json payload with its original content
	 * 
	 * @throws IOException
	 */

	@After("@resetPayload")
	public void resetPayload() {

		try {
			fileUtils.replaceFile(BaseClass.staticPaylaods.get(scenarioName),
					BaseClass.requestPayloads.get(scenarioName));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fileUtils.replaceFile(BaseClass.staticPaylaods.get(scenarioName),
					BaseClass.requestPayloads.get(scenarioName));
		}

	}

}
