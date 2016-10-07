package core;

import java.util.ArrayList;
import java.util.List;

import object.Connessione;
import object.Flusso;
import object.Host;
import net.sourceforge.jpcap.capture.PacketCapture;
import net.sourceforge.jpcap.capture.PacketListener;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.TCPPacket;


public class Sniffer 
{


	 private static final int INFINITE = -1;
	  private static final int PACKET_COUNT = INFINITE; 
	  /*
	    private static final String HOST = "203.239.110.20";
	    private static final String FILTER = 
	      "host " + HOST + " and proto TCP and port 23";
	  */

	  private static final String FILTER =  "";
	   // "";

	  public static void main(String[] args) {
	    try {
	      if(args.length == 1)
	      {
	    	  //System.out.println("Entro nel if");
	    	  String[] devs = PacketCapture.lookupDevices();
	    	  for(int i = 0; i < devs.length ; i++)
	    		  System.out.println("\t" + devs[i]);
	    	  	Sniffer sniffer = new Sniffer(args[0]);
	    	  	
	      } 
	      else
	      {
	    	//  System.out.println("entro nell else");
	    	  System.out.println("Usage: java Sniffer [device name]");
	    	  System.out.println("Available network devices on your machine:");
	    	  String[] devs = PacketCapture.lookupDevices();
	    	  for(int i = 0; i < devs.length ; i++)
	    		  System.out.println("\t" + devs[i]);
	      	}
	    }
	    catch(Exception e) 
	    {
	      e.printStackTrace();
	    }
	  }

	  public Sniffer(String device) throws Exception
	  {
	    // Initialize jpcap
	    PacketCapture pcap = new PacketCapture();
	    
	    System.out.println("Using device '" + device + "'");
	    pcap.open(device, true);
	    pcap.setFilter(FILTER, true);
	    pcap.addPacketListener(new PacketHandler());

	    System.out.println("Capturing packets...");
	    pcap.capture(PACKET_COUNT);
	  }
	}


	class PacketHandler implements PacketListener 
	{

		List<Connessione> connessioni= new ArrayList<Connessione>();
	//	boolean primoPaccheto=true;
	//	Connessione connessioneDaVericare=null;
		@Override
		public void packetArrived(Packet packet) 
		{
			if(packet instanceof TCPPacket)
			{
				TCPPacket tcpPacket=(TCPPacket) packet;
				Connessione tmp=new Connessione(new Flusso(new Host(tcpPacket.getSourceAddress()),new Host(tcpPacket.getDestinationAddress()),tcpPacket.getSourcePort(),tcpPacket.getDestinationPort()),new Flusso(new Host(tcpPacket.getDestinationAddress()),new Host(tcpPacket.getSourceAddress()),tcpPacket.getDestinationPort(),tcpPacket.getSourcePort()));
				if(connessioni.contains(tmp))//(connessioneDaVericare.equals(tmp))//
				{
					int pos=connessioni.indexOf(tmp);
					System.out.println("Connessione prensente : connessioni totali "+connessioni.size());
					connessioni.get(pos).setChanged(true, tcpPacket);

				}
				else //vuol dire che è una nuova connessione trovata percio l aggiungo alla lista e poi faccio tutto cio che ho fatto sopra
				{
					System.out.println("Connessione non prensente, connessioni totali  "+connessioni.size());
					tmp.setChanged(true, tcpPacket);
					connessioni.add(tmp);
				}
			}
			
		} 
		
	}

