package altevie.wanderin.utility.deque;

import altevie.wanderin.utility.exceptions.EmptyDequeException;
import altevie.wanderin.utility.exceptions.EmptyVectorException;
import altevie.wanderin.utility.iterator.DequeElementIterator;
import altevie.wanderin.utility.vector.ArrayVector;
import altevie.wanderin.utility.vector.Vector;

public class VectorDeque implements Deque{

	public VectorDeque() {
		vector = new ArrayVector(false);
	}

	/* FUNZIONALITA' BASE DELLA VECTORDEQUE */

	public Object first() throws EmptyDequeException {return vector.elementAtRank(0);}

	public void insertFirst(Object o) {vector.insertAtRank(0, o);}

	public void insertLast(Object o) {vector.insertAtRank(vector.size(), o);}

	public boolean isEmpty() {return vector.isEmpty();}

	public Object last() throws EmptyDequeException {return vector.elementAtRank(vector.size()-1);}

	public Object removeFirst() throws EmptyDequeException {return vector.removeAtRank(0);}

	public Object removeLast() throws EmptyDequeException {return vector.removeAtRank(vector.size()-1);}

	public int size() {	return vector.size();}
	
	public DequeElementIterator elements() {return new DequeElementIterator(this);	}
	
	/* FUNZIONALITA' ACCESSORIE DELLA VECTORDEQUE */

	public String toString()
	{
		String str = "";
		if(this.isEmpty()){
			try {
				throw new EmptyVectorException("Struttura Dati vuota!!");
			} catch (EmptyVectorException e) {
				throw new EmptyDequeException("Struttura Dati vuota!!");
				
			}
		}
		
		str += "Classe VectorDeque che contiene :( Front : ";
		
		for(int i = 0; i < this.size(); i++)
		{
			Object obj = this.removeFirst();
			this.insertLast(obj);
			str += "" + obj;
			
			if(i == this.size() - 1 )
				str += " )";
			else
				str += " ,";
		}
		
		return str;
	}
	
	public Object clone()
	{
		VectorDeque cloned = new VectorDeque();
		int taglia = this.size();
		Object obj = null;
		for(int i=0; i < taglia; i++){
			obj = this.removeFirst();
			cloned.insertLast(obj);
			this.insertLast(obj);
		}
		return cloned;
	}
	
	public boolean equals(Object obj)
	{
		VectorDeque other = (VectorDeque) obj;

		if(this.size() != other.size())
			return false;
		if(this.isEmpty()&& other.isEmpty())
			return true;
		else
		{
			boolean uguali = true;
			Object obj1 =null;
			Object obj2 = null;
			int taglia = this.size();
			for(int i =0; i < taglia;i++){
				obj1 = this.removeFirst();
				obj2 = other.removeFirst();
				if(!obj1.equals(obj2))
					uguali = false;
				this.insertLast(obj1);
				other.insertLast(obj2);
			}			
			return uguali;
		}		
	}

	private Vector vector;
}
