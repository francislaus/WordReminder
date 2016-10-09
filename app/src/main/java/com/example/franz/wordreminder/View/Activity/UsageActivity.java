package com.example.franz.wordreminder.View.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.franz.wordreminder.Controller.GraphDataController;
import com.example.franz.wordreminder.MainActivity;
import com.example.franz.wordreminder.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Collection;

public class UsageActivity extends AbstractActivity {

    private static final String LOG_TAG = UsageActivity.class.getSimpleName();

    private GraphView graph;
    private Button buttonLast;
    private Button buttonThis;
    private GraphDataController dataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage);

        //init the graph
        graph = (GraphView) findViewById(R.id.activity_usage_graphview);
        buttonLast = (Button) findViewById(R.id.activity_usage_button_last);
        buttonThis = (Button) findViewById(R.id.activity_usage_button_this);
        dataController = new GraphDataController(getApplicationContext());
        //setup the buttons
        buttonThis.callOnClick();
    }

    private void setSettings(int MaxX){
        Viewport viewport = graph.getViewport();
        viewport.setMaxX(MaxX);
        viewport.setMinX(1);
        viewport.setMaxY(50);
        viewport.setMinY(0);
        /*StaticLabelsFormatter formatter = new StaticLabelsFormatter(graph);
        String[] strings = new String[5];
        strings[0] = "0";
        strings[4] = String.valueOf(MaxX);
        for(int i = 1 ; i < 4; i++){

        }*/
    }


    public void onLast(View v){
        if(dataController.lastAvailable()) {
            Log.e(LOG_TAG, "onLast");
            buttonLast.setEnabled(false);
            buttonThis.setEnabled(true);
            //we must get the actual data
            ArrayList<DataPoint> points = dataController.lastMonthValues();
            DataPoint[] dataPoints = new DataPoint[points.size()];
            for (int i = 0; i < dataPoints.length; i++) {
                dataPoints[i] = points.get(i);
            }
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
            setBarColor(series);
            //setSettings(dataController.lastDays());
            graph.removeAllSeries();
            graph.addSeries(series);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "not available", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onThis(View v){
        if(dataController.thisAvailable()) {
            buttonThis.setEnabled(false);
            buttonLast.setEnabled(true);
            //we must get the actual data
            ArrayList<DataPoint> points = dataController.thisMonthValues();
            DataPoint[] dataPoints = new DataPoint[points.size()];
            for (int i = 0; i < dataPoints.length; i++) {
                dataPoints[i] = points.get(i);
                Log.e(LOG_TAG, "dataPoint: " + dataPoints[i]);
            }
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
            series.setSpacing(4);
            setBarColor(series);
            setSettings(dataController.thisDays());
            //remove unnecessary other series
            graph.removeAllSeries();
            graph.addSeries(series);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "not available", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void setBarColor(BarGraphSeries<DataPoint> series){
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if(data.getY() < 25){
                    return Color.GREEN;
                }
                else if(data.getY() < 50){
                    return Color.BLUE;
                }
                else if(data.getY() < 75){
                    return Color.RED;
                }
                else{
                    return Color.YELLOW;
                }
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        //we have to switch the activity!
        switchActivity(MainActivity.class);
    }

}
