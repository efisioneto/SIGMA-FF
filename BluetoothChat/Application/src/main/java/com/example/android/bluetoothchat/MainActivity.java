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

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
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
    int i;
    public static final String TAG = "MainActivity";

    public double graphLastXValue = 5d;
    private final Handler mHandler = new Handler();
    // private LineGraphSeries<DataPoint> series= new LineGraphSeries<DataPoint>();
    public Runnable mTimer;
    public double yvalue = 5d;

    public LineGraphSeries<DataPoint> series= new LineGraphSeries<DataPoint>();;

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    //Parte nova
    private ArrayList<XYValue> xyValueArray;
    PointsGraphSeries<DataPoint> xySeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BluetoothChatFragment grafico = new BluetoothChatFragment();

        setContentView(R.layout.activity_main);
       final GraphView graph = (GraphView) findViewById(R.id.graph);

       grafico.initGraph(graph);

        BluetoothChatFragment k = new BluetoothChatFragment();



//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
//
//             //  new DataPoint(0, 1),
//             // new DataPoint(1, 5),
//              //  new DataPoint(2, 3),
//
//       });
//
//
//
//        series.appendData(new DataPoint(0, 1),
//                true, 22);
//        series.appendData(new DataPoint(1, 5),
//                true, 22);
//        series.appendData(new DataPoint(2, 3),
//                true, 22);
//        series.appendData(new DataPoint(20, 20),
//                true, 22);
//
//
//
//       graph.addSeries(series);
//        series.setColor(Color.RED);
//        series.setDrawDataPoints(true);
//        series.setDrawBackground(true);
//
//        graph.getViewport().setMinX(0);
//        graph.getViewport().setMaxX(50);
//
//        graph.getViewport().setYAxisBoundsManual(true); // These lines seem to be causing it
//        graph.getViewport().setMinY(0);
//        graph.getViewport().setMaxY(50.0);
//
//        graph.getViewport().setScrollable(true); // enables horizontal scrolling
//
//        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
//
//
//        //graph.getGridLabelRenderer().setLabelVerticalWidth(100);
//
//        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
//
//        gridLabel.setHorizontalAxisTitle("x axis");
//
//        gridLabel.setVerticalAxisTitle("y axis");


        final ArrayList<XYValue>[] xyValueArray = new ArrayList[]{new ArrayList<>()};



//        BluetoothChatFragment.Decimal Xvalue= new BluetoothChatFragment.Decimal();
//
//        BluetoothChatFragment.Decimal Yvalue= new BluetoothChatFragment.Decimal();
//
//        xyValueArray.add(new XYValue(Xvalue.ValorX,Yvalue.ValorY));
//
//        xyValueArray= sortArray(xyValueArray);




        mTimer = new Runnable()
        {
            @Override
            public void run()
            {

                graphLastXValue += 1.0;

                try{

//                                mSeries.appendData(new DataPoint(0, 10),
//                                        true, 22);




                    BluetoothChatFragment.Decimal Xvalue= new BluetoothChatFragment.Decimal();

                    BluetoothChatFragment.Decimal Yvalue= new BluetoothChatFragment.Decimal();

//                    series.appendData(new DataPoint( Xvalue.ValorX,Yvalue.ValorY),
//                            true, 100);


                    xyValueArray[0].add(new XYValue(Xvalue.ValorX,Yvalue.ValorY));

//                    series.appendData(new DataPoint(Xvalue.ValorX,Yvalue.ValorY),
//
//                            true, 100);
//
//                    graph.removeAllSeries();
//
//                    graph.addSeries(series);

                }catch (NullPointerException ignored){

                }



                //sort it in ASC order
                xyValueArray[0] = sortArray(xyValueArray[0]);

                for(int i = 0; i< xyValueArray[0].size(); i++){

                    double x = xyValueArray[0].get(i).getX();
                    double y = xyValueArray[0].get(i).getY();
                    series.appendData(new DataPoint(-x,y),true,1000);

                }

                // mHandler.postDelayed(this, 330);
            }


        };


        mHandler.postDelayed(mTimer, 1500);

        try{

        }catch (NullPointerException ignored){

        }



        graph.addSeries(series);


        series.setColor(Color.RED);
        series.setDrawDataPoints(true);
        series.setDrawBackground(true);


//        graph.getViewport().setMinX(0);
//        graph.getViewport().setMaxX(10000);
//
//        graph.getViewport().setYAxisBoundsManual(true); // These lines seem to be causing it
//        graph.getViewport().setXAxisBoundsManual(true); // These lines seem to be causing it
//        graph.getViewport().setMinY(0);
//        graph.getViewport().setMaxY(10000);

//        graph.getViewport().setScrollable(true); // enables horizontal scrolling
//
//        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling


      //  graph.getGridLabelRenderer().setLabelVerticalWidth(0);

//        gridLabel.setHorizontalAxisTitle("x axis");
//
//        gridLabel.setVerticalAxisTitle("y axis");


        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothChatFragment fragment = new BluetoothChatFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
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
                ViewAnimator output = findViewById(R.id.sample_output);
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
}
