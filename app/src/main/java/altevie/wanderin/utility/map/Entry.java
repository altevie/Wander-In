package altevie.wanderin.utility.map;

import altevie.wanderin.utility.position.Position;

public interface Entry {
	public Object key();
	public Object element();
	public Position loc();
}
