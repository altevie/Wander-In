package altevie.wanderin.utility.priorityQueue;

import altevie.wanderin.utility.exceptions.EmptyPQException;
import altevie.wanderin.utility.exceptions.InvalidKeyException;

public interface PriorityQueue {

	public int size(); //restituisce il numero di entry della PQ
	public boolean isEmpty(); //verifica se la PQ � vuota
	public Entry min() throws EmptyPQException; //restituisce, senza
	//rimuovere un oggetto di key minimo
	public Entry insert(Object key, Object value) throws InvalidKeyException; //inserisce una coppia key-value e
	// restituisce l�entry che ha creato
	public Entry removeMin() throws EmptyPQException; //rimuove e
	// restituisce l�entry con key minima.
}
