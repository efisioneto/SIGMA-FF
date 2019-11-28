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

    //LineGraphSeries<DataPoint> xySeries = new LineGraphSeries<DataPoint>();

  //  ArrayList<ArrayList<LineGraphSeries<DataPoint>>> xySeries = new ArrayList<ArrayList<LineGraphSeries<DataPoint>>>();

    //Botoes que serao utilizados
    private Button btnAddPt,btnRight,btnLeft,btnUpFloor,btnDownFloor;
    //Imagem que sera usada
    private ImageButton btnUp,btnDown;
    //variaveis usadas para pegar os numeros que o usuario escreve
    private EditText mX,mY;
    //Variaveis usadas para comparar valores
    public double x,y;
    public double NewArrayX1,NewArrayY1,NewArrayX2,NewArrayY2;
    //Criando uma array de graficos
    //Representa o andar
    public int n=0;

    //ArrayList<GraphView> mScatterPlot = new ArrayList<GraphView>();
    public GraphView[] mScatterPlot;
    //make xyValue global
 // private ArrayList<XYValue>[] xyValueArray;
// private ArrayList<XYValue>[] CorrectArray;
//private ArrayList<XYValue>[] NewArray;
    //Criando um booleano que condicione a mudanca de andar
    boolean ChangeFloor=false;

    //Here aList is an ArrayList of ArrayLists
    //ArrayList<ArrayList<Integer> > aLists= new ArrayList<ArrayList<Integer>();

   //Teste de novo programa

    ArrayList<ArrayList<XYValue>> NewArray = new ArrayList<ArrayList<XYValue>>();
    ArrayList<XYValue> xyValueArray = new ArrayList<XYValue>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mScatterPlot= new GraphView[100];


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



//       xyValueArray= new ArrayList[100];
//      //  CorrectArray= new ArrayList[100];
//        NewArray= new ArrayList[100];
//
//
//       xyValueArray[0] = new ArrayList<>(100);
//      //  CorrectArray[0]= new ArrayList<>(100);
//       NewArray[0]= new ArrayList<>(100);
//
//        xyValueArray[1] = new ArrayList<>();
//      // CorrectArray[1]= new ArrayList<>();
//        NewArray[1]= new ArrayList<>();

        //Create values for OriginalFloor1
        ArrayList<XYValue> OriginalFloor1 = new ArrayList<>();
        NewArray.add(new ArrayList<XYValue>());
        //Create values for OriginalFloor2
        //ArrayList<XYValue> OriginalFloor2 = new ArrayList<>();
        //NewArray.add(new ArrayList<XYValue>());

        //Create values for Floor1
        //ArrayList<XYValue> Floor1 = new ArrayList<>();
       // xyValueArray.add(new ArrayList<XYValue>());
        //Create values for Floor2
        //ArrayList<XYValue> Floor2 = new ArrayList<>();
      //  xyValueArray.add(new ArrayList<XYValue>());


        //Declare Variables to change the floor
        btnUpFloor = (Button) findViewById(R.id.ChangeFloorUp);
        btnDownFloor = (Button) findViewById(R.id.ChangeFloorDown);

//        xySeries.add(new ArrayList<LineGraphSeries<DataPoint>>());
//        xySeries.add(new ArrayList<LineGraphSeries<DataPoint>>());

        //xySeries.add(new LineGraphSeries<DataPoint>());
        //xySeries.add(new LineGraphSeries<DataPoint>());


        init();


    }

    private void init (){

        //declare xySeries Object

        //xySeries = new PointsGraphSeries<>();
        //xySeries = new LineGraphSeries<>()[100];

        //Declarando as series
        xySeries= new LineGraphSeries[100];
       xySeries[n]= new LineGraphSeries<>();
        //xySeries[1]= new LineGraphSeries<>();



//Subindo de andar
        btnUpFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View upFloor) {
                mScatterPlot[n].removeAllSeries();
                ChangeFloor=true;
                n+=1;
                Log.d(TAG,"Mudando de andar: " + n);
                mScatterPlot[n] = (GraphView) findViewById(R.id.scatterPlot);
                if (n>NewArray.size()-1){
                    NewArray.add(new ArrayList<XYValue>());
                    xySeries[n] = new LineGraphSeries<>() ;
                    createScatterPlot1();
                }
                else {
                    xySeries[n] = new LineGraphSeries<>() ;
                    createScatterPlot2();
                }
            }
        });

