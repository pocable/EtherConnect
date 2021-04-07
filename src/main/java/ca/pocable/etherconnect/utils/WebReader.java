package ca.pocable.etherconnect.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class WebReader {
	
	/**
	 * Send a GET request to the given url
	 * @param baseUrl The base url
	 * @param params The parameter list
	 * @return
	 */
	public String request(String baseURL, HashMap<String, String> params) {
		try {
			return request(baseURL + ParameterStringBuilder.getParamsString(params));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Send a GET request to the given url
	 * @param url The url to GET from
	 * @return The data from the url in String form
	 */
	public String request(String url) {
		try {
			URL urlObj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			return content.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
