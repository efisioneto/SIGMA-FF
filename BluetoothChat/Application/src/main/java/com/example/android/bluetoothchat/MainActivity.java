/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.example.android.bluetoothchat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ViewAnimator;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;
import com.example.android.common.logger.LogWrapper;
import com.example.android.common.logger.MessageOnlyLogFilter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase {
    public LineGraphSeries<DataPoint> Series;
    public static final String TAG = "MainActivity";

    public int graphLastXValue = 0;
    private final Handler mHandler = new Handler();
    // private LineGraphSeries<DataPoint> series= new LineGraphSeries<DataPoint>();
    public Runnable mTimer;
    public double yvalue = 5d;

    public LineGraphSeries<DataPoint> series= new LineGraphSeries<DataPoint>();

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    //Parte nova

    public double a,b,c,d;

   // public ArrayList<XYValue> xyValueArray;
    //choice Point ou line
    //PointsGraphSeries<DataPoint> xySeries;
    LineGraphSeries<DataPoint> xySeries;

    GraphView mScatterPlot;

    //make xyValue global

    private ArrayList<XYValue> xyValueArray;
    private ArrayList<XYValue> OriginalArray;




    @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BluetoothChatFragment grafico = new BluetoothChatFragment();

        setContentView(R.layout.activity_main);

        //Declarar variaveis

        mScatterPlot = (GraphView) findViewById(R.id.graph);
        xyValueArray = new ArrayList<>();

        //Parte antiga
        //graph = (GraphView) findViewById(R.id.graph);
        //grafico.initGraph(graph);
        //BluetoothChatFragment k = new BluetoothChatFragment();
         xySeries = new LineGraphSeries<>();
         init();

        mTimer = new Runnable(){
         double x2= 5d;
         double y2= 5d;


            @Override
            public void run()
            {




                try{

                    BluetoothChatFragment.Decimal Xvalue= new BluetoothChatFragment.Decimal();

                    BluetoothChatFragment.Decimal Yvalue= new BluetoothChatFragment.Decimal();

                    double x1= Xvalue.ValorX;
                    double y1= Yvalue.ValorY;

                    if(x1 !=x2 || y1 !=y2 ){

                        x2= x1;
                        y2= y1;
                        xyValueArray.add(new XYValue(x2,y2));
                        OriginalArray.add(new XYValue(x2,y2));

                        if(xyValueArray.size()!=0 && xyValueArray!=null){

                            a = OriginalArray.get(OriginalArray.size()-1).getX();
                            b = OriginalArray.get(OriginalArray.size()-1).getY();

                            if(xyValueArray.size()>1) {

                                c = OriginalArray.get(OriginalArray.size()-2).getX();
                                d = OriginalArray.get(OriginalArray.size()-2).getY();

                            }

                                createScatterPlot();

                        }else{
                            Log.d(TAG, "No data plot");
                        }

                    }



                }catch (NullPointerException ignored){



                }




           //     graphLastXValue += 1.0;



              mHandler.postDelayed(this, 330);
            }


        };


      mHandler.postDelayed(mTimer, 1500);





        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothChatFragment fragment = new BluetoothChatFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
        }

    private void init (){






    }

    public void createScatterPlot() {
        android.util.Log.d(TAG, "Fazendo o grafico");
        //Colocar a array na ordem

        xySeries.resetData(new DataPoint[] {});
        xyValueArray=sortArray(xyValueArray);



        for(int i=0; i<xyValueArray.size();i++){

            try {

                double x = xyValueArray.get(i).getX();
                double y = xyValueArray.get(i).getY();

                if(x==c&&y==d ||x==a&&y==b){
                    xySeries.appendData(new DataPoint(x,y),true,1000);

                }


//                xySeries.appendData(new DataPoint(x,y),true,1000);

            }catch (IllegalArgumentException e){

                Log.e(TAG,"Deu ruim "+e.getMessage() );

            }



}


        //Preencher a area abaixo

        //xySeries.setDrawingCacheBackgroundColor(true);

        xySeries.setColor(Color.RED);

       // xySeries.setSize(10f);
        xySeries.setThickness(10);


        //set Scrollable and Scaleable

        mScatterPlot.getViewport().setScalable(true);

        mScatterPlot.getViewport().setScalableY(true);

        mScatterPlot.getViewport().setScrollable(true);

        mScatterPlot.getViewport().setScrollableY(true);



        //set manual y bounds

        mScatterPlot.getViewport().setYAxisBoundsManual(true);

        mScatterPlot.getViewport().setMaxY(2000000);

        mScatterPlot.getViewport().setMinY(0);



        //set manual x bounds

        mScatterPlot.getViewport().setXAxisBoundsManual(true);

        mScatterPlot.getViewport().setMaxX(2000000);

        mScatterPlot.getViewport().setMinX(0);

        //make area under the graph
        xySeries.setDrawBackground(false);

        //Data points can be highlighted

        xySeries.setDrawDataPoints(true);


        mScatterPlot.addSeries(xySeries);







    }

    //#graphView
    double mLastRandom = 2;
    // String sharedFact = mDataField.getText().toString();
    //double yvalue = Double.parseDouble(data);
    Random mRand = new Random();
    private double getRandom()
    {
        mLastRandom += mRand.nextDouble()*0.05 - 0.05;
        return mLastRandom ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                @SuppressLint("WrongViewCast") ViewAnimator output = findViewById(R.id.sample_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Create a chain of targets that will receive log data
     */
    @Override
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
       // LogFragment logFragment = (LogFragment) getSupportFragmentManager()
        //  msgFilter.setNext(logFragment.getLogView());

       // Log.i(TAG, "Ready");
    }

    /**

     * Sorts an ArrayList<XYValue> with respect to the x values.

     * @param array

     * @return

     */

    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array){

        /*

        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>

         */

        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));

        int m = array.size()-1;

        int count = 0;

        android.util.Log.d(TAG, "sortArray: Sorting the XYArray.");



        while(true){

            m--;

            if(m <= 0){

                m = array.size() - 1;

            }

            android.util.Log.d(TAG, "sortArray: m = " + m);

            try{

                //print out the y entrys so we know what the order looks like

                //Log.d(TAG, "sortArray: Order:");

                //for(int n = 0;n < array.size();n++){

                //Log.d(TAG, "sortArray: " + array.get(n).getY());

                //}

                double tempY = array.get(m-1).getY();

                double tempX = array.get(m-1).getX();

                if(tempX > array.get(m).getX() ){

                    array.get(m-1).setY(array.get(m).getY());

                    array.get(m).setY(tempY);

                    array.get(m-1).setX(array.get(m).getX());

                    array.get(m).setX(tempX);

                }

                else if(tempY == array.get(m).getY()){

                    count++;

                    android.util.Log.d(TAG, "sortArray: count = " + count);

                }



                else if(array.get(m).getX() > array.get(m-1).getX()){

                    count++;

                    android.util.Log.d(TAG, "sortArray: count = " + count);

                }

                //break when factorial is done

                if(count == factor ){

                    break;

                }

            }catch(ArrayIndexOutOfBoundsException e){

                android.util.Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +

                        e.getMessage());

                break;

            }

        }

        return array;

    }

    private void toastMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }

}
