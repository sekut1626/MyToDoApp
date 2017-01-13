package com.example.sebastian.mytodoapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sebastian.mytodoapp.R;
import com.example.sebastian.mytodoapp.db.CursorNote;
import com.example.sebastian.mytodoapp.db.DbHelper;

/**
 * Created by sebastian on 09.10.16.
 */
public class NoteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int CAMERA_REQUEST = 0;
    private DbHelper dbHelperN;
    private EditText titleNoteText;
    private EditText contenetNoteText;
    private ListView nTaskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initNoteControls();
        iniDB();
        updateListViewN();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initNoteControls() {

        titleNoteText = (EditText) findViewById(R.id.title_note);
        contenetNoteText = (EditText) findViewById(R.id.cont_note);
        nTaskListView = (ListView) findViewById(R.id.list_note);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_task) {
            Intent iinent= new Intent(NoteActivity.this,MainActivity.class);
            startActivity(iinent);
            finish();
        } else if (id == R.id.nav_note) {

        } else if (id == R.id.nav_stat) {
            Intent iinent= new Intent(NoteActivity.this,StatisticActivity.class);
            startActivity(iinent);
            finish();
        } else if (id == R.id.nav_info) {
            Intent iinent= new Intent(NoteActivity.this,InfoActivity.class);
            startActivity(iinent);
            finish();
        } else if (id == R.id.nav_exit) {
            finish();
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void iniDB() {

        dbHelperN = new DbHelper(this);
    }

    public void addNote(View view) {

        String noteTitleText = String.valueOf(titleNoteText.getText());
        String noteContentText = String.valueOf(contenetNoteText.getText());

        dbHelperN.addNote(noteTitleText, noteContentText);

        titleNoteText.getText().clear();
        contenetNoteText.getText().clear();
        updateListViewN();
    }

    public void delNote(View view) {

        View parentN = (View) view.getParent();
        TextView noteTextView = (TextView) parentN.findViewById(R.id.out_note_title);

        String noteName = String.valueOf(noteTextView.getText());
        dbHelperN.deleteNote(noteName);
        Toast.makeText(this, "usunąłeś notatkę", Toast.LENGTH_SHORT).show();
        updateListViewN();
    }

    private void updateListViewN() {

        Cursor cursor = dbHelperN.getNoteValues();
        CursorNote cursorAdapter = new CursorNote(this, cursor, 0);

        nTaskListView.setAdapter(cursorAdapter);
    }

    public void getPhoto(View view) {
    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(cameraIntent, CAMERA_REQUEST);
}


}