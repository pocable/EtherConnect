package ca.pocable.etherconnect.etherscan;

/**
 * EtherEventResult
 * 
 * Generated class to represent the Result JSON object from EtherScan
 * @author Thomas
 * @version 1.0
 */
public class EtherEventResult {
	
	private String address;
	private String[] topics;
	private String data;
	private String blockNumber;
	private String timeStamp;
	private String gasPrice;
	private String gasUsed;
	private String logIndex;
	private String transactionHash;
	private String transactionIndex;
	
	public EtherEventResult() {
		
	}
	
	public String getTransactionIndex() {
		return transactionIndex;
	}
	public void setTransactionIndex(String transactionIndex) {
		this.transactionIndex = transactionIndex;
	}
	public String getTransactionHash() {
		return transactionHash;
	}
	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}
	public String getLogIndex() {
		return logIndex;
	}
	public void setLogIndex(String logIndex) {
		this.logIndex = logIndex;
	}
	public String getGasUsed() {
		return gasUsed;
	}
	public void setGasUsed(String gasUsed) {
		this.gasUsed = gasUsed;
	}
	public  String getGasPrice() {
		return gasPrice;
	}
	public  void setGasPrice(String gasPrice) {
		this.gasPrice = gasPrice;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getBlockNumber() {
		return blockNumber;
	}
	public void setBlockNumber(String blockNumber) {
		this.blockNumber = blockNumber;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String[] getTopics() {
		return topics;
	}
	public void setTopics(String[] topics) {
		this.topics = topics;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
