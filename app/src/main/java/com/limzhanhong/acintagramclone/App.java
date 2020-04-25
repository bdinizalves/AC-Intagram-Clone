package com.limzhanhong.acintagramclone;

import android.app.Application;
import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("se9QEIjymy5e2xrFPdQflzT2EIhlixjZEACLDQtz")
                // if defined
                .clientKey("HAlZjXKChcO3yKFnTUlDYdE7HvvClv1ktmdaO8aw")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
