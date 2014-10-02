package com.taxi123.taxi123android;

import android.app.Activity;

public class Configurations {
    public static Activity MainActivity = null;

    public static double EARTH_RADIUS = 6371;

    public static String BOOLEAN_TRUE = "1";
    public static String BOOLEAN_FALSE = "0";

    public static String SERVER_LINKQUERY_ALL = "http://taxi.seannguyentesting.net/queryAllLocations.php";
    public static String SERVER_LINKQUERY_IDSTATUS = "http://taxi.seannguyentesting.net/queryIdStatus.php";

    public static String GGLAPI_LINKQUERY_POSITION = "http://maps.googleapis.com/maps/api/geocode/json?address=";
    public static String GGLAPI_STATUS = "status";
    public static String GGLAPI_STATUS_OK = "OK";
    public static String GGLAPI_RESULT = "results";
    public static String GGLAPI_GEOMETRY = "geometry";
    public static String GGLAPI_LOCATION = "location";
    public static String GGLAPI_LATITUDE = "lat";
    public static String GGLAPI_LONGITUDE = "lng";

    public static String DATABASE_JSONRESPONSE_LOCATIONS = "locations";
    public static String DATABASE_JSONRESPONSE_SUCCESS = "success";
    public static String DATABASE_JSONRESPONSE_LOCATIONID = "memberID";
    public static String DATABASE_JSONRESPONSE_LOCATIONNAME = "locationName";
    public static String DATABASE_JSONRESPONSE_ADDRESS = "address";
    public static String DATABASE_JSONRESPONSE_POSTALCODE = "postalCode";
    public static String DATABASE_JSONRESPONSE_LOCATIONSTATUS = "status";

    public static String COLOR_INDEMAND = "#7fff00";
    public static String COLOR_NOTINDEMAND = "#696969";
    public static String COLOR_BLACK = "#000000";

    public static String MESSAGE_LOADING = "Loading";


}
