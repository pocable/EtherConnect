package ca.pocable.etherconnect.etherscan;

/**
 * EtherResponse
 * 
 * Generated class to represent the Response JSON object from EtherScan
 * @author Thomas
 * @version 1.0
 */
public class EtherResponse {
	private String status;
	private String message;
	private EtherEventResult[] result;
	
	public EtherResponse() {
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EtherEventResult[] getResult() {
		return result;
	}

	public void setResult(EtherEventResult[] result) {
		this.result = result;
	}
	
	/**
	 * Return if the response was valid.
	 * @return
	 */
	public boolean isValid() {
		return status.equals("1");
	}
	

}
