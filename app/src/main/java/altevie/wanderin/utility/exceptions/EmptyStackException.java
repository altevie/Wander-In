package altevie.wanderin.utility.exceptions;

public class EmptyStackException extends RuntimeException {

	/**
	 * classe EmptyStackException 
	 * eccezione che indica quando lo queue � vuoto
	 */

	public EmptyStackException(String string) {
		super(string);
	}

	private static final long serialVersionUID = -7846665341191841437L;

}
