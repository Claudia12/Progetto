package observer;

import java.util.Observable;
import java.util.Observer;

import net.sourceforge.jpcap.net.TCPPacket;
import object.Connessione;
import object.Flusso;
import object.Host;

public class IndicatoreTOTByte implements Observer {

	@Override
	public void update(Observable o, Object arg) 
	{
	//	System.out.println("SONO L'INDICATORE TotByte ");
		Connessione connessioneDaControllare=(Connessione)o;
		TCPPacket pacchettoArrivato=(TCPPacket)arg;
		Flusso flusso=new Flusso(new Host(pacchettoArrivato.getSourceAddress()),new Host(pacchettoArrivato.getDestinationAddress()),pacchettoArrivato.getSourcePort(),pacchettoArrivato.getDestinationPort());
		if(flusso.equals(connessioneDaControllare.getAB()))
		{
			check(connessioneDaControllare.getAB(), pacchettoArrivato);	

		}
		else if(flusso.equals(connessioneDaControllare.getBA()))
		{
		check(connessioneDaControllare.getBA(), pacchettoArrivato);	
		}
//		pacchettoArrivato.getTCPData();
//		pacchettoArrivato.getHeaderLength();
//		pacchettoArrivato.getPayloadDataLength();
		
	}

	private void check(Flusso f, TCPPacket p)
	{
		int somma=f.getTotByte()+p.getPayloadDataLength();
		System.out.println("somma in BA "+somma);
		f.setTotByte(somma);

	}
}
