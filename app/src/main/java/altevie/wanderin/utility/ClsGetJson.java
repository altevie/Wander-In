package altevie.wanderin.utility;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;
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

/**
 * Created by PiervincenzoAstolfi on 04/03/2018.
 */

public class ClsGetJson {
    static JSONObject jObj = null;
    static String strjson = "";
    private JSONArray jArray;
    public void getJSONFromUrl(final Context context, String url, RequestQueue queue, final ArrayList<HashMap<String, String>> listHashMap, final BaseAdapter mAdapter){

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    jArray = response.getJSONObject("d").getJSONArray("results");
                    for(int i = 0; i < jArray.length(); i++){
                        JSONObject json = jArray.getJSONObject(i);
                        String luogo = json.getString("NOME");
                        HashMap<String,String> map = new HashMap<String,String>();
                        map.put("NOME", luogo);
                        listHashMap.add(map);
                    }
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(context, "Lista caricata correttamente", Toast.LENGTH_LONG).show();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con la sincronizzazione dei POI", Toast.LENGTH_LONG).show();
            }
        });
        queue.start();
        queue.add(jor);

    }
}
