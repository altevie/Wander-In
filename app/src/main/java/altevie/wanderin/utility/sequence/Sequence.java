package altevie.wanderin.utility.sequence;

import altevie.wanderin.utility.exceptions.BoundaryViolationException;
import altevie.wanderin.utility.exceptions.InvalidPositionException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.list.List;
import altevie.wanderin.utility.position.Position;
import altevie.wanderin.utility.vector.Vector;

public interface Sequence extends List, Vector {
	// Metodi ponte (aggiuntivi)
	public Position atRank(int rank) throws BoundaryViolationException;
	public int rankOf(Position p) throws InvalidPositionException;
	public Iterator elements();
	public Iterator positions();
}
