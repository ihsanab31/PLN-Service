package com.sundevs.ihsan.plnservice.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sundevs.ihsan.plnservice.R;
import com.sundevs.ihsan.plnservice.config.URLConfig;
import com.sundevs.ihsan.plnservice.controller.AppController;
import com.sundevs.ihsan.plnservice.model.Data;

import java.util.List;

/**
 * Created by iihsa on 9/15/2017.
 */

public class HistoryAdapter extends BaseAdapter {
    private Fragment activity;
    private LayoutInflater inflater;
    private List<Data> itemHistory;
    ImageLoader imageLoader;
    public HistoryAdapter (Fragment activity, List<Data> itemHistory){
        this.activity=activity;
        this.itemHistory=itemHistory;
    }
    @Override
    public int getCount() {
        return itemHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return itemHistory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
            inflater= (LayoutInflater)activity.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.history_adapter, null);
        if (imageLoader==null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.gambar_history);
        TextView waktu = (TextView)convertView.findViewById(R.id.jam);
        TextView tanggal= (TextView)convertView.findViewById(R.id.tnggl);
        TextView keluhan= (TextView)convertView.findViewById(R.id.keluhan);
        TextView status= (TextView)convertView.findViewById(R.id.status);
        Data data = itemHistory.get(position);
        imageView.setImageUrl(URLConfig.URL_BERANDA+data.getGambar(), imageLoader);
        waktu.setText(data.getWaktu());
        tanggal.setText(data.getTanggal());
        keluhan.setText(data.getKeluhan());
        status.setText(data.getStatus());
        return convertView;
    }
}
