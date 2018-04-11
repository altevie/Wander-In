package altevie.wanderin.utility.iterator;

import altevie.wanderin.utility.exceptions.NoSuchElementException;
import altevie.wanderin.utility.linkedlist.LinkedList;
import altevie.wanderin.utility.linkedlist.Node;

public class LinkedListElementIterator implements Iterator {

	//	creo un iteratore su collezione vuota
	public LinkedListElementIterator(){ list = null; cur = null; contatore =0;}
	public LinkedListElementIterator(LinkedList L)
	{ //creo un iteratore sulla lista L
		list = L;
		if (list.isEmpty()) {
			cur = null; // lista vuota: cursore � messo a null
			list = null;
			contatore =0;
		}
		else {
			cur = list.cancellaInTesta(); //altrimenti � la prima posizione
			list.inserisciInCoda(cur);
			contatore++;
		}
	}
	
	public boolean hasNext() { return (cur != null); }
	
	public Object next() throws NoSuchElementException {
		if (!hasNext()) throw new NoSuchElementException("No next position");
		
		Node toReturn = cur;
		if (contatore == list.size()) 
			cur = null; // fine della lista: cursore messo a null
		else {
			cur = list.cancellaInTesta(); // sposta il cursore in avanti
			list.inserisciInCoda(cur);
			contatore++;
		}
		return toReturn;
	}	
	
	//	due variabili istanza
	private LinkedList list; //riferimento alla lista da scandire
	private Node cur = null; //posizione corrente (next) - cursore 
	private int contatore ;
}
