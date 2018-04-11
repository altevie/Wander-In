package altevie.wanderin.utility.vector;

import altevie.wanderin.utility.exceptions.BoundaryViolationException;
import altevie.wanderin.utility.exceptions.EmptyListException;
import altevie.wanderin.utility.exceptions.EmptyVectorException;
import altevie.wanderin.utility.exceptions.FullVectorException;
import altevie.wanderin.utility.exceptions.NecessaryElementNotFoundException;
import altevie.wanderin.utility.exceptions.OneElemListException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.VectorElementIterator;

public class ArrayVector2 implements Vector
{	
	public ArrayVector2(boolean canBeFull) {
		this(CAPACITA);
		this.canBeFull= canBeFull;
	}
	
	public ArrayVector2(int capacity){
		list = new Object[capacity];
		this.canBeFull=true;
	}
	
	private void duplica()
	{
		Object[] temp = list.clone();
		list = new Object[list.length*2];

		for(int i = 0; i < temp.length; i++)
			list[i] = temp[i];
	}
	/* FUNZIONALITA' BASE DEL VECTOR */
	
	public Object elementAtRank(int r) throws BoundaryViolationException
	{
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
		
		return list[(r + front) % list.length];
	}

	public void insertAtRank(int r, Object e) throws BoundaryViolationException
	{
		if(r > size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
		else{
			if(this.size() == (list.length -1)){
				if(canBeFull){
					try{
						throw new FullVectorException("VECTOR PIENO!!");
					}catch(FullVectorException e1){
						e1.printStackTrace();
					}
				}
				else{
					this.duplica();
					this.insertAtRank(r, e);
				}		
			}
			
			if(r == 0) // inserimento in testa
			{
				if(isEmpty())
				{
					list[front] = e;
					rear++;
				}
				else{
					if(front > 0)
					{
						front--;
						list[front] = e;
					}else
						if(front == 0)
						{
							front = list.length - 1;
							list[front] = e;
						}				
				}
			}
			else // altrimenti
			{
				int i = (front + r) % list.length;
				Object prec = list[i];
				Object succ = null;
				
				do
				{
					succ = list[i];
					list[i] = prec;
					prec = succ;
					i = (i+1) % list.length;
				}while(succ != null);
				
				list[(front + r) % list.length] = e;				
				rear = (rear + 1) % list.length;
			}
		}	
	}

	public boolean isEmpty() {return (front == rear);}

	public Object removeAtRank(int r) throws BoundaryViolationException {
		Object e = null;
		if(r > size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
		else{
			if(r == 0) // cancellazione in testa
			{
				if(isEmpty())
					throw new EmptyVectorException("VUOTO!!!"); 
				else{
					e = list[front];
					list[front] = null;
					front = (front + 1) % list.length;
				}
			}
			else // cancellazione altrove
			{
				int i = front;
				Object prec = list[i];
				Object succ = null;
				i = (i+1) % list.length;
				
				do
				{
					succ = list[i];
					list[i] = prec;
					prec = succ;
					i = (i+1) % list.length;
				}while(i != ((front + r + 1) % list.length));
				
				list[front] = null;				
				front = (front + 1) % list.length;
				e = prec;
			}
		}	
		
		return e;
	}

	public Object replaceAtRank(int r, Object e)throws BoundaryViolationException {
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");

		Object o = list[(front + r) % list.length];
		list[(front + r) % list.length] = e;
		return o;
	}

	public int size() {return (list.length - front + rear ) % list.length ;}
	
	public Iterator elements(){ return new VectorElementIterator(this); }
	
	/* FUNZIONALITA' ACCESSORIE DEL VECTOR */
	public void LocalRemoveEven(int x){
		int var=2;
		int taglia = this.size();
		int primo = 0;
		int secondo =0;
		for(int i =0; (i < taglia)&& (var != 0); i++){
			
			if((this.elementAtRank(i)).equals(x) && (var == 2)){
				primo = i;
				var--;
				continue;
			}
			if((this.elementAtRank(i)).equals(x)&& var == 1){	
				secondo =i;
				var--;
				continue;
			}

		}
		
		if(var != 0){
			throw new NecessaryElementNotFoundException("Non sono stati " +
					"trovati elementi sufficienti per effetuare la modifica della sequenza!"); 
		}
		else
			if((secondo - primo ) == 1)
				throw new OneElemListException("Non ci sono elementi di rango pari !!");

			int N = secondo - primo - 1;
			int Nc=0;
			
			if((N%2)==0)
				Nc = N/2;
			else
				Nc = ((int)N/2)+1;
				
				for(int i =0; i <= (Nc  - 1) ; i++)
					this.removeAtRank(primo + i + 1);
	}
	
	public ArrayVector2 LocalRemoveEvent(int x){
		ArrayVector2 av = (ArrayVector2) this.clone();
		av.LocalRemoveEven(x);
		return av;
	}
	
	public boolean coniugata(Vector P){
		if(this.isEmpty())
			try {
				throw new EmptyVectorException("ArrayVector VUOTO!!!");
			} catch (EmptyVectorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(!(this.size() == P.size()))
			return false;
		boolean ris = false;
		int taglia = this.size();
		for(int i=0; i <taglia; i++){
			try {
				this.insertAtRank(size()- 1, this.removeAtRank(0));
				if(this.equals(P))
					ris = true;
			} catch (EmptyVectorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ris;
	}
	public void removeOdd(){
		if(this.isEmpty())
			throw new EmptyListException("Lista VUOTA!!");
		if(this.size()== 1)
			throw new OneElemListException("Non ci sono elementi di rango dispari!!");
		
		int n = ((int)size()/2);
		
		for(int i =0 ; i < n ; i++){
			this.removeAtRank(i + 1);
		}
	}
	
	
	public int numElementiAlmenoRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
			
		return size() - r - 1;
	}
	
	public ArrayVector2 vectorNumElementiAlmenoRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
		
			ArrayVector2 vector2 = new ArrayVector2(false);
			for(int i = r + 1 ; i < size(); i++)
				vector2.insertAtRank(i-(r+1),this.elementAtRank(i) );
				
		return vector2;
	}
	
	public int numElementiAlpiuRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
			
		return  r;
	}
	public ArrayVector2 vectorNumElementiAlpiuRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
		
			ArrayVector2 vector2 = new ArrayVector2(false);
			for(int i =0 ; i <  r ; i++)
				vector2.insertAtRank(i,this.elementAtRank(i) );
				
		return vector2;
	}
	
	public int numElementiMaggioreUgualeRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
			
		return (size() - r);
	}
	public ArrayVector2 vectorNumElementiMaggioreUgualeRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
		
