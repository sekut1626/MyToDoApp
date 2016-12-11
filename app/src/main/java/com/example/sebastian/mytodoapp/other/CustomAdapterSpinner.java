package com.example.sebastian.mytodoapp.other;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.sebastian.mytodoapp.R;

import java.util.ArrayList;

/**
 * Created by sebastian on 24.10.16.
 */

public class CustomAdapterSpinner extends ArrayAdapter<Integer> {

    private int groupid;
    private ArrayList<Integer> list;
    private LayoutInflater inflater;

    public CustomAdapterSpinner(Activity context, int groupid, int id, ArrayList<Integer> list) {
        super(context, id, list);

        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid = groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = inflater.inflate(groupid, parent, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img);
        imageView.setImageResource(list.get(position));
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}