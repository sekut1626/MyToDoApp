package com.example.sebastian.mytodoapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.sebastian.mytodoapp.db.TaskContract;
import com.example.sebastian.mytodoapp.db.TaskDbHelper;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //    private String[] xData = {"Ilość dodanych zadań", "Ilość aktualnyh zadań", "Ilość usuniętych zadań",
//            "Ilość dodanych notatek", "Ilość usuniętych notatek", " "};
    PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        TaskDbHelper db = new TaskDbHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        pieChart = (PieChart) findViewById(R.id.wykres);

        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText(" ");
        pieChart.setCenterTextSize(15);
        pieChart.setDrawEntryLabels(true);

        addDataSet(db.getStatisiticValues());

    }

    private void addDataSet(Cursor cursor) {

        int all = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COL_STAT_ALL_TASK));
        int deleted = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COL_STAT_DELETED_TASK));
        int finished = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COL_STAT_FINISHED_TASK));
        int actives = all - deleted;

        TextView sta1 = (TextView) findViewById(R.id.stat1);
        sta1.setText(String.valueOf(all));

        TextView sta2 = (TextView) findViewById(R.id.stat2);
        sta2.setText(String.valueOf(deleted));

        TextView sta3 = (TextView) findViewById(R.id.stat3);
        sta3.setText(String.valueOf(finished));

        TextView sta4 = (TextView) findViewById(R.id.stat4);
        sta4.setText(String.valueOf(actives));

        ArrayList<PieEntry> yEntries = new ArrayList<>();

        yEntries.add(new PieEntry(all));
        yEntries.add(new PieEntry(deleted));
        yEntries.add(new PieEntry(actives));
        yEntries.add(new PieEntry(finished));

        PieDataSet pieDataSet = new PieDataSet(yEntries, "statystyki");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);


        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.GRAY);
        colors.add(Color.GREEN);

        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

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
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_task) {
            Intent iinent = new Intent(StatisticActivity.this, MainActivity.class);
            startActivity(iinent);
        } else if (id == R.id.nav_note) {
            Intent iinent = new Intent(StatisticActivity.this, NoteActivity.class);
            startActivity(iinent);

        } else if (id == R.id.nav_stat) {

        } else if (id == R.id.nav_info) {
            Intent iinent = new Intent(StatisticActivity.this, InfoActivity.class);
            startActivity(iinent);
        } else if (id == R.id.nav_exit) {
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
