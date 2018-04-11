package altevie.wanderin.utility.iterator;

import altevie.wanderin.utility.exceptions.NoSuchElementException;
import altevie.wanderin.utility.list.List;
import altevie.wanderin.utility.list.NodeList;
import altevie.wanderin.utility.position.Position;

public class ListPositionIterator implements Iterator
{
	//	creo un iteratore su collezione vuota
	public ListPositionIterator(){ list = null; cur = null; }
	public ListPositionIterator(List L)
	{ //creo un iteratore sulla lista L
		list = L;
		if (list.isEmpty()) 
			cur = null; // lista vuota: cursore � messo a null
		else 
			cur = list.first(); //altrimenti � la prima posizione
	}
	
	public boolean hasNext() { return (cur != null); }
	
	public Object next() throws NoSuchElementException {
		if (!hasNext()) throw new NoSuchElementException("No next position");
		
		Position toReturn = cur;
		if (cur == list.last()) 
			cur = null; // fine della lista: cursore messo a null
		else 
			cur = list.after(cur); // sposta il cursore in avanti
		
		return toReturn;
	}	
	
	public Iterator attach(Iterator J){
		List M = new NodeList();
		while(this.hasNext())
			M.insertLast(this.next());
		while(J.hasNext())
			M.insertLast(J.next());
		return M.elements();
	}
	
	//	due variabili istanza
	private List list; //riferimento alla lista da scandire
	private Position cur = null; //posizione corrente (next) - cursore
}