package demo.chat.com.myfirstinternapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

/**
 * Created by engineering on 13.06.2016.
 */
public class Home  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences preferences ;
    private NavigationView mDrawer;
    private DrawerLayout mDrawerLayout;
    private static final String SELECTED_ITEM_ID = "selected_item_id";
    private static final String FIRST_TIME = "first_time";
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean mUserSawDrawer = false;
    private int mSelectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mDrawer = (NavigationView) findViewById(R.id.navigation_layout);
        mDrawer.setNavigationItemSelectedListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                null,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
//mDrawerToggle.setDrawerIndicatorEnabled(false);
         if (!didUserSeeDrawer()) {
            //showDrawer();
            //markDrawerSeen();
        } else {
            hideDrawer();
        }
        MapView map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            float lat =  extras.getFloat("lat");
            float lng = extras.getFloat("lng");

            String mahalle="";
            if(preferences.getString("mahalle","").equals(""))
                mahalle=preferences.getString("bkb_mah","")+" "+preferences.getString("bkb","");
            else
              mahalle=preferences.getString("mahalle","");
             String adres= mahalle+" mahallesi "+preferences.getString("yol","")+
                    " no: "+preferences.getString("no","")+" "+preferences.getString("ilce","")+"/"+
                    preferences.getString("il","")+"-"+preferences.getString("ulke","");
           //tüm preferencesleri temizledik.
           /* PreferenceManager.getDefaultSharedPreferences(getBaseContext()).
                    edit().clear().apply();*/

            GeoPoint startPoint = new GeoPoint(lat, lng);

            Marker startMarker = new Marker(map);
            startMarker.setTitle("Koordinatlar: lat:"+lat+" lng:"+lng);
            startMarker.setSubDescription(adres);

            startMarker.setPosition(startPoint);
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            map.getOverlays().add(startMarker);
            IMapController mapController = map.getController();
             mapController.setZoom(12);
            mapController.setCenter(startPoint);

        }
    }
    private boolean didUserSeeDrawer() {//bu fonksiyonlar navigationviewin sabit fonksyiyonlarıdır..
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = sharedPreferences.getBoolean(FIRST_TIME, false);
        return mUserSawDrawer;
    }

    private void markDrawerSeen() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = true;
        sharedPreferences.edit().putBoolean(FIRST_TIME, mUserSawDrawer).apply();
    }
    private void hideDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
    private void navigate(int mSelectedId) {

        Intent intent = null;
        if (mSelectedId == R.id.navigation_item_1){
            hideDrawer();

        }
        if (mSelectedId == R.id.navigation_item_2) {
            mDrawerLayout.closeDrawer(GravityCompat.START);

        }
        if (mSelectedId == R.id.navigation_item_3) {
            mDrawerLayout.closeDrawer(GravityCompat.START);

        }
    }
    private void showDrawer() {
         mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        menuItem.setChecked(true);
        mSelectedId = menuItem.getItemId();

        navigate(mSelectedId);
        return true;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onResume()
    {
        super.onResume();


    }
}
