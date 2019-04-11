package com.example.dahiya.taxifare;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.mapquest.android.commoncore.log.L;

import java.util.Arrays;
import java.util.List;




public class MainActivity extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener
        {

    Button go_button;
    ImageView imageView;
    EditText frome,toe,passengerse;
    String from_string,to_string,passengers_string;
    TextView heading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        heading=(TextView)findViewById(R.id.heading);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/LobsterTwo-Bold.otf");
        heading.setTypeface(typeface);


        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);






        frome=(EditText)findViewById(R.id.from);
        toe=(EditText)findViewById(R.id.to);
        passengerse=(EditText)findViewById(R.id.passengers);




        go_button=(Button) findViewById(R.id.go);
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        imageView=findViewById(R.id.imageView2);
        imageView.setMinimumWidth(width);
        imageView.setMinimumHeight(height);

        imageView.setMaxWidth(width);
        imageView.setMaxHeight(height);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);


    }

//    private void hidetitlebar()
//    {
//        this.getWindow().getDecorView()
//                .setSystemUiVisibility(
//                        View.SYSTEM_UI_FLAG_FULLSCREEN |
//                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
//                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
//                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
//                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
//                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                );
//    }
//
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
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
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

    public void go_clicked(View view) {

        from_string=frome.getText().toString();
        to_string=toe.getText().toString();
        passengers_string=passengerse.getText().toString();
        //Log.d("errr","f"+from_string+"to"+to_string+"pa"+passengers_string);

        Intent intent=new Intent(MainActivity.this,Combined.class);
        intent.putExtra("from",from_string);
        intent.putExtra("to",to_string);
        intent.putExtra("passengers",passengers_string);
//        Intent intent=new Intent(MainActivity.this,SearchBarView.class);
        startActivity(intent);
    }



}

