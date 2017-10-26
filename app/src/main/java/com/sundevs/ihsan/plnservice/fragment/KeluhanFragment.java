package com.sundevs.ihsan.plnservice.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class KeluhanFragment extends Fragment {
    View rootView;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    @BindView(R.id.coor)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.rdbtn)
    RadioGroup radioGroup;
    @BindView(R.id.kon)
    EditText lainnya;
    @BindView(R.id.foto)
    ImageView imageView;
    Bitmap bitmap;
    String kondisi = "", alamat = "", id_pelanggan = "", date = "", time = "", pilihan = "",hasil="";
    double bobot = 0;
    SharedPreferences preference;
    private static final String TAGG = KeluhanFragment.class.getSimpleName();
    public KeluhanFragment() {
        // Required empty public constructor
    }
    public static KeluhanFragment newInstance(){
        return new KeluhanFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_keluhan, container, false);
        getActivity().setTitle("Pengajuan Keluhan");
        preference =
                getActivity().getApplicationContext().getSharedPreferences("data", 0);
        id_pelanggan = preference.getString("id_pel", null);
        alamat = preference.getString("alamat", null);
        ButterKnife.bind(this, rootView);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case (R.id.padam):
                        kondisi = "Padam";
                        kondisi1();
                        break;
                    case (R.id.gangguan):
                        kondisi = "Gangguna";
                        kondisi2();
                        break;
                    case (R.id.mcb):
                        kondisi = "MCB";
                        kondisi3();
                        break;
                    case (R.id.lpb):
                        kondisi = "LPB";
                        pilihan = kondisi + " Muncul Tanda Periksa";
                        bobot = 50;
                        bobot = bobot * 0.1282051282;
                        Snackbar snacka = Snackbar.make(coordinatorLayout, "Anda mengajukan keluhan " + pilihan, Snackbar.LENGTH_LONG);
                        snacka.show();
                        break;
                }
            }
        });
        return rootView;
    }

    @OnClick(R.id.foto)
    void ambilfoto() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @OnClick(R.id.kel)
    void ajukankeluhan() {
        if (lainnya.getText().toString().equals("")){
              if(pilihan ==""){
                  Snackbar snackbar = Snackbar.make(coordinatorLayout, "Belum Memilih Ganggaun", Snackbar.LENGTH_LONG);
                  snackbar.show();
              }else {
                  if(bobot>=6 && bobot<9 ){
                      hasil="Ringan";
                      if(bitmap==null){
                          Snackbar snackbar = Snackbar.make(coordinatorLayout, "Belum Mengambil Foto", Snackbar.LENGTH_LONG);
                          snackbar.show();
                      }else {
                          date =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                          time =new SimpleDateFormat("h:m:s").format(new Date());
                          uploadData();
                          imageView.setImageBitmap(null);
                      }
                  }
                  else if(bobot>=9 && bobot<16 ){
                      hasil="Sedang";
                      if(bitmap==null){
                          Snackbar snackbar = Snackbar.make(coordinatorLayout, "Belum Mengambil Foto", Snackbar.LENGTH_LONG);
                          snackbar.show();
                      }else {
                          date =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                          time =new SimpleDateFormat("h:m:s").format(new Date());
                          uploadData();
                          imageView.setImageBitmap(null);
                      }
                  }

                  else if(bobot>=16 && bobot<25 ){
                      hasil="Berat";
                      if(bitmap==null){
                          Snackbar snackbar = Snackbar.make(coordinatorLayout, "Belum Mengambil Foto", Snackbar.LENGTH_LONG);
                          snackbar.show();
                      }else {
                          date =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                          time =new SimpleDateFormat("h:m:s").format(new Date());
                          uploadData();
                          imageView.setImageBitmap(null);
                      }
                  }
                  else if(bobot>25){
                      hasil="Urgent";
                      if(bitmap==null){
                          Snackbar snackbar = Snackbar.make(coordinatorLayout, "Belum Mengambil Foto", Snackbar.LENGTH_LONG);
                          snackbar.show();
                      }else {
                          date =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                          time =new SimpleDateFormat("h:m:s").format(new Date());
                          uploadData();
                          imageView.setImageBitmap(null);
                      }

                  }
              }

        }else {
            pilihan = lainnya.getText().toString();
            hasil = "Tidak Termasuk Klafikasi";
            date =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            time =new SimpleDateFormat("h:m:s").format(new Date());
            uploadData();
            imageView.setImageBitmap(null);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        try {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.belumdiambil, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void kondisi1() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.kondisi, null);
        RadioGroup rd = (RadioGroup) dialogView.findViewById(R.id.radioku);
        RadioButton rd1 = (RadioButton) dialogView.findViewById(R.id.radio1);
        RadioButton rd2 = (RadioButton) dialogView.findViewById(R.id.radio2);
        RadioButton rd3 = (RadioButton) dialogView.findViewById(R.id.radio3);
        rd1.setVisibility(View.VISIBLE);
        rd2.setVisibility(View.VISIBLE);
        rd3.setVisibility(View.VISIBLE);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        rd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case (R.id.radio1):
                        pilihan = kondisi + " 1 Rumah";
                        bobot = 50;
                        bobot = bobot * 0.1282051282;
                        break;
                    case (R.id.radio2):
                        pilihan = kondisi + " Sebagian Rumah";
                        bobot = 60;
                        bobot = bobot * 0.1538461538;
                        break;
                    case (R.id.radio3):
                        pilihan = kondisi + " Menyeluruh";
                        bobot = 80;
                        bobot = bobot * 0.2051282051;
                        break;
                }
            }
        });
        dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pilihan = pilihan;
                bobot = bobot;

            }
        });
        dialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    private void kondisi2() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.kondisi, null);
        RadioGroup rd = (RadioGroup) dialogView.findViewById(R.id.radioku);
        RadioButton rd4 = (RadioButton) dialogView.findViewById(R.id.radio4);
        RadioButton rd5 = (RadioButton) dialogView.findViewById(R.id.radio5);
        RadioButton rd6 = (RadioButton) dialogView.findViewById(R.id.radio6);
        RadioButton rd7 = (RadioButton) dialogView.findViewById(R.id.radio7);
        rd4.setVisibility(View.VISIBLE);
        rd5.setVisibility(View.VISIBLE);
        rd6.setVisibility(View.VISIBLE);
        rd7.setVisibility(View.VISIBLE);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        rd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case (R.id.radio4):
                        pilihan = "Putus Pada Sambungan Rumah(SR)";
                        bobot = 50;
                        bobot = bobot * 0.1282051282;
                        break;
                    case (R.id.radio5):
                        pilihan = "Gangguan JTR";
                        bobot = 60;
                        bobot = bobot * 0.1538461538;
                        break;
                    case (R.id.radio6):
                        pilihan = "Gangguna App";
                        bobot = 50;
                        bobot = bobot * 0.1282051282;
                        break;
                    case (R.id.radio7):
                        pilihan = "Gangguna KWH Meter";
                        bobot = 50;
                        bobot = bobot * 0.1282051282;
                        break;
                }
            }
        });
        dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pilihan = pilihan;
                bobot = bobot;

            }
        });
        dialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    private void kondisi3() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.kondisi, null);
        RadioGroup rd = (RadioGroup) dialogView.findViewById(R.id.radioku);
        RadioButton rd8 = (RadioButton) dialogView.findViewById(R.id.radio8);
        RadioButton rd9 = (RadioButton) dialogView.findViewById(R.id.radio9);
        rd8.setVisibility(View.VISIBLE);
        rd9.setVisibility(View.VISIBLE);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        rd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case (R.id.radio8):
                        pilihan = "MCB Terbakar";
                        bobot = 100;
                        bobot = bobot * 0.2564102564;
                        break;
                    case (R.id.radio9):
                        pilihan = "MCB Loncer";
                        bobot = 50;
                        bobot = bobot * 0.1282051282;
                        break;

                }
            }
        });
        dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pilihan = pilihan;
                bobot = bobot;

            }
        });
        dialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    private void uploadData() {
        final ProgressDialog loading = new ProgressDialog(this.getActivity());
        loading.setMessage("Upload Data");
        loading.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLConfig.URL_KELUHAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAGG, "Response: " + response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Constants.success = jObj.getInt(Constants.TAG_SUCCESS);

                            if (Constants.success == 1) {
                                Log.d("v Add", jObj.toString());
                                Toast.makeText(getActivity(), jObj.getString(Constants.TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                Snackbar snacka = Snackbar.make(coordinatorLayout, "Anda mengajukan keluhan " + pilihan, Snackbar.LENGTH_LONG);
                                snacka.show();
                                bitmap=null;
                                time="";
                                date ="";
                                pilihan = "";
                                lainnya.setText("");
                                loading.dismiss();
                            } else {
                                Toast.makeText(getActivity(), jObj.getString(Constants.TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                        if (volleyError instanceof NetworkError){
                            Snackbar snacka = Snackbar.make(coordinatorLayout, R.string.networkerror, Snackbar.LENGTH_LONG);
                            snacka.show();
                            loading.dismiss();
                        } else if (volleyError instanceof ServerError){
                            Snackbar snackb = Snackbar.make(coordinatorLayout, R.string.ServerError, Snackbar.LENGTH_LONG);
                            snackb.show();
                            loading.dismiss();
                        } else if (volleyError instanceof AuthFailureError){
                            Snackbar snackc = Snackbar.make(coordinatorLayout, R.string.AuthFailureError, Snackbar.LENGTH_LONG);
                            snackc.show();
                            loading.dismiss();
                        } else if (volleyError instanceof ParseError){
                            Snackbar snackd = Snackbar.make(coordinatorLayout, R.string.ParseError, Snackbar.LENGTH_LONG);
                            snackd.show();
                            loading.dismiss();
                        } else if (volleyError instanceof NoConnectionError){
                            Snackbar snacke = Snackbar.make(coordinatorLayout, R.string.NoConnectionError, Snackbar.LENGTH_LONG);
                            snacke.show();
                            loading.dismiss();
                        } else if (volleyError instanceof TimeoutError){
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
                params.put(Constants.TAG_IDKEL, "");
                params.put(Constants.TAG_IDPEL, id_pelanggan);
                params.put(Constants.TAG_ALAMAT, alamat);
                params.put(Constants.TAG_WAKTU, time);
                params.put(Constants.TAG_TANGGAL, date);
                params.put(Constants.TAG_KELUHAN, pilihan);
                params.put(Constants.TAG_TKEL, hasil);
                params.put(Constants.TAG_IMAGES, getStringImage(bitmap));
                //kembali ke parameters
                Log.d(TAGG, "" + params);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest, Constants.tag_json_obj);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
