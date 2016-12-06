package core;

import support.PacketHandler;
import net.sourceforge.jpcap.capture.PacketCapture;



public class Sniffer 
{
	private static final int INFINITE = -1;
	private static final int PACKET_COUNT = INFINITE; 
	private static final String FILTER =  "";
	private String nomeFile="";


	public Sniffer(String device,String nomeFile) throws Exception
	{
		// Initialize jpcap
		PacketCapture pcap = new PacketCapture();

		System.out.println("Using device '" + device + "'");
		pcap.open(device, true);
		pcap.setFilter(FILTER, true);
		PacketHandler ph =new PacketHandler();
		ph.setNomeFile(nomeFile);
		
		pcap.addPacketListener(ph);
		

		System.out.println("Capturing packets...");
		pcap.capture(PACKET_COUNT);
	}

	public Sniffer(String device) throws Exception
	{
		// Initialize jpcap
		PacketCapture pcap = new PacketCapture();

		System.out.println("Using device '" + device + "'");
		pcap.open(device, true);
		pcap.setFilter(FILTER, true);
		PacketHandler ph =new PacketHandler();
		
		ph.setNomeFile(nomeFile);
		pcap.addPacketListener(ph);

		System.out.println("Capturing packets...");
		pcap.capture(PACKET_COUNT);
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) 
	{
		if(this.nomeFile.equals(""))
		{
		this.nomeFile = nomeFile;
		}
	}
}


