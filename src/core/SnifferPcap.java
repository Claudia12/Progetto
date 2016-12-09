package core;

import support.PacketHandler;
import net.sourceforge.jpcap.capture.CaptureFileOpenException;
import net.sourceforge.jpcap.capture.CapturePacketException;
import net.sourceforge.jpcap.capture.PacketCapture;


public class SnifferPcap 
{


	private static final int INFINITE = -1;
	private static final int PACKET_COUNT = INFINITE; 
	private String nomeFile="";

	public SnifferPcap( String file) throws CaptureFileOpenException, CapturePacketException
	{
		// Initialize jpcap
		PacketCapture pcap = new PacketCapture();

		System.out.println("Using device file testo pcap'");
		pcap.openOffline(file);

		PacketHandler ph =new PacketHandler();
		ph.setNomeFile(nomeFile);
		pcap.addPacketListener(ph);

		System.out.println("Capturing packets...");
		pcap.capture(PACKET_COUNT);
	}
	public SnifferPcap( String file,String fileOutput) throws CaptureFileOpenException, CapturePacketException
	{
		// Initialize jpcap
		PacketCapture pcap = new PacketCapture();

		System.out.println("Using device file testo pcap'");
		pcap.openOffline(file);

		PacketHandler ph =new PacketHandler();
		ph.setNomeFile(fileOutput);
		
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
