package altevie.wanderin.utility.iterator;

import altevie.wanderin.utility.exceptions.NoSuchElementException;

public interface Iterator 
{
	public boolean hasNext();
	public Object next() throws NoSuchElementException;
}
