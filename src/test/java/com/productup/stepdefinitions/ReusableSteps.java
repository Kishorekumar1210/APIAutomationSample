package com.productup.stepdefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import com.productsup.api.APIResources;
import com.productsup.api.BaseClass;
import com.productsup.payload.PayloadMapping;
import com.productsup.utils.ExtentReportLogs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;

public class ReusableSteps extends BaseClass {

	PayloadMapping payLoad = new PayloadMapping();
	ExtentReportLogs extentReportLogs = new ExtentReportLogs();

	@Given("{string} request payload is available")
	public void request_payload_is_available(String requestBody) {
		try {
			BaseClass.requestSpec = given().spec(
					utilities.requestSpecification().body(generateStringFromResource(System.getProperty("user.dir")
							+ "//src//test//resources//TestData//RequestBody//" + requestBody + ".json")));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@When("user calls the {string} endpoint with {string} http request")
	public void user_calls_the_endpoint_with_http_request(String resource, String httpMethod) {
		try {
			resourcesAPI = APIResources.valueOf(resource);
			responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
			switch (httpMethod) {
			case "POST":
				BaseClass.response = utilities.generatePostRequest(requestSpec, resourcesAPI.getResource());
				break;

			case "GET":
				BaseClass.response = utilities.generateGetRequest(requestSpec, resourcesAPI.getResource());
				break;

			case "PUT":
				BaseClass.response = utilities.generatePutRequest(requestSpec, resourcesAPI.getResource());
				break;

			default:
				System.out.println("****HTTP REQUEST NOT PERFORMED***");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Then("{string} in response body should be {string}")
	public void in_response_body_should_be(String expectedKey, String expectedValue) throws Exception {
		String exp = utilities.getJsonPath(BaseClass.response, expectedKey);
		Assert.assertEquals(exp.trim(), expectedValue.trim());
		extentReportLogs.addResultData(exp, expectedValue);
	}


	public String generateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}
	
	
	@Given("request has no payload")
	public void request_has_no_payload() {
	  System.out.println("***********NO PAYLOAD **********************");
	}

	
	@Given("request specifications is loaded with {string} headers")
	public void request_specifications_is_loaded_with_headers(String competencyDetails) throws IOException {
		BaseClass.requestSpec = given()
				.spec(utilities.requestSpecification());

		System.out.println("****Executed Given Method*********");
	}



}
