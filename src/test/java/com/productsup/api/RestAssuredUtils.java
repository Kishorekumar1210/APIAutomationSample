package com.productsup.api;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.productsup.utils.ReadPropertyFile;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredUtils

{

	public String response = null;
	public static RequestSpecification req;
	ReadPropertyFile readPropertiesFile=new ReadPropertyFile();
	public Properties configProperties = readPropertiesFile.loadconfigurations("config");



	
	public RequestSpecification requestSpecWithQueryParams(RequestSpecification requestSpecification, String paramKey,
			String paramvalue) {
		return given().spec(requestSpecification).queryParam(paramKey, paramvalue);
	}
	
	public RequestSpecification requestSpecWithRequestPayload(RequestSpecification requestSpecification,
			String requestPayload) {
		return given().spec(requestSpecification).body(requestPayload);
	}

	public RequestSpecification requestSpecWithQueryParamsMap(RequestSpecification requestSpecification,
			HashMap<String, String> queryParams) {
		return given().spec(requestSpecification).queryParams(queryParams);
	}

	
	
	
	public RequestSpecification requestSpecWithPathParams(RequestSpecification requestSpecification, String paramKey,
			String paramvalue) {
		return given().spec(requestSpecification).pathParam(paramKey, paramvalue);
	}

	public RequestSpecification requestSpecificationWithHeaders(Map<String, String> headersMap) throws IOException {

		if (req == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			req = new RequestSpecBuilder().setBaseUri(configProperties.getProperty("baseURL")).addHeaders(headersMap)
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
			return req;
		}
		return req;

	}
	
	public RequestSpecification requestSpecification() throws IOException {

		if (req == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			req = new RequestSpecBuilder().setBaseUri(configProperties.getProperty("baseURL"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
			return req;
		}
		return req;

	}


	


	/***
	 * Constructing a GET Request with Query Params
	 * 
	 * @param queryParams
	 * @param requestURL
	 * @return
	 * @throws Exception
	 */
	public String getHttpRequestWithQueryParams(Map<String, String> queryParams, String requestURL) throws Exception {
		response = given().queryParams(queryParams).when().get(requestURL).then().log().all().assertThat()
				.statusCode(200).extract().asString();
		return response;

	}

	/***
	 * Constructing a GET Request with Path Params
	 * 
	 * @param pathParams
	 * @param requestURL
	 * @return
	 * @throws Exception
	 */
	public String getHttpRequestWithPathParams(Map<String, String> pathParams, String requestURL) throws Exception {
		response = given().pathParams(pathParams).when().get(requestURL).then().log().all().assertThat().statusCode(200)
				.extract().asString();
		return response;

	}

	/***
	 * Constructing a GET Request with Headers
	 * 
	 * @param headerDetails
	 * @param requestURL
	 * @return
	 * @throws Exception
	 */
	public String getHttpRequestWithHeaders(Map<String, String> headerDetails, String requestURL) throws Exception {
		response = given().headers(headerDetails).when().get(requestURL).then().log().all().assertThat().statusCode(200)
				.extract().asString();
		return response;

	}

	/***
	 * Constructing a GET Request with Headers, Query Params,Path Params
	 * 
	 * @param headerDetails
	 * @param pathParams
	 * @param queryParams
	 * @param requestURL
	 * @return
	 * @throws Exception
	 */
	public String getHttpRequestWithHeadersPathAndQueryParams(Map<String, String> headerDetails,
			Map<String, String> pathParams, Map<String, String> queryParams, String requestURL) throws Exception {
		response = given().headers(headerDetails).pathParams(pathParams).queryParams(queryParams).when().get(requestURL)
				.then().log().all().assertThat().statusCode(200).extract().asString();
		return response;
	}

	// POST Request Type

	/***
	 * Constructing a POST Request with Headers
	 * 
	 * @param headerDetails
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */

	public String postHttpRequest(Map<String, String> headerDetails, String requestBody, String resourceURL)
			throws Exception { // response =
		RestAssured.baseURI = "https://tlxbeba0115.emea.fedex.com:8708/genius-neo/";
		String response = given().log().all().headers(headerDetails).body(requestBody).when().post(resourceURL).then()
				.log().all().assertThat().statusCode(200).extract().response().asString();
		return response;

	}

	/***
	 * Constructing a POST Request with Headers and Path Params
	 * 
	 * @param headerDetails
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	public String postHttpRequest(RequestSpecification requestSpec, String resourceURL) throws Exception {
		response = requestSpec.when().post(resourceURL)
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		return response;

	}

	/***
	 * Constructing a POST Request with Headers and Query Params
	 * 
	 * @param headerDetails
	 * @param queryParams
	 * @param requestBody
	 * @param resourceURL
	 * @return
	 * @throws Exception
	 */
	public String postHttpRequestWithQueryParams(Map<String, String> headerDetails, Map<String, String> queryParams,
			String requestBody, String resourceURL) throws Exception {
		response = given().log().all().headers(headerDetails).queryParams(queryParams)
				.body(new String(Files.readAllBytes(Paths.get(requestBody)))).when().post(resourceURL).then().log()
				.all().assertThat().statusCode(200).extract().response().asString();
		return response;

	}

	// PUT

	public String putHttpRequest(Map<String, String> headerDetails, String requestBody, String resourceURL)
			throws Exception {
		response = given().log().all().headers(headerDetails)
				.body(new String(Files.readAllBytes(Paths.get(requestBody)))).when().put(resourceURL).then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		return response;

	}

	/***
	 * Constructing a PUT Request with Headers and Path Params
	 * 
	 * @param headerDetails
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	public String putHttpRequestWithPathParams(Map<String, String> headerDetails, Map<String, String> pathParams,
			String requestBody, String resourceURL) throws Exception {
		response = given().log().all().headers(headerDetails).pathParams(pathParams)
				.body(new String(Files.readAllBytes(Paths.get(requestBody)))).when().put(resourceURL).then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		return response;

	}

	/***
	 * Constructing a PUT Request with Headers and Query Params
	 * 
	 * @param headerDetails
	 * @param queryParams
	 * @param requestBody
	 * @param resourceURL
	 * @return
	 * @throws Exception
	 */
	public String putHttpRequestWithQueryParams(Map<String, String> headerDetails, Map<String, String> queryParams,
			String requestBody, String resourceURL) throws Exception {
		response = given().log().all().headers(headerDetails).queryParams(queryParams)
				.body(new String(Files.readAllBytes(Paths.get(requestBody)))).when().put(resourceURL).then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		return response;

	}

	/***
	 * Constructing a PUT Request with Headers, Query and Path Params
	 * 
	 * @param headerDetails
	 * @param pathParams
	 * @param queryParams
	 * @param requestBody
	 * @param resourceURL
	 * @return
	 * @throws Exception
	 */
	public String putHttpRequest(Map<String, String> headerDetails, Map<String, String> pathParams,
			Map<String, String> queryParams, String requestBody, String resourceURL) throws Exception {
		response = given().log().all().headers(headerDetails).pathParams(pathParams).queryParams(queryParams)
				.body(new String(Files.readAllBytes(Paths.get(requestBody)))).when().put(resourceURL).then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		return response;

	}

	// DELETE HTTP Request

	/*****
	 * Constructing DELETE Request with Query Params
	 * 
	 * @param queryParams
	 * @param requestURL
	 * @return
	 * @throws Exception
	 */
	public String deleteHttpRequestWithQueryParams(Map<String, String> queryParams, String requestURL)
			throws Exception {
		response = given().queryParams(queryParams).when().delete(requestURL).then().log().all().assertThat()
				.statusCode(200).extract().asString();
		return response;

	}

	/****
	 * Constructing DELETE Request with Path Params
	 * 
	 * @param pathParams
	 * @param requestURL
	 * @return
	 * @throws Exception
	 */
	public String deleteHttpRequestWithPathParams(Map<String, String> pathParams, String requestURL) throws Exception {
		response = given().pathParams(pathParams).when().delete(requestURL).then().log().all().assertThat()
				.statusCode(200).extract().asString();
		return response;

	}

	/****
	 * Constructing DELETE Request with Headers,Path and Query Params
	 * 
	 * @param headerDetails
	 * @param pathParams
	 * @param queryParams
	 * @param requestURL
	 * @return
	 */
	public String deleteHttpRequestWithHeadersQueryAndPathParams(Map<String, String> headerDetails,
			Map<String, String> pathParams, Map<String, String> queryParams, String requestURL) {
		response = given().headers(headerDetails).pathParams(pathParams).queryParams(queryParams).when()
				.delete(requestURL).then().log().all().assertThat().statusCode(200).extract().asString();
		return response;

	}

	/***
	 * Constructing DELETE Request with Input Payload
	 * 
	 * @param headerDetails
	 * @param requestBody
	 * @param requestURL
	 * @return
	 * @throws IOException
	 */
	public String deleteHttpRequestWithBody(Map<String, String> headerDetails, String requestBody, String requestURL)
			throws IOException {
		response = given().headers(headerDetails).body(new String(Files.readAllBytes(Paths.get(requestBody)))).when()
				.delete(requestURL).then().log().all().assertThat().statusCode(200).extract().asString();
		return response;

	}

	/**
	 * Constructing Multipart Request for adding attachments
	 * 
	 * @param headerDetails
	 * @param fileLocation
	 * @param requestURL
	 * @return
	 */
	public String addingAttachments(Map<String, String> headerDetails, String fileLocation, String requestURL) {
		response = given().headers(headerDetails).multiPart("file", new File(fileLocation)).when().post(requestURL)
				.then().log().all().assertThat().statusCode(200).extract().asString();
		return response;
	}

	/***
	 * Returns the values of parsed JSON path
	 * 
	 * @param response
	 * @param key
	 * @return
	 */
	public String getJsonPath(Response response, String key) {
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(key).toString();
	}


	
	public Response generatePostRequest(RequestSpecification requestSpecification, String endPoint) {
		return requestSpecification.when().post(endPoint);
	}
	
	public Response generateGetRequest(RequestSpecification requestSpecification, String endPoint) {
		return requestSpecification.when().get(endPoint);
	}
	
	public Response generatePutRequest(RequestSpecification requestSpecification, String endPoint) {
		return requestSpecification.when().put(endPoint);
	}
	
	

 
	

}
