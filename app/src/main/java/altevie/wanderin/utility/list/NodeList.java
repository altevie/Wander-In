package altevie.wanderin.utility.list;

import altevie.wanderin.utility.exceptions.BoundaryViolationException;
import altevie.wanderin.utility.exceptions.EmptyListException;
import altevie.wanderin.utility.exceptions.InvalidPositionException;
import altevie.wanderin.utility.exceptions.NecessaryElementNotFoundException;
import altevie.wanderin.utility.exceptions.NoSuchElementException;
import altevie.wanderin.utility.exceptions.OneElemListException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.ListElementIterator;
import altevie.wanderin.utility.iterator.ListPositionIterator;
import altevie.wanderin.utility.map.DoubleLinkedListMap;
import altevie.wanderin.utility.map.Entry;
import altevie.wanderin.utility.position.Position;

public class NodeList implements List{

	public NodeList() {
		size = 0;
		header = new DNode(null,null,null);
		trailer = new DNode(null,null,null);
		header.setNext(trailer);
		trailer.setPrev(header);
	}
	
	/* FUNZIONALITA' BASE DELLA NODELIST */
	
	public DNode CheckPosition(Position p) throws InvalidPositionException{
		if (p == null) 
			throw new InvalidPositionException("Posizione nulla passata a NodeList");
		if (p == header) 
			throw new InvalidPositionException("Header non � una posizione valida");
		if (p == trailer) 
			throw new InvalidPositionException("Trailer non � una posizione valida");
		
		try { 
			DNode temp = (DNode) p; // si tenta il cast: se ha successo, potr�
			//riferirmi al nodo e quindi implementare, ad es., before(p) con v.getPrev()
			if ((temp.getPrev() == null) || (temp.getNext() == null)) 
				throw new InvalidPositionException("Posizione non appartenente ad una valida NodeList");
			
			return temp; 
		}
		catch (ClassCastException e) {
			throw new InvalidPositionException("Posizione di tipo sbagliato per questo contenitore lista"); 
		}
	}
	
	public int size() {return size;}
	
	public boolean isEmpty() {return (size == 0);}
	
	public Position first() throws EmptyListException {
		if(this.isEmpty())
			throw new EmptyListException("Lista VUOTA!!");
		return header.getNext();
	}
	
	public Position last() throws EmptyListException {
		if(this.isEmpty())
			throw new EmptyListException("Lista VUOTA!!");
		return trailer.getPrev();
	}

	public Position before(Position p) throws InvalidPositionException,BoundaryViolationException {
		try{
			DNode node = this.CheckPosition(p);	
			return node.getPrev();
		}catch(BoundaryViolationException e){
			throw new BoundaryViolationException("Non � possibile andare oltre l'header!");
		}
	}

	public Position after(Position p) throws InvalidPositionException,BoundaryViolationException {
		try{
			DNode node = this.CheckPosition(p);	
			return node.getNext();
		}catch(BoundaryViolationException e){
			throw new BoundaryViolationException("Non � possibile andare oltre il trailer!");
		}
	}
	
	public Position insertBefore(Position p, Object o)throws InvalidPositionException {
		DNode node = this.CheckPosition(p);
		DNode newNode = new DNode(o,node.getPrev(),node);
		node.getPrev().setNext(newNode);
		node.setPrev(newNode);
		this.size++;
		return newNode;
	}

	public Position insertAfter(Position p, Object o)throws InvalidPositionException {
		DNode node = this.CheckPosition(p);
		DNode newNode = new DNode(o,node,node.getNext());
		node.getNext().setPrev(newNode);
		node.setNext(newNode);
		this.size++;
		return newNode;
	}

	public Position insertFirst(Object o) {
		DNode node = new DNode(o,header,header.getNext());
		header.getNext().setPrev(node);
		header.setNext(node);
		this.size++;
		return node;
	}

