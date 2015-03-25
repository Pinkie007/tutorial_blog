package com.tutomap;


import com.google.android.gms.maps.model.LatLng;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

/**
 * Created by guillaumeagis on 24/03/15.
 */
public class GeolocationHelper {

    private static final String TAG = "GeolocationHelper";
    private static final String GOOGLEAPIKEY = "YOUR_GOOGLE_API_KEY";


   public static LatLng getCoordonate(String fullAddr)
    {
        LatLng result = null;
        GeoApiContext context = new GeoApiContext().setApiKey(GOOGLEAPIKEY);
        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = GeocodingApi.geocode(context, fullAddr).await();
            if (results == null)
                return null;
            double lat = results[0].geometry.location.lat;
            double lng = results[0].geometry.location.lng;
            result = new LatLng(lat, lng);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Compute the distance between 2 coordinates
     * @param lat1 latitude 1st point
     * @param lon1 longitude 1st point
     * @param lat2 latitude 2nd point
     * @param lon2 longitude 2nd point
     * @return distance, in meter
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    public static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     *  Give the duration , in minute, according to a distance in miles
     *  Average speed considered : 5.5km/h
     * @param miles distance, in miles
     * @return duration, in minute
     */
    public static double distanceByWalkMiles(double miles)
    {
        return miles * 15;
    }


    /**
     * Give the duration, in minute , according to a distance in meter
     *  Average speed considered : 5.5km/h
     * @param meter distance, in meter
     * @return duration, in minute
     */
    public static double distanceByWalkMeter(int meter)
    {
        return (60 * (double)meter) / 5500; // conv to min
    }

}
