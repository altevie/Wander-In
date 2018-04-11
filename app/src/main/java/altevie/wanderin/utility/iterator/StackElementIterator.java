package altevie.wanderin.utility.iterator;

import altevie.wanderin.utility.exceptions.NoSuchElementException;
import altevie.wanderin.utility.stack.ArrayStack;
import altevie.wanderin.utility.stack.DequeStack;
import altevie.wanderin.utility.stack.LinkedStack;
import altevie.wanderin.utility.stack.QueueStack;
import altevie.wanderin.utility.stack.SequenceStack;
import altevie.wanderin.utility.stack.Stack;
import altevie.wanderin.utility.stack.TwoQueueStack;
import altevie.wanderin.utility.stack.VectorStack;

public class StackElementIterator implements Iterator{
	public StackElementIterator(){stack = null; cur = null;}
	public StackElementIterator(Stack S)
	{	
		stack = S;
		
		if(S instanceof ArrayStack)
			cloned = (ArrayStack) ((ArrayStack)stack).clone();
		
		if(S instanceof DequeStack)
			cloned = (DequeStack) ((DequeStack)stack).clone();

		if(S instanceof QueueStack)
			cloned = (QueueStack) ((QueueStack)stack).clone();
		
		if(S instanceof VectorStack)
			cloned = (VectorStack) ((VectorStack)stack).clone();
		
		if(S instanceof TwoQueueStack)
			cloned = (TwoQueueStack) ((TwoQueueStack)stack).clone();
		
		if(S instanceof LinkedStack)
			cloned = (LinkedStack) ((LinkedStack)stack).clone();
		
		if(S instanceof SequenceStack)
			cloned = (SequenceStack) ((SequenceStack)stack).clone();
		
		if(cloned.isEmpty())
			cur = null;
		
		cur = cloned.pop();		
	}
	
	public boolean hasNext() {return (cur != null);}

	public Object next() throws NoSuchElementException {
		if (!hasNext()) throw new NoSuchElementException("No next position");

		Object toReturn = cur;
		if (cloned.isEmpty()) 
			cur = null; // fine della lista: cursore messo a null
		else 
			cur = cloned.pop(); // sposta il cursore in avanti

		return toReturn;
	}
private Stack stack;
private Stack cloned; 
private Object cur;
}
