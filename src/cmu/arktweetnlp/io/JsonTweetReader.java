package cmu.arktweetnlp.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

import cmu.arktweetnlp.util.BasicFileIO;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 */
public class JsonTweetReader  {
	ObjectMapper mapper;
	JsonNode rootNode; // Temporary, used so that we can get out the node after a parse
	
	public JsonTweetReader() {
		mapper = new ObjectMapper();
	}
	
	public JsonNode getRootNode() {
		return rootNode;
	}
	/**
	 * Get the text from a raw Tweet JSON string.
	 * 
	 * @param tweetJson
	 * @return null if there is no text field, or invalid JSON.
	 */
	public String getText(String tweetJson) {
		try {
			rootNode = mapper.readValue(tweetJson, JsonNode.class);
		} catch (JsonParseException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		
		if (! rootNode.isObject())
			return null;
		
		JsonNode textValue = rootNode.get("text");
		if (textValue==null)
			return null;
		
		return textValue.asText();
	}
	
	public boolean isJson(String isThisJson) {
		JsonNode rootNode; 
		
		if (isThisJson.charAt(0) != '{')
			return false;
		
		try {
			rootNode = mapper.readValue(isThisJson, JsonNode.class);
		} catch (JsonParseException e) {
			return false;
		} catch (IOException e) {
			System.err.println("WTF -- got IOException in isJson()");
			return false;
		}
		return true;
		
	}

}
