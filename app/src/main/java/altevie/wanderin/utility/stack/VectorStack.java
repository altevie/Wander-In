package altevie.wanderin.utility.stack;

import altevie.wanderin.utility.exceptions.EmptyStackException;
import altevie.wanderin.utility.exceptions.EmptyVectorException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.StackElementIterator;
import altevie.wanderin.utility.vector.ArrayVector;
import altevie.wanderin.utility.vector.Vector;

public class VectorStack implements Stack{

	public VectorStack(){
		vector = new ArrayVector(false);
		
	}

	/* FUNZIONALITA' BASE DEL VECTORSTACK */
	
	public Object Top() throws EmptyStackException {return vector.elementAtRank(size()-1);}

	public boolean isEmpty() {return vector.isEmpty();}

	public Object pop() throws EmptyStackException {return vector.removeAtRank(vector.size()-1);}

	public void push(Object o) {vector.insertAtRank(vector.size(), o);}

	public int size() {return vector.size();}
	
	public Iterator elements() {return new StackElementIterator(this);}
	
	/* FUNZIONALITA' ACCESSORIE DEL VECTORSTACK */
	
	public String toString()
	{
		String str = "";
		if(this.isEmpty()){
			try {
				throw new EmptyVectorException("Struttura Dati vuota!!");
			} catch (EmptyVectorException e) {
				try {
					throw new EmptyStackException("Struttura Dati vuota!!");
				} catch (EmptyStackException e1) {
					e1.printStackTrace();
				}
			
			}
		}
		str += new String("Classe VectorStack che contiene:\n"+ "( ");
		VectorStack appoggio = new VectorStack();
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
	
	public boolean equals(Object obj)
	{ 
		VectorStack appoggio = new VectorStack();
		VectorStack stack = (VectorStack)obj;
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
	
	public Object clone(){
		VectorStack appoggio = new VectorStack();
		VectorStack cloned = new VectorStack();
		
		while(!this.isEmpty()){
			try {
				appoggio.push(this.pop());
			} catch (EmptyStackException e) {
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
	
private Vector vector;
}
