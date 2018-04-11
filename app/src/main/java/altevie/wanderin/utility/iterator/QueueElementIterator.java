package altevie.wanderin.utility.iterator;

import altevie.wanderin.utility.exceptions.NoSuchElementException;
import altevie.wanderin.utility.queue.Queue;

public class QueueElementIterator implements Iterator{

	public QueueElementIterator(){ queue = null; cur = null; contatore = 0;}
	public QueueElementIterator(Queue D){ 
		queue = D; 
		contatore = 0;
		if(queue.isEmpty()){
			cur = null;	
		}
		else{
			cur = queue.dequeue();
			queue.enqueue(cur);
			contatore++;
		}

	}
	public boolean hasNext() {return cur != null;}

	public Object next() throws NoSuchElementException {
		if (!hasNext()) throw new NoSuchElementException("No next position");

		Object toReturn = cur;
		if (contatore == queue.size())
			cur = null; // fine della lista: cursore messo a null
		else {
			cur = queue.dequeue(); // sposta il cursore in avanti
			queue.enqueue(cur);
			contatore++;
		}
		return toReturn;
	}
	//	due variabili istanza
private Queue queue; //riferimento alla lista da scandire
private Object cur ; //posizione corrente (next) - cursore
private int contatore;
}
