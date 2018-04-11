package altevie.wanderin.utility.queue;

import altevie.wanderin.utility.deque.DLinkedDeque;
import altevie.wanderin.utility.exceptions.EmptyQueueException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.QueueElementIterator;

public class DequeQueue implements Queue{

	public DequeQueue() {
		deque = new DLinkedDeque();
	}
	
	/* FUNZIONALITA' BASE DELLA CODA */
	
	public boolean isEmpty() {return deque.isEmpty();}

	public int size() {	return deque.size();}
	
	public Object dequeue() throws EmptyQueueException {
		if(deque.isEmpty())
			throw new EmptyQueueException("Coda VUOTA!!"); 
		else{
			Object o = deque.removeFirst();
			return o;
		}
	}

	public void enqueue(Object o) {deque.insertLast(o);	}

	public Object front() throws EmptyQueueException {
		if(deque.isEmpty())
			throw new EmptyQueueException("Coda VUOTA!!"); 
		else{
			return deque.first();
		}

	}
	public Iterator elements() {return new QueueElementIterator(this);}
	
	/* FUNZIONALITA' ACCESSORIE DELLA CODA */
	
	public String toString()
	{
		String str = "";
		if(this.isEmpty()){
			try {
				throw new EmptyQueueException("Struttura Dati vuota!!");
			} catch (EmptyQueueException e) {
				e.printStackTrace();
				return str;
			}
		}
		str += "Classe DequeQueue che contiene :( Front : ";

		for(int i=0; i < this.size();i++){
			Object obj=null;
			try {
				obj = this.dequeue();
			} catch (EmptyQueueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			str += (String)obj;
			if(i != this.size())
				str += " ,";
			else
				str +=" )";
			this.enqueue(obj);
			
		}
		return str;
	}

	public Object clone(){
		DequeQueue cloned = new DequeQueue();
		Object obj = null;
		for(int i=0; i < this.size(); i++){
			try {
				obj = this.dequeue();
				cloned.enqueue(obj);
				this.enqueue(obj);
			} catch (EmptyQueueException e) {
				e.printStackTrace();
			}
		}
		return cloned;
	}
	
	public boolean equals(Object obj)
	{
		DequeQueue other = (DequeQueue) obj;
				
		if(this.size() != other.size())
			return false;
		if(this.isEmpty()&& other.isEmpty())
			return true;
		else
		{
			boolean uguali = true;
			Object obj1 =null;
			Object obj2 = null;
			for(int i =0; i < this.size();i++){
				try {
					obj1 = this.dequeue();
					obj2 = other.dequeue();
					if(!obj1.equals(obj2))
						uguali = false;
					this.enqueue(obj1);
					other.enqueue(obj2);
				} catch (EmptyQueueException e) {
					e.printStackTrace();
				}
				
			}			
			return uguali;
		}		
	}
	
private DLinkedDeque deque;
}
