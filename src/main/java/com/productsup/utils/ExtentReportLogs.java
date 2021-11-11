package com.productsup.utils;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExtentReportLogs {

	public void addResultData(String exceptedResult, String actualResult) throws Exception {
		String[][] logData = { { "Excepted value", "Actual value" }, { exceptedResult, actualResult } };
		ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, MarkupHelper.createTable(logData));
	}

	public void addLoggerData(String data) throws Exception {

		ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "[INFO] |---" + data + "---|");
		
	}

}
