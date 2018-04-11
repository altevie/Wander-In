package altevie.wanderin.utility.map;

import altevie.wanderin.utility.exceptions.EmptyListException;
import altevie.wanderin.utility.exceptions.InvalidKeyException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.list.List;
import altevie.wanderin.utility.list.NodeList;
import altevie.wanderin.utility.position.Position;

public class DoubleLinkedListMap implements Map {
	
	/** Inner class for entries */
	protected static class MyEntry implements Entry {
		
		public MyEntry(Object key, Object value) { //costruttore
			k = key;
			v = value;
			actionPos = null;
		}
		// methods of the Entry interface
		
		public Object key() { return k; }
		public Object element() { return v; }
		public Position loc() {return actionPos;}
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
		private Position actionPos;
	}
	
	protected static class DefaultEqualityTester implements EqualityTester{
		public DefaultEqualityTester() { /* default constructor */}
		public boolean isEqualTo(Object a, Object b) throws ClassCastException {
			return (a.equals(b));
		}
	}
	
	
	public DoubleLinkedListMap(){
		list = new NodeList();
		eqTester = new DefaultEqualityTester();
	}
	public DoubleLinkedListMap(EqualityTester et){
		list = new NodeList();
		eqTester = et;
	}
	
	// Verifica se la chiave � presente nella mappa .. utilizzata nell'inserimento di un entry
	private Object checkKey(Object k)throws InvalidKeyException{
		if(k== null)
			throw new InvalidKeyException("Valore della chiave errato!!!");
		
		return k;
	}
	
	public Object get(Object key) throws InvalidKeyException
	{
		this.checkKey(key);
		Iterator itKeys = this.keys();
		Iterator itValues = this.values();
		while(itKeys.hasNext()){
			Object currentKey = itKeys.next();
			Object currentValue = itValues.next();
			if(currentKey.equals(key))
				return currentValue;
		}
		return null;
	}
	
	
	public boolean isEmpty() {return list.isEmpty();}
	
	public Iterator keys() {
		List L = new NodeList();
		Iterator it = this.list.elements();
		while(it.hasNext()){
			MyEntry myE =(MyEntry)it.next();
			L.insertLast(myE.key());
		}
		return L.elements();
		
	}
	
	public Object put(Object key, Object value) throws InvalidKeyException {
		this.checkKey(key);
		Iterator it = this.positions();
		while(it.hasNext()){
			Position p = (Position)it.next();
			if(eqTester.isEqualTo(((MyEntry)p.element()).key(), key)){
				MyEntry myE = new MyEntry(key,value);
				return this.replace(p,myE);	
			}
			
		}
		
		MyEntry myE = new MyEntry(key,value);
		myE.actionPos = list.insertLast(myE);
		
		return null;
	}
	
	public Object replace(Position p, Entry myE)
	{
		Object obj = ((MyEntry)p.element()).element();
		((MyEntry)p.element()).setValue(myE.element());
		return obj;
		
	}
	
	public Object remove(Object key) throws InvalidKeyException {
		this.checkKey(key);
		Object obj = null;
		Iterator it =this.positions();
		while( it.hasNext()){ 
			Position p = (Position)it.next();
			if (eqTester.isEqualTo(((MyEntry)p.element()).key(), key)){
				obj = ((MyEntry)p.element()).element();
				list.remove(p);
			}
		}
		return obj;
	}
	
	public int size() {return list.size();}
	
	public Iterator positions() {return list.positions();}
	
	public Iterator values() {
		List l = new NodeList();
		Iterator it = list.elements();
		while(it.hasNext()){
			l.insertLast(((MyEntry)it.next()).element());
		}
		return l.elements();
	}
	//METODI AGGIUNTIVI 
	
	public String toStringKey(){
		String str = "";
		if(this.isEmpty())
			throw new EmptyListException("Struttura Dati vuota!!");
		
		str += "Classe che contiene le chiavi :( ";
		
		Iterator it = this.keys();
		int i = 0;
		while (it.hasNext()) {
			if(i == this.size()-1)
				str += "" + it.next()+" )";
			else
				str += "" + it.next()+" ,";
			i++;	
		}
		
		return str;
	}
	
	public String toStringValue(){
		String str = "";
		if(this.isEmpty())
			throw new EmptyListException("Struttura Dati vuota!!");
		
		str += "Classe che contiene i valori :( ";
		
		Iterator it = this.values();
		int i = 0;
		while (it.hasNext()) {
			if(i == this.size()-1)
				str += "" + it.next()+" )";
			else
				str += "" + it.next()+" ,";
			i++;	
		}
		return str;
	}
	public String toStringKeyValue(){
		String str = "";
		if(this.isEmpty())
			throw new EmptyListException("Struttura Dati vuota!!");
		
		str += "Classe che contiene le chiavi :( ";
		
		Iterator it = this.keys();
		int i = 0;
		while (it.hasNext()) {
			if(i == this.size()-1)
				str += "" + it.next()+" )";
			else
				str += "" + it.next()+" ,";
			i++;	
		}
		it = this.values();
		str += "e i valori :( ";
		int k = 0;
		while (it.hasNext()) {
			if(k == this.size()-1)
				str += "" + it.next()+" )";
			else
				str += "" + it.next()+" ,";
			k++;	
		}
		return str;
	}
	public String toStringPair(){
		String str = "";
		if(this.isEmpty())
			throw new EmptyListException("Struttura Dati vuota!!");
		
		str += "Classe che contiene le coppie (KEY, VALUE) :( ";
		
		Iterator it = this.positions();
		
		while (it.hasNext()) {
			Position p = (Position)it.next();
			
			str += "(" + ((MyEntry)p.element()).key()+" , "+((MyEntry)p.element()).element()+ ")".toString() ;
			
		}
		str +=")";
		
		return str;
	}
	public boolean equals(Object obj)
	{
		DoubleLinkedListMap PQ = (DoubleLinkedListMap) obj;
		if(PQ.size() != this.size())
			return false;
		
		boolean var = true;
		Iterator it1 = this.list.elements();
		Iterator it2 = PQ.list.elements();
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
		DoubleLinkedListMap clonedPQ = new DoubleLinkedListMap();
		
		Iterator it = this.list.elements();
		while(it.hasNext())
		{
			MyEntry temp = (MyEntry) it.next();
			clonedPQ.put(temp.k, temp.v);
		}
		
		return clonedPQ;
	}
	
	private List list;
	private EqualityTester eqTester;
}
