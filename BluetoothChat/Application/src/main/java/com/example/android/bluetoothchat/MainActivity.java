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
    public static final String TAG = "MainActivity";

    //Determinate initial floor
    public int floor= 0;

    //Variables of run method
    private final Handler mHandler = new Handler();
    public Runnable mTimer;

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;


    //Variables that represent the last 2 points
    public double OriginalArrayX1,OriginalArrayY1,OriginalArrayX2,OriginalArrayY2;

    //choice Point ou line
    //PointsGraphSeries<DataPoint> xySeries;
    public LineGraphSeries<DataPoint>[] xySeries;

    // Separate the different data in different floors
    public GraphView[] mScatterPlot;


    // Create ArrayList of Original data e data will be sorted
    ArrayList<ArrayList<XYValue>> OriginalArray = new ArrayList<ArrayList<XYValue>>();
    ArrayList<XYValue> xyValueArray = new ArrayList<XYValue>();





    @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declare variables


        OriginalArray.add(new ArrayList<XYValue>());
        xyValueArray = new ArrayList<>();

        try {
            mScatterPlot= new GraphView[100];
            mScatterPlot[floor] = (GraphView) findViewById(R.id.graph);
        }catch (NullPointerException e){

            android.util.Log.e(TAG,"Its wrong "+e.getMessage() );
        }

        mTimer = new Runnable(){
        //variables that do not allow data to be repeated
         double PreviousDataX= 5d;
         double PreviousDataY= 5d;


            @Override
            public void run()
            {
                xySeries= new LineGraphSeries[100];
                xySeries[floor]= new LineGraphSeries<>();
                try{

                    BluetoothChatFragment.Decimal Xvalue= new BluetoothChatFragment.Decimal();

                    BluetoothChatFragment.Decimal Yvalue= new BluetoothChatFragment.Decimal();
                    //A undidade enviada eh em dm
                    double NextDataX= Xvalue.ValorX;
                    double NextDataY= Yvalue.ValorY;

                    //Data does not repeat if same as above
                    if(NextDataX !=PreviousDataX || NextDataY !=PreviousDataY ){

                        PreviousDataX= NextDataX;
                        PreviousDataY= NextDataY;
                       OriginalArray.get(floor).add(new XYValue(NextDataX,NextDataY));

                        if (OriginalArray.get(floor).size()!=0){

                            OriginalArrayX1 = OriginalArray.get(floor).get(OriginalArray.get(floor).size()-1).getX();
                            OriginalArrayY1 = OriginalArray.get(floor).get(OriginalArray.get(floor).size()-1).getY();

                            if(OriginalArray.get(floor).size()>1) {

                                OriginalArrayX2 = OriginalArray.get(floor).get(OriginalArray.get(floor).size()-2).getX();
                                OriginalArrayY2 = OriginalArray.get(floor).get(OriginalArray.get(floor).size()-2).getY();

                            }
                               //Create graph
                               createScatterPlot();

                        }else{
                            Log.d(TAG, "No data plot");
                        }

                    }



                }catch (NullPointerException ignored){


                }

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

    private void createScatterPlot() {

        android.util.Log.d(TAG, "Fazendo o grafico");


//        for(int i=0; i<OriginalArray[n].size();i++){
//
//           double x= OriginalArray[n].get(i).getX();
//           double y =OriginalArray[n].get(i).getY();
//           xyValueArray[n].set(i, new XYValue(x,y));
//
//        }

        //    xyValueArray[n] = OriginalArray[n];


        xyValueArray.clear();


        for (int i = 0; i < OriginalArray.get(floor).size(); i++) {

            double x = OriginalArray.get(floor).get(i).getX();
            double y = OriginalArray.get(floor).get(i).getY();
            xyValueArray.add(new XYValue(x, y));

        }


        //Colocar a array na ordem

        sortArray(xyValueArray);

        System.out.println("OriginalArray:");
        for (int i = 0; i < OriginalArray.size(); i++) {
            for (int j = 0; j < OriginalArray.get(i).size(); j++) {

                final double x = OriginalArray.get(i).get(j).getX();
                final double y = OriginalArray.get(i).get(j).getY();

                // System.out.print(aLists.get(i).get(j) + " ");

                System.out.print("(" + x + ", " + y + ") ");
            }
            System.out.println();
        }

        System.out.println("xyValueArray:");

        for (int i = 0; i < xyValueArray.size(); i++) {


            final double x = xyValueArray.get(i).getX();
            final double y = xyValueArray.get(i).getY();

            // System.out.print(aLists.get(i).get(j) + " ");

            System.out.print("(" + x + ", " + y + ") ");

        }
        System.out.println();


        //Colocar a array na ordem
        //xyValueArray[n]=sortArray(xyValueArray[n]);

//        if (xyValueArray[n] == OriginalArray[n]) {
//
//            Log.d(TAG, "estranho2");
//
//        }else{
//            Log.d(TAG, "Confirmei minha ideia");
//        }

        for(int i=0; i< xyValueArray.size();i++){

            try {
                double x =  xyValueArray.get(i).getX();
                double y =  xyValueArray.get(i).getY();

                //Plotar apenas mais um ponto caso mude de andar


                    if(x==OriginalArrayX1&&y==OriginalArrayY1){


                        xySeries[floor].appendData(new DataPoint(x,y),true,1000);

                    }


                //Ligar dois pontos caso o andar seja o mesmo
                else  {

                    if(x==OriginalArrayX1&&y==OriginalArrayY1||x==OriginalArrayX2&&y==OriginalArrayY2){
                        xySeries[floor].appendData(new DataPoint(x,y),true,1000);

                        android.util.Log.d(TAG, "New Data: "+ x+ " "+ y);
                    }

                }


            }catch (IllegalArgumentException e){

                android.util.Log.e(TAG,"Deu ruim "+e.getMessage() );

            }catch (NullPointerException k){

                android.util.Log.e(TAG,"Deu ruim no dado com array "+k.getMessage() );

            }

        }



        //set some properties

        // xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);


        initGraph();

    }

    private void initGraph() {

   //     android.util.Log.d(TAG, "Plotting data: ");

        xySeries[floor].setColor(Color.RED);

       //xySeries[floor].setSize(10f);// if there is Data Point

        xySeries[floor].setThickness(10);//if there is Data Series

        //set Scrollable and Scaleable

        mScatterPlot[floor].getViewport().setScalable(true);

        mScatterPlot[floor].getViewport().setScalableY(true);

        mScatterPlot[floor].getViewport().setScrollable(true);

        mScatterPlot[floor].getViewport().setScrollableY(true);


        //set manual x bounds

        mScatterPlot[floor].getViewport().setYAxisBoundsManual(true);

        // Maximo aceitavel para o grafico ficar bom
        mScatterPlot[floor].getViewport().setMaxY(40);

        mScatterPlot[floor].getViewport().setMinY(-40);

        //set manual y bounds

        mScatterPlot[floor].getViewport().setXAxisBoundsManual(true);

        mScatterPlot[floor].getViewport().setMaxX(40);

        mScatterPlot[floor].getViewport().setMinX(-40);

        mScatterPlot[floor].addSeries(xySeries[floor]);

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

   //     android.util.Log.d(TAG, "sortArray: Sorting the XYArray.");



        while(true){

            m--;

            if(m <= 0){

                m = array.size() - 1;

            }

         //   android.util.Log.d(TAG, "sortArray: m = " + m);

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

             //       android.util.Log.d(TAG, "sortArray: count = " + count);

                }


//CHANGE HERE
                else if(array.get(m).getX() > array.get(m-1).getX()){

                    count++;

         //           android.util.Log.d(TAG, "sortArray: count = " + count);

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
