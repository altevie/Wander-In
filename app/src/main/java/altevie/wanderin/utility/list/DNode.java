package altevie.wanderin.utility.list;

import altevie.wanderin.utility.exceptions.InvalidPositionException;
import altevie.wanderin.utility.position.Position;

public class DNode implements Position {
	//Costruttori
	public DNode(Object e, DNode p, DNode n)
	{
	element = e;
	next = n;
	prev = p;
	}
	
	/* FUNZIONALITA' BASE DEL DNODE */
	
	public void setElement(Object newElem){ element = newElem; }
	public void setNext(DNode newNext){ next = newNext; }
	public void setPrev(DNode newPrev){ prev = newPrev; }
	
	public Object element() throws InvalidPositionException
	{
		if ((prev == null) && (next == null))
			throw new InvalidPositionException("Posizione non valida");

		return element;
	}
	public DNode getNext(){ return next; }
	public DNode getPrev(){ return prev; }
	
	/* FUNZIONALITA' ACCESSORIE DEL DNODE */
	
	public String toString(){
		return (String)element;
	}
	
	public boolean equals(Object obj){
		DNode node = (DNode)obj;
		return (this.element().equals(node.element()));
		
	}
	public boolean eq(Object obj){
		DNode node = (DNode)obj;
		if(this.element().equals(node.element()))
			if(this.next.equals(node.next));
				if(this.prev.equals(node.prev))
					return true;
				
		return false;
	}
	public Object clone(){
		return new DNode(this.element(),this.getPrev(),this.getNext());	
	}
	
// Variabili istanza
private Object element;
private DNode next, prev; //posso riferirmi al nodo precedente e successivo

}
