package altevie.wanderin.utility.position;

public interface BTPosition extends Position {
	// Metodi di accesso
	public BTPosition getLeft() ;
	public BTPosition getRight() ;
	public BTPosition getParent();
	// Metodi di aggiornamento
	public void setElement(Object newElem);
	public void setLeft(BTPosition newLeft);
	public void setRight(BTPosition newRight);
	public void setParent(BTPosition newParent) ;
	}