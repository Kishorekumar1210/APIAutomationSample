package com.productsup.runner;

import org.testng.annotations.BeforeSuite;

import com.productsup.api.BaseClass;
import com.productsup.payload.PayloadMapping;
import com.productsup.utils.FileUtilities;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(monochrome = true, features = "src/test/resources/Features", glue = "com.productup.stepdefinitions", plugin = {
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" }, tags = (""))

public class CucumberRunner extends AbstractTestNGCucumberTests {

	BaseClass baseClass = new BaseClass();
	FileUtilities fileUtils = new FileUtilities();

	PayloadMapping payload = new PayloadMapping();

	@BeforeSuite(alwaysRun = true)
	public void setUp() throws Exception {
		baseClass.excelReader.openExcelWorkBook();

		BaseClass.requestPayloads = payload.getRequestPayloadLocation(BaseClass.requestPayloadFile,
				"RequestPayloads.json", BaseClass.requestBodyFilePath);
		BaseClass.staticPaylaods = payload.getRequestPayloadLocation(BaseClass.requestPayloadFile,
				"RequestPayloads.json", BaseClass.staticPayloadFilePath);

	}

}
