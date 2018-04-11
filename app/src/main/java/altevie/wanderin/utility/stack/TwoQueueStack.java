package altevie.wanderin.utility.stack;

import altevie.wanderin.utility.exceptions.EmptyQueueException;
import altevie.wanderin.utility.exceptions.EmptyStackException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.StackElementIterator;
import altevie.wanderin.utility.queue.ArrayQueue;
import altevie.wanderin.utility.queue.Queue;

public class TwoQueueStack implements Stack{

	public TwoQueueStack() {
		queue1 = new ArrayQueue(false);
		queue2 = new ArrayQueue(false);
	}

	/* FUNZIONALITA' BASE DELLO STACK */
	
	public void push(Object o){queue1.enqueue(o);}
	
	public int size(){return queue1.size();}
	
	public boolean isEmpty(){return queue1.isEmpty();}
	
	public Object Top() throws EmptyStackException
	{
		if(queue1.isEmpty())
			throw new EmptyStackException("Stack VUOTO!!");
		
		while(queue1.size() > 1)
		{
			try {
				queue2.enqueue(queue1.dequeue());
			} catch (EmptyQueueException e) {
				throw new EmptyStackException("Stack VUOTO!!");
			}
		}
		
		try {
			Object s = queue1.dequeue();
			queue2.enqueue(s);
			
			while(!queue2.isEmpty()){
				queue1.enqueue(queue2.dequeue());
			}
			return s;		
		} catch (EmptyQueueException e) {
			throw new EmptyStackException("Stack VUOTO!!");
		}			
	}

	public Object pop() throws EmptyStackException {
		if(queue1.isEmpty())
			throw new EmptyStackException("Stack VUOTO!!");
		
		while(queue1.size() > 1){
			try {
				queue2.enqueue(queue1.dequeue());
			} catch (EmptyQueueException e) {
				throw new EmptyStackException("Stack VUOTO!!");
			}		
		}
		
		try {
			Object s = queue1.dequeue();
			while(!queue2.isEmpty()){
				queue1.enqueue(queue2.dequeue());	
			}
			return s;
		} catch (EmptyQueueException e) {
			throw new EmptyStackException("Stack VUOTO!!");
		}
	}

	public Iterator elements() {return new StackElementIterator(this);}
	
	/* FUNZIONALITA' ACCESSORIE DELLO STACK */	
	
	public String toString()
	{
		String str = "";
		if(this.isEmpty()){
			try {
				throw new EmptyStackException("Struttura Dati vuota!!");
			} catch (EmptyStackException e) {
				e.printStackTrace();
				return str;
			}
		}
		str += new String("Classe TwoQueueStack che contiene:\n"+ "( ");
		TwoQueueStack appoggio = new TwoQueueStack();
		while(!this.isEmpty())
		{
			try {				
				appoggio.push(pop());
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
		}
		
		int j = appoggio.size();
		while(!appoggio.isEmpty())
		{
			try {
				Object temp = appoggio.pop();
				str += (String) temp;
				j--;
				if(j != 0 )
					str += " , ";
				else
					str += " : TOP )";
				
				this.push(temp);
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
		}
		
		return str;
	}

	public Object clone(){
		TwoQueueStack appoggio = new TwoQueueStack();
		TwoQueueStack cloned = new TwoQueueStack();
		
		while(!this.isEmpty()){
			try {
				appoggio.push(this.pop());
			} catch (EmptyStackException e){
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

	public boolean equals(Object o)
	{		
		TwoQueueStack appoggio = new TwoQueueStack();
		TwoQueueStack stack = (TwoQueueStack)o;
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
				e.printStackTrace();
			}
		}

		return var;	
	}
	
	private Queue queue1;
	private Queue queue2;
}