			ArrayVector2 vector2 = new ArrayVector2(false);
			for(int i =r ; i < size() ; i++)
				vector2.insertAtRank(i-r,this.elementAtRank(i) );
				
		return vector2;
	}
	
	public int numElementiMinoreUgualeRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
			
		return (r + 1);
	}
	
	public ArrayVector2 vectorNumElementiMinoreUgualeRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
		
			ArrayVector2 vector2 = new ArrayVector2(false);
			for(int i =0 ; i < r + 1 ; i++)
				vector2.insertAtRank(i,this.elementAtRank(i) );
				
		return vector2;
	}
	
	public String toString()
	{
		String str = new String("Classe ArrayVector che contiene:\n"+ "( ");
		for(int r = 0; r < this.size() - 1; r++ ){
			str += this.elementAtRank(r) + " ,".toString();
		}
		str += elementAtRank(this.size() -1) + " )".toString();
		return str;
	}

	public Object clone()
	{
		ArrayVector2 cloned = new ArrayVector2(false);
		for(int i = 0; i < this.size(); i++)
			cloned.insertAtRank(i, this.elementAtRank(i));
		
		return cloned;
	}

	public boolean equals(Object obj)
	{
		ArrayVector2 array = (ArrayVector2)obj;
		if(this.size() != array.size())
			return false;
		
		boolean var = true;

		for(int r = 0; r < this.size(); r++)	
			if(!this.elementAtRank(r).equals(array.elementAtRank(r)))
			{
				var = false;
				break;
			}

		return var;
	}


public final static int CAPACITA = 1024;
private Object list[];
private int front = 0;
private int rear = 0;
//	 indica se lo queue pu� raggiungere la condizione di "pieno"
private boolean canBeFull;
}
