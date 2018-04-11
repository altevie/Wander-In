package altevie.wanderin.utility;

import android.content.Context;
import android.graphics.Path;
import android.util.Log;
import android.widget.Toast;

import com.estimote.indoorsdk_module.cloud.LocationPosition;

import java.util.ArrayList;

import altevie.wanderin.utility.graphDec.AdjacencyListGraph;
import altevie.wanderin.utility.graphDec.Vertex;
import altevie.wanderin.utility.iterator.Iterator;

/**
 * Created by mvoccia on 30/03/2018.
 */

public class ClsGraph {

    public ClsGraph(){
        super();
    }

    public AdjacencyListGraph calcGraph(LocationPosition locationPosition, ArrayList<AdjacencyListGraph.DecVertex> listHashMapPon, ArrayList<AdjacencyListGraph.DecVertex> listHashMapPoi, AdjacencyListGraph graph, Context context){

        AdjacencyListGraph graph2 = new AdjacencyListGraph();
//definire destinazione

        double distance_min = Double.POSITIVE_INFINITY;
        double distance = 0;
        double x_s = locationPosition.getX();
        double y_s = locationPosition.getY();

        Log.i("Sorgente(x)", String.valueOf(x_s));
        Log.i("Sorgente(y)", String.valueOf(y_s));

/**************************************************************************************************************/
        if (x_s == 0.0 && y_s == 0.0) {

            java.util.Iterator<AdjacencyListGraph.DecVertex> ite = listHashMapPoi.iterator();
            while (ite.hasNext()){
                AdjacencyListGraph.DecVertex v = ite.next();
                if (((String)v.get("NOME")).equals("Ingresso")){
                    sorgente = (AdjacencyListGraph.DecVertex) v;
                    graph2 = graph;
                }
            }

            this.printGraph(graph2,context,"Grafo");

        }else {

/**************************************************************************************************************/
            AdjacencyListGraph g = new AdjacencyListGraph();
            AdjacencyListGraph.DecVertex v_min = g.new DecVertex("SORGENTE");

            java.util.Iterator<AdjacencyListGraph.DecVertex> it = listHashMapPon.iterator();

            while (it.hasNext()) {
                AdjacencyListGraph.DecVertex vertice = it.next();

                double x = Double.parseDouble((String) vertice.get("LAT"));
                double y = Double.parseDouble((String) vertice.get("LON"));
                distance = Math.hypot(x_s - x, y_s - y);

                if (distance < distance_min) {
                    v_min = vertice;
                    distance_min = distance;
                }
            }

            Log.i("Vertice (PON):", v_min.toString());
            Log.i("Distanza da sorgente:", "" + distance_min);

            graph2 = graph;
            this.printGraph(graph2,context,"Grafo");

            Vertex v = graph2.insertVertex("SORGENTE");
            sorgente = (AdjacencyListGraph.DecVertex) v;

            graph2.insertEdge(v, v_min, distance_min);
            this.printGraph(graph2,context,"Grafo_dopo_ins");
        }
        return graph2;
    }

    public AdjacencyListGraph calculateDijkstra(AdjacencyListGraph graph, Vertex sorgente, Vertex destinazione){
        D = new Dijkstra(graph, sorgente, destinazione);
        D.execute();
        Log.i("Grafo_Dijkstra_Tree",graph.toString());

        AdjacencyListGraph g = D.percorsoMinimo();

        return g;
    }

    public AdjacencyListGraph.DecVertex calculateDestination(AdjacencyListGraph graph2, ArrayList<AdjacencyListGraph.DecVertex> listHashMapPoi, int i){
        AdjacencyListGraph.DecVertex ver = listHashMapPoi.get(i);

        Log.i("Destinazione:",ver.toString());

        altevie.wanderin.utility.iterator.Iterator ite = graph2.vertices();
        while (ite.hasNext()) {
            AdjacencyListGraph.DecVertex decV = (AdjacencyListGraph.DecVertex) ite.next();
            if(ver.toString().substring(0,16).equals(decV.toString().substring(0,16))){
                decV.put("NOME",ver.get("NOME"));
                decV.put("LAT",ver.get("LAT"));
                decV.put("LON",ver.get("LON"));
                this.setDestinazione(decV);
            }
        }
        return this.destinazione;
    }

    public void printGraph(AdjacencyListGraph gr, Context context, String descr ){
        altevie.wanderin.utility.iterator.Iterator vv = gr.vertices();
        String ss= "", tot = "";
        while(vv.hasNext()){
            ss = vv.next().toString();
            tot = tot + ss  ;
            ss = "";
        }
        altevie.wanderin.utility.iterator.Iterator ee = gr.edges();
        String sse= "", tote = "";
        while(ee.hasNext()){
            sse = ee.next().toString();
            tote = tote + sse  ;
            sse = "";
        }
        //
        //Toast.makeText(context, "Grafo caricato correttamente - size:" + gr.numVertices(), Toast.LENGTH_LONG).show();
        Log.i(descr+"-vertici:",tot);
        Log.i(descr+"-archi:",tote);

    }

