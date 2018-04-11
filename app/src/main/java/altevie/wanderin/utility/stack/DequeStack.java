package altevie.wanderin.utility.stack;
////////////////////////////controllare le equals e chiamarle equal

import altevie.wanderin.utility.deque.DLinkedDeque;
import altevie.wanderin.utility.exceptions.EmptyDequeException;
import altevie.wanderin.utility.exceptions.EmptyStackException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.StackElementIterator;

public class DequeStack implements Stack{

	public DequeStack() {
		super();
		deque = new DLinkedDeque();
	}
	
	/* FUNZIONALITA' BASE DELLO STACK */
	
	public void push(Object o) {deque.insertLast(o);}
	
	public boolean isEmpty() {return deque.isEmpty();}
	
	public int size() {	return deque.size();}
	
	public Object Top() throws EmptyStackException {
		try{
			if(deque.isEmpty())
				throw new EmptyDequeException("");
		}catch(EmptyDequeException e){
			throw new EmptyStackException("Stack VUOTO!!");
		}
		
		return deque.last();
	}
	
	public Object pop() throws EmptyStackException {
		try{
			if(deque.isEmpty())
				throw new EmptyDequeException("");
		}catch(EmptyDequeException e){
			throw new EmptyStackException("Stack VUOTO!!");
		}
		
		return deque.removeLast();
	}
	public Iterator elements() {return new StackElementIterator(this);}
	
	/* FUNZIONALITA' ACCESSORIE DELLO STACK */
	
	public String toString()
	{
		
		return deque.toString();
	}

	public boolean equals(Object obj)
	{ 
		DequeStack appoggio = new DequeStack();
		DequeStack stack = (DequeStack)obj;
		if(this.size()!= stack.size())
			return false;
		
		Object elemento1= null;
		Object elemento2 = null;
		boolean var = true;
		
		while(!this.isEmpty()){
			try {
				elemento1= this.pop();
				elemento2= stack.pop();
				if(elemento1.equals(elemento2))
					appoggio.push(elemento1);
				else{
					this.push(elemento1);
					stack.push(elemento2);
					var = false;
					break;
				}
			} catch (EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}

		Object temp=null;
		while(!appoggio.isEmpty()){
			
			try {
				temp =appoggio.pop();
				this.push(temp);
				stack.push(temp);
			} catch (EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return var;	
	}
	
	public Object clone(){
		DequeStack appoggio = new DequeStack();
		DequeStack cloned = new DequeStack();
		
		while(!this.isEmpty()){
			try {
				appoggio.push(this.pop());
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
		}
		Object temp=null;
		while(!appoggio.isEmpty()){
			try {
				temp = appoggio.pop();
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
			this.push(temp);
			cloned.push(temp);
		}
		return cloned;
	}
private DLinkedDeque deque;
}
