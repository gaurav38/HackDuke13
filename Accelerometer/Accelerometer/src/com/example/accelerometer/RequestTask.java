package com.example.accelerometer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

class RequestTask extends AsyncTask<String, String, String>{

	private Context context = null;
	RequestTask(Context context){
		this.context = context; 
	}
	
    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
                //Log.i("response=", responseString);
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        
        responseString = responseString.trim();
        int gestureID = Integer.parseInt(responseString);
		Object sbservice = context.getSystemService( "statusbar" );
		Class<?> statusbarManager = null;
		try {
			statusbarManager = Class.forName( "android.app.StatusBarManager" );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Method showsb = null;
		if(gestureID == 1){
			if (Build.VERSION.SDK_INT >= 17) {
				try {
					showsb = statusbarManager.getMethod("expandNotificationsPanel");
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					showsb = statusbarManager.getMethod("expand");
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				showsb.invoke( sbservice );
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("response=", responseString);
			Log.i("Down", "Down");
			//flag = true;
		}else if(gestureID == 2){
			if (Build.VERSION.SDK_INT >= 17) {
				try {
					showsb = statusbarManager.getMethod("collapsePanels");
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					showsb = statusbarManager.getMethod("collapse");
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				showsb.invoke( sbservice );
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//flag = false;
			//System.out.println("UP");
			Log.i("response=", responseString);
			Log.i("UP", "UP");
		}else {
			//System.out.println("No Action");
			Log.i("No Action", "No action");
		}

        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
}
