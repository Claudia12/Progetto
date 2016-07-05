package observer;

import java.util.Observable;
import java.util.Observer;

import net.sourceforge.jpcap.net.TCPPacket;
import object.Connessione;
import object.Flusso;
import object.Host;

public class IndicatoreRTO implements Observer {

	@Override
	public void update(Observable o, Object arg) 
	{
		System.out.println("SONO L'INDICATORE DI RTO ");	
		Connessione connessioneDaControllare=(Connessione)o;
	//	System.out.println("list sequence number ab prima "+ connessioneDaControllare.getAB().getNumberSequenceList().size());
		//System.out.println("list sequence number ba prima "+ connessioneDaControllare.getBA().getNumberSequenceList().size());
//		System.out.println(connessioneDaControllare.getAB().getA().getHostname());
//		System.out.println(connessioneDaControllare.getAB().getB().getHostname());
//		System.out.println(connessioneDaControllare.getBA().getA().getHostname());
//		System.out.println(connessioneDaControllare.getBA().getB().getHostname());
		TCPPacket pacchettoArrivato=(TCPPacket)arg;
		System.out.println("A in questo caso è : "+pacchettoArrivato.getSourceAddress()+" B invece è : "+pacchettoArrivato.getDestinationAddress());
		//System.out.println(pacchettoArrivato.getSequenceNumber());
		Flusso flusso=new Flusso(new Host(pacchettoArrivato.getSourceAddress()),new Host(pacchettoArrivato.getDestinationAddress()),pacchettoArrivato.getSourcePort(),pacchettoArrivato.getDestinationPort());

		if(flusso.equals(connessioneDaControllare.getAB()) /*&& !pacchettoArrivato.isAck()*/)
		{
			System.out.println("ab");
			check(connessioneDaControllare.getAB(), pacchettoArrivato);
		}
		else if(flusso.equals(connessioneDaControllare.getBA()) /*&& !pacchettoArrivato.isAck()*/)		
		{
			System.out.println("ab");
			check(connessioneDaControllare.getBA(), pacchettoArrivato);
		}
		
		
		//System.out.println("list sequence number ab dopo "+connessioneDaControllare.getAB().getNumberSequenceList().size());
		//System.out.println("list sequence number ba dopo "+connessioneDaControllare.getBA().getNumberSequenceList().size());
	
	}

	private void check(Flusso f,TCPPacket p)
	{
		if(f.getNumberSequenceList().containsKey(p.getSequenceNumber()))//se c'è vuol dire che è un pacchetto ripetuto
		{
			System.out.println("ripetizione");
			f.setRipetizione(f.getRipetizione()+1);
			f.getNumberSequenceListRepeat().put(p.getSequenceNumber(), p.getTimeval().getMicroSeconds());
			long rto=p.getTimeval().getMicroSeconds()-f.getNumberSequenceList().get(p.getSequenceNumber());
			f.getRtoList().add(rto);
		}
		else //vuol dire che un pacchetto nuovo
		{
			System.out.println("nuovo");
			f.getNumberSequenceList().put(p.getSequenceNumber(), p.getTimeval().getMicroSeconds());
			System.out.println("list sequence number nel mezzo  "+f.getNumberSequenceList().size());
			
		}
		
		for(long rto:f.getRtoList())
		{
			System.out.println("rto "+rto );
		}
	}

}
