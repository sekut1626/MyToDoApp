package com.example.sebastian.mytodoapp.db;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.sebastian.mytodoapp.R;

/**
 * Created by sebastian on 31.12.16.
 */

public class CursorNote extends CursorAdapter {

    private LayoutInflater layoutInflaterNote;

    public CursorNote(Context context, Cursor c, int flags) {
        super(context, c, flags);

        layoutInflaterNote = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return layoutInflaterNote.inflate(R.layout.item_note, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView NTitle = (TextView) view.findViewById(R.id.out_note_title);
        String getOutTitleNote = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COL_NOTE_TITLE));
        NTitle.setText(getOutTitleNote);



    }
}
