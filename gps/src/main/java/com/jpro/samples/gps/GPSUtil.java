package com.jpro.samples.gps;

import com.jpro.webapi.WebAPI;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.function.Consumer;

public class GPSUtil {
    WebAPI webAPI;

    GPSUtil(WebAPI webAPI) {
        this.webAPI = webAPI;
    }

    public void requestData(Consumer<GPSData> f) {
        webAPI.registerJavaFunction("sendGPS", str -> {
            System.out.println("got: " + str);
            JSONObject tocken = new JSONObject(str);
            if(tocken.isEmpty()) {
                f.accept(null);
            } else {
                GPSData result = new GPSData();
                result.longitude = tocken.getDouble("longitude");
                result.latitude = tocken.getDouble("latitude");
                f.accept(result);
            }
        });
        webAPI.executeScript("navigator.geolocation.getCurrentPosition(" +
                "function(v) {jpro.sendGPS({latitude: v.coords.latitude, longitude: v.coords.longitude});}, " +
                "function(v) {jpro.sendGPS({});}" +
                ")");

    }

    class GPSData {
        double longitude;
        double latitude;
    }

}
