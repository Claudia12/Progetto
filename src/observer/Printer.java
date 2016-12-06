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
import object.DoubleKey;
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
		//System.out.println("SONO IL PRINTER");
		Connessione connessioneDaControllare=(Connessione)o;
		TCPPacket pacchettoArrivato=(TCPPacket)arg;
		Flusso flusso=new Flusso(new Host(pacchettoArrivato.getSourceAddress()),new Host(pacchettoArrivato.getDestinationAddress()),pacchettoArrivato.getSourcePort(),pacchettoArrivato.getDestinationPort());

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
		o.print("Numero di ack del pacchetto arrivato "+p.getAcknowledgementNumber() +" lung dati : "+ p.getPayloadDataLength()+" , lung header " +p.getHeaderLength());
		o.println();
		o.flush();
		o.print("% ripetizione : "+f.getRipetizione());
		o.println();
		o.flush();
		o.print("Size mappa  "+f.getNumberSequenceMap().size());
		o.println();
		o.flush();

		o.print("Size mappa zero  "+f.getNumberSequenceMapZero().size());
		o.println();
		o.flush();

		for(String sn:f.getNumberSequenceMapZero().keySet())
		{
			o.print("key : "+sn+" microsecondi "+String.format("%.7f",f.getNumberSequenceMapZero().get(sn).getTempoArrivo())+" ha avuto ripetuti questo pacchetto "+f.getNumberSequenceMapZero().get(sn).getListaDuplicati().size());
			o.println();
			o.flush();
		}

		for(Long sn:f.getNumberSequenceMap().keySet())
		{
			o.print("sequence : "+sn+" microsecondi "+String.format("%.7f",f.getNumberSequenceMap().get(sn).getTempoArrivo())+" ha avuto ripetuti questo pacchetto "+f.getNumberSequenceMap().get(sn).getListaDuplicati().size());
			o.println();
			o.flush();
		}
		o.print("size map "+f.getNumberSequenceMap().size());
		o.println();
		o.flush();
		Iterator it =f.getNumberSequenceMap().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			// Stampa a schermo la coppia chiave-valore;
			o.print("Key = " + entry.getKey());
			o.print(" Value = " + entry.getValue());
			o.println();
			o.flush();
		}

		for(double rto:f.getRtoList())
		{
			o.print("rto = " +  String.format( "%.7f", rto));
			o.println();
			o.flush();

		}
		for(double rtt:f.getRttList())
		{
			o.print("rtt = " + String.format( "%.7f", rtt));
			o.println();
			o.flush();

		}
//		for(Integer i:f.getByteSec())
//		{
//			o.print("byte/sec = " + i);
//			o.println();
//			o.flush();
//
//		}
//
//		for(Integer r:f.getRwin())
//		{
//			o.print("rwin = " + r);
//			o.println();
//			o.flush();		
//		}
	}
}
