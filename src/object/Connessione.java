package object;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import observer.IndicatoreRTO;
import observer.IndicatoreRTT;
import observer.IndicatoreRWin;
import observer.IndicatoreTOTByte;
import observer.Printer;

public class Connessione extends Observable
{
	private Flusso AB;
	private Flusso BA;
	private boolean changed = false;
	List<Observer> indicatori=new ArrayList<Observer>();
	
	public Connessione(Flusso aB, Flusso bA) 
	{
		
		AB = aB;
		BA = bA;
		indicatori.add(new IndicatoreTOTByte());
		indicatori.add(new IndicatoreRTO());
		indicatori.add(new Printer());
	//	indicatori.add(new IndicatoreRTT());
	
//		indicatori.add(new IndicatoreRipetizioni());
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
	//	System.out.println("io vengo chiamato?");
		Connessione cn=(Connessione)obj;
		
		return (this.AB.equals(cn.getAB()) && this.BA.equals(cn.getBA())) || (this.AB.equals(cn.getBA()) && this.BA.equals(cn.getAB()));
	}


	public boolean isChanged() {
		return changed;
	}


	public void setChanged(boolean changed,Object obj) 
	{
		this.changed = changed;
		//System.out.println("qui");
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
	
	
}
