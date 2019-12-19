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
    private Button btnAddPt,btnRight,btnLeft,btnUpFloor,btnDownFloor,btnUp,btnDown;
    //Imagem que sera usada

    //variaveis usadas para pegar os numeros que o usuario escreve
    private EditText mX,mY;
    //Variaveis usadas para comparar valores
    public double x,y;
    public double OriginalArrayX1,OriginalArrayY1,OriginalArrayX2,OriginalArrayY2;
    //Criando uma array de graficos
    //Representa o andar
    public int floor=0;

    //ArrayList<GraphView> mScatterPlot = new ArrayList<GraphView>();
    public GraphView[] mScatterPlot;
    //make xyValue global
 // private ArrayList<XYValue>[] xyValueArray;
// private ArrayList<XYValue>[] CorrectArray;
//private ArrayList<XYValue>[] OriginalArray;
    //Criando um booleano que condicione a mudanca de andar
    boolean ChangeFloor=false;

    //Here aList is an ArrayList of ArrayLists
    //ArrayList<ArrayList<Integer> > aLists= new ArrayList<ArrayList<Integer>();

   //Teste de novo programa

    ArrayList<ArrayList<XYValue>> OriginalArray = new ArrayList<ArrayList<XYValue>>();
    ArrayList<XYValue> xyValueArray = new ArrayList<XYValue>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mScatterPlot= new GraphView[100];


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declare variables in onCreate

        btnUp = (Button) findViewById(R.id.ArrowUp);
        btnDown = (Button) findViewById(R.id.ArrowDown);
        btnRight= (Button) findViewById(R.id.ArrowRight);
        btnLeft = (Button) findViewById(R.id.ArrowLeft);
        btnAddPt = (Button) findViewById(R.id.btnAddPt);
        mX = (EditText) findViewById(R.id.numX);
        mY = (EditText) findViewById(R.id.numY);

        try {
        mScatterPlot[floor] = (GraphView) findViewById(R.id.scatterPlot);
        }catch (NullPointerException e){

            Log.e(TAG,"Deu ruim na array "+e.getMessage() );

        }



//       xyValueArray= new ArrayList[100];
//      //  CorrectArray= new ArrayList[100];
//        OriginalArray= new ArrayList[100];
//
//
//       xyValueArray[0] = new ArrayList<>(100);
//      //  CorrectArray[0]= new ArrayList<>(100);
//       OriginalArray[0]= new ArrayList<>(100);
//
//        xyValueArray[1] = new ArrayList<>();
//      // CorrectArray[1]= new ArrayList<>();
//        OriginalArray[1]= new ArrayList<>();

        //Create values for OriginalFloor1
        ArrayList<XYValue> OriginalFloor1 = new ArrayList<>();
        OriginalArray.add(new ArrayList<XYValue>());
        //Create values for OriginalFloor2
        //ArrayList<XYValue> OriginalFloor2 = new ArrayList<>();
        //OriginalArray.add(new ArrayList<XYValue>());

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
       xySeries[floor]= new LineGraphSeries<>();
        //xySeries[1]= new LineGraphSeries<>();



//Subindo de andar
        btnUpFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View upFloor) {
                mScatterPlot[floor].removeAllSeries();
                ChangeFloor=true;
                floor+=1;
                Log.d(TAG,"Mudando de andar: " + floor);
                mScatterPlot[floor] = (GraphView) findViewById(R.id.scatterPlot);
                if (floor>OriginalArray.size()-1){
                    OriginalArray.add(new ArrayList<XYValue>());
                    xySeries[floor] = new LineGraphSeries<>() ;
                    createScatterPlot1();
                }
                else {
                 //   xySeries[floor] = new LineGraphSeries<>() ;
                    createScatterPlot2();
                }
            }
        });

//Descendo de andar

        btnDownFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View DownFloor) {
               mScatterPlot[floor].removeAllSeries();
                ChangeFloor=true;
                if(floor!=0){
                    floor-=1;
                }
                Log.d(TAG,"Mudando de andar de andar: " + floor);
                mScatterPlot[floor] = (GraphView) findViewById(R.id.scatterPlot);
             //   xySeries[floor]=new LineGraphSeries<>();
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
              OriginalArray.get(floor).add(new XYValue(x,y));



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
                OriginalArray.get(floor).add(new XYValue(x,y));

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
                OriginalArray.get(floor).add(new XYValue(x,y));

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
                OriginalArray.get(floor).add(new XYValue(x,y));

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
                    OriginalArray.get(floor).add(new XYValue(x,y));

                 init();

                }else{

            toastMessage("Tem que preencher x e y");

                }

            }
        });

