package observer;


import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import net.sourceforge.jpcap.net.TCPPacket;
import object.Connessione;
import object.DoubleKey;
import object.Flusso;
import object.Host;
import object.StatisticaPacchetto;
import support.WriterCSV;


public class IndicatoreRTO implements Observer {

	@Override
	public void update(Observable o, Object arg) 
	{
		Connessione connessioneDaControllare=(Connessione)o;
		TCPPacket pacchettoArrivato=(TCPPacket)arg;
		Flusso flusso=new Flusso(new Host(pacchettoArrivato.getSourceAddress()),new Host(pacchettoArrivato.getDestinationAddress()),pacchettoArrivato.getSourcePort(),pacchettoArrivato.getDestinationPort());

		if(flusso.equals(connessioneDaControllare.getAB()))
		{
			check(connessioneDaControllare,connessioneDaControllare.getAB(), pacchettoArrivato);
		}
		else if(flusso.equals(connessioneDaControllare.getBA()))		
		{
			check(connessioneDaControllare,connessioneDaControllare.getBA(), pacchettoArrivato);
		}
	}

	private void controlloSeRipetutoUno(Connessione connessioneDaControllare, Flusso f,Map<Long,StatisticaPacchetto> map,TCPPacket p)
	{
		double microInSecondi=(double)p.getTimeval().getMicroSeconds()/1000000;    
		double tempoArrivo=p.getTimeval().getSeconds() + microInSecondi;
		
		int lung=p.getPayloadDataLength();
		int lungHeader=p.getHeaderLength();
		long keySequenceNumber=p.getSequenceNumber();
		StatisticaPacchetto sp=new StatisticaPacchetto (keySequenceNumber,lung,lungHeader,p.getWindowSize(), tempoArrivo,p.isAck(),p.isSyn(),p.isFin());
		if( map.containsKey(keySequenceNumber) && map.get(keySequenceNumber).equals(sp))//allora e ripetuto
		{
			f.setDuplicatiTotale();
			double rto=tempoArrivo-map.get(keySequenceNumber).getTempoArrivo();
			f.getRtoList().add(rto);
			new WriterCSV().writeInCSV(connessioneDaControllare, f, "RTO", rto, tempoArrivo);
			
			map.get(keySequenceNumber).getListaDuplicati().add(sp);
		}
		else //e nuovo
		{
			map.put(p.getSequenceNumber(), sp);
		}
	}
	
	
	private void controlloSeRipetutoDue(Connessione c, Flusso f,Map<String,StatisticaPacchetto> map,TCPPacket p)
	{
		double microInSecondi=(double)p.getTimeval().getMicroSeconds()/1000000;
		double tempoArrivo=p.getTimeval().getSeconds() + microInSecondi;
		
		int lung=p.getPayloadDataLength();
		int lungHeader=p.getHeaderLength();
		DoubleKey dk=new DoubleKey(p.getSequenceNumber(), p.getAcknowledgementNumber());
		String key=dk.getKey();
		StatisticaPacchetto sp=new StatisticaPacchetto (dk.getnSeq(),lung,lungHeader,p.getWindowSize(),tempoArrivo,p.isAck(),p.isSyn(),p.isFin());
		if( map.containsKey(key) && map.get(key).equals(sp))//allora e ripetuto
		{
			f.setDuplicatiTotale();
			double rto=tempoArrivo-map.get(key).getTempoArrivo();
			f.getRtoList().add(rto);
			new WriterCSV().writeInCSV(c, f, "RTO", rto, tempoArrivo);
			
			map.get(key).getListaDuplicati().add(sp);
		}
		else //e nuovo
		{
			map.put(new DoubleKey(p.getSequenceNumber(),p.getAcknowledgementNumber()).getKey(), sp);

		}
	}
	
	
	
	private void check(Connessione connessioneDaControllare, Flusso f,TCPPacket p)
	{
		f.setNumeroPacchettiTotali();
		if(p.getPayloadDataLength()>0)
		{
		
			controlloSeRipetutoUno(connessioneDaControllare,f, f.getNumberSequenceMap(), p);
		}
		else //if ( p.getPayloadDataLength()=0) 
		{
			controlloSeRipetutoDue(connessioneDaControllare,f, f.getNumberSequenceMapZero(), p);
			
		}

	}

}
