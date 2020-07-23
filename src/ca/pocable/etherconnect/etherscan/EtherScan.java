package ca.pocable.etherconnect.etherscan;

import java.util.HashMap;

import com.google.gson.Gson;

import ca.pocable.etherconnect.utils.WebReader;

/**
 * EtherScan
 * 
 * Communicates with the EtherScan website API and retrieves results.
 * @author Thomas
 * @version 1.0
 */
public class EtherScan extends WebReader{
	
	private String apiKey;
	private String endpoint;
	private String contractAddress;
	
	public EtherScan(String endpoint, String apiKey, String contractAddress) {
		this.apiKey = apiKey;
		this.endpoint = endpoint;
		this.contractAddress = contractAddress;
	}
	
	/**
	 * Get a blockchain event response
	 * @param fromBlock The first block to search from
	 * @param toBlock The last block to search to
	 * @param topicsAndOps The topic operators and topics
	 * @return EtherResponse object
	 */
	public EtherResponse getEventResponse(String fromBlock, String toBlock, HashMap<String, String> topicsAndOps) {
		HashMap<String, String> res = new HashMap<String, String>();
		
		res.put("module", "logs");
		res.put("action", "getLogs");
		res.put("fromBlock", fromBlock);
		res.put("toBlock", toBlock);
		res.put("address", contractAddress);
		res.putAll(topicsAndOps);
		res.put("apikey", apiKey);
		return getResultFromInput(this.request(endpoint, res));
	}
	
	
	/**
	 * Get a blockchain event response from only one topic
	 * @param fromBlock The first block to search from
	 * @param toBlock The last block to search to
	 * @param topic0 The topic to search for
	 * @return EtherResponse object
	 */
	public EtherResponse getEventResponse(String fromBlock, String toBlock, String topic0) {
		HashMap<String, String> res = new HashMap<String, String>();
		res.put("module", "logs");
		res.put("action", "getLogs");
		res.put("fromBlock", fromBlock);
		res.put("toBlock", toBlock);
		res.put("topic0", topic0);
		res.put("address", contractAddress);
		res.put("apikey", apiKey);
		return getResultFromInput(this.request(endpoint, res));
	}
	
	/**
	 * Converts the input JSON and converts it into an EtherResponse
	 * @param input The input JSON string.
	 * @return The EtherResponse from the server.
	 */
	public EtherResponse getResultFromInput(String input) {
		Gson gson = new Gson();
		return gson.fromJson(input, EtherResponse.class);
	}
	

}
