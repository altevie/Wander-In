package altevie.wanderin.utility.priorityQueue;

//InsertionSort

import altevie.wanderin.utility.exceptions.EmptyListException;
import altevie.wanderin.utility.exceptions.EmptyPQException;
import altevie.wanderin.utility.exceptions.InvalidKeyException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.QueueElementIterator;
import altevie.wanderin.utility.list.List;
import altevie.wanderin.utility.list.NodeList;
import altevie.wanderin.utility.position.Position;
import altevie.wanderin.utility.queue.ArrayQueue;
import altevie.wanderin.utility.sequence.Sequence;

public class SortedListPriorityQueue implements PriorityQueue{
	protected List L;
	protected Comparator c;

	/** Inner class for entries */
	protected static class MyEntry implements Entry 
	{
		public MyEntry(Object key, Object value) { //costruttore
			k = key;
			v = value;
			actionPos = null;
		}

		public Object key() { return k; }
		public Object element() { return v; }
		public Position loc() {return actionPos;}
		
		// METODI ACCESSORI
		public boolean equals(Object obj)
		{
			MyEntry ee = (MyEntry) obj;
			boolean bEl = ee.element().equals(this.element());
			boolean bKey = ee.key().equals(this.key());
			
			return (bEl && bKey);
		}

		public Object clone()
		{
			return (new MyEntry(this.key(),this.element()));
		}
		
		public String toString()
		{
			return "Entry: (" + this.key()+ "," + this.element() + ") ";
		}
		
		private void setLoc(Position p){actionPos = p;}
		private void setKey(Object key){k = key;}
		private void setValue(Object val){v= val;}
		

		protected Object k; // key
		protected Object v; // value
		private Position actionPos; // location della entry nella lista
	}

	/** Inner class for a default comparator using the natural ordering */
	protected static class DefaultComparator implements Comparator {
		public DefaultComparator() { /* default constructor */}

		public int compare(Object a, Object b) throws ClassCastException
		{
			//**MM      double compareTo = ((Comparable)a).compareTo(b);
			double a1 = Double.parseDouble(a.toString());
			double b1 = Double.parseDouble(b.toString());
			int compareTo = Double.compare(a1,b1);
			return compareTo;
		}
	}

	//creazione di una PQ con il default comparator
	public SortedListPriorityQueue () 
	{
		L = new NodeList();
		c = new DefaultComparator();
	}
	//creazione di una PQ con un dato comparator
	public SortedListPriorityQueue (Comparator comp)
	{
		L = new NodeList();
		c = comp;
	}

	/** Sets the comparator for this priority queue.
	 * @throws IllegalStateException if priority queue is not empty */
	public void setComparator(Comparator comp) throws IllegalStateException {
		if (!isEmpty()) throw new IllegalStateException("Priority queue is not"+
		"empty");
		c = comp;
	}
	public int size() {
		return L.size();
	}
	public boolean isEmpty() {
		return L.isEmpty();
	}

	public Entry min() throws EmptyPQException {
		if (L.isEmpty()) throw new EmptyPQException("PQ vuota, non c�� minimo");
		else return (Entry) L.first().element();
	}
	
	public Entry removeMin() throws EmptyPQException 
	{
		if (L.isEmpty()) 
			throw new EmptyPQException("PQ vuota, non c�� minimo");
		else
		{
			((MyEntry)(L.first().element())).setLoc(null);
			return (Entry) (L.remove(L.first()));
		}
	}
	/** Inserts a key-value pair and return the entry created. */
	public Entry insert(Object k, Object v) throws InvalidKeyException {
		MyEntry entry = new MyEntry(k, v);
		insertEntry(entry); // auxiliary insertion method
		return entry;
	}
	/** Auxiliary method that returns the key stored at a given node. */
	protected Object key(Position pos) {
		return ((Entry) pos.element()).key();
	}
	/** Auxiliary method used for insertion. */
	protected void insertEntry(Entry e) {
		Object k = e.key();
		if (L.isEmpty())
			((MyEntry)e).setLoc( (Position)L.insertFirst(e)); // insert into empty list
		
		else if (c.compare(k, key(L.last())) > 0) {
			((MyEntry)e).setLoc( (Position)L.insertLast(e)); // insert at the end of the list
		}
		else {
			Position curr = L.first();
			while (c.compare(k, key(curr)) > 0) {
				curr = L.after(curr); // advance toward insertion position
			}
			((MyEntry)e).setLoc( (Position)L.insertBefore(curr,e)); // useful for subclasses
		}
	}

	public void replaceKey(Entry e,Object k)
	{
		MyEntry ee = (MyEntry) e;
		Object key = ee.key();
		checkKey(key);
		Remove(ee);
		ee.setLoc(null);
		ee.setKey(k);
		insertEntry(ee);
	}

	public Entry replaceValue(Entry e,Object x)
	{
		Object key = e.key();
		checkKey(key); 
		((MyEntry)e).setValue(x);
		return e;
	}

	public Entry Remove(Entry e){

		if (L.isEmpty()) throw new EmptyPQException("PQ vuota, non c'è minimo");
		else
		{
			Object key = e.key();
			checkKey(key); 
			return (Entry) (L.remove(e.loc()));
		}
	}

