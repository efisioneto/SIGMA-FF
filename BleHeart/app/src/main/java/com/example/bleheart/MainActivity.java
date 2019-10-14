package com.example.bleheart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;
import java.util.ArrayList;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothAdapter BA;
    Button b1,b2;
    private Set<BluetoothDevice>pairedDevices;
    ListView lv;
    boolean retvalue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    public void on(View v) {
        if (!bluetoothAdapter.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_LONG).show();
            final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            bluetoothAdapter = bluetoothManager.getAdapter();
            retvalue = false;
        } else {
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }
    }
    public void off(View v){
        bluetoothAdapter.disable();
        Toast.makeText(getApplicationContext(), "Turned off" ,Toast.LENGTH_LONG).show();
    }

    // Initializes Bluetooth adapter.  BluetoothManager documentation says that
    //Use getSystemService(java.lang.String) with BLUETOOTH_SERVICE to create an BluetoothManager,
    //then call getAdapter() to obtain the BluetoothAdapter.

    // final BluetoothManager bluetoothManager =(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
    // bluetoothAdapter = bluetoothManager.getAdapter();



//    if(bluetoothAdapter == null) {
//        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//        bluetoothAdapter = bluetoothManager.getAdapter();
//        retvalue = false;
//    }
// Ensures Bluetooth is available on the device and it is enabled. If not,
// displays a dialog requesting user permission to enable Bluetooth.

    private final static int REQUEST_ENABLE_BT = 1;
    private void check() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new
                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
        }

    }

    /**
     * Activity for scanning and displaying available BLE devices.
     */
    public class DeviceScanActivity extends ListActivity {

        private LeDeviceListAdapter mLeDeviceListAdapter;
        private BluetoothAdapter bluetoothAdapter;
        private boolean mScanning;
        private Handler mhandler;

        private static final int REQUEST_ENABLE_BT = 1;
        // Stops scanning after 10 seconds.
        private static final long SCAN_PERIOD = 10000;

        private void scanLeDevice(final boolean enable) {

            final BluetoothLeScanner bluetoothLeScanner = BluetoothAdapter.getBluetoothLeScanner();

            if (enable) {
                // Stops scanning after a pre-defined scan period.
                mhandler.postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        mScanning = false;
                        bluetoothLeScanner.stopScan((ScanCallback) leScanCallback);
                    }
                }, SCAN_PERIOD);

                mScanning = true;
                bluetoothLeScanner.startScan((ScanCallback) leScanCallback);
            } else {
                mScanning = false;
                bluetoothLeScanner.stopScan((ScanCallback) leScanCallback);
            }
          invalidateOptionsMenu();
        }

        private LeDeviceListAdapter leDeviceListAdapter;

            // Device scan callback.
            private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
                        @Override
                        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    leDeviceListAdapter.addDevice(device);
                                    leDeviceListAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    };



    }

    private class LeDeviceListAdapter extends BaseAdapter {

        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = DeviceScanActivity.this.getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device) {
            if(!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }

        public BluetoothDevice getDevice(int position) {
            return mLeDevices.get(position);
        }

        public void clear() {
            mLeDevices.clear();
        }

        @Override
        public int getCount() {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }


    }

}

