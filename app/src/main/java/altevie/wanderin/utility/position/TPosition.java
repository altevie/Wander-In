package altevie.wanderin.utility.position;

import altevie.wanderin.utility.iterator.Iterator;

public interface TPosition extends Position{
	// Metodi di accesso
	public Iterator getChildren(); 
	public TPosition getParent();

	// Metodi di aggiornamento  
	public void setElement(Object newElem);
	public void setChildren(TPosition newChildren);
	public void setParent(TPosition newParent);
}
