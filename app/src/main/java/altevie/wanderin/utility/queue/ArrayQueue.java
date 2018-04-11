package altevie.wanderin.utility.queue;

import altevie.wanderin.utility.deque.DLinkedDeque;
import altevie.wanderin.utility.deque.Deque;
import altevie.wanderin.utility.exceptions.EmptyQueueException;
import altevie.wanderin.utility.exceptions.EmptyStackException;
import altevie.wanderin.utility.exceptions.FullQueueException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.QueueElementIterator;
import altevie.wanderin.utility.stack.ArrayStack;

public class ArrayQueue implements Queue{
	
	public ArrayQueue(boolean canBeFull){
		this(CAPACITA);
		this.canBeFull = canBeFull;
	}
	
	public ArrayQueue(int capacita) {
		super();
		array = new Object[capacita];
		this.canBeFull = true;
	}
	/* FUNZIONALITA' BASE DELLA CODA */
	
	public Object front() throws EmptyQueueException {return array[front];}

	public boolean isEmpty() {return (front == rear);}

	public int size() {return (array.length - front + rear ) % array.length ;}
	
	public Object dequeue() throws EmptyQueueException {
		Object temp = null;
		if(this.isEmpty())
			 new EmptyQueueException("CODA VUOTA!!"); 
		else{
			temp = array[front];
			array[front] = null;
			front = (front +1) % array.length;
		}
		return temp;
	}

	public void enqueue(Object o) {
		
		if(this.size() == (array.length -1)){
			if(canBeFull){
				new FullQueueException("CODA PIENA!!");
				
			}
			else{
				this.duplica();
				this.enqueue(o);
			}		
		}
		else{
			array[rear]= o;
			rear = (rear + 1)% array.length;
		}
	}

	private void duplica()
	{
		Object[] temp = array.clone();
		array = new Object[array.length*2];
		
		for(int i = 0; i < temp.length; i++)
			array[i] = temp[i];
	}
	public Iterator elements() {return new QueueElementIterator(this);}
	
	/* FUNZIONALITA' ACCESSORIE DELLA CODA */
	
	public boolean complementari (Queue q){
		ArrayQueue codaEsterna = (ArrayQueue)q;
		Integer val =0;

		if(codaEsterna.isEmpty() && this.isEmpty())
			return true;

		if(codaEsterna.size() != this.size())
			return false;

		Deque deque = new DLinkedDeque();
		int taglia = codaEsterna.size();
		for(int i=0; i< taglia ; i++){
			deque.insertFirst(codaEsterna.dequeue());
		}
		for(int i=0; i< this.size(); i++){
			Object o = this.dequeue();
			Object o1 = deque.removeFirst();
			if((((Integer.valueOf((String)o))%2) == 0) && (((Integer.valueOf((String)o1))%2)== 0))
				val++;
			if((((Integer.valueOf((String)o1))%2) != 0) && (((Integer.valueOf((String)o))%2)!= 0))
				val++;

			this.enqueue(o);
			deque.insertLast(o1);
		}
		for(int i=0; i< taglia ; i++){
			codaEsterna.enqueue(deque.removeLast());
		}
		if(val == this.size())
			return true;
		else
			return false;
	}


	
	public Object last() throws EmptyQueueException{
		if(!this.isEmpty()){
			for(int i=0; i < this.size()-1; i++){
				this.enqueue(this.dequeue());
			}
			Object obj = this.dequeue();
			this.enqueue(obj);
			return obj;
		}			
		else{
			throw new EmptyQueueException("Coda VUOTA!!");
		}
	}
	
	public Object last1(){
		ArrayQueue coda = new ArrayQueue(this.size());
		Object temp = null;
		
		while(!this.isEmpty())
		{
			try {
				temp = this.dequeue();
			} catch (EmptyQueueException e) {
				e.printStackTrace();
			}
			coda.enqueue(temp);
		}
		
		while(!coda.isEmpty())
		{
			try {
				this.enqueue(coda.dequeue());
			} catch (EmptyQueueException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
	
	public void ReverseQueue(){
		ArrayStack appoggio = new ArrayStack(this.size());
		while(!this.isEmpty()){
			try {
				appoggio.push(this.dequeue());
			} catch (EmptyQueueException e) {
				e.printStackTrace();
			}
		}
		while(!appoggio.isEmpty()){
			try {
				this.enqueue(appoggio.pop());
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Queue ReverseQueue1()
	{
		ArrayQueue codaAppoggio = new ArrayQueue(this.size());
		ArrayStack stackAppoggio = new ArrayStack(this.size());
		
		// PRIMO PASSO: POPOLA LE STRUTTURE DI APPOGGIO
		Object temp = null;
		while(!this.isEmpty())
		{
			try {
				temp = this.dequeue();
			} catch (EmptyQueueException e) {
				e.printStackTrace();
			}
			
			codaAppoggio.enqueue(temp);
			stackAppoggio.push(temp);
		}
		
		// SECONDO PASSO: RISTABILISCE IL THIS
		while(!codaAppoggio.isEmpty())
		{
			try {
				this.enqueue(codaAppoggio.dequeue());
			} catch (EmptyQueueException e) {
				e.printStackTrace();
			}			
		}
		
		// TERZO PASSO: CODA INVERSA
		while(!stackAppoggio.isEmpty())
		{
			try {
				codaAppoggio.enqueue(stackAppoggio.pop());
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}			
		}
		
		return codaAppoggio;
	}
	
	public boolean coniugata(Queue P){
		if(this.isEmpty())
			try {
				throw new EmptyQueueException("CODA VUOTA!!!");
			} catch (EmptyQueueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(!(this.size() == P.size()))
			return false;
		boolean ris = false;
		int taglia = this.size();
		for(int i=1; i <=taglia; i++){
			try {
				this.enqueue(this.dequeue());
				if(this.equals(P))
					ris = true;
			} catch (EmptyQueueException e) {
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
				throw new EmptyQueueException("Struttura Dati vuota!!");
			} catch (EmptyQueueException e) {
				e.printStackTrace();
				return str;
			}
		}
		str += "Classe ArrayQueue che contiene :( Front : ";

		for(int i=0; i < this.size();i++){
			Object obj=null;
			try {
				obj = this.dequeue();
			} catch (EmptyQueueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			str += obj.toString();
			if(i != this.size())
				str += " ,";
			else
				str +=" )";
			this.enqueue(obj);
			
		}
		return str;
	}

	public Object clone(){
		ArrayQueue cloned = new ArrayQueue(false);
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
		ArrayQueue other = (ArrayQueue) obj;
				
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
	
public final static int CAPACITA = 1024;
private Object array[];
private int front =0;
private int rear =0;
// indica se lo queue pu� raggiungere la condizione di "pieno"
private boolean canBeFull;


}
