package com.example.sebastian.mytodoapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.sebastian.mytodoapp.R;
import com.example.sebastian.mytodoapp.db.CursorTask;
import com.example.sebastian.mytodoapp.db.DbHelper;
import com.example.sebastian.mytodoapp.other.CustomAdapterSpinner;
import com.example.sebastian.mytodoapp.other.DatePickerFragment;

import java.util.ArrayList;

/**
 * Created by sebastian on 12.06.16.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView imageView;
    private DbHelper dbHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    private EditText taskNameEditText;
    private CheckBox checkBoxStar;
    private CheckBox checkBoxStrike;
    private Spinner spinner;
    private TextView txt;
    private int stateOfStar = 0;
    private int stateOfPriority = 0;
    private int stateOfStrikeTrough = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControls();
        initDb();
        updateListView();
        priorityList();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_task) {

        } else if (id == R.id.nav_note) {
            Intent iinent = new Intent(MainActivity.this, NoteActivity.class);
            startActivity(iinent);
            finish();
        } else if (id == R.id.nav_stat) {
            Intent iinent = new Intent(MainActivity.this, StatisticActivity.class);
            startActivity(iinent);
            finish();
        } else if (id == R.id.nav_info) {
            Intent iinent = new Intent(MainActivity.this, InfoActivity.class);
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

    public void priorityList() {

        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.drawable.jeden);
        list.add(R.drawable.dwa);
        list.add(R.drawable.trzy);

        SpinnerAdapter adapter = new CustomAdapterSpinner(this, R.layout.spinner_layout, R.id.txt, list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        stateOfPriority = 0;
                        break;
                    case 1:
                        stateOfPriority = 1;
                        break;
                    case 2:
                        stateOfPriority = 2;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initControls() {

        checkBoxStar = (CheckBox) findViewById(R.id.checkBox1);
        checkBoxStrike = (CheckBox) findViewById(R.id.checkbox2);
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        taskNameEditText = (EditText) findViewById(R.id.taketask);
        spinner = (Spinner) findViewById(R.id.spinner);
        txt = (TextView) findViewById(R.id.textViewDate);
    }

    private void initDb() {

        dbHelper = new DbHelper(this);
    }

    public void addData(View view) {

        String task = String.valueOf(taskNameEditText.getText());
        int stateOfStar = this.stateOfStar;
        int stateOfPriority = this.stateOfPriority;
        String tektstdaty = String.valueOf(txt.getText());
        int stateOfStrikeTrough = this.stateOfStrikeTrough;

        dbHelper.addTaskWithStar(task, stateOfStar, stateOfPriority, tektstdaty, stateOfStrikeTrough);

        taskNameEditText.getText().clear();
        updateListView();

    }

    public void delTask(View view) {

        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);

        CheckBox checkBox = (CheckBox) parent.findViewById(R.id.checkbox2);

        if (checkBox.isChecked())
            stateOfStrikeTrough = 1;
        else
            stateOfStrikeTrough = 0;

        String taskName = String.valueOf(taskTextView.getText());
        dbHelper.deleteTask(taskName, stateOfStrikeTrough);
        updateListView();
    }

    public void updateStrikeTrough(View view) {

        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);

        CheckBox checkBox = (CheckBox) parent.findViewById(R.id.checkbox2);

        if (checkBox.isChecked())
            stateOfStrikeTrough = 1;
        else
            stateOfStrikeTrough = 0;

        String taskName = String.valueOf(taskTextView.getText());
        dbHelper.updateTask(taskName, stateOfStrikeTrough);
        updateListView();
    }

    private void updateListView() {

        Cursor cursor = dbHelper.getDataForListView();
        CursorTask cursorAdapter = new CursorTask(this, cursor, 0);

        mTaskListView.setAdapter(cursorAdapter);
    }

    public void setCheckBoxStar(View view) {

        if (checkBoxStar.isChecked()) {
            stateOfStar = 1;
        } else {
            stateOfStar = 0;
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}


