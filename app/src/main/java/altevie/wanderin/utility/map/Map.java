package altevie.wanderin.utility.map;

import altevie.wanderin.utility.exceptions.InvalidKeyException;
import altevie.wanderin.utility.iterator.Iterator;

public interface Map {

	public int size(); //restituisce il numero di entry della Map
	public boolean isEmpty(); //verifica se la Map � vuota
	//inserisce una coppia key-value e rimpiazza il value precedente, se k
	//c�� gi�, altrimenti null.
	public Object put(Object key, Object value) throws InvalidKeyException;
	//Restituisce il value associato a k
	public Object get(Object key) throws InvalidKeyException;
	//Cancella la coppia (chiave-valore) specificata da key
	public Object remove(Object key) throws InvalidKeyException;
	public Iterator keys(); //Restituisce un iteratore delle chiavi della mappa
	public Iterator values(); //Restituisce un iteratore dei valori della mappa
}