	public Position insertLast(Object o) {
		DNode node = new DNode(o,trailer.getPrev(),trailer);
		trailer.getPrev().setNext(node);
		trailer.setPrev(node);
		this.size++;
		return node;
	}

	public boolean isFirst(Position p) throws InvalidPositionException {
		DNode node = this.CheckPosition(p);
		return (node.getPrev()== header);
	}

	public boolean isLast(Position p) throws InvalidPositionException {
		DNode node = this.CheckPosition(p);
		return (node.getNext()== trailer);
	}

	public Object remove(Position p) throws InvalidPositionException {
		DNode node = this.CheckPosition(p);
		Object o = node.element();
		DNode b = node.getPrev();
		DNode a = node.getNext();
		b.setNext(a);
		a.setPrev(b);
		node.setPrev(null);
		node.setNext(null);
		this.size--;
		return o;
	}

	public Object replace(Position p, Object o) throws InvalidPositionException {
		DNode node = this.CheckPosition(p);
		Object old = node.element();
		node.setElement(o);
		return old;
	}
	
	public Iterator positions(){return new ListPositionIterator(this);}
	public Iterator elements(){ return new ListElementIterator(this); }

	/* FUNZIONALITA' ACCESSORIE DELLA NODELIST */
	public void LocalRemoveEven(int x){
		int var=2;
		Position node = (DNode)this.first();
		int taglia = this.size();
		int primo = 0;
		int secondo =0;
		Position p = null, s = null;
		for(int i =0; (i < taglia) && (var != 0); i++){
			
			if(((node.element()).equals(x))&& (var == 2)){
				primo = i;
				var--;
				p= node;
				node = this.after(node);
				continue;
			}
			if((node.element()).equals(x)&& var == 1){	
				secondo =i;
				var--;
				s = node;
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
				
			Position prec = null;
			for(int i =0; i <= (Nc  - 1) ; i++){ 
				int z = primo + i + 1;
				int k=0;
				prec = p;
				while (k < z){
					prec = this.after(prec);

				}
				this.remove(prec);
			}
				

	}
	
	public List LocalRemoveEvent(int x){
		NodeList L = (NodeList) this.clone();
		L.LocalRemoveEven(x);
		return L;
	}
	
	public void MakeFirst(Position p){
		this.CheckPosition(p);
		DNode dp = (DNode)p;
		
		if(dp.eq(this.first()))
			throw new InvalidPositionException("Lista non valida perk� l'elemento � in prima posizione!!");

		while(this.before(p)!= header){
			Object obj = this.remove(this.before(p));
			this.insertAfter(p,obj);

		}
			
	}

	public void removeMaxOcc(){
		DoubleLinkedListMap map = new DoubleLinkedListMap();
		Iterator it = this.positions();
		int i =1;
		while(it.hasNext()){
			Position p = ((Position)it.next());
			Object obj = p.element();
			
			Object o = map.put(obj, i);
			if(o != null)
				map.put(obj, i++);

		}
		it = map.positions();
		Integer max =0;
		int k=0;
		Entry ee = null;
		if(!map.isEmpty()){
			ee = (Entry)(((Position)it.next()).element());
			max = (Integer)ee.element();
			k = Integer.valueOf(""+ee.key());
		}
		while(it.hasNext()){
			ee = (Entry)(((Position)it.next()).element());
			int num = (Integer)ee.element();
			if( num > max){
				max = num;
				k = Integer.valueOf(""+ee.key());
			}
		}
		it = this.positions();
 		while(it.hasNext()){
 		Position pp = (Position)it.next();	
 		Object ob = pp.element();
 		if((Integer.valueOf(""+ob)).equals(k))
 			this.remove(pp);
 		}
	}
	
	public void Rimuovi(int x)throws NoSuchElementException{
		int rank1 =-1, rank2= 0;
		if(this.isEmpty())
			try {
				throw new EmptyListException("Sequence VUOTA!!!");
			} catch (EmptyListException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Iterator it = this.positions();
			boolean var  = false;
			int ele=0;

			while(it.hasNext()){
				if(!var){
					rank1++;
					int el =(int) Integer.valueOf(((DNode)it.next()).element().toString());
					ele = el;
				}
				if(ele == x){
					var = true;
					rank2++;
					int el1 =(int) Integer.valueOf(((DNode)it.next()).element().toString());
					if(el1 == x){
						rank2 += rank1; 
						Iterator it1 = this.positions();
						int i=0;
						while(it1.hasNext()&& (i < rank1)){
							it1.next();
							i++;
						}
						while((it1.hasNext())&&(rank1 < rank2) ){
							it1.next();
							this.remove((DNode)it1.next());
							rank1+= 2;
						}
					}
				}
			}

			if(rank2 == 0)
				throw new NoSuchElementException("Non � stato inserito un valore di cui la occorrenza sia presente come minimo 2 volte!!");

	}
	public boolean coniugata(List P){
		if(this.isEmpty())
			try {
				throw new EmptyListException("Sequence VUOTA!!!");
			} catch (EmptyListException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(!(this.size() == P.size()))
			return false;
		boolean ris = false;
		int taglia = this.size();
		for(int i=1; i <=taglia; i++){
			try {
				this.insertLast(this.remove(this.first()));
				if(this.equals(P))
					ris = true;
			} catch (EmptyListException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ris;
	}
	
	public Object[] removeFrom(Position p)
	{	
		CheckPosition(p);
		
		int i = 0;
		for(DNode idx = (DNode) this.last(); idx != ((DNode) p).getPrev(); idx = idx.getPrev())			
			i++;
		
		Object[] array = new Object[i];
		
		DNode sentinella = ((DNode) p).getPrev();
		i--;
		DNode temp = (DNode) this.last();
		while(temp != sentinella)
		{
			array[i] = temp.element();
			temp = temp.getPrev();
			i--;
		}
		
		return array;
	}
	
	public Object[] removeTo(Position p)
	{	
		int i = 0;
		for(DNode idx = (DNode) this.first(); idx != ((DNode)p).getNext(); idx = idx.getNext())			
			i++;
		
		Object[] array = new Object[i];
		DNode sentinella = ((DNode) p).getNext();

		i = 0;
		DNode temp = (DNode) this.first();
		while(temp != sentinella)
		{
			array[i] = temp.element();
			temp = temp.getNext();
			i++;
		}
		
		return array;
	}
	
	public void swapElements(Position p, Position q){
	
		Position positionBeforeP = this.before(p);
		Position positionBeforeQ = this.before(q);
		
		this.insertAfter(positionBeforeP, q.element());
		this.insertAfter(positionBeforeQ, p.element());
		this.remove(p);
		this.remove(q);
	}
	
	public void removeOdd(){
		if(this.isEmpty())
			throw new EmptyListException("Lista VUOTA!!");
		if(this.size()== 1)
			throw new OneElemListException("Non ci sono elementi di rango dispari!!");
		
		int n = ((int)size()/2);
		
		int k = 0;
		for(DNode i = (DNode) this.first(); k < n ; i = i.getNext(), k++){
			this.remove(i.getNext());
		}
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
		NodeList cloned= new NodeList();
		DNode prec = (DNode)this.first();
		for(int i =0; i < this.size(); i++)
		{
			cloned.insertLast(prec.element());
			prec = prec.getNext();
		}
		return cloned;
	}
	
	public boolean equals(Object obj){
		NodeList nodeList = (NodeList)obj;
		
		if(nodeList.size()!= this.size())
			return false;

		boolean var = true;
		DNode node = (DNode)this.first();
		DNode temp =(DNode)nodeList.first();
		for(int r = 0; r < size(); r++)
		{	
			if(!node.equals(temp))
			{
				var = false;
				break;
			}
			temp =temp.getNext();
			node = node.getNext();
		}
		return var;
	}
	

	
private int size;
private DNode header, trailer;

}
