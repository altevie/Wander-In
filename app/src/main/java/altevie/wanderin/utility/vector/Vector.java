package altevie.wanderin.utility.vector;

import altevie.wanderin.utility.exceptions.BoundaryViolationException;
import altevie.wanderin.utility.iterator.Iterator;

public interface Vector {
	public Object removeAtRank(int r)throws BoundaryViolationException;
	public void insertAtRank(int r, Object e)throws BoundaryViolationException;
	public Object replaceAtRank(int r, Object e)throws BoundaryViolationException;
	public Object elementAtRank(int r)throws BoundaryViolationException;
	public boolean isEmpty();
	public int size();
	public Iterator elements();
}