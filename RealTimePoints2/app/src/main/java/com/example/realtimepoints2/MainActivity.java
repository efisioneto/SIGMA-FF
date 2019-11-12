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
    //Criando uma array de dados
    public LineGraphSeries<DataPoint>[] xySeries;

    private Button btnAddPt,btnRight,btnLeft,btnUpFloor,btnDownFloor;
    private ImageButton btnUp,btnDown;

    private EditText mX,mY;
    public double x,y;
    public double a,b,c,d,e,f,g,h,i,j;


    //Criando uma array de graficos
    public int n=0;
    public GraphView[] mScatterPlot;

    //make xyValue global
    private ArrayList<XYValue>[] xyValueArray;
    private ArrayList<XYValue>[] CorrectArray;
    private ArrayList<XYValue>[] NewArray;

    //Criando um inteiro que condicione a mudanca de andar
    int andar=0;
    int change=0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mScatterPlot= new  GraphView[100];


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

        try {
        mScatterPlot[n] = (GraphView) findViewById(R.id.scatterPlot);
        }catch (NullPointerException e){

            Log.e(TAG,"Deu ruim na array "+e.getMessage() );

        }

        xyValueArray= new ArrayList[100];
        CorrectArray= new ArrayList[100];
        NewArray= new ArrayList[100];


        xyValueArray[0] = new ArrayList<>();
        CorrectArray[0]= new ArrayList<>();
        NewArray[0]= new ArrayList<>();

        xyValueArray[1] = new ArrayList<>();
        CorrectArray[1]= new ArrayList<>();
        NewArray[1]= new ArrayList<>();

        //Declare Variables to change the floor
        btnUpFloor = (Button) findViewById(R.id.ChangeFloorUp);
        btnDownFloor = (Button) findViewById(R.id.ChangeFloorDown);


        init();


    }

    private void init (){

        //declare xySeries Object

        //xySeries = new PointsGraphSeries<>();
        //xySeries = new LineGraphSeries<>()[100];

        //Declarando as series
        xySeries= new LineGraphSeries[100];
        xySeries[0]= new LineGraphSeries<>();
        xySeries[1]= new LineGraphSeries<>();



//Subindo de andar
        btnUpFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View upFloor) {

                n=1;
                andar=1;
                Log.d(TAG,"Mudando de andar: " + n);
                mScatterPlot[n] = (GraphView) findViewById(R.id.scatterPlot);
                init();

            }
        });

//Descendo de andar
                btnDownFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View upFloor) {
                n=0;
                andar=1;
                Log.d(TAG,"Mudando de andar de andar: " + n);
                mScatterPlot[n] = (GraphView) findViewById(R.id.scatterPlot);
                init();

            }
        });




//Subindo no Grafico
      btnUp.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View up) {
              x=x;
              y=y+20;
if(andar==1){
    change=1;
    andar=0;

}
              Log.d(TAG,"Adicionadando os valores: " + x+ " + "+y);
              xyValueArray[n].add(new XYValue(x,y));
              NewArray[n].add(new XYValue(x,y));

              init();

          }
      });



        //Indo pra Direita no Grafico
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View right) {
                x=x+20;
                y=y;
                if(andar==1){
                    change=1;
                    andar=0;
                }
                Log.d(TAG,"Adicionadando os valores: " + x+ " + "+y);
                xyValueArray[n].add(new XYValue(x,y));
                NewArray[n].add(new XYValue(x,y));

                init();

            }
        });

        //Descendo no Grafico
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View down) {
                x=x;
                y=y-20;
                if(andar==1){
                    change=1;
                    andar=0;
                }
                Log.d(TAG,"Adicionadando os valores: " + x+ " + "+y);
                xyValueArray[n].add(new XYValue(x,y));
                NewArray[n].add(new XYValue(x,y));

                init();

            }
        });

        //Indo pra esquerda no Grafico
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View left) {
                x=x-20;
                y=y;
                if(andar==1){
                    change=1;
                    andar=0;
                }
                Log.d(TAG,"Adicionadando os valores: " + x+ " + "+y);
                xyValueArray[n].add(new XYValue(x,y));
                NewArray[n].add(new XYValue(x,y));

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
                 xyValueArray[n].add(new XYValue(x,y));
                 NewArray[n].add(new XYValue(x,y));

                 init();

                }else{

            toastMessage("Tem que preencher x e y");

                }



            }
        });

if (xyValueArray[n].size()!=0){


    a = NewArray[n].get(NewArray[n].size()-1).getX();
    b = NewArray[n].get(NewArray[n].size()-1).getY();

if(xyValueArray[n].size()>1) {

    c = NewArray[n].get(NewArray[n].size()-2).getX();
    d = NewArray[n].get(NewArray[n].size()-2).getY();


}
    if(xyValueArray[n].size()>2) {

        e = NewArray[n].get(NewArray[n].size()-3).getX();
        f = NewArray[n].get(NewArray[n].size()-3).getY();


    }
    if(xyValueArray[n].size()>3) {

        g = NewArray[n].get(NewArray[n].size()-4).getX();
        h = NewArray[n].get(NewArray[n].size()-4).getY();


    }
    if(xyValueArray[n].size()>5) {

        i = NewArray[n].get(NewArray[n].size()-5).getX();
        j = NewArray[n].get(NewArray[n].size()-5).getY();


    }




 createScatterPlot();


}else {

    Log.d(TAG, "nao ha dados");

}


    }

    private void createScatterPlot() {

        Log.d(TAG, "Fazendo o grafico");
        //Colocar a array na ordem

        CorrectArray[n]=sortArray(xyValueArray[n]);

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
        for(int i=0; i< CorrectArray[n].size();i++){

            try {
                double x =  CorrectArray[n].get(i).getX();
                double y =  CorrectArray[n].get(i).getY();
              if(change==0){

                  if(x==c&&y==d ||x==a&&y==b){
                    xySeries[n].appendData(new DataPoint(x,y),true,1000);
                  }
                }

                  else if(change==1) {


                      if(x==a&&y==b){
                          xySeries[n].appendData(new DataPoint(x,y),true,1000);

                        }


                      }




           }catch (IllegalArgumentException e){

                Log.e(TAG,"Deu ruim "+e.getMessage() );

            }catch (NullPointerException k){

                Log.e(TAG,"Deu ruim no dado com array "+k.getMessage() );

            }




        }
        if(change==1){
            change=0;
        }
        //set some properties

        // xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);


if (n==0){
    xySeries[n].setColor(Color.RED);
}else{
    xySeries[n].setColor(Color.BLUE);
}


      //  xySeries.setSize(10f);
        xySeries[n].setThickness(10);

        //make area under the graph
        xySeries[n].setDrawBackground(false);

        //Data points can be highlighted

        xySeries[n].setDrawDataPoints(true);




        //set Scrollable and Scaleable

        mScatterPlot[n].getViewport().setScalable(true);

        mScatterPlot[n].getViewport().setScalableY(true);

        mScatterPlot[n].getViewport().setScrollable(true);

        mScatterPlot[n].getViewport().setScrollableY(true);



        //set manual x bounds

        mScatterPlot[n].getViewport().setYAxisBoundsManual(true);

        mScatterPlot[n].getViewport().setMaxY(150);

        mScatterPlot[n].getViewport().setMinY(-150);



        //set manual y bounds

        mScatterPlot[n].getViewport().setXAxisBoundsManual(true);

        mScatterPlot[n].getViewport().setMaxX(150);

        mScatterPlot[n].getViewport().setMinX(-150);



        mScatterPlot[n].addSeries(xySeries[n]);



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
