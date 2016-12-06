package object;

import java.util.ArrayList;
import java.util.List;

public class StatisticaPacchetto 
{ 
	private long nSequ;

	private int len;
	
	private int lungHeader;
	
	private double tempoArrivo;
	
	private boolean ack;
	
	private boolean syn;
	
	private boolean fyn;
	
	private int rwin;
	
	private List<StatisticaPacchetto> listaDuplicati= new ArrayList<StatisticaPacchetto>();

	public StatisticaPacchetto(long ns,int len,int lungHeader,int rwin,double tempoArrivo,boolean ack,boolean syn, boolean fyn)
	{
		this.nSequ=ns;
		this.len=len;
		this.lungHeader=lungHeader;
		this.rwin=rwin;
		this.tempoArrivo=tempoArrivo;
		this.ack=ack;
		this.syn=syn;
		this.fyn=fyn;
	}
	
	
	public long getnSequ() {
		return nSequ;
	}

	public void setnSequ(long nSequ) {
		this.nSequ = nSequ;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public double getTempoArrivo() {
		return tempoArrivo;
	}

	public void setTempoArrivo(double tempoArrivo) {
		this.tempoArrivo = tempoArrivo;
	}

	public List<StatisticaPacchetto> getListaDuplicati() {
		return listaDuplicati;
	}

	public void setListaDuplicati(List<StatisticaPacchetto> listaDuplicati) {
		this.listaDuplicati = listaDuplicati;
	}

	public boolean isAck() {
		return ack;
	}


	public void setAck(boolean ack) {
		this.ack = ack;
	}


	public boolean isSyn() {
		return syn;
	}


	public void setSyn(boolean syn) {
		this.syn = syn;
	}


	public boolean isFyn() {
		return fyn;
	}


	public void setFyn(boolean fyn) {
		this.fyn = fyn;
	}


	public int getLungHeader() {
		return lungHeader;
	}


	public void setLungHeader(int lungHeader) {
		this.lungHeader = lungHeader;
	}


	@Override
	public boolean equals(Object obj) 
	{
		if(obj==null || !(obj instanceof StatisticaPacchetto))
		{
		return false;
		}
	StatisticaPacchetto sp=(StatisticaPacchetto) obj;
	if(this.nSequ==sp.getnSequ() && this.len==sp.getLen() && this.rwin==sp.getRwin() && this.ack==sp.isAck()
			&& this.syn==sp.isSyn() && this.fyn==sp.isFyn())
	{
		return true;
	}
	
return false;
	}


	public int getRwin() {
		return rwin;
	}


	public void setRwin(int rwin) {
		this.rwin = rwin;
	}
}

