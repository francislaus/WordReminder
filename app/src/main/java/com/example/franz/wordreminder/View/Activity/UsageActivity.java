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

    //has to be > 1
    private static final int MAX_HEIGHT = 100;

    //values for the colors
    //depending on the MAX_HEIGHT value
    private static final int GREEN_BORDER = MAX_HEIGHT/4;
    private static final int BLUE_BORDER = 2 * GREEN_BORDER;
    private static final int RED_BORDER = 3 * GREEN_BORDER;

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
        setSettings(31);
        buttonThis.callOnClick();
    }

    private void setSettings(int MaxX){
        Viewport viewport = graph.getViewport();
        viewport.setXAxisBoundsManual(true);
        viewport.setYAxisBoundsManual(true);
        viewport.setMaxX(MaxX);
        viewport.setMinX(1);
        viewport.setMaxY(MAX_HEIGHT);
        viewport.setMinY(0);
    }


    public void onLast(View v){
        if(dataController.lastAvailable()) {
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
            /*for (int i = 0; i < dataPoints.length; i++) {
                dataPoints[i] = points.get(i);
                Log.e(LOG_TAG, "dataPoint: " + dataPoints[i]);
            }*/
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
                if(data.getY() < GREEN_BORDER){
                    return Color.GREEN;
                }
                else if(data.getY() < BLUE_BORDER){
                    return Color.BLUE;
                }
                else if(data.getY() < RED_BORDER){
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
