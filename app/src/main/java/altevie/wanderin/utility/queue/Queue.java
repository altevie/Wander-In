package altevie.wanderin.utility.queue;

import altevie.wanderin.utility.exceptions.EmptyQueueException;
import altevie.wanderin.utility.iterator.Iterator;

public interface Queue {
	
public int size();
public boolean isEmpty();
public Object front()throws EmptyQueueException;
public void enqueue(Object o);
public Object dequeue()throws EmptyQueueException;
public Iterator elements();
}
