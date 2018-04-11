package altevie.wanderin.utility.iterator;


import altevie.wanderin.utility.exceptions.NoSuchElementException;
import altevie.wanderin.utility.position.Position;
import altevie.wanderin.utility.sequence.Sequence;

public class SequencePositionIterator implements Iterator
{
	//	creo un iteratore su collezione vuota
	public SequencePositionIterator(){ sequence = null; cur = null; }
	public SequencePositionIterator(Sequence S)
	{ //creo un iteratore sulla sequenza S
		sequence = S;
		if (sequence.isEmpty()) 
			cur = null; // sequenza vuota: cursore � messo a null
		else 
			cur = sequence.first(); //altrimenti � la prima posizione
	}
	
	public boolean hasNext() { return (cur != null); }
	
	public Object next() throws NoSuchElementException {
		if (!hasNext()) throw new NoSuchElementException("No next position");
		
		Position toReturn = cur;
		if (cur == sequence.last()) 
			cur = null; // fine della sequenza: cursore messo a null
		else 
			cur = sequence.after(cur); // sposta il cursore in avanti
		
		return toReturn;
	}	
	
	//	due variabili istanza
	private Sequence sequence; //riferimento alla sequenza da scandire
	private Position cur = null; //posizione corrente (next) - cursore
}
