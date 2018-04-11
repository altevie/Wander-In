package altevie.wanderin.utility;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.estimote.indoorsdk_module.cloud.LocationPosition;
import com.estimote.indoorsdk_module.view.IndoorLocationView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import altevie.wanderin.R;
import altevie.wanderin.utility.graphDec.AdjacencyListGraph;
import altevie.wanderin.utility.graphDec.Vertex;

/**
 * Created by PiervincenzoAstolfi on 04/03/2018.
 */

public class ClsGetJson {
    static JSONObject jObj = null;
    static String strjson = "";
    private JSONArray jArray;
    private JSONArray jArray_pon;
    private JSONArray jArray_graph;
    private AdjacencyListGraph graph;
    public void getJSONFromUrl(final Context context, String url, String url_pon, String url_graph, RequestQueue queue, final ArrayList<HashMap<String, String>> listHashMap, final ArrayList<AdjacencyListGraph.DecVertex> listHashMapPoi, final ArrayList<AdjacencyListGraph.DecVertex>  listHashMapPon, final AdjacencyListGraph graph, final BaseAdapter mAdapter, final IndoorLocationView indoorLocationView, final Button update){

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    jArray = response.getJSONObject("d").getJSONArray("results");
                    ArrayList<LocationPosition> arrayList = new ArrayList<LocationPosition>();
                    for(int i = 0; i < jArray.length(); i++){
                        JSONObject json = jArray.getJSONObject(i);
                        String id_poi = json.getString("ID_POI");
                        String luogo = json.getString("NOME");
                        String lat = json.getString("LAT");
                        String lon = json.getString("LON");
                        String data = json.getString("DATA");
                        String data_type = json.getString("DATA_TYPE");

                        HashMap<String,String> map = new HashMap<String,String>();
                        map.put("NOME", luogo);
                        map.put("LAT", lat);
                        map.put("LON", lon);
                        map.put("DATA", data);
                        map.put("DATA_TYPE", data_type);
                        listHashMap.add(map);
                        //double x = jArray.getJSONObject(i).getDouble("LAT");
                        //double y = jArray.getJSONObject(i).getDouble("LON");
                        arrayList.add(new LocationPosition(jArray.getJSONObject(i).getDouble("LAT"),jArray.getJSONObject(i).getDouble("LON"),245.00));
                        AdjacencyListGraph g = new AdjacencyListGraph();
                        AdjacencyListGraph.DecVertex v = g.new DecVertex(id_poi);
                        v.put("NOME",luogo);
                        v.put("LAT",lat);
                        v.put("LON",lon);
                        listHashMapPoi.add(v);

                    }
                    indoorLocationView.setCustomPoints(arrayList);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(context, "Lista caricata correttamente", Toast.LENGTH_LONG).show();
                    update.setVisibility(View.INVISIBLE);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con la sincronizzazione dei POI", Toast.LENGTH_LONG).show();
                update.setVisibility(View.VISIBLE);
            }
        });
        queue.add(jor);
        JsonObjectRequest j_pon = new JsonObjectRequest(Request.Method.GET, url_pon, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    jArray_pon = response.getJSONObject("d").getJSONArray("results");
                    AdjacencyListGraph g = new AdjacencyListGraph();

                    for(int i = 0; i < jArray_pon.length(); i++){
                        JSONObject json = jArray_pon.getJSONObject(i);
                        String x = json.getString("LAT");
                        String y = json.getString("LON");
                        String id_pon = json.getString("ID_PON");

                        AdjacencyListGraph.DecVertex v = g.new DecVertex(id_pon);
                        v.put("LAT",x);
                        v.put("LON",y);
                        listHashMapPon.add(v);

                    }}catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con la sincronizzazione dei PON", Toast.LENGTH_LONG).show();

            }
        });
        queue.add(j_pon);
        JsonObjectRequest j_graph = new JsonObjectRequest(Request.Method.GET, url_graph, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    jArray_graph = response.getJSONObject("d").getJSONArray("results");

                    for(int i = 0; i < jArray_graph.length(); i++){
                        JSONObject json = jArray_graph.getJSONObject(i);
                        String from = json.getString("FROM");
                        String to = json.getString("TO");
                        String distance = json.getString("DISTANCE");

                        altevie.wanderin.utility.iterator.Iterator vv = graph.vertices();
                        Vertex v = new Vertex() {
                            @Override
                            public Object element() {
                                return null;
                            }
                        };
                        Vertex u = new Vertex() {
                            @Override
                            public Object element() {
                                return null;
                            }
                        };
                        int flag_v = 0, flag_u = 0;

                        while (vv.hasNext()){
                            Vertex v_it = (Vertex)vv.next();
                            if(v_it.toString().substring(0,16).equals(from.toString())){
                                v = v_it;
                                flag_v = 1;
                            }
                            if(v_it.toString().substring(0,16).equals(to.toString())){
                                u = v_it;
                                flag_u = 1;
                            }
                        }
                        if (flag_v == 0){
                            v = graph.insertVertex(from);
                        }else {flag_v = 0;}

                        if (flag_u == 0){
                            u = graph.insertVertex(to);
                        }else{flag_u = 0;}

                        graph.insertEdge(v,u, distance);

                    }}catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con la sincronizzazione del Grafo", Toast.LENGTH_LONG).show();
                //update.setVisibility(View.VISIBLE);
            }
        });
        queue.add(j_graph);
        queue.start();
    }

    public void nearPosition(final Context context,LocationPosition locationPosition){
        try {
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                Double lat = json.getDouble("LAT");
                Double lon = json.getDouble("LON");
                if(Math.sqrt(Math.pow(lat - locationPosition.getX(), 2) + Math.pow(lon - locationPosition.getY(), 2))<= 2){
                    String info = json.getString("DATA");
                    //Toast.makeText(context, info, Toast.LENGTH_LONG).show();
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
