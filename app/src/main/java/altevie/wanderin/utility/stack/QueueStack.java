package altevie.wanderin.utility.stack;

import altevie.wanderin.utility.exceptions.EmptyQueueException;
import altevie.wanderin.utility.exceptions.EmptyStackException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.StackElementIterator;
import altevie.wanderin.utility.queue.ArrayQueue;
import altevie.wanderin.utility.queue.Queue;

public class QueueStack implements Stack{

	public QueueStack() {
		queue = new ArrayQueue(false);
	}

	/* FUNZIONALITA' BASE DELLO STACK */

	public boolean isEmpty(){return queue.isEmpty();}
	
	public void push(Object o){queue.enqueue(o);}
	
	public int size(){return queue.size();}
	
	public Object Top() throws EmptyStackException {
		if(queue.isEmpty())
			throw new EmptyStackException("Stack VUOTO!!");
		
		Object obj = null;
		for(int i =0; i < queue.size()-1 ; i++){
			try {
				queue.enqueue(queue.dequeue());		
			} catch (EmptyQueueException e) {
				throw new EmptyStackException("Stack VUOTO!!");
			}
		}
		try {
			obj = queue.dequeue();
		} catch (EmptyQueueException e) {
			throw new EmptyStackException("Stack VUOTO!!");
		}
		queue.enqueue(obj);
		return obj;
	}

	public Object pop() throws EmptyStackException {
		if(queue.isEmpty())
			throw new EmptyStackException("STACK VUOTO!");
		
		Object obj = null;
		for(int i =0; i < queue.size()-1 ; i++){
			try {
				queue.enqueue(queue.dequeue());
			} catch (EmptyQueueException e) {
				throw new EmptyStackException("STACK VUOTO!");
			}	
		}
		try {
			obj = queue.dequeue();
		} catch (EmptyQueueException e) {
			throw new EmptyStackException("STACK VUOTO!");
		}
		return obj;
	}

	public Iterator elements() {return new StackElementIterator(this);}
	
	/* FUNZIONALITA' ACCESSORIE DELLO STACK */
	
	public String toString()
	{
		
		return queue.toString();
	}

	public Object clone(){
		QueueStack appoggio = new QueueStack();
		QueueStack cloned = new QueueStack();
		
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
	
	public boolean equals(Object o){
		QueueStack stack = (QueueStack)o;
		return queue.equals(stack);	
	}
	
private Queue queue;
}
