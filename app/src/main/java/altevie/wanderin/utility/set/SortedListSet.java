package altevie.wanderin.utility.set;

import java.util.Comparator;

import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.sequence.NodeSequence;
import altevie.wanderin.utility.sequence.Sequence;

public class SortedListSet implements Set{
	protected Sequence L;
	protected Comparator comp;
	protected int size;
	
	/** Inner class for a default comparator using the natural ordering */
	protected static class DefaultComparator implements Comparator {

		public DefaultComparator() { /* default constructor */}
		public int compare(Object a, Object b) throws ClassCastException
		{
			return ((Comparable)a).compareTo(b);
		}
	}
	
	public SortedListSet(){
		L = new NodeSequence();
		comp = new DefaultComparator();
	}
	public SortedListSet(Comparator c){
		L = new NodeSequence();
		comp = c;
	}
	
	public int size() { return L.size(); }
	public boolean isEmpty() { return L.isEmpty(); }
	
	public Sequence getStructure() { return L; }

	public Set union(Set B) {
		UnionMerge U = new UnionMerge();
		Set C = new SortedListSet();
		U.merge(this.getStructure(), ((SortedListSet)B).getStructure(), comp, ((SortedListSet)C).getStructure());
		size = L.size();
		return C;
	}
	public Set intersect(Set B) {
		IntersectMerge I = new IntersectMerge();
		Set C = new SortedListSet();
		I.merge(this.getStructure(), ((SortedListSet)B).getStructure(), comp, ((SortedListSet)C).getStructure());
		size = L.size();
		return C;
	}
	public Set subtract(Set B) { 
		SubtractMerge S = new SubtractMerge();
		Set C = new SortedListSet();
		S.merge(this.getStructure(), ((SortedListSet)B).getStructure(), comp, ((SortedListSet)C).getStructure());
		size = L.size();
		return C;
	}

	public Set insertElement(Object o) {
		SortedListSet P = new SortedListSet();
		SetPosition sp = new SetPosition(o);
		P.getStructure().insertLast(sp);
		SortedListSet C = ((SortedListSet)this.union(P));
		L = C.getStructure();
		size = L.size();
		sp.setSet(this);
		return C;
	}
	//METODI AGGIUNTIVI
	
	public boolean CheckSets(Set B, Set C){
		if(((SortedListSet)B).subset(this))
		{
			if((!((this.subtract(B)).intersect(C)).isEmpty()))
				return true;
			else
				return false;
		}
		else
		{
			SortedListSet s1 = (SortedListSet) this.subtract(B);
			SortedListSet s2 = (SortedListSet) B.subtract(this);
			if(C.equals(s1.union(s2)) || C.equals(s1) || C.equals(s2))
				return true;
			else 
				return false;
		}	
	}
	public boolean subset(Set B)
	{
		if((this.union(B)).size() == B.size())
			return true;
		
		return false;
		
	}
	public boolean equals(Set B){
		if (size()!= B.size()) return false;
		Set R = intersect(B);
		if ((R.size())==size()) return true;  
		else return false;
	}
	
	public Iterator elements()
	{
		return L.elements();
	}
	
	public String toString(){
		return this.L.toString();
	}
	
	public Object clone()
	{
		SortedListSet newSet = new SortedListSet();
		if(isEmpty())
			return newSet;
		
		Iterator it = this.L.elements();
		while(it.hasNext())
		{
			newSet.getStructure().insertLast(it.next());
			newSet.size++;
		}
		
		return newSet;
	}
}