package altevie.wanderin.utility.stack;

import altevie.wanderin.utility.exceptions.EmptySequenceException;
import altevie.wanderin.utility.exceptions.EmptyStackException;
import altevie.wanderin.utility.exceptions.FullStackException;
import altevie.wanderin.utility.exceptions.NecessaryElementNotFoundException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.StackElementIterator;
import altevie.wanderin.utility.sequence.NodeSequence;
import altevie.wanderin.utility.sequence.Sequence;

public class ArrayStack implements Stack 
{	
	public ArrayStack(boolean canBeFull){
		this(CAPSTD);
		this.canBeFull/*potrebbe riempirsi*/ = canBeFull;
	}
	//Costruttore che viene utilizzato nel caso in cui l'array non � dinamico
	public ArrayStack(int capacita) {
		super();
		list = new Object[capacita];
		this.canBeFull = true;
	}
	/* FUNZIONALITA' BASE DELLO STACK */
	
	public int size() {	return top + 1; }
	
	public boolean isEmpty() { return (top == -1); }
	
	public Object Top() throws EmptyStackException {
		if(this.isEmpty())
		{
			throw new EmptyStackException("LO STACK E' VUOTO!");
		
		}
		else return list[top];
	}
	
	public void push(Object o) {
		if(top != (list.length - 1)){
			top++;
			list[top] = o;
		}
		else { // quando � pieno
			if(canBeFull)
				try {
					throw new FullStackException("LO STACK E' PIENO!");
				} catch (FullStackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else
			{
				duplica();
				push(o);
			}
		}	
	}
	
	public Object pop() throws EmptyStackException {
		Object temp = Top();
		list[top] = null;
		top--;
		return temp;
	}
	
	private void duplica()
	{
		Object[] temp = list.clone();
		list = new Object[list.length*2];
		
		for(int i = 0; i < temp.length; i++)
			list[i] = temp[i];
	}
	
	public Iterator elements() {return new StackElementIterator(this);}
	
	/* FUNZIONALITA' ACCESSORIE DELLO STACK */
	
	public ArrayStack BlockSwap(Object x){
		ArrayStack appoggio = new ArrayStack(false);
		ArrayStack ArrayOutput = new ArrayStack(false);
		boolean var = true;
		int i =0;
		while(!this.isEmpty()){
			Object obj =this.pop();
			if(obj.equals(x)){
				appoggio.push(obj);
				i = appoggio.size();
				var = false;
				break;
			}
			appoggio.push(obj);
		}
		if(var){
			while(!appoggio.isEmpty())
				this.push(appoggio.pop());
			throw new NecessaryElementNotFoundException("Elemento non trovato !!");
		}

		while(!appoggio.isEmpty()){
			Object obj = appoggio.pop();
			ArrayOutput.push(obj);
			this.push(obj);
			
		}
		while(!this.isEmpty()){
			Object obj =this.pop();
			appoggio.push(obj);
			
		}

		while(appoggio.size() > i){
			Object obj =appoggio.pop();
			this.push(obj);
			ArrayOutput.push(obj);
	
		}
		while(!appoggio.isEmpty()){
			this.push(appoggio.pop());
		}
		return ArrayOutput;
	}
		
	
	public boolean Complementari (Stack J){
		ArrayStack stackE =(ArrayStack)J;
		Sequence seq = new NodeSequence();
		Integer questo=null, quello=null;
		boolean var = false;
		if(this.size() != stackE.size())
			return false;
		ArrayStack appoggio = new ArrayStack(this.size());
		while(!this.isEmpty()){
			appoggio.push(this.pop());
		}
		while(!appoggio.isEmpty()){
			questo = Integer.valueOf((appoggio.pop().toString()));
			quello = Integer.valueOf((stackE.pop().toString()));
			this.push(questo);
			seq.insertFirst(quello);
			if((((questo % 2)== 0)&&((quello %2) != 0))||(((quello % 2)== 0)&&((questo %2) != 0)) )
				var = true;
			else
				var = false;
		}
		while(!seq.isEmpty()){
			stackE.push(seq.remove(seq.first()));
		}
		return var;
	}
	public boolean coniugata(Stack P){
		if(this.isEmpty())
			try {
				throw new EmptyStackException("Stack VUOTO!!!");
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
			
		if(!(this.size() == P.size()))
			return false;
		
		ArrayStack appoggio = new ArrayStack(false);
		boolean ris = false;
		for(int i=1; i <= this.size(); i++)
		{
			try {
				Object temp = this.pop();
				while(!this.isEmpty())
					appoggio.push(this.pop());
					
				appoggio.push(temp);
				while(!appoggio.isEmpty())
					this.push(appoggio.pop());
					
				if(this.equals(P))
					ris = true;
				
			} catch (EmptySequenceException e) {
				e.printStackTrace();
			}
		}
		
		return ris;
	}
	public Object First(){
		ArrayStack appoggio = new ArrayStack(this.size());
		while(!this.isEmpty()){
			try {
				appoggio.push(this.pop());
				
			} catch (EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		
		Object z = null;
		try {
			z = appoggio.Top();
		} catch (EmptyStackException e1) {
			e1.printStackTrace();
		}
		
		while(!appoggio.isEmpty())
		{
			try {
				this.push(appoggio.pop());		
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
		}
		return z;
	}
	
	public Stack ReverseStack ()
	{
		ArrayStack appoggio = new ArrayStack(this.size());
		while(!this.isEmpty()){
			try {
				appoggio.push(this.pop());
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
		}
		
		Object a = appoggio.clone();
		
		while(!appoggio.isEmpty())
		{
			try {
				this.push(appoggio.pop());
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
		}
		
		return (Stack)a;
	}
	
	
	public boolean Double(){
		
		if(!(this.size() % 2 == 0 ))
			return false;
		
		int x = this.size()/2;
		ArrayStack stackAppoggio1 = new ArrayStack(x);
		ArrayStack stackAppoggio2 = new ArrayStack(x);
		
		// RIEMPI IL PRIMO STACK CON META' IN SENSO INVERSO
		for(int i=0; i < x;i++){
			try {
				stackAppoggio1.push(this.pop());
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
			
		}
		// RIEMPI IL SECONDO STACK CON IL PRIMO IN SENSO CORRETTO
		for(int i =0 ;i < x;i++) {
			try {
				stackAppoggio2.push(stackAppoggio1.pop());
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
		}
		
		boolean ret;
		if(this.equals(stackAppoggio2))
			ret = true;
		else
			ret = false;
		
		while(!stackAppoggio2.isEmpty()){
			try {
				stackAppoggio1.push(stackAppoggio2.pop());
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
		}
		while(!stackAppoggio1.isEmpty()){
			try {
				this.push(stackAppoggio1.pop());
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public boolean ParentesiBilanciate(String w)throws EmptyStackException
	{
		ArrayStack s = new ArrayStack(w.length());
	
		for(int i=0; i < w.length();i++){
			if(w.charAt(i)=='(')
				s.push(new Character(w.charAt(i)));
			else
				if(w.charAt(i)==')'){
					if(s.isEmpty())
						throw new EmptyStackException("NON BIALNCIATE!!");
					else
						s.pop();
				}
		}
		if(s.isEmpty())
			return true;
		else
			return false;
	}
	
	public String reverseString(String w){
		ArrayStack stack = new ArrayStack(w.length());
		String str = "";
		
		for(int i =0 ; i < w.length(); i++){
			stack.push(w.charAt(i));
		}		
		while(!stack.isEmpty()){
			try {
				str += stack.pop();
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	public Stack ArrayStackPartialReverse(Object x)
	{
		ArrayStack stackAppoggio = new ArrayStack(this.size());
		ArrayStack stackParzialmenteInvertito = new ArrayStack(this.size());
		boolean var = false;
	
		while(!this.isEmpty()){
			try {
				Object temp = this.pop();
				if(var)
					stackParzialmenteInvertito.push(temp);
				if(temp.equals(x))
					var = true;

				stackAppoggio.push(temp);
			}catch (EmptyStackException e) {
				e.printStackTrace();
			}
		}
		
		if(!var)
		{
			while(!stackAppoggio.isEmpty())
			{
				try {
					this.push(stackAppoggio.pop());
				} catch (EmptyStackException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		Object temp = null;
		do{
			try {
				temp = stackAppoggio.pop();
				this.push(temp);
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
		}while(!temp.equals(x));

		stackParzialmenteInvertito.push(temp);
		
		while(!stackAppoggio.isEmpty())
		{
			Object temp1 = null;
			try {
				temp1 = stackAppoggio.pop();
				this.push(temp1);
				stackParzialmenteInvertito.push(temp1);
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}			
		}
		
		return stackParzialmenteInvertito;		
	}
	
	public String toString(){
		String str = "";
		if(this.isEmpty()){
			try {
				throw new EmptyStackException("Struttura Dati vuota!!");
			} catch (EmptyStackException e) {
				e.printStackTrace();
				return str;
			}
		}
		str += new String("Classe ArrayStack che contiene:\n"+ "( ");
		ArrayStack appoggio = new ArrayStack(this.size());
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
				str += temp.toString();
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
		ArrayStack appoggio = new ArrayStack(this.size());
		ArrayStack stack = (ArrayStack)obj;
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
	
	public Object clone(){
		ArrayStack appoggio = new ArrayStack(this.size());
		ArrayStack cloned = new ArrayStack(size());
		
		while(!this.isEmpty()){
			try {
				appoggio.push(this.pop());
			} catch (EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Object temp=null;
		while(!appoggio.isEmpty()){
			try {
				temp = appoggio.pop();
			} catch (EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.push(temp);
			cloned.push(temp);
		}
		return cloned;
	}
	
	// costante standard di capacit�
	public static final int CAPSTD = 5;
	// contiene gli elementi dello stack
	private Object list[];
	// indice del top
	private int top = -1;
	// indica se lo stack pu� raggiungere la condizione di "pieno"
	private boolean canBeFull;

}
