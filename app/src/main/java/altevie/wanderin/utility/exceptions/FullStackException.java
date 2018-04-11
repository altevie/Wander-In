package altevie.wanderin.utility.exceptions;

public class FullStackException extends RuntimeException {

	/**
	 * classe FullStackException 
	 * eccezione che indica quando lo queue � vuoto
	 */
	
	public FullStackException(String string) {
		super(string);
	}
	
	private static final long serialVersionUID = -3612257005370216332L;

}
