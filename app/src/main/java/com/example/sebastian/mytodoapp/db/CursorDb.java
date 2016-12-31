
package com.example.sebastian.mytodoapp.db;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import com.example.sebastian.mytodoapp.other.CustomAdapterSpinner;
import com.example.sebastian.mytodoapp.R;

import java.util.ArrayList;


/**
 * Created by sebastian on 16.10.16.
 */
public class CursorDb extends CursorAdapter {

    private LayoutInflater layoutInflater;

    public CursorDb(Context context, Cursor c, int flags) {
        super(context, c, flags);

        layoutInflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return layoutInflater.inflate(R.layout.item_todo, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView txtTitle = (TextView) view.findViewById(R.id.task_title);
        String title = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COL_TASK_TITLE));
        txtTitle.setText(title);


        int isStrikeTrough = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COL_TASK_IS_STRIKETROUGH));
        if (isStrikeTrough == 1)
            txtTitle.setPaintFlags(txtTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        TextView txtData = (TextView) view.findViewById(R.id.dataSet);
        String date = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COL_TASK_DATE));
        txtData.setText(date);

        CheckBox checkBoxstrike = (CheckBox) view.findViewById(R.id.checkbox2);
        int isDon = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COL_TASK_IS_STRIKETROUGH));
        switch (isDon) {
            case 0:
                checkBoxstrike.setChecked(false);
                break;

            case 1:
                checkBoxstrike.setChecked(true);
                break;
        }


        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox1);
        int isDone = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COL_TASK_STAR));

        switch (isDone) {
            case 0:
                checkBox.setChecked(false);
                break;

            case 1:
                checkBox.setChecked(true);
                break;
        }

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner2);
        int prio = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COL_TASK_PRIORITY));
        ArrayList<Integer> list = new ArrayList<>();
        switch (prio) {
            case 0:
                list.add(R.drawable.jeden);
                list.add(R.drawable.trzy);
                list.add(R.drawable.dwa);
                break;

            case 1:

                list.add(R.drawable.trzy);
                list.add(R.drawable.dwa);
                list.add(R.drawable.jeden);
                break;

            case 2:

                list.add(R.drawable.dwa);
                list.add(R.drawable.jeden);
                list.add(R.drawable.trzy);
                break;

        }
        SpinnerAdapter adapter = new CustomAdapterSpinner((Activity) context, R.layout.spinner_layout, R.id.txt, list);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
//                        Toast.makeText(parent.getContext(), "zadanie NORMALNE", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
//                        Toast.makeText(parent.getContext(), "zadanie WAŻNE", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
//                        Toast.makeText(parent.getContext(), "zadanie PILNE", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

