
package com1032.cw2.mm01632.mm01632cw2com1032;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by marne on 16/05/2018.
 * A thread is created in a Service to get the location.
 */

public class LocationService extends Service{

    public LocationService(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * A new thread is created inside the service.
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      Thread thread = new Thread(new LocationThread(getApplicationContext()));
      thread.run();
      return Service.START_STICKY;
    }

     class LocationThread implements Runnable{

        Context appContext;
        Intent intent;

        public LocationThread(Context c) {
            appContext = c;
            intent = new Intent("location");
        }

         /**
          * Inside the run method a new location manager and listener is created,
          * As soon as the location is changed the new lat and lng is retrieved.
          */
        @SuppressLint("MissingPermission")
        @Override
        public void run() {
                LocationManager locationManager = (LocationManager) appContext.getSystemService(LOCATION_SERVICE);
                LocationListener listener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.i("changed", "I am here");
                        Log.i("longitude", location.getLongitude() + "l");

                        double lat = (double) (location.getLatitude());
                        double lng = (double) (location.getLongitude());

                        intent.putExtra("lat",lat);
                        intent.putExtra("lng", lng);
                        Log.i("LOCATION SEND", String.valueOf(lat));
                        sendBroadcast(intent);
                        Log.i("SEND IS", String.valueOf(intent.getExtras().getDouble("lat")));


                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setPowerRequirement(Criteria.POWER_HIGH);
                String provider = locationManager.getBestProvider(criteria, false);
                locationManager.requestLocationUpdates(provider, 1000, 10, listener);
        }
    }
}
