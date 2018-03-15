package altevie.wanderin.utility;

import android.app.Application;

import com.estimote.indoorsdk_module.cloud.Location;

/**
 * Created by PiervincenzoAstolfi on 14/03/2018.
 */

public class GlobalObject extends Application {
    private Location location;
    public Location getLocation(){
        return this.location;
    }
    public void setLocation(Location location){
        this.location = location;
    }
}
