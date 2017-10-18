package com.gundel.bluecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FirstScreen
  extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    startActivity(new Intent(this, Main.class));
  }
}


/* Location:           C:\Users\Arjun\Desktop\final\classes-dex2jar.jar
 * Qualified Name:     com.gundel.bluecontrol.FirstScreen
 * JD-Core Version:    0.7.0.1
 */