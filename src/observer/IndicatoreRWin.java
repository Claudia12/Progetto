package observer;

import java.util.Observable;
import java.util.Observer;

import net.sourceforge.jpcap.net.TCPPacket;
import object.Connessione;
import object.Flusso;
import object.Host;

public class IndicatoreRWin implements Observer {

	@Override
	public void update(Observable o, Object arg) 
	{
		System.out.println("SONO L'INDICATORE RWIN ");
		Connessione connessioneDaControllare=(Connessione)o;
		TCPPacket pacchettoArrivato=(TCPPacket)arg;
		Flusso flusso=new Flusso(new Host(pacchettoArrivato.getSourceAddress()),new Host(pacchettoArrivato.getDestinationAddress()),pacchettoArrivato.getSourcePort(),pacchettoArrivato.getDestinationPort());
		
		if(flusso.equals(connessioneDaControllare.getAB()))
		{
			connessioneDaControllare.getAB().getRwin().add(pacchettoArrivato.getWindowSize());
		}
		else if(flusso.equals(connessioneDaControllare.getBA()))
		{
			connessioneDaControllare.getBA().getRwin().add(pacchettoArrivato.getWindowSize());
		}
	}



}
