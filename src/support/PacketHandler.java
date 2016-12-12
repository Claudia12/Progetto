package support;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import object.Connessione;
import object.Flusso;
import object.Host;
import net.sourceforge.jpcap.capture.PacketListener;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.TCPPacket;

public class PacketHandler implements PacketListener
{
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
	List<Connessione> connessioni= new ArrayList<Connessione>();
	private String nomeFile=df.format(new Date()) +"_Statistics.csv";

	@Override
	public void packetArrived(Packet packet)
	{
		
		if(packet instanceof TCPPacket)
		{
			TCPPacket tcpPacket=(TCPPacket) packet;
			Host A = new Host(tcpPacket.getSourceAddress());
			Host B = new Host(tcpPacket.getDestinationAddress());
			int portaA=tcpPacket.getSourcePort();
			int portaB=tcpPacket.getDestinationPort();
			
			Flusso AB=new Flusso(A,B,portaA,portaB);
			Flusso BA=new Flusso(B,A,portaB,portaA);
			Connessione tmp=new Connessione(AB,BA);
			if(connessioni.contains(tmp))
			{
				int pos=connessioni.indexOf(tmp);
				connessioni.get(pos).setChanged(true, tcpPacket);

			}
			else
			{	
				tmp.setNomeFile(nomeFile);
				tmp.setChanged(true, tcpPacket);
				connessioni.add(tmp);				
			}
		}
		
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile)
	{
		if(nomeFile.equals(""))
		{
			return;
		}
			
		if(nomeFile.endsWith(".csv") && nomeFile.startsWith("."))
		{
			String []s=this.nomeFile.split("_");
			this.nomeFile=s[0]+nomeFile;
		}
		if(nomeFile.endsWith(".csv") && !nomeFile.startsWith("."))
		{
		this.nomeFile=nomeFile;	
		}
		else if((!nomeFile.endsWith(".csv")))
		{
			String [] s=nomeFile.split("\\.");
			this.nomeFile=s[0].concat(".csv");
		}
		
	} 
}


