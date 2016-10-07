package observer;

import java.util.Observable;
import java.util.Observer;

import net.sourceforge.jpcap.net.TCPPacket;
import object.Connessione;
import object.Flusso;
import object.Host;

public class IndicatoreRipetizione implements Observer {

	@Override
	public void update(Observable o, Object arg) 
	{
		System.out.println("SONO L'INDICATORE DI RIPETIZIONE ");
		Connessione connessioneDaControllare=(Connessione)o;
		TCPPacket pacchettoArrivato=(TCPPacket)arg;
	    Flusso flusso=new Flusso(new Host(pacchettoArrivato.getSourceAddress()),new Host(pacchettoArrivato.getDestinationAddress()),pacchettoArrivato.getSourcePort(),pacchettoArrivato.getDestinationPort());

	    if(flusso.equals(connessioneDaControllare.getAB()))
	    {
	    	check(connessioneDaControllare.getAB(),pacchettoArrivato);
	    }
	    else if(flusso.equals(connessioneDaControllare.getBA()))
	    {
	    	check(connessioneDaControllare.getAB(),pacchettoArrivato);
	    }
	}

	private void check(Flusso f, TCPPacket p) 
	{
		f.setRipetizione();
	}

}
