package altevie.wanderin.utility.iterator;

import altevie.wanderin.utility.exceptions.NoSuchElementException;
import altevie.wanderin.utility.position.Position;
import altevie.wanderin.utility.sequence.Sequence;

public class SequenceElementIterator extends ListPositionIterator
{	
	public SequenceElementIterator(){ super(); }
	public SequenceElementIterator(Sequence L){ super(L);}
	public boolean hasNext() {return super.hasNext();}
	public Object next() throws NoSuchElementException{
		return ((Position)super.next()).element();
	}	
}