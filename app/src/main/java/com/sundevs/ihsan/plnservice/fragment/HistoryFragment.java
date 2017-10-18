package com.sundevs.ihsan.plnservice.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.sundevs.ihsan.plnservice.R;
import com.sundevs.ihsan.plnservice.adapter.HistoryAdapter;
import com.sundevs.ihsan.plnservice.config.Constants;
import com.sundevs.ihsan.plnservice.config.URLConfig;
import com.sundevs.ihsan.plnservice.controller.AppController;
import com.sundevs.ihsan.plnservice.model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View rootView;
    String id_pel;
    Handler handler;
    Runnable runnable;
    HistoryAdapter historyAdapter;
    @Bind(R.id.coodinat)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.list_history)
    ListView listView;
    private static final String TAGG = HistoryFragment.class.getSimpleName();
    List<Data> listItemHistory=new ArrayList<Data>();
    public HistoryFragment() {
        // Required empty public constructor
    }
    public static HistoryFragment newInstance(){
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=  inflater.inflate(R.layout.fragment_history, container, false);
        getActivity().setTitle("Riwaya Keluhan");
        ButterKnife.bind(this, rootView);
        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) HistoryFragment.this);
        SharedPreferences preference =
                getActivity().getSharedPreferences("data", 0);
        id_pel= preference.getString("id_pel", null);
        historyAdapter = new HistoryAdapter(HistoryFragment.this, listItemHistory);
        listView.setAdapter(historyAdapter);
        swipeRefreshLayout.post(new Runnable() {
                       @Override
                       public void run() {
                           swipeRefreshLayout.setRefreshing(true);
                           listItemHistory.clear();
                           historyAdapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );

        return rootView;
    }

    private void callVolley() {
        listItemHistory.clear();
        historyAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(true);
        // membuat request JSON
        StringRequest jArr = new StringRequest(Request.Method.POST, URLConfig.URL_HISTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAGG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONArray Jarr = new JSONArray(response);
                        JSONObject obj = Jarr.getJSONObject(i);
                        Data item = new Data();
                        item.setWaktu(obj.getString(Constants.TAG_WAKTU));
                        item.setTanggal(obj.getString(Constants.TAG_TANGGAL));
                        item.setKeluhan(obj.getString(Constants.TAG_KELUHAN));
                        item.setGambar(obj.getString(Constants.TAG_IMAGES));
                        // menambah item ke array
                        listItemHistory.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                historyAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError instanceof NetworkError){
                    Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.networkerror, Snackbar.LENGTH_LONG);
                    snacka.show();
                    swipeRefreshLayout.setRefreshing(false);
                } else if (volleyError instanceof ServerError){
                    Snackbar snackb = Snackbar.make(coordinatorLayout, R.string.ServerError, Snackbar.LENGTH_LONG);
                    snackb.show();
                    swipeRefreshLayout.setRefreshing(false);
                } else if (volleyError instanceof AuthFailureError){
                    Snackbar snackc = Snackbar.make(coordinatorLayout, R.string.AuthFailureError, Snackbar.LENGTH_LONG);
                    snackc.show();
                    swipeRefreshLayout.setRefreshing(false);
                } else if (volleyError instanceof ParseError){
                    Snackbar snackd = Snackbar.make(coordinatorLayout, R.string.ParseError, Snackbar.LENGTH_LONG);
                    snackd.show();
                    swipeRefreshLayout.setRefreshing(false);
                } else if (volleyError instanceof NoConnectionError){
                    Snackbar snacke = Snackbar.make(coordinatorLayout, R.string.NoConnectionError, Snackbar.LENGTH_LONG);
                    snacke.show();
                    swipeRefreshLayout.setRefreshing(false);
                } else if (volleyError instanceof TimeoutError){
                    Snackbar snackf = Snackbar.make(coordinatorLayout, R.string.TimeoutError, Snackbar.LENGTH_LONG);
                    snackf.show();
                    swipeRefreshLayout.setRefreshing(false);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.TAG_IDPEL, id_pel);
                return params;
            }
        };
        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr, Constants.tag_json_obj);
    }
    @Override
    public void onRefresh() {
        listItemHistory.clear();
        historyAdapter.notifyDataSetChanged();
        callVolley();
    }
}
