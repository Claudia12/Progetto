package object;

import java.util.ArrayList;
import java.util.List;

public class StatisticaPacchetto 
{ 
	private long nSequ;


	private int len;
	
	private double tempoArrivo;
	
	private List<StatisticaPacchetto> listaDuplicati= new ArrayList<StatisticaPacchetto>();

	public StatisticaPacchetto(long ns,int len,double tempoArrivo)
	{
		this.nSequ=ns;
		this.len=len;
		this.tempoArrivo=tempoArrivo;
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

	
	
}

