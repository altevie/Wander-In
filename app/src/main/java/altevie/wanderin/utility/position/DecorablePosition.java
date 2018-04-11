package altevie.wanderin.utility.position;

import altevie.wanderin.utility.exceptions.InvalidKeyException;
import altevie.wanderin.utility.iterator.Iterator;

public interface DecorablePosition {

	public int size();
	public boolean isEmpty();
	public Object element();
	public Object get(Object key) throws InvalidKeyException;
	public Object put(Object key, Object value) throws InvalidKeyException;
	public Object remove(Object key) throws InvalidKeyException;
	public Iterator entries();
}