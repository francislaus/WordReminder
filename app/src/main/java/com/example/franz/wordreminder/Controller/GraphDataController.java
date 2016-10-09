package com.example.franz.wordreminder.Controller;

import android.content.Context;
import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

/**
 * Created by franz on 30.09.16.
 */

public class GraphDataController {

    //creates data for the graph

    private final static String LOG_TAG = GraphDataController.class.getSimpleName();

    private Context context;

    public GraphDataController(Context context){
        this.context = context;
    }


    //returns all the values for the last month
    public ArrayList<DataPoint> lastMonthValues(){
        //we have to find the right filename
        int month = getCurrentMonth();
        int year = getCurrentYear();
        if(month == 1){
            //it is Jan of the current year
            //so have to jump to december of the year before
            month = 12;
            year--;
        }
        String path = ""+month+year+".properties";
        return getValues(path);
    }

    //returns all the values for the this month
    public ArrayList<DataPoint> thisMonthValues(){
        int month = getCurrentMonth();
        int year = getCurrentYear();
        String path = ""+month+year+".properties";
        return getValues(path);
    }

    public void addNewWord(){
        //check for file
        String month = String.valueOf(getCurrentMonth());
        String year = String.valueOf(getCurrentYear());
        String path = ""+month+year+".properties";
        Log.e(LOG_TAG, "path: "+path);
        checkForExistence(path);
        //check for todays properties
        Properties properties = new Properties();
        loadProperties(properties, path);
        String key = String.valueOf(getCurrentDay());
        String stringValue = properties.getProperty(key);
        if(stringValue != null){
            //Log.e(LOG_TAG, "stringValue: "+stringValue);
            int value = Integer.valueOf(stringValue);
            //Log.e(LOG_TAG, "value: "+value);
            value++;
            //properties.remove(key);
            //value is incremented and new set
            properties.setProperty(key, String.valueOf(value));
        }
        else {
            //first time for this day
            properties.setProperty(key, "1");
        }
        //restore the properties
        storeProperties(properties, path);

    }

    public boolean lastAvailable(){
        //we have to find the right filename
        int month = getCurrentMonth();
        int year = getCurrentYear();
        if(month == 1){
            //it is Jan of the current year
            //so have to jump to december of the year before
            month = 12;
            year--;
        }
        String path = ""+month+year+".properties";
        return fileExists(path);
    }

    public boolean thisAvailable(){
        int month = getCurrentMonth();
        int year = getCurrentYear();
        String path = ""+month+year+".properties";
        return fileExists(path);
    }

    public int lastDays(){
        int month = (getCurrentMonth()-1)%13;
        return getDaysOfMonth(month);
    }

    public int thisDays(){
        int month = getCurrentMonth();
        return getDaysOfMonth(month);
    }

    private int getDays(int month){
        Calendar cal = Calendar.getInstance();
        return 0;
    }

    //method returns the current day
    private int getCurrentDay(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    //method returns the current month
    private int getCurrentMonth(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return 1 + cal.get(Calendar.MONTH);
    }

    //method returns the current year
    private int getCurrentYear(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    private void checkForExistence(String path){
        File file = new File(path);
        if(!fileExists(path)){
            //we have to create a new file
            Log.e(LOG_TAG, "file does not exist");
            Properties properties = new Properties();
            storeProperties(properties, path);
        }
    }

    private ArrayList<DataPoint> getValues(String path){
        File file = new File(path);
        if(fileExists(path)){
            Properties properties = new Properties();
            loadProperties(properties, path);
            ArrayList<DataPoint> list = new ArrayList<>();
            //not more than 31 days in a month
            for(int i = 1; i < 31; i++){
                String key = String.valueOf(i);
                String value = properties.getProperty(key);
                /*Log.e(LOG_TAG, "key: "+key);
                Log.e(LOG_TAG, "value: "+value);*/
                if(value != null){
                    list.add(new DataPoint(Double.valueOf(key), Double.valueOf(value)));
                    Log.e(LOG_TAG, "key: "+key);
                    Log.e(LOG_TAG, "value: "+value);
                }
                //otherwise this day will not be added
            }
            return list;
        }
        else{
            Log.e(LOG_TAG, "getValues file does not exist");
        }
        return null;
    }

    private void loadProperties(Properties properties, String path){
        //loads the properties in a unique way
        try{
            FileInputStream inputStream = context.openFileInput(path);
            properties.load(inputStream);
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void storeProperties(Properties properties, String path){
        //stores the properties in a unique way
        try{
            FileOutputStream outputStream = context.openFileOutput(path, Context.MODE_PRIVATE);
            properties.store(outputStream, "");
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private boolean fileExists(String path){
        try{
            FileInputStream inputStream = context.openFileInput(path);
            return true;
        }
        catch (IOException ioe){
            return false;
        }
    }

    private int getDaysOfMonth(int month){
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;

            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        //otherwise it is february

        //remember the leap year
        if(isLeapYear(getCurrentYear())) {
            //one day more than usually
            return 29;
        }
        else {
            return 28;
        }
    }

    private boolean isLeapYear(int year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        //it is a leap if there are more than 365 days in a year
        return (cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365);
    }

}
