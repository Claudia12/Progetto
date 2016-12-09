package object;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import observer.IndicatoreRTO;
import observer.IndicatoreRTT;
import observer.IndicatoreRWIN;
import observer.IndicatoreRipetizione;
import observer.IndicatoreTOTByte;


public class Connessione extends Observable
{	
	private Flusso AB;
	private Flusso BA;
	private boolean changed = false;
	private List<Observer> indicatori=new ArrayList<Observer>();
	private String nomeFile;
	
	public Connessione(Flusso aB, Flusso bA) 
	{
		
		this.AB = aB;
		this.BA = bA;
		
		indicatori.add(new IndicatoreRTO());
		indicatori.add(new IndicatoreRWIN());
		indicatori.add(new IndicatoreRTT());
		indicatori.add(new IndicatoreTOTByte());
		indicatori.add(new IndicatoreRipetizione());

	}


	public Flusso getAB() {
		return AB;
	}


	public Flusso getBA() {
		return BA;
	}
	
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj==null || !(obj instanceof Connessione))
		{
		return false;
		}
		Connessione cn=(Connessione)obj;
		
		return (this.AB.equals(cn.getAB()) && this.BA.equals(cn.getBA())) || (this.AB.equals(cn.getBA()) && this.BA.equals(cn.getAB()));
	}


	@Override
	public String toString() 
	{
		String c="Connessione con Flusso AB "+ AB.toString() +" e Flusso BA "+BA.toString();
		
		return c;
	}


	public boolean isChanged() {
		return changed;
	}


	public void setChanged(boolean changed,Object obj) 
	{
		this.changed = changed;
		this.notifyObservers(obj);
	}


	@Override
	public void notifyObservers(Object arg) 
	{
		 if (!changed)
             return;

     for(Observer i:indicatori)
     {
    	 i.update(this, arg);
     }
	}


	public String getNomeFile() 
	{
		return nomeFile;
	}


	public void setNomeFile(String nomeFile)
	{
	this.nomeFile=nomeFile;
	}
	
}
