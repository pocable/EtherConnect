package ca.pocable.etherconnect.etherscan;

/**
 * BlockUpdate
 * 
 * Generated class to represent the Response JSON object from EtherScan
 * @author Thomas
 * @version 1.0
 */
public class BlockEvent {
	private String user;
	private long blockloc;
	private int blockID;
	
	public BlockEvent(String user, long blockNum, int blockID) {
		this.setUser(user);
		this.setBlockLoc(blockNum);
		this.setBlockID(blockID);
	}
	
	public BlockEvent() {}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public long getBlockLoc() {
		return blockloc;
	}

	public void setBlockLoc(long blockNum) {
		this.blockloc = blockNum;
	}

	public int getBlockID() {
		return blockID;
	}

	public void setBlockID(int blockID) {
		this.blockID = blockID;
	}

}
