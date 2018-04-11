package altevie.wanderin.utility.iterator;

import altevie.wanderin.utility.exceptions.NoSuchElementException;
import altevie.wanderin.utility.vector.Vector;

public class VectorElementIterator implements Iterator {
	
	public VectorElementIterator() {vector = null; rango = -1; } // Il costruttore di default
	
	public VectorElementIterator(Vector V) 
	{
		vector = V;
		if (vector.isEmpty()) {rango = -1;}
		else {rango = 0; }
	}
	
	public boolean hasNext() { return (rango != (vector.size())); }
	
	public Object next() throws NoSuchElementException {
		if (!hasNext()) throw new NoSuchElementException("Non esiste tale elemento");
		
		rango++;
		return vector.elementAtRank(rango - 1);		
	}
	
	private Vector vector; // Il vettore sottostante
	private int rango;	
}