if (OriginalArray.get(floor).size()!=0){

    OriginalArrayX1 = OriginalArray.get(floor).get(OriginalArray.get(floor).size()-1).getX();
    OriginalArrayY1 = OriginalArray.get(floor).get(OriginalArray.get(floor).size()-1).getY();

if(OriginalArray.get(floor).size()>1) {

    OriginalArrayX2 = OriginalArray.get(floor).get(OriginalArray.get(floor).size()-2).getX();
    OriginalArrayY2 = OriginalArray.get(floor).get(OriginalArray.get(floor).size()-2).getY();

}


 createScatterPlot1();


}else {

    Log.d(TAG, "nao ha dados");

}

    }



    private void createScatterPlot1() {

        Log.d(TAG, "Fazendo o grafico");


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
                if(ChangeFloor==true){

                      if(x==OriginalArrayX1&&y==OriginalArrayY1){


                          xySeries[floor].appendData(new DataPoint(x,y),true,1000);

                      }

                }
                //Ligar dois pontos caso o andar seja o mesmo
                  else  {

                      if(x==OriginalArrayX1&&y==OriginalArrayY1||x==OriginalArrayX2&&y==OriginalArrayY2){
                        xySeries[floor].appendData(new DataPoint(x,y),true,1000);

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

        for (int i = 0; i < OriginalArray.get(floor).size(); i++) {

            double x = OriginalArray.get(floor).get(i).getX();
            double y = OriginalArray.get(floor).get(i).getY();
            xyValueArray.add(new XYValue(x, y));

        }


        //Colocar a array na ordem
        Log.d(TAG, "Refazendo o grafico");
        sortArray(xyValueArray);

        System.out.println("Refazendo OriginalArray:");
        for (int i = 0; i < OriginalArray.size(); i++) {
            for (int j = 0; j < OriginalArray.get(i).size(); j++) {

                final double x = OriginalArray.get(i).get(j).getX();
                final double y = OriginalArray.get(i).get(j).getY();

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
//        for (int i = 0; i < OriginalArray.get(0).size(); i++) {
//
//            double x = OriginalArray.get(n).get(i).getX();
//            double y = OriginalArray.get(n).get(i).getY();
//            xyValueArray.get(n).add(new XYValue(0, 0));
//            xyValueArray.get(n).set(i,new XYValue (x,y));
//
//        }

        //Colocar a array na ordem

    //    sortArray(xyValueArray.get(n));

//for (int j=0; j< OriginalArray[n].size()-1;j++){

        for(int j=0; j< OriginalArray.get(floor).size()-1;j++){

            xySeries= new LineGraphSeries[100];
            xySeries[floor]= new LineGraphSeries<>();


            double x1=OriginalArray.get(floor).get(j).getX();
            double x2=OriginalArray.get(floor).get(j+1).getX();
            double y1=OriginalArray.get(floor).get(j).getY();
            double y2=OriginalArray.get(floor).get(j+1).getY();

            for(int i=0; i< xyValueArray.size();i++){

            try {

                double x =  xyValueArray.get(i).getX();
                double y =   xyValueArray.get(i).getY();

//problema esta aqui
                if(x==x1&&y==y1||x==x2&&y==y2){
                    Log.d(TAG, "New: "+ x+ " "+ y);
                    xySeries[floor].appendData(new DataPoint(x,y),true,1000);

                }



            }
          catch (IllegalArgumentException e){

                Log.e(TAG,"Deu ruim "+e.getMessage() );

            }catch (NullPointerException k){

                Log.e(TAG,"Deu ruim no dado com array "+k.getMessage() );

            }

        }
            initGraph();

           // xySeries[n].resetData(new DataPoint[] {});
       // mScatterPlot[n].removeAllSeries();

        }





    }

    //Configuracoes do grafico
    private void initGraph() {


        if (floor==0){
            xySeries[floor].setColor(Color.RED);
        }else{
            xySeries[floor].setColor(Color.BLUE);
        }


        //  xySeries.setSize(10f);
        xySeries[floor].setThickness(10);

        //make area under the graph
        xySeries[floor].setDrawBackground(false);

        //Data points can be highlighted

        xySeries[floor].setDrawDataPoints(true);


        //set Scrollable and Scaleable

        mScatterPlot[floor].getViewport().setScalable(true);

        mScatterPlot[floor].getViewport().setScalableY(true);

        mScatterPlot[floor].getViewport().setScrollable(true);

        mScatterPlot[floor].getViewport().setScrollableY(true);



        //set manual x bounds

        mScatterPlot[floor].getViewport().setYAxisBoundsManual(true);

        mScatterPlot[floor].getViewport().setMaxY(150);

        mScatterPlot[floor].getViewport().setMinY(-150);



        //set manual y bounds

        mScatterPlot[floor].getViewport().setXAxisBoundsManual(true);

        mScatterPlot[floor].getViewport().setMaxX(150);

        mScatterPlot[floor].getViewport().setMinX(-150);



        mScatterPlot[floor].addSeries(xySeries[floor]);





    }


    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array){

        /*

        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>

         */

        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));

        int m = array.size()-1;

        int count = 0;

   //     Log.d(TAG, "sortArray: Sorting the XYArray.");



        while(true){

            m--;

            if(m <= 0){

                m = array.size() - 1;

            }

  //        Log.d(TAG, "sortArray: m = " + m);

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


//CHANGE HERE

//                else if(array.get(m).getX() >= array.get(m-1).getX()){
//
//                    count++;
//
//                    //        Log.d(TAG, "sortArray: count = " + count);
//
//                }

                else if(array.get(m).getX() >= array.get(m-1).getX()){

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
