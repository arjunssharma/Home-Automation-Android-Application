package com.gundel.bluecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main
  extends Activity
{
  private static final boolean D = true;
  public static final String DEVICE_NAME = "device_name";
  private static final String HOSTING = "HOSTING";
  private static final String HOST_DEVICE_ADDRESS = "HOST_DEVICE_ADDRESS";
  public static final int MESSAGE_DEVICE_NAME = 4;
  public static final int MESSAGE_READ = 2;
  public static final int MESSAGE_STATE_CHANGE = 1;
  public static final int MESSAGE_TOAST = 5;
  public static final int MESSAGE_WRITE = 3;
  private static final int REQUEST_CONNECT_DEVICE = 1;
  private static final int REQUEST_ENABLE_BT = 2;
  private static final String TAG = "BluetoothApp";
  public static final String TOAST = "toast";
  private BluetoothAdapter mBluetoothAdapter = null;
  private String mConnectedDeviceName = null;
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      case 2: 
      case 3: 
      default: 
        return;
      case 1: 
        Log.i("BluetoothApp", "MESSAGE_STATE_CHANGE: " + paramAnonymousMessage.arg1);
        switch (paramAnonymousMessage.arg1)
        {
        }
        return;
      case 4: 
        Main.this.mConnectedDeviceName = paramAnonymousMessage.getData().getString("device_name");
        Toast.makeText(Main.this.getApplicationContext(), "Connected to " + Main.this.mConnectedDeviceName, 0).show();
        return;
      }
      Toast.makeText(Main.this.getApplicationContext(), paramAnonymousMessage.getData().getString("toast"), 0).show();
    }
  };
  private BluetoothService mService = null;
  public int timer = 0;
  
  private void ensureDiscoverable()
  {
    Log.d("BluetoothApp", "ensure discoverable");
    if (this.mBluetoothAdapter.getScanMode() != 23)
    {
      Intent localIntent = new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
      localIntent.putExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", 300);
      startActivity(localIntent);
    }
  }
  
  private void sendMessage(String paramString)
  {
    if (this.mService.getState() != 3) {
      Toast.makeText(this, "Not Connected", 0).show();
    }
    while (paramString.length() <= 0) {
      return;
    }
    byte[] arrayOfByte = paramString.getBytes();
    this.mService.write(arrayOfByte);
  }
  
  private void setupConnection()
  {
    Log.d("BluetoothApp", "setupConnection()");
    this.mService = new BluetoothService(this, this.mHandler);
    if (getIntent().getStringExtra("HOST_DEVICE_ADDRESS") != null)
    {
      BluetoothDevice localBluetoothDevice = this.mBluetoothAdapter.getRemoteDevice(getIntent().getStringExtra("HOST_DEVICE_ADDRESS"));
      this.mService.connect(localBluetoothDevice);
    }
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    Log.d("BluetoothApp", "onActivityResult " + paramInt2);
    switch (paramInt1)
    {
    default: 
    case 1: 
      do
      {
        return;
      } while (paramInt2 != -1);
      String str = paramIntent.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
      BluetoothDevice localBluetoothDevice = this.mBluetoothAdapter.getRemoteDevice(str);
      this.mService.connect(localBluetoothDevice);
      return;
    }
    if (paramInt2 == -1)
    {
      setupConnection();
      return;
    }
    Log.d("BluetoothApp", "BT not enabled");
    Toast.makeText(this, "Bluetooth not enabled, Leaving...", 0).show();
    finish();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903044);
    Log.e("BluetoothApp", "+++ ON CREATE +++");
    this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (this.mBluetoothAdapter == null)
    {
      Toast.makeText(this, "Bluetooth is not available", 1).show();
      finish();
      return;
    }
    Button localButton1 = (Button)findViewById(2131099654);
    Button localButton2 = (Button)findViewById(2131099657);
    Button localButton3 = (Button)findViewById(2131099655);
    Button localButton4 = (Button)findViewById(2131099658);
    Button localButton5 = (Button)findViewById(2131099660);
    Button localButton6 = (Button)findViewById(2131099661);
    Button localButton7 = (Button)findViewById(2131099662);
    Button localButton8 = (Button)findViewById(2131099663);
    Button localButton9 = (Button)findViewById(2131099664);
    Button localButton10 = (Button)findViewById(2131099665);
    Button localButton11 = (Button)findViewById(2131099666);
    Button localButton12 = (Button)findViewById(2131099667);
    Button localButton13 = (Button)findViewById(2131099659);
    localButton1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (Main.this.mService.getState() == 3) {
          Main.this.sendMessage(String.valueOf('U'));
        }
      }
    });
    localButton4.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (Main.this.mService.getState() == 3) {
          Main.this.sendMessage(String.valueOf('D'));
        }
      }
    });
    localButton2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (Main.this.mService.getState() == 3) {
          Main.this.sendMessage(String.valueOf('L'));
        }
      }
    });
    localButton3.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (Main.this.mService.getState() == 3) {
          Main.this.sendMessage(String.valueOf('R'));
        }
      }
    });
    localButton5.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (Main.this.mService.getState() == 3) {
          Main.this.sendMessage(String.valueOf('a'));
        }
      }
    });
    localButton6.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (Main.this.mService.getState() == 3) {
          Main.this.sendMessage(String.valueOf('c'));
        }
      }
    });
    localButton7.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (Main.this.mService.getState() == 3) {
          Main.this.sendMessage(String.valueOf('b'));
        }
      }
    });
    localButton8.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (Main.this.mService.getState() == 3) {
          Main.this.sendMessage(String.valueOf('d'));
        }
      }
    });
    localButton9.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (Main.this.mService.getState() == 3) {
          Main.this.sendMessage(String.valueOf('e'));
        }
      }
    });
    localButton10.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (Main.this.mService.getState() == 3) {
          Main.this.sendMessage(String.valueOf('f'));
        }
      }
    });
    localButton11.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (Main.this.mService.getState() == 3) {
          Main.this.sendMessage(String.valueOf('g'));
        }
      }
    });
    localButton12.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (Main.this.mService.getState() == 3) {
          Main.this.sendMessage(String.valueOf('h'));
        }
      }
    });
    localButton13.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (Main.this.mService.getState() == 3) {
          Main.this.sendMessage(String.valueOf('C'));
        }
      }
    });
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131034112, paramMenu);
    return true;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    if (this.mService != null) {
      this.mService.stop();
    }
    Log.e("BluetoothApp", "--- ON DESTROY ---");
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return false;
    case 2131099668: 
      startActivityForResult(new Intent(this, DeviceListActivity.class), 1);
      return true;
    case 2131099669: 
      ensureDiscoverable();
      return true;
    }
    startActivity(new Intent(this, About.class));
    return true;
  }
  
  public void onPause()
  {
    try
    {
      super.onPause();
      Log.e("BluetoothApp", "- ON PAUSE -");
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void onResume()
  {
    try
    {
      super.onResume();
      Log.e("BluetoothApp", "+ ON RESUME +");
      if ((this.mService != null) && (this.mService.getState() == 0)) {
        this.mService.start();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void onStart()
  {
    super.onStart();
    Log.e("BluetoothApp", "++ ON START ++");
    if (!this.mBluetoothAdapter.isEnabled()) {
      startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 2);
    }
    for (;;)
    {
      if (getIntent().getBooleanExtra("HOSTING", false)) {
        ensureDiscoverable();
      }
      return;
      if (this.mService == null) {
        setupConnection();
      }
    }
  }
  
  public void onStop()
  {
    super.onStop();
    Log.e("BluetoothApp", "-- ON STOP --");
  }
}


/* Location:           C:\Users\Arjun\Desktop\final\classes-dex2jar.jar
 * Qualified Name:     com.gundel.bluecontrol.Main
 * JD-Core Version:    0.7.0.1
 */