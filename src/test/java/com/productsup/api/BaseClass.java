package com.productsup.api;

import java.util.Map;
import java.util.Properties;

import com.productsup.utils.ExcelReader;
import com.productsup.utils.ExtentReportLogs;
import com.productsup.utils.FileUtilities;
import com.productsup.utils.ReadPropertyFile;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseClass {
	public RestAssuredUtils utilities = new RestAssuredUtils();

	public ReadPropertyFile readPropertiesFile = new ReadPropertyFile();
	public ExtentReportLogs extentReport = new ExtentReportLogs();
	public FileUtilities fileUtils = new FileUtilities();

	public final String USER_DIR = "user.dir";
	public ExcelReader excelReader = new ExcelReader();
	public Properties configProperties = readPropertiesFile.loadconfigurations("config");
	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;
	public static Response response;
	public String featureID = null;
	public String scenarioID = null;

	public static Map<String, String> requestPayloads;
	public static Map<String, String> staticPaylaods;

	public static String requestBodyFilePath = System.getProperty("user.dir")
			+ "//src//test//resources//TestData//RequestBody//";
	public static String staticPayloadFilePath = System.getProperty("user.dir")
			+ "//src//test//resources//TestData//StaticPayload//";
	public static String requestPayloadFile = System.getProperty("user.dir")
			+ "//src//test//resources//TestData//FileLocation//";
	
	public APIResources resourcesAPI;


	
	

}
