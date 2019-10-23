/*

 * Copyright (C) 2014 The Android Open Source Project

 *

 * Licensed under the Apache License, Version 2.0 (the "License");

 * you may not use this file except in compliance with the License.

 * You may obtain a copy of the License at

 *

 *      http://www.apache.org/licenses/LICENSE-2.0

 *

 * Unless required by applicable law or agreed to in writing, software

 * distributed under the License is distributed on an "AS IS" BASIS,

 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

 * See the License for the specific language governing permissions and

 * limitations under the License.

 */



package com.example.android.bluetoothchat;



import android.app.ActionBar;

import android.app.Activity;

import android.bluetooth.BluetoothAdapter;

import android.bluetooth.BluetoothDevice;

import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;

import android.os.Message;

import android.view.KeyEvent;

import android.view.LayoutInflater;

import android.view.Menu;

import android.view.MenuInflater;

import android.view.MenuItem;

import android.view.View;

import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;

import android.widget.ArrayAdapter;

import android.widget.Button;

import android.widget.EditText;

import android.widget.ListView;

import android.widget.TextView;

import android.widget.Toast;



import androidx.annotation.NonNull;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;



import com.example.android.common.logger.Log;

//#graphView
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.GridLabelRenderer;

import java.util.Random;



/**

 * This fragment controls Bluetooth to communicate with other devices.

 */

public class BluetoothChatFragment extends Fragment{



    public Runnable mTimer;
    public double graphLastXValue = 5d;
    public double yvalue = 5d;

    public LineGraphSeries<DataPoint> mSeries;

    private static final String TAG = "BluetoothChatFragment";


    // Intent request codes

    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;

    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;

    private static final int REQUEST_ENABLE_BT = 3;



    // Layout Views

    private ListView mConversationView;

    private EditText mOutEditText;

    private Button mSendButton;


    /**
     * Name of the connected device
     */

    private String mConnectedDeviceName = null;


    /**
     * Array adapter for the conversation thread
     */

    private ArrayAdapter<String> mConversationArrayAdapter;


    /**
     * String buffer for outgoing messages
     */

    private StringBuffer mOutStringBuffer;


    /**
     * Local Bluetooth adapter
     */

    private BluetoothAdapter mBluetoothAdapter = null;


    /**
     * Member object for the chat services
     */

    private BluetoothChatService mChatService = null;

    private String hexX, hexY;
    public int  decimalX,decimalY;
    public int i =0;

    public void initGraph(GraphView graph)
    {
        graph.getViewport().setXAxisBoundsManual(true);



      //  graph.getViewport().setMinX(0);

       // graph.getViewport().setMaxX(1048575);



      //  graph.getViewport().setYAxisBoundsManual(true);

        //graph.getViewport().setMinY(0);

       // graph.getViewport().setMaxY(1048575);

        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(50);

        graph.getViewport().setYAxisBoundsManual(true); // These lines seem to be causing it
        graph.getViewport().setMinY(2.0);
        graph.getViewport().setMaxY(15.0);


        graph.getViewport().setScrollable(true); // enables horizontal scrolling

        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling





        //graph.getGridLabelRenderer().setLabelVerticalWidth(100);

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();

        gridLabel.setHorizontalAxisTitle("x axis");

        gridLabel.setVerticalAxisTitle("y axis");




        // first mSeries is a line

        mSeries = new LineGraphSeries<>();

        mSeries.setDrawDataPoints(true);

        mSeries.setDrawBackground(true);

        graph.addSeries(mSeries);

        mSeries.setColor(Color.RED);
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        // Get local Bluetooth adapter

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        // If the adapter is null, then Bluetooth is not supported

        FragmentActivity activity = getActivity();

        if (mBluetoothAdapter == null && activity != null) {

            Toast.makeText(activity, "Bluetooth is not available", Toast.LENGTH_LONG).show();

            activity.finish();

        }
        //#graphView https://github.com/jjoe64/GraphView
        //GraphView graph = (GraphView) findViewById(R.id.graph);
//        GraphView graph = (GraphView) findViewById(R.id.graph);
//        initGraph(graph);
//

    }

    @Override

