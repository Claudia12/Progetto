package object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;



public class Flusso  
{
	private Host A;
	private Host B;
	private int portaSorgente;
	private int portaDestinazione;
	private Map<Long, Integer> numberSequenceMap = new HashMap<Long,Integer>();//cambiare
	//private List<Long> pacchettiDiCuiHoRicevutoACK=new ArrayList<Long>(); 
	private Hashtable<Integer,SequenceNumberObject>copieDiNumberSequence=new Hashtable<Integer,SequenceNumberObject>();
	
	private List<Long> rttList= new ArrayList<Long>();
	private List<Long> rtoList= new ArrayList<Long>();
	private List<Integer> rwin= new ArrayList<Integer>();
	private long ripetizione;
	private Integer totByte;
	private List<Integer> byteSec= new ArrayList<Integer>();
	private int contatore=0;
	private int numeroPacchettiTotali;
	private int duplicatiTotale=0;
	
	public Flusso(Host A,Host B,int ps,int pd)
	{
		this.A=A;
		this.B=B;
		portaSorgente=ps;
		portaDestinazione=pd;
		totByte=0;
		numeroPacchettiTotali=0;
		//creaTimer();
	}

//	private void creaTimer() 
//	{
//		Timer timer = new Timer();
//        timer.schedule(new TimerTask() 
//        {
//            public void run()
//            {
//            //System.out.println("vengo eseguita");
//            setContatore();
//            }
//
//        }, 1000, 1000);	
//	}

	public Host getA() {
		return A;
	}


	public Host getB() {
		return B;
	}
	
	public int getPortaSorgente() {
		return portaSorgente;
	}

	public int getPortaDestinazione() {
		return portaDestinazione;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
	//	System.out.println("che succede?");
		if(obj==null || !(obj instanceof Flusso))
			{
			return false;
			}
		Flusso f=(Flusso) obj;
		if(this.A.equals(f.getA()) && this.B.equals(f.getB()) && this.portaSorgente==f.getPortaSorgente() && this.portaDestinazione==f.getPortaDestinazione())
		{
		// System.out.println("uguale");
			return true;
		}
		
	return false;
	}

	public Map<Long, Integer> getNumberSequenceMap() 
	{
		//System.out.println("Faccio il get");
		return numberSequenceMap;
	}

	public long getRipetizione() 
	{
		return ripetizione;
	}

	public void setRipetizione()
	{
		System.out.println("duplicati Totali : "+duplicatiTotale+" e pacchetti totali : "+numeroPacchettiTotali);
		ripetizione=duplicatiTotale/numeroPacchettiTotali;
		System.out.println("ripetizione uguale : "+ripetizione);
	
	}

	public List<Integer> getByteSec() {
		return byteSec;
	}

	public void setByteSec(List<Integer> byteSec) {
		this.byteSec = byteSec;
	}

	public List<Long> getRttList() {
		return rttList;
	}

	public List<Long> getRtoList() {
		return rtoList;
	}

	public List<Integer> getRwin() {
		return rwin;
	}

	public Integer getTotByte() {
		return totByte;
	}

	public void setTotByte(Integer b) 
	{
		System.out.println("tot byte prima "+totByte+" cosa passo alla funzione "+b);
		this.totByte += b;
		System.out.println("tot byte dopo "+totByte+" cosa passo alla funzione "+b);
	}

	private void calcola() 
	{
		System.out.println("totbyte prima "+totByte);
		
		if(totByte!=0)
		{
			System.out.println("totbyte ora "+totByte);
			byteSec.add(totByte/5);
			totByte=0;
		}
		else
		{
			System.out.println("sono entrata nel else");
			System.out.println("totByte : "+totByte);
			byteSec.add(totByte);
		}
		
//		for(Integer i:byteSec)
//		{
//			System.out.println("byte/sec = " + i);
//		}  
	}

	public int getNumeroPacchettiTotali() {
		return numeroPacchettiTotali;
	}

	public void setNumeroPacchettiTotali() 
	{
		numeroPacchettiTotali++;
	}

	public Hashtable<Integer,SequenceNumberObject> getCopieDiNumberSequence() {
		return copieDiNumberSequence;
	}

	public void setCopieDiNumberSequence(Hashtable<Integer, SequenceNumberObject> copieDiNumberSequence) {
		this.copieDiNumberSequence = copieDiNumberSequence;
	}
   
	
	public void setDuplicatiTotale()
	{	
		duplicatiTotale++;
	}


	private void setContatore() 
	{
	
		if(contatore<5)
		{
			 contatore++;
		}
		else
		{
			calcola();
			contatore=0;
		}
	}

	@Override
	public String toString() 
	{
		String f="Flusso con Host A :" + A + " e Host B : " + B;
		return f;
	}

//	public List<Long> getPacchettiDiCuiHoRicevutoACK() {
//		return pacchettiDiCuiHoRicevutoACK;
//	}
//
//	public void setPacchettiDiCuiHoRicevutoACK(
//			List<Long> pacchettiDiCuiHoRicevutoACK) {
//		this.pacchettiDiCuiHoRicevutoACK = pacchettiDiCuiHoRicevutoACK;
//	}

}
