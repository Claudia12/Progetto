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
		o.print("size map "+f.getNumberSequenceList().size());
		o.println();
		o.flush();
//		Iterator it =f.getNumberSequenceList().entrySet().iterator();
//		 while (it.hasNext()) {
//		    Map.Entry entry = (Map.Entry)it.next();
//		    // Stampa a schermo la coppia chiave-valore;
//		    o.print("Key = " + entry.getKey());
//		    o.print(" Value = " + entry.getValue());
//		    o.println();
//			o.flush();
//		    }
//		  
//		 for(Long rto:f.getRtoList())
//		 {
//			 	o.print("rto = " + rto);
//			    o.println();
//				o.flush();
//		
//		 }
//		 
//		  for(Integer i:f.getByteSec())
//		  {
//			  	o.print("byte/sec = " + i);
//			    o.println();
//				o.flush();
//			      
//		  }
//	
	}
}
