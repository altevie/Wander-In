package altevie.wanderin.utility.list;

import altevie.wanderin.utility.exceptions.BoundaryViolationException;
import altevie.wanderin.utility.exceptions.EmptyListException;
import altevie.wanderin.utility.exceptions.InvalidPositionException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.position.Position;

public interface List {
	// Metodi generici
	public int size();
	public boolean isEmpty();
	// Metodi di interrogazione
	public boolean isFirst(Position p) throws InvalidPositionException;
	public boolean isLast(Position p) throws InvalidPositionException;
	// Metodi di accesso
	public Position first() throws EmptyListException;
	public Position last() throws EmptyListException;
	public Position before(Position p) throws InvalidPositionException,BoundaryViolationException;
	public Position after(Position p) throws InvalidPositionException,BoundaryViolationException;
	// Metodi di aggiornamento
	public Position insertBefore(Position p, Object o)throws InvalidPositionException;
	public Position insertAfter(Position p, Object o)
	throws InvalidPositionException;
	public Position insertFirst(Object o);
	public Position insertLast(Object o);
	public Object remove(Position p)throws InvalidPositionException;
	public Object replace(Position p, Object o)throws InvalidPositionException;
	public Iterator elements();
	public Iterator positions();
}
