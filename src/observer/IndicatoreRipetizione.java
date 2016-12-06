package observer;

import java.util.Observable;
import java.util.Observer;
import net.sourceforge.jpcap.net.TCPPacket;
import object.Connessione;
import object.Flusso;
import object.Host;
import support.WriterCSV;

public class IndicatoreRipetizione implements Observer {

	@Override
	public void update(Observable o, Object arg) 
	{
		Connessione connessioneDaControllare=(Connessione)o;
		TCPPacket pacchettoArrivato=(TCPPacket)arg;
	    Flusso flusso=new Flusso(new Host(pacchettoArrivato.getSourceAddress()),new Host(pacchettoArrivato.getDestinationAddress()),pacchettoArrivato.getSourcePort(),pacchettoArrivato.getDestinationPort());

	    if(flusso.equals(connessioneDaControllare.getAB()))
	    {
	    	check(connessioneDaControllare,connessioneDaControllare.getAB(),pacchettoArrivato);
	    }
	    else if(flusso.equals(connessioneDaControllare.getBA()))
	    {
	    	check(connessioneDaControllare,connessioneDaControllare.getBA(),pacchettoArrivato);
	    }
	}

	private void check(Connessione c,Flusso f, TCPPacket p) 
	{
		
		double microInSecondi=(double)p.getTimeval().getMicroSeconds()/1000000;  
		double tempoArrivo=p.getTimeval().getSeconds() + microInSecondi;
		double ripetizione=f.getRipetizione();
		f.setRipetizione();
		if(f.getRipetizione()!=ripetizione)//vuol dire che qualcosa è cambiato
		{
			
			WriterCSV wr=new WriterCSV();
			wr.writeInCSV(c, f,"RepeatRate" ,f.getRipetizione(), tempoArrivo);
		}
	}

}