//Descendo de andar

        btnDownFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View DownFloor) {
                mScatterPlot[n].removeAllSeries();
                ChangeFloor=true;
                if(n!=0){
                    n-=1;
                }
                Log.d(TAG,"Mudando de andar de andar: " + n);
                mScatterPlot[n] = (GraphView) findViewById(R.id.scatterPlot);
                xySeries[n]=new LineGraphSeries<>();
               createScatterPlot2();
            }
        });




//Subindo no Grafico
      btnUp.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View up) {
              x=x;
              y=y+20;

              Log.d(TAG,"Adicionadando os valores: " + x+ " + "+y);
     //   xyValueArray[n].add(new XYValue(x,y));
              NewArray.get(n).add(new XYValue(x,y));



              init();

          }
      });



        //Indo pra Direita no Grafico
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View right) {
                x=x+20;
                y=y;
                Log.d(TAG,"Adicionadando os valores: " + x+ " + "+y);
   //    xyValueArray[n].add(new XYValue(x,y));
                NewArray.get(n).add(new XYValue(x,y));

                init();

            }
        });

        //Descendo no Grafico
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View down) {
                x=x;
                y=y-20;
                Log.d(TAG,"Adicionadando os valores: " + x+ " + "+y);
//              xyValueArray[n].add(new XYValue(x,y));
                NewArray.get(n).add(new XYValue(x,y));

                init();

            }
        });

        //Indo pra esquerda no Grafico
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View left) {
                x=x-20;
                y=y;
                Log.d(TAG,"Adicionadando os valores: " + x+ " + "+y);
   //       xyValueArray[n].add(new XYValue(x,y));
                NewArray.get(n).add(new XYValue(x,y));

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
    //          xyValueArray[n].add(new XYValue(x,y));
                    NewArray.get(n).add(new XYValue(x,y));

                 init();

                }else{

            toastMessage("Tem que preencher x e y");

                }

            }
        });

