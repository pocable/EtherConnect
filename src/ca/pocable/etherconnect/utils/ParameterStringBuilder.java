package ca.pocable.etherconnect.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * ParameterStringBuilder
 * 
 * Builds a map of parameters into HTTP params.
 * @author Baeldung {@link https://www.baeldung.com/java-http-request}
 */
public class ParameterStringBuilder {
	
	/**
	 * Gets the map as a bunch of HTTP parameters
	 * @param params The HashMap of parameters
	 * @return The string output of the parameters
	 * @throws UnsupportedEncodingException If the encoding is unsupported
	 */
    public static String getParamsString(Map<String, String> params) 
      throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        result.append("?");
 
        for (Map.Entry<String, String> entry : params.entrySet()) {
          result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
          result.append("=");
          result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
          result.append("&");
        }
 
        String resultString = result.toString();
        return resultString.length() > 0
          ? resultString.substring(0, resultString.length() - 1)
          : resultString;
    }
}