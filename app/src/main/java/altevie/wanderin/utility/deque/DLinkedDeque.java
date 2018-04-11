package altevie.wanderin.utility.deque;

import altevie.wanderin.utility.exceptions.EmptyDequeException;
import altevie.wanderin.utility.iterator.DequeElementIterator;

public class DLinkedDeque implements Deque{

	public DLinkedDeque() {
		super();
		header = new DLNode();
		trailer = new DLNode();
		header.setNext(trailer);
		trailer.setPrev(header);
		size = 0;
	}

	/* FUNZIONALITA' BASE DELLA DEQUE */
	
	public int size() {return size;}
	
	public boolean isEmpty() {return (size() == 0);}
	
	public Object first() throws EmptyDequeException {
		if(isEmpty())
			throw new  EmptyDequeException("CodaDoppiamenteLincata VUOTA!!"); 
		else{
			return header.getNext().getElement();
		}

	}
	public void insertFirst(Object o) {
		DLNode temp = header.getNext();
		DLNode newNode = new DLNode(o,header,temp);
		header.setNext(newNode);
		temp.setPrev(newNode);
		size++;
	}
	public void insertLast(Object o) {
		DLNode temp = trailer.getPrev();
		DLNode newNode = new DLNode(o,temp,trailer);
		trailer.setPrev(newNode);
		temp.setNext(newNode);
		size++;
	}

	public Object last() throws EmptyDequeException {
		if(isEmpty())
			throw new EmptyDequeException("CodaDoppiamenteLincata VUOTA!!");
		else
			return trailer.getPrev().getElement();
	}
	public Object removeFirst() throws EmptyDequeException {
		if(isEmpty())
			throw new EmptyDequeException("CodaDoppiamenteLincata VUOTA!!");
		else{
			DLNode first = header.getNext();
			Object o = first.getElement();
			DLNode secondToFirst = first.getNext();
			header.setNext(secondToFirst);
			secondToFirst.setPrev(header);
			size--;

			return o;
		}

	}
	public Object removeLast() throws EmptyDequeException {
		if(isEmpty())
			throw new EmptyDequeException("CodaDoppiamenteLincata VUOTA!!");
		else{
			DLNode last = trailer.getPrev();
			Object o = last.getElement();
			DLNode secondToLast = last.getPrev();
			trailer.setPrev(secondToLast);
			secondToLast.setNext(trailer);
			size--;

			return o;
		}

	}
	public DequeElementIterator elements() {return new DequeElementIterator(this);	}

	/* FUNZIONALITA' ACCESSORIE DELLA DEQUE */

	public boolean coniugata(Deque P){
		if(this.isEmpty())
			try {
				throw new EmptyDequeException("Sequence VUOTA!!!");
			} catch (EmptyDequeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(!(this.size() == P.size()))
			return false;
		
		boolean ris = false;
		int taglia = this.size();
		for(int i=1; i <=taglia; i++){
			try {
				this.insertLast(this.removeFirst());
				if(this.equals(P))
					ris = true;
			} catch (EmptyDequeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ris;
	}
	
	public String toString()
	{
		String str = "";
		if(this.isEmpty()){
			try {
				throw new EmptyDequeException("Struttura Dati vuota!!");
			} catch (EmptyDequeException e) {
				e.printStackTrace();
				return str;
			}
		}
		
		str += "Classe che contiene :( Front : ";
		
		for(int i = 0; i < this.size(); i++)
		{
			Object obj = this.removeFirst();
			this.insertLast(obj);
			str += "" + obj.toString();
			
			if(i == this.size() - 1 )
				str += " )";
			else
				str += " ,";
		}
		
		return str;
	}
	
	public Object clone()
	{
		DLinkedDeque cloned = new DLinkedDeque();
		int taglia = this.size();
		Object obj = null;
		for(int i=0; i < taglia; i++)
		{
			obj = this.removeFirst();
			cloned.insertLast(obj);
			this.insertLast(obj);
		}
		return cloned;
	}
	
	public boolean equals(Object obj)
	{
		DLinkedDeque other = (DLinkedDeque) obj;
				
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
			for(int i = 0; i < taglia; i++)
			{
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

	private DLNode header;
	private DLNode trailer;
	private int size;

}
