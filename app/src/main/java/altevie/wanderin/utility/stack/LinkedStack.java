package altevie.wanderin.utility.stack;

import altevie.wanderin.utility.exceptions.EmptyStackException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.StackElementIterator;
import altevie.wanderin.utility.linkedlist.Node;

public class LinkedStack implements Stack{

	public LinkedStack() {
		super();
		size = 0;
		top= null;
	}
	
	/* FUNZIONALITA' BASE DELLO STACK */
	
	public boolean isEmpty() { return (size() == 0);}

	public int size() {	return size;}
	
	public Object Top() throws EmptyStackException {
		if(this.isEmpty())
			throw new EmptyStackException("Stack VUOTO!!");
		else				
			return top.getElement();
	}

	public void push(Object o) {
		//inserisce in testa
		if(size==1)
			top.setNext(null);
		
		Node v = new Node(o,top);
		top = v;
		size++;
	}
	
	public Object pop() throws EmptyStackException {
		//cancella in testa
		if(isEmpty())
			throw new EmptyStackException("Stack VUOTO!!");
		else{
			Object temp = top.getElement();
			top = top.getNext();
			size--;
			return temp;
		}	
	}
	public Iterator elements() {return new StackElementIterator(this);}
	
	/* FUNZIONALITA' ACCESSORIE DELLO STACK */
	
	public Object clone(){
		LinkedStack appoggio = new LinkedStack();
		LinkedStack cloned = new LinkedStack();
		
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
	
	public String toString()
	{
		LinkedStack appoggio = new LinkedStack();
		String str = "(Top :";
		while(!this.isEmpty()){
			try {
				Object o= this.pop();
				str += o;
				if(this.size!=0)
					str+=" ,";
				else
					str+=")";
				appoggio.push(o);
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
			
		}
		return str;
	}
	
	public boolean equals(Object o){
		LinkedStack appoggio = new LinkedStack();
		LinkedStack stack = (LinkedStack)o;
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
	
private int size;
private Node top;
}
