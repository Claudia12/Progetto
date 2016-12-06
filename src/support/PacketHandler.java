package support;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
	private String nomeFile2=df.format(new Date()) +"_Flussi.txt";
	
	FileOutputStream file;
	PrintStream outfile;
	
	public PacketHandler()
	{
			try {
				file=new FileOutputStream(nomeFile2, true);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			outfile=new PrintStream(file);
		
		
	}
	
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
				connessioni.get(pos).setChanged(true, tcpPacket);

			}
			else //vuol dire che è una nuova connessione trovata percio l aggiungo alla lista e poi faccio tutto cio che ho fatto sopra
			{	
				tmp.setNomeFile(nomeFile);
				tmp.setChanged(true, tcpPacket);
				connessioni.add(tmp);
				outfile.print(tmp.getAB().IDFlusso());
				outfile.println();
				outfile.flush();
				
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


