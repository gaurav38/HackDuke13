package com.example.accelerometer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

public class MainActivity extends Activity {
	private PebbleKit.PebbleDataReceiver accel = null;
	TextView text;
	boolean flag;
	private final static UUID PEBBLE_APP_UUID = UUID.fromString("729dfbcc-9160-4c5b-92c6-72efeeb2c20b");
	//private NotificationReceiver nReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//text = (TextView) findViewById(R.id.text);
		flag = false;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Always deregister any Activity-scoped BroadcastReceivers when the Activity is paused
		
		if (accel != null) {
			unregisterReceiver(accel);
			accel = null;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		final Handler handler = new Handler();

		// To receive data back from the sports watch-app, Android
		// applications must register a "DataReceiver" to operate on the
		// dictionaries received from the watch.
		//
		// In this example, we're registering a receiver to listen for
		// changes in the activity state sent from the watch, allowing
		// us the pause/resume the activity when the user presses a
		// button in the watch-app.
		accel = new PebbleKit.PebbleDataReceiver(PEBBLE_APP_UUID) {
			@Override
			public void receiveData(final Context context, final int transactionId, final PebbleDictionary data) {
				final long value = data.getInteger(0);
				final long value1 = data.getInteger(1);
				final long value2 = data.getInteger(2);

				handler.post(new Runnable() {
					@Override
					public void run() {
						try {
							updateUi(value, value1, value2);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		};
		PebbleKit.registerReceivedDataHandler(this, accel);
	}

	public void updateUi(long value, long value1, long value2) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException, ExecutionException {
		TextView statusText = (TextView) findViewById(R.id.text);
		statusText.setText("x="+value+" y="+value1+" z="+value2);
		
		RequestTask req = new RequestTask(getApplicationContext());
		req.execute("http://172.30.21.141:8080/GestureRecognizer/GRTServlet?x="+value+"&y="+value1+"&z="+value2);		
	}
}
