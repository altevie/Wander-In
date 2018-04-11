package altevie.wanderin.utility;

/**
 * Created by mvoccia on 22/03/2018.
 */

import altevie.wanderin.utility.graphDec.AdjacencyListGraph;
import altevie.wanderin.utility.graphDec.AdjacencyListGraph.DecEdge;
import altevie.wanderin.utility.graphDec.AdjacencyListGraph.DecVertex;
import altevie.wanderin.utility.graphDec.Vertex;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.priorityQueue.Entry;
import altevie.wanderin.utility.priorityQueue.SortedListPriorityQueue;
import altevie.wanderin.utility.set.SortedListSet;

public class Dijkstra
{
    public Dijkstra(AdjacencyListGraph graph, Vertex sorgente)
    {
        this.graph = graph;
        this.sorgente = (DecVertex) sorgente;
        Q = new SortedListPriorityQueue();
    }

    public Dijkstra(AdjacencyListGraph graph, Vertex sorgente, Vertex destinazione)
    {
        this.graph = graph;
        this.sorgente = (DecVertex) sorgente;
        this.destinazione = (DecVertex) destinazione;
        Q = new SortedListPriorityQueue();
    }
    private void init()
    {
        this.residualNodes = new SortedListSet(); // S = vuoto

        Iterator it = graph.vertices();
        while(it.hasNext())
        {
            DecVertex v =(DecVertex)it.next();
            graph.putDecoration(v,"KEY", INFINITO);
            graph.putDecoration(v,"PREDECESSORE", null);
        }
        graph.putDecoration(sorgente, "KEY", 0);

        it = graph.vertices();
        while(it.hasNext())
        {
            DecVertex v =(DecVertex)it.next();
            Q.insert(graph.getDecoration(v, "KEY"), v);
        }
    }

    public void execute()
    {
        this.init();

        //coda
        while(!Q.isEmpty())
        {
            Entry entry = Q.removeMin();
            Vertex u = (Vertex) entry.element();

            residualNodes.insertElement(u.element());

            Iterator incidentEdgesIt = graph.incidentEdges(u);
            while(incidentEdgesIt.hasNext())
            {
                DecEdge e = (DecEdge) incidentEdgesIt.next();
                DecVertex vertex =(DecVertex) graph.opposite(u, e);
                this.relax(u,vertex,e.element());
            }
        }
    }

    private void relax(Vertex u, Vertex v, Object e)
    {

        double dU = (Double.parseDouble(graph.getDecoration(u,"KEY").toString()));
        double w = Double.parseDouble(e.toString());
        double dV = (Double.parseDouble(graph.getDecoration(v,"KEY").toString()));

        if(dU + w < dV)
        {
            Q.replaceKey(Q.checkEntry(v,graph.getDecoration(v, "KEY")), dU + w);
            graph.putDecoration(v, "KEY", dU + w);
            graph.putDecoration(v, "PREDECESSORE", u.element());
        }
    }


    public AdjacencyListGraph percorsoMinimo() {
        DecVertex v;
        v = destinazione;
        g = new AdjacencyListGraph();

        String uStr = (String) graph.getDecoration(v, "PREDECESSORE");

        Vertex uu,vv;
        vv = g.insertVertex(v.element());
        g.putDecoration(v, "KEY",(Double)graph.getDecoration(v, "KEY"));
        g.putDecoration(v, "PREDECESSORE", graph.getDecoration(v, "PREDECESSORE"));

        while(uStr != null){

            Iterator it2 = v.incidentEdges();
            String w = "not found";

            while(it2.hasNext())
            {
                DecEdge e = (DecEdge) it2.next();
                Vertex u;
                u = graph.opposite(v, e);

                if(u.element().equals(uStr)) {
                    w = e.element().toString();
                    uu = g.insertVertex(uStr);
                    g.insertEdge(vv, uu, w);

                    g.putDecoration(uu, "KEY", graph.getDecoration(u, "KEY"));
                    g.putDecoration(uu, "PREDECESSORE", graph.getDecoration(u, "PREDECESSORE"));

                    uStr = (String) graph.getDecoration((DecVertex)u, "PREDECESSORE");
                    v = (DecVertex)u;
                    vv = uu;
                    break;
                }

            }

        }
        return g;
    }
    private SortedListSet residualNodes;

    private final static double INFINITO = (double) Integer.MAX_VALUE;
    private DecVertex sorgente;
    private DecVertex destinazione;
    private SortedListPriorityQueue Q;
    private AdjacencyListGraph graph;
    private AdjacencyListGraph g;

}
