package observer;

import java.util.Observable;
import java.util.Observer;

import net.sourceforge.jpcap.net.TCPPacket;
import object.Connessione;
import object.Flusso;
import object.Host;
import object.SequenceNumberObject;

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
		System.out.println("Il pacchetto arrivato è questo:  "+pacchettoArrivato.getSequenceNumber());
		Flusso flusso=new Flusso(new Host(pacchettoArrivato.getSourceAddress()),new Host(pacchettoArrivato.getDestinationAddress()),pacchettoArrivato.getSourcePort(),pacchettoArrivato.getDestinationPort());

		if(flusso.equals(connessioneDaControllare.getAB()) /*&& !pacchettoArrivato.isAck()*/)
		{
			System.out.println("ab");
			check(connessioneDaControllare.getAB(), pacchettoArrivato);
		}
		else if(flusso.equals(connessioneDaControllare.getBA()) /*&& !pacchettoArrivato.isAck()*/)		
		{
			System.out.println("ba");
			check(connessioneDaControllare.getBA(), pacchettoArrivato);
		}
		
		
		//System.out.println("list sequence number ab dopo "+connessioneDaControllare.getAB().getNumberSequenceList().size());
		//System.out.println("list sequence number ba dopo "+connessioneDaControllare.getBA().getNumberSequenceList().size());
	
	}

	private void check(Flusso f,TCPPacket p)
	{
		f.setNumeroPacchettiTotali();
		System.out.println("Dimensioni dati del pacchetto "+ p.getSequenceNumber() +" "+ p.getPayloadDataLength());
		if(f.getNumberSequenceMap().containsKey(p.getSequenceNumber()) && p.getPayloadDataLength()>0)//se c'è vuol dire che è un pacchetto ripetuto
		{
			f.setDuplicatiTotale();
			SequenceNumberObject s=new SequenceNumberObject(p.getSequenceNumber());
			System.out.println("hash"+s.hashCode());
			//devo salvarlo nella lista devo chiedermi se prima ce in quella lista
			if(!presente(f,s))//non essendo presente lo devo aggiungere invece se è presente devo solo aggiornare quante copie ho trovato
			{
				System.out.println("non è presente è la prima copia che trovo");
				s.setCopie();
				f.getCopieDiNumberSequence().put(s.hashCode(), s);
			//	f.getCopieDiNumberSequence().add(s);
			}
			System.out.println("sequence number : "+p.getSequenceNumber()+"quello nella mappa ");
			int secondiInMicro=(int)p.getTimeval().getSeconds()*1000000;
			System.out.println("secondi in micro cosa ne esce "+ secondiInMicro);
			int tempoArrivo= secondiInMicro+p.getTimeval().getMicroSeconds();
			System.out.println("totale : "+tempoArrivo);
			System.out.println("devo fare la sottrazione tra : "+tempoArrivo+" - "+f.getNumberSequenceMap().get(p.getSequenceNumber()));
			
			long rto=tempoArrivo-f.getNumberSequenceMap().get(p.getSequenceNumber());
			f.getRtoList().add(rto);
		}
		else //if ( p.getPayloadDataLength()>0) //vuol dire che un pacchetto nuovo
		{
			System.out.println("nuovo");
			int secondiInMicro=(int)p.getTimeval().getSeconds()*1000000;
			System.out.println("secondi in micro cosa ne esce "+ secondiInMicro);
			int tempoArrivo= secondiInMicro+p.getTimeval().getMicroSeconds();
			System.out.println("totole : "+tempoArrivo);

			f.getNumberSequenceMap().put(p.getSequenceNumber(),tempoArrivo);

		}
//		else
//		{
//			System.out.println("sara solo una ricevuta di ritorno non contiene dati");
//		}
//		for(long rto:f.getRtoList())
//		{
//			System.out.println("rto "+rto );
//		}
	}

	private boolean presente(Flusso f, SequenceNumberObject s) 
	{
	
		System.out.println("presente");
		if(f.getCopieDiNumberSequence().contains(s))
		{	
			System.out.println("sono entrata nel if del rto copia, avevo gia trovato una copia ");
			int key=s.hashCode();
			f.getCopieDiNumberSequence().get(key).setCopie();
			
			return true;
		}
			
	return false;
	}

}
