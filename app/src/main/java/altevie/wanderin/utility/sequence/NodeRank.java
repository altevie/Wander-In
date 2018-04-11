package altevie.wanderin.utility.sequence;

import altevie.wanderin.utility.exceptions.InvalidPositionException;
import altevie.wanderin.utility.position.Position;

public class NodeRank implements Position{
	private Object element; // Elemento memorizzato in questa position
	private int rank; //rango dell'elemento
	// costruttori
	public NodeRank() { }
	public NodeRank(Object elem, int r) {
	element = elem;
	rank = r;
	}
	// Metodo dell�interfaccia Position
	public Object element() throws InvalidPositionException {
	if ( element == null ) throw new InvalidPositionException("Position non in una lista!");
	return element;
	}
	// altro metodo di accesso
	public int getRank() { return rank; }
	// Metodi di aggiornamento
	public void setRank(int r) { rank = r; }
	public void setElement(Object newElement) { element = newElement; }
	}
