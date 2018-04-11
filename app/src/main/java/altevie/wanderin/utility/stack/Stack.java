package altevie.wanderin.utility.stack;

import altevie.wanderin.utility.exceptions.EmptyStackException;
import altevie.wanderin.utility.iterator.Iterator;

public interface Stack {
	public int size();
	public boolean isEmpty();
	public Object Top() throws EmptyStackException;
	public void push(Object o);
	public Object pop() throws EmptyStackException;
	public Iterator elements();
}
