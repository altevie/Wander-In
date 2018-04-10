package altevie.wanderin;

import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.estimote.indoorsdk.IndoorLocationManagerBuilder;
import com.estimote.indoorsdk_module.algorithm.OnPositionUpdateListener;
import com.estimote.indoorsdk_module.algorithm.ScanningIndoorLocationManager;
import com.estimote.indoorsdk_module.cloud.Location;
import com.estimote.indoorsdk_module.cloud.LocationPosition;
import com.estimote.indoorsdk_module.view.IndoorLocationView;
import com.estimote.internal_plugins_api.cloud.CloudCredentials;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import altevie.wanderin.utility.ClsGetJson;
import altevie.wanderin.utility.GlobalObject;
import altevie.wanderin.utility.PathView;
import altevie.wanderin.utility.SyncResult;

import static altevie.wanderin.R.*;

public class MainActivity extends AppCompatActivity {

    protected ArrayList<HashMap<String,String>> listHashMap;
    private ClsGetJson getJson;
    private JSONArray jArray;
    private JSONObject jobj;
    private ListView listView;
    private BaseAdapter mAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    public RequestQueue queue;
    protected Location loc;
    protected IndoorLocationView indoorLocationView;
    protected final SyncResult syncResult = new SyncResult();
    protected Context context;
    ScanningIndoorLocationManager indoorLocationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawable.ic_menu);
        final PathView pathView = (PathView)findViewById(id.path_view);
        getJson = new ClsGetJson();
        listHashMap = new ArrayList<HashMap<String, String>>();
        listView = (ListView)findViewById(R.id.POIList);
        queue = Volley.newRequestQueue(this);
        context = this;
        indoorLocationView = (IndoorLocationView) findViewById(R.id.indoor_view);
        GlobalObject g = (GlobalObject)getApplication();
        loc = g.getLocation();

        String[] from = new String[] {"NOME"};
        int[] to = new int[]  {id.textView};
        mAdapter = new SimpleAdapter(this, listHashMap, layout.line_style, from, to);
        listView.setAdapter(mAdapter);

        final Button update = (Button)findViewById(id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJson.getJSONFromUrl(context,getString(string.url), queue, listHashMap, mAdapter, indoorLocationView, update);
            }
        });
        getJson.getJSONFromUrl(this,getString(string.url), queue, listHashMap, mAdapter, indoorLocationView, update);

        mDrawerLayout = findViewById(id.drawer_layout);
        mActivityTitle = getTitle().toString();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, string.drawer_open, string.drawer_close){
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            // Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mDrawerLayout.closeDrawers();
                HashMap item = listHashMap.get(i);
                createDialog(context, item);
                ArrayList<LocationPosition> lp = new ArrayList<LocationPosition>();
                lp.add(new LocationPosition(3.87,6.86,245)); //da ingresso
                lp.add(new LocationPosition(1.65,11.72,245));// a ufficio Nicola
                lp.add(new LocationPosition(1.65,11.72,245));// da ufficio Nicola
                lp.add(new LocationPosition(1.29,5.12,245));// a ufficio Piciucchi
                pathView.setPath(lp);
                /*String lat = item.get("LAT").toString();
                String lon = item.get("LON").toString();
                Toast.makeText(MainActivity.this, lat+lon, Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawers();*/
            }
        });

        CloudCredentials cloudCredentials = g.getCloudCredentials();
        indoorLocationView.setLocation(loc);

        Notification.Builder notification = new Notification.Builder(this);
        notification.setSmallIcon(R.drawable.beacon_grey_small);
        notification.setContentTitle("Wander-In");
        notification.setContentText("Indoor location in esecuzione...");
        notification.build();

        Notification not = notification.getNotification();

        indoorLocationManager = new IndoorLocationManagerBuilder(this, loc, cloudCredentials)
                .withScannerInForegroundService(not)
                .build();
        indoorLocationManager.setOnPositionUpdateListener(new OnPositionUpdateListener() {
            @Override
            public void onPositionUpdate(LocationPosition locationPosition) {
                indoorLocationView.updatePosition(locationPosition);
                getJson.nearPosition(context, locationPosition);
            }

            @Override
            public void onPositionOutsideLocation() {
                indoorLocationView.hidePosition();
            }
        });
        indoorLocationManager.startPositioning();
    }

    @Override
    protected void onStart() {
        super.onStart();
        indoorLocationManager.startPositioning();
    }

    @Override
    protected void onStop() {
        super.onStop();
        indoorLocationManager.stopPositioning();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void createDialog(Context context, HashMap item){
        final Dialog d = new Dialog(context);
        d.setTitle(item.get("NOME").toString());
        d.setCancelable(false);
        switch(item.get("DATA_TYPE").toString()){
            case "MESSAGE":
                d.setContentView(layout.message);
                TextView tv = (TextView) d.findViewById(id.textView2);
                tv.setText(item.get("DATA").toString());
                Button okMessage = (Button)d.findViewById(id.messageok);
                okMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.cancel();
                    }
                });
                break;
            case "URL":
                d.setContentView(layout.url);
                TextView tvUrl = (TextView) d.findViewById(id.textView3);
                if(!item.get("DATA").toString().contains("http://") || !item.get("DATA").toString().contains("https://")){
                    tvUrl.setText("http://" + item.get("DATA").toString());
                }else {
                    tvUrl.setText(item.get("DATA").toString());
                }
                Button okUrl = (Button)d.findViewById(id.urlok);
                okUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.cancel();
                    }
                });

                Button apriUrl = (Button)d.findViewById(id.urlapri);
                apriUrl.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view) {
                        d.cancel();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((TextView)d.findViewById(id.textView3)).getText().toString()));
                        startActivity(browserIntent);
                    }
                });
                break;
        }
        d.show();
    }
}
