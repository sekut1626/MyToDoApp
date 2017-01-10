package com.example.sebastian.mytodoapp;

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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sebastian.mytodoapp.db.CursorNote;
import com.example.sebastian.mytodoapp.db.TaskDbHelper;

/**
 * Created by sebastian on 09.10.16.
 */
public class NoteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TaskDbHelper dbHelperN;
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
        updateListView();

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




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_task) {
            Intent iinent= new Intent(NoteActivity.this,MainActivity.class);
            startActivity(iinent);
        } else if (id == R.id.nav_note) {

        } else if (id == R.id.nav_stat) {
            Intent iinent= new Intent(NoteActivity.this,StatisticActivity.class);
            startActivity(iinent);

        } else if (id == R.id.nav_info) {
            Intent iinent= new Intent(NoteActivity.this,InfoActivity.class);
            startActivity(iinent);
        } else if (id == R.id.nav_exit) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void iniDB() {

        dbHelperN = new TaskDbHelper(this);
    }

    public void addNoteT(View view) {

        String noteTitleText = String.valueOf(titleNoteText.getText());
        String noteContentText = String.valueOf(contenetNoteText.getText());

        dbHelperN.addNote(noteTitleText, noteContentText);

        titleNoteText.getText().clear();
        contenetNoteText.getText().clear();
        updateListView();
    }

    private void updateListView() {

        Cursor cursor = dbHelperN.getNoteValues();
        CursorNote cursorAdapter = new CursorNote(this, cursor, 0);

        nTaskListView.setAdapter(cursorAdapter);
    }

    public void displayAlert(View view){

//        TextView text = (TextView) view.findViewById(R.id.out_cont);
//        Toast.makeText(this, text.getText(), Toast.LENGTH_SHORT).show();

    }


}