	private Object checkKey(Object k)throws InvalidKeyException{
		if (L.isEmpty()) {
			throw new InvalidKeyException("Kiave non trovata!!");
		}
		else if ((c.compare(k, key(L.last())) > 0) ||(c.compare(k, key(L.first())) < 0)) {
			throw new InvalidKeyException("Kiave non trovata!!");
		}
		else {
			Position curr = L.first();
			while ((c.compare(k, key(curr)) != 0)&&(curr != null)) {
				curr = L.after(curr); // advance toward insertion position
			}
			if((c.compare(k, key(curr)) == 0))	
				return k;
			else
				throw new InvalidKeyException("Kiave non trovata!!");
		}
	}
	
	//METODI ACCESSORI	
	public Iterator removeLess(Object x){
		Entry e = null;
		if(this.isEmpty())
			throw new EmptyPQException("Coda Vuota !!");
		List output = new NodeList();
		Object minKey = this.min().key();
		
		if(c.compare(x, minKey) < 0)
			throw new IllegalArgumentException("Input non valido !!");
		
		ArrayQueue appoggio = new ArrayQueue(false);
		while(c.compare(x, minKey) >  0){
			e = this.removeMin();
			minKey = e.key();
			Object minValue = e.element();
			appoggio.enqueue(e);
			output.insertLast(minValue);
		}
		while(!appoggio.isEmpty()){
			this.insertEntry((Entry)appoggio.dequeue());
		}
		return output.elements();
		
	}
	public Iterator entries(){return new QueueElementIterator();}
	
	public Iterator removeIn(Object x, Object y)throws EmptyPQException{
		if(this.isEmpty())
			throw new EmptyPQException("Coda Vuota !!");

		List output = new NodeList();
		Object minKey = this.min().key();
		if(c.compare(x, y)>0)
			throw new IllegalArgumentException("Input non validi !!");
		
		if(c.compare( minKey,  y) > 0)
			throw new IllegalArgumentException("Input non validi !!");
		
		while(c.compare(minKey, y)< 0){
			Entry e = this.removeMin();
			minKey = e.key();
			Object minValue = e.element();
			if(c.compare(x, minKey)<= 0)
				output.insertLast(minValue);
		}
		return output.elements();
		
	}
	public Position checkEntryPosition(Object e , Object k)
	{
		Iterator it = L.elements();
		while(it.hasNext()){
			MyEntry myE =(MyEntry)it.next();
			if((myE.element().equals(e))&& (myE.key().equals(k)))
				return myE.loc();
		}
		return null;
	}
	
	public Entry checkEntry(Object e, Object k)
	{
		Iterator it = L.elements();
		while(it.hasNext())
		{
			MyEntry myE =(MyEntry)it.next();

			//Log.i("myE_element:",myE.element().toString());
			//Log.i("myE_key:",myE.key().toString());
			//Log.i("element:",e.toString());
			//Log.i("key:",myE.k.toString());

			//MM***************
				//if((myE.element().equals(e))&& (myE.key().equals(k)))
				//	return myE;
			if((myE.element().toString().substring(0,16).equals(e.toString().substring(0,16)))&& (myE.key().equals(k)))
				return myE;
		}
		return null;
	}
	
	public String toString()
	{
		String str = "";
		if(this.isEmpty())
			throw new EmptyListException("Struttura Dati vuota!!");

		str += "Classe che contiene :( Front : ";
		
		Iterator it = L.elements();
		int i = 0;
		while (it.hasNext())
		{
			str += "" + (it.next()).toString();
			if(i == (this.size() - 1))
				str +=")";
			else
				str +=",";
			
			i++;
		}
		
		return str;
	}
	
	
	public String toStringKey()
	{
		String str = "";
		if(this.isEmpty())
			throw new EmptyListException("Struttura Dati vuota!!");

		str += "Classe che contiene :( Front : ";
		
		Iterator it = L.elements();
		int i = 0;
		while (it.hasNext())
		{
			str += "" + (((Entry)it.next()).key());
			if(i == (this.size() - 1))
				str +=")";
			else
				str +=",";
			
			i++;
		}
		
		return str;
	}
	
	public String toStringValue()
	{
		String str = "";
		if(this.isEmpty())
			throw new EmptyListException("Struttura Dati vuota!!");

		str += "Classe che contiene :( Front : ";
		
		Iterator it = L.elements();
		int i = 0;
		while (it.hasNext())
		{
			str += "" + (((Entry)it.next()).element()).toString();
			if(i == (this.size() - 1))
				str +=")";
			else
				str +=",";
			
			i++;
		}
		
		return str;
	}
	public boolean equals(Object obj)
	{
		SortedListPriorityQueue PQ = (SortedListPriorityQueue) obj;
		if(PQ.size() != this.size())
			return false;
		
		boolean var = true;
		Iterator it1 = this.L.elements();
		Iterator it2 = PQ.L.elements();
		while(it1.hasNext())
		{
			MyEntry e1 = (MyEntry) it1.next();
			MyEntry e2 = (MyEntry) it2.next();
			if(!e1.equals(e2))
			{
				var = false;
				break;
			}
		}
		
		return var;
	}
	
	public Object clone()
	{
		SortedListPriorityQueue clonedPQ = new SortedListPriorityQueue();
		
		Iterator it = this.L.elements();
		while(it.hasNext())
		{
			MyEntry temp = (MyEntry) it.next();
			MyEntry cloned = (MyEntry) temp.clone();
			clonedPQ.insertEntry(cloned);
		}
		
		return clonedPQ;
	}
	
	public static Sequence PQ_Sort(Sequence s, PriorityQueue q)
	{
		while(!s.isEmpty())
		{
			Object obj = s.remove(s.first());
			q.insert(obj, null);
		}
		
		while(!q.isEmpty())
			s.insertLast(q.removeMin().key());
		
		return s;
	}
}