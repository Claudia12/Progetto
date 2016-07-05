package object;

import java.util.ArrayList;
import java.util.HashMap;
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
	private static Map<Long,Integer> numberSequenceList = new HashMap<>();
	private static Map<Long,Integer> numberSequenceListRepeat = new HashMap<>();
	private List<Long> rttList= new ArrayList<Long>();
	private List<Long> rtoList= new ArrayList<Long>();
	private List<Integer> rwin= new ArrayList<Integer>();
	private int ripetizione;
	private Integer totByte;
	private List<Integer> byteSec= new ArrayList<Integer>();
	private int contatore=0;
	private int numeroPacchettiTotali;
	
	
	
	public Flusso(Host A,Host B,int ps,int pd)
	{
		this.A=A;
		this.B=B;
		portaSorgente=ps;
		portaDestinazione=pd;
		totByte=0;
		numeroPacchettiTotali=0;
		creaTimer();
	}

	private void creaTimer() 
	{
		Timer timer = new Timer();
        timer.schedule(new TimerTask() 
        {
            public void run()
            {
             setContatore();
            }

        }, 1000, 1000);	
	}

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
	 Flusso f=(Flusso) obj;
	 if(this.A.equals(f.getA()) && this.B.equals(f.getB()) && this.portaSorgente==f.getPortaSorgente() && this.portaDestinazione==f.getPortaDestinazione())
	 {
		// System.out.println("uguale");
		 return true;
	 }
	 else
		 return false;
	}

	public Map<Long, Integer> getNumberSequenceList() 
	{
		//System.out.println(A.hostname+" - "+B.hostname);
		return numberSequenceList;
	}

	public int getRipetizione() {
		return ripetizione;
	}

	public void setRipetizione(int ripetizione) {
		this.ripetizione = ripetizione;
	}

	public List<Integer> getByteSec() {
		return byteSec;
	}

	public void setByteSec(List<Integer> byteSec) {
		this.byteSec = byteSec;
	}

	public Map<Long, Integer> getNumberSequenceListRepeat() {
		return numberSequenceListRepeat;
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

	public void setTotByte(Integer totByte) {
		this.totByte = totByte;
	}

	public int getContatore() {
		return contatore;
	}

	private void setContatore()
	{
		//System.out.println(A.hostname+" - "+B.hostname+" contatore : "+contatore);
		if(contatore<5)
		{		
			this.contatore++;
		}
		else
		{
			System.out.println("calcola");
			contatore=0;
			calcola();
			
		}
		
	}

	private void calcola() 
	{
		System.out.println("totbyte prima "+totByte);
		
		if(totByte!=0)
		{
			System.out.println("totbyte ora "+totByte);
			byteSec.add(totByte/5);
		}
		else
		{
			
			byteSec.add(totByte);
		}
		
		for(Integer i:byteSec)
		{
			System.out.println("byte/sec = " + i);
		}  
	}
   
	
	

}
