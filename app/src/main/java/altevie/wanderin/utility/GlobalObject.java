package altevie.wanderin.utility;

import android.app.Application;

import com.estimote.indoorsdk_module.cloud.Location;

/**
 * Created by PiervincenzoAstolfi on 14/03/2018.
 */

public class GlobalObject extends Application {
    private Location location;
    private static GlobalObject instance;
    public Location getLocation(){
        return this.location;
    }
    public void setLocation(Location location){
        this.location = location;
    }
    public static synchronized GlobalObject getInstance(){
        if(instance==null){
            instance=new GlobalObject();
        }
        return instance;
    }
}
