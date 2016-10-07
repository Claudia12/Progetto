package observer;

import java.util.Observable;
import java.util.Observer;

import net.sourceforge.jpcap.net.TCPPacket;
import object.Connessione;
import object.Flusso;
import object.Host;
import object.SequenceNumberObject;

public class IndicatoreRTT implements Observer{


	@Override
	public void update(Observable o, Object arg) 
	{
		System.out.println("SONO L'INDICATORE DI RTT ");
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
		System.out.println("A in questo caso è : "+pacchettoArrivato.getSourceAddress()+" B invece è : "+pacchettoArrivato.getDestinationAddress());
		System.out.println("Il pacchetto arrivato è questo:  "+pacchettoArrivato.getSequenceNumber());
		if(flusso.equals(connessioneDaControllare.getAB()) && pacchettoArrivato.isAck())
		{
			System.out.println("E' un ack");
			check(connessioneDaControllare.getBA(), pacchettoArrivato);

		}
		
		else if (flusso.equals(connessioneDaControllare.getBA()) && pacchettoArrivato.isAck())
		{
			System.out.println("E' un ack ");
			check(connessioneDaControllare.getAB(), pacchettoArrivato);
		}
		  
		else
		{
			System.out.println("non devo fare nulla");
		}
		
	
    }
	
	private void check(Flusso f, TCPPacket p)
	{
		if(f.getNumberSequenceMap().containsKey(p.getAcknowledgementNumber()))
		  {
			System.out.println("ricevuto ack del paccheto : "+p.getAcknowledgementNumber());
			int secondiInMicro=(int)p.getTimeval().getSeconds()*1000000;
			System.out.println("secondi in micro cosa ne esce "+ secondiInMicro);
			int tempoArrivo= secondiInMicro+p.getTimeval().getMicroSeconds();
			System.out.println("totole : "+tempoArrivo);
			 long rtt=tempoArrivo-f.getNumberSequenceMap().get(p.getAcknowledgementNumber());
			 f.getRttList().add(rtt);
			 f.getPacchettiDiCuiHoRicevutoACK().add(p.getAcknowledgementNumber());
			 f.getNumberSequenceMap().remove(p.getAcknowledgementNumber());
			 SequenceNumberObject s=new SequenceNumberObject(p.getAcknowledgementNumber());
			 
			 //f.getCopieDiNumberSequence().remove(s.hashCode());
		  }
		else
		{
			System.out.println("arrivato un ack ma non ho il pacchetto corrispondente");
		}
		//arrivato un ack ma non ho il pacchetto corrispondente
		for(long l:f.getRttList())
		{
			System.out.println("rtt : "+l);
		}
	}
	
}




