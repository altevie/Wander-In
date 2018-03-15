package altevie.wanderin.utility;

import android.app.Application;

import com.estimote.indoorsdk_module.cloud.Location;
import com.estimote.internal_plugins_api.cloud.CloudCredentials;

/**
 * Created by PiervincenzoAstolfi on 14/03/2018.
 */

public class GlobalObject extends Application {
    private Location location;
    private CloudCredentials cloudCredentials;
    public Location getLocation(){
        return this.location;
    }
    public void setLocation(Location location){
        this.location = location;
    }

    public CloudCredentials getCloudCredentials() {
        return this.cloudCredentials;
    }

    public void setCloudCredentials(CloudCredentials cloudCredentials) {
        this.cloudCredentials = cloudCredentials;
    }
}
