package altevie.wanderin.utility.queue;

import altevie.wanderin.utility.exceptions.EmptyQueueException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.QueueElementIterator;
import altevie.wanderin.utility.vector.ArrayVector;
import altevie.wanderin.utility.vector.Vector;


public class VectorQueue implements Queue{

	public VectorQueue() {
		vector = new ArrayVector(false);
	}
	/* FUNZIONALITA' BASE DELLA CODA */

	public Object dequeue() throws EmptyQueueException {return vector.removeAtRank(0);}

	public void enqueue(Object o) {vector.insertAtRank(vector.size(), o);}

	public Object front() throws EmptyQueueException {	return vector.elementAtRank(0);}

	public boolean isEmpty() {return vector.isEmpty();}

	public int size() {return vector.size();}
	
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
		str += "Classe VectorQueue che contiene :( Front : ";

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
	VectorQueue cloned = new VectorQueue();
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
	VectorQueue other = (VectorQueue) obj;

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

private Vector vector;
}
