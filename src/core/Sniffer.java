package core;

import java.io.FileOutputStream;
import java.io.PrintStream;
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
		
		void controlloENotificaObserver(Connessione c)
		{
			
		}
		@Override
		public void packetArrived(Packet packet) 
		{
			if(packet instanceof TCPPacket)
			{
				TCPPacket tcpPacket=(TCPPacket) packet;
//				Host srcHost=new Host(tcpPacket.getSourceAddress());
//				Host dstHost=new Host(tcpPacket.getDestinationAddress());
//				int srcPort=tcpPacket.getSourcePort();
//				int dstPort=tcpPacket.getDestinationPort();
				Connessione tmp=new Connessione(new Flusso(new Host(tcpPacket.getSourceAddress()),new Host(tcpPacket.getDestinationAddress()),tcpPacket.getSourcePort(),tcpPacket.getDestinationPort()),new Flusso(new Host(tcpPacket.getDestinationAddress()),new Host(tcpPacket.getSourceAddress()),tcpPacket.getDestinationPort(),tcpPacket.getSourcePort()));
				if(connessioni.contains(tmp))
				{
					int pos=connessioni.indexOf(tmp);
					//System.out.println("Connessioni size c'� "+connessioni.size());
					connessioni.get(pos).setChanged(true, tcpPacket);
					//System.out.println("c'�");
//					if((tmp.getAB().getA().getHostname().equals(srcHost)) && (tmp.getAB().getB().getHostname().equals(dstHost)) && (tmp.getAB().getPortaSorgente()==srcPort) && (tmp.getAB().getPortaDestinazione()==dstPort) )//vuol dire che il flusso che mi interesa � AB
//					{
//						tmp.getAB().notifyObservers();
//					}
//					else if ((tmp.getBA().getA().getHostname().equals(srcHost)) && (tmp.getBA().getB().getHostname().equals(dstHost))) //
//					{
//						tmp.getBA().notifyObservers();
//					}
				}
				else //vuol dire che � una nuova connessione trovata percio l aggiungo alla lista e poi faccio tutto cio che ho fatto sopra
				{
					//System.out.println("Connessioni size "+connessioni.size());
					connessioni.add(tmp);
					tmp.setChanged(true, tcpPacket);
//					System.out.println("non c'�");
//					System.out.println(tmp.getAB().getA().getHostname()+" - "+tmp.getAB().getB().getHostname()+" "+ tmp.getAB().getPortaSorgente()+" "+tmp.getAB().getPortaDestinazione());
//					System.out.println(tmp.getBA().getA().getHostname()+" - "+tmp.getBA().getB().getHostname()+" "+ tmp.getBA().getPortaSorgente()+" "+tmp.getBA().getPortaDestinazione());
					
					//forse devo aggiungere anche gli observer 
//					if((tmp.getAB().getA().getHostname().equals(srcHost)) && (tmp.getAB().getB().getHostname().equals(dstHost)))//vuol dire che il flusso che mi interesa � AB
//					{
//						tmp.getAB().notifyObservers();
//					}
//					else if ((tmp.getBA().getA().getHostname().equals(srcHost)) && (tmp.getBA().getB().getHostname().equals(dstHost))) //
//					{
//						tmp.getBA().notifyObservers();
//					}
//					
				}
			}
			
		} 
		
	}


