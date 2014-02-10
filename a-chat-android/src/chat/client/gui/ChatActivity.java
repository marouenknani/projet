/*****************************************************************
JADE - Java Agent DEvelopment Framework is a framework to develop 
multi-agent systems in compliance with the FIPA specifications.
Copyright (C) 2000 CSELT S.p.A. 

GNU Lesser General Public License

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation, 
version 2.1 of the License. 

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA  02111-1307, USA.
 *****************************************************************/

package chat.client.gui;

import jade.core.MicroRuntime;
import jade.util.Logger;
import jade.wrapper.ControllerException;
import jade.wrapper.O2AException;
import jade.wrapper.StaleProxyException;

import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import chat.client.agent.ChatClientInterface;

import com.google.gson.Gson;

/**
 * This activity implement the chat interface.
 * 
 * @author Michele Izzo - Telecomitalia
 */

public class ChatActivity extends Activity {
	private Logger logger = Logger.getJADELogger(this.getClass().getName());

	static final int PARTICIPANTS_REQUEST = 0;

	private MyReceiver myReceiver;

	private String nickname;
	private ChatClientInterface chatClientInterface;
		
    ProgressDialog pDialog;
    // GPSTracker class
    GPSTracker gps;
    
    double latitude;
    double longitude;
    
    TextView chatField ;
    
    String DATA = "" ;
    

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			nickname = extras.getString("nickname");
		}

		try {
			chatClientInterface = MicroRuntime.getAgent(nickname)
					.getO2AInterface(ChatClientInterface.class);
		} catch (StaleProxyException e) {
			showAlertDialog(getString(R.string.msg_interface_exc), true);
		} catch (ControllerException e) {
			showAlertDialog(getString(R.string.msg_controller_exc), true);
		}

		myReceiver = new MyReceiver();

		IntentFilter refreshChatFilter = new IntentFilter();
		refreshChatFilter.addAction("jade.demo.chat.REFRESH_CHAT");
		registerReceiver(myReceiver, refreshChatFilter);

		IntentFilter clearChatFilter = new IntentFilter();
		clearChatFilter.addAction("jade.demo.chat.CLEAR_CHAT");
		registerReceiver(myReceiver, clearChatFilter);

		setContentView(R.layout.chat);

		Button button = (Button) findViewById(R.id.button_send);
		button.setOnClickListener(buttonSendListener);
		
		chatField = (TextView)findViewById(R.id.chatTextView);
		
		chatField.setText("");
		gps = new GPSTracker(ChatActivity.this); 
        if(gps.canGetLocation()){                   
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();                  	                                      
            new AttemptGetAddress().execute();                    
        }else{
            gps.showSettingsAlert();
        }
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		unregisterReceiver(myReceiver);

		logger.log(Level.INFO, "Destroy activity!");
	}

	private OnClickListener buttonSendListener = new OnClickListener() {
		public void onClick(View v) 
		{
			chatField.setText("");
			gps = new GPSTracker(ChatActivity.this); 
            if(gps.canGetLocation()){                   
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();                  	                                      
                new AttemptGetAddress().execute();                    
            }else{
                gps.showSettingsAlert();
            }
            
        	try {						                
				chatClientInterface.handleSpoken(DATA);					
			} catch (O2AException e) {
				showAlertDialog(e.getMessage(), false);
			}			

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.chat_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {				
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PARTICIPANTS_REQUEST) {
			if (resultCode == RESULT_OK) {
				// TODO: A partecipant was picked. Send a private message.
			}
		}
	}

	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			logger.log(Level.INFO, "Received intent " + action);
			if (action.equalsIgnoreCase("jade.demo.chat.REFRESH_CHAT")) {
				chatField = (TextView) findViewById(R.id.chatTextView);
				chatField.append(intent.getExtras().getString("sentence"));
				//scrollDown();
			}
			if (action.equalsIgnoreCase("jade.demo.chat.CLEAR_CHAT")) {
				chatField = (TextView) findViewById(R.id.chatTextView);
				chatField.setText("");
			}
		}
	}

	/*private void scrollDown() {
	//	final ScrollView scroller = (ScrollView) findViewById(R.id.scroller);
		chatField = (TextView) findViewById(R.id.chatTextView);
		scroller.smoothScrollTo(0, chatField.getBottom());
	}*/

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		chatField = (TextView) findViewById(R.id.chatTextView);
		savedInstanceState.putString("chatField", chatField.getText()
				.toString());
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		chatField = (TextView) findViewById(R.id.chatTextView);
		chatField.setText(savedInstanceState.getString("chatField"));
	}

	private void showAlertDialog(String message, final boolean fatal) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ChatActivity.this);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialog, int id) {
								dialog.cancel();
								if(fatal) finish();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();		
	}
	
	
	class AttemptGetAddress extends AsyncTask<String, String, String> {
   	 String location_string;    	
   	 JSONObject location_json;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ChatActivity.this);
			pDialog.setMessage("En cours...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// get lat and lng value
			//35.65998, 10.08874	 
			//"http://maps.google.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=false"
			//latitude = 35.65998;
			//longitude = 10.08874;
			location_string = new Helper().GET("http://maps.google.com/maps/api/geocode/json?latlng="+latitude+","+longitude+"&sensor=false");
	        //location_string = new Helper().GET("http://maps.google.com/maps/api/geocode/json?latlng=35.65998,10.08874&sensor=false");			
			try {
				location_json = new JSONObject(location_string);				
				//Log.d("aloo", location_json.getJSONArray("results").getJSONObject(0).getString("formatted_address").toString());
				location_string = location_json.getJSONArray("results").getJSONObject(0).getString("formatted_address").toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d("lol", location_string);
	        
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			pDialog.dismiss();
			Date d = new Date();
			CharSequence systemDate  = DateFormat.format("EEEE, MMMM d, yyyy hh:mm:ss a", d.getTime());			
			
			Context mContext = getBaseContext();
			DisplayMetrics displayMetrics = new DisplayMetrics();
			displayMetrics = mContext.getResources().getDisplayMetrics();
			int mScreenWidth = displayMetrics.widthPixels;
			int mSreenHeight = displayMetrics.heightPixels;
			
			DATA = "Informations sur le contexte : \nLatitude : " 
					+ latitude + "\nLongitude : " + longitude + "\nAddresse : " 
							+ location_string + "\nDate : " + systemDate + 
							"\nLangue : " + Locale.getDefault().getLanguage() 
							+"\nType de réseau : " + new Helper().getNetworkClass(getBaseContext()) 
							+ "\nType de dispositif : " 
							+ new Helper().isTabletDevice(getBaseContext())
							+ "\nTaile de l'écran : " + mScreenWidth + "x" + mSreenHeight 
							+ "\nNiveau : " + new Helper().level();
			
			Gson gson = new Gson();
			Contexte c= new Contexte(longitude+"", latitude+"", location_string, Locale.getDefault().getLanguage().toString() , systemDate.toString(), new Helper().getNetworkClass(getBaseContext()).toString(), new Helper().isTabletDevice(getBaseContext()).toString(),  mScreenWidth + "x" + mSreenHeight , new Helper().level());
			
			DATA = gson.toJson(c);
			
			chatField.setText(c.toString());
		}
   }
}