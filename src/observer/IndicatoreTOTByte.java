package observer;

import java.util.Observable;
import java.util.Observer;


import net.sourceforge.jpcap.net.TCPPacket;
import object.Connessione;
import object.Flusso;
import object.Host;
import support.WriterCSV;

public class IndicatoreTOTByte implements Observer {

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

	private void check(Connessione c, Flusso f, TCPPacket p)
	{

		double microInSecondi=(double)p.getTimeval().getMicroSeconds()/1000000;
		double tmp=p.getTimeval().getSeconds()+microInSecondi;
		int byteArrivati=p.getHeaderLength()+p.getPayloadDataLength();
		double secondiTrascorsi=tmp-f.getTempoPartenza();
		if(f.getTempoPartenza()!=0)
		{
			if(secondiTrascorsi<1)//non è passato un secondo percio sommo
			{
				f.setTotByte(byteArrivati);
			}
			else
			{
				int dati=(int) Math.round((f.getTotByte()/secondiTrascorsi));//(f.getTotByte()/secondiTrascorsi);

				f.getByteSec().add(dati);
				new WriterCSV().writeInCSV(c,f, "Byte/sec", dati, tmp);
				f.resetTotByte();
				f.setTotByte(byteArrivati);
				f.setTempoPartenza(tmp);
			}

		}
		else
		{	
			f.setTempoPartenza(tmp);
			f.setTotByte(byteArrivati);
		}

	}

}

