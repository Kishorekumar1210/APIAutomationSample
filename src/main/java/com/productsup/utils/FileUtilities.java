package com.productsup.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileUtilities 
{

	ObjectMapper mapper=new ObjectMapper();

	public void modifyFile(String filePath, String oldString, String newString) {
		File fileToBeModified = new File(filePath);
		String oldContent = "";
		BufferedReader reader = null;
		FileWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));

			// Reading all the lines of input text file into oldContent

			String line = reader.readLine();

			while (line != null) {
				oldContent = oldContent + line + System.lineSeparator();

				line = reader.readLine();
			}

			// Replacing oldString with newString in the oldContent

			String newContent = oldContent.replaceAll(oldString, newString);
			// Rewriting the input text file with newContent
			writer = new FileWriter(fileToBeModified);
			writer.write(newContent);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Closing the resources

				reader.close();
				writer.close();
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void replaceContentInFile(String filePath, Map<String, String> elementMapping, boolean replaceValues)
			throws IOException, InterruptedException {
		try {
			for (Map.Entry<String, String> entry : elementMapping.entrySet()) {
				if (replaceValues) {
					modifyFile(filePath, entry.getKey(), entry.getValue());
				}

				else 
				{
					modifyFile(filePath, entry.getValue(), entry.getKey());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
    public void replaceFile(String sourceFile,String targetFile ) 
	{
		Path sourceDirectory = Paths.get(sourceFile);
		Path targetDirectory = Paths.get(targetFile);

		// copy source to target using Files Class
		try {
			Files.copy(sourceDirectory, targetDirectory, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
    

	
	/***** Reads content from JSON file and creates a hashmap
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public List<HashMap<String, String>> generateHashMapList(String filePath,String fileName) throws IOException
	{
		String jsonContent=FileUtils.readFileToString(new File(filePath + fileName),StandardCharsets.UTF_8);
		List<HashMap<String,String>>hashMap=mapper.readValue(jsonContent,new TypeReference<List<HashMap<String,String>>>(){});
		return hashMap;
	
	}
	
	/***** Reads content from JSON file and creates a hashmap
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> generateHashMap(String filePath,String fileName) throws IOException
	{
		String jsonContent=FileUtils.readFileToString(new File(filePath + fileName),StandardCharsets.UTF_8);
		Map<String,String>hashMap=mapper.readValue(jsonContent,new TypeReference<Map<String,String>>(){});
		return hashMap;
	
	}
    
	public Map<String, Map<String, String>> generateMultipleHashMaps(String filePath, String fileName)
			throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(filePath + fileName), StandardCharsets.UTF_8);
		Map<String, Map<String, String>> hashMap = mapper.readValue(jsonContent,
				new TypeReference<Map<String, Map<String, String>>>() {
				});
		return hashMap;

	}

}