if (NewArray.get(n).size()!=0){

    NewArrayX1 = NewArray.get(n).get(NewArray.get(n).size()-1).getX();
    NewArrayY1 = NewArray.get(n).get(NewArray.get(n).size()-1).getY();

if(NewArray.get(n).size()>1) {

    NewArrayX2 = NewArray.get(n).get(NewArray.get(n).size()-2).getX();
    NewArrayY2 = NewArray.get(n).get(NewArray.get(n).size()-2).getY();

}


 createScatterPlot1();


}else {

    Log.d(TAG, "nao ha dados");

}

    }



    private void createScatterPlot1() {

        Log.d(TAG, "Fazendo o grafico");


//        for(int i=0; i<NewArray[n].size();i++){
//
//           double x= NewArray[n].get(i).getX();
//           double y =NewArray[n].get(i).getY();
//           xyValueArray[n].set(i, new XYValue(x,y));
//
//        }

    //    xyValueArray[n] = NewArray[n];


        xyValueArray.clear();


        for (int i = 0; i < NewArray.get(n).size(); i++) {

            double x = NewArray.get(n).get(i).getX();
            double y = NewArray.get(n).get(i).getY();
            xyValueArray.add(new XYValue(x, y));

        }


        //Colocar a array na ordem

        sortArray(xyValueArray);

        System.out.println("NewArray:");
        for (int i = 0; i < NewArray.size(); i++) {
            for (int j = 0; j < NewArray.get(i).size(); j++) {

                final double x = NewArray.get(i).get(j).getX();
                final double y = NewArray.get(i).get(j).getY();

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

//        if (xyValueArray[n] == NewArray[n]) {
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
                if(ChangeFloor==true){

                      if(x==NewArrayX1&&y==NewArrayY1){


                          xySeries[n].appendData(new DataPoint(x,y),true,1000);

                      }

                }
                //Ligar dois pontos caso o andar seja o mesmo
                  else  {

                      if(x==NewArrayX1&&y==NewArrayY1||x==NewArrayX2&&y==NewArrayY2){
                        xySeries[n].appendData(new DataPoint(x,y),true,1000);

                          Log.d(TAG, "New Data: "+ x+ " "+ y);
                      }

                      }


           }catch (IllegalArgumentException e){

                Log.e(TAG,"Deu ruim "+e.getMessage() );

            }catch (NullPointerException k){

                Log.e(TAG,"Deu ruim no dado com array "+k.getMessage() );

            }

        }

            //Caso o andar foi alterado e os pontos ja foram plotados
            //deve-se informar que agora o andar eh mesmo
            if(ChangeFloor==true){

                ChangeFloor=false;
            }


            //set some properties

        // xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);


            initGraph();

    }

    private void createScatterPlot2() {


        xyValueArray.clear();

        for (int i = 0; i < NewArray.get(n).size(); i++) {

            double x = NewArray.get(n).get(i).getX();
            double y = NewArray.get(n).get(i).getY();
            xyValueArray.add(new XYValue(x, y));

        }


        //Colocar a array na ordem
        Log.d(TAG, "Refazendo o grafico");
        sortArray(xyValueArray);

        System.out.println("Refazendo NewArray:");
        for (int i = 0; i < NewArray.size(); i++) {
            for (int j = 0; j < NewArray.get(i).size(); j++) {

                final double x = NewArray.get(i).get(j).getX();
                final double y = NewArray.get(i).get(j).getY();

                // System.out.print(aLists.get(i).get(j) + " ");

                System.out.print("(" + x + ", " + y + ") ");
            }
            System.out.println();
        }

        System.out.println("Refazendo xyValueArray:");

        for (int i = 0; i < xyValueArray.size(); i++) {

            final double x = xyValueArray.get(i).getX();
            final double y = xyValueArray.get(i).getY();

            // System.out.print(aLists.get(i).get(j) + " ");

            System.out.print("(" + x + ", " + y + ") ");

        }
        System.out.println();



//        xySeries[n].resetData(new DataPoint[] {});
//
//        for (int i = 0; i < NewArray.get(0).size(); i++) {
//
//            double x = NewArray.get(n).get(i).getX();
//            double y = NewArray.get(n).get(i).getY();
//            xyValueArray.get(n).add(new XYValue(0, 0));
//            xyValueArray.get(n).set(i,new XYValue (x,y));
//
//        }

        //Colocar a array na ordem

    //    sortArray(xyValueArray.get(n));

//for (int j=0; j< NewArray[n].size()-1;j++){

        for(int j=0; j< NewArray.get(n).size()-1;j++){
            double x1=NewArray.get(n).get(j).getX();
            double x2=NewArray.get(n).get(j+1).getX();
            double y1=NewArray.get(n).get(j).getY();
            double y2=NewArray.get(n).get(j+1).getY();
            for(int i=0; i< xyValueArray.size();i++){

            try {

                double x =  xyValueArray.get(i).getX();
                double y =   xyValueArray.get(i).getY();

//problema esta aqui
                if(x==x1&&y==y1||x==x2&&y==y2){
                    Log.d(TAG, "New: "+ x+ " "+ y);
                    xySeries[n].appendData(new DataPoint(x,y),true,1000);

                }



            }
          catch (IllegalArgumentException e){

                Log.e(TAG,"Deu ruim "+e.getMessage() );

            }catch (NullPointerException k){

                Log.e(TAG,"Deu ruim no dado com array "+k.getMessage() );

            }

        }


           // xySeries[n].resetData(new DataPoint[] {});
       // mScatterPlot[n].removeAllSeries();

        }

        initGraph();



    }

    //Configuracoes do grafico
    private void initGraph() {


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

     //   Log.d(TAG, "sortArray: Sorting the XYArray.");



        while(true){

            m--;

            if(m <= 0){

                m = array.size() - 1;

            }

        //   Log.d(TAG, "sortArray: m = " + m);

            try{

//               // print out the y entrys so we know what the order looks like
//
//                Log.d(TAG, "sortArray: Order:");
//
//                for(int n = 0;n < array.size();n++){
//
//                Log.d(TAG, "sortArray: " + array.get(n).getY());
//
//                }

                double tempY = array.get(m-1).getY();

                double tempX = array.get(m-1).getX();

                if(tempX >array.get(m).getX() ){

                    array.get(m-1).setY(array.get(m).getY());

                    array.get(m).setY(tempY);

                    array.get(m-1).setX(array.get(m).getX());

                    array.get(m).setX(tempX);

                }

                else if(tempY == array.get(m).getY()){

                    count++;

          //          Log.d(TAG, "sortArray: count = " + count);

                }



                else if(array.get(m).getX() > array.get(m-1).getX()){

                    count++;

            //        Log.d(TAG, "sortArray: count = " + count);

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
