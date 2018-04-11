package altevie.wanderin.utility.sequence;

import altevie.wanderin.utility.exceptions.BoundaryViolationException;
import altevie.wanderin.utility.exceptions.EmptyListException;
import altevie.wanderin.utility.exceptions.EmptySequenceException;
import altevie.wanderin.utility.exceptions.InvalidElementFoundException;
import altevie.wanderin.utility.exceptions.InvalidPositionException;
import altevie.wanderin.utility.exceptions.NecessaryElementNotFoundException;
import altevie.wanderin.utility.exceptions.OneElemListException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.ListElementIterator;
import altevie.wanderin.utility.iterator.ListPositionIterator;
import altevie.wanderin.utility.list.DNode;
import altevie.wanderin.utility.list.NodeList;
import altevie.wanderin.utility.position.Position;

public class NodeSequence extends NodeList implements Sequence {
	
	public NodeSequence(){
		super();
	}
	
	// controlla che il rango sia nel range [0,numElements-1]
	
	protected void checkRank(int rank, int n) throws BoundaryViolationException {
		if (rank < 0 || rank >= n)
			throw new BoundaryViolationException("Rango " + rank + " non valido per" +
			" questa sequenza di " + n + " elementi.");
	}
		
	//Operazioni dal TDA Vector
	public Object elementAtRank(int r) throws BoundaryViolationException {
		this.checkRank(r, size());
		return this.atRank(r).element();
	}

	public void insertAtRank (int rank, Object element)throws BoundaryViolationException {
		checkRank(rank, size()+ 1);
		if (rank == size())
			insertLast(element);
		else insertBefore(atRank(rank), element);
	}
	
	public Object removeAtRank (int rank) throws BoundaryViolationException {
		checkRank(rank,size());
		return remove(atRank(rank));
	}

	public Object replaceAtRank (int rank, Object element)throws BoundaryViolationException {
		checkRank(rank,size());
		return replace(atRank(rank),element);
	}
	
	
	//metodi ponte
	
	public Position atRank(int rank) throws BoundaryViolationException{
		DNode node;
		checkRank(rank, size());
		if (rank <= size()/2) { //scorre la lista a partire dall�header
			node = ((DNode)first());
			for (int i=0; i < rank; i++)
				node = node.getNext(); }
		else { //scorre la lista all�indietro a partire dal trailer
			node = ((DNode)last());
			for (int i=1; i < size()-rank; i++)
				node = node.getPrev(); }
		return node;
	}

	public int  rankOf(Position p)throws InvalidPositionException {
		DNode node;
		int rango = 0;
		this.CheckPosition(p);
		node = (DNode) first();

		for(; rango< size(); rango++)
		{
			if (node.equals((DNode)p))
				break;

			node = node.getNext();			
		}
		
		return rango;
	}
	public Iterator positions() { return new ListPositionIterator(this); }
	public Iterator elements() { return new ListElementIterator(this); }
	
