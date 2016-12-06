package object;

import java.util.ArrayList;
import java.util.List;

public class StatisticaACK 
{
	private long nAck;
	
	private double tempoArrivo;
	
	private List<StatisticaACK> listaDuplicatiACK = new ArrayList<StatisticaACK>();

	
	public StatisticaACK(long nACK,double tempoArrivo)
	{
		this.nAck=nACK;
		this.tempoArrivo=tempoArrivo;
	}
	
	
	public long getnAck() {
		return nAck;
	}

	public void setnAck(long nAck) {
		this.nAck = nAck;
	}

	public double getTempoArrivo() {
		return tempoArrivo;
	}

	public void setTempoArrivo(double tempoArrivo) {
		this.tempoArrivo = tempoArrivo;
	}

	public List<StatisticaACK> getListaDuplicatiACK() {
		return listaDuplicatiACK;
	}

	public void setListaDuplicatiACK(List<StatisticaACK> listaDuplicatiACK) {
		this.listaDuplicatiACK = listaDuplicatiACK;
	}
	
	
	
}
