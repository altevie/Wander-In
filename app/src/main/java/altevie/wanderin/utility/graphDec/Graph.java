package altevie.wanderin.utility.graphDec;

import altevie.wanderin.utility.exceptions.InvalidPositionException;
import altevie.wanderin.utility.iterator.Iterator;

public interface Graph {
	public int numVertices();
	public int numEdges();
	public Iterator vertices();
	public Iterator edges();
	public Iterator incidentEdges(Vertex v) throws InvalidPositionException;
	public Object replace(Vertex p, Object o) throws InvalidPositionException;
	public Object replace(Edge p, Object o) throws InvalidPositionException;
	public Vertex[] endVertices(Edge e) throws InvalidPositionException;
	public Vertex opposite(Vertex v, Edge e) throws	InvalidPositionException;
	public boolean areAdjacent(Vertex u, Vertex v) throws InvalidPositionException;
	public Vertex insertVertex(Object o);
	public Edge insertEdge(Vertex u, Vertex v, Object o) throws	InvalidPositionException;
	public Object removeVertex(Vertex v) throws	InvalidPositionException;
	public Object removeEdge(Edge e) throws	InvalidPositionException;
}
