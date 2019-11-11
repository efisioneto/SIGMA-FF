package com.example.realtimepoints2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //PointsGraphSeries<DataPoint> xySeries;

    LineGraphSeries<DataPoint> xySeries;

    private Button btnAddPt,btnRight,btnLeft;
    private ImageButton btnUp,btnDown;

    private EditText mX,mY;
    public double x,y;
    public double a,b,c,d,e,f,g,h,i,j;

    GraphView mScatterPlot;

    //make xyValue global
    private ArrayList<XYValue> xyValueArray;
    private ArrayList<XYValue> CorrectArray;
    private ArrayList<XYValue> NewArray;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declare variables in onCreate

        btnUp = (ImageButton) findViewById(R.id.ArrowUp);
        btnDown = (ImageButton) findViewById(R.id.ArrowDown);
        btnRight= (Button) findViewById(R.id.ArrowRight);
        btnLeft = (Button) findViewById(R.id.ArrowLeft);
        btnAddPt = (Button) findViewById(R.id.btnAddPt);
        mX = (EditText) findViewById(R.id.numX);
        mY = (EditText) findViewById(R.id.numY);
        mScatterPlot = (GraphView) findViewById(R.id.scatterPlot);
        xyValueArray = new ArrayList<>();
        CorrectArray= new ArrayList<>();
        NewArray= new ArrayList<>();


        init();


    }

    private void init (){

        //declare xySeries Object

        //xySeries = new PointsGraphSeries<>();
        xySeries = new LineGraphSeries<>();

//Subindo no Grafico
      btnUp.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View up) {
              x=x;
              y=y+10;

              Log.d(TAG,"Adicionadando os valore: " + x+ " + "+y);
              xyValueArray.add(new XYValue(x,y));
              NewArray.add(new XYValue(x,y));

              init();

          }
      });

        //Indo pra Direita no Grafico
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View right) {
                x=x+10;
                y=y;

                Log.d(TAG,"Adicionadando os valore: " + x+ " + "+y);
                xyValueArray.add(new XYValue(x,y));
                NewArray.add(new XYValue(x,y));

                init();

            }
        });

        //Descendo no Grafico
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View down) {
                x=x;
                y=y-10;

                Log.d(TAG,"Adicionadando os valore: " + x+ " + "+y);
                xyValueArray.add(new XYValue(x,y));
                NewArray.add(new XYValue(x,y));

                init();

            }
        });

        //Indo pra esquerda no Grafico
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View left) {
                x=x-10;
                y=y;

                Log.d(TAG,"Adicionadando os valore: " + x+ " + "+y);
                xyValueArray.add(new XYValue(x,y));
                NewArray.add(new XYValue(x,y));

                init();

            }
        });

        btnAddPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mX.getText().toString().equals("")&&!mY.getText().toString().equals("")){

                x= Double.parseDouble(mX.getText().toString());
                y= Double.parseDouble(mY.getText().toString());

                 Log.d(TAG,"Adicionadando os valore: " + x+ " + "+y);
                 xyValueArray.add(new XYValue(x,y));
                 NewArray.add(new XYValue(x,y));

                 init();

                }else{

            toastMessage("Tem que preencher x e y");

                }



            }
        });

if (xyValueArray.size()!=0){


    a = NewArray.get(NewArray.size()-1).getX();
    b = NewArray.get(NewArray.size()-1).getY();

if(xyValueArray.size()>1) {

    c = NewArray.get(NewArray.size()-2).getX();
    d = NewArray.get(NewArray.size()-2).getY();


}
    if(xyValueArray.size()>2) {

        e = NewArray.get(NewArray.size()-3).getX();
        f = NewArray.get(NewArray.size()-3).getY();


    }
    if(xyValueArray.size()>3) {

        g = NewArray.get(NewArray.size()-4).getX();
        h = NewArray.get(NewArray.size()-4).getY();


    }
    if(xyValueArray.size()>5) {

        i = NewArray.get(NewArray.size()-5).getX();
        j = NewArray.get(NewArray.size()-5).getY();


    }




 createScatterPlot();

}else {

    Log.d(TAG, "nao ha dados");

}


    }

    private void createScatterPlot() {

        Log.d(TAG, "Fazendo o grafico");
        //Colocar a array na ordem

        CorrectArray=sortArray(xyValueArray);

        //adicionando dados

        //for(int i=0; i<xyValueArray.size();i++){
//        try {
//
//        xySeries.appendData(new DataPoint(a,b),true,1000);
//
//        }catch (IllegalArgumentException e){
//            Log.e(TAG,"Deu ruim "+e.getMessage() );
//
//
//        }
        for(int i=0; i< CorrectArray.size();i++){

            try {
                double x =  CorrectArray.get(i).getX();
                double y =  CorrectArray.get(i).getY();

                if(x==c&&y==d ||x==a&&y==b){
                    xySeries.appendData(new DataPoint(x,y),true,1000);

                }


           }catch (IllegalArgumentException e){

                Log.e(TAG,"Deu ruim "+e.getMessage() );

            }

        }

        //set some properties

        // xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);

        xySeries.setColor(Color.RED);

      //  xySeries.setSize(10f);
        xySeries.setThickness(10);

        //make area under the graph
        xySeries.setDrawBackground(false);

        //Data points can be highlighted

        xySeries.setDrawDataPoints(true);




        //set Scrollable and Scaleable

        mScatterPlot.getViewport().setScalable(true);

        mScatterPlot.getViewport().setScalableY(true);

        mScatterPlot.getViewport().setScrollable(true);

        mScatterPlot.getViewport().setScrollableY(true);



        //set manual x bounds

        mScatterPlot.getViewport().setYAxisBoundsManual(true);

        mScatterPlot.getViewport().setMaxY(150);

        mScatterPlot.getViewport().setMinY(-150);



        //set manual y bounds

        mScatterPlot.getViewport().setXAxisBoundsManual(true);

        mScatterPlot.getViewport().setMaxX(150);

        mScatterPlot.getViewport().setMinX(-150);



        mScatterPlot.addSeries(xySeries);



    }


    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array){

        /*

        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>

         */

        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));

        int m = array.size()-1;

        int count = 0;

        Log.d(TAG, "sortArray: Sorting the XYArray.");



        while(true){

            m--;

            if(m <= 0){

                m = array.size() - 1;

            }

            Log.d(TAG, "sortArray: m = " + m);

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

                    Log.d(TAG, "sortArray: count = " + count);

                }



                else if(array.get(m).getX() > array.get(m-1).getX()){

                    count++;

                    Log.d(TAG, "sortArray: count = " + count);

                }

                //break when factorial is done

                if(count == factor ){

                    break;

                }

            }catch(ArrayIndexOutOfBoundsException e){

                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +

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
