package altevie.wanderin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.estimote.cloud_plugin.common.EstimoteCloudCredentials;
import com.estimote.indoorsdk.IndoorLocationManagerBuilder;
import com.estimote.indoorsdk_module.algorithm.OnPositionUpdateListener;
import com.estimote.indoorsdk_module.algorithm.ScanningIndoorLocationManager;
import com.estimote.indoorsdk_module.cloud.CloudCallback;
import com.estimote.indoorsdk_module.cloud.EstimoteCloudException;
import com.estimote.indoorsdk_module.cloud.IndoorCloudManager;
import com.estimote.indoorsdk_module.cloud.IndoorCloudManagerFactory;
import com.estimote.indoorsdk_module.cloud.Location;
import com.estimote.indoorsdk_module.cloud.LocationPosition;
import com.estimote.internal_plugins_api.cloud.CloudCredentials;

import altevie.wanderin.utility.GlobalObject;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        final CloudCredentials cloudCredentials = new EstimoteCloudCredentials(getString(R.string.app_id),getString(R.string.app_token));
        IndoorCloudManager cloudManager = new IndoorCloudManagerFactory().create(this, cloudCredentials);
        final GlobalObject g = (GlobalObject)getApplication();
        g.setCloudCredentials(cloudCredentials);
        cloudManager.getLocation(getString(R.string.loc_id), new CloudCallback<Location>() {
                    @Override
                    public void success(Location location) {
                        g.setLocation(location);
                        startMainActivity();

                    }

                    @Override
                    public void failure(EstimoteCloudException e) {

                    }
                }
        );
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
