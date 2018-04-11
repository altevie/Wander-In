package altevie.wanderin.utility.set;
// specializziamo l'algoritmo generale merge ridefinendo alcuni passi

import java.util.Comparator;

import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.sequence.Sequence;

public abstract class Merge {
	private Object a, b; // elementi correnti in A e B
	private Iterator iterA, iterB; // iteratori per A e B

	public void merge(Sequence A, Sequence B, Comparator comp, Sequence C) {
		iterA = A.elements();
		iterB = B.elements();
		boolean aExists = advanceA(); // Boolean test if there is a current a
		boolean bExists = advanceB(); // Boolean test if there is a current b
		while (aExists && bExists) { // Main loop for merging a and b
			int x = comp.compare(a, b);
			
			if (x < 0) { 
				aIsLess(a, C);
				aExists = advanceA(); 
			}
			else if (x == 0) 
				{
					bothAreEqual(a, b, C);
					aExists = advanceA();
					bExists = advanceB(); 
				}
				else
				{ 
					bIsLess(b, C);
					bExists = advanceB(); 
				} 
			}
		while (aExists) { aIsLess(a, C); aExists = advanceA(); }
		while (bExists) { bIsLess(b, C); bExists = advanceB(); }
	}

	protected void aIsLess(Object a, Sequence C) { }
	protected void bothAreEqual(Object a, Object b, Sequence C) { }
	protected void bIsLess(Object b, Sequence C) { }

	private boolean advanceA() {
		if (iterA.hasNext()) { a = iterA.next();
		return true; }
		return false;
	}
	private boolean advanceB() {
		if (iterB.hasNext()) { b = iterB.next();
		return true; }
		return false;
	}
}