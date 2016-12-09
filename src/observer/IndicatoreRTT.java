package observer;

import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import net.sourceforge.jpcap.net.TCPPacket;
import object.Connessione;
import object.Flusso;
import object.Host;
import object.StatisticaACK;
import support.WriterCSV;


public class IndicatoreRTT implements Observer{


	@Override
	public void update(Observable o, Object arg) 
	{
		Connessione connessioneDaControllare=(Connessione)o;
		TCPPacket pacchettoArrivato=(TCPPacket)arg;
		Flusso flusso=new Flusso(new Host(pacchettoArrivato.getSourceAddress()),new Host(pacchettoArrivato.getDestinationAddress()),pacchettoArrivato.getSourcePort(),pacchettoArrivato.getDestinationPort());
		if(flusso.equals(connessioneDaControllare.getAB()) && pacchettoArrivato.isAck())
		{
			check(connessioneDaControllare,connessioneDaControllare.getBA(), pacchettoArrivato);

		}

		else if (flusso.equals(connessioneDaControllare.getBA()) && pacchettoArrivato.isAck())
		{
			check(connessioneDaControllare,connessioneDaControllare.getAB(), pacchettoArrivato);
		}

	}

	private void check(Connessione connessioneDaControllare, Flusso f, TCPPacket p)
	{
		boolean pacchettoCorrispondenteTrovato=false;
		long ackNumber=p.getAcknowledgementNumber();
		double microInSecondi=(double)p.getTimeval().getMicroSeconds()/1000000;
		double tempoArrivo= p.getTimeval().getSeconds()+ microInSecondi;
	
		if(f.getNumberSequenceMap().size()>0)
		{
			 Iterator it =f.getNumberSequenceMap().entrySet().iterator();
			while(it.hasNext() && !pacchettoCorrispondenteTrovato)
			{
				Map.Entry entry = (Map.Entry)it.next();
				long tmp=f.getNumberSequenceMap().get(entry.getKey()).getnSequ()+f.getNumberSequenceMap().get(entry.getKey()).getLen();
				if(ackNumber==tmp)
				{
					pacchettoCorrispondenteTrovato=true;
					double rtt=tempoArrivo-f.getNumberSequenceMap().get(entry.getKey()).getTempoArrivo();
					f.getRttList().add(rtt);
					new WriterCSV().writeInCSV(connessioneDaControllare, f, "RTT", rtt, tempoArrivo);
					if(!presente(f.getNumberACKMap(),ackNumber,tempoArrivo))
					{
						StatisticaACK sa= new StatisticaACK(ackNumber, tempoArrivo);
						f.getNumberACKMap().put(ackNumber,sa);
					}
				}
			
			}
			
		}
		if(f.getNumberSequenceMapZero().size()>0 && !pacchettoCorrispondenteTrovato)
		{
			 Iterator it =f.getNumberSequenceMapZero().entrySet().iterator();
				while(it.hasNext() && !pacchettoCorrispondenteTrovato)
				{
					Map.Entry entry = (Map.Entry)it.next();
					long tmp=f.getNumberSequenceMapZero().get(entry.getKey()).getnSequ();
					if(ackNumber==tmp+1)
					{
						pacchettoCorrispondenteTrovato=true;
						double rtt=tempoArrivo-f.getNumberSequenceMapZero().get(entry.getKey()).getTempoArrivo();
						f.getRttList().add(rtt);
						new WriterCSV().writeInCSV(connessioneDaControllare, f, "RTT", rtt, tempoArrivo);
						if(!presente(f.getNumberACKMap(),ackNumber,tempoArrivo))
						{
							StatisticaACK sa= new StatisticaACK(ackNumber, tempoArrivo);
							f.getNumberACKMap().put(ackNumber,sa);
						}
						
					}
				
				}
		}
		
	}

	private boolean presente(Map<Long, StatisticaACK> numberACKMap,long ackNumber, double tempoArrivo) 
	{
		
		if(numberACKMap.containsKey(ackNumber))
		{
			numberACKMap.get(ackNumber).getListaDuplicatiACK().add(new StatisticaACK(ackNumber, tempoArrivo));
			return true;
		}
		else
		{
			return false;
		}
	}	

}




