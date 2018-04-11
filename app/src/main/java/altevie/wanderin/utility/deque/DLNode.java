package altevie.wanderin.utility.deque;

public class DLNode {

	public DLNode(){ 
		this(null, null, null); 
	}

	public DLNode(Object e, DLNode p, DLNode n){
		element = e;
		next = n;
		prev = p;
	}

	public void setElement(Object newElem){
		element = newElem;
	}

	public void setNext(DLNode newNext){
		next = newNext; 
	}

	public void setPrev(DLNode newPrev){
		prev = newPrev;
	}

	public Object getElement(){
		return element; 
	}

	public DLNode getNext(){
		return next; 
	}

	public DLNode getPrev(){
		return prev; 
	}

	private Object element;
	private DLNode next, prev;	
}