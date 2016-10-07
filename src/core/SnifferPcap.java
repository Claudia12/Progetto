package core;

import net.sourceforge.jpcap.capture.CaptureFileOpenException;
import net.sourceforge.jpcap.capture.CapturePacketException;
import net.sourceforge.jpcap.capture.PacketCapture;
import net.sourceforge.jpcap.capture.PacketListener;
import net.sourceforge.jpcap.net.Packet;

public class SnifferPcap {


	 private static final int INFINITE = -1;
	  private static final int PACKET_COUNT = INFINITE; 
	  /*
	    private static final String HOST = "203.239.110.20";
	    private static final String FILTER = 
	      "host " + HOST + " and proto TCP and port 23";
	  */

	  private static final String FILTER =  "";
	   // "";
	public static void main(String[] args) throws CaptureFileOpenException, CapturePacketException 
	{
		SnifferPcap sniffer = new SnifferPcap(null);
	}

	public SnifferPcap( String file) throws CaptureFileOpenException, CapturePacketException
	  {
	    // Initialize jpcap
	    PacketCapture pcap = new PacketCapture();
	    
	    System.out.println("Using device file testo pcap'");
	    pcap.openOffline("C:/Users/Claudia/Desktop/Tesi/wireshark1.pcap");
	    
	    pcap.addPacketListener(new PacketHandler());

	    System.out.println("Capturing packets...");
	    pcap.capture(PACKET_COUNT);
	  }

//	}
}
