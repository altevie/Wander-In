package altevie.wanderin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.estimote.cloud_plugin.common.EstimoteCloudCredentials;
import com.estimote.indoorsdk.IndoorLocationManagerBuilder;
import com.estimote.indoorsdk_module.algorithm.IndoorLocationManager;
import com.estimote.indoorsdk_module.algorithm.OnPositionUpdateListener;
import com.estimote.indoorsdk_module.algorithm.ScanningIndoorLocationManager;
import com.estimote.indoorsdk_module.cloud.CloudCallback;
import com.estimote.indoorsdk_module.cloud.EstimoteCloudException;
import com.estimote.indoorsdk_module.cloud.IndoorCloudManager;
import com.estimote.indoorsdk_module.cloud.IndoorCloudManagerFactory;
import com.estimote.indoorsdk_module.cloud.Location;
import com.estimote.indoorsdk_module.cloud.LocationPosition;
import com.estimote.indoorsdk_module.common.helpers.EstimoteIndoorHelper;
import com.estimote.indoorsdk_module.view.IndoorLocationView;
import com.estimote.internal_plugins_api.cloud.CloudCredentials;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import altevie.wanderin.utility.ClsGetJson;
import altevie.wanderin.utility.GlobalObject;
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

        getJson = new ClsGetJson();
        listHashMap = new ArrayList<HashMap<String, String>>();
        listView = (ListView)findViewById(id.POIList);
        queue = Volley.newRequestQueue(this);
        context = this;
        indoorLocationView = (IndoorLocationView) findViewById(R.id.indoor_view);
        GlobalObject g = (GlobalObject)getApplication();
        loc = g.getLocation();

        String[] from = new String[] {"NOME"};
        int[] to = new int[]  {id.textView};
        mAdapter = new SimpleAdapter(this, listHashMap, layout.line_style, from, to);
        listView.setAdapter(mAdapter);
        getJson.getJSONFromUrl(this,getString(string.url), queue, listHashMap, mAdapter);

        EstimoteIndoorHelper estimoteIndoorHelper = new EstimoteIndoorHelper();

        mDrawerLayout = findViewById(id.drawer_layout);
        mActivityTitle = getTitle().toString();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, string.drawer_open, string.drawer_close){
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Hai cliccato", Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawers();
            }
        });

        CloudCredentials cloudCredentials = g.getCloudCredentials();
        indoorLocationView.setLocation(loc);
        indoorLocationManager = new IndoorLocationManagerBuilder(this, loc, cloudCredentials)
                .withDefaultScanner()
                .build();
        indoorLocationManager.setOnPositionUpdateListener(new OnPositionUpdateListener() {
            @Override
            public void onPositionUpdate(LocationPosition locationPosition) {
                indoorLocationView.updatePosition(locationPosition);
            }

            @Override
            public void onPositionOutsideLocation() {
                indoorLocationView.hidePosition();
            }
        });
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
}