    public void drawGraph(AdjacencyListGraph gr, Context context, PathView pathView, ArrayList<AdjacencyListGraph.DecVertex> listHashMapPon){
        Iterator ee = gr.edges();
        java.util.Iterator<AdjacencyListGraph.DecVertex> pon = listHashMapPon.iterator();
        ArrayList<LocationPosition> lp = new ArrayList<LocationPosition>();

        float lat1 = 0, lon1 = 0, lat2 = 0, lon2 = 0;
        AdjacencyListGraph.DecEdge edge;

        Path path = new Path();

        while(ee.hasNext()) {
            pon = listHashMapPon.iterator();
            edge = (AdjacencyListGraph.DecEdge) ee.next();
            AdjacencyListGraph.MyVertex[] my_v = edge.endVertices();

            if(my_v[0].toString().substring(0, 16).equals(sorgente.toString().substring(0,16))){
                lat1 = (float)Double.parseDouble((String) sorgente.get("LAT"));
                lon1 = (float)Double.parseDouble((String) sorgente.get("LON"));

            }

            if(my_v[0].toString().substring(0, 16).equals(destinazione.toString().substring(0,16))){
                lat1 = (float)Double.parseDouble((String) destinazione.get("LAT"));
                lon1 = (float)Double.parseDouble((String) destinazione.get("LON"));

            }

            if(my_v[1].toString().substring(0, 16).equals(sorgente.toString().substring(0,16))){
                lat2 = (float)Double.parseDouble((String) sorgente.get("LAT"));
                lon2 = (float)Double.parseDouble((String) sorgente.get("LON"));

            }

            if(my_v[1].toString().substring(0, 16).equals(destinazione.toString().substring(0,16))){
                lat2 = (float)Double.parseDouble((String) destinazione.get("LAT"));
                lon2 = (float)Double.parseDouble((String) destinazione.get("LON"));

            }

                while (pon.hasNext()) {
                    AdjacencyListGraph.DecVertex pon_v = pon.next();

                    if (my_v[0].toString().substring(0, 16).equals(pon_v.toString().substring(0, 16))) {

                        lat1 = (float)Double.parseDouble((String) pon_v.get("LAT"));
                        lon1 = (float)Double.parseDouble((String) pon_v.get("LON"));
                    }
                    if (my_v[1].toString().substring(0, 16).equals(pon_v.toString().substring(0, 16))) {

                        lat2 = (float)Double.parseDouble((String) pon_v.get("LAT"));
                        lon2 = (float)Double.parseDouble((String) pon_v.get("LON"));
                    }
                }

            lp.add(new LocationPosition(lat1,lon1,245));//da ingresso
            lp.add(new LocationPosition(lat2,lon2,245));// a ufficio Nicola

            lat1 = 0; lat2 = 0; lon1 = 0; lon2 = 0;
        }

        pathView.setPath(lp);

    }

    public void calcPath(LocationPosition locPos, ArrayList<AdjacencyListGraph.DecVertex> listHashMapPon, AdjacencyListGraph graph, ArrayList<AdjacencyListGraph.DecVertex> listHashMapPoi, int i, Context context, PathView pathView){
        //calculate the distance between my_position and PON
        //add my_position to graph and the edge (min_distance)
        AdjacencyListGraph graph2 = this.calcGraph(locPos, listHashMapPon, listHashMapPoi, graph,context);

        //calculate Dikstra
        AdjacencyListGraph gr = this.calculateDijkstra(graph2, this.getSorgente(), this.calculateDestination(graph2,listHashMapPoi,i));
        this.printGraph(gr,context,"Grafo_Dijkstra");

        /****************************************************/
        this.drawGraph(gr,context, pathView, listHashMapPon);
        /****************************************************/
    }

    public AdjacencyListGraph.DecVertex getSorgente(){
        return this.sorgente;
    }

    public AdjacencyListGraph.DecVertex getDestinazione(){
        return this.destinazione;
    }

    public void setDestinazione(Vertex destinazione){
        this.destinazione = (AdjacencyListGraph.DecVertex)destinazione;
        Log.i("DESTINAZIONE:", destinazione.toString());

    }

    private Dijkstra D;
    private AdjacencyListGraph.DecVertex sorgente;
    private AdjacencyListGraph.DecVertex destinazione;
}
