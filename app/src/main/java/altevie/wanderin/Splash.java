package altevie.wanderin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import altevie.wanderin.utility.GlobalObject;

public class Splash extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private IndoorCloudManager cloudManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        final CloudCredentials cloudCredentials = new EstimoteCloudCredentials(getString(R.string.app_id),getString(R.string.app_token));
        cloudManager = new IndoorCloudManagerFactory().create(this, cloudCredentials);
        final GlobalObject g = (GlobalObject)getApplication();
        g.setCloudCredentials(cloudCredentials);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
        if(loggedIn){
            loginButton.setVisibility(View.INVISIBLE);
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
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
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

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });


    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
