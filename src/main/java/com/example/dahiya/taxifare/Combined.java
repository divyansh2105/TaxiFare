package com.example.dahiya.taxifare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import com.lyft.lyftbutton.LyftStyle;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapquest.mapping.MapQuest;
import com.uber.sdk.android.core.UberButton;
import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestButtonCallback;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.rides.client.SessionConfiguration;
import com.uber.sdk.android.rides.RideRequestButton;
import com.uber.sdk.rides.client.ServerTokenSession;
import com.uber.sdk.rides.client.error.ApiError;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.lyft.lyftbutton.LyftButton;
import com.lyft.lyftbutton.RideParams;
import com.lyft.lyftbutton.RideTypeEnum;
import com.lyft.networking.ApiConfig;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import com.mapquest.mapping.MapQuest;
import com.mapquest.mapping.maps.MapView;
//import com.mapbox.mapboxsdk.geometry.LatLng;

public class Combined extends AppCompatActivity
        //implements RoutingListener
{



    String from_string,to_string,passengers_string;
    com.google.android.gms.maps.model.LatLng start_location,end_location;
    double start_latitude ;
    double end_latitude ;
    double start_longitude ;
    double end_longitude ;
    CardView card1,card2;

//    private GoogleMap mMap;
    private static final int[] COLORS = new int[]{R.color.primary_dark_material_light};

    UberButton uberButton;

    private MapView mMapView;
    private MapboxMap mMapboxMap;


    protected void onCreate(Bundle bundle) {
        try {


//            Log.d("errr","sa"+start_latitude+"so"+start_longitude+"ea"+end_latitude+"eo"+end_longitude);
            super.onCreate(bundle);
            setContentView(R.layout.combined);

//            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
//            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                    Combined.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//            drawer.addDrawerListener(toggle);
//            toggle.syncState();
//
//            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//            navigationView.setNavigationItemSelectedListener(this);


            SessionConfiguration config = new SessionConfiguration.Builder()
                    .setClientId("HJjpMOZ1bAmyYJWqf0zACfmWQW-MHfyd")
                    .setServerToken("NG2r5ojZ83-jKs1XXsdmrmM0CkfgJXxlVTwJAOlb")
                    .setRedirectUri("")
                    .setEnvironment(SessionConfiguration.Environment.PRODUCTION) //Useful for testing your app in the sandbox environment
                    .setScopes(Arrays.asList(Scope.RIDE_WIDGETS)) //Your scopes for authentication here
                    .build();
            //RideRequestButton requestButton= new RideRequestButton(Combined.this);
            RideRequestButton requestButton = (RideRequestButton) findViewById(R.id.uber_button);
            UberSdk.initialize(config);
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_combined);
            if (requestButton.getParent() != null) {
                ((ViewGroup) requestButton.getParent()).removeView(requestButton);
            }
//            card1 = (CardView) findViewById(R.id.card1);
//            if (card1.getParent() != null) {
//                ((ViewGroup) card1.getParent()).removeView(card1);
//            }
//            card1.addView(requestButton);
//            layout.addView(card1);
            layout.addView(requestButton);


            Bundle extra = getIntent().getExtras();
            from_string = extra.getString("from");
            to_string = extra.getString("to");
            passengers_string = extra.getString("passengers");

            Log.d("errr","f"+from_string+"to"+to_string+"pa"+passengers_string);

            start_location = getLocationFromAddress(Combined.this, from_string);
            end_location = getLocationFromAddress(Combined.this, to_string);

            start_latitude = start_location.latitude;
            start_longitude = start_location.longitude;
            end_latitude = end_location.latitude;
            end_longitude = end_location.longitude;
            Log.d("startloc", String.valueOf(start_latitude) + " " + String.valueOf(start_longitude));
            Log.d("endloc", String.valueOf(end_latitude) + " " + String.valueOf(end_longitude));



            RideParameters rideParams = new RideParameters.Builder()
                    // Optional product_id from /v1/products endpoint (e.g. UberX). If not provided, most cost-efficient product will be used
                    //.setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")

                    // Required for price estimates; lat (Double), lng (Double), nickname (String), formatted address (String) of dropoff location
                    .setDropoffLocation(
                            end_latitude, end_longitude, "", "4425 Windrose Lane, Kingman, AZ 86401\n" +
                                    "Grasshopper Junction Arizona United States")
                    // Required for pickup estimates; lat (Double), lng (Double), nickname (String), formatted address (String) of pickup location
                    .setPickupLocation(start_latitude, start_longitude, "", "4280 El Antonio Place, Las Vegas, NV 89121\n" +
                            "Las Vegas Paradise, Las Vegas Nevada United States")
                    .build();
// set parameters for the RideRequestButton instance
            requestButton.setRideParameters(rideParams);

            ServerTokenSession session = new ServerTokenSession(config);
            requestButton.setSession(session);
            requestButton.loadRideInformation();


            RideRequestButtonCallback callback = new RideRequestButtonCallback() {

                @Override
                public void onRideInformationLoaded() {
                    // react to the displayed estimates

                }

                @Override
                public void onError(ApiError apiError) {
                    // API error details: /docs/riders/references/api#section-errors
                }

                @Override
                public void onError(Throwable throwable) {
                    // Unexpected error, very likely an IOException
                }
            };
            requestButton.setCallback(callback);


            ApiConfig apiConfig = new ApiConfig.Builder()
                    .setClientId("mQ97p5RtHhra")
                    .setClientToken("AFxubPuz6wwIzg8MY38jyeSrA7ZpyFrWpoklhxgpvabTKDgdG2lQ1BEKB3TNv+79qKcLDjRlWMBlczLtaPtkechhFAQvwl5a0zIZXMm+MEnv9pvmU2MV6ik=")
                    .build();

            LyftButton lyftButton = (LyftButton) findViewById(R.id.lyft_button);
            lyftButton.setApiConfig(apiConfig);

            if (lyftButton.getParent() != null) {
                ((ViewGroup) lyftButton.getParent()).removeView(lyftButton);
            }
//            card2 = (CardView) findViewById(R.id.card2);
//            if (card2.getParent() != null) {
//                ((ViewGroup) card2.getParent()).removeView(card2);
//            }
//            card2.addView(lyftButton);
//            layout.addView(card2);
                layout.addView(lyftButton);

            RideParams.Builder rideParamsBuilder = new RideParams.Builder()
                    .setPickupLocation(start_latitude, start_longitude)
                    .setDropoffLocation(end_latitude, end_longitude);
            rideParamsBuilder.setRideTypeEnum(RideTypeEnum.CLASSIC);

            lyftButton.setLyftStyle(LyftStyle.MULBERRY_LIGHT);
            lyftButton.setRideParams(rideParamsBuilder.build());
            lyftButton.load();

            MapQuest.start(getApplicationContext());
            mMapView = (MapView) findViewById(R.id.mapquestMapView);

//            int ad_start_latitude=String.valueOf(start_latitude).split("\\.")[1].length();
//            int ad_start_longitude=String.valueOf(start_longitude).split("\\.")[1].length();
//            int ad_end_latitude=String.valueOf(end_latitude).split("\\.")[1].length();
//            int ad_end_longitude=String.valueOf(end_longitude).split("\\.")[1].length();
//            int ad_lat,ad_lon;
//            if(ad_start_latitude>=ad_end_latitude)
//            {
//                ad_lat=ad_start_latitude;
//            }
//            else
//            {
//                ad_lat=ad_end_latitude;
//            }
//
//            if(ad_start_longitude>=ad_end_longitude)
//            {
//                ad_lon=ad_start_longitude;
//            }
//            else
//            {
//                ad_lon=ad_end_longitude;
//            }
//            double mean_lat=(start_latitude+end_latitude)/2;
//            double mean_lon=(start_longitude+end_longitude)/2;
//            BigDecimal bd1=new BigDecimal(Double.toString(mean_lat));
//            bd1=bd1.setScale(ad_lat);
//            BigDecimal bd2=new BigDecimal(Double.toString(mean_lon));
//            bd2=bd2.setScale(ad_lon);

            com.mapbox.mapboxsdk.geometry.LatLng CAMERA_LOC = new com.mapbox.mapboxsdk.geometry.LatLng(start_latitude,start_longitude);
            mMapView.onCreate(bundle);
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(MapboxMap mapboxMap) {
                    mMapboxMap = mapboxMap;
                    mMapView.setStreetMode();
                    mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CAMERA_LOC, 13));
                    setPolyline(mapboxMap);
                }
            });


        }

        catch (Exception e)
        {
            Log.e("errr", "exited"+Log.getStackTraceString(e));
        }
    }

    private void setPolyline(MapboxMap mapboxMap) {
        List<com.mapbox.mapboxsdk.geometry. LatLng> coordinates = new ArrayList<>();
        coordinates.add(new com.mapbox.mapboxsdk.geometry.LatLng(start_latitude,start_longitude));
        coordinates.add(new com.mapbox.mapboxsdk.geometry.LatLng(end_latitude,end_longitude));
//        coordinates.add(new com.mapbox.mapboxsdk.geometry.LatLng(39.7468,-105.00709));
//        coordinates.add(new com.mapbox.mapboxsdk.geometry.LatLng(39.74391,-105.00794));
//        coordinates.add(new com.mapbox.mapboxsdk.geometry.LatLng(39.7425,-105.0047));
//        coordinates.add(new com.mapbox.mapboxsdk.geometry.LatLng(39.74634,-105.00478));
//        coordinates.add(new com.mapbox.mapboxsdk.geometry.LatLng(39.74734,-104.99984));

        PolylineOptions polyline = new PolylineOptions();
        polyline.addAll(coordinates);
        polyline.width(3);
        polyline.color(Color.BLUE);
        mapboxMap.addPolyline(polyline);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    { super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState); }


    public com.google.android.gms.maps.model.LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        com.google.android.gms.maps.model.LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new com.google.android.gms.maps.model.LatLng(location.getLatitude(), location.getLongitude() );



        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }


//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
