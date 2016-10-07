package object;


public class SequenceNumberObject
{

	private long sequenceNumberPacket;
	private int copie;
	
	
	
	public SequenceNumberObject(long sequenceNumberPacket) 
	{
		super();
		this.sequenceNumberPacket = sequenceNumberPacket;
		copie=0;
	}

	public long getSequenceNumberPacket() {
		return sequenceNumberPacket;
	}
	
	public void setSequenceNumberPacket(long sequenceNumberPacket) {
		this.sequenceNumberPacket = sequenceNumberPacket;
	}
	
	public int getCopie() {
		return copie;
	}
	
	public void setCopie() 
	{
	System.out.println("copie prima "+copie);
	copie++;
	System.out.println("copie dopo "+copie);
	}
	
	@Override
	public int hashCode()
	{
		return (int)sequenceNumberPacket;
	}
	
}
