package com.productsup.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadPropertyFile {
	public Properties prop = new Properties();

	/**reading the data from properties file
	 * 
	 * @param propertiesFileName
	 * @return
	 */
	public Properties loadconfigurations(String propertiesFileName)  
	{
		try {

			String fileName = System.getProperty("user.dir") + "//src//test//resources//config//" + propertiesFileName
					+ ".properties";
			FileInputStream inputStream = new FileInputStream(fileName);
			prop.load(inputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}


}