	//METODI ACCESSORI
	/*
	public void LocalRemoveEven(int x){
		if(this.isEmpty())
			throw new EmptySequenceException("Vuota !!");
		Iterator itE = this.elements();
		int rango =0;
		while(itE.hasNext()){
			if(((Integer)itE.next()).intValue()== x )
				break;
			rango = rango + 1;
		}
		
		if(rango == this.size())
			throw new NoSuchElementException("Non c'� l'intero!!");
		
		Position px = this.atRank(rango);
		Position copiaPx = px;
		boolean trovato = false;
		
		while((!(this.after(copiaPx)== this.last())) && (!trovato)){
			Object elem = this.after(copiaPx).element();
			
			if(((Integer)elem).intValue() == x)
				trovato = true;
			
			copiaPx = this.after(copiaPx);
		}
		
		if(this.after(copiaPx)== this.last()){
			Object elem1 = (this.after(copiaPx)).element();
			if(((Integer)elem1).intValue()==x){
				copiaPx = this.last();
				trovato = true;
			}
		}
		
		if(!trovato)
			throw new NoSuchElementException("Non c'� la seconda occorrenza dell'intero!!");
		
		int rango2 = this.rankOf(copiaPx);
		int N = rango2 - rango - 1;
		int Nc=0;
		
		if((N%2)==0)
			Nc = N/2;
		else
			Nc = ((int)N/2)+1;
			
			for(int i =0; i <= (Nc  - 1) ; i++)
				this.removeAtRank(rango + i + 1);
		
	}
*/
	public Sequence Fondi(Sequence M){
		int l = this.size();
		int s = M.size();
		
		if(!this.elementAtRank(l-1).equals("*"))
			throw new NecessaryElementNotFoundException("Sequence non valida!!");
		if(!M.elementAtRank(s-1).equals("*"))
			throw new NecessaryElementNotFoundException("Sequence in input non valida!!");
		
		Iterator it =((NodeSequence)this).elements();
		Iterator it2 = ((NodeSequence)M).elements();
		
		Object var =null;
		int a=0;
		while(a< l-1){
			Object obj =this.elementAtRank(a);
			if(var==null)
				var = obj;
			if(((Comparable)var).compareTo((Comparable)obj) <= 0)
				var = obj;
			else
				throw new InvalidElementFoundException("Lista non ordinata !!");
			a++;
		}
		
		var =null;
		a=0;
		while(a< s-1){
			Object obj =((NodeSequence)M).elementAtRank(a);
			if(var==null)
				var = obj;
			if(((Comparable)var).compareTo((Comparable)obj) <= 0)
				var = obj;
			else
				throw new InvalidElementFoundException("Lista in input non ordinata !!");
			a++;
		}
		NodeSequence F = new NodeSequence();
		Object obj, obj1 ;
		int r = 0, r1 =0;
		NodeSequence P = ((NodeSequence)M);
		while((r < l-1) &&(r1 < s-1)){
			obj = this.elementAtRank(r);
			obj1 = ((NodeSequence)M).elementAtRank(r1);
			if(((Comparable)obj).compareTo((Comparable)obj1) <= 0 ){
				F.insertLast(obj);
				r++;
			}
			else{
				F.insertLast(obj1);
				r1++;
			}
		}
		if(r < l-1){
			while(r < (l-1)){
			F.insertLast(this.elementAtRank(r));
			r++;
			}
		}
		if(r1 < s-1){
			while(r1 < (s-1)){
			F.insertLast(((NodeSequence)M).elementAtRank(r1));
			r1++;
			}
		}
		return F;
	}

	public void LocalRemoveEven(int x){
		int var=2;
		Position node = (DNode)this.first();
		int taglia = this.size();
		int primo = 0;
		int secondo =0;
		for(int i =0; (i < taglia)&& (var != 0); i++){
			
			if(((node.element()).equals(x))&& (var == 2)){
				primo = i;
				var--;
				node = this.after(node);
				continue;
			}
			if((node.element()).equals(x)&& var == 1){	
				secondo =i;
				var--;
				node = this.after(node);
				continue;
			}
			node = this.after(node);
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
	
	public Sequence LocalRemoveEvent(int x){
		NodeSequence S = (NodeSequence) this.clone();
		S.LocalRemoveEven(x);
		return S;
	}

	public boolean coniugata(Sequence P){
		if(this.isEmpty())
			try {
				throw new EmptySequenceException("Sequence VUOTA!!!");
			} catch (EmptySequenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(!(this.size() == P.size()))
			return false;
		boolean ris = false;
		int taglia = this.size();
		for(int i=1; i <=taglia; i++){
			try {
				this.insertLast(this.removeAtRank(0));
				if(this.equals(P))
					ris = true;
			} catch (EmptySequenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ris;
	}
	
	
	public String toString()
	{
		String str = "";
		if(this.isEmpty())
			throw new EmptyListException("Struttura Dati vuota!!");

		str += "Classe che contiene :( Front : ";
		int k = 0;
		for(DNode i = (DNode) this.first(); k < this.size(); i = i.getNext(), k++)
		{
			str += "" + i.element().toString() + " ";
		}
		str +=")";
		
		return str;
	}

	public Object clone()
	{
		NodeSequence cloned= new NodeSequence();
		DNode prec = (DNode)this.first();
		for(int i =0; i < this.size(); i++){
			cloned.insertLast(prec.element());
			prec = prec.getNext();
		}
		return cloned;
	}
	
	public boolean equals(Object obj){
		NodeSequence nodeSequence = (NodeSequence)obj;
		if(nodeSequence.size()!= this.size())
			return false;

		boolean var = true;

		for(int r = 0; r < this.size(); r++)
		{	
			if(!this.elementAtRank(r).equals(nodeSequence.elementAtRank(r)))
			{
				var = false;
				break;
			}
		}

	return var;	
	}

}
