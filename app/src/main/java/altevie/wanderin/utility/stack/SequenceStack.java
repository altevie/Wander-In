///////////////////////////////////////////non va

package altevie.wanderin.utility.stack;

import altevie.wanderin.utility.exceptions.EmptyStackException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.StackElementIterator;
import altevie.wanderin.utility.list.DNode;
import altevie.wanderin.utility.sequence.NodeSequence;

public class SequenceStack implements Stack{

	public SequenceStack(){
		super();
		size = 0;
		sequence = new NodeSequence();
		top= null;
	}
	
	/* FUNZIONALITA' BASE DELLO STACK */
	
	public Object Top() throws EmptyStackException {
		if(this.isEmpty())
			throw new EmptyStackException("Stack VUOTO!!");
		else		
			top = (DNode) sequence.first();
		return top.element();
	}

	public Iterator elements() {return new StackElementIterator(this);}

	public boolean isEmpty() {return (size() == 0);}

	public Object pop() throws EmptyStackException {
		//cancella in testa
		if(isEmpty())
			throw new EmptyStackException("Stack VUOTO!!");
		else{
			
			Object temp = sequence.remove(top);
			top = (DNode)sequence.first();
			size--;
			return temp;
		}	
	}

	public void push(Object o) {
		//inserisce in testa
		sequence.insertFirst(o);
		top = (DNode)sequence.first();
		size++;
	}
	public int size() {return size;}
	
/* FUNZIONALITA' ACCESSORIE DELLO STACK */
	
	public Object clone(){
		SequenceStack appoggio = new SequenceStack();
		SequenceStack cloned = new SequenceStack();
		
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
		SequenceStack appoggio = new SequenceStack();
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
		SequenceStack appoggio = new SequenceStack();
		SequenceStack stack = (SequenceStack)o;
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
private DNode top;
private NodeSequence sequence;
}
