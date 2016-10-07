package observer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import net.sourceforge.jpcap.net.TCPPacket;
import object.Connessione;
import object.Flusso;
import object.Host;

public class Printer implements Observer {


	FileOutputStream fileAB;
	FileOutputStream fileBA;
	PrintStream outfileAB;
	PrintStream outfileBA;
	@Override
	public void update(Observable o, Object arg) 
	{
		System.out.println("SONO IL PRINTER");
		Connessione connessioneDaControllare=(Connessione)o;
		TCPPacket pacchettoArrivato=(TCPPacket)arg;
		Flusso flusso=new Flusso(new Host(pacchettoArrivato.getSourceAddress()),new Host(pacchettoArrivato.getDestinationAddress()),pacchettoArrivato.getSourcePort(),pacchettoArrivato.getDestinationPort());
		if(pacchettoArrivato.isAck())
		{
			System.out.println("A in questo caso è : "+pacchettoArrivato.getSourceAddress()+" B invece è : "+pacchettoArrivato.getDestinationAddress()+" ack number : "+pacchettoArrivato.getAcknowledgementNumber());	
		}
		else
		{
			System.out.println("A in questo caso è : "+pacchettoArrivato.getSourceAddress()+" B invece è : "+pacchettoArrivato.getDestinationAddress()+ " No ack");
		}
		System.out.println("Il pacchetto arrivato è questo:  "+pacchettoArrivato.getSequenceNumber());
		try {

			fileAB=new FileOutputStream("flussiAB.txt", true);
			outfileAB=new PrintStream(fileAB);
			fileBA=new FileOutputStream("flussiBA.txt", true);
			outfileBA=new PrintStream(fileBA);

			if(flusso.equals(connessioneDaControllare.getAB()))
			{
				check(connessioneDaControllare.getAB(), pacchettoArrivato, outfileAB);
			}
			else if(flusso.equals(connessioneDaControllare.getBA()))
			{
				check(connessioneDaControllare.getBA(), pacchettoArrivato, outfileBA);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void check(Flusso f,TCPPacket p,PrintStream o)
	{
		o.print(f.getA().getHostname()+" - "+f.getB().getHostname()+" - "+f.getPortaSorgente()+" - "+f.getPortaDestinazione());
		o.println();
		o.flush();
		o.print("Numero di sequnza del pacchetto arrivato "+p.getSequenceNumber()+" il tempo di arrivo : "+ p.getTimeval().getDate()+" , " +p.getTimeval().getSeconds()+" secondi "+p.getTimeval().getMicroSeconds()+" microsecondi");
		o.println();
		o.flush();
		o.print("% ripetizione : "+f.getRipetizione());
		o.println();
		o.flush();
		o.print("Size mappa  "+f.getNumberSequenceMap().size());
		o.println();
		o.flush();
		for(Integer key:f.getCopieDiNumberSequence().keySet())
		{
			o.print("hashCode : "+key+" valore : sequence number "+f.getCopieDiNumberSequence().get(key).getSequenceNumberPacket()+" numero copie "+f.getCopieDiNumberSequence().get(key).getCopie());
			o.println();
			o.flush();
		}

		for(Long sn:f.getNumberSequenceMap().keySet())
		{
			o.print("sequence : "+sn+" microsecondi "+f.getNumberSequenceMap().get(sn));
			o.println();
			o.flush();
		}
		//		o.print("size map "+f.getNumberSequenceMap().size());
		//		o.println();
		//		o.flush();
		//		Iterator it =f.getNumberSequenceMap().entrySet().iterator();
		//		 while (it.hasNext()) {
		//		    Map.Entry entry = (Map.Entry)it.next();
		//		    // Stampa a schermo la coppia chiave-valore;
		//		    o.print("Key = " + entry.getKey());
		//		    o.print(" Value = " + entry.getValue());
		//		    o.println();
		//			o.flush();
		//		    }
		//		 
		System.out.println("size rto list : "+f.getRtoList().size());
		for(Long rto:f.getRtoList())
		{
			o.print("rto = " + rto);
			o.println();
			o.flush();

		}
		System.out.println("size rtt list : "+f.getRttList().size());
		for(Long rtt:f.getRttList())
		{
			o.print("rtt = " + rtt);
			o.println();
			o.flush();

		}

		System.out.println("size ackricevuti list : "+f.getPacchettiDiCuiHoRicevutoACK().size());
		for(Long ack:f.getPacchettiDiCuiHoRicevutoACK())
		{
			o.print("numberSequenceDiCuiHoRicevutoACK = " + ack);
			o.println();
			o.flush();

		}
		//			System.out.println("size byte/sec list : "+f.getByteSec().size());
		//		  for(Integer i:f.getByteSec())
		//		  {
		//			  	o.print("byte/sec = " + i);
		//			    o.println();
		//				o.flush();
		//			      
		//		  }

		//			System.out.println("size rwin list : "+f.getRwin().size());
		//		for(Integer r:f.getRwin())
		//		{
		//		  	o.print("rwin = " + r);
		//		    o.println();
		//			o.flush();		
		//		}
	}
}
