package altevie.wanderin.utility.set;

public interface Set {

	// Restituisce il numero degli elementi nell�insieme
	public int size();
	// Restituisce true se l�insieme � vuoto
	public boolean isEmpty();
	// Restituisce l�unione di this e B
	public Set union(Set B);
	// Restituisce l�intersezione di this e B
	public Set intersect(Set B);
	// Restituisce differenza di this e B
	public Set subtract(Set B);

	public Set insertElement(Object o);

}
