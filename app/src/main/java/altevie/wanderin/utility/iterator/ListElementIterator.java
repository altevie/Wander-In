package altevie.wanderin.utility.iterator;

import altevie.wanderin.utility.exceptions.NoSuchElementException;
import altevie.wanderin.utility.list.List;
import altevie.wanderin.utility.position.Position;

public class ListElementIterator extends ListPositionIterator{
	
	public ListElementIterator(){ super(); }
	public ListElementIterator(List L){ super(L);}
	public boolean hasNext() {return super.hasNext();}
	public Object next() throws NoSuchElementException{
		return ((Position)super.next()).element();
	}	
}
