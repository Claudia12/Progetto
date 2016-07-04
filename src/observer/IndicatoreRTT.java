package observer;

import java.util.Observable;
import java.util.Observer;

import net.sourceforge.jpcap.net.TCPPacket;
import object.Connessione;
import object.Flusso;
import object.Host;

public class IndicatoreRTT implements Observer{


	@Override
	public void update(Observable o, Object arg) 
	{
		//System.out.println("SONO L'INDICATORE DI RTT ");
		Connessione connessioneDaControllare=(Connessione)o;
//		System.out.println(connessioneDaControllare.getAB().getNumberSequenceList().size());
//		System.out.println(connessioneDaControllare.getBA().getNumberSequenceList().size());
//		System.out.println(connessioneDaControllare.getAB().getA().getHostname());
//		System.out.println(connessioneDaControllare.getAB().getB().getHostname());
//		System.out.println(connessioneDaControllare.getBA().getA().getHostname());
//		System.out.println(connessioneDaControllare.getBA().getB().getHostname());
		TCPPacket pacchettoArrivato=(TCPPacket)arg;
		
//		System.out.println(pacchettoArrivato.getSequenceNumber());
		Flusso flusso=new Flusso(new Host(pacchettoArrivato.getSourceAddress()),new Host(pacchettoArrivato.getDestinationAddress()),pacchettoArrivato.getSourcePort(),pacchettoArrivato.getDestinationPort());
		//System.out.println(pacchettoArrivato.isAck());
		if(flusso.equals(connessioneDaControllare.getAB()) && pacchettoArrivato.isAck())
		{
			check(connessioneDaControllare.getBA(), pacchettoArrivato);

		}
		
		else if (flusso.equals(connessioneDaControllare.getBA()) && pacchettoArrivato.isAck())
		{
			check(connessioneDaControllare.getAB(), pacchettoArrivato);
		}
		  
		else
		{
			System.out.println("non devo fare nulla");
		}
		
	
    }
	
	private void check(Flusso f, TCPPacket p)
	{
		if(f.getNumberSequenceList().containsKey(p.getAcknowledgementNumber()))
		  {
			 long rtt=p.getTimeval().getMicroSeconds()-f.getNumberSequenceList().get(p.getAcknowledgementNumber());
			 f.getRttList().add(rtt);
			 
		  }
		  else if(f.getNumberSequenceListRepeat().containsKey(p.getAcknowledgementNumber()))
		  {
			  long rtt=p.getTimeval().getMicroSeconds()-f.getNumberSequenceListRepeat().get(p.getAcknowledgementNumber());
			  f.getRttList().add(rtt);
		  }

		for(long l:f.getRttList())
		{
			System.out.println("rtt : "+l);
		}
	}
	
}




