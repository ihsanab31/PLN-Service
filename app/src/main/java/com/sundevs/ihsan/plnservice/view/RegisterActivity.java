package com.sundevs.ihsan.plnservice.view;

import android.app.ProgressDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    int success;
    @BindView(R.id.no_tamper_register)
    EditText tamper;
    @BindView(R.id.password_register)
    EditText password;
    @BindView(R.id.ulpassword_register)
    EditText upassword;
    String id_pel;
    String tag_json_obj = "json_obj_req";
    @BindView(R.id.coordintor)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //full screen android
        ButterKnife.bind(this);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void register() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Proses Registrasi");
        loading.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLConfig.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(Constants.TAG_SUCCESS);
                            if (success == 1) {
                                Log.d("v Add", jObj.toString());
                                Snackbar snack = Snackbar.make(coordinatorLayout, jObj.getString(Constants.TAG_MESSAGE), Snackbar.LENGTH_LONG);
                                snack.show();
                                kosong();
                                loading.dismiss();
                            } else {
                                Snackbar snack1 = Snackbar.make(coordinatorLayout, jObj.getString(Constants.TAG_MESSAGE), Snackbar.LENGTH_LONG);
                                snack1.show();
                                loading.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //menghilangkan progress dialog
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (volleyError instanceof NetworkError) {
                            Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.networkerror, Snackbar.LENGTH_LONG);
                            snacka.show();
                            loading.dismiss();
                        } else if (volleyError instanceof ServerError) {
                            Snackbar snackb = Snackbar.make(coordinatorLayout, R.string.ServerError, Snackbar.LENGTH_LONG);
                            snackb.show();
                            loading.dismiss();
                        } else if (volleyError instanceof AuthFailureError) {
                            Snackbar snackc = Snackbar.make(coordinatorLayout, R.string.AuthFailureError, Snackbar.LENGTH_LONG);
                            snackc.show();
                            loading.dismiss();
                        } else if (volleyError instanceof ParseError) {
                            Snackbar snackd = Snackbar.make(coordinatorLayout, R.string.ParseError, Snackbar.LENGTH_LONG);
                            snackd.show();
                            loading.dismiss();
                        } else if (volleyError instanceof NoConnectionError) {
                            Snackbar snacke = Snackbar.make(coordinatorLayout, R.string.NoConnectionError, Snackbar.LENGTH_LONG);
                            snacke.show();
                            loading.dismiss();
                        } else if (volleyError instanceof TimeoutError) {
                            Snackbar snackf = Snackbar.make(coordinatorLayout, R.string.TimeoutError, Snackbar.LENGTH_LONG);
                            snackf.show();
                            loading.dismiss();
                        }
                        loading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();
                //menambah parameter yang di kirim ke web servis
                params.put(Constants.TAG_USERNAME, tamper.getText().toString().trim());
                params.put(Constants.TAG_PASSWORD, password.getText().toString().trim());
                //kembali ke parameters
                Log.d(TAG, "" + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    @OnClick(R.id.daftar)
    void daftar() {
        //get token dari firebase
        if (upassword.getText().toString().matches(password.getText().toString())) {
            id_pel = tamper.getText().toString();
            cekid(id_pel);
        } else {
            Snackbar bar = Snackbar
                    .make(coordinatorLayout, "Password Tidak Sama", Snackbar.LENGTH_LONG);
            bar.show();
        }
    }

    private void cekid(final String username) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cek ID Pelanggan...");
        progressDialog.show();
        // membuat request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLConfig.CEKID_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (response.contains(Constants.LOGIN_SUCCESS)) {
                            register();
                            progressDialog.dismiss();
                        } else {
                            //Displaying an error message on toast
                            progressDialog.dismiss();
                            Snackbar snack5 = Snackbar
                                    .make(coordinatorLayout, "Pelanggan Belum Terdaftar", Snackbar.LENGTH_LONG);
                            snack5.show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        if (volleyError instanceof NetworkError) {
                            Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.networkerror, Snackbar.LENGTH_LONG);
                            snacka.show();
                            progressDialog.dismiss();
                        } else if (volleyError instanceof ServerError) {
                            Snackbar snackb = Snackbar.make(coordinatorLayout, R.string.ServerError, Snackbar.LENGTH_LONG);
                            snackb.show();
                            progressDialog.dismiss();
                        } else if (volleyError instanceof AuthFailureError) {
                            Snackbar snackc = Snackbar.make(coordinatorLayout, R.string.AuthFailureError, Snackbar.LENGTH_LONG);
                            snackc.show();
                            progressDialog.dismiss();
                        } else if (volleyError instanceof ParseError) {
                            Snackbar snackd = Snackbar.make(coordinatorLayout, R.string.ParseError, Snackbar.LENGTH_LONG);
                            snackd.show();
                            progressDialog.dismiss();
                        } else if (volleyError instanceof NoConnectionError) {
                            Snackbar snacke = Snackbar.make(coordinatorLayout, R.string.NoConnectionError, Snackbar.LENGTH_LONG);
                            snacke.show();
                            progressDialog.dismiss();
                        } else if (volleyError instanceof TimeoutError) {
                            Snackbar snackf = Snackbar.make(coordinatorLayout, R.string.TimeoutError, Snackbar.LENGTH_LONG);
                            snackf.show();
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Constants.TAG_USERNAME, username);
                //returning parameter
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    public void kosong() {
        tamper.setText("");
        password.setText("");
        upassword.setText("");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
