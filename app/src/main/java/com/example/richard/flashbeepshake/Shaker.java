/***
  Copyright (c) 2008-2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Advanced Android Development_
    http://commonsware.com/AdvAndroid
*/

package com.example.richard.flashbeepshake;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import java.util.ArrayList;
import java.util.List;
/***
 Copyright (c) 2008-2012 CommonsWare, LLC
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.

 From _The Busy Coder's Guide to Advanced Android Development_
 http://commonsware.com/AdvAndroid
 */
public class Shaker {
  private SensorManager mgr;
  private long lastShakeTimestamp;
  private double threshold;
  private long gap;
  private Shaker.Callback cb;
  
  public Shaker(Context ctxt, double threshold, long gap,
                  Shaker.Callback cb) {
    this.threshold=threshold*threshold;
    this.threshold=this.threshold
                    *SensorManager.GRAVITY_EARTH
                    *SensorManager.GRAVITY_EARTH;
    this.gap=gap;
    this.cb=cb;
    
    mgr=(SensorManager)ctxt.getSystemService(Context.SENSOR_SERVICE);
    mgr.registerListener(listener,
                          mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                          SensorManager.SENSOR_DELAY_UI);
  }
  
  public void close() {
    mgr.unregisterListener(listener);
  }
  
  private void isShaking() {
    long now=SystemClock.uptimeMillis();

    if (lastShakeTimestamp==0) {
      lastShakeTimestamp=now;
      cb.shakingStarted();
    }
    else {
      lastShakeTimestamp=now;
    }
  }
  
  private void isNotShaking() {
    long now=SystemClock.uptimeMillis();

    if (lastShakeTimestamp>0) {
      if (now-lastShakeTimestamp>gap) {
        lastShakeTimestamp=0;
          cb.shakingStopped();
      }
    }
  }
  
  public interface Callback {
    void shakingStarted();
    void shakingStopped();
  }
  
  private SensorEventListener listener=new SensorEventListener() {
    public void onSensorChanged(SensorEvent e) {

      if (cb == null) throw new IllegalStateException("Callback can't be null");

      if (e.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
        double netForce=e.values[0]*e.values[0];
        
        netForce+=e.values[1]*e.values[1];
        netForce+=e.values[2]*e.values[2];
        
        if (threshold<netForce) {
          isShaking();
        }
        else {
          isNotShaking();
        }
      }
    }
    
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
      // unused
    }
  };
}