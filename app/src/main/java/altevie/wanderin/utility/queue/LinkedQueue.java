package altevie.wanderin.utility.queue;

import altevie.wanderin.utility.exceptions.EmptyQueueException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.QueueElementIterator;
import altevie.wanderin.utility.linkedlist.Node;


public class LinkedQueue implements Queue{

	public LinkedQueue() {
		super();
		front = null;
		rear= null;
		size = 0;
	}
	
	/* FUNZIONALITA' BASE DELLA CODA */
	
	public Object front() throws EmptyQueueException {return front.getElement();}

	public boolean isEmpty() {return (size()== 0);}

	public int size() {return size;}
	
	public Object dequeue() throws EmptyQueueException {
		if(this.size == 0)
			throw new EmptyQueueException("Coda VUOTA!!");
		else{
			Object nodo = front.getElement();
			front = front.getNext();
			size--;
			if(size ==0)
				rear=null;
			return nodo;
		}

	}

	public void enqueue(Object o) {
		Node nodo =new Node();
		nodo.setElement(o);
		nodo.setNext(null);
		if(this.size() == 0){
			front = nodo;
		}
		else{
			rear.setNext(nodo);

		}
		rear = nodo;
		size++;	
	}
	public Iterator elements() {return new QueueElementIterator(this);}
	
	/* FUNZIONALITA' ACCESSORIE DELLA CODA */
	
	
	private Node front;
	private Node rear;
	private int size;
}
