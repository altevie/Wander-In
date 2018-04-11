package altevie.wanderin.utility.iterator;

import altevie.wanderin.utility.deque.Deque;
import altevie.wanderin.utility.exceptions.NoSuchElementException;

public class DequeElementIterator implements Iterator{
	public DequeElementIterator(){ deque = null; cur = null; contatore = 0;}
	public DequeElementIterator(Deque D){ 
		deque = D; 
		contatore = 0;
		if(deque.isEmpty()){
			cur = null;	
		}
		else{
			cur = deque.removeFirst();
			deque.insertLast(cur);
			contatore++;
		}

	}
	public boolean hasNext() {return cur != null;}

	public Object next() throws NoSuchElementException {
		if (!hasNext()) throw new NoSuchElementException("No next position");

		Object toReturn = cur;
		if (contatore == deque.size())
			cur = null; // fine della lista: cursore messo a null
		else {
			cur = deque.removeFirst(); // sposta il cursore in avanti
			deque.insertLast(cur);
			contatore++;
		}
		return toReturn;
	}
	//	due variabili istanza
private Deque deque; //riferimento alla lista da scandire
private Object cur ; //posizione corrente (next) - cursore
private int contatore;
}
