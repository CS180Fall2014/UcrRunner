package fbutil.firebase4j.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import fbutil.firebase4j.error.JacksonUtilityException;


public class JacksonUtility {

	
	
	
	/**
	 * Creates a json-string representing the data provided by the map.
	 * 
	 * @param dataMap; can be null/empty, but will result in an empty String;
	 * 			otherwise, must be Strings mapped to arbitrary Objects.
	 * @return the json-string representing the data, or an empty-string; will not return null
	 * @throws JacksonUtilityException if there was an error converting the map-data into a json-string
	 */
	public static String GET_JSON_STRING_FROM_MAP( Map<String, Object> dataMap ) throws JacksonUtilityException {
	
		/* NOTE: per Jackson-dox, the map must be of type <String, Object> */
		
		if( dataMap == null || dataMap.isEmpty() ) {
			return new String(); // don't want to return null to avoid NPEs
		}
		
		Writer writer = new StringWriter();		
		try {
		
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue( writer, dataMap );
			
		} catch( Throwable t ) {
			
			String msg = "unable to convert data from map into json: " + dataMap.toString();
			throw new JacksonUtilityException( msg );
			
		}
		
		return writer.toString();
	}
	

	/**
	 * Creates a map represented by the json-data provided.
	 * 
	 * @param jsonResponse; can be null/empty, but will result in an empty-map;
	 * @return Strings mapped to arbitrary Objects
	 * 
	 * @throws JacksonUtilityException if there was an error converting the json-string into map-data
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> GET_JSON_STRING_AS_MAP( String jsonResponse ) throws JacksonUtilityException {
		
		/* NOTE: @SuppressWarnings("unchecked") because Jackson-dox state that a JSON-Object will always return as 
		 * Map<String, Object>
		 * http://wiki.fasterxml.com/JacksonDataBinding
		 */	
		
		if( jsonResponse == null || jsonResponse.trim().isEmpty() ) {
			return new HashMap<String, Object>(); // don't want to return null to avoid NPEs
		}
		jsonResponse = jsonResponse.trim();
		
		
		Map<String, Object> result = null;		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			Object o = mapper.readValue( jsonResponse, Object.class );
			if( o instanceof Map ) {
				result = (Map<String, Object>) o;
			}
			
		} catch( Throwable t ) {
			
			String msg = "unable to map json-response: " + jsonResponse;
			throw new JacksonUtilityException( msg, t );
			
		}
		
		// don't want to return null to avoid NPEs
		if( result == null ) {
			result = new LinkedHashMap<String, Object>();
		}
		
		return result;
	}
	
}






