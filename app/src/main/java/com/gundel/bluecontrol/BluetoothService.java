

android.bluetooth.BluetoothAdapter
android.bluetooth.BluetoothDevice
android.bluetooth.BluetoothServerSocket
android.bluetooth.BluetoothSocket
android.content.Context
android.os.Bundle
android.os.Handler
android.os.Message
android.util.Log
java.io.IOException
java.io.InputStream
java.io.OutputStream
java.util.UUID

BluetoothService

  D = 
  MY_UUID = fromString"00001101-0000-1000-8000-00805F9B34FB"
  NAME = "BluetoothApp"
  STATE_CONNECTED = 3
  STATE_CONNECTING = 2
  STATE_LISTEN = 1
  STATE_NONE = 0
  TAG = "BluetoothService"
  mAcceptThread
  mAdapter = getDefaultAdapter()
  mConnectThread
  mConnectedThread
  mHandler
  mState = 0
  
  BluetoothService, 
  
    mHandler = paramHandler;
  }
  
  private void connectionFailed()
  {
    setState(1);
    Message localMessage = this.mHandler.obtainMessage(5);
    Bundle localBundle = new Bundle();
    localBundle.putString("toast", "Unable to connect device");
    localMessage.setData(localBundle);
    this.mHandler.sendMessage(localMessage);
  }
  
  private void connectionLost()
  {
    setState(1);
    Message localMessage = this.mHandler.obtainMessage(5);
    Bundle localBundle = new Bundle();
    localBundle.putString("toast", "Device connection was lost");
    localMessage.setData(localBundle);
    this.mHandler.sendMessage(localMessage);
  }
  
  private void setState(int paramInt)
  {
    try
    {
      Log.d("BluetoothService", "setState() " + this.mState + " -> " + paramInt);
      this.mState = paramInt;
      Message localMessage = this.mHandler.obtainMessage(1, paramInt, -1);
      this.mHandler.sendMessage(localMessage);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void connect(BluetoothDevice paramBluetoothDevice)
  {
    try
    {
      Log.d("BluetoothService", "connect to: " + paramBluetoothDevice);
      if ((this.mState == 2) && (this.mConnectThread != null))
      {
        this.mConnectThread.cancel();
        this.mConnectThread = null;
      }
      if (this.mConnectedThread != null)
      {
        this.mConnectedThread.cancel();
        this.mConnectedThread = null;
      }
      this.mConnectThread = new ConnectThread(paramBluetoothDevice);
      this.mConnectThread.start();
      setState(2);
      return;
    }
    finally {}
  }
  
  public void connected(BluetoothSocket paramBluetoothSocket, BluetoothDevice paramBluetoothDevice)
  {
    try
    {
      Log.d("BluetoothService", "connected");
      if (this.mConnectThread != null)
      {
        this.mConnectThread.cancel();
        this.mConnectThread = null;
      }
      if (this.mConnectedThread != null)
      {
        this.mConnectedThread.cancel();
        this.mConnectedThread = null;
      }
      if (this.mAcceptThread != null)
      {
        this.mAcceptThread.cancel();
        this.mAcceptThread = null;
      }
      this.mConnectedThread = new ConnectedThread(paramBluetoothSocket);
      this.mConnectedThread.start();
      Message localMessage = this.mHandler.obtainMessage(4);
      Bundle localBundle = new Bundle();
      localBundle.putString("device_name", paramBluetoothDevice.getName());
      localMessage.setData(localBundle);
      this.mHandler.sendMessage(localMessage);
      setState(3);
      return;
    }
    finally {}
  }
  
  public Handler getHandler()
  {
    return this.mHandler;
  }
  
  public int getState()
  {
    try
    {
      int i = this.mState;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void start()
  {
    try
    {
      Log.d("BluetoothService", "start");
      if (this.mConnectThread != null)
      {
        this.mConnectThread.cancel();
        this.mConnectThread = null;
      }
      if (this.mConnectedThread != null)
      {
        this.mConnectedThread.cancel();
        this.mConnectedThread = null;
      }
      if (this.mAcceptThread == null)
      {
        this.mAcceptThread = new AcceptThread();
        this.mAcceptThread.start();
      }
      setState(1);
      return;
    }
    finally {}
  }
  
  public void stop()
  {
    try
    {
      Log.d("BluetoothService", "stop");
      if (this.mConnectThread != null)
      {
        this.mConnectThread.cancel();
        this.mConnectThread = null;
      }
      if (this.mConnectedThread != null)
      {
        this.mConnectedThread.cancel();
        this.mConnectedThread = null;
      }
      if (this.mAcceptThread != null)
      {
        this.mAcceptThread.cancel();
        this.mAcceptThread = null;
      }
      setState(0);
      return;
    }
    finally {}
  }
  
  public void write(byte[] paramArrayOfByte)
  {
    try
    {
      if (this.mState != 3) {
        return;
      }
      ConnectedThread localConnectedThread = this.mConnectedThread;
      localConnectedThread.write(paramArrayOfByte);
      return;
    }
    finally {}
  }
  
  private class AcceptThread
    extends Thread
  {
    private final BluetoothServerSocket mmServerSocket;
    
    public AcceptThread()
    {
      try
      {
        BluetoothServerSocket localBluetoothServerSocket2 = BluetoothService.this.mAdapter.listenUsingRfcommWithServiceRecord("BluetoothApp", BluetoothService.MY_UUID);
        localBluetoothServerSocket1 = localBluetoothServerSocket2;
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          Log.e("BluetoothService", "listen() failed", localIOException);
          BluetoothServerSocket localBluetoothServerSocket1 = null;
        }
      }
      this.mmServerSocket = localBluetoothServerSocket1;
    }
    
    public void cancel()
    {
      Log.d("BluetoothService", "cancel " + this);
      try
      {
        this.mmServerSocket.close();
        return;
      }
      catch (IOException localIOException)
      {
        Log.e("BluetoothService", "close() of server failed", localIOException);
      }
    }
    
    public void run()
    {
      Log.d("BluetoothService", "BEGIN mAcceptThread" + this);
      setName("AcceptThread");
      if (BluetoothService.this.mState == 3)
      {
        Log.i("BluetoothService", "END mAcceptThread");
        return;
      }
      for (;;)
      {
        BluetoothSocket localBluetoothSocket;
        try
        {
          for (;;)
          {
            localBluetoothSocket = this.mmServerSocket.accept();
            if (localBluetoothSocket == null) {
              break;
            }
            synchronized (BluetoothService.this)
            {
              switch (BluetoothService.this.mState)
              {
              }
            }
          }
        }
        catch (IOException localIOException1)
        {
          Log.e("BluetoothService", "accept() failed", localIOException1);
        }
        BluetoothService.this.connected(localBluetoothSocket, localBluetoothSocket.getRemoteDevice());
        continue;
        try
        {
          localBluetoothSocket.close();
        }
        catch (IOException localIOException2)
        {
          Log.e("BluetoothService", "Could not close unwanted socket", localIOException2);
        }
      }
    }
  }
  
  private class ConnectThread
    extends Thread
  {
    private final BluetoothDevice mmDevice;
    private final BluetoothSocket mmSocket;
    
    public ConnectThread(BluetoothDevice paramBluetoothDevice)
    {
      this.mmDevice = paramBluetoothDevice;
      try
      {
        BluetoothSocket localBluetoothSocket2 = paramBluetoothDevice.createRfcommSocketToServiceRecord(BluetoothService.MY_UUID);
        localBluetoothSocket1 = localBluetoothSocket2;
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          Log.e("BluetoothService", "create() failed", localIOException);
          BluetoothSocket localBluetoothSocket1 = null;
        }
      }
      this.mmSocket = localBluetoothSocket1;
    }
    
    public void cancel()
    {
      try
      {
        this.mmSocket.close();
        return;
      }
      catch (IOException localIOException)
      {
        Log.e("BluetoothService", "close() of connect socket failed", localIOException);
      }
    }
    
    /* Error */
    public void run()
    {
      // Byte code:
      //   0: ldc 37
      //   2: ldc 56
      //   4: invokestatic 60	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
      //   7: pop
      //   8: aload_0
      //   9: ldc 62
      //   11: invokevirtual 66	com/gundel/bluecontrol/BluetoothService$ConnectThread:setName	(Ljava/lang/String;)V
      //   14: aload_0
      //   15: getfield 16	com/gundel/bluecontrol/BluetoothService$ConnectThread:this$0	Lcom/gundel/bluecontrol/BluetoothService;
      //   18: invokestatic 70	com/gundel/bluecontrol/BluetoothService:access$0	(Lcom/gundel/bluecontrol/BluetoothService;)Landroid/bluetooth/BluetoothAdapter;
      //   21: invokevirtual 76	android/bluetooth/BluetoothAdapter:cancelDiscovery	()Z
      //   24: pop
      //   25: aload_0
      //   26: getfield 35	com/gundel/bluecontrol/BluetoothService$ConnectThread:mmSocket	Landroid/bluetooth/BluetoothSocket;
      //   29: invokevirtual 79	android/bluetooth/BluetoothSocket:connect	()V
      //   32: aload_0
      //   33: getfield 16	com/gundel/bluecontrol/BluetoothService$ConnectThread:this$0	Lcom/gundel/bluecontrol/BluetoothService;
      //   36: astore 6
      //   38: aload 6
      //   40: monitorenter
      //   41: aload_0
      //   42: getfield 16	com/gundel/bluecontrol/BluetoothService$ConnectThread:this$0	Lcom/gundel/bluecontrol/BluetoothService;
      //   45: aconst_null
      //   46: invokestatic 83	com/gundel/bluecontrol/BluetoothService:access$4	(Lcom/gundel/bluecontrol/BluetoothService;Lcom/gundel/bluecontrol/BluetoothService$ConnectThread;)V
      //   49: aload 6
      //   51: monitorexit
      //   52: aload_0
      //   53: getfield 16	com/gundel/bluecontrol/BluetoothService$ConnectThread:this$0	Lcom/gundel/bluecontrol/BluetoothService;
      //   56: aload_0
      //   57: getfield 35	com/gundel/bluecontrol/BluetoothService$ConnectThread:mmSocket	Landroid/bluetooth/BluetoothSocket;
      //   60: aload_0
      //   61: getfield 21	com/gundel/bluecontrol/BluetoothService$ConnectThread:mmDevice	Landroid/bluetooth/BluetoothDevice;
      //   64: invokevirtual 87	com/gundel/bluecontrol/BluetoothService:connected	(Landroid/bluetooth/BluetoothSocket;Landroid/bluetooth/BluetoothDevice;)V
      //   67: return
      //   68: astore_3
      //   69: aload_0
      //   70: getfield 16	com/gundel/bluecontrol/BluetoothService$ConnectThread:this$0	Lcom/gundel/bluecontrol/BluetoothService;
      //   73: invokestatic 91	com/gundel/bluecontrol/BluetoothService:access$3	(Lcom/gundel/bluecontrol/BluetoothService;)V
      //   76: aload_0
      //   77: getfield 35	com/gundel/bluecontrol/BluetoothService$ConnectThread:mmSocket	Landroid/bluetooth/BluetoothSocket;
      //   80: invokevirtual 51	android/bluetooth/BluetoothSocket:close	()V
      //   83: aload_0
      //   84: getfield 16	com/gundel/bluecontrol/BluetoothService$ConnectThread:this$0	Lcom/gundel/bluecontrol/BluetoothService;
      //   87: invokevirtual 94	com/gundel/bluecontrol/BluetoothService:start	()V
      //   90: return
      //   91: astore 4
      //   93: ldc 37
      //   95: ldc 96
      //   97: aload 4
      //   99: invokestatic 45	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   102: pop
      //   103: goto -20 -> 83
      //   106: astore 7
      //   108: aload 6
      //   110: monitorexit
      //   111: aload 7
      //   113: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	114	0	this	ConnectThread
      //   68	1	3	localIOException1	IOException
      //   91	7	4	localIOException2	IOException
      //   106	6	7	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   25	32	68	java/io/IOException
      //   76	83	91	java/io/IOException
      //   41	52	106	finally
      //   108	111	106	finally
    }
  }
  
  private class ConnectedThread
    extends Thread
  {
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final BluetoothSocket mmSocket;
    
    public ConnectedThread(BluetoothSocket paramBluetoothSocket)
    {
      Log.d("BluetoothService", "create ConnectedThread");
      this.mmSocket = paramBluetoothSocket;
      InputStream localInputStream = null;
      try
      {
        localInputStream = paramBluetoothSocket.getInputStream();
        OutputStream localOutputStream2 = paramBluetoothSocket.getOutputStream();
        localOutputStream1 = localOutputStream2;
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          Log.e("BluetoothService", "temp sockets not created", localIOException);
          OutputStream localOutputStream1 = null;
        }
      }
      this.mmInStream = localInputStream;
      this.mmOutStream = localOutputStream1;
    }
    
    public void cancel()
    {
      try
      {
        this.mmSocket.close();
        return;
      }
      catch (IOException localIOException)
      {
        Log.e("BluetoothService", "close() of connect socket failed", localIOException);
      }
    }
    
    public void run()
    {
      Log.i("BluetoothService", "BEGIN mConnectedThread");
      byte[] arrayOfByte = new byte[1024];
      try
      {
        for (;;)
        {
          int i = this.mmInStream.read(arrayOfByte);
          Message localMessage = BluetoothService.this.mHandler.obtainMessage(2, i, -1, arrayOfByte);
          BluetoothService.this.mHandler.sendMessage(localMessage);
        }
        return;
      }
      catch (IOException localIOException)
      {
        Log.e("BluetoothService", "disconnected", localIOException);
        BluetoothService.this.connectionLost();
      }
    }
    
    public void write(byte[] paramArrayOfByte)
    {
      try
      {
        this.mmOutStream.write(paramArrayOfByte);
        Message localMessage = BluetoothService.this.mHandler.obtainMessage(3, -1, -1, paramArrayOfByte);
        BluetoothService.this.mHandler.sendMessage(localMessage);
        return;
      }
      catch (IOException localIOException)
      {
        Log.e("BluetoothService", "Exception during write", localIOException);
      }
    }
  }
}


/* Location:           C:\Users\Arjun\Desktop\final\classes-dex2jar.jar
 * Qualified Name:     com.gundel.bluecontrol.BluetoothService
 * JD-Core Version:    0.7.0.1
 */