package observer;


import java.util.Observable;
import java.util.Observer;


import net.sourceforge.jpcap.net.TCPPacket;
import object.Connessione;
import object.Flusso;
import object.Host;
import support.WriterCSV;

public class IndicatoreRWIN implements Observer {

	@Override
	public void update(Observable o, Object arg) 
	{
		Connessione connessioneDaControllare=(Connessione)o;
		TCPPacket pacchettoArrivato=(TCPPacket)arg;
		Flusso flusso=new Flusso(new Host(pacchettoArrivato.getSourceAddress()),new Host(pacchettoArrivato.getDestinationAddress()),pacchettoArrivato.getSourcePort(),pacchettoArrivato.getDestinationPort());
		double microInSecondi=(double)pacchettoArrivato.getTimeval().getMicroSeconds()/1000000;  
		double tempoArrivo=pacchettoArrivato.getTimeval().getSeconds() + microInSecondi;
		if(flusso.equals(connessioneDaControllare.getAB()))
		{
			connessioneDaControllare.getAB().getRwin().add(pacchettoArrivato.getWindowSize());
			new WriterCSV().writeInCSV(connessioneDaControllare,connessioneDaControllare.getAB(), "RWIN", pacchettoArrivato.getWindowSize(), tempoArrivo);
		}
		else if(flusso.equals(connessioneDaControllare.getBA()))
		{
			connessioneDaControllare.getBA().getRwin().add(pacchettoArrivato.getWindowSize());
			new WriterCSV().writeInCSV(connessioneDaControllare,connessioneDaControllare.getBA(), "RWIN", pacchettoArrivato.getWindowSize(), tempoArrivo);
		}
	}

}
