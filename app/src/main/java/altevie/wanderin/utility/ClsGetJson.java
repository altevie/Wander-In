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

/**
 * Created by PiervincenzoAstolfi on 04/03/2018.
 */

public class ClsGetJson {
    static JSONObject jObj = null;
    static String strjson = "";
    private JSONArray jArray;
    public void getJSONFromUrl(final Context context, String url, RequestQueue queue, final ArrayList<HashMap<String, String>> listHashMap, final BaseAdapter mAdapter, final IndoorLocationView indoorLocationView, final Button update){

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    jArray = response.getJSONObject("d").getJSONArray("results");
                    ArrayList<LocationPosition> arrayList = new ArrayList<LocationPosition>();
                    for(int i = 0; i < jArray.length(); i++){
                        JSONObject json = jArray.getJSONObject(i);
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
        queue.start();
        queue.add(jor);
    }

    public void nearPosition(final Context context,LocationPosition locationPosition){
        try {
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                Double lat = json.getDouble("LAT");
                Double lon = json.getDouble("LON");
                if(Math.sqrt(Math.pow(lat - locationPosition.getX(), 2) + Math.pow(lon - locationPosition.getY(), 2))<= 2){
                    String info = json.getString("DATA");
                    Toast.makeText(context, info, Toast.LENGTH_LONG).show();
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
