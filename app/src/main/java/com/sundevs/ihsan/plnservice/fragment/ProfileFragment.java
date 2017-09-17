package com.sundevs.ihsan.plnservice.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.sundevs.ihsan.plnservice.R;
import com.sundevs.ihsan.plnservice.config.Constants;
import com.sundevs.ihsan.plnservice.config.URLConfig;
import com.sundevs.ihsan.plnservice.controller.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProfileFragment extends Fragment {
    View rootView;
    SharedPreferences preference;
    @Bind(R.id.nama)
    TextView txtnama;
    @Bind(R.id.alamat)
    TextView txtalamat;
    @Bind(R.id.nohp_prof)
    TextView txtnohp;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View view;
    private static final String TAG1 = ProfileFragment.class.getSimpleName();
    String nama1="", alamat1="",nohp1="", id_pel="";
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile");
        ButterKnife.bind(this, rootView);
        preference =
                getActivity().getApplicationContext().getSharedPreferences("data", 0);
        id_pel= preference.getString("id_pel", null);
        nama1= preference.getString("nama", null);
        nohp1= preference.getString("no_hp", null);
        alamat1= preference.getString("alamat", null);
        txtnama.setText(nama1);
        txtalamat.setText(alamat1);
        txtnohp.setText(nohp1);
        getActivity().setTitle("Profile");
        return rootView;
    }
    @OnClick(R.id.edit)
    void editData(){
        edit();
    }
    private void edit() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.edit_prof, null);
        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.setTitle("Edit Profile ");
        final EditText nam=(EditText)view.findViewById(R.id.nam);
        final EditText almt=(EditText)view.findViewById(R.id.almt);
        final EditText phone=(EditText)view.findViewById(R.id.phone);
        preference =
                getActivity().getApplicationContext().getSharedPreferences("data", 0);
        nama1= preference.getString("nama", null);
        nohp1= preference.getString("no_hp", null);
        alamat1= preference.getString("alamat", null);
        nam.setText(nama1);
        almt.setText(alamat1);
        phone.setText(nohp1);
        dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editprofile(id_pel, nam.getText().toString(),almt.getText().toString(),phone.getText().toString());
                preference =
                        getActivity().getApplicationContext().getSharedPreferences("data", 0);
                nama1= preference.getString("nama", null);
                nohp1= preference.getString("no_hp", null);
                alamat1= preference.getString("alamat", null);
                txtnama.setText(nam.getText().toString());
                txtalamat.setText(almt.getText().toString());
                txtnohp.setText(phone.getText().toString());
            }
        });
        dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }
    private void editprofile(final String idpel, final String nama, final String alamat, final String no_hp) {
        final ProgressDialog loading =new  ProgressDialog(this.getActivity());
        loading.setMessage("Ubah Profile");
        loading.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLConfig.URL_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG1, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            Constants.success = jObj.getInt(Constants.TAG_SUCCESS);

                            if ( Constants.success == 1) {
                                Log.d("v Add", jObj.toString());
                                preference =
                                        getActivity().getApplicationContext().getSharedPreferences("data", 0);
                                SharedPreferences.Editor editor = preference.edit();
                                editor.putString("nama", nama);
                                editor.putString("alamat", alamat);
                                editor.putString("no_hp", no_hp);
                                editor.commit();
                                txtnama.setText(nama);
                                txtalamat.setText(alamat);
                                txtnohp.setText(no_hp);
                                Toast.makeText(getActivity(), jObj.getString( Constants.TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), jObj.getString( Constants.TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();


                //menambah parameter yang di kirim ke web servis
                params.put(Constants.TAG_IDPEL,idpel);
                params.put(Constants.TAG_NAMA, nama);
                params.put(Constants.TAG_ALAMAT, alamat);
                params.put(Constants.TAG_NOHP, no_hp);
                //kembali ke parameters
                Log.d(TAG1, "" + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest, Constants.tag_json_obj);
    }
}
