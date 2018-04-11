package altevie.wanderin.utility.deque;

import altevie.wanderin.utility.exceptions.EmptyDequeException;
import altevie.wanderin.utility.iterator.DequeElementIterator;

public interface Deque {
	public void insertFirst(Object o);
	public void insertLast(Object o);
	public Object removeFirst() throws EmptyDequeException;
	public Object removeLast() throws EmptyDequeException;
	public boolean isEmpty();
	public Object first() throws EmptyDequeException;
	public Object last() throws EmptyDequeException;
	public int size();
	public DequeElementIterator elements();
}