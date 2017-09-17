package com.sundevs.ihsan.plnservice.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sundevs.ihsan.plnservice.R;
import com.sundevs.ihsan.plnservice.config.Constants;
import com.sundevs.ihsan.plnservice.config.URLConfig;
import com.sundevs.ihsan.plnservice.controller.AppController;
import com.sundevs.ihsan.plnservice.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences preferences;
    @Bind(R.id.coordintorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.no_tamper)
    EditText tamper;
    @Bind(R.id.password)
    EditText pass;
    SessionManager session;
    private static final String TAG = LoginActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toobar_logreg);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //full screen android
        ButterKnife.bind(this);
        session =new SessionManager(getApplicationContext());
        if(session.isLoggedIn()){
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        }
    }

    @OnClick(R.id.masuk)
    void masuk(){
        login(tamper.getText().toString(), pass.getText().toString());

    }
    @OnClick(R.id.tanya)
    void tanyaregister(){
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }
    private void login(final String username, final String password) {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Cek pengguna...");
        loading.show();
        // string request webservice
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLConfig.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //kondisi login sukses
                        if (response.contains(Constants.LOGIN_SUCCESS)) {
                            loading.dismiss();
                            session.createLoginSession(username,password);
                            simpan(username);
                        } else {
                            Snackbar snack = Snackbar.make(coordinatorLayout, "Username atau Password Salah", Snackbar.LENGTH_LONG);
                            snack.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (volleyError instanceof NetworkError){
                            Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.networkerror, Snackbar.LENGTH_LONG);
                            snacka.show();
                        } else if (volleyError instanceof ServerError){
                            Snackbar snackb = Snackbar.make(coordinatorLayout, R.string.ServerError, Snackbar.LENGTH_LONG);
                            snackb.show();
                        } else if (volleyError instanceof AuthFailureError){
                            Snackbar snackc = Snackbar.make(coordinatorLayout, R.string.AuthFailureError, Snackbar.LENGTH_LONG);
                            snackc.show();
                        } else if (volleyError instanceof ParseError){
                            Snackbar snackd = Snackbar.make(coordinatorLayout, R.string.ParseError, Snackbar.LENGTH_LONG);
                            snackd.show();
                        } else if (volleyError instanceof NoConnectionError){
                            Snackbar snacke = Snackbar.make(coordinatorLayout, R.string.NoConnectionError, Snackbar.LENGTH_LONG);
                            snacke.show();
                        } else if (volleyError instanceof TimeoutError){
                            Snackbar snackf = Snackbar.make(coordinatorLayout, R.string.TimeoutError, Snackbar.LENGTH_LONG);
                            snackf.show();
                        }
                        loading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.TAG_USERNAME, username);
                params.put(Constants.TAG_PASSWORD, password);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //antrian string request
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void simpan(final String id_pel) {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Cek Biodata");
        loading.show();
        StringRequest strReq = new StringRequest(Request.Method.POST, URLConfig.URL_SAVELOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    Constants.success = jObj.getInt(Constants.TAG_SUCCESS);

                    // Cek error node pada json
                    if (Constants.success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String id_pel = jObj.getString(Constants.TAG_IDPEL);
                        String no_tamper = jObj.getString(Constants.TAG_TAMPER);
                        String nama = jObj.getString(Constants.TAG_NAMA);
                        String alamat = jObj.getString(Constants.TAG_ALAMAT);
                        String no_hp = jObj.getString(Constants.TAG_NOHP);
                        preferences=
                                getApplicationContext().getSharedPreferences("data", 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("id_pel", id_pel);
                        editor.putString("no_tamper", no_tamper);
                        editor.putString("nama", nama);
                        editor.putString("alamat", alamat);
                        editor.putString("no_hp", no_hp);
                        editor.commit();
                        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                    } else {
                        Snackbar snacka = Snackbar.make(coordinatorLayout, jObj.getString(Constants.TAG_MESSAGE), Snackbar.LENGTH_LONG);
                        snacka.show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError instanceof NetworkError){
                    Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.networkerror, Snackbar.LENGTH_LONG);
                    snacka.show();
                } else if (volleyError instanceof ServerError){
                    Snackbar snackb = Snackbar.make(coordinatorLayout, R.string.ServerError, Snackbar.LENGTH_LONG);
                    snackb.show();
                } else if (volleyError instanceof AuthFailureError){
                    Snackbar snackc = Snackbar.make(coordinatorLayout, R.string.AuthFailureError, Snackbar.LENGTH_LONG);
                    snackc.show();
                } else if (volleyError instanceof ParseError){
                    Snackbar snackd = Snackbar.make(coordinatorLayout, R.string.ParseError, Snackbar.LENGTH_LONG);
                    snackd.show();
                } else if (volleyError instanceof NoConnectionError){
                    Snackbar snacke = Snackbar.make(coordinatorLayout, R.string.NoConnectionError, Snackbar.LENGTH_LONG);
                    snacke.show();
                } else if (volleyError instanceof TimeoutError){
                    Snackbar snackf = Snackbar.make(coordinatorLayout, R.string.TimeoutError, Snackbar.LENGTH_LONG);
                    snackf.show();
                }
                loading.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.TAG_IDPEL, id_pel);
                return params;
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq, Constants.tag_json_obj);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
