package com.example.sebastian.mytodoapp;

import android.content.Intent;
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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{



//    private  float[] yData = {};
    private String[] xData = {"Ilość dodanych zadań", "Ilość aktualnyh zadań", "Ilość usuniętych zadań",
            "Ilość dodanych notatek", "Ilość usuniętych notatek", " "};
    PieChart pieChart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);





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
        pieChart.setCenterText("dupa");
        pieChart.setCenterTextSize(15);
        pieChart.setDrawEntryLabels(true);

        addDataSet();

    }


    private void addDataSet() {
        TextView sta1 = (TextView) findViewById(R.id.stat1);
        String s1 = sta1.getText().toString();
        int S1 = Integer.parseInt(s1);

        TextView sta2 = (TextView) findViewById(R.id.stat2);
        String s2 = sta2.getText().toString();
        int S2 = Integer.parseInt(s2);

        TextView sta3 = (TextView) findViewById(R.id.stat3);
        String s3 = sta3.getText().toString();
        int S3 = Integer.parseInt(s3);

        TextView sta4 = (TextView) findViewById(R.id.stat4);
        String s4 = sta4.getText().toString();
        int S4 = Integer.parseInt(s4);

        TextView sta5 = (TextView) findViewById(R.id.stat5);
        String s5 = sta5.getText().toString();
        int S5 = Integer.parseInt(s5);


        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();


        yEntries.add(new PieEntry(S1));
        yEntries.add(new PieEntry(S2));
        yEntries.add(new PieEntry(S3));
        yEntries.add(new PieEntry(S4));
        yEntries.add(new PieEntry(S5));

//        for(int i=0; i<yData.length; i++){
//            yEntries.add(new PieEntry(yData[i], i));
//        }

        for(int i=0; i<xData.length; i++){
            xEntries.add(xData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntries, "statystyki");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);


        ArrayList<Integer> colors = new ArrayList<>();;
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.GRAY);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);

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
            Intent iinent= new Intent(StatisticActivity.this,MainActivity.class);
            startActivity(iinent);
        } else if (id == R.id.nav_note) {
            Intent iinent= new Intent(StatisticActivity.this,NoteActivity.class);
            startActivity(iinent);

        } else if (id == R.id.nav_stat) {

        } else if (id == R.id.nav_info) {
            Intent iinent= new Intent(StatisticActivity.this,InfoActivity.class);
            startActivity(iinent);
        } else if (id == R.id.nav_exit) {
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
