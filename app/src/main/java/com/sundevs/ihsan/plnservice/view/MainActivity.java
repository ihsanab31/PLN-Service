package com.sundevs.ihsan.plnservice.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sundevs.ihsan.plnservice.R;
import com.sundevs.ihsan.plnservice.config.Constants;
import com.sundevs.ihsan.plnservice.config.URLConfig;
import com.sundevs.ihsan.plnservice.controller.AppController;
import com.sundevs.ihsan.plnservice.fragment.HistoryFragment;
import com.sundevs.ihsan.plnservice.fragment.KeluhanFragment;
import com.sundevs.ihsan.plnservice.fragment.ProfileFragment;
import com.sundevs.ihsan.plnservice.session.SQLiteHandler;
import com.sundevs.ihsan.plnservice.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

public class MainActivity extends BaseActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    SQLiteHandler db;
    SessionManager session;
    String id_pelanggan;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preference =
                getApplicationContext().getSharedPreferences("data", 0);
        String nama= preference.getString("nama", null);
        String nohp= preference.getString("no_hp", null);
        id_pelanggan = preference.getString("id_pel", null);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView name = (TextView)header.findViewById(R.id.nama);
        TextView email = (TextView)header.findViewById(R.id.textView);
        name.setText(nama);
        email.setText(nohp);
        db = new SQLiteHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setupDrawerContent(navigationView);
        actionBarDrawerToggle = setupDrawerToggle();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        selectDrawerItem(navigationView.getMenu().getItem(1));

    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, getToolbar(), R.string.drawer_open,  R.string.drawer_close);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.keluhan:
                fragment = KeluhanFragment.newInstance();
                break;
            case R.id.riwayat:
                fragment = HistoryFragment.newInstance();
                break;
            case R.id.profile:
                fragment = ProfileFragment.newInstance();
                break;
            case R.id.keluar:
                fragment = KeluhanFragment.newInstance();
                logout(id_pelanggan);
                break;
            default:
                fragment = KeluhanFragment.newInstance();

                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        menuItem.setChecked(true);

        getToolbar().setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        this.finish();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Ask the user if they want to quit
        this.finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void logoutUser() {
        session.logoutUser();
        db.deleteUsers();
        // Launching the login activity
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    private void logout(final String idx){
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Proses Logout ....");
        loading.show();
        StringRequest strReq = new StringRequest(Request.Method.POST, URLConfig.URL_LOGOUT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    Constants.success = jObj.getInt(Constants.TAG_SUCCESS);

                    // Cek error node pada json
                    if (Constants.success == 1) {
                        Log.d("delete", jObj.toString());
                        logoutUser();
                        Toast.makeText(MainActivity.this, jObj.getString(Constants.TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        loading.dismiss();

                    } else {
                        Toast.makeText(MainActivity.this, jObj.getString(Constants.TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    loading.dismiss();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_pel", idx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, Constants.tag_json_obj);
    }

}