package object;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



public class Flusso  
{
	private Host A;
	private Host B;
	private int portaSorgente;
	private int portaDestinazione;
	private Map<Long, StatisticaPacchetto> numberSequenceMap = new TreeMap<Long,StatisticaPacchetto>();
	private Map<String, StatisticaPacchetto> numberSequenceMapZero= new TreeMap<String,StatisticaPacchetto>();
	private Map<Long,StatisticaACK> numberACKMap=new TreeMap<Long,StatisticaACK>();
	
	private List<Double> rttList= new ArrayList<Double>();
	private List<Double> rtoList= new ArrayList<Double>();
	private List<Integer> rwin= new ArrayList<Integer>();
	private double ripetizione;
	private double tempoPartenza=0;
	private Integer totByte=0;
	private List<Integer> byteSec= new ArrayList<Integer>();
	private int numeroPacchettiTotali=0;
	private int duplicatiTotale=0;
	
	public Flusso(Host A,Host B,int ps,int pd)
	{
		this.A=A;
		this.B=B;
		portaSorgente=ps;
		portaDestinazione=pd;
		totByte=0;
		numeroPacchettiTotali=0;
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

	public Map<Long, StatisticaPacchetto> getNumberSequenceMap() 
	{
		return numberSequenceMap;
	}

	public double getRipetizione() 
	{
		return (double)ripetizione;
	}

	public void setRipetizione()
	{
		ripetizione=((double)duplicatiTotale/numeroPacchettiTotali)*100;
	
	}

	public List<Integer> getByteSec() {
		return byteSec;
	}

	public void setByteSec(List<Integer> byteSec) {
		this.byteSec = byteSec;
	}

	public List<Double> getRttList() {
		return rttList;
	}

	public List<Double> getRtoList() {
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
		this.totByte += b;
	}

	public void resetTotByte()
	{
		this.totByte=0;
	}
	public int getNumeroPacchettiTotali() 
	{
		return numeroPacchettiTotali;
	}

	public void setNumeroPacchettiTotali() 
	{
		numeroPacchettiTotali++;
	}

	public void setDuplicatiTotale()
	{	
		duplicatiTotale++;
	}


	@Override
	public String toString() 
	{
		String f="Flusso con Host A :" + A + " e Host B : " + B;
		return f;
	}

	public Map<Long,StatisticaACK> getNumberACKMap() {
		return numberACKMap;
	}

	public void setNumberACKMap(Map<Long,StatisticaACK> numberACKMap) {
		this.numberACKMap = numberACKMap;
	}

	public Map<String, StatisticaPacchetto> getNumberSequenceMapZero() {
		return numberSequenceMapZero;
	}

	public void setNumberSequenceMapZero(Map<String, StatisticaPacchetto> numberSequenceMapZero) {
		this.numberSequenceMapZero = numberSequenceMapZero;
	}

	public double getTempoPartenza() {
		return tempoPartenza;
	}

	public void setTempoPartenza(double tempoPartenza) {
		this.tempoPartenza = tempoPartenza;
	}


	public String IDFlusso()
	{
		return A+":"+portaSorgente+":"+B+":"+portaDestinazione;
	}
}
