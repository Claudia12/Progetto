package object;

public class DoubleKey
{

	private Long nSeq;
	private Long nAck;
	private String key;
	
	public DoubleKey(long nSeq,long nAck)
	{
	this.nSeq=nSeq;
	this.nAck=nAck;
	setKey(nSeq+":"+nAck);
	}

	public long getnSeq() {
		return nSeq;
	}

	public long getnAck() {
		return nAck;
	}

	@Override
	public String toString() 
	{
		return "Prima chiave "+nSeq+"Seconda Chiave "+nAck;
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
