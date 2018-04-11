package altevie.wanderin.utility.vector;


import altevie.wanderin.utility.exceptions.BoundaryViolationException;
import altevie.wanderin.utility.exceptions.EmptyListException;
import altevie.wanderin.utility.exceptions.EmptyStackException;
import altevie.wanderin.utility.exceptions.EmptyVectorException;
import altevie.wanderin.utility.exceptions.FullVectorException;
import altevie.wanderin.utility.exceptions.OneElemListException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.VectorElementIterator;

public class ArrayVector implements Vector{

	public ArrayVector(boolean canBeFull) {
		this(CAPSTD);
		this.canBeFull= canBeFull;
		this.size = 0;
	}
	public ArrayVector(int capacity){
		list = new Object[capacity];
		this.canBeFull=true;
		this.size=0;

	}

	/* FUNZIONALITA' BASE DEL VECTOR */

	public int size() {return size;}

	public boolean isEmpty() {return size==0;}

	public Object elementAtRank(int r) throws BoundaryViolationException {
		if(r >= size || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");

		return list[r];
	}

	public void insertAtRank(int r, Object e) throws BoundaryViolationException
	{
		if(r > size || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
		else{

			if((this.size() == list.length)&& (this.canBeFull))
				try {
					throw new FullVectorException("ArrayPieno!!");
				} catch (FullVectorException e1) {
					e1.printStackTrace();
				}
				if((this.size() == list.length)&& (!this.canBeFull)){
					duplica();	
				}
				
				for(int i = this.size(); i > r; i--)
					list[i]=list[i-1];
				
				list[r]=e;
				size++;
		}	
	}

	public Object removeAtRank(int r) throws BoundaryViolationException
	{
		Object e = null;
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
		e = list[r];
		for(int i = r+1; i < size; i++)
			list[i-1]=list[i];
		
		list[size-1] = null;
		size--;
		return e;
	}

	public Object replaceAtRank(int r, Object e)throws BoundaryViolationException
	{
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
		Object o = list[r];
		list[r]=e;
		return o;
	}

	private void duplica()
	{
		Object[] temp = list.clone();
		list = new Object[list.length*2];

		for(int i = 0; i < temp.length; i++)
			list[i] = temp[i];

	}
	public Iterator elements(){ return new VectorElementIterator(this); }
	/* FUNZIONALITA' ACCESSORIE DEL VECTOR */
	
	public boolean coniugata(Vector P){
		if(this.isEmpty())
			try {
				throw new EmptyVectorException("ArrayVector VUOTO!!!");
			} catch (EmptyVectorException e) {
				e.printStackTrace();
			}
		if(!(this.size() == P.size()))
			return false;
		
		boolean ris = false;
		for(int i = 0; i < this.size(); i++)
		{
			try {
				this.insertAtRank(size()- 1, this.removeAtRank(0));
				if(this.equals(P))
					ris = true;
			} catch (EmptyVectorException e) {
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
	
	public ArrayVector vectorNumElementiAlmenoRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
		
			ArrayVector vector = new ArrayVector(false);
			for(int i = r + 1 ; i < size(); i++)
				vector.insertAtRank(i-(r+1),this.elementAtRank(i) );
				
		return vector;
	}
	
	public int numElementiAlpiuRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
			
		return  r;
	}
	public ArrayVector vectorNumElementiAlpiuRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
		
			ArrayVector vector = new ArrayVector(false);
			for(int i =0 ; i <  r ; i++)
				vector.insertAtRank(i,this.elementAtRank(i) );
				
		return vector;
	}
	
	public int numElementiMaggioreUgualeRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
			
		return (size() - r);
	}
	public ArrayVector vectorNumElementiMaggioreUgualeRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
		
			ArrayVector vector = new ArrayVector(false);
			for(int i =r ; i < size() ; i++)
				vector.insertAtRank(i-r,this.elementAtRank(i) );
				
		return vector;
	}
	
	public int numElementiMinoreUgualeRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
			
		return (r + 1);
	}
	
	public ArrayVector vectorNumElementiMinoreUgualeRangoR(int r){
		if(r >= size() || r < 0)
			throw new BoundaryViolationException("Elemento inesistente per il rango richiesto!!");
		
			ArrayVector vector = new ArrayVector(false);
			for(int i =0 ; i < r + 1 ; i++)
				vector.insertAtRank(i,this.elementAtRank(i) );
				
		return vector;
	}

	public String toString()
	{
		String str = "";
		if(this.isEmpty()){
			try {
				throw new EmptyStackException("Struttura Dati vuota!!");
			} catch (EmptyStackException e) {
				e.printStackTrace();
				return str;
			}
		}
		str += new String("Classe ArrayVector che contiene:\n"+ "( ");
		
		for(int r = 0;r < this.size() -1 ; r++ )
			str += this.elementAtRank(r) + " ,".toString();
		
		str += elementAtRank(this.size() -1) + " )".toString();
		
		return str;
	}

	public Object clone()
	{
		ArrayVector cloned = new ArrayVector(false);
		for(int i = 0; i < size(); i++)
			cloned.insertAtRank(i, this.elementAtRank(i));
		
		return cloned;
	}

	public boolean equals(Object obj)
	{
		ArrayVector array = (ArrayVector) obj;
		if(this.size() != array.size())
			return false;

		boolean var = true;

		for(int r = 0; r < size(); r++)
		{	
			if(!this.elementAtRank(r).equals(array.elementAtRank(r)))
			{
				var = false;
				break;
			}
		}

		return var;
	}

	// costante standard di capacit�
	public static final int CAPSTD = 3;
	// contiene gli elementi dello stack
	private Object list[];
	// indice del top
	private int size = 0;
	// indica se lo stack pu� raggiungere la condizione di "pieno"
	private boolean canBeFull;
}