    public void onStart() {


        super.onStart();

        if (mBluetoothAdapter == null) {

            return;

        }

        // If BT is not on, request that it be enabled.

        // setupChat() will then be called during onActivityResult

        if (!mBluetoothAdapter.isEnabled()) {

            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);

            // Otherwise, setup the chat session

        } else if (mChatService == null) {

            setupChat();

        }

    }


    @Override

    public void onDestroy() {

        super.onDestroy();

        if (mChatService != null) {

            mChatService.stop();

        }

    }



    @Override

    public void onResume() {

        super.onResume();


        // Performing this check in onResume() covers the case in which BT was

        // not enabled during onStart(), so we were paused to enable it...

        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.

        if (mChatService != null) {

            // Only if the state is STATE_NONE, do we know that we haven't started already

            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {

                // Start the Bluetooth chat services

                mChatService.start();

            }

        }

    }


    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,

                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_bluetooth_chat, container, false);

    }


    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mConversationView = view.findViewById(R.id.in);

        mOutEditText = view.findViewById(R.id.edit_text_out);

        mSendButton = view.findViewById(R.id.button_send);



    }


    /**
     * Set up the UI and background operations for chat.
     */

    private void setupChat() {

        Log.d(TAG, "setupChat()");


        // Initialize the array adapter for the conversation thread

        FragmentActivity activity = getActivity();

        if (activity == null) {

            return;

        }

        mConversationArrayAdapter = new ArrayAdapter<>(activity, R.layout.message);


        mConversationView.setAdapter(mConversationArrayAdapter);


        // Initialize the compose field with a listener for the return key

        mOutEditText.setOnEditorActionListener(mWriteListener);


        // Initialize the send button with a listener that for click events

        mSendButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // Send a message using content of the edit text widget

                View view = getView();

                if (null != view) {

                    TextView textView = view.findViewById(R.id.edit_text_out);

                    String message = textView.getText().toString();

                    sendMessage(message);

                }

            }

        });


        // Initialize the BluetoothChatService to perform bluetooth connections

        mChatService = new BluetoothChatService(activity, mHandler);


        // Initialize the buffer for outgoing messages

        mOutStringBuffer = new StringBuffer();

    }


    /**
     * Makes this device discoverable for 300 seconds (5 minutes).
     */

    private void ensureDiscoverable() {

        if (mBluetoothAdapter.getScanMode() !=

                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {

            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);

            startActivity(discoverableIntent);

        }

    }


    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */

    private void sendMessage(String message) {

        // Check that we're actually connected before trying anything

        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {

            Toast.makeText(getActivity(), R.string.not_connected, Toast.LENGTH_SHORT).show();

            return;

        }


        // Check that there's actually something to send

        if (message.length() > 0) {

            // Get the message bytes and tell the BluetoothChatService to write

            byte[] send = message.getBytes();

            mChatService.write(send);


            // Reset out string buffer to zero and clear the edit text field

            mOutStringBuffer.setLength(0);

            mOutEditText.setText(mOutStringBuffer);

        }

    }


    /**
     * The action listener for the EditText widget, to listen for the return key
     */

    private TextView.OnEditorActionListener mWriteListener

            = new TextView.OnEditorActionListener() {

        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {

            // If the action is a key-up event on the return key, send the message

            if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {

                String message = view.getText().toString();

                sendMessage(message);

            }

            return true;

        }

    };


    /**
     * Updates the status on the action bar.
     *
     * @param resId a string resource ID
     */

    private void setStatus(int resId) {

        FragmentActivity activity = getActivity();

        if (null == activity) {

            return;

        }

        final ActionBar actionBar = activity.getActionBar();

        if (null == actionBar) {

            return;

        }

        actionBar.setSubtitle(resId);

    }


    /**
     * Updates the status on the action bar.
     *
     * @param subTitle status
     */

    private void setStatus(CharSequence subTitle) {

        FragmentActivity activity = getActivity();

        if (null == activity) {

            return;

        }

        final ActionBar actionBar = activity.getActionBar();

        if (null == actionBar) {

            return;

        }

        actionBar.setSubtitle(subTitle);

    }


    /**
     * The Handler that gets information back from the BluetoothChatService
     */

    private final Handler mHandler = new Handler() {

        @Override

        public void handleMessage(Message msg) {





            FragmentActivity activity = getActivity();

            switch (msg.what) {

                case Constants.MESSAGE_STATE_CHANGE:

                    switch (msg.arg1) {

                        case BluetoothChatService.STATE_CONNECTED:

                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));

                            mConversationArrayAdapter.clear();

                            break;

                        case BluetoothChatService.STATE_CONNECTING:

                            setStatus(R.string.title_connecting);

                            break;

                        case BluetoothChatService.STATE_LISTEN:

                        case BluetoothChatService.STATE_NONE:

                            setStatus(R.string.title_not_connected);

                            break;

                    }

                    break;

                case Constants.MESSAGE_WRITE:

                    byte[] writeBuf = (byte[]) msg.obj;

                    // construct a string from the buffer

                    String writeMessage = new String(writeBuf);
                    //String writeMessage="aaa";

                    mConversationArrayAdapter.add(writeMessage);

                    break;

                case Constants.MESSAGE_READ:

                    byte[] readBuf = (byte[]) msg.obj;

                    // construct a string from the valid bytes in the buffer

                    // String readMessage = new String(readBuf, 0, msg.arg1);

                    String readMessage = new String(readBuf, 0, 80);

                    //Operator ID, inteiro de 0 a 9
                    Mensagem ID = new Mensagem();
                    ID.setMensagem(readMessage.substring(1, 3));
                    String palavra = ID.getMessagem();

                    //Message counter, inteiro de 0 a 65535
                    Mensagem MessageCounter = new Mensagem();
                    MessageCounter.setMensagem(readMessage.substring(3, 7));

                    //Step Counter, inteiro de 0 a 65535
                    Mensagem StepCounter = new Mensagem();
                    StepCounter.setMensagem(readMessage.substring(7, 11));

                    //Flag x 16: bit0: initial stance OK, bit1: no magnetic calibration, bit2: operator KO,
                    // bit3: high temperature
                    Mensagem Flag = new Mensagem();
                    Flag.setMensagem(readMessage.substring(11, 15));

                    // Position estimation given by integration of inertial and magnetic data, X (North), LSB 0.1 m,
                    // value from -52.4288 km to +52.4288 km
                    Mensagem PositionIMX = new Mensagem();
                    PositionIMX.setMensagem(readMessage.substring(15, 20));

                    // Position estimation given by integration of inertial and magnetic data, Y (East) , LSB 0.1 m,
                    // value from -52.4288 km to +52.4288 km
                    Mensagem PositionIMY = new Mensagem();
                    PositionIMY.setMensagem(readMessage.substring(20, 25));

                    //Position estimation given by integration of inertial data only, X (North) , LSB 0.1 m,
                    // value from -52.4288 km to +52.4288 km 8
                    Mensagem PositionIX = new Mensagem();
                    PositionIX.setMensagem(readMessage.substring(25, 30));

                    // Position estimation given by integration of inertial data only, Y (East) , LSB 0.1 m,
                    // value from -52.4288 km to +52.4288 km
                    Mensagem PositionIY = new Mensagem();
                    PositionIY.setMensagem(readMessage.substring(30, 35));

                    //Altitude estimation given by integration of inertial data only, Z (Down) , LSB 0.1 m,
                    // value from -3276.8 m to +3276.8 m
                    Mensagem PositionIZ = new Mensagem();
                    PositionIZ.setMensagem(readMessage.substring(35, 39));

                    //Altitude estimation given by pressometer, Z (Down), LSB 0.1 m, value from -3276.8 m to +3276.8 m
                    Mensagem Altitude = new Mensagem();
                    Altitude.setMensagem(readMessage.substring(39, 43));

                    // Latitude estimation from GPS, LSB = 2^-29 rad (nearly 1.2 c at sea level),
                    // value from –PI/2 to +PI/2 rad
                    Mensagem Latitude = new Mensagem();
                    Latitude.setMensagem(readMessage.substring(43, 51));

                    // Longitude estimation from GPS, LSB = 2^-29 rad (nearly 1.2 c at sea level),
                    // value from –PI to +PI rad
                    Mensagem Longitude = new Mensagem();
                    Longitude.setMensagem(readMessage.substring(51, 59));

                    //GPS estimation quality, value from 0 to 99
                    Mensagem GPS = new Mensagem();
                    GPS.setMensagem(readMessage.substring(59, 61));

                    //North alignment angle of inertial path, LSB 2^-13 rad, value from –PI to PI rad
                    Mensagem Angle = new Mensagem();
                    Angle.setMensagem(readMessage.substring(61, 65));

                    //Yaw drift of inertial path, in rotation per step, LSB 2^-19 rad,
                    // value from –3.56 to 3.56 rad
                    Mensagem Drift = new Mensagem();
                    Drift.setMensagem(readMessage.substring(65, 69));

                    //CRC-CCITT
                    Mensagem CRC = new Mensagem();
                    CRC.setMensagem(readMessage.substring(69, 73));
                    //String hex="FE" ;


                  //Loop inicial apenas para a primeira string nao ser lida
                    if(i<=3) {
                        hexX="00000";
                        hexY="00000";
                        i++;
                    }
                    else {
                        hexX=PositionIMX.getMessagem();
                        hexY=PositionIMX.getMessagem();
                    }

                    // Converter hexadecimal em decimal
                    decimalX=Integer.parseInt(hexX,16);
                    decimalY=Integer.parseInt(hexY,16);


                    //#graphView
                    mTimer = new Runnable()
                    {

                        public void run()
                        {
                            graphLastXValue += 1.0;

                           // BluetoothChatFragment.Decimal Xvalue= new BluetoothChatFragment.Decimal();
                         //   BluetoothChatFragment.Decimal Yvalue= new BluetoothChatFragment.Decimal();



                           //double a =Xvalue.ValorX;
                           //double b =Yvalue.ValorY;



                            double a =decimalX;
                            double b =decimalY;

                           // mSeries.appendData(new DataPoint(a, b),
                             //     true, 22);

                            //mSeries.appendData(new DataPoint(Xvalue.ValorX,Yvalue.ValorY),
                            //       true, 100);

                            // mHandler.postDelayed(this, 330);
                        }
                    };
                    mHandler.postDelayed(mTimer, 1500);


                    //String valor= Integer.toString(decimal);
                    // mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    // mConversationArrayAdapter.add("XXXXXX "+readMessage+"XXXXXX");
//                    mConversationArrayAdapter.add("XXXXXX "+readMessage+"XXXXXX"+ID.getMessagem()
//                            +MessageCounter.getMessagem()+StepCounter.getMessagem()+Flag.getMessagem());

                    //mConversationArrayAdapter.add("X:"+PositionIMX.getMessagem()+" Y:"
                    //      +PositionIMY.getMessagem()+"XX" +decimal);
                    //System.out.println(decimal);

                    //Printar os dados
                    mConversationArrayAdapter.add("X:"+decimalX+" Y:"+decimalY);

                    break;

                case Constants.MESSAGE_DEVICE_NAME:

                    // save the connected device's name

                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);

                    if (null != activity) {

                        Toast.makeText(activity, "Connected to "

                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();

                    }

                    break;

                case Constants.MESSAGE_TOAST:

                    if (null != activity) {

                        Toast.makeText(activity, msg.getData().getString(Constants.TOAST),

                                Toast.LENGTH_SHORT).show();

                    }

                    break;

            }

        }

    };


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case REQUEST_CONNECT_DEVICE_SECURE:

                // When DeviceListActivity returns with a device to connect

                if (resultCode == Activity.RESULT_OK) {

                    connectDevice(data, true);

                }

                break;

            case REQUEST_CONNECT_DEVICE_INSECURE:

                // When DeviceListActivity returns with a device to connect

                if (resultCode == Activity.RESULT_OK) {

                    connectDevice(data, false);

                }

                break;

            case REQUEST_ENABLE_BT:

                // When the request to enable Bluetooth returns

                if (resultCode == Activity.RESULT_OK) {

                    // Bluetooth is now enabled, so set up a chat session

                    setupChat();

                } else {

                    // User did not enable Bluetooth or an error occurred

                    Log.d(TAG, "BT not enabled");

                    FragmentActivity activity = getActivity();

                    if (activity != null) {

                        Toast.makeText(activity, R.string.bt_not_enabled_leaving,

                                Toast.LENGTH_SHORT).show();

                        activity.finish();

                    }

                }

        }

    }


    /**
     * Establish connection with other device
     *
     * @param data   An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */

    private void connectDevice(Intent data, boolean secure) {

        // Get the device MAC address

        Bundle extras = data.getExtras();

        if (extras == null) {

            return;

        }

        String address = extras.getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

        // Get the BluetoothDevice object

        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

        // Attempt to connect to the device

        mChatService.connect(device, secure);

    }


    @Override

    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {


        inflater.inflate(R.menu.bluetooth_chat, menu);

    }


    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.secure_connect_scan: {

                // Launch the DeviceListActivity to see devices and do scan

                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);

                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);

                return true;

            }

            case R.id.insecure_connect_scan: {

                // Launch the DeviceListActivity to see devices and do scan

                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);

                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);

                return true;

            }

            case R.id.discoverable: {

                // Ensure this device is discoverable by others

                ensureDiscoverable();

                return true;

            }

        }

        return false;

    }

    public class Mensagem {
        private String mensagem;

        // Getter
        public String getMessagem() {

            return mensagem;
        }

        //Setter
        public void setMensagem(String m) {

            this.mensagem = m;


        }



    }

    public static class Decimal{
       // int ValorX = decimalX;
       // int ValorY = decimalY;

    }


}