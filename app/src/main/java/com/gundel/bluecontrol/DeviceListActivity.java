package com.gundel.bluecontrol;

import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DeviceListActivity
  extends Activity
{
  private static final boolean D = true;
  public static String EXTRA_DEVICE_ADDRESS = "device_address";
  private static final String TAG = "DeviceListActivity";
  private BluetoothAdapter mBtAdapter;
  private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener()
  {
    public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
    {
      DeviceListActivity.this.mBtAdapter.cancelDiscovery();
      String str1 = ((TextView)paramAnonymousView).getText().toString();
      String str2 = str1.substring(str1.length() - 17);
      Intent localIntent = new Intent();
      localIntent.putExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS, str2);
      DeviceListActivity.this.setResult(-1, localIntent);
      DeviceListActivity.this.finish();
    }
  };
  private ArrayAdapter<String> mNewDevicesArrayAdapter;
  private ArrayAdapter<String> mPairedDevicesArrayAdapter;
  private final BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      if ("android.bluetooth.device.action.FOUND".equals(str))
      {
        BluetoothDevice localBluetoothDevice = (BluetoothDevice)paramAnonymousIntent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        if (localBluetoothDevice.getBondState() != 12) {
          DeviceListActivity.this.mNewDevicesArrayAdapter.add(localBluetoothDevice.getName() + "\n" + localBluetoothDevice.getAddress());
        }
      }
      do
      {
        do
        {
          return;
        } while (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(str));
        DeviceListActivity.this.setProgressBarIndeterminateVisibility(false);
        DeviceListActivity.this.setTitle("Select a Device");
      } while (DeviceListActivity.this.mNewDevicesArrayAdapter.getCount() != 0);
      DeviceListActivity.this.mNewDevicesArrayAdapter.add("No Devices Found");
    }
  };
  
  private void doDiscovery()
  {
    Log.d("DeviceListActivity", "doDiscovery()");
    setProgressBarIndeterminateVisibility(true);
    setTitle("Scanning...");
    findViewById(2131099651).setVisibility(0);
    if (this.mBtAdapter.isDiscovering()) {
      this.mBtAdapter.cancelDiscovery();
    }
    this.mBtAdapter.startDiscovery();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(5);
    setContentView(2130903041);
    setResult(0);
    ((Button)findViewById(2131099653)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DeviceListActivity.this.doDiscovery();
        paramAnonymousView.setVisibility(8);
      }
    });
    this.mPairedDevicesArrayAdapter = new ArrayAdapter(this, 2130903042);
    this.mNewDevicesArrayAdapter = new ArrayAdapter(this, 2130903042);
    ListView localListView1 = (ListView)findViewById(2131099650);
    localListView1.setAdapter(this.mPairedDevicesArrayAdapter);
    localListView1.setOnItemClickListener(this.mDeviceClickListener);
    ListView localListView2 = (ListView)findViewById(2131099652);
    localListView2.setAdapter(this.mNewDevicesArrayAdapter);
    localListView2.setOnItemClickListener(this.mDeviceClickListener);
    IntentFilter localIntentFilter1 = new IntentFilter("android.bluetooth.device.action.FOUND");
    registerReceiver(this.mReceiver, localIntentFilter1);
    IntentFilter localIntentFilter2 = new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
    registerReceiver(this.mReceiver, localIntentFilter2);
    this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    Set localSet = this.mBtAdapter.getBondedDevices();
    if (localSet.size() > 0)
    {
      findViewById(2131099649).setVisibility(0);
      Iterator localIterator = localSet.iterator();
      for (;;)
      {
        if (!localIterator.hasNext()) {
          return;
        }
        BluetoothDevice localBluetoothDevice = (BluetoothDevice)localIterator.next();
        this.mPairedDevicesArrayAdapter.add(localBluetoothDevice.getName() + "\n" + localBluetoothDevice.getAddress());
      }
    }
    this.mPairedDevicesArrayAdapter.add("No Devices");
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    if (this.mBtAdapter != null) {
      this.mBtAdapter.cancelDiscovery();
    }
    unregisterReceiver(this.mReceiver);
  }
}


/* Location:           C:\Users\Arjun\Desktop\final\classes-dex2jar.jar
 * Qualified Name:     com.gundel.bluecontrol.DeviceListActivity
 * JD-Core Version:    0.7.0.1
 */