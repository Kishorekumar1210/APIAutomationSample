package com.productsup.payload;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.productsup.api.BaseClass;
import com.productup.stepdefinitions.Hooks;

public class PayloadMapping extends BaseClass {

	public Map<String, String> payloadData() throws Exception {

		Map<String, Map<String, String>> setPayload = new HashMap<String, Map<String, String>>();
		setPayload.put("Creating a new user via API", createUserPayload());
		return setPayload.get(Hooks.scenarioName);

	}

	public Map<String, String> getRequestPayloadLocation(String requestPayloadPath, String fileName, String payloadType)
			throws IOException {
		Map<String, String> payloadFileLocation = new HashMap<String, String>();
		try {

			payloadFileLocation = fileUtils.generateHashMap(requestPayloadPath, fileName);

			for (Map.Entry<String, String> entry : payloadFileLocation.entrySet()) {

				String con = payloadType + entry.getValue();
				payloadFileLocation.put(entry.getKey(), con);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return payloadFileLocation;
	}
	
	
	
	public Map<String,String>createUserPayload() throws IOException
	{
	      HashMap<String,String>user=new HashMap<String,String>();
	      user.put("USER_NAME", excelReader.getTestData(Hooks.scenarioName, "Name", "DemoSheet"));
	      user.put("JOB_TYPE", excelReader.getTestData(Hooks.scenarioName, "Job", "DemoSheet"));
		  return user;
	}